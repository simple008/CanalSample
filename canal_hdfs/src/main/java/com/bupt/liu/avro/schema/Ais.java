package com.bupt.liu.avro.schema;

import com.bupt.liu.util.TableColumnsInfo;

import javax.naming.ldap.HasControls;
import java.util.HashMap;

public class Ais {
    HashMap<String, String> columns;
    HashMap<String, String> columnsFunctions;
    HashMap<String, String> hiveColumnType;
    String table = "Ais";
    TableColumnsInfo aisTableColumnsInfo =new TableColumnsInfo(table, columns, hiveColumnType, columnsFunctions);


}
