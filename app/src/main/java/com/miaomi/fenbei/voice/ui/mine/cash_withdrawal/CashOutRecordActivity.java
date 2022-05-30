package com.miaomi.fenbei.voice.ui.mine.cash_withdrawal;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.miaomi.fenbei.base.bean.CashOutRecordBean;
import com.miaomi.fenbei.base.core.BaseActivity;
import com.miaomi.fenbei.base.net.Callback;
import com.miaomi.fenbei.base.net.Data;
import com.miaomi.fenbei.base.net.NetService;
import com.miaomi.fenbei.base.util.DataHelper;
import com.miaomi.fenbei.base.util.LoadHelper;
import com.miaomi.fenbei.base.widget.xrecyclerview.XRecyclerView;
import com.miaomi.fenbei.voice.R;
import com.miaomi.fenbei.voice.ui.mine.cash_withdrawal.adapter.RecordAdapter;

import org.jetbrains.annotations.NotNull;

import androidx.recyclerview.widget.LinearLayoutManager;

public class CashOutRecordActivity extends BaseActivity implements XRecyclerView.LoadingListener{


    private XRecyclerView rvRecord;
    private RecordAdapter mRecordAdapter;
    private LoadHelper loadHelper;
    private int page = 1;

    public static Intent getIntent(Context context) {
        return new Intent(context, CashOutRecordActivity.class);
    }
    @Override
    public int getLayoutId() {
        return R.layout.user_activity_record;
    }

    @Override
    public void initView() {
        setBaseStatusBar(false,false);
        loadHelper = new LoadHelper();
        rvRecord = findViewById(R.id.rv_record);
        loadHelper.registerLoad(rvRecord);
        mRecordAdapter = new RecordAdapter();
        rvRecord.setAdapter(mRecordAdapter);
        rvRecord.setLayoutManager(new LinearLayoutManager(CashOutRecordActivity.this));
        rvRecord.setLoadingMoreEnabled(false);
        rvRecord.setPullRefreshEnabled(true);
        rvRecord.setLoadingListener(this);
        getData(getTYPE_REFRESH(),1);
    }

    private void initData(CashOutRecordBean bean, int type){
        if (bean.getList().size() == 0 || bean.getList() == null){
            rvRecord.setVisibility(View.GONE);
        }else {
            rvRecord.setVisibility(View.VISIBLE);
            if (type == getTYPE_REFRESH()) {
                mRecordAdapter.setData(bean.getList());
                rvRecord.refreshComplete();
            } else {
                mRecordAdapter.addData(bean.getList());
                rvRecord.loadMoreComplete();
            }

        }
    }

    private void getData(final int type, final int mPage){
        NetService.Companion.getInstance(this).getCashOutRecord(DataHelper.INSTANCE.getLoginToken(),mPage, new Callback<CashOutRecordBean>() {
            @Override
            public void onSuccess(int nextPage, CashOutRecordBean bean, int code) {
                if (bean.getList().size() > 0){
                    loadHelper.bindView(code);
                    page = nextPage;
                    if (page == 1) {
                        rvRecord.setLoadingMoreEnabled(false);
                    } else {
                        rvRecord.setLoadingMoreEnabled(true);
                    }
                    initData(bean,type);
                }else{
                    loadHelper.setEmpty2Callback("","暂无提现记录~",0);
                }
            }

            @Override
            public void onError(@NotNull String msg, @NotNull Throwable throwable, int code) {
                loadHelper.setErrorCallback(code, v -> {
                    loadHelper.bindView(Data.CODE_LOADING);
                    onRefresh();
                });
            }

            @Override
            public boolean isAlive() {
                return isLive();
            }
        });
    }

    @Override
    public void onRefresh() {
        getData(getTYPE_REFRESH(),1);
    }

    @Override
    public void onLoadMore() {
        getData(getTYPE_LOADMROE(),page);
    }
}
