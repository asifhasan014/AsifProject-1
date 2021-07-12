package com.softron.census.domain;

public class ReportDataTO {

    private String key;
    private byte[] byteArrays;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public byte[] getByteArrays() {
        return byteArrays;
    }

    public void setByteArrays(byte[] byteArrays) {
        this.byteArrays = byteArrays;
    }

    public ReportDataTO() {
    }

}
