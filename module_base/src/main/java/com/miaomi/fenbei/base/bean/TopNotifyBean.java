package com.miaomi.fenbei.base.bean;


/**
 * Created by
 * on 2019-10-30.
 */
public class TopNotifyBean {
    //全服礼物
    public final static int TNB_GIFT_TOP_QF = 0;
    //全服许愿
    public final static int TNB_GIFT_TOP_XY = 1;
    //开通爵位
    public final static int TNB_GIFT_TOP_OPEN_NOBLE = 2;
    //藏宝活动
    public final static int TNB_GIFT_TOP_TREASURES = 3;
    //空投许愿池
    public final static int TNB_GIFT_TOP_CJ_XY = 4;
    //种树幸运时间
    public final static int TNB_GIFT_TOP_ZS_LUCK = 5;
    //种树
    public final static int TNB_GIFT_TOP_ZX_LW = 6;
    //购买铁杆粉
    public final static int BUY_TIE_FAN = 7;
    //购买真爱粉
    public final static int BUY_AI_FAN = 8;
    private String content;
    private int bg;
    private String roomId;


    private String fromUserAvatar = "";
    private String toUserAvatar = "";
    private String giftIcon = "";
    private String fromUserName = "";
    private String toUserName = "";

    public String getFromUserName() {
        return fromUserName;
    }

    public void setFromUserName(String fromUserName) {
        this.fromUserName = fromUserName;
    }

    public String getToUserName() {
        return toUserName;
    }

    public void setToUserName(String toUserName) {
        this.toUserName = toUserName;
    }

    public String getFromUserAvatar() {
        return fromUserAvatar;
    }

    public void setFromUserAvatar(String fromUserAvatar) {
        this.fromUserAvatar = fromUserAvatar;
    }

    public String getToUserAvatar() {
        return toUserAvatar;
    }

    public void setToUserAvatar(String toUserAvatar) {
        this.toUserAvatar = toUserAvatar;
    }


    public String getGiftIcon() {
        return giftIcon;
    }

    public void setGiftIcon(String giftIcon) {
        this.giftIcon = giftIcon;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public TopNotifyBean() {
    }

    public TopNotifyBean(String content, int bg) {
        this.content = content;
        this.bg = bg;
    }
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getBg() {
        return bg;
    }

    public void setBg(int bg) {
        this.bg = bg;
    }
}
