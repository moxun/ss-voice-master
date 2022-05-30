package com.miaomi.fenbei.base.bean.event;


import com.miaomi.fenbei.base.bean.C2CCustomBean;
import com.tencent.imsdk.TIMElem;
import com.tencent.imsdk.TIMMessage;

public class C2CMsgBean {
    //消息类型
    public final static int Empty = 0;
    public final static int Text = 1;
    public final static int Image = 2;
    public final static int Sound = 3;
    public final static int Emoji = 4;
    public final static int Custom = 5;

    public final static int CustomGift = 11;
    public final static int CustomReply = 12;
    public final static int CustomHello = 13;
    public final static int CustomRedEnvelope = 14;

    public final static String CUSTOM_CHAT_GIFT=  "CHAT_GIFT";
    public final static String CUSTOM_CHAT_REPLY=  "CHAT_REPLY";
    public final static String CUSTOM_CHAT_HELLO=  "CHAT_HELLO";
    public final static String CUSTOM_CHAT_RED_ENVELOPE=  "RED_ENVELOPE";
    //语音状态
    public final static int SOUND_STATUS_READ = 1;
    public final static int SOUND_STATUS_UNREAD = 0;
    //发送状态
    public final static int SEND_STATUS_SUCCESS = 0;
    public final static int SEND_STATUS_ING = 1;
    public final static int SEND_STATUS_FAILE = -1;

    //公共参数
    private TIMMessage timMessage;
    private TIMElem element;
    private String sender;
    private String face;
    private int msgType;
    private long time;
    private boolean isRead;
    private long send_status;
    private long tag;
    public long uniqueId;
    private int status;

    //文本消息参数
    private String txtContent;

    //图片消息参数
    private String thumbnailImg = "";
    private String originalImg = "";
    private int width;
    private int height;

    //语音消息参数
    private long duration;
    private int is_sound;
    private String soundPath;

    //Emoji消息参数
    private String emojiUrl;

    //自定义消息参数
    private C2CCustomBean customBean;

    public C2CCustomBean getCustomBean() {
        return customBean;
    }

    public void setCustomBean(C2CCustomBean customBean) {
        this.customBean = customBean;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getSoundPath() {
        return soundPath;
    }

    public void setSoundPath(String soundPath) {
        this.soundPath = soundPath;
    }

    public String getThumbnailImg() {
        return thumbnailImg;
    }

    public void setThumbnailImg(String thumbnailImg) {
        this.thumbnailImg = thumbnailImg;
    }

    public String getEmojiUrl() {
        return emojiUrl;
    }

    public void setEmojiUrl(String emojiUrl) {
        this.emojiUrl = emojiUrl;
    }

    public String getTxtContent() {
        return txtContent;
    }

    public void setTxtContent(String txtContent) {
        this.txtContent = txtContent;
    }

    public TIMElem getElement() {
        return element;
    }

    public void setElement(TIMElem element) {
        this.element = element;
    }

    public int getCustomInt() {
        if (getTimMessage() == null){
            return SOUND_STATUS_UNREAD;
        }
        return getTimMessage().getCustomInt();
    }

    public void setCustomInt(int customInt) {
        if (getTimMessage() != null){
            getTimMessage().setCustomInt(customInt);
        }
    }

    public long getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(long uniqueId) {
        this.uniqueId = uniqueId;
    }

    public TIMMessage getTimMessage() {
        return timMessage;
    }

    public void setTimMessage(TIMMessage timMessage) {
        this.timMessage = timMessage;
    }

    public long getTag() {
        return tag;
    }

    public void setTag(long tag) {
        this.tag = tag;
    }

    public int getIs_sound() {
        return is_sound;
    }

    public void setIs_sound(int is_sound) {
        this.is_sound = is_sound;
    }

    public long getSend_status() {
        return send_status;
    }

    public void setSend_status(long send_status) {
        this.send_status = send_status;
    }

    public boolean getIsRead() {
        return isRead;
    }

    public void setIsRead(boolean isRead) {
        this.isRead = isRead;
    }

//    public TIMSoundElem getSoundElem() {
//        return soundElem;
//    }
//
//    public void setSoundElem(TIMSoundElem soundElem) {
//        this.soundElem = soundElem;
//    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getOriginalImg() {
        return originalImg;
    }

    public void setOriginalImg(String originalImg) {
        this.originalImg = originalImg;
    }

    public String getFace() {
        return face;
    }

    public void setFace(String face) {
        this.face = face;
    }

    public String getSender() {
        return sender.substring(sender.indexOf("_") + 1);
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public int getMsgType() {
        return msgType;
    }

    public void setMsgType(int msgType) {
        this.msgType = msgType;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }


}
