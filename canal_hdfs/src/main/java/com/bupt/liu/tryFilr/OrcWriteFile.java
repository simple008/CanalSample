package com.bupt.liu.tryFilr;

import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.client.CanalConnectors;
import com.alibaba.otter.canal.protocol.CanalEntry;
import com.alibaba.otter.canal.protocol.Message;
import com.bupt.liu.util.Data;
import com.bupt.liu.util.OrcData;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.*;

/**
 * Created by lpeiz on 2017/10/21.
 */

public class OrcWriteFile {
    //    private static final Logger LOGGER = Logger.getLogger(OrcWriteFile.class);
    static List<Data<OrcData>> results = new ArrayList<>();

    public static void main(String[] args) throws IOException{
        CanalConnector connector = CanalConnectors.newSingleConnector(new InetSocketAddress("127.0.0.1", 11111), "example", "", "");
        MultiWrite multiWrite = new MultiWrite();
        int batchSize = 1000; //一批次拿1000条数据
        int emptyCount = 0;
        try {
            connector.connect();
            connector.subscribe(".*\\..*");//不知道什么意思
            connector.rollback();
            int totalEmtryCount = 1200;
            while (emptyCount < totalEmtryCount) {
                Message message = connector.getWithoutAck(batchSize); //获取指定数量的数据
                long batchId = message.getId();
//                System.out.println("batchId a  : " + batchId);
//                System.out.println("message size :" + message.getEntries().size());
                int size = message.getEntries().size();

                int pr = 0;
                if (batchId == -1 || size == 0) {
                    emptyCount++;
//                    System.out.println("empty count : " + emptyCount);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {  //取到数据的处理
                    emptyCount = 0;
                    pr++;
                    printEntry(message.getEntries());
                    System.out.println("results size:" + results.size());
//                    for (CanalEntry.Entry entry : message.getEntries())
//                        System.out.println(entry.getHeader().getEventLength());
                    if(pr>1000) break;
                }

                if(results.size()>=10){
                    System.out.println("要执行了");
//                    PackageIntoOrc.intoOrc(results);
                    multiWrite.pushData(results);
                    System.out.println("执行了");
                    /*for(Data<OrcData> orcData : results){
                        System.out.println(orcData.getData().getDatas());
                        for(Map.Entry entry :orcData.getData().getDatas().entrySet())
                            System.out.println(entry.getKey() + "***" + entry.getValue());
                    }*/
                    results.clear();
                }

                connector.ack(batchId);
            }
            System.out.println("empty too many times, exit ");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connector.disconnect();
        }

    }

