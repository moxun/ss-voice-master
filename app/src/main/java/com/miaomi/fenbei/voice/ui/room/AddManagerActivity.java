package com.miaomi.fenbei.voice.ui.room;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.miaomi.fenbei.base.bean.RoleSetBean;
import com.miaomi.fenbei.base.bean.UserInfo;
import com.miaomi.fenbei.base.core.BaseActivity;
import com.miaomi.fenbei.base.net.Callback;
import com.miaomi.fenbei.base.net.Data;
import com.miaomi.fenbei.base.net.NetService;
import com.miaomi.fenbei.base.util.LoadHelper;
import com.miaomi.fenbei.base.util.ToastUtil;
import com.miaomi.fenbei.base.widget.search.SearchView;
import com.miaomi.fenbei.voice.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class AddManagerActivity extends BaseActivity implements View.OnClickListener ,AddManagerAdapter.OnSettingListener {
    RecyclerView mSearchRecyclerView;
    SearchView mSearchView;
    ImageView closeIv;
    LoadHelper loadHelper;
    AddManagerAdapter mAddManagerAdapter;
    List<UserInfo> mUsetInfoList = new ArrayList<>();
    private String roomId;
    private final static String ROOM_ID = "ROOM_ID";

    public static void startActivity(Context context,String roomId){
        Intent intent = new Intent(context,AddManagerActivity.class);
        intent.putExtra(ROOM_ID,roomId);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.chatting_activity_add_maneger;
    }

    @Override
    public void initView() {
        roomId = getIntent().getStringExtra(ROOM_ID);
        mSearchRecyclerView = findViewById(R.id.rv_search);
        mSearchView = findViewById(R.id.sv_search);
        closeIv = findViewById(R.id.iv_back);
        closeIv.setOnClickListener(this);
        mSearchView.setOnSearchListener(new SearchView.OnSearchListener() {
            @Override
            public void onSearch(String keyWord) {
                getData();
            }
        });
        mAddManagerAdapter = new AddManagerAdapter(this,mUsetInfoList);
//        mHistroyGiftView.setPullRefreshEnabled(false);
        mSearchRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mSearchRecyclerView.setAdapter(mAddManagerAdapter);
        mAddManagerAdapter.setOnSettingListener(this);
        loadHelper = new LoadHelper();
        loadHelper.registerLoad(mSearchRecyclerView);
        loadHelper.bindView(Data.CODE_SUC);
        mSearchView.setHint("请输入用户昵称或ID");
        mSearchView.setFocusable(true);
        mSearchView.setFocusableInTouchMode(true);
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.iv_back){
            finish();
        }
    }
    private void bindData(List<UserInfo> list){
        if (list.size() == 0){
            loadHelper.bindView(Data.CODE_EMPTY);
            return;
        }
        mUsetInfoList.clear();
        mUsetInfoList.addAll(list);
        mAddManagerAdapter.notifyDataSetChanged();
    }
    private void addData(List<UserInfo> list){
        mUsetInfoList.addAll(list);
        mAddManagerAdapter.notifyDataSetChanged();
    }
    private void getData(){
        if (TextUtils.isEmpty(mSearchView.getKeyword())){
            ToastUtil.INSTANCE.suc(AddManagerActivity.this,"请输入需要搜索的内容");
            return;
        }
        loadHelper.bindView(Data.CODE_LOADING);
        NetService.Companion.getInstance(this).searchRoomUser(roomId,mSearchView.getKeyword(), new Callback<List<UserInfo>>() {

            @Override
            public void onSuccess(int nextPage, List<UserInfo> bean, int code) {
                loadHelper.bindView(Data.CODE_SUC);
                if (bean.size() <= 0){
                    //空状态
                    loadHelper.setEmptyCallback(R.drawable.common_empty_bg,"什么都没有找到～");
                    return;
                }
                bindData(bean);
            }

            @Override
            public void onError(@NotNull String msg, @NotNull Throwable throwable, int code) {
                loadHelper.bindView(Data.CODE_ERROR);
                ToastUtil.INSTANCE.suc(AddManagerActivity.this,msg);
            }

            @Override
            public boolean isAlive() {
                return isLive();
            }
        });
    }

    @Override
    public void onSetting(String uid) {
        NetService.Companion.getInstance(this).setUserRole(roomId, uid, new Callback<RoleSetBean>() {
            @Override
            public void onSuccess(int nextPage, RoleSetBean bean, int code) {
                ToastUtil.INSTANCE.suc(AddManagerActivity.this,"操作成功");
                getData();
            }

            @Override
            public void onError(@NotNull String msg, @NotNull Throwable throwable, int code) {
                ToastUtil.INSTANCE.suc(AddManagerActivity.this,msg);
            }

            @Override
            public boolean isAlive() {
                return false;
            }
        });
    }
}
