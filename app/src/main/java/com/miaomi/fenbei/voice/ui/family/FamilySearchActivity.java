package com.miaomi.fenbei.voice.ui.family;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.miaomi.fenbei.base.bean.FamilyBean;
import com.miaomi.fenbei.base.core.BaseActivity;
import com.miaomi.fenbei.base.net.Callback;
import com.miaomi.fenbei.base.net.Data;
import com.miaomi.fenbei.base.net.NetService;
import com.miaomi.fenbei.base.util.LoadHelper;
import com.miaomi.fenbei.base.util.SpSearchUtils;
import com.miaomi.fenbei.base.widget.ZFlowLayout;
import com.miaomi.fenbei.base.widget.search.SearchView;
import com.miaomi.fenbei.voice.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class FamilySearchActivity extends BaseActivity implements View.OnClickListener {
    RecyclerView mSearchRecyclerView;
    FamilyRecomListAdapter mSearchAdapter;
    SearchView mSearchView;
    ImageView closeIv;
    ImageView delIV;
//    LoadHelper loadHelper;
    public final static int TYPE_REFRESH = 0;
    public final static int TYPE_LOADMROE = 1;
    private int page = 1;
    String keyword;
    ZFlowLayout zFlowLayout;
    RecyclerView recommedRv;
    FamilyRecomListAdapter adapter;
    private LinearLayout  familySerchLl;
    LoadHelper loadHelper;
    public static void start(Context context){
        Intent intent = new Intent(context, FamilySearchActivity.class);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_family_search;
    }

    @Override
    public void initView() {
        setBaseStatusBar(false,false);
        mSearchRecyclerView = findViewById(R.id.rv_search);
        mSearchView = findViewById(R.id.sv_search);
        closeIv = findViewById(R.id.iv_back);
        zFlowLayout = findViewById(R.id.history_fl);
        delIV= findViewById(R.id.iv_del);
        recommedRv=findViewById(R.id.rv_recommed);
        familySerchLl=findViewById(R.id.ll_family_serch);
        closeIv.setOnClickListener(this);
        delIV.setOnClickListener(this);
        adapter = new FamilyRecomListAdapter(this);
        recommedRv.setLayoutManager(new LinearLayoutManager(this));
        recommedRv.setAdapter(adapter);
        loadHelper = new LoadHelper();
        loadHelper.registerLoad(mSearchRecyclerView);
        loadHelper.bindView(Data.CODE_SUC);
        initHistory();
        mSearchView.setOnSearchListener(new SearchView.OnSearchListener() {
            @Override
            public void onSearch(String keyWord) {
                if (!isNullorEmpty(keyWord)) {
                    SpSearchUtils.getInstance(FamilySearchActivity.this).save(keyWord);
                    initHistory();
                    familySerchLl.setVisibility(View.GONE);
                    mSearchRecyclerView.setVisibility(View.VISIBLE);
                    getData(TYPE_REFRESH);
                }
            }
        });
        mSearchAdapter = new FamilyRecomListAdapter(this);

//        mSearchRecyclerView.setPullRefreshEnabled(false);
        mSearchRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mSearchRecyclerView.setAdapter(mSearchAdapter);
//        loadHelper = new LoadHelper();
//        loadHelper.registerLoad(mSearchRecyclerView);
//        loadHelper.bindView(Data.CODE_SUC);
        mSearchView.setFocusable(true);
        mSearchView.setFocusableInTouchMode(true);
        getRecommedData();
    }

//    @Override
//    public void onWindowFocusChanged(boolean hasFocus) {
//        super.onWindowFocusChanged(hasFocus);
//        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
//    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.iv_back){
            finish();
        }
        if(id==R.id.iv_del){
            SpSearchUtils.getInstance(FamilySearchActivity.this).cleanHistory();
            initHistory();
        }
    }
//    private void bindData(List<FamilyBean> list){
//        mSearchList.clear();
//        mSearchList.addAll(list);
//        mSearchAdapter.notifyDataSetChanged();
//    }
//    private void addData(List<FamilyBean> list){
//        mSearchList.addAll(list);
//        mSearchAdapter.notifyDataSetChanged();
//    }
private void getRecommedData(){
    NetService.Companion.getInstance(this).RecommedFamily(new Callback<FamilyBean>() {
        @Override
        public void onSuccess(int nextPage, FamilyBean bean, int code) {
            adapter.setData(bean.getList());
        }

        @Override
        public void onError(@NotNull String msg, @NotNull Throwable throwable, int code) {

        }

        @Override
        public boolean isAlive() {
            return isLive();
        }
    });
}

    private void getData(final int type){
        if (type == TYPE_REFRESH){
            page = 1;
            keyword = mSearchView.getKeyword();
        }
//        loadHelper.bindView(Data.CODE_LOADING);
        NetService.Companion.getInstance(this).searchFamily(page, keyword, new Callback<List<FamilyBean.ListBean>>() {
            @Override
            public void onSuccess(int nextPage, List<FamilyBean.ListBean> bean, int code) {
                loadHelper.bindView(Data.CODE_SUC);
                if (bean.size() == 0){
                    loadHelper.bindView(Data.CODE_EMPTY);
                    return;
                }
                if (type == TYPE_REFRESH){
                    mSearchAdapter.setData(bean);
                }else{
                    mSearchAdapter.addData(bean);
                }
            }

            @Override
            public void onError(@NotNull String msg, @NotNull Throwable throwable, int code) {
//                mSearchRecyclerView.refreshComplete();
//                loadHelper.setErrorCallback(code, new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        getData(TYPE_REFRESH);
//                    }
//                });
            }
            @Override
            public boolean isAlive() {
                return isLive();
            }
        });
    }
    private boolean isNullorEmpty(String str) {
        return str == null || "".equals(str);
    }
        /**
         * 初始化  历史记录列表
         */
    private void initHistory() {

        final String[] data = SpSearchUtils.getInstance(FamilySearchActivity.this).getHistoryList();
        ViewGroup.MarginLayoutParams layoutParams = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(10, 10, 10, 10);

        zFlowLayout.removeAllViews();
        for (int i = 0; i < data.length; i++) {
            if (isNullorEmpty(data[i])) {

                return;
            }
            //有数据往下走
            final int j = i;
            //添加分类块
            View paramItemView = getLayoutInflater().inflate(R.layout.adapter_search_keyword, null);
            TextView keyWordTv = paramItemView.findViewById(R.id.tv_content);
            keyWordTv.setText(data[j]);
            zFlowLayout.addView(paramItemView, layoutParams);
            keyWordTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    mSearchView.seKeyword(data[j]);
                    if (!isNullorEmpty(data[j])) {
                        familySerchLl.setVisibility(View.GONE);
                        mSearchRecyclerView.setVisibility(View.VISIBLE);
                        SpSearchUtils.getInstance(FamilySearchActivity.this).save(mSearchView.getKeyword());
                        getData(TYPE_REFRESH);
                    }
                    //点击事件
                }
            });
            // initautoSearch();
        }
    }



}
