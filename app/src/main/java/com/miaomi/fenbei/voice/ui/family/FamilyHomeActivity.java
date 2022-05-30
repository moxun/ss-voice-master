package com.miaomi.fenbei.voice.ui.family;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.miaomi.fenbei.base.bean.FamilyCenterInfoBean;
import com.miaomi.fenbei.base.core.BaseActivity;
import com.miaomi.fenbei.base.net.Callback;
import com.miaomi.fenbei.base.net.Data;
import com.miaomi.fenbei.base.net.NetService;
import com.miaomi.fenbei.base.util.LoadHelper;
import com.miaomi.fenbei.base.widget.search.SearchView;
import com.miaomi.fenbei.voice.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class FamilyHomeActivity extends BaseActivity {
    FamilyRoomAdapter mFamilyRoomAdapter;
    RecyclerView mRecyclerView;
    SearchView mSearchView;
    public final static String FAMILY_ID = "FAMILY_ID";
    public final static String FAMILY_TYPE = "FAMILY_TYPE";
    private String familyId = "";
    private int familyType = 0;
    LoadHelper loadHelper;
    String keyword;
    public static void start(Context context,String familyId,int familyType){
        Intent intent = new Intent(context, FamilyHomeActivity.class);
        intent.putExtra(FAMILY_ID,familyId);
        intent.putExtra(FAMILY_TYPE,familyType);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_family_home;
    }

    @Override
    public void initView() {
        setBaseStatusBar(false,false);
        familyType  = getIntent().getIntExtra(FAMILY_TYPE,0);
        familyId = getIntent().getStringExtra(FAMILY_ID);
        mRecyclerView = findViewById(R.id.rv_family_home);
        mSearchView = findViewById(R.id.sv_search);
        mSearchView.setHint("请输入房间名称、ID或房主名称");
        mSearchView.setOnSearchListener(new SearchView.OnSearchListener() {
            @Override
            public void onSearch(String keyWord) {
                    getData();
            }
        });
        mFamilyRoomAdapter = new FamilyRoomAdapter(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(FamilyHomeActivity.this));
        mRecyclerView.setAdapter(mFamilyRoomAdapter);
        loadHelper = new LoadHelper();
        loadHelper.registerLoad(mRecyclerView);
        loadHelper.bindView(Data.CODE_SUC);
        mSearchView.setOnSearchListener(new SearchView.OnSearchListener() {
            @Override
            public void onSearch(String keyWord) {
                getData();
            }
        });

        getData();
    }
    private void getData(){
        keyword = mSearchView.getKeyword();
        loadHelper.bindView(Data.CODE_LOADING);
        NetService.Companion.getInstance(this).getFamilyRoomList(familyId,keyword, new Callback<List<FamilyCenterInfoBean.RoomInfoBean>>() {
            @Override
            public void onSuccess(int nextPage, List<FamilyCenterInfoBean.RoomInfoBean> bean, int code) {
                loadHelper.bindView(Data.CODE_SUC);
                if (bean.size() == 0){
                    loadHelper.bindView(Data.CODE_EMPTY);
                    return;
                }
                mFamilyRoomAdapter.setData(bean);
            }

            @Override
            public void onError(@NotNull String msg, @NotNull Throwable throwable, int code) {
                loadHelper.setErrorCallback(code, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getData();
                    }
                });
            }

            @Override
            public boolean isAlive() {
                return isLive();
            }
        });
    }

}
