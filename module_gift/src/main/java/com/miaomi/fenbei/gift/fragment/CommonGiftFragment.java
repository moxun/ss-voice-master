package com.miaomi.fenbei.gift.fragment;

import android.os.Bundle;

import com.miaomi.fenbei.gift.GiftManager;


public class CommonGiftFragment extends BaseChildFragment {
    public static CommonGiftFragment newInstance(){
        CommonGiftFragment fragment = new CommonGiftFragment();
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
        GiftManager.getInstance().resetGiftList(GiftManager.GIFT_TYPE_COMMON);
    }

    @Override
    int getType() {
        return GiftManager.GIFT_TYPE_COMMON;
    }

    @Override
    int getSize() {
        return GiftManager.getInstance().getPageCount(GiftManager.GIFT_TYPE_COMMON);
    }

    @Override
    boolean isShowEmpty() {
        return false;
    }
}
