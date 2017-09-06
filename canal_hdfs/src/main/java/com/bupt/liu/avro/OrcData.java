package com.bupt.liu.avro;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lpeiz on 2017/9/2.
 */
public class OrcData {
    private Map<String, String> datas;
    private String checkcode;

    public OrcData() {
    }

    public Map<String, String> getDatas() {
        return this.datas;
    }

    public void setDatas(Map<String, String> datas) {
        this.datas = datas;
    }

    public void putData(String colname, String value) {
        if(this.datas == null) {
            this.datas = new HashMap();
        }

        this.datas.put(colname, value);
    }

    public String getCheckcode() {
        return this.checkcode;
    }

    public void setCheckcode(String checkcode) {
        this.checkcode = checkcode;
    }
}
