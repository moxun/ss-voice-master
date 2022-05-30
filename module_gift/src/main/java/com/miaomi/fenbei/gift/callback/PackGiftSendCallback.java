package com.miaomi.fenbei.gift.callback;

import com.miaomi.fenbei.base.bean.DiamondsBean;
import com.miaomi.fenbei.gift.GiftManager;
import com.miaomi.fenbei.base.bean.GiftBean;
import com.miaomi.fenbei.base.bean.GiftInfoBean;

import java.util.List;

public class PackGiftSendCallback extends GiftSendCallback<DiamondsBean> {


    public PackGiftSendCallback(GiftBean.DataBean selectedGift, int num, List<GiftInfoBean.ListBean> userList) {
        super(selectedGift, num, userList);
    }

    @Override
    void onSuccess(DiamondsBean bean) {
        sendGiftMsg(GiftManager.GIFT_TYPE_PACK,selectedGift,num,userList);
    }

}
