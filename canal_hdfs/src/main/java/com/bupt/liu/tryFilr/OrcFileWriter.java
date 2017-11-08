package com.bupt.liu.tryFilr;

import com.bupt.liu.avro.schema.DataSchema;
import com.bupt.liu.util.HiveDataType;
import com.bupt.liu.util.OrcData;
import com.bupt.liu.util.OrcFileLimiter;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hive.ql.exec.vector.ColumnVector;
import org.apache.hadoop.hive.ql.exec.vector.VectorizedRowBatch;
import org.apache.log4j.Logger;
import org.apache.orc.CompressionKind;
import org.apache.orc.OrcFile;
import org.apache.orc.TypeDescription;
import org.apache.orc.Writer;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by lpeiz on 2017/11/3.
 */
public class OrcFileWriter {

    private static final Logger LOG = Logger.getLogger(OrcFileWriter.class);
    private Writer writer;
    private VectorizedRowBatch batch;    //创建一个批
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private final SimpleDateFormat file_sdf = new SimpleDateFormat("HH-mm-ss");
    private int colsize; //这一类数据的 栏数
    private String cols[];
    private HashMap<String, String> colsType;
    private String wpath;
    private OrcFile.WriterOptions woption;
    private TypeDescription schema;
    private String hiveTable;
//    private int wcounter;   //第几个写线程
//    private int

    private int batchMax = 1000;
    private long timeMax = 30000;

    private OrcFileLimiter limiter;

    public OrcFileWriter(String table) throws IOException{
        this.hiveTable = table;
        Configuration configuration = new Configuration();
        configuration.set("mapreduce.framework.name", "local");
        configuration.set("fs.defaultFS","file:///");
//        System.out.println("table:" + table);

        this.limiter = new OrcFileLimiter();
        this.woption = OrcFile.writerOptions(configuration).
                setSchema(this.schema).blockSize(134217728L).stripeSize(33554432L).compress(CompressionKind.SNAPPY);
        this.colsType = new DataSchema().getTypes(table);

        this.cols = new DataSchema().getCols(table);
        System.out.println(table);
        for(int i = 0; i < cols.length; i++)
            System.out.println("cols" + i + ":" + cols[i]);
        this.colsize = cols.length;

//        System.out.println("colsType:" + colsType + "cols" + cols + "colsize"+ colsize);
        this.loadSchema();
        this.batch = schema.createRowBatch(OrcFileWriter.this.batchMax);
        this.recreate();
    }

    public void loadSchema(){
        this.schema = TypeDescription.createStruct();

        String[] arr = this.cols;
        int len = arr.length;
        for(int i = 0; i < len; i++){
            String col = arr[i];
            String type = colsType.get(col);
//            System.out.println("col:"+col);
//            System.out.println("type:"+type);

            HiveDataType hdtype = HiveDataType.findType(type);
            TypeDescription orcType = null;
            if(hdtype == null) {
                orcType = TypeDescription.createString();
            } else {
                orcType = hdtype.toOrcTypeDescption();
            }

            this.schema.addField(col, orcType);
        }
    }

    private void recreate() throws IOException {
        if(this.writer == null) {
            this.wpath = this.newfilePath();
            OrcFileWriter.LOG.info("Writer[" + "" + "] Create file [" + this.wpath + "] orc writer ...");
            this.writer = OrcFile.createWriter(new Path(this.wpath), OrcFileWriter.this.woption.setSchema(schema));
//            this.wstate = OrcWriterState.PERPARING;
            OrcFileWriter.LOG.info("Writer[" + "" + "] Reset time limiter ...");
            this.limiter.resetTime();
        }

    }
//指定文件生成的路径
    private String newfilePath() {
        long uuid = UUID.randomUUID().getLeastSignificantBits();
        long timstamp = System.currentTimeMillis();
        StringBuilder sb = new StringBuilder();
        Date cur = new Date();
//        sb.append("/user/jrdw/tmp").append("/").append(OrcFileWriter.this.hiveTable).append("/dt=r-").append(this.sdf.format(cur)).append("/").append(this.file_sdf.format(cur)).append(timstamp).append("_").append(uuid).append(".orc");
        sb.append("F:\\atestDir\\tmp\\").append(OrcFileWriter.this.hiveTable).append("-").append(this.sdf.format(cur)).append("-").append(this.file_sdf.format(cur)).append(timstamp).append("_").append(uuid).append(".orc");

        return sb.toString();
    }



