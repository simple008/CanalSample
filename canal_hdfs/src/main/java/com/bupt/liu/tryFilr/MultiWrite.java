package com.bupt.liu.tryFilr;

import com.bupt.liu.util.Data;
import com.bupt.liu.util.OrcData;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by lpeiz on 2017/11/3.
 */
public class MultiWrite {
    private Map<String, OrcWriteThread> writers = new HashMap<>();
    private ExecutorService pool = Executors.newFixedThreadPool(5);
    private Map<String, Future<Boolean>> exeResults = new HashMap<>();

    public static void main(String[] args) {

    }
    public  void pushData(List<Data<OrcData>> results) throws Exception{
        for(int i = 0; i < results.size(); i++){
            String table = results.get(i).getData().getTable();
            System.out.println("result get i: " + results.get(i).getData().getDatas().values() );
            OrcWriteThread writer = calculateDatasToWriter(prepareWriters(table), results.get(i));

        }

        for(Map.Entry<String, OrcWriteThread> entry : writers.entrySet()){
            exeResults.put(entry.getKey(), pool.submit(entry.getValue()));
        }


    }

    private OrcWriteThread prepareWriters(String table) throws IOException {
        String key = table;
        if(!writers.containsKey(key)){
            OrcWriteThread writer = new OrcWriteThread(table);
            writers.put(key, writer);
        }
        return writers.get(key);
    }

    private OrcWriteThread calculateDatasToWriter(OrcWriteThread wt, Data<OrcData> data){
        wt.addToWrite(data.getData());
        return wt;
    }
}
