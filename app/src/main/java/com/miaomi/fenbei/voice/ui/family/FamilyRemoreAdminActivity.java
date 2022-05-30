package com.miaomi.fenbei.voice.ui.family;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.miaomi.fenbei.base.bean.FamilyMemberBean;
import com.miaomi.fenbei.base.core.BaseActivity;
import com.miaomi.fenbei.base.core.dialog.CommonDialog;
import com.miaomi.fenbei.base.net.Callback;
import com.miaomi.fenbei.base.net.NetService;
import com.miaomi.fenbei.base.util.ToastUtil;
import com.miaomi.fenbei.base.widget.search.SearchView;
import com.miaomi.fenbei.voice.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class FamilyRemoreAdminActivity extends BaseActivity {
    RemoreAdminAdapter adapter;
    RecyclerView mRecyclerView;
    SearchView mSearchView;
    private TextView adminAddTv;
    public final static String FAMILY_ID = "FAMILY_ID";
    public final static String FAMILY_TYPE = "FAMILY_TYPE";
    private String familyId = "";
    private int familyType = 0;
    String keyword;
    private List<FamilyMemberBean> familyMemberBean;
    public static void start(Context context,String familyId,int familyType){
        Intent intent = new Intent(context,FamilyRemoreAdminActivity.class);
        intent.putExtra(FAMILY_ID,familyId);
        intent.putExtra(FAMILY_TYPE,familyType);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_family_remore_admin;
    }

    @Override
    public void initView() {
        setBaseStatusBar(false,false);
        familyType  = getIntent().getIntExtra(FAMILY_TYPE,0);
        familyId = getIntent().getStringExtra(FAMILY_ID);
        adapter = new RemoreAdminAdapter(this);
        mRecyclerView = findViewById(R.id.rv_family_member);
        mSearchView = findViewById(R.id.sv_search);
        adminAddTv=findViewById(R.id.tv_add_admin);
        mSearchView.setOnSearchListener(new SearchView.OnSearchListener() {
            @Override
            public void onSearch(String keyWord) {
                    getData();
            }
        });
        adminAddTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FamilyAddAdminActivity.start(FamilyRemoreAdminActivity.this,familyId,familyType);
            }
        });
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(adapter);
        familyMemberBean=new ArrayList<>();
        adapter.setOnOprateListener(new RemoreAdminAdapter.OnOprateListener() {
            @Override
            public void onSetManeger(final String uid, boolean isManager) {
                String content = "你确定要将改用户设置为管理员吗？";
                if (isManager){
                    content = "你确定要取消该用户管理员身份吗？";
                }
                final CommonDialog outDialog = new CommonDialog(FamilyRemoreAdminActivity.this);
                outDialog.setTitle("提示");
                outDialog.setContent(content);
                outDialog.setLeftBt("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        outDialog.dismiss();
                    }
                });
                outDialog.setRightBt("确定", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        outDialog.dismiss();
                        if(isManager){
                            adminDel(uid);
                        }else{
                            oprate(uid,0);
                        }

                    }
                });
                outDialog.show();
            }
        });

    }
    @Override
    protected void onResume() {
        super.onResume();
        getData();
    }
    private void getData(){
        keyword = mSearchView.getKeyword();
        NetService.Companion.getInstance(this).getFamilyMemberList(familyId,keyword, new Callback<List<FamilyMemberBean>>() {
            @Override
            public void onSuccess(int nextPage, List<FamilyMemberBean> bean, int code) {
                familyMemberBean.clear();
                for(int i=0;i<bean.size();i++){
                    if(bean.get(i).getType()==1){
                        familyMemberBean.add(bean.get(i));
                    }
                }
                adapter.setData(familyMemberBean);
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
    private void oprate(String uid,int type){
        NetService.Companion.getInstance(this).operateFamily(familyId, uid,type, new Callback<Object>() {
            @Override
            public void onSuccess(int nextPage, Object bean, int code) {
                getData();
            }

            @Override
            public void onError(@NotNull String msg, @NotNull Throwable throwable, int code) {
                ToastUtil.INSTANCE.i(FamilyRemoreAdminActivity.this,msg);
            }

            @Override
            public boolean isAlive() {

                return isLive();
            }
        });
    }
    private void adminDel(String uid){
        NetService.Companion.getInstance(this).administrator_del(familyId, uid, new Callback<Object>() {
            @Override
            public void onSuccess(int nextPage, Object bean, int code) {
                getData();
            }

            @Override
            public void onError(@NotNull String msg, @NotNull Throwable throwable, int code) {
                ToastUtil.INSTANCE.i(FamilyRemoreAdminActivity.this,msg);
            }

            @Override
            public boolean isAlive() {

                return isLive();
            }
        });
    }
}
