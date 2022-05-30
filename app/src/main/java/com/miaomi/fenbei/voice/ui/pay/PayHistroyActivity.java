package com.miaomi.fenbei.voice.ui.pay;

import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.miaomi.fenbei.base.bean.PayHistroyBean;
import com.miaomi.fenbei.base.core.BaseActivity;
import com.miaomi.fenbei.base.net.Callback;
import com.miaomi.fenbei.base.net.NetService;
import com.miaomi.fenbei.base.util.LoadHelper;
import com.miaomi.fenbei.base.widget.xrecyclerview.XRecyclerView;
import com.miaomi.fenbei.voice.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;

@Route(path = "/app/payhistroy")
public class PayHistroyActivity extends BaseActivity implements XRecyclerView.LoadingListener {
    XRecyclerView mPayHistroyXRecyclerView;
    PayHistroyAdapter mPayHistroyAdapter;
    LoadHelper loadHelper;
    private static final int TYPE_REFRESH = 0;
    private static final int TYPE_LOADMORE = 1;
    private List<PayHistroyBean> mPayHistroyBeanList = new ArrayList<>();
    private int page;

    @Override
    public int getLayoutId() {
        return R.layout.pay_activity_histroy;
    }

    @Override
    public void initView() {
        setBaseStatusBar(false, false);
        loadHelper = new LoadHelper();
        mPayHistroyAdapter = new PayHistroyAdapter(mPayHistroyBeanList, this);
        mPayHistroyXRecyclerView = findViewById(R.id.rv_pay_histroy);
        mPayHistroyXRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mPayHistroyXRecyclerView.setLoadingListener(this);
        mPayHistroyXRecyclerView.setLoadingMoreEnabled(true);
        mPayHistroyXRecyclerView.setAdapter(mPayHistroyAdapter);
        loadHelper.registerLoad(mPayHistroyXRecyclerView);
        getData(TYPE_REFRESH);
    }

    private void getData(final int type) {
        if (type == TYPE_REFRESH) {
            mPayHistroyXRecyclerView.setLoadingMoreEnabled(true);
            page = 1;
        }
        NetService.Companion.getInstance(this).payHistory(page, new Callback<List<PayHistroyBean>>() {
            @Override
            public void onSuccess(int nextPage, List<PayHistroyBean> bean, int code) {
                page = page + 1;
                loadHelper.bindView(code);
                mPayHistroyXRecyclerView.refreshComplete();
                if (type == TYPE_REFRESH) {
                    mPayHistroyBeanList.clear();
                    mPayHistroyBeanList.addAll(bean);
                } else {
                    mPayHistroyBeanList.addAll(bean);
                }
                mPayHistroyAdapter.notifyDataSetChanged();
            }

            @Override
            public void noMore() {
                mPayHistroyXRecyclerView.setLoadingMoreEnabled(false);
            }

            @Override
            public void onError(@NotNull String msg, @NotNull Throwable throwable, int code) {
                mPayHistroyXRecyclerView.refreshComplete();
                loadHelper.setErrorCallback(code, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getData(TYPE_REFRESH);
                    }
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
        getData(TYPE_REFRESH);
    }

    @Override
    public void onLoadMore() {
        getData(TYPE_LOADMORE);
    }
}
