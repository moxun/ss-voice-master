package com.miaomi.fenbei.base.bean;


public class MineBean extends LocalUserBean{


    private String earning = "";
    private int young_pwd = 0;
    private int visit_count = 0;

    public int getVisit_count() {
        return visit_count;
    }

    public void setVisit_count(int visit_count) {
        this.visit_count = visit_count;
    }

//    private String version = "1";
//    private boolean system_message = false;
//    private boolean legend_identity;
//    private String custom_service_qq = "";
//    private String family_entry_qq = "";
//    private String download_url = "";
//    private int legend;
//    private String anchor_url;

    public String getFans_count() {
        return fans_count;
    }

    public void setFans_count(String fans_count) {
        this.fans_count = fans_count;
    }

    public String getFollower_count() {
        return follower_count;
    }

    public void setFollower_count(String follower_count) {
        this.follower_count = follower_count;
    }

    private String fans_count;
    private String follower_count;

//    public String getAnchor_url() {
//        return anchor_url;
//    }
//
//    public void setAnchor_url(String anchor_url) {
//        this.anchor_url = anchor_url;
//    }
//
//    public boolean isLegend_identity() {
//        return legend_identity;
//    }
//
//    public void setLegend_identity(boolean legend_identity) {
//        this.legend_identity = legend_identity;
//    }
//
//    public String getDownload_url() {
//        return download_url;
//    }
//
//    public void setDownload_url(String download_url) {
//        this.download_url = download_url;
//    }
//
//    public String getCustom_service_qq() {
//        return custom_service_qq;
//    }
//
//    public void setCustom_service_qq(String custom_service_qq) {
//        this.custom_service_qq = custom_service_qq;
//    }
//
//
//    public String getFamily_entry_qq() {
//        return family_entry_qq;
//    }
//
//    public void setFamily_entry_qq(String family_entry_qq) {
//        this.family_entry_qq = family_entry_qq;
//    }

//    public boolean isSystem_message() {
//        return system_message;
//    }
//
//    public void setSystem_message(boolean system_message) {
//        this.system_message = system_message;
//    }



//    public int getIdentify_status() {
//        return identify_status;
//    }
//
//    public void setIdentify_status(int is_identify) {
//        this.identify_status = is_identify;
//    }

//    public String getVersion() {
//        return version;
//    }
//
//    public void setVersion(String version) {
//        this.version = version;
//    }

    public String getEarning() {
        return earning;
    }

    public void setEarning(String earning) {
        this.earning = earning;
    }

    public int getYoung_pwd() {
        return young_pwd;
    }

    public void setYoung_pwd(int young_pwd) {
        this.young_pwd = young_pwd;
    }

//    public int getLegend() {
//        return legend;
//    }
//
//    public void setLegend(int legend) {
//        this.legend = legend;
//    }
}
