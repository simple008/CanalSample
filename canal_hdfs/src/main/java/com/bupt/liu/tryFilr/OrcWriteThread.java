package com.bupt.liu.tryFilr;

import com.bupt.liu.util.OrcData;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
/**
 * Created by lpeiz on 2017/11/3.
 */
public class OrcWriteThread implements Callable<Boolean> {
    List<OrcData> toWrite ;
    OrcFileWriter writer ;

    public OrcWriteThread(String table) throws IOException{
        toWrite = new ArrayList<OrcData>();
        writer =  new OrcFileWriter(table);
    }

    public void addToWrite(OrcData data){
        if(data != null && data.getDatas().size() != 0){
            System.out.println(data.getDatas().values());
            toWrite.add(data);
        }
    }

    @Override
    public Boolean call() throws IOException {

        Boolean call = new Boolean(true);
            writer.write(toWrite);
        System.out.println("OrcWriteThread call!!!**********************************");
        toWrite.clear();
        return call;
    }

   /* @Override
    public void close() throws IOException {
        if (writer != null) {
            writer.close();
            this.hasClosed = true;
        }
    }*/

    public class OrcWriteThreadCallback{

    }

}
