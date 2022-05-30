package com.miaomi.fenbei.voice.ui.family;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.miaomi.fenbei.base.bean.UserDividedBean;
import com.miaomi.fenbei.base.core.BaseActivity;
import com.miaomi.fenbei.base.net.Callback;
import com.miaomi.fenbei.base.net.NetService;
import com.miaomi.fenbei.voice.R;

import org.jetbrains.annotations.NotNull;


public class FamilyPartProportionActivity extends BaseActivity implements View.OnClickListener {


    public final static String FAMILY_ID = "FAMILY_ID";
    public static void start(Context context,String familyId){
        Intent intent = new Intent(context, FamilyPartProportionActivity.class);
        intent.putExtra(FAMILY_ID,familyId);
        context.startActivity(intent);
    }

    private LinearLayout partLl_70;
    private LinearLayout partLl_60;
    private LinearLayout partLl_50;
    private String familyId;
    private TextView  numberperTv_70,numberperTv_60,numberperTv_50;

    @Override
    public int getLayoutId() {
        return R.layout.activity_family_part_proportion;
    }

    @Override
    protected void onResume() {
        super.onResume();
        getData();
    }

    @Override
    public void initView() {
        setBaseStatusBar(false,false);
        familyId = getIntent().getStringExtra(FAMILY_ID);
        partLl_70=findViewById(R.id.ll_part_70);
        partLl_60=findViewById(R.id.ll_part_60);
        partLl_50=findViewById(R.id.ll_part_50);
        numberperTv_70=findViewById(R.id.tv_number_per_70);
        numberperTv_60= findViewById(R.id.tv_number_per_60);
        numberperTv_50=findViewById(R.id.tv_number_per_50);
        partLl_50.setOnClickListener(this);
        partLl_60.setOnClickListener(this);
        partLl_70.setOnClickListener(this);
    }
    private void getData(){

        NetService.Companion.getInstance(getBaseContext()).getFamily_User_Divided(familyId, new Callback<UserDividedBean>() {
            @Override
            public void onSuccess(int nextPage, UserDividedBean bean, int code) {

            for (int i=0;i<bean.getList().size();i++){
                if(bean.getList().get(i).getDivided()==0.7){
                    numberperTv_70.setText(bean.getList().get(i).getCount()+"人");
                }else if(bean.getList().get(i).getDivided()==0.6){
                    numberperTv_60.setText(bean.getList().get(i).getCount()+"人");
                }else{
                    numberperTv_50.setText(bean.getList().get(i).getCount()+"人");
                }
             }
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

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.iv_back){
            finish();
        }
        if(id==R.id.ll_part_70){
            FamilyPartActivity.start(this,familyId,70);
        }
        if(id==R.id.ll_part_60){
            FamilyPartActivity.start(this,familyId,60);
        }
        if(id==R.id.ll_part_50){
            FamilyPartActivity.start(this,familyId,50);
        }
    }



}
