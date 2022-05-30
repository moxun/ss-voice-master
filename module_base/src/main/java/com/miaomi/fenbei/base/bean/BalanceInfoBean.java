package com.miaomi.fenbei.base.bean;

public class BalanceInfoBean {




    private String earning;
    private int identify_status;
    private double today_pending;
    private double today_rent;
    private double month_pending;
    private double month_rent;
    private boolean bind_status;
    private String alipay_account;
    private boolean bank_status;

    public boolean isBank_status() {
        return bank_status;
    }

    public void setBank_status(boolean bank_status) {
        this.bank_status = bank_status;
    }

    public String getEarning() {
        return earning;
    }

    public void setEarning(String earning) {
        this.earning = earning;
    }

    public int getIdentify_status() {
        return identify_status;
    }

    public void setIdentify_status(int identify_status) {
        this.identify_status = identify_status;
    }

    public double getToday_pending() {
        return today_pending;
    }

    public void setToday_pending(double today_pending) {
        this.today_pending = today_pending;
    }

    public double getToday_rent() {
        return today_rent;
    }

    public void setToday_rent(double today_rent) {
        this.today_rent = today_rent;
    }

    public double getMonth_pending() {
        return month_pending;
    }

    public void setMonth_pending(double month_pending) {
        this.month_pending = month_pending;
    }

    public double getMonth_rent() {
        return month_rent;
    }

    public void setMonth_rent(double month_rent) {
        this.month_rent = month_rent;
    }

    public boolean isBind_status() {
        return bind_status;
    }

    public void setBind_status(boolean bind_status) {
        this.bind_status = bind_status;
    }

    public String getAlipay_account() {
        return alipay_account;
    }

    public void setAlipay_account(String alipay_account) {
        this.alipay_account = alipay_account;
    }
}