    private void setValue(ColumnVector vector, int row, String type, String value) {
        HiveDataType.setValue(vector, row, type, value);
    }

    public void write(List<OrcData> ods) throws IOException{
        Iterator it = ods.iterator();


        for(int j = 0; j < ods.size(); j++) {
            System.out.println("thread write *******************");
            System.out.println("ods size:" + ods.size());
            OrcData d = ods.get(j);

            Map datas = d.getDatas(); //OrcData getDatas
            System.out.println("datas : " + datas);
            System.out.println(this.colsize);
            int row = this.batch.size++;
            for(int i = 0; i < this.colsize; i++){
//                System.out.println("aaaaaaaaaaaaaaaaaaa");
                String value = (String) datas.get(cols[i]);
                String type =  colsType.get(cols[i]);
//                System.out.println(value + "!!!!!!!!!" + type);

                try {
                    OrcFileWriter.this.setValue(this.batch.cols[i], row, type, value);
//                    System.out.println("1111111111111111111111111111111111");
                } catch (Exception var10) {
                    System.out.println("setValue 出错");
//                    OrcFileWriter.LOG.error("Writer[" + this.wcounter + "] When set value occur Exception : col [" + OrcFileWriter.this.cols[i] + "], value [" + value + "], type [" + type + "],error msg " + var10.getMessage(), var10);
                }
            }
            this.checkAndFlush();
        }
        this.checkAndClose();

    }

    private void checkAndFlush() throws IOException {
//        System.out.println("checkflush!!!!!!!!!!!!!!!!!!!!!!!!!!");
        if(this.limiter.isOverBatchLimit(this.batch.size)) {
//            System.out.println("overBatchLimit!!!!!!!!!!!!!!!!!!!!!!!!!!");
            OrcFileWriter.LOG.info("Writer[ batch size " + this.batch.size + " has over " + OrcFileWriter.this.batchMax + " flush and reset...");
            this.writer.addRowBatch(this.batch);
            this.flush();
        }

    }

    private void checkAndClose() throws IOException {
        if(this.limiter.isOverTimeLimit()) {
//            System.out.println("overTimeLimit!!!!!!!!!!!!!!!!!!!!!!!!!!");
            OrcFileWriter.LOG.info("Writer[] Writer has over time limit " + OrcFileWriter.this.timeMax + " ... close file...");
            this.writer.addRowBatch(this.batch);
            this.closeFile();
//            this.wstate = OrcWriterState.NEED_CLOSE;
        }

    }

    public void flush() throws IOException {
        this.batch.reset();
        this.writer.writeIntermediateFooter();
    }

    public void closeFile() throws IOException {
        OrcFileWriter.LOG.info("Writer[" + "" + "] Closing file....");
        this.batch.reset();
        this.writer.close();
        this.writer = null;
//        this.wstate = OrcWriterState.CLOSED;
        OrcFileWriter.LOG.info("Writer[" + "" + "] Closed file, Begin to mv tmp file to table path....");
//        String successFile = this.successFilePath();
//        OrcFileWriter.this.hdfsUtils.mvHdfsPath(this.wpath, successFile);
//        OrcFileWriter.LOG.info("Writer[" + this.wcounter + "] mv file[" + this.wpath + "] to [" + successFile + "] done....");
        this.recreate();
    }

    public void close() throws IOException {
        if(this.writer != null) {
            if(this.batch.size > 0) {
                this.writer.addRowBatch(this.batch);
                this.batch.reset();
            }

            /*if(!this.wstate.equals(OrcWriterState.CLOSED)) {
                this.writer.close();
                this.wstate = OrcWriterState.CLOSED;
                OrcFileWriterFactory.LOG.info("Writer[" + this.wcounter + "] Closed....");
            }*/
        }

    }
}
