package com.miaomi.fenbei.voice.ui.mine.redpack;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


import com.miaomi.fenbei.base.bean.RedPacketRecordBean;
import com.miaomi.fenbei.base.core.BaseFragment;
import com.miaomi.fenbei.base.net.Callback;
import com.miaomi.fenbei.base.net.Data;
import com.miaomi.fenbei.base.net.NetService;
import com.miaomi.fenbei.base.util.LoadHelper;
import com.miaomi.fenbei.base.widget.xrecyclerview.XRecyclerView;
import com.miaomi.fenbei.voice.R;

import org.jetbrains.annotations.NotNull;


import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;

public class RedPacketRecordFragment extends BaseFragment implements View.OnClickListener, XRecyclerView.LoadingListener {
    XRecyclerView mRecyclerView;
    LoadHelper loadHelper;
    private final static int TYPE_REFRESH = 0;
    private final static int TYPE_LOADMROE = 1;
    public final static String TYPE_RED_PACKET_HISTORY = "TYPE_RED_PACKET_HISTORY";
    public final static int TYPE_RED_PACKET_HISTORY_SEND = 2;
    public final static int TYPE_RED_PACKET_HISTORY_GET = 1;
    private int page = 1;
    private RedPacketRecordAdapter adapter;
    private int redPacketType = 1;
    private TextView tipTv;

    public static RedPacketRecordFragment newInstance(int type){
        RedPacketRecordFragment fragment = new RedPacketRecordFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(TYPE_RED_PACKET_HISTORY,type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.room_fragment_red_packer_record;
    }

    @Override
    public void initView(@NotNull View view) {
        adapter = new RedPacketRecordAdapter(getMContext());
        redPacketType = getArguments().getInt(TYPE_RED_PACKET_HISTORY);
        mRecyclerView = view.findViewById(R.id.rv_gift);
        tipTv = view.findViewById(R.id.tv_tip);
        mRecyclerView.setPullRefreshEnabled(true);
        mRecyclerView.setLoadingMoreEnabled(false);
        mRecyclerView.setLoadingListener(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getMContext()));
        mRecyclerView.setAdapter(adapter);
        loadHelper = new LoadHelper();
        loadHelper.registerLoad(mRecyclerView);
        getData(TYPE_REFRESH);
    }


    @Override
    public void onClick(View v) {

    }

    private void getData(final int type){
        if (type == TYPE_REFRESH){
            page = 1;
        }
        NetService.Companion.getInstance(getContext()).getRedListRecord(redPacketType, new Callback<List<RedPacketRecordBean>>() {
            @Override
            public void onSuccess(int nextPage, List<RedPacketRecordBean> list, int code) {

                if (list.size()>0){
                    loadHelper.bindView(Data.CODE_SUC);
                    mRecyclerView.refreshComplete();
                    if (type == TYPE_REFRESH){
                        adapter.setData(list);
                    }else{
                        adapter.addData(list);
                    }
                    page ++;
                }else{
                    loadHelper.setEmptyCallback(0,"暂无数据");
                }
            }


            @Override
            public void onError(@NotNull String msg, @NotNull Throwable throwable, int code) {
                mRecyclerView.refreshComplete();
                loadHelper.setErrorCallback(code, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getData(TYPE_REFRESH);
                    }
                });
            }

            @Override
            public void noMore() {
                super.noMore();
                mRecyclerView.setNoMore(true);
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
        getData(TYPE_LOADMROE);
    }
}