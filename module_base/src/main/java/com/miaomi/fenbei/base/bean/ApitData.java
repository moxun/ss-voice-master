package com.miaomi.fenbei.base.bean;

public class ApitData{
    private String msg;
    private String data;
    private int code;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "ApitData{" +
                "msg='" + msg + '\'' +
                ", data='" + data + '\'' +
                ", code=" + code +
                '}';
    }
}