    private static void printEntry(@NotNull List<CanalEntry.Entry> entrys) {
        for (CanalEntry.Entry entry : entrys) {
            //过滤不需要的数据表
            if(tableFilter(entry.getHeader().getTableName()))
                continue;
            if (entry.getEntryType() == CanalEntry.EntryType.TRANSACTIONBEGIN ||
                    entry.getEntryType() == CanalEntry.EntryType.TRANSACTIONEND) {
                continue;
            }

            CanalEntry.RowChange rowChange = null;
            try {
                rowChange = CanalEntry.RowChange.parseFrom(entry.getStoreValue());
            } catch (Exception e) {
                throw new RuntimeException("ERROR ## parser of eromanga-event has an error , data:" +
                        entry.toString(), e);
            }

            CanalEntry.EventType eventType = rowChange.getEventType();
            System.out.println(String.format("================> binlog[%s:%s] , name[%s,%s] , eventType : %s",
                    entry.getHeader().getLogfileName(), entry.getHeader().getLogfileOffset(),
                    entry.getHeader().getSchemaName(), entry.getHeader().getTableName(),
                    eventType));

//            Avro avro = new Avro();

            for (CanalEntry.RowData rowData : rowChange.getRowDatasList()) {
                Map<CharSequence, CharSequence> src = new HashMap<>();
                Map<CharSequence, CharSequence> cur = new HashMap<>();
                //dbtb

                if (eventType == CanalEntry.EventType.DELETE) {
                    List<CanalEntry.Column> columns = rowData.getBeforeColumnsList();
                    for (CanalEntry.Column column : columns) {
                        src.put(column.getName(), column.getValue());    //把所有字段都放到了src里  删除之前的数据
                        if (column.getIsKey()) {
                            cur.put(column.getName(), column.getValue());
                        }
                        if (column.getIsNull()) {
                            src.put(column.getName(), null);
                        }
                    }
                } else if (eventType == CanalEntry.EventType.UPDATE) {
                    List<CanalEntry.Column> srcCols = rowData.getBeforeColumnsList();
                    List<CanalEntry.Column> curCols = rowData.getAfterColumnsList();

                    for (int i = 0; i < srcCols.size(); i++) {
                        src.put(srcCols.get(i).getName(), srcCols.get(i).getValue());

                        if (srcCols.get(i).getIsNull()) {
                            src.put(srcCols.get(i).getName(), null);
                        }

                        if (curCols.get(i).getUpdated() || curCols.get(i).getIsKey()) {
                            cur.put(curCols.get(i).getName(), curCols.get(i).getValue());
                            if (curCols.get(i).getIsNull()) {
                                cur.put(curCols.get(i).getName(), null);
                            }
                        }
                    }
                } else if (eventType == CanalEntry.EventType.INSERT) {
                    List<CanalEntry.Column> columns = rowData.getAfterColumnsList();
                    for (CanalEntry.Column column : columns) {
                        cur.put(column.getName(), column.getValue());
                        if (column.getIsNull()) {
                            cur.put(column.getName(), null);
                        }
                    }
                } else {
                    //除去insert update delete的情况
                }

                OrcData od = new OrcData();
                Map<String, String> merged = merge(src, cur);
                od.setDatas(merged);
                od.setTable(entry.getHeader().getTableName());
                Data<OrcData> dod = new Data<OrcData>();
                dod.setData(od);
                results.add(dod);
//                System.out.println("results size" + results.size());
            }
        }

    }

    private static Map<String, String> merge(Map<CharSequence, CharSequence> src, Map<CharSequence, CharSequence> cur){
        Map<String, String> merged = new HashMap<>();
        Map.Entry<CharSequence, CharSequence> err = null;
        if(src != null){
            for(Map.Entry<CharSequence, CharSequence> entry : src.entrySet()){
                err = entry;
                String key = entry.getKey().toString().trim().toLowerCase();
//                if(filter(key))
                    merged.put(key,entry.getValue() == null ? null:entry.getValue().toString());
            }
        }
        if(cur != null){
            for(Map.Entry<CharSequence, CharSequence> entry : cur.entrySet()){
                err = entry;
                String key = entry.getKey().toString().trim().toLowerCase();
//                if(filter(key))
                    merged.put(key, entry.getValue() == null ? null : entry.getValue().toString());
            }
        }

        return merged;
    }

    private static OrcData addProcessedData(List<Data<OrcData>> results) {
        Data<OrcData> dod = new Data<OrcData>();
        OrcData od = new OrcData();
        dod.setData(od);
        results.add(dod);
        /*for (Map.Entry<String, String> entry : headers.entrySet()) {
            dod.putHeaders(entry.getKey(), entry.getValue());
        }*/
        return od;
    }

    private static boolean filter(String key){
        Set<String> dict = new HashSet<String>();
        dict.add("mmsi");
        dict.add("shiplength");
        dict.add("shipwide");
        if(dict.contains(key)){
            return true;
        }
        else return false;
    }
    private static boolean tableFilter(String table){
        Set<String> dict = new HashSet<String>();
        dict.add("myaisbasestationreport_mem");
        dict.add("myaisdynamic_fed");
        dict.add("myaisstatic_fed");
        if(dict.contains(table)){
            return true;
        }
        else return false;
    }

}