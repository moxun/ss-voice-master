package com.miaomi.fenbei.voice.ui.main;


import android.content.Context;
import android.content.Intent;

import com.miaomi.fenbei.base.bean.ChatListBean;
import com.miaomi.fenbei.base.bean.FollowResultBean;
import com.miaomi.fenbei.base.core.BaseActivity;
import com.miaomi.fenbei.base.core.dialog.CommonDialog;
import com.miaomi.fenbei.base.net.Callback;
import com.miaomi.fenbei.base.net.NetService;
import com.miaomi.fenbei.base.util.LoadHelper;
import com.miaomi.fenbei.base.util.ToastUtil;
import com.miaomi.fenbei.base.widget.GridSpacingItemDecoration;
import com.miaomi.fenbei.base.widget.xrecyclerview.XRecyclerView;
import com.miaomi.fenbei.voice.R;
import com.miaomi.fenbei.voice.ui.main.adapter.ChatFollowListAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.util.DensityUtil;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class FollowRoomActivity extends BaseActivity implements XRecyclerView.LoadingListener {

//    private LoadHelper loadHelper;

    private ChatFollowListAdapter adapter;
    private RecyclerView recyclerView;
    private SmartRefreshLayout mSwipeRefreshLayout;
    private int page;
    @Override
    public int getLayoutId() {
        return R.layout.activity_flolow;
    }

    public static void start(@NonNull Context context) {
        Intent intent = new Intent(context, FollowRoomActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void initView() {
        setBaseStatusBar(false,false);
        recyclerView = findViewById(R.id.follow_rv);
        mSwipeRefreshLayout = findViewById(R.id.refresh_layout);
        adapter =new ChatFollowListAdapter(this);
        adapter.setOnRoomLongClickListner((roomName, roomId) -> {
            CommonDialog upDialog = new CommonDialog(FollowRoomActivity.this);
            upDialog.setTitle("友情提示");
            upDialog.setContent("是否取消对【"+roomName+"】的收藏");
            upDialog.setLeftBt("取消", v -> upDialog.dismiss());
            upDialog.setRightBt("确认取消", v -> {
                upDialog.dismiss();
                followChat(roomId);
            });
            upDialog.show();
        });
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, DensityUtil.dp2px(8f),true));

        recyclerView.setAdapter(adapter);
        mSwipeRefreshLayout.setOnRefreshListener(refreshLayout -> loadData(getTYPE_REFRESH()));
        mSwipeRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                loadData(getTYPE_LOADMROE());
            }
        });

//        loadHelper = new LoadHelper();
//        loadHelper.registerLoad(recyclerView);
        loadData(getTYPE_REFRESH());
    }

    private void loadData(int type) {
        if (type == super.getTYPE_REFRESH()){
            page = 1;
        }
        NetService.Companion.getInstance(this).getFollowChats(page, new Callback<ArrayList<ChatListBean>>() {
            @Override
            public void onSuccess(int nextPage, ArrayList<ChatListBean> list, int code) {
//                recyclerView.refreshComplete();
                mSwipeRefreshLayout.finishRefresh(true);
//                if (page == 1 && list.size() == 0) {
//                    loadHelper.setEmptyCallback(R.drawable.common_empty_bg, "空空如也~");
//                } else {
//                    loadHelper.bindView(code);
//                }
                if (type == getTYPE_REFRESH()){
                    adapter.setData(list);
                }else{
                    adapter.addData(list);
                }
                page = nextPage;
            }

            @Override
            public void onError(@NotNull String msg, @NotNull Throwable throwable, int code) {
//                recyclerView.refreshComplete();
                mSwipeRefreshLayout.finishRefresh(true);
//                loadHelper.setErrorCallback(code, v -> loadData(getTYPE_REFRESH()));
            }

            @Override
            public boolean isAlive() {
                return isLive();
            }
        });
    }



    private void followChat(String roomId) {
        NetService.Companion.getInstance(this).followChat(roomId, new Callback<FollowResultBean>() {
            @Override
            public void onSuccess(int nextPage, FollowResultBean bean, int code) {
                ToastUtil.INSTANCE.suc(FollowRoomActivity.this, "取消成功");
                onRefresh();
            }

            @Override
            public void onError(@NotNull String msg, @NotNull Throwable throwable, int code) {
                ToastUtil.INSTANCE.suc(FollowRoomActivity.this, msg);
            }

            @Override
            public boolean isAlive() {
                return isLive();
            }
        });
    }

    @Override
    public void onRefresh() {
        loadData(getTYPE_REFRESH());
    }

    @Override
    public void onLoadMore() {
        loadData(getTYPE_LOADMROE());
    }
}
