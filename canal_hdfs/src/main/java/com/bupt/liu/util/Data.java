package com.bupt.liu.util;

import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lpeiz on 2017/9/2.
 */
public class Data<T> {
    private T data;
    private Map<String, String> headers = new HashMap();
    private String targetElement;

    public Data() {
    }

    public Data(String targetName) {
        this.targetElement = targetName;
    }

    public String getTargetElement() {
        return this.targetElement;
    }

    public void setTargetElementName(String name) {
        this.targetElement = name;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers.putAll(headers);
    }

    public Map<String, String> getHeaders() {
        return this.headers;
    }

    public String getHeader(String key) {
        return (String)this.headers.get(key);
    }

    public void putHeaders(String key, String value) {
        String prev = (String)this.headers.get(key);
        if(StringUtils.isNotBlank(prev)) {
            prev = prev + "#^#" + value;
        } else {
            prev = value;
        }

        this.headers.put(key, prev);
    }

    public T getData() {
        return this.data;
    }

    public void setData(T data) {
        this.data = data;
    }
}

