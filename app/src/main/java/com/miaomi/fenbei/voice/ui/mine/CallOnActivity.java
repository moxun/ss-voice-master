package com.miaomi.fenbei.voice.ui.mine;

import android.content.Context;
import android.content.Intent;

import com.miaomi.fenbei.base.bean.CallOnBean;
import com.miaomi.fenbei.base.core.BaseActivity;
import com.miaomi.fenbei.base.net.Callback;
import com.miaomi.fenbei.base.net.NetService;
import com.miaomi.fenbei.base.widget.xrecyclerview.XRecyclerView;
import com.miaomi.fenbei.voice.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;

public class CallOnActivity extends BaseActivity implements XRecyclerView.LoadingListener{
    private XRecyclerView callOnUserRv;
    private CallOnUserAdapter adapter;
    private int page = 1;
    @Override
    public int getLayoutId() {
        return R.layout.activity_call_on;
    }

    public static void start(Context context){
        Intent intent = new Intent(context,CallOnActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void initView() {
        setBaseStatusBar(false, false);
        adapter = new CallOnUserAdapter(this);
        callOnUserRv = findViewById(R.id.rv_user);
        callOnUserRv.setLayoutManager(new LinearLayoutManager(this));
        callOnUserRv.setPullRefreshEnabled(true);
        callOnUserRv.setLoadingListener(this);
        callOnUserRv.setLoadingMoreEnabled(true);
        callOnUserRv.setAdapter(adapter);
        getData(getTYPE_REFRESH());
    }

    @Override
    public void onRefresh() {
        getData(getTYPE_REFRESH());

    }

    @Override
    public void onLoadMore() {
        getData(getTYPE_LOADMROE());
    }
    private void getData(int type){
        if (type == getTYPE_REFRESH()){
            page = 1;
        }
        NetService.Companion.getInstance(this).getVisitRecord(page, new Callback<List<CallOnBean>>() {
            @Override
            public void onSuccess(int nextPage, List<CallOnBean> list, int code) {

                if (type == getTYPE_REFRESH()){
                    callOnUserRv.refreshComplete();
                    adapter.setData(list);
                }else{
                    callOnUserRv.loadMoreComplete();
                    adapter.addData(list);
                }
                page ++;
            }

            @Override
            public void onError(@NotNull String msg, @NotNull Throwable throwable, int code) {
                callOnUserRv.refreshComplete();
            }

            @Override
            public void noMore() {
                super.noMore();
                callOnUserRv.setNoMore(true);
            }

            @Override
            public boolean isAlive() {
                return isLive();
            }
        });
    }
}
