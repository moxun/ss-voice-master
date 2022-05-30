package com.miaomi.fenbei.gift.fragment;

import android.os.Bundle;

import com.miaomi.fenbei.gift.GiftManager;

public class CommonPrivateGiftFragment extends BaseChildFragment {
    public static CommonPrivateGiftFragment newInstance(){
        CommonPrivateGiftFragment fragment = new CommonPrivateGiftFragment();
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
        GiftManager.getInstance().resetGiftList(GiftManager.GIFT_TYPE_COMMON_PRIVATE);
    }

    @Override
    int getType() {
        return GiftManager.GIFT_TYPE_COMMON_PRIVATE;
    }

    @Override
    int getSize() {
        return GiftManager.getInstance().getPageCount(GiftManager.GIFT_TYPE_COMMON_PRIVATE);
    }

    @Override
    boolean isShowEmpty() {
        return false;
    }
}
