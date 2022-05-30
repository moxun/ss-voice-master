package com.miaomi.fenbei.base.bean;

import com.miaomi.fenbei.base.bean.event.C2CMsgBean;

public class C2CCustomBean {
    private String msgType;
    private String content;
    private String price;
    private String giftUrl;

    private String preTextMsg = "";

    public String getPreTextMsg() {
        return preTextMsg;
    }

    public void setPreTextMsg(String preTextMsg) {
        this.preTextMsg = preTextMsg;
    }


    public String getMsgType() {
        return msgType;
    }

    public int getRealMsgType() {
        switch (getMsgType()) {
            case C2CMsgBean.CUSTOM_CHAT_GIFT:
                return C2CMsgBean.CustomGift;
            case C2CMsgBean.CUSTOM_CHAT_REPLY:
                return C2CMsgBean.CustomReply;
            case C2CMsgBean.CUSTOM_CHAT_HELLO:
                return C2CMsgBean.CustomHello;
            case C2CMsgBean.CUSTOM_CHAT_RED_ENVELOPE:
                return C2CMsgBean.CustomRedEnvelope;
            default:
                return C2CMsgBean.Custom;
        }
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getGiftUrl() {
        return giftUrl;
    }

    public void setGiftUrl(String giftUrl) {
        this.giftUrl = giftUrl;
    }
}
