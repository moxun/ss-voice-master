package com.miaomi.fenbei.gift.callback;


import com.miaomi.fenbei.base.bean.MsgGiftBean;
import com.miaomi.fenbei.base.bean.UserInfo;
import com.miaomi.fenbei.gift.GiftManager;
import com.miaomi.fenbei.base.bean.ChestGiftBean;
import com.miaomi.fenbei.base.bean.GiftBean;
import com.miaomi.fenbei.base.bean.GiftInfoBean;

import java.util.List;

public class ChestGiftSendCallback extends GiftSendCallback<ChestGiftBean> {


    public ChestGiftSendCallback(GiftBean.DataBean selectedGift, int num, List<GiftInfoBean.ListBean> userList) {
        super(selectedGift, num, userList);
    }

    @Override
    void onSuccess(ChestGiftBean bean) {
        //刷新钻石余额
        sendGiftMsg( selectedGift,bean);
    }
    protected void sendGiftMsg(GiftBean.DataBean selectedGift,ChestGiftBean bean){
        if (onGiftListener != null){
            for (final ChestGiftBean.RewardBean rewardBean: bean.getReward()){
                UserInfo toUserInfo = new UserInfo();
                toUserInfo.setUser_id(rewardBean.getUser_id());
                toUserInfo.setFace(rewardBean.getFace());
                toUserInfo.setNickname(rewardBean.getNickname());
                MsgGiftBean giftBean = new MsgGiftBean(rewardBean.getId(), rewardBean.getGift_num(), rewardBean.getSvg_url(),
                        rewardBean.getIcon(),rewardBean.getPrice(),rewardBean.getName()
                        ,GiftManager.GIFT_TYPE_CHEST,0,0
                        ,selectedGift.getSvg_url(),selectedGift.getName());
                onGiftListener.onSendChest(giftBean,toUserInfo);
            }
        }

    }
}
