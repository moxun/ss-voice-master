package com.miaomi.fenbei.gift.callback;

import com.miaomi.fenbei.base.bean.DiamondsBean;
import com.miaomi.fenbei.base.bean.GiftBean;
import com.miaomi.fenbei.base.bean.GiftInfoBean;
import com.miaomi.fenbei.gift.GiftManager;

import java.util.List;

public class ExpressGiftSendCallback  extends GiftSendCallback<DiamondsBean>{

    private String note;

    public ExpressGiftSendCallback(GiftBean.DataBean selectedGift, int num, List<GiftInfoBean.ListBean> userList,String note) {
        super(selectedGift, num, userList);
        this.note = note;
    }

    @Override
    void onSuccess(DiamondsBean bean) {
        //刷新钻石余额
        sendExpressGiftMsg(GiftManager.GIFT_TYPE_EXPRESS,selectedGift,num,userList,note);
    }
}
