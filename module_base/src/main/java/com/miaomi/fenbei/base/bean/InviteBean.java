package com.miaomi.fenbei.base.bean;

import java.util.List;

public class InviteBean {


    /**
     * chatId : 123467
     * opt : XIANGQING_INVITE
     * zhu_tongzhi_list : ["100271"]
     * ci_tongzhi_list : []
     */

    private String chatId;
    private String opt;
    private List<String> zhu_tongzhi_list;
    private List<String> ci_tongzhi_list;

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public String getOpt() {
        return opt;
    }

    public void setOpt(String opt) {
        this.opt = opt;
    }

    public List<String> getZhu_tongzhi_list() {
        return zhu_tongzhi_list;
    }

    public void setZhu_tongzhi_list(List<String> zhu_tongzhi_list) {
        this.zhu_tongzhi_list = zhu_tongzhi_list;
    }

    public List<String> getCi_tongzhi_list() {
        return ci_tongzhi_list;
    }

    public void setCi_tongzhi_list(List<String> ci_tongzhi_list) {
        this.ci_tongzhi_list = ci_tongzhi_list;
    }
}
