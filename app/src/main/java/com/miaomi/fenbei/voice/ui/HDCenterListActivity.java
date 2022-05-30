package com.miaomi.fenbei.voice.ui;

import android.content.Context;
import android.content.Intent;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.miaomi.fenbei.base.bean.HdCenterItemBean;
import com.miaomi.fenbei.base.core.BaseActivity;
import com.miaomi.fenbei.base.net.Callback;
import com.miaomi.fenbei.base.net.NetService;
import com.miaomi.fenbei.base.widget.xrecyclerview.XRecyclerView;
import com.miaomi.fenbei.voice.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class HDCenterListActivity extends BaseActivity {

    private HdCenterListAdapter expressRecordAdapter;
    private XRecyclerView xRecyclerView;
    private int mPage = 1;
    @Override
    public int getLayoutId() {
        return R.layout.activity_hd_center;
    }


    public static void start(Context context){
        Intent intent = new Intent(context,HDCenterListActivity.class);
        context.startActivity(intent);
    }



    @Override
    public void initView() {
        setBaseStatusBar(false,false);
        expressRecordAdapter = new HdCenterListAdapter(this);
        xRecyclerView = findViewById(R.id.rv_hd);
        xRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        xRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                getData(getTYPE_REFRESH());
            }

            @Override
            public void onLoadMore() {
                getData(getTYPE_LOADMROE());
            }
        });
        xRecyclerView.setAdapter(expressRecordAdapter);
        getData(getTYPE_REFRESH());
    }

    private void getData(int type){
        if (type == getTYPE_REFRESH()){
            mPage = 1;
        }
        NetService.Companion.getInstance(this).getActivityCenterList(new Callback<List<HdCenterItemBean>>() {
            @Override
            public void onSuccess(int nextPage, List<HdCenterItemBean> list, int code) {
                xRecyclerView.refreshComplete();
                if (type == getTYPE_REFRESH()){
                    expressRecordAdapter.setData(list);
                }else{
                    expressRecordAdapter.addData(list);
                }
                mPage = nextPage;
            }

            @Override
            public void onError(@NotNull String msg, @NotNull Throwable throwable, int code) {
                xRecyclerView.refreshComplete();
            }

            @Override
            public void noMore() {
                super.noMore();
                xRecyclerView.setNoMore(true);
            }

            @Override
            public boolean isAlive() {
                return isLive();
            }
        });
    }

}
