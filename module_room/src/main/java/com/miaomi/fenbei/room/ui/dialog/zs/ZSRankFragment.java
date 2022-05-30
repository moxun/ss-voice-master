package com.miaomi.fenbei.room.ui.dialog.zs;

import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.miaomi.fenbei.base.bean.ZSRankItemBean;
import com.miaomi.fenbei.base.core.BaseFragment;
import com.miaomi.fenbei.base.net.Callback;
import com.miaomi.fenbei.base.net.NetService;
import com.miaomi.fenbei.base.util.ToastUtil;
import com.miaomi.fenbei.room.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ZSRankFragment extends BaseFragment {

    private RecyclerView xRecyclerView;
    private ZSRankAdapter mAdapter;
    private int dressType = 1;

    public static ZSRankFragment newInstance(int type) {
        ZSRankFragment fragment = new ZSRankFragment();
        Bundle args = new Bundle();
        args.putInt("dressType", type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.room_fragment_zs_rank_child;
    }

    @Override
    public void initView(@NotNull View view) {
        dressType = getArguments().getInt("dressType",1);
        xRecyclerView = view.findViewById(R.id.rv_dress);
        xRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new ZSRankAdapter();
        xRecyclerView.setAdapter(mAdapter);
        getData();
    }

    protected void getData(){
        NetService.Companion.getInstance(getContext()).getZSRankList(dressType,new Callback<List<ZSRankItemBean>>() {
            @Override
            public void onSuccess(int nextPage, List<ZSRankItemBean> list, int code) {
                mAdapter.setData(list);
            }

            @Override
            public void onError(@NotNull String msg, @NotNull Throwable throwable, int code) {
                ToastUtil.INSTANCE.suc(getContext(),msg);
            }

            @Override
            public boolean isAlive() {
                return isLive();
            }
        });
    }


}
