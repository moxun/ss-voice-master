package com.miaomi.fenbei.gift.fragment;

import android.os.Bundle;

import com.miaomi.fenbei.gift.GiftManager;

public class ExpressGiftFragment extends BaseChildFragment {
    public static ExpressGiftFragment newInstance(){
        ExpressGiftFragment fragment = new ExpressGiftFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    boolean isNeedgetData() {
        return false;
    }

    @Override
    void initData() {
        GiftManager.getInstance().resetGiftList(GiftManager.GIFT_TYPE_EXPRESS);
    }

    @Override
    int getType() {
        return GiftManager.GIFT_TYPE_EXPRESS;
    }

    @Override
    int getSize() {
        return GiftManager.getInstance().getPageCount(GiftManager.GIFT_TYPE_EXPRESS);
    }

    @Override
    boolean isShowEmpty() {
        return false;
    }
}
