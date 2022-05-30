package com.miaomi.fenbei.gift.fragment;

import android.os.Bundle;
import android.view.View;

import com.miaomi.fenbei.base.net.Callback;
import com.miaomi.fenbei.gift.GiftManager;
import com.miaomi.fenbei.base.bean.MyPackBean;
import com.miaomi.fenbei.base.net.NetService;

import org.jetbrains.annotations.NotNull;


public class PackGiftFragment extends BaseChildFragment {

    public static PackGiftFragment newInstance(){
        PackGiftFragment fragment = new PackGiftFragment();
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
        if (getParentFragment() != null){
            ((GiftFragment)getParentFragment()).setPackFragment(this);
        }
        getData();
    }

    @Override
    int getType() {
        return GiftManager.GIFT_TYPE_PACK;
    }

    @Override
    int getSize() {
        return 0;
    }

    @Override
    boolean isShowEmpty() {
        return false;
    }


    private void getData(){

        NetService.Companion.getInstance(getMContext()).getAllPack(new Callback<MyPackBean>() {
            @Override
            public void onSuccess(int nextPage, MyPackBean bean, int code) {
                if (isAlive()){
                    if (bean.getData().size() >0){
                        mEmptyTextView.setVisibility(View.GONE);
                        mViewpager.setVisibility(View.VISIBLE);
                        assert getParentFragment() != null;
                        ((GiftFragment)getParentFragment()).setPackTotal(bean.getTotal());
                        GiftManager.getInstance().saveGiftList(GiftManager.GIFT_TYPE_PACK,bean.getData());
                        int pageCount = GiftManager.getInstance().getPageCount(GiftManager.GIFT_TYPE_PACK);
                        getGirdViewList(GiftManager.GIFT_TYPE_PACK,pageCount);
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
