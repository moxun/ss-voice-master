package com.miaomi.fenbei.voice.ui.family;

import android.os.Bundle;
import android.view.View;


import com.miaomi.fenbei.base.bean.FamilyRankBean;
import com.miaomi.fenbei.base.core.BaseFragment;
import com.miaomi.fenbei.base.net.Callback;
import com.miaomi.fenbei.base.net.NetService;
import com.miaomi.fenbei.base.util.LoadHelper;
import com.miaomi.fenbei.base.widget.xrecyclerview.XRecyclerView;
import com.miaomi.fenbei.voice.R;

import org.jetbrains.annotations.NotNull;


import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;

public class FamilyRankFragment extends BaseFragment {
    XRecyclerView mFamilyRank;
    FamilyRankAdapter adapter;
    public final static String RANK_TYPE = "rank_type";
    public final static String FAMILY_ID = "FAMILY_ID";
    private int rankType;
    private String familyId;
    private LoadHelper loadHelper;

    public static FamilyRankFragment newInstance(int type,String familyId){
        FamilyRankFragment fragment = new FamilyRankFragment();
        Bundle args = new Bundle();
        args.putInt(RANK_TYPE,type);
        args.putString(FAMILY_ID,familyId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_family_rank;
    }

    @Override
    public void initView(@NotNull View view) {
        rankType = getArguments().getInt(RANK_TYPE);
        familyId = getArguments().getString(FAMILY_ID);
        adapter = new FamilyRankAdapter(getMContext());
        mFamilyRank = view.findViewById(R.id.rv_family_rank);
        mFamilyRank.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                getData();
            }

            @Override
            public void onLoadMore() {

            }
        });
        mFamilyRank.setLoadingMoreEnabled(false);
        mFamilyRank.setLayoutManager(new LinearLayoutManager(getMContext()));
        mFamilyRank.setAdapter(adapter);
        loadHelper = new LoadHelper();
        loadHelper.registerLoad(mFamilyRank);
        getData();
    }
    private void getData(){
        NetService.Companion.getInstance(getMContext()).getFamilyRankList(rankType, familyId, new Callback<List<FamilyRankBean>>() {
            @Override
            public void onSuccess(int nextPage, List<FamilyRankBean> bean, int code) {
                mFamilyRank.refreshComplete();
                loadHelper.bindView(code);
                adapter.setData(bean);
            }

            @Override
            public void onError(@NotNull String msg, @NotNull Throwable throwable, int code) {
                mFamilyRank.refreshComplete();
                loadHelper.setErrorCallback(code, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getData();
                    }
                });
            }

            @Override
            public boolean isAlive() {
                return isLive();
            }
        });
    }
}
