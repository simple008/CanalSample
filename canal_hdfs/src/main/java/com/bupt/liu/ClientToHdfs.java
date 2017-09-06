package com.bupt.liu;

import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.client.CanalConnectors;
import com.alibaba.otter.canal.protocol.CanalEntry;
import com.alibaba.otter.canal.protocol.Message;
import com.bupt.liu.util.avro.AisData;
import org.jetbrains.annotations.NotNull;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lpeiz on 2017/9/2.
 */
public class ClientToHdfs {
    public static void main(String args[]) {
        // 创建链接
        CanalConnector connector = CanalConnectors.newSingleConnector(new InetSocketAddress("127.0.0.1",
                11111), "example", "", "");
        int batchSize = 1000;
        int emptyCount = 0;
        try {
            connector.connect();
            connector.subscribe(".*\\..*");
            connector.rollback();
            int totalEmtryCount = 1200;
            while (emptyCount < totalEmtryCount) {
                Message message = connector.getWithoutAck(batchSize); // 获取指定数量的数据
                long batchId = message.getId();
                int size = message.getEntries().size();
                if (batchId == -1 || size == 0) {
                    emptyCount++;
                    System.out.println("empty count : " + emptyCount);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    emptyCount = 0;
                    // System.out.printf("message[batchId=%s,size=%s] \n", batchId, size);
                    printEntry(message.getEntries());
                }

                connector.ack(batchId); // 提交确认
                // connector.rollback(batchId); // 处理失败, 回滚数据
            }

            System.out.println("empty too many times, exit");
        } finally {
            connector.disconnect();
        }
    }

    private static void printEntry(@NotNull List<CanalEntry.Entry> entrys) {
        for (CanalEntry.Entry entry : entrys) {
            if (entry.getEntryType() == CanalEntry.EntryType.TRANSACTIONBEGIN || entry.getEntryType() == CanalEntry.EntryType.TRANSACTIONEND) {
                continue;
            }

            CanalEntry.RowChange rowChage = null;
            try {
                rowChage = CanalEntry.RowChange.parseFrom(entry.getStoreValue());
            } catch (Exception e) {
                throw new RuntimeException("ERROR ## parser of eromanga-event has an error , data:" + entry.toString(),
                        e);
            }

            CanalEntry.EventType eventType = rowChage.getEventType();
            System.out.println(String.format("================> binlog[%s:%s] , name[%s,%s] , eventType : %s",
                    entry.getHeader().getLogfileName(), entry.getHeader().getLogfileOffset(),
                    entry.getHeader().getSchemaName(), entry.getHeader().getTableName(),
                    eventType));

            AisData avro=new AisData();

            for (CanalEntry.RowData rowData : rowChage.getRowDatasList()) {
                Map<CharSequence, CharSequence> src = new HashMap<CharSequence, CharSequence>();
                Map<CharSequence, CharSequence> cur = new HashMap<CharSequence, CharSequence>();
                String dbtb = entry.getHeader().getSchemaName()+"."+entry.getHeader().getTableName();



                if (eventType == CanalEntry.EventType.DELETE) {
                    List<CanalEntry.Column> columns =  rowData.getBeforeColumnsList();
                    for(CanalEntry.Column column:columns){
                        src.put(column.getName(),column.getValue());
                        if (column.getIsKey()) {
                            cur.put(column.getName(), column.getValue());
                        }
                        if (column.getIsNull()) {
                            src.put(column.getName(), null);
                        }
                    }



                    printColumn(rowData.getBeforeColumnsList());
                } else if (eventType == CanalEntry.EventType.UPDATE) {
                    List<CanalEntry.Column> srcCols = rowData.getBeforeColumnsList();
                    List<CanalEntry.Column> curCols = rowData.getAfterColumnsList();

                    for (int ii = 0; ii <= srcCols.size() - 1; ii++) {
                        src.put(srcCols.get(ii).getName(),
                                srcCols.get(ii).getValue());
                        if (srcCols.get(ii).getIsNull()) {
                            src.put(srcCols.get(ii).getName(), null);
                        }
                        // cur add only updated and primary
                        if (curCols.get(ii).getUpdated()
                                || curCols.get(ii).getIsKey()) {
                            cur.put(curCols.get(ii).getName(), curCols
                                    .get(ii).getValue());
                            if (curCols.get(ii).getIsNull()) {
                                cur.put(curCols.get(ii).getName(), null);
                            }
                        }

                    }

                    printColumn(rowData.getAfterColumnsList());
                }else if(eventType == CanalEntry.EventType.INSERT){
                    List<CanalEntry.Column> columns = rowData
                            .getAfterColumnsList();
                    for (CanalEntry.Column column : columns) {
                        cur.put(column.getName(), column.getValue());
                        if (column.getIsNull()) {
                            cur.put(column.getName(), null);
                        }
                        // filter sense field
                    }
                }


                else {
                    System.out.println("-------> before");
                    printColumn(rowData.getBeforeColumnsList());
                    System.out.println("-------> after");
                    printColumn(rowData.getAfterColumnsList());
                }

                avro.setDdl(null);
                avro.setErr(null);
                avro.setDb(entry.getHeader().getDatabaseName());
                avro.setOpt(entry.getHeader().getEventType().toString());
                avro.setSch(entry.getHeader().getDatabaseName());
                avro.setTab(entry.getHeader().getTableName());
//                avro.setMid(tableUid.get(hiveTables.get(avro.getDb() + "." + avro.getTab())).incrementAndGet());
//                avro.setTs(fetchTime);
                // avro data
                avro.setCur(cur);
                avro.setSrc(src);
                Map<CharSequence, CharSequence> cus = new HashMap<>();
//                cus.put(CUS_IP, host);
//                cus.put(CUS_FT, String.valueOf(fetchTime));
                //cus.put("HDFSTABLENAME",hiveTables.get(avro.getDb() + "." + avro.getTab()));
                avro.setCus(cus);
                Data<byte[]> rd = new Data<>();
                rd.setTargetElementName("tp_target");
                rd.putHeaders( "HDFSTABLENAME", (String) this.hiveTables.get(avro.getDb() + "." + avro.getTab()));
                rd.putHeaders("UID", String.valueOf(curpos.getUId()));
                cp.setRowData(binlog, pos, i);
                cp.setEvent(binlog, pos);
                rd.putHeaders("cp", generateCp());
                OrcData od = addProcessedData(results, rd.getHeaders());
                Map<String, String> merged = merge(src, cur);
                addExtendsCols(avro, merged);
                od.setDatas(merged);











            }
        }
    }

    private static void printColumn(@NotNull List<CanalEntry.Column> columns) {
        for (CanalEntry.Column column : columns) {
            System.out.println(column.getName() + " : " + column.getValue() + "    update=" + column.getUpdated());
        }
    }
}
