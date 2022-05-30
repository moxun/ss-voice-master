package com.miaomi.fenbei.base.bean;

public class SVGUrlBean {
    private String giftUrl;
    private String chestSvga;
    private int type;
    private GiftPoint endPosition;
    private GiftPoint startPosition;
    private GiftPoint midPosition;
    private String gifIcon;
    private int num;
    private String content = "";

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getChestSvga() {
        return chestSvga;
    }

    public void setChestSvga(String chestSvga) {
        this.chestSvga = chestSvga;
    }

    public GiftPoint getEndPosition() {
        return endPosition;
    }

    public void setEndPosition(GiftPoint endPosition) {
        this.endPosition = endPosition;
    }

    public GiftPoint getStartPosition() {
        return startPosition;
    }

    public void setStartPosition(GiftPoint startPosition) {
        this.startPosition = startPosition;
    }

    public GiftPoint getMidPosition() {
        return midPosition;
    }

    public void setMidPosition(GiftPoint midPosition) {
        this.midPosition = midPosition;
    }

    public SVGUrlBean(int type, String giftUrl,String gifIcon, GiftPoint endPosition, GiftPoint startPosition, GiftPoint midPosition,int num) {
        this.giftUrl = giftUrl;
        this.type = type;
        this.gifIcon = gifIcon;
        this.endPosition = endPosition;
        this.startPosition = startPosition;
        this.midPosition = midPosition;
        this.num = num;
    }

    public String getGifIcon() {
        return gifIcon;
    }

    public void setGifIcon(String gifIcon) {
        this.gifIcon = gifIcon;
    }

    public String getGiftUrl() {
        return giftUrl;
    }

    public void setGiftUrl(String giftUrl) {
        this.giftUrl = giftUrl;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

}
