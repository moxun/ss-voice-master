package com.miaomi.fenbei.gift;

import android.content.Context;
import android.net.http.HttpResponseCache;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;

import com.miaomi.fenbei.base.bean.GiftBean;
import com.miaomi.fenbei.base.bean.GiftInfoBean;
import com.miaomi.fenbei.base.bean.GiftListInfo;
import com.miaomi.fenbei.base.bean.GiftPoint;
import com.miaomi.fenbei.base.bean.MsgGiftBean;
import com.miaomi.fenbei.base.bean.NobleOnlineBean;
import com.miaomi.fenbei.base.bean.SVGUrlBean;
import com.miaomi.fenbei.base.bean.TopNotifyBean;
import com.miaomi.fenbei.base.bean.UserInfo;
import com.miaomi.fenbei.base.bean.XqResultBean;
import com.miaomi.fenbei.base.core.CommonLib;
import com.miaomi.fenbei.base.net.Callback;
import com.miaomi.fenbei.base.net.NetService;
import com.miaomi.fenbei.base.util.DataHelper;
import com.miaomi.fenbei.base.util.DisplayUtil;
import com.miaomi.fenbei.gift.fragment.GiftFragment;
import com.miaomi.fenbei.gift.listener.OnGiftListener;
import com.miaomi.fenbei.gift.widget.GiftAnimView;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GiftManager {


    public static final int GIFT_TYPE_COMMON = 0;
    //私聊礼物列表
    public static final int GIFT_TYPE_COMMON_PRIVATE = 11;
    public static final int GIFT_TYPE_PACK = 1;
//    public static final int GIFT_TYPE_LUCK = 2;
    public static final int GIFT_TYPE_GUARD= 3;
    public static final int GIFT_TYPE_CHEST = 4;
    public static final int GIFT_TYPE_NOBLE= 7;
    public static final int GIFT_TYPE_EXPRESS= 10;

    private final static int PAGE_ITEM_NUMBER = 8;

    public final static int GIFT_SEND_BT_POSITION = -1;
    public final static int GIFT_POSITION_NO = -2;
    private static GiftManager instance;
//    private GiftPoint commonGiftStartPoint = new GiftPoint();
    private SparseArray<GiftPoint> mGiftPointList = new SparseArray<>();
//    private List<GiftBean.DataBean> mBackpackGiftList = new ArrayList<>();
//    private List<GiftBean.DataBean> mAllGiftList = new ArrayList<>();
    private GiftListInfo mGiftList = new GiftListInfo();
    private GiftListInfo mLuckGiftList = new GiftListInfo();
    private GiftListInfo mPackList = new GiftListInfo();

    private GiftListInfo mPresssList = new GiftListInfo();
    private GiftListInfo mGuardList = new GiftListInfo();
    private GiftListInfo mNobleList = new GiftListInfo();
    private GiftListInfo mChestList = new GiftListInfo();
    private GiftPoint chestStartPoint = new GiftPoint();


    private GiftManager(){
        chestStartPoint.setX(DisplayUtil.INSTANCE.getRealWidth(CommonLib.mContext)/2);
        chestStartPoint.setY(DisplayUtil.INSTANCE.getRealHeight(CommonLib.mContext)/2);
    }

    public static GiftManager getInstance(){
        if (instance == null) {
            synchronized (GiftManager.class) {
                if (instance == null) {
                    instance = new GiftManager();
                }
            }
        }
        return instance;
    }



    public GiftFragment buildGiftFragment(OnGiftListener onGiftListener){
        GiftFragment fragment = new GiftFragment();
        fragment.setOnGiftListener(onGiftListener);
        return fragment;
    }

    public void addDefalutPoint(View view){
        int[] hostLocation =new int[2];
        view.getLocationOnScreen(hostLocation);
        int endX = hostLocation[0] + view.getWidth();
        int endY = hostLocation[1] + view.getHeight() ;
        GiftPoint giftPoint = new GiftPoint();
        giftPoint.setX(endX);
        giftPoint.setY(endY);
        mGiftPointList.put(GIFT_SEND_BT_POSITION,giftPoint);
    }

    public void addHostPoint(View view){

        int[] hostLocation =new int[2];
        view.getLocationOnScreen(hostLocation);
        int endX = hostLocation[0] + view.getWidth() / 2 - 30;
        int endY = hostLocation[1] + view.getHeight() / 2 - 30;
        GiftPoint giftPoint = new GiftPoint();
        giftPoint.setX(endX);
        giftPoint.setY(endY);
        mGiftPointList.put(0,giftPoint);
    }
    public void addMicPoint(int positon,int x, int y){
        GiftPoint giftPoint = new GiftPoint();
        giftPoint.setX(x);
        giftPoint.setY(y);
        mGiftPointList.put(positon,giftPoint);
    }



    public void setGiftAnimView(GiftAnimView view){
        if (view != null){
            mGiftAnimView = view ;
        }
    }

//    public void addRewardAnim(String str){
//        if (mGiftAnimView == null){
//            return;
//        }
//        mGiftAnimView.addRewardAnim(str);
//    }


    public void clearAnim(){
        if (mGiftAnimView == null){
            return;
        }
        mGiftAnimView.clear();
    }



//    public void addLuckGiftAnim(String avter, String nickname, String giftname, String toUsername
//            , String giftIcon, int giftNum, int userId, int toUserId, int giftId, int rewardType, int reward){
//        if (!isOpenAnim()){
//            return;
//        }
//        if (mGiftAnimView == null){
//            return;
//        }
//        GiftMsgBean bean = new GiftMsgBean();
//        bean.setAvter(avter);
//        bean.setNickname(nickname);
//        bean.setGiftname(giftname);
//        bean.setGiftNum(giftNum);
//        bean.setUserId(userId);
//        bean.setToUserId(toUserId);
//        bean.setGiftId( giftId);
//        bean.setGiftIcon(giftIcon);
//        bean.setToUserName(toUsername);
//        bean.setLuck_reward_type(rewardType);
//        bean.setLuck_reward_count(reward);
//        mGiftAnimView.addLuckGiftAnim(bean);
//    }

//    public void addDTGiftAnim(String avter, String nickname, String giftname, String toUsername
//            , String giftIcon, int giftNum, int userId, int toUserId, int giftId, int rewardType, int reward,String giftUrl){
//        if (!isOpenAnim()){
//            return;
//        }
//        if (mGiftAnimView == null){
//            return;
//        }
//        GiftMsgBean bean = new GiftMsgBean();
//        bean.setAvter(avter);
//        bean.setNickname(nickname);
//        bean.setGiftname(giftname);
//        bean.setGiftNum(giftNum);
//        bean.setUserId(userId);
//        bean.setToUserId(toUserId);
//        bean.setGiftId( giftId);
//        bean.setGiftIcon(giftIcon);
//        bean.setToUserName(toUsername);
//        bean.setLuck_reward_type(rewardType);
//        bean.setLuck_reward_count(reward);
//        mGiftAnimView.addLuckGiftAnim(bean);
//        //带特效动画
//        if (giftUrl.endsWith(".svga")){
//            SVGUrlBean svgUrlBean = new SVGUrlBean(GIFT_TYPE_COMMON,giftUrl,giftIcon,null,null,null,0);
//            mGiftAnimView.addSVGAAnim(svgUrlBean);
//        }
//    }

    public void addTopNotifyGiftAnim(TopNotifyBean msg){
        if (!isOpenFullService()){
            return;
        }
        if (mGiftAnimView == null){
            return;
        }
        mGiftAnimView.addTopNotifyGiftAnim(msg);
    }

    public void addExpressTopNotifyAnim(TopNotifyBean msg){
        if (!isOpenFullService()){
            return;
        }
        if (mGiftAnimView == null){
            return;
        }
        mGiftAnimView.addExpressTopNotifyAnim(msg);
    }

    public void addEnterRoom(UserInfo userInfo){
        if (!isOpenFullService()){
            return;
        }
        if (mGiftAnimView == null || userInfo == null){
            return;
        }
        mGiftAnimView.addEnterRoom(userInfo);
    }

    public void addNobleOnline(NobleOnlineBean bean){
        if (!isOpenFullService()){
            return;
        }
        if (mGiftAnimView == null){
            return;
        }
        mGiftAnimView.addNobleOnline(bean);
    }





    public void addAnim(String content,int num,String giftUrl, String giftIcon ,int startPosition, int endPosition){
        if (!isOpenAnim()){
            return;
        }
        if (mGiftAnimView == null){
            return;
        }
        //带特效动画
        if (giftUrl.endsWith(".svga")){
            SVGUrlBean svgUrlBean = new SVGUrlBean(GIFT_TYPE_COMMON,giftUrl,giftIcon,null,null,null,0);
            svgUrlBean.setContent(content);
            mGiftAnimView.addSVGAAnim(svgUrlBean);
        }
        mGiftAnimView.showCommonGiftAnim(num,giftUrl,giftIcon,mGiftPointList.get(startPosition), mGiftPointList.get(endPosition));
    }
    public void xQresult(int resultType,XqResultBean xqResultBean){
        if (mGiftAnimView == null){
            return;
        }

        mGiftAnimView.XqResult(resultType,xqResultBean);
    }
    public void addSvgaAnim(String giftUrl, String content){
        if (!isOpenAnim()){
            return;
        }
        SVGUrlBean svgUrlBean = new SVGUrlBean(GIFT_TYPE_COMMON,giftUrl,"",null,null,null,0);
        svgUrlBean.setContent(content);
        mGiftAnimView.addSVGAAnim(svgUrlBean);
    }

    public void addChestGiftAnim(MsgGiftBean bean, int endPosition){
        if (!isOpenAnim()){
            return;
        }
        if (mGiftAnimView == null){
            return;
        }
        SVGUrlBean svgUrlBean;
        if (endPosition == GIFT_POSITION_NO){
            svgUrlBean = new SVGUrlBean(GIFT_TYPE_CHEST,bean.getGiftChestUrl(),bean.getGiftIcon(),new GiftPoint(),chestStartPoint,mGiftPointList.get(0),bean.getGiftNum());
        }else{
            svgUrlBean = new SVGUrlBean(GIFT_TYPE_CHEST,bean.getGiftChestUrl(),bean.getGiftIcon(),mGiftPointList.get(endPosition),chestStartPoint,mGiftPointList.get(0),bean.getGiftNum());
//        svgUrlBean.setChestSvga(bean.getGiftChestUrl());

        }
        mGiftAnimView.addSVGAAnim(svgUrlBean);
        if (bean.getGiftUrl().endsWith(".svga")){
            SVGUrlBean svgUrlBean1 = new SVGUrlBean(GIFT_TYPE_COMMON,bean.getGiftUrl(),bean.getGiftIcon(),null,null,null,0);
            mGiftAnimView.addSVGAAnim(svgUrlBean1);
        }
//        mGiftAnimView.showChestGiftAnim(bean,chestStartPoint,mGiftPointList.get(0), mGiftPointList.get(endPosition));

    }



    public void initGiftList(Context context){
        File cacheDir = new File(context.getCacheDir() + File.separator, "http");
        try {
            HttpResponseCache.install(cacheDir, 1024 * 1024 * 128);
        } catch (IOException e) {
            e.printStackTrace();
        }
        initGiftList(context,null);
    }

    private void initGiftList(Context context, final OnGiftInitCallback onGiftInitCallback){
        NetService.Companion.getInstance(context).getAllGiftList(new Callback<List<GiftBean.DataBean>>() {
            @Override
            public void onSuccess(int nextPage, List<GiftBean.DataBean> list, int code) {
                if (onGiftInitCallback != null){
                    onGiftInitCallback.success();
                }
//                mAllGiftList.clear();
                List<GiftBean.DataBean> commonGifts = new ArrayList<>();
                List<GiftBean.DataBean> expresssList = new ArrayList<>();
                List<GiftBean.DataBean> chestGifts = new ArrayList<>();
                for (GiftBean.DataBean item : list){
//                    mAllGiftList.add(item);
                    if (item.getCategory() == GIFT_TYPE_EXPRESS){
                        expresssList.add(item);
                        continue;
                    }
                    if (item.getCategory() == GIFT_TYPE_CHEST){
                        chestGifts.add(item);
                        continue;
                    }
                    if (item.getCategory() == GIFT_TYPE_COMMON){
                        commonGifts.add(item);
                    }
                }
                saveGiftList(GIFT_TYPE_EXPRESS,expresssList);
                saveGiftList(GIFT_TYPE_CHEST,chestGifts);
                saveGiftList(GIFT_TYPE_COMMON,commonGifts);
//                saveGiftList(GIFT_TYPE_LUCK,luckGifts);
            }

            @Override
            public void onError(@NotNull String msg, @NotNull Throwable throwable, int code) {

            }

            @Override
            public boolean isAlive() {
                return true;
            }
        });
//        NetService.Companion.getInstance(context).getBackpackGiftList( new Callback<List<GiftBean.DataBean>>() {
//            @Override
//            public void onSuccess(int nextPage, List<GiftBean.DataBean> list, int code) {
//                saveBackpackGiftList(list);
//            }
//
//            @Override
//            public void onError(@NotNull String msg, @NotNull Throwable throwable, int code) {
//
//            }
//
//            @Override
//            public boolean isAlive() {
//                return true;
//            }
//        });

    }

    private GiftListInfo getAssignGiftList(int type){
        if (type == GIFT_TYPE_COMMON){
            return mGiftList;
        }
        if (type == GIFT_TYPE_EXPRESS){
            return mPresssList;
        }
        if (type == GIFT_TYPE_PACK){
            return mPackList;
        }
        if (type == GIFT_TYPE_GUARD){
            return mGuardList;
        }
        if (type == GIFT_TYPE_CHEST){
            return mChestList;
        }
        if (type == GIFT_TYPE_NOBLE){
            return mNobleList;
        }
        return mGiftList;
    }

    public int getPageCount(int type){
        int size = getAssignGiftList(type).getList().size();
        if (size % PAGE_ITEM_NUMBER == 0){
            return size/PAGE_ITEM_NUMBER;
        }else{
            return size/PAGE_ITEM_NUMBER+1;
        }
    }

    public List<GiftBean.DataBean> getGiftPageList(int type,int page){
        List<GiftBean.DataBean> list = getAssignGiftList(type).getList();
        if (page < getPageCount(type)-1){
            return list.subList(page*PAGE_ITEM_NUMBER,page*PAGE_ITEM_NUMBER+PAGE_ITEM_NUMBER);
        }else{
            return list.subList(page*PAGE_ITEM_NUMBER,list.size());
        }
    }


    public void saveGiftList(int type,List<GiftBean.DataBean> list){
        getAssignGiftList(type).saveGiftList(list);
    }

//    public int getGiftPrice(int id) {
//        int price = 0;
//        for (GiftBean.DataBean bean : mAllGiftList) {
//            if (id == bean.getId()) {
//                price = bean.getPrice();
//            }
//        }
//        if (price == 0) {
//            for (GiftBean.DataBean bean : mBackpackGiftList) {
//                if (id == bean.getId()) {
//                    price = bean.getPrice();
//                }
//            }
//        }
//        return price;
//    }



    public void resetGiftList(int type){
        getAssignGiftList(type).resetGiftList();
    }

//    private void saveBackpackGiftList(List<GiftBean.DataBean> list){
//        mBackpackGiftList.clear();
//        mBackpackGiftList.addAll(list);
//    }


    public void setSelectedGift(int type, GiftBean.DataBean bean){
        getAssignGiftList(type).setSelectedGift(bean);
    }


    public GiftBean.DataBean getSelectedGift(int type){
        return getAssignGiftList(type).getSelectedGift();
    }

    public interface OnGiftInitCallback{
        void success();
    }

    public void isInitGift(Context context,OnGiftInitCallback onGiftInitCallback){
        if (mGiftList.getList().size() > 0){
            onGiftInitCallback.success();
        }else{
            initGiftList(context,onGiftInitCallback);
        }
    }


    //---------------------------------礼物部分所需数据-------------------------
    private int roomType = 0;

    private String roomId;
    //选中用户信息
    private int userId;
    private String face;
    private String nickName;
    private int mystery;
    private boolean needSeleted;
    //动画
    private GiftAnimView mGiftAnimView;
    private boolean isOpenAnim;
    private List<GiftInfoBean.ListBean> mMicUserlist = new ArrayList<>();

    //金币余额
    private int packTotal;
    private int accountBalance;
    private int accountLuckCoin;



    public int getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(int accountBalance) {
        this.accountBalance = accountBalance;
    }

    public void saveMicUserList(List<GiftInfoBean.ListBean> list){
        mMicUserlist.clear();
        mMicUserlist.addAll(list);
    }

    public List<GiftInfoBean.ListBean> getMicUserList(){
        return mMicUserlist;
    }

    public int getRoomType() {
        return roomType;
    }

    public void setRoomType(int roomType) {
        this.roomType = roomType;
    }

    public boolean isNeedSeleted() {
        return needSeleted;
    }

    public void setNeedSeleted(boolean needSeleted) {
        this.needSeleted = needSeleted;
    }

    public int getPackTotal() {
        return packTotal;
    }

    public void setPackTotal(int packTotal) {
        this.packTotal = packTotal;
    }

    public int getAccountLuckCoin() {
        return accountLuckCoin;
    }

    public void setAccountLuckCoin(int accountLuckCoin) {
        this.accountLuckCoin = accountLuckCoin;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getMystery() {
        return mystery;
    }

    public void setMystery(int mystery) {
        this.mystery = mystery;
    }

    public String getFace() {
        return face;
    }

    public void setFace(String face) {
        this.face = face;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }


    public boolean isOpenAnim() {
        return DataHelper.INSTANCE.isGiftAnimOpen();
    }

    public boolean isOpenFullService() {
        return DataHelper.INSTANCE.isFullChatOpen();
    }

    public void changeFullServiceStatus(){
        if (isOpenFullService()){
            DataHelper.INSTANCE.saveOpenFullChat(false);
        }else{
            DataHelper.INSTANCE.saveOpenFullChat(true);
        }
    }

    private void closeAnim() {
        DataHelper.INSTANCE.saveGiftAnimOpen(false);
    }

    public void changeAnimstatus(){
        if (isOpenAnim()){
            closeAnim();
        }else{
            openAnim();
        }
    }

    private void openAnim() {
        DataHelper.INSTANCE.saveGiftAnimOpen(true);
    }


}
