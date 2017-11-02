package com.bupt.liu.tryFilr;

import com.bupt.liu.util.Data;
import com.bupt.liu.util.OrcData;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hive.ql.exec.vector.BytesColumnVector;
import org.apache.hadoop.hive.ql.exec.vector.DoubleColumnVector;
import org.apache.hadoop.hive.ql.exec.vector.LongColumnVector;
import org.apache.hadoop.hive.ql.exec.vector.VectorizedRowBatch;
import org.apache.orc.CompressionKind;
import org.apache.orc.OrcFile;
import org.apache.orc.TypeDescription;
import org.apache.orc.Writer;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;





public class PackageIntoOrc {
    public static void main(String[] args) throws Exception{
        intoOrc(new ArrayList<Data<OrcData>>());
    }

    public static void intoOrc(List<Data<OrcData>> results) throws IOException {
        SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        long time = System.currentTimeMillis();
        String d = format.format(time);

        Path filePath = new Path("F:\\atestDir\\tmp\\"+ d + ".orc");

        Configuration conf = new Configuration();
        conf.set("mapreduce.framework.name", "local");
        conf.set("fs.defaultFS","file:///");

        TypeDescription schema = TypeDescription.fromString("struct<mmsi:int," +
                "shiplength:double,shipwidth:double>");
        FileSystem.getLocal(conf);
        Writer writer = OrcFile.createWriter(filePath, OrcFile.writerOptions(conf).setSchema(schema));

        VectorizedRowBatch batch = schema.createRowBatch();
        LongColumnVector mmsi = (LongColumnVector) batch.cols[0];
        DoubleColumnVector shiplength = (DoubleColumnVector) batch.cols[1];
        DoubleColumnVector shipwidth = (DoubleColumnVector) batch.cols[2];

        final int BATCH_SIZE = batch.getMaxSize();


        System.out.println("this is result size " + results.size());
        for(int r = 0; r < results.size(); r++){
            int row = batch.size++;
            System.out.println("row:" + row);
            Data<OrcData> orcData = results.get(r);
            Map<String, String> entry = orcData.getData().getDatas();

                mmsi.vector[row] = Integer.parseInt(entry.get("mmsi"));
            System.out.println("this is mmsi" + mmsi.vector[row]);
                shiplength.vector[row] = Double.parseDouble(entry.get("shiplength"));
                shipwidth.vector[row] = Double.parseDouble(entry.get("shipwide"));

            if (row == BATCH_SIZE - 1) {
                writer.addRowBatch(batch);
                batch.reset();
            }
        }
        if (batch.size != 0) {
            writer.addRowBatch(batch);
            batch.reset();
        }
        writer.close();
    }

    public static void writeOrc() throws IOException{
        //定义ORC数据结构，即表结构
//		CREATE TABLE lxw_orc1 (
//		 field1 STRING,
//		 field2 STRING,
//		 field3 STRING
//		) stored AS orc;
        TypeDescription schema = TypeDescription.createStruct()
                .addField("field1", TypeDescription.createString())
                .addField("field2", TypeDescription.createString())
                .addField("field3", TypeDescription.createString());
        //输出ORC文件本地绝对路径
        String lxw_orc1_file = "F:\\atestDir\\tmp\\liu012300.orc";
        Configuration conf = new Configuration();
        conf.set("mapreduce.framework.name", "local");
        conf.set("fs.defaultFS","file:///");
        FileSystem.getLocal(conf);
        Writer writer = OrcFile.createWriter(new Path(lxw_orc1_file),
                OrcFile.writerOptions(conf)
                        .setSchema(schema)
                        .stripeSize(67108864)
                        .bufferSize(131072)
                        .blockSize(134217728)
                        .compress(CompressionKind.ZLIB)
                        .version(OrcFile.Version.V_0_12));
        //要写入的内容
        String[] contents = new String[]{"1,a,aa","2,b,bb","3,c,cc","4,d,dd"};
        contents[0].intern();
        VectorizedRowBatch batch = schema.createRowBatch();
        for(String content : contents) {
            int rowCount = batch.size++;
            String[] logs = content.split(",", -1);
            for(int i=0; i<logs.length; i++) {
                ((BytesColumnVector) batch.cols[i]).setVal(rowCount, logs[i].getBytes());
                //batch full
                if (batch.size == batch.getMaxSize()) {
                    writer.addRowBatch(batch);
                    batch.reset();
                }
            }
        }
        writer.addRowBatch(batch);
        writer.close();
    }
}
