package com.miaomi.fenbei.voice.ui.family;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.miaomi.fenbei.base.bean.DividedUserBean;
import com.miaomi.fenbei.base.bean.FamilyMemberBean;
import com.miaomi.fenbei.base.core.BaseActivity;
import com.miaomi.fenbei.base.net.Callback;
import com.miaomi.fenbei.base.net.NetService;
import com.miaomi.fenbei.base.util.ToastUtil;
import com.miaomi.fenbei.base.widget.search.SearchView;
import com.miaomi.fenbei.voice.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class FamilyAddPartActivity extends BaseActivity implements AddPartAdapter.CheckItemListener{
    AddPartAdapter adapter;
    RecyclerView mRecyclerView;
    SearchView mSearchView;
    public final static String FAMILY_ID = "FAMILY_ID";
    public final static String FAMILY_TYPE = "FAMILY_TYPE";
    private String familyId = "";
    private int familyType = 0;
    String keyword;
    private TextView adminAddTv;
    //列表数据
    private List<DividedUserBean.ListBean> familyMemberBean;
    private TextView tipsTv;

    //选中后的数据
    private List<String> checkedList;
    public static void start(Context context,String familyId,int familyType){
        Intent intent = new Intent(context, FamilyAddPartActivity.class);
        intent.putExtra(FAMILY_ID,familyId);
        intent.putExtra(FAMILY_TYPE,familyType);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_family_part;
    }

    @Override
    public void initView() {
        setBaseStatusBar(false,false);
        familyType  = getIntent().getIntExtra(FAMILY_TYPE,0);
        familyId = getIntent().getStringExtra(FAMILY_ID);
        adapter = new AddPartAdapter(this,this);
        mRecyclerView = findViewById(R.id.rv_family_member);
        mSearchView = findViewById(R.id.sv_search);
        adminAddTv=findViewById(R.id.tv_add_admin);
        tipsTv=findViewById(R.id.tv_tips);
        adminAddTv.setText("保存");
        tipsTv.setVisibility(View.VISIBLE);
        mSearchView.setOnSearchListener(new SearchView.OnSearchListener() {
            @Override
            public void onSearch(String keyWord) {
                    getData();
            }
        });
        adminAddTv.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                if(checkedList.size()<=0){
                    ToastUtil.INSTANCE.i(FamilyAddPartActivity.this,"请选中添加的用户");
                }else{
                    String userList = String.join(",", checkedList);
                    oprate(userList,familyType);
                }

            }
        });
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(adapter);
        familyMemberBean=new ArrayList<>();
        checkedList=new ArrayList<>();

        getData();
    }
    private void getData(){
        keyword = mSearchView.getKeyword();
        NetService.Companion.getInstance(this).getFamilyAddDividedUser(familyId,familyType,keyword, new Callback<DividedUserBean>() {
            @Override
            public void onSuccess(int nextPage, DividedUserBean bean, int code) {
                familyMemberBean.clear();
                familyMemberBean.addAll(bean.getList());
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

    private void oprate(String uid, int type){
        Log.e(familyId+"uid"+uid,"type"+type);
        NetService.Companion.getInstance(this).upDividedSection(familyId, uid,type, new Callback<Object>() {
            @Override
            public void onSuccess(int nextPage, Object bean, int code) {
                getData();
            }

            @Override
            public void onError(@NotNull String msg, @NotNull Throwable throwable, int code) {
                ToastUtil.INSTANCE.i(FamilyAddPartActivity.this,msg);
            }

            @Override
            public boolean isAlive() {

                return isLive();
            }
        });
    }

    @Override
    public void itemChecked(String userid, boolean isChecked) {
        //处理Item点击选中回调事件
        if (isChecked) {
            //选中处理
            if (!checkedList.contains(userid)) {
                checkedList.add(userid);
            }
        } else {
            //未选中处理
            if (checkedList.contains(userid)) {
                checkedList.remove(userid);
            }
        }

    }
}
