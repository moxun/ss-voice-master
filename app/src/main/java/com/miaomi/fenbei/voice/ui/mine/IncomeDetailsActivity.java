package com.miaomi.fenbei.voice.ui.mine;

import android.content.Context;
import android.content.Intent;
import com.miaomi.fenbei.base.bean.IncomeHistoryBean;
import com.miaomi.fenbei.base.core.BaseActivity;
import com.miaomi.fenbei.base.net.Callback;
import com.miaomi.fenbei.base.net.NetService;
import com.miaomi.fenbei.base.widget.xrecyclerview.XRecyclerView;
import com.miaomi.fenbei.voice.R;
import com.miaomi.fenbei.voice.ui.mine.balance.adapter.BalanceAdapter;

import org.jetbrains.annotations.NotNull;


import androidx.recyclerview.widget.LinearLayoutManager;


public class IncomeDetailsActivity extends BaseActivity {

    private BalanceAdapter mBalanceAdapter;
    private XRecyclerView rvIncomeInfo;

    public static void start(Context context){
        Intent intent = new Intent(context,IncomeDetailsActivity.class);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_income_detail;
    }

    @Override
    public void initView() {
        setBaseStatusBar(false,false);
        mBalanceAdapter = new BalanceAdapter(IncomeDetailsActivity.this);
        rvIncomeInfo = findViewById(R.id.rv_income_info);
        rvIncomeInfo.setPullRefreshEnabled(false);
        rvIncomeInfo.setAdapter(mBalanceAdapter);
//        rvIncomeInfo.setLoadingListener(new XRecyclerView.LoadingListener() {
//            @Override
//            public void onRefresh() {
//                getData();
//            }
//
//            @Override
//            public void onLoadMore() {
//
//            }
//        });
        rvIncomeInfo.setLayoutManager(new LinearLayoutManager(IncomeDetailsActivity.this));
        getData();
    }

    private void getData(){
        NetService.Companion.getInstance(IncomeDetailsActivity.this).getIncomeHistory(new Callback<IncomeHistoryBean>() {
            @Override
            public void onSuccess(int nextPage, IncomeHistoryBean bean, int code) {
                rvIncomeInfo.refreshComplete();
                mBalanceAdapter.setData(bean.getList());
            }

            @Override
            public void onError(@NotNull String msg, @NotNull Throwable throwable, int code) {
                rvIncomeInfo.refreshComplete();
            }

            @Override
            public boolean isAlive() {
                return isLive();
            }
        });
    }

}
