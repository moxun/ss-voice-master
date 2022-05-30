package com.miaomi.fenbei.room.ui.dialog.zs;

import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.miaomi.fenbei.base.bean.ZSGiftPrizePoolBean;
import com.miaomi.fenbei.base.core.BaseFragment;
import com.miaomi.fenbei.base.net.Callback;
import com.miaomi.fenbei.base.net.NetService;
import com.miaomi.fenbei.base.util.DensityUtil;
import com.miaomi.fenbei.base.util.ToastUtil;
import com.miaomi.fenbei.base.widget.GridSpacingItemDecoration;
import com.miaomi.fenbei.room.R;
import com.opensource.svgaplayer.SVGAImageView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ZSGiftFragment  extends BaseFragment {

    private RecyclerView xRecyclerView;
    private ZSGiftAdapter mAdapter;
    private int dressType = 0;
    private SVGAImageView svgaImageView;

    public static ZSGiftFragment newInstance(int type) {
        ZSGiftFragment fragment = new ZSGiftFragment();
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
        dressType = getArguments().getInt("dressType",0);
        xRecyclerView = view.findViewById(R.id.rv_dress);
        svgaImageView = view.findViewById(R.id.iv_svga);
        xRecyclerView.setLayoutManager(new GridLayoutManager(getContext(),3));
        xRecyclerView.addItemDecoration(new GridSpacingItemDecoration(3, DensityUtil.INSTANCE.dp2px(getContext(),10f),false));
        mAdapter = new ZSGiftAdapter(dressType);
        xRecyclerView.setAdapter(mAdapter);
        getData();
    }

    protected void getData(){
        NetService.Companion.getInstance(getContext()).getZSGiftPrizePool(dressType,new Callback<List<ZSGiftPrizePoolBean>>() {
            @Override
            public void onSuccess(int nextPage, List<ZSGiftPrizePoolBean> list, int code) {
                mAdapter.setData(list);
            }

            @Override
            public void onError(@NotNull String msg, @NotNull Throwable throwable, int code) {
                ToastUtil.INSTANCE.suc(getContext(),msg);
            }

            @Override
            public boolean isAlive() {
                return true;
            }
        });
    }

}
