package com.bupt.liu.tryFilr;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hive.ql.exec.vector.VectorizedRowBatch;
import org.apache.orc.OrcFile;
import org.apache.orc.Reader;
import org.apache.orc.RecordReader;

import java.io.IOException;


/**
 * Created by lpeiz on 2017/10/23.
 */
public class ReadOrc {
    public static void main(String[] args) throws IOException{
        Configuration conf = new Configuration();
        FileSystem.getLocal(conf);
        conf.set("mapreduce.framework.name", "local");
        conf.set("fs.defaultFS","file:///");
        Reader reader = OrcFile.createReader(new Path("F:\\atestDir\\tmp\\liu012300.orc"),OrcFile.readerOptions(conf));

        RecordReader rows = reader.rows();
        VectorizedRowBatch batch = reader.getSchema().createRowBatch();

        while (rows.nextBatch(batch)) {
            for(int r=0; r < batch.size; ++r) {


                System.out.println(batch.cols[0]);
            }
        }
        rows.close();
    }
}
