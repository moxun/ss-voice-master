package com.miaomi.fenbei.voice.ui.family;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.miaomi.fenbei.base.bean.DividedUserBean;
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

public class FamilyPartActivity extends BaseActivity {
    PartAdapter adapter;
    RecyclerView mRecyclerView;
    SearchView mSearchView;
    private TextView adminAddTv;
    public final static String FAMILY_ID = "FAMILY_ID";
    public final static String FAMILY_TYPE = "FAMILY_TYPE";
    private String familyId = "";
    private int familyType = 0;
    private TextView titleTv;
    String keyword;
    private TextView tipsTv;
    private List<DividedUserBean.ListBean> familyMemberBean;
    public static void start(Context context,String familyId,int familyType){
        Intent intent = new Intent(context, FamilyPartActivity.class);
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
        adapter = new PartAdapter(this,familyType);
        mRecyclerView = findViewById(R.id.rv_family_member);
        mSearchView = findViewById(R.id.sv_search);
        adminAddTv=findViewById(R.id.tv_add_admin);
        titleTv=findViewById(R.id.main_tv);
        tipsTv=findViewById(R.id.tv_tips);
        titleTv.setText("成员列表("+familyType+"%)");
        tipsTv.setVisibility(View.GONE);
        mSearchView.setOnSearchListener(new SearchView.OnSearchListener() {
            @Override
            public void onSearch(String keyWord) {
                    getData();
            }
        });
        adminAddTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FamilyAddPartActivity.start(FamilyPartActivity.this,familyId,familyType);
            }
        });
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(adapter);
        familyMemberBean=new ArrayList<>();
        adapter.setOnOprateListener(new PartAdapter.OnOprateListener() {
            @Override
            public void onSetManeger(final String uid, int isManager) {
                String content = "你确定要更改用户分配比例吗？";

                final CommonDialog outDialog = new CommonDialog(FamilyPartActivity.this);
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
                        oprate(uid,isManager);

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

        NetService.Companion.getInstance(this).getFamilyUserDividedDetail(familyId,familyType,keyword, new Callback<DividedUserBean>() {
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
    private void oprate(String uid,int type){
        NetService.Companion.getInstance(this).upDividedSection(familyId, uid,type, new Callback<Object>() {
            @Override
            public void onSuccess(int nextPage, Object bean, int code) {
                getData();
            }

            @Override
            public void onError(@NotNull String msg, @NotNull Throwable throwable, int code) {
                ToastUtil.INSTANCE.i(FamilyPartActivity.this,msg);
            }

            @Override
            public boolean isAlive() {

                return isLive();
            }
        });
    }

}
