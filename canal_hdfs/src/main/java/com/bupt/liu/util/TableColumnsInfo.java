package com.bupt.liu.util;

import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class TableColumnsInfo {
    private static final Logger LOGGER = Logger.getLogger(TableColumnsInfo.class);
    private String table;
    private Map<String,String> columns;
    private Map<String,String> columnFunctions;
    private Map<String,String> hiveColumnTypes;
    private Set<String> stringColumns = new HashSet<>();
    
    public TableColumnsInfo(String table, HashMap<String, String> columns, HashMap<String, String> hiveColumnTypes, HashMap<String, String> columnFunctions) {
        this.table = table;
        this.columns = columns;
        this.columnFunctions = columnFunctions;
        this.hiveColumnTypes = hiveColumnTypes;
        for(Map.Entry<String,String> entry : hiveColumnTypes.entrySet()){
            if(entry.getValue().equals("string")){
                stringColumns.add(entry.getKey());
            }
        }
    }
    //获得hive列名
    public String getColumn(String column){
        String hiveColumn = columns.get(column);
        return hiveColumn;
    }
    
    
    public String getValue(String hiveColumn,String value){
        if(columnFunctions.containsKey(hiveColumn)){
            String function = columnFunctions.get(hiveColumn);
            switch(function){
            case "toNullString" :
                if(stringColumns.contains(hiveColumn)){
                    return "";
                }else{
                    return value;
                }
            case "replaceEsc":
                return value.replace("\t", "").replace("\n", "").replace("\r", "");
            default:
                return value;
            }
        }
        return value;
    }
    
    public String globleFunctions(String hiveColumn,String value){
        if(stringColumns.contains(hiveColumn)){
            return value.replace("\t", " ").replace("\n", " ").replace("\r", " ").replace("\\", " ");
        }
        return value;
    }
    public String changeNullString(String hiveColumn,String value){
        if(stringColumns.contains(hiveColumn)){
            return "";
        }
        return null;
    }
    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public Map<String, String> getColumns() {
        return columns;
    }

    public void setColumns(Map<String, String> columns) {
        this.columns = columns;
    }

    public Set<String> getStringColumns() {
        return stringColumns;
    }

    public void setStringColumns(Set<String> stringColumns) {
        this.stringColumns = stringColumns;
    }

    public Map<String, String> getColumnFunctions() {
        return columnFunctions;
    }

    public void setColumnFunctions(Map<String, String> columnFunctions) {
        this.columnFunctions = columnFunctions;
    }
}