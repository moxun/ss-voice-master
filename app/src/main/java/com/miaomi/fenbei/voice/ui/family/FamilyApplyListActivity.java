package com.miaomi.fenbei.voice.ui.family;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.miaomi.fenbei.base.bean.ApplyListBean;
import com.miaomi.fenbei.base.core.BaseActivity;
import com.miaomi.fenbei.base.net.Callback;
import com.miaomi.fenbei.base.net.NetService;
import com.miaomi.fenbei.base.util.LoadHelper;
import com.miaomi.fenbei.base.util.ToastUtil;
import com.miaomi.fenbei.voice.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;


public class FamilyApplyListActivity extends BaseActivity {
    RecyclerView applyListRv;
    ApplyListAdapter applyListAdapter;
    public final static String FAMILY_ID = "FAMILY_ID";
    private String familyId = "";
    private LoadHelper loadHelper;
  private ImageView backIv;
    public static void start(Context context,String familyId){
        Intent intent = new Intent(context, FamilyApplyListActivity.class);
        intent.putExtra(FAMILY_ID,familyId);
        context.startActivity(intent);
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_apply_list;
    }


    @Override
    public void initView() {
        setBaseStatusBar(false,false);
        familyId = getIntent().getStringExtra(FAMILY_ID);
        applyListAdapter = new ApplyListAdapter(this);
        applyListAdapter.setOnApplyClickListener(new ApplyListAdapter.OnApplyClickListener() {
            @Override
            public void onAgree(String uid) {
                agree(2,uid);
            }

            @Override
            public void onUnAgree(String uid,int type) {
                agree(type,uid);
            }

            @Override
            public void onOutFamily(String uid) {
                outFamily(uid);
            }
        });
        applyListRv = findViewById(R.id.rv_apply_list);
        backIv=findViewById(R.id.back_btn);
        applyListRv.setLayoutManager(new LinearLayoutManager(this));
        applyListRv.setAdapter(applyListAdapter);
        loadHelper = new LoadHelper();
        loadHelper.registerLoad(applyListRv);
//        backIv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
        getData();
    }
    private void getData(){
        NetService.Companion.getInstance(this).getFamilyApplyList(familyId, new Callback<List<ApplyListBean>>() {
            @Override
            public void onSuccess(int nextPage, List<ApplyListBean> bean, int code) {
                loadHelper.bindView(code);
                applyListAdapter.setData(bean);
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
    private void agree(int type , String uid){

        NetService.Companion.getInstance(this).operateFamily(familyId, uid, type, new Callback<Object>() {
            @Override
            public void onSuccess(int nextPage, Object bean, int code) {
                getData();
            }

            @Override
            public void onError(@NotNull String msg, @NotNull Throwable throwable, int code) {
                ToastUtil.INSTANCE.i(FamilyApplyListActivity.this,msg);
            }

            @Override
            public boolean isAlive() {
                return isLive();
            }
        });
    }
    private void outFamily(String uid){

        NetService.Companion.getInstance(this).outFamily(uid, new Callback<Object>() {
            @Override
            public void onSuccess(int nextPage, Object bean, int code) {
                getData();
            }

            @Override
            public void onError(@NotNull String msg, @NotNull Throwable throwable, int code) {
                ToastUtil.INSTANCE.i(FamilyApplyListActivity.this,msg);
            }

            @Override
            public boolean isAlive() {
                return isLive();
            }
        });
    }

}
