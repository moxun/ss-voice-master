package com.miaomi.fenbei.gift.fragment;

import android.os.Bundle;
import android.view.View;

import com.miaomi.fenbei.base.net.Callback;
import com.miaomi.fenbei.base.net.NetService;
import com.miaomi.fenbei.gift.GiftManager;
import com.miaomi.fenbei.base.bean.GiftBean;
//import com.miaomi.fenbei.common.net.NetService;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class NobleGiftFragment extends BaseChildFragment {

    public static NobleGiftFragment newInstance(){
        NobleGiftFragment fragment = new NobleGiftFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    boolean isNeedgetData() {
        return true;
    }

    @Override
    void initData() {
        getData();
    }

    @Override
    int getType() {
        return GiftManager.GIFT_TYPE_NOBLE;
    }

    @Override
    int getSize() {
        return 0;
    }

    @Override
    boolean isShowEmpty() {
        return true;
    }


    private void getData(){

        NetService.Companion.getInstance(getMContext()).getAllNobleGift(GiftManager.getInstance().getRoomId(),new Callback<List<GiftBean.DataBean>>() {
            @Override
            public void onSuccess(int nextPage, List<GiftBean.DataBean> list, int code) {
                if (isAlive()){
                    if (list.size() >0){
                        mEmptyTextView.setVisibility(View.GONE);
                        mViewpager.setVisibility(View.VISIBLE);
                        assert getParentFragment() != null;
                        GiftManager.getInstance().saveGiftList(GiftManager.GIFT_TYPE_NOBLE,list);
                        int pageCount = GiftManager.getInstance().getPageCount(GiftManager.GIFT_TYPE_NOBLE);
                        getGirdViewList(GiftManager.GIFT_TYPE_NOBLE,pageCount);
                    }else{
                        mEmptyTextView.setVisibility(View.VISIBLE);
                        mViewpager.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onError(@NotNull String msg, @NotNull Throwable throwable, int code) {

            }

            @Override
            public boolean isAlive() {
                return isLive();
            }
        });
    }
}
