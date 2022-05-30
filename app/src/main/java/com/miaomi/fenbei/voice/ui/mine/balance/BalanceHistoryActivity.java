package com.miaomi.fenbei.voice.ui.mine.balance;

import android.content.Context;
import android.content.Intent;

import com.miaomi.fenbei.base.bean.BalanceHistoryBean;
import com.miaomi.fenbei.base.core.BaseActivity;
import com.miaomi.fenbei.base.net.Callback;
import com.miaomi.fenbei.base.net.Data;
import com.miaomi.fenbei.base.net.NetService;
import com.miaomi.fenbei.base.util.LoadHelper;
import com.miaomi.fenbei.base.widget.xrecyclerview.XRecyclerView;
import com.miaomi.fenbei.voice.R;
import com.miaomi.fenbei.voice.ui.mine.balance.adapter.BalanceHistoryAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;

public class BalanceHistoryActivity extends BaseActivity implements XRecyclerView.LoadingListener{

    XRecyclerView mPayHistroyXRecyclerView;
    BalanceHistoryAdapter mPayHistroyAdapter;
    LoadHelper loadHelper;
    private List<BalanceHistoryBean> mPayHistroyBeanList = new ArrayList<>();

    public static Intent getIntent(Context context) {
        return new Intent(context, BalanceHistoryActivity.class);
    }

    @Override
    public int getLayoutId() {
        return R.layout.user_activity_balance_history;
    }

    @Override
    public void initView() {
        setBaseStatusBar(false,false);
        loadHelper = new LoadHelper();
        mPayHistroyAdapter = new BalanceHistoryAdapter(mPayHistroyBeanList,this);
        mPayHistroyXRecyclerView = findViewById(R.id.rv_pay_histroy);
        mPayHistroyXRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mPayHistroyXRecyclerView.setLoadingMoreEnabled(true);
        mPayHistroyXRecyclerView.setPullRefreshEnabled(true);
        mPayHistroyXRecyclerView.setLoadingListener(this);
        mPayHistroyXRecyclerView.setAdapter(mPayHistroyAdapter);
        loadHelper.registerLoad(mPayHistroyXRecyclerView);
        getData();
    }
    private void getData(){
        NetService.Companion.getInstance(this).getBalanceHistory(new Callback<List<BalanceHistoryBean>>() {
            @Override
            public void onSuccess(int nextPage, List<BalanceHistoryBean> bean, int code) {
                loadHelper.bindView(code);
                mPayHistroyBeanList.clear();
                mPayHistroyBeanList.addAll(bean);
                mPayHistroyAdapter.notifyDataSetChanged();
                mPayHistroyXRecyclerView.refreshComplete();
            }

            @Override
            public void onError(@NotNull String msg, @NotNull Throwable throwable, int code) {
                loadHelper.bindView(Data.CODE_ERROR);
                mPayHistroyXRecyclerView.refreshComplete();
            }

            @Override
            public void noMore() {
                super.noMore();
                mPayHistroyXRecyclerView.setNoMore(true);
            }

            @Override
            public boolean isAlive() {
                return isLive();
            }
        });
    }

    @Override
    public void onRefresh() {
        getData();
    }

    @Override
    public void onLoadMore() {

    }
}
