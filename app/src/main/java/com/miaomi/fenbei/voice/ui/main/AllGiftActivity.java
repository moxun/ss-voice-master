package com.miaomi.fenbei.voice.ui.main;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.miaomi.fenbei.base.bean.GiftWallBean;
import com.miaomi.fenbei.base.config.BaseConfig;
import com.miaomi.fenbei.base.core.BaseActivity;
import com.miaomi.fenbei.base.net.NetService;
import com.miaomi.fenbei.base.util.StatusBarUtil;
import com.miaomi.fenbei.base.util.ToastUtil;
import com.miaomi.fenbei.base.web.WebActivity;
import com.miaomi.fenbei.base.widget.GridSpacingItemDecoration;
import com.miaomi.fenbei.voice.R;
import com.miaomi.fenbei.voice.ui.mine.user_homepage.adapter.AllGiftWallAdapter;
import com.miaomi.fenbei.base.net.Callback;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.scwang.smartrefresh.layout.util.DensityUtil;

import org.jetbrains.annotations.NotNull;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class AllGiftActivity extends BaseActivity {

    private RecyclerView rvGift;
    private AllGiftWallAdapter giftAdapter;
    private int mGiftWallPage = 1;
    private String userId = "";
    private SmartRefreshLayout smartRefreshLayout;
    public final static int TYPE_GIFT_SEND = 0;
    public final static int TYPE_GIFT_GET = 1;
    private int giftType = TYPE_GIFT_GET;
    private TextView hasGiftTv;
    private TextView startCountTv;

    @Override
    public int getLayoutId() {
        return R.layout.activity_all_gift;
    }

    public static void start(@NonNull Context context,String userId) {
        Intent intent = new Intent(context, AllGiftActivity.class);
        intent.putExtra("user_id",userId);
//        intent.putExtra("type",type);
        context.startActivity(intent);
    }

    @Override
    public void initView() {
        StatusBarUtil.setStatusBarTextColor(this,true);
        userId = getIntent().getStringExtra("user_id");
        rvGift =  findViewById(R.id.rv_gift);
        smartRefreshLayout = findViewById(R.id.refresh_layout);
        hasGiftTv=findViewById(R.id.tv_has_gift);
        startCountTv=findViewById(R.id.tv_start_count);
        giftAdapter = new AllGiftWallAdapter(AllGiftActivity.this);
        smartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                getGiftWall(getTYPE_LOADMROE());
            }
        });
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                getGiftWall(getTYPE_REFRESH());
            }
        });
        findViewById(R.id.tv_star_explain).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebActivity.start(AllGiftActivity.this, BaseConfig.URL_STAR_EXPALIN,"集星说明");
            }
        });
//        rvGift.setPullRefreshEnabled(true);
//        rvGift.setLoadingMoreEnabled(true);
//        rvGift.setLoadingListener(new XRecyclerView.LoadingListener() {
//            @Override
//            public void onRefresh() {
//                getGiftWall(getTYPE_REFRESH());
//            }
//
//            @Override
//            public void onLoadMore() {
//                getGiftWall(getTYPE_LOADMROE());
//            }
//        });
        rvGift.setLayoutManager(new GridLayoutManager(this,3));
        rvGift.addItemDecoration(new GridSpacingItemDecoration(3, DensityUtil.dp2px(15f),false));
        rvGift.setAdapter(giftAdapter);
        getGiftWall(getTYPE_REFRESH());
    }

    private void getGiftWall(int type){
        if (type == getTYPE_REFRESH()){
            mGiftWallPage = 1;
        }
        if (giftType == TYPE_GIFT_GET){
            NetService.Companion.getInstance(this).getGiftWall(mGiftWallPage,userId, new Callback<GiftWallBean>() {
                @Override
                public void onSuccess(int nextPage, GiftWallBean list, int code) {
                    if (type == getTYPE_REFRESH()){
                        smartRefreshLayout.finishRefresh();
                        showView(list.getCount());
                        giftAdapter.setData(list.getList());
                    }else{
                        smartRefreshLayout.finishLoadMore();
                        giftAdapter.addData(list.getList());
                    }
                    mGiftWallPage++;

                }

                @Override
                public void noMore() {
                    super.noMore();
                    smartRefreshLayout.finishLoadMoreWithNoMoreData();
                }

                @Override
                public void onError(@NotNull String msg, @NotNull Throwable throwable, int code) {
                    ToastUtil.INSTANCE.suc(AllGiftActivity.this,msg);
                }

                @Override
                public boolean isAlive() {
                    return isLive();
                }
            });
        }else{
//            NetService.Companion.getInstance(this).getSendGiftWall(mGiftWallPage,userId, new Callback<List<GiftWallBean>>() {
//                @Override
//                public void onSuccess(int nextPage, List<GiftWallBean> list, int code) {
//                    if (type == getTYPE_REFRESH()){
//                        smartRefreshLayout.finishRefresh();
//                        giftAdapter.setData(list);
//                    }else{
//                        smartRefreshLayout.finishLoadMore();
//                        giftAdapter.addData(list);
//                    }
//                    mGiftWallPage++;
//
//                }
//
//                @Override
//                public void noMore() {
//                    super.noMore();
//                    smartRefreshLayout.finishLoadMoreWithNoMoreData();
//                }
//
//                @Override
//                public void onError(@NotNull String msg, @NotNull Throwable throwable, int code) {
//                    ToastUtil.INSTANCE.suc(AllGiftActivity.this,msg);
//                }
//
//                @Override
//                public boolean isAlive() {
//                    return isLive();
//                }
//            });
        }

    }
    private  void  showView(GiftWallBean.CountBean  countBean){
        hasGiftTv.setText(""+countBean.getHas_gift());
        startCountTv.setText(""+countBean.getStart_count());
    }
}
