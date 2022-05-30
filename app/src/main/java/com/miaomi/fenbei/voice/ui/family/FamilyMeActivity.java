package com.miaomi.fenbei.voice.ui.family;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.miaomi.fenbei.base.bean.FamilyBean;
import com.miaomi.fenbei.base.bean.MeFamilyBean;
import com.miaomi.fenbei.base.core.BaseActivity;
import com.miaomi.fenbei.base.net.Callback;
import com.miaomi.fenbei.base.net.NetService;
import com.miaomi.fenbei.base.util.ImgUtil;
import com.miaomi.fenbei.voice.R;

import org.jetbrains.annotations.NotNull;


public class FamilyMeActivity extends BaseActivity implements View.OnClickListener {



    ImageView closeIv;
    MeFamilyBean meFamilyBean;
    public final static int TYPE_REFRESH = 0;
    public final static int TYPE_LOADMROE = 1;
    private int page = 1;
    ImageView iconIv;
    TextView fmNameTv;
    TextView nameTv;
    TextView totalTv;
    TextView idTv;
    LinearLayout familyNLl;
    RecyclerView recommedRv;
    FamilyRecomListAdapter adapter;
    public static void start(Context context){
        Intent intent = new Intent(context, FamilyMeActivity.class);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_family_me;
    }

    @Override
    public void initView() {
        setBaseStatusBar(false,false);
        closeIv = findViewById(R.id.iv_back);
        iconIv = findViewById(R.id.user_icon);
        fmNameTv = findViewById(R.id.tv_family_name);
        nameTv = findViewById(R.id.tv_name);
        totalTv = findViewById(R.id.tv_total);
        idTv = findViewById(R.id.tv_family_id);
        familyNLl=findViewById(R.id.ll_family_n);

        recommedRv=findViewById(R.id.rv_recommed);
        closeIv.setOnClickListener(this);

        adapter = new FamilyRecomListAdapter(this);
        recommedRv.setLayoutManager(new LinearLayoutManager(this));
        recommedRv.setAdapter(adapter);
//        getData();
        getRecommedData();
    }



    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.iv_back){
            finish();
        }
//        if(id==R.id.ll_family_y){
//            if(meFamilyBean.getHost_info().getFamily_type()==3||meFamilyBean.getHost_info().getFamily_type()==2){
//                FamilyAdminCenterActivity.start(FamilyMeActivity.this,String.valueOf(meFamilyBean.getHost_info().getFamily_id()));
//            }else{
//                FamilyCenterActivity.start(FamilyMeActivity.this,String.valueOf(meFamilyBean.getHost_info().getFamily_id()));
//            }
//        }
    }

    private void getData(){
        NetService.Companion.getInstance(this).meFamily(new Callback<MeFamilyBean>() {
            @Override
            public void onSuccess(int nextPage, MeFamilyBean bean, int code) {
                meFamilyBean=bean;
                bindData();
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

    private void getRecommedData(){
        NetService.Companion.getInstance(this).RecommedFamily( new Callback<FamilyBean>() {
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

    private void bindData(){
         if(meFamilyBean.getType()==0){
             familyNLl.setVisibility(View.GONE);
             ImgUtil.INSTANCE.loadCircleImg(FamilyMeActivity.this,meFamilyBean.getHost_info().getIcon(),iconIv,R.drawable.common_avter_placeholder);
             totalTv.setText(""+meFamilyBean.getHost_info().getMoney_total());
             fmNameTv.setText(meFamilyBean.getHost_info().getFamily_name());
             nameTv.setText("族长："+meFamilyBean.getHost_info().getNickname());
             idTv.setText("家族ID："+meFamilyBean.getHost_info().getFamily_id());
         }else{
             familyNLl.setVisibility(View.VISIBLE);

         }
    }

}
