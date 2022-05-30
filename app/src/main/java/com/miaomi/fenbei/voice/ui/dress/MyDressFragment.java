package com.miaomi.fenbei.voice.ui.dress;

import android.os.Bundle;
import android.view.View;

import com.miaomi.fenbei.base.bean.DressItemBean;
import com.miaomi.fenbei.base.config.BaseConfig;
import com.miaomi.fenbei.base.core.BaseLazyFragment;
import com.miaomi.fenbei.base.net.Callback;
import com.miaomi.fenbei.base.net.NetService;
import com.miaomi.fenbei.base.util.DensityUtil;
import com.miaomi.fenbei.base.util.ToastUtil;
import com.miaomi.fenbei.base.widget.GridSpacingItemDecoration;
import com.miaomi.fenbei.voice.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MyDressFragment extends BaseLazyFragment {

    private RecyclerView xRecyclerView;
    private int dressType = BaseConfig.DRESS_TYPE_ZWK;

    private OnDressItemClickListener onDressItemClickListener;

    public void setOnDressItemClickListener(OnDressItemClickListener onDressItemClickListener) {
        this.onDressItemClickListener = onDressItemClickListener;
    }

    public static MyDressFragment newInstance(int type) {
        MyDressFragment fragment = new MyDressFragment();
        Bundle args = new Bundle();
        args.putInt("dressType", type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_dress_seat;
    }

    @Override
    public void initView(@NotNull View view) {
        dressType = getArguments().getInt("dressType", BaseConfig.DRESS_TYPE_ZWK);
        xRecyclerView = view.findViewById(R.id.rv_dress);
        xRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

    }


    private void getData() {
        NetService.Companion.getInstance(getContext()).getMyDress(dressType, new Callback<List<DressItemBean>>() {
            @Override
            public void onSuccess(int nextPage, List<DressItemBean> list, int code) {
                if (dressType == BaseConfig.DRESS_TYPE_ZWK) {
                    DressSeatAdapter adapter = new DressSeatAdapter(getContext());
                    adapter.setOnDressItemClickListener(onDressItemClickListener);
                    xRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
                    xRecyclerView.addItemDecoration(new GridSpacingItemDecoration(3, DensityUtil.INSTANCE.dp2px(getContext(), 10f), false));
                    xRecyclerView.setAdapter(adapter);
                    adapter.setData(list);
                }
                if (dressType == BaseConfig.DRESS_TYPE_JCTX) {
                    DressJCTXAdapter adapter = new DressJCTXAdapter(getContext());
                    adapter.setOnDressItemClickListener(onDressItemClickListener);
                    xRecyclerView.setAdapter(adapter);
                    xRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    adapter.setData(list);
                }
                if (dressType == BaseConfig.DRESS_TYPE_ZJ) {
                    DressZJAdapter adapter = new DressZJAdapter(getContext());
                    adapter.setOnDressItemClickListener(onDressItemClickListener);
                    xRecyclerView.setAdapter(adapter);
                    xRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
                    xRecyclerView.addItemDecoration(new GridSpacingItemDecoration(2, DensityUtil.INSTANCE.dp2px(getContext(), 10f), false));
                    adapter.setData(list);
                }
                if (dressType == BaseConfig.DRESS_TYPE_XZ){
                    DressXzAdapter adapter = new DressXzAdapter(getContext());
                    adapter.setOnDressItemClickListener(onDressItemClickListener);
                    xRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
                    xRecyclerView.addItemDecoration(new GridSpacingItemDecoration(3, DensityUtil.INSTANCE.dp2px(getContext(), 10f), false));
                    xRecyclerView.setAdapter(adapter);
                    adapter.setData(list);
                }


//                xRecyclerView.refreshComplete();
            }

            @Override
            public void onError(@NotNull String msg, @NotNull Throwable throwable, int code) {
//                xRecyclerView.refreshComplete();
                ToastUtil.INSTANCE.suc(getContext(), msg);
            }

            @Override
            public boolean isAlive() {
                return isLive();
            }
        });
    }

    @Override
    public void loadData() {
        getData();
    }
}
