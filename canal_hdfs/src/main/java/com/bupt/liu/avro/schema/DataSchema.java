package com.bupt.liu.avro.schema;

import java.util.HashMap;

/**
 * Created by lpeiz on 2017/11/6.
 */
public class DataSchema {
    static HashMap<String, String[]> cols = new HashMap<>(); //字段名
    static HashMap<String, HashMap<String, String>> types = new HashMap<>();//字段类型

    public DataSchema(){
        HashMap<String, String> typeAisStatic = new HashMap<>();
        HashMap<String, String> typeNsar = new HashMap<>();
        HashMap<String, String> typeAisDynamic = new HashMap<>();
        typeAisStatic.put("mmsi","int");
        typeAisStatic.put("shiplength","double");
        typeAisStatic.put("shipwide","double");
        typeAisStatic.put("callsign","string");
        typeAisStatic.put("imonumber","int");
        typeAisStatic.put("shiptype2","string");
        typeAisStatic.put("destination","string");
        typeAisStatic.put("eta","string");
        typeAisStatic.put("rawdata","string");
        typeAisStatic.put("time","bigint");
        typeAisStatic.put("mid","int");
        typeAisStatic.put("timeStamp","String");
        typeAisStatic.put("type_data","String");


        typeAisDynamic.put("mmsi", "int");
        typeAisDynamic.put("longtitude", "double");
        typeAisDynamic.put("latitude", "double");
        typeAisDynamic.put("direction", "double");
        typeAisDynamic.put("heading", "int");
        typeAisDynamic.put("speed", "double");
        typeAisDynamic.put("status", "int");
        typeAisDynamic.put("rot_sensor", "int");
        typeAisDynamic.put("positionaccurate","int");
        typeAisDynamic.put("second", "int");
        typeAisDynamic.put("rawdata", "string");
        typeAisDynamic.put("time", "bigint");
        typeAisDynamic.put("timestamp", "string");
        typeAisDynamic.put("mid", "int");
        typeAisDynamic.put("type_data", "string");




        typeNsar.put("a","a");

        types.put("myaisstatic_mem", typeAisStatic);
        types.put("nsar", typeNsar);
        types.put("myaisdynamic_mem", typeAisDynamic);

        String[] colsAisStatic = new String[]{"mmsi","shiplength","shipwide","callsign","imonumber","shiptype2",
                "destination","eta","rawdata","time", "mid", "timeStamp", "type_data"};
        String[] colsNsar = new String[]{"asdf","asdf"};
        String[] colsAisDynamic = new String[]{"mmsi","longtitude", "latitude", "direction", "heading", "speed",
                "status", "rot_sensor", "positionaccurate", "second", "rawdata", "time", "timestamp", "mid", "type_data"};

        this.cols.put("myaisstatic_mem", colsAisStatic);
        this.cols.put("nsar", colsNsar);
        this.cols.put("myaisdynamic_mem", colsAisDynamic);
    }

    public static HashMap getTypes(String table){
        return types.get(table);
    }
    public static String[] getCols(String table){
        return cols.get(table);
    }

    public static void main(String[] args) {
        DataSchema dataSchema = new DataSchema();
        System.out.println(getCols("myaisstatic_mem"));
    }
}
