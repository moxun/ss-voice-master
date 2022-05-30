package com.miaomi.fenbei.voice.ui;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.miaomi.fenbei.base.bean.ExpressItemBean;
import com.miaomi.fenbei.base.core.BaseActivity;
import com.miaomi.fenbei.base.net.Callback;
import com.miaomi.fenbei.base.net.NetService;
import com.miaomi.fenbei.base.widget.xrecyclerview.XRecyclerView;
import com.miaomi.fenbei.voice.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;

public class ExpressRecordActivity extends BaseActivity {

    private ExpressRecordAdapter expressRecordAdapter;
    private XRecyclerView xRecyclerView;
    private int mPage = 1;

    public static void start(Context context){
        Intent intent = new Intent(context,ExpressRecordActivity.class);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_express_record;
    }

    @Override
    public void initView() {
        setBaseStatusBar(false,false);
        expressRecordAdapter = new ExpressRecordAdapter(this);
        xRecyclerView = findViewById(R.id.rv_express_record);
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
        expressRecordAdapter.setOnExpressItemOprateListener((item, heartMeView, heartNum) -> NetService.Companion.getInstance(ExpressRecordActivity.this).heartBBQ(String.valueOf(item.getId()),new Callback<List<String>>() {
            @Override
            public void onSuccess(int nextPage, List<String> list, int code) {
                heartMeView.setVisibility(View.VISIBLE);
                heartNum.setVisibility(View.VISIBLE);
                heartMeView.setContent(list);
                heartNum.setText("等"+(item.getHeart_num()+1)+"人送上祝福");
            }

            @Override
            public void onError(@NotNull String msg, @NotNull Throwable throwable, int code) {

            }

            @Override
            public boolean isAlive() {
                return isLive();
            }
        }));
        xRecyclerView.setAdapter(expressRecordAdapter);
//        TextView headView = new TextView(this);
//        headView.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DensityUtil.INSTANCE.dp2px(this,315f)));
//        xRecyclerView.addHeaderView(headView);
        getData(getTYPE_REFRESH());
    }

    private void getData(int type){
        if (type == getTYPE_REFRESH()){
            mPage = 1;
        }
        NetService.Companion.getInstance(this).getExpressRecord(mPage,new Callback<List<ExpressItemBean>>() {
            @Override
            public void onSuccess(int nextPage, List<ExpressItemBean> list, int code) {
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
