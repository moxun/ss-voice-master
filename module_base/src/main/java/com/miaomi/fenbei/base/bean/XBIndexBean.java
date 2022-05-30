package com.miaomi.fenbei.base.bean;

import java.util.List;

public class XBIndexBean {
    private List<XBItemBean> list;
    private List<XBIndexLogBean> log;
    private String rule;

    public String getRule(){
        return rule;
    }


    public void setRule(String rule){
        this.rule = rule;
    }

    public List<XBIndexLogBean> getLog() {
        return log;
    }

    public void setLog(List<XBIndexLogBean> log) {
        this.log = log;
    }

    public List<XBItemBean> getList() {
        return list;
    }

    public void setList(List<XBItemBean> list) {
        this.list = list;
    }
}
