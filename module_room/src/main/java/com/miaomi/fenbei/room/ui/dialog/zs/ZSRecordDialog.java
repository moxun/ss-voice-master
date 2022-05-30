package com.miaomi.fenbei.room.ui.dialog.zs;

import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.miaomi.fenbei.base.bean.ZSPrizeRecordBean;
import com.miaomi.fenbei.base.core.dialog.BaseBottomDialog;
import com.miaomi.fenbei.base.net.Callback;
import com.miaomi.fenbei.base.net.NetService;
import com.miaomi.fenbei.base.widget.xrecyclerview.XRecyclerView;
import com.miaomi.fenbei.room.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ZSRecordDialog extends BaseBottomDialog {
    private ZSRecordGiftAdapter zsRecordGiftAdapter;
    private XRecyclerView dataRv;
    private int mPage = 1;
    @Override
    public int getLayoutRes() {
        return R.layout.room_dialog_zs_record;
    }

    @Override
    public void bindView(View v) {
        zsRecordGiftAdapter = new ZSRecordGiftAdapter();
        dataRv = v.findViewById(R.id.rv_data);
        dataRv.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                getData(0);
            }

            @Override
            public void onLoadMore() {
                getData(1);
            }
        });
        dataRv.setLayoutManager(new LinearLayoutManager(getContext()));
        dataRv.setAdapter(zsRecordGiftAdapter);
        v.findViewById(R.id.iv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        getData(0);
    }
    private void getData(final int type){
        if (type == 0){
            mPage = 1;
        }
        NetService.Companion.getInstance(getContext()).getZSRecordList(mPage,new Callback<ArrayList<ZSPrizeRecordBean>>() {
            @Override
            public void onSuccess(int nextPage, ArrayList<ZSPrizeRecordBean> bean, int code) {
                dataRv.refreshComplete();
                if (type == 0){
                    zsRecordGiftAdapter.setData(bean);
                }else{
                    zsRecordGiftAdapter.addData(bean);
                }
                mPage = mPage + 1;
            }

            @Override
            public void noMore() {
                super.noMore();
                dataRv.setNoMore(true);
            }

            @Override
            public void onError(@NotNull String msg, @NotNull Throwable throwable, int code) {
                dataRv.refreshComplete();
            }

            @Override
            public boolean isAlive() {
                return isLive;
            }
        });
    }
}