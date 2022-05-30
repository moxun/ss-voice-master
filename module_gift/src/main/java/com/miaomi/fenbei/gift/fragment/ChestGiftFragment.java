package com.miaomi.fenbei.gift.fragment;

import android.os.Bundle;

import com.miaomi.fenbei.gift.GiftManager;

public class ChestGiftFragment extends BaseChildFragment {
    public static ChestGiftFragment newInstance(){
        ChestGiftFragment fragment = new ChestGiftFragment();
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
        GiftManager.getInstance().resetGiftList(GiftManager.GIFT_TYPE_CHEST);
    }

    @Override
    int getType() {
        return GiftManager.GIFT_TYPE_CHEST;
    }

    @Override
    int getSize() {
        return GiftManager.getInstance().getPageCount(GiftManager.GIFT_TYPE_CHEST);
    }

    @Override
    boolean isShowEmpty() {
        return false;
    }
}

