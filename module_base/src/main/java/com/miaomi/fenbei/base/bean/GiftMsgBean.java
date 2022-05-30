package com.miaomi.fenbei.base.bean;

public class GiftMsgBean {
    private String avter = "";
    private String nickname = "";
    private String giftname  = "";
    private String giftUrl  = "";
    private String giftIcon  = "";
    // 0 未中奖 1小奖 2大奖
    private int luck_reward_type = 0;
    private int luck_reward_count = 0;
    private int giftNum;
    private int userId;
    private int toUserId;
    private GiftPoint startPoint;
    private GiftPoint endPoint;
    private GiftPoint midPoint;
    private String toUserName  = "";
    private String chestIcon;
    private String chestName;

    public String getChestIcon() {
        return chestIcon;
    }

    public void setChestIcon(String chestIcon) {
        this.chestIcon = chestIcon;
    }

    public String getChestName() {
        return chestName;
    }

    public void setChestName(String chestName) {
        this.chestName = chestName;
    }

    public GiftPoint getMidPoint() {
        return midPoint;
    }

    public void setMidPoint(GiftPoint midPoint) {
        this.midPoint = midPoint;
    }

    public int getLuck_reward_count() {
        return luck_reward_count;
    }

    public void setLuck_reward_count(int luck_reward_count) {
        this.luck_reward_count = luck_reward_count;
    }

    public GiftMsgBean() {
    }

    public int getLuck_reward_type() {
        return luck_reward_type;
    }

    public void setLuck_reward_type(int luck_reward_type) {
        this.luck_reward_type = luck_reward_type;
    }

    //    public GiftMsgBean(String avter, String nickname, String giftname, String giftUrl, String giftIcon, int giftNum, int userId, int toUserId, int giftId) {
//        this.avter = avter;
//        this.nickname = nickname;
//        this.giftname = giftname;
//        this.giftUrl = giftUrl;
//        this.giftIcon = giftIcon;
//        this.giftNum = giftNum;
//        this.userId = userId;
//        this.toUserId = toUserId;
//        this.giftId = giftId;
//    }


    public GiftPoint getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(GiftPoint startPoint) {
        this.startPoint = startPoint;
    }

    public String getToUserName() {
        return toUserName;
    }

    public void setToUserName(String toUserName) {
        this.toUserName = toUserName;
    }

    public int getToUserId() {
        return toUserId;
    }

    public void setToUserId(int toUserId) {
        this.toUserId = toUserId;
    }

//    public View getStartView() {
//        return startView;
//    }
//
//    public void setStartView(View startView) {
//        this.startView = startView;
//    }


    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    private int giftId =1;

    public GiftPoint getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(GiftPoint endPoint) {
        this.endPoint = endPoint;
    }

    //    private int theUserId =1;
//    private long latestRefreshTime;
//    private long giftStayTime = 3000;
//    private int currentIndex;

    /**
     * 单次礼物数目
     */
//    private int theGiftSendSize = 1;
//    private int theGiftCount;

//    public int getTheGiftCount() {
//        return theGiftCount;
//    }
//
//    public void setTheGiftCount(int theGiftCount) {
//        this.theGiftCount = theGiftCount;
//    }

//    public int getTheGiftSendSize() {
//        return theGiftSendSize;
//    }
//
//    public void setTheGiftSendSize(int theGiftSendSize) {
//        this.theGiftSendSize = theGiftSendSize;
//    }

    public int getGiftId() {
        return giftId;
    }

    public void setGiftId(int giftId) {
        this.giftId = giftId;
    }

//    public long getLatestRefreshTime() {
//        return latestRefreshTime;
//    }
//
//    public void setLatestRefreshTime(long latestRefreshTime) {
//        this.latestRefreshTime = latestRefreshTime;
//    }

//    public long getGiftStayTime() {
//        return giftStayTime;
//    }
//
//    public void setGiftStayTime(long giftStayTime) {
//        this.giftStayTime = giftStayTime;
//    }

//    public int getCurrentIndex() {
//        return currentIndex;
//    }
//
//    public void setCurrentIndex(int currentIndex) {
//        this.currentIndex = currentIndex;
//    }

    //    public int getTheUserId() {
//        return theUserId;
//    }
//
//    public void setTheUserId(int theUserId) {
//        this.theUserId = theUserId;
//    }

    public String getGiftIcon() {
        return giftIcon;
    }

    public void setGiftIcon(String giftIcon) {
        this.giftIcon = giftIcon;
    }

    public int getGiftNum() {
        return giftNum;
    }

    public void setGiftNum(int giftNum) {
        this.giftNum = giftNum;
    }

    public String getAvter() {
        return avter;
    }

    public void setAvter(String avter) {
        this.avter = avter;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getGiftname() {
        return giftname;
    }

    public void setGiftname(String giftname) {
        this.giftname = giftname;
    }

    public String getGiftUrl() {
        return giftUrl;
    }

    public void setGiftUrl(String giftUrl) {
        this.giftUrl = giftUrl;
    }

//    @Override
//    public int compareTo(@NonNull GiftMsgBean o) {
//        return (int) (this.getLatestRefreshTime()-o.getLatestRefreshTime());
//    }
//
//    @Override
//    public Object clone() throws CloneNotSupportedException {
//        return super.clone();
//    }
}
