package com.miaomi.fenbei.gift.callback;

import com.miaomi.fenbei.base.bean.MsgGiftBean;
import com.miaomi.fenbei.base.bean.UserInfo;
import com.miaomi.fenbei.base.net.Callback;
import com.miaomi.fenbei.base.bean.GiftBean;
import com.miaomi.fenbei.base.bean.GiftInfoBean;
import com.miaomi.fenbei.gift.listener.OnGiftListener;
import com.miaomi.fenbei.gift.listener.OnGiftSendCallback;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public abstract class GiftSendCallback<T> extends Callback<T> {
    protected OnGiftListener onGiftListener;
    protected GiftBean.DataBean selectedGift;
    protected int num;
    protected List<GiftInfoBean.ListBean> userList;

    public GiftSendCallback(GiftBean.DataBean selectedGift, int num, List<GiftInfoBean.ListBean> userList) {
        this.selectedGift = selectedGift;
        this.num = num;
        this.userList = userList;
    }

    public void setOnGiftListener(OnGiftListener onGiftListener) {
        this.onGiftListener = onGiftListener;
    }

    OnGiftSendCallback<T> onGiftSendCallback;
//    final BaseChildFragment fragment = giftPackFragment;



    public void setOnGiftSendCallback(OnGiftSendCallback<T> onGiftSendCallback) {
        this.onGiftSendCallback = onGiftSendCallback;
    }

    @Override
    public void onSuccess(int nextPage, T bean, int code) {
        onSuccess(bean);
        if (onGiftSendCallback != null){
            onGiftSendCallback.onSuccess(bean);
        }
    }

    @Override
    public void onError(@NotNull String msg, @NotNull Throwable throwable, int code) {
        if (1006 == code){
            return;
        }
        if (1004 == code){
            if (onGiftListener != null){
                onGiftListener.showPayDialog();
            }
            return;
        }
        if (onGiftListener != null){
            onGiftListener.onSendFail(msg);
        }
    }

    @Override
    public boolean isAlive() {
        return true;
    }

    protected void sendGiftMsg(int type, GiftBean.DataBean selectedGift, int num, List<GiftInfoBean.ListBean> userList){
        if (onGiftListener != null){
            for (final GiftInfoBean.ListBean userInfo: userList){
                UserInfo toUserInfo = new UserInfo();
                toUserInfo.setType(userInfo.getType());
                toUserInfo.setUser_id(userInfo.getUser_id());
                toUserInfo.setFace(userInfo.getFace());
                toUserInfo.setNickname(userInfo.getNickname());
                toUserInfo.setMystery(userInfo.getMystery());
                toUserInfo.setRank_id(userInfo.getRank_id());
                MsgGiftBean giftBean = new MsgGiftBean(selectedGift.getId(), num, selectedGift.getSvg_url(),
                        selectedGift.getIcon(),selectedGift.getPrice(),selectedGift.getName(),type,0,0,"","");
                onGiftListener.onSendSuccess(giftBean,toUserInfo);
            }
        }

    }

    protected void sendExpressGiftMsg(int type, GiftBean.DataBean selectedGift, int num, List<GiftInfoBean.ListBean> userList,String note){
        if (onGiftListener != null){
            for (final GiftInfoBean.ListBean userInfo: userList){
                UserInfo toUserInfo = new UserInfo();
                toUserInfo.setUser_id(userInfo.getUser_id());
                toUserInfo.setFace(userInfo.getFace());
                toUserInfo.setNickname(userInfo.getNickname());
                toUserInfo.setMystery(userInfo.getMystery());
                toUserInfo.setRank_id(userInfo.getRank_id());
                MsgGiftBean giftBean = new MsgGiftBean(selectedGift.getId(), num, selectedGift.getSvg_url(),
                        selectedGift.getIcon(),selectedGift.getPrice(),selectedGift.getName(),type,0,0,"","");
                onGiftListener.onSendExpressGiftSuccess(giftBean,toUserInfo,note);
            }
        }

    }

    abstract void onSuccess(T bean);
}
