package com.miaomi.fenbei.voice.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.miaomi.fenbei.base.bean.GiftHistoryBean;
import com.miaomi.fenbei.base.core.BaseFragment;
import com.miaomi.fenbei.base.net.Callback;
import com.miaomi.fenbei.base.net.Data;
import com.miaomi.fenbei.base.net.NetService;
import com.miaomi.fenbei.base.util.LoadHelper;
import com.miaomi.fenbei.base.widget.xrecyclerview.XRecyclerView;
import com.miaomi.fenbei.voice.R;

import org.jetbrains.annotations.NotNull;

import androidx.recyclerview.widget.LinearLayoutManager;

public class GiftHistoryFragment extends BaseFragment implements View.OnClickListener,XRecyclerView.LoadingListener {
    XRecyclerView mHistroyGiftView;
    LoadHelper loadHelper;
    private final static int TYPE_REFRESH = 0;
    private final static int TYPE_LOADMROE = 1;
    public final static String TYPE_GIFT_HISTORY = "TYPE_GIFT_HISTORY";
    public final static int TYPE_GIFT_HISTORY_SEND = 1;
    public final static int TYPE_GIFT_HISTORY_GET = 0;
    private int mPage = 1;
    private GiftHistoryAdapter adapter;
    private int giftType = TYPE_GIFT_HISTORY_SEND;
    private TextView monthTv;
    private TextView giftSumTv;
    private TextView monthGiftSumTv;
    private TextView monthGiftDesTv;
//    private TextView tipTv;

    public static GiftHistoryFragment newInstance(int type){
        GiftHistoryFragment fragment = new GiftHistoryFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(TYPE_GIFT_HISTORY,type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_gift_history;
    }

    @Override
    public void initView(@NotNull View view) {
        adapter = new GiftHistoryAdapter(getMContext());
        giftType = getArguments().getInt(TYPE_GIFT_HISTORY);
        mHistroyGiftView = view.findViewById(R.id.rv_gift);
        monthTv = view.findViewById(R.id.tv_month);
        giftSumTv = view.findViewById(R.id.tv_gift_num);
        monthGiftSumTv = view.findViewById(R.id.tv_month_gift_sum);
        monthGiftDesTv = view.findViewById(R.id.tv_month_gift_des);

//        tipTv = view.findViewById(R.id.tv_tip);
        mHistroyGiftView.setPullRefreshEnabled(true);
        mHistroyGiftView.setLoadingMoreEnabled(true);
        mHistroyGiftView.setLoadingListener(this);
        mHistroyGiftView.setLayoutManager(new LinearLayoutManager(getMContext()));
        mHistroyGiftView.setAdapter(adapter);
        loadHelper = new LoadHelper();
        loadHelper.registerLoad(mHistroyGiftView);
        getData(TYPE_REFRESH);
    }


    @Override
    public void onClick(View v) {

    }

    private void getData(final int type){
        if (type == TYPE_REFRESH){
            mHistroyGiftView.setLoadingMoreEnabled(true);
            mPage = 1;
        }
        NetService.Companion.getInstance(getMContext()).getGiftHistory(giftType, mPage, new Callback<GiftHistoryBean>() {
            @Override
            public void onSuccess(int nextPage, GiftHistoryBean bean, int code) {
                monthTv.setText(String.valueOf(bean.getTotal().getMonth()));
                if (giftType == TYPE_GIFT_HISTORY_GET){
                    monthGiftDesTv.setText("本月共收到");
                    giftSumTv.setText(bean.getTotal().getNumber()+"款礼物");
                    monthGiftSumTv.setText(bean.getTotal().getPrice()+"钻石");
                }else{
                    monthGiftDesTv.setText("本月共送出");
                    giftSumTv.setText(bean.getTotal().getNumber()+"款礼物");
                    monthGiftSumTv.setText(bean.getTotal().getPrice()+"钻石");
                }
                if (bean.getList().size()>0){
                    loadHelper.bindView(Data.CODE_SUC);
                    mHistroyGiftView.refreshComplete();
                    if (type == TYPE_REFRESH){
                        adapter.setData(bean.getList(),giftType);
                    }else{
                        adapter.addData(bean.getList(),giftType);
                    }
                    mPage++;
                }else{
                    if (giftType != TYPE_GIFT_HISTORY_SEND){
                        loadHelper.setEmptyCallback(0,"暂未收到礼物");
                    }else{
                        loadHelper.setEmptyCallback(0,"暂无送礼记录");
                    }
                }
            }

            @Override
            public void onError(@NotNull String msg, @NotNull Throwable throwable, int code) {
                mHistroyGiftView.refreshComplete();
                loadHelper.setErrorCallback(code, v -> getData(TYPE_REFRESH));
            }

            @Override
            public void noMore() {
                super.noMore();
                mHistroyGiftView.setLoadingMoreEnabled(false);
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
