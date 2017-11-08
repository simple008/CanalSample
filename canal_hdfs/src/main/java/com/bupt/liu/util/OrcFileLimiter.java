package com.bupt.liu.util;

import org.apache.log4j.Logger;

/**
 * Created by lpeiz on 2017/11/6.
 */
public class OrcFileLimiter {
    private static final Logger LOG = Logger.getLogger(OrcFileLimiter.class);
    private int batchMax = 100;
    private long bytesMax;
    private long lastTime = System.currentTimeMillis();
    private long timeMax = 10000;

    public OrcFileLimiter() {
    }

    public boolean isOverBatchLimit(int batchCur) {
        if(batchCur >= this.batchMax) {
            LOG.info("cur batch [" + batchCur + "], limit [" + this.batchMax + "]");
            return true;
        } else {
            return false;
        }
    }

    public boolean isOverBytesLimit(long bytesCur) {
        return bytesCur >= this.bytesMax;
    }

    public boolean isOverTimeLimit() {
        return System.currentTimeMillis() - this.lastTime >= this.timeMax;
    }

    public void resetTime() {
        this.lastTime = System.currentTimeMillis();
    }

    public int getBatchMax() {
        return this.batchMax;
    }

    public OrcFileLimiter setBatchMax(int batchMax) {
        this.batchMax = batchMax;
        return this;
    }

    public long getBytesMax() {
        return this.bytesMax;
    }

    public OrcFileLimiter setBytesMax(long bytesMax) {
        this.bytesMax = bytesMax;
        return this;
    }

    public OrcFileLimiter setTimeMax(long timeMax) {
        this.timeMax = timeMax;
        return this;
    }

    public long getTimeMax() {
        return this.timeMax;
    }

    public static void main(String[] args) throws InterruptedException {
        long first = System.currentTimeMillis();
        Thread.sleep(10000L);
        long send = System.currentTimeMillis();
        System.out.println(send - first);
    }
}
