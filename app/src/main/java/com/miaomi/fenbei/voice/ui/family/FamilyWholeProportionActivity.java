package com.miaomi.fenbei.voice.ui.family;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import android.widget.Button;
import android.widget.RadioGroup;


import com.miaomi.fenbei.base.bean.BaseBean;

import com.miaomi.fenbei.base.bean.FamilyProportionBean;
import com.miaomi.fenbei.base.core.BaseActivity;
import com.miaomi.fenbei.base.net.Callback;
import com.miaomi.fenbei.base.net.NetService;
import com.miaomi.fenbei.base.util.ToastUtil;
import com.miaomi.fenbei.voice.R;

import android.widget.RadioButton;

import org.jetbrains.annotations.NotNull;


public class FamilyWholeProportionActivity extends BaseActivity implements View.OnClickListener {


    public final static String FAMILY_ID = "FAMILY_ID";
    public static void start(Context context,String familyId){
        Intent intent = new Intent(context, FamilyWholeProportionActivity.class);
        intent.putExtra(FAMILY_ID,familyId);
        context.startActivity(intent);
    }
    private RadioGroup radioGroup;
    private int wholeType=0;
    private Button preserBt;
    private String familyId;
    private RadioButton wholeRb_70;
    private RadioButton wholeRb_60;
    private RadioButton wholeRb_50;
    @Override
    public int getLayoutId() {
        return R.layout.activity_family_whole_proportion;
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
        radioGroup=findViewById(R.id.rg_proportion);
        preserBt=findViewById(R.id.bt_preser);
        wholeRb_70= findViewById(R.id.rb_whole_70);
        wholeRb_60=findViewById(R.id.rb_whole_60);
        wholeRb_50=findViewById(R.id.rb_whole_50);
        preserBt.setOnClickListener(this);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {

             //在这里同时可以根据小组定义数据传递到服务器；
                if(checkedId==R.id.rb_whole_70){
                    wholeType=70;
//                    Toast.makeText(getApplicationContext(),"应用至全部成员",Toast.LENGTH_LONG).show();
                }else if(checkedId==R.id.rb_whole_60){
                    wholeType=60;
                }else{
                    wholeType=50;
                }

          }
      });

    }
    private void getData(){

        NetService.Companion.getInstance(this).getFamilyDivided(familyId, new Callback<FamilyProportionBean>() {
            @Override
            public void onSuccess(int nextPage, FamilyProportionBean bean, int code) {

                if (bean.getDivided_type() == 70) {
                    wholeRb_70.setChecked(true);
                } else if(bean.getDivided_type() == 60){
                    wholeRb_60.setChecked(true);
                }else{
                    wholeRb_50.setChecked(true);
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
    private void setUpProportion(int divided_type){

        NetService.Companion.getInstance(this).getFamily_Split_Into_All(familyId,divided_type, new Callback<BaseBean>() {
            @Override
            public void onSuccess(int nextPage, BaseBean bean, int code) {
                ToastUtil.INSTANCE.suc(FamilyWholeProportionActivity.this,"分配成功");
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
        if(id==R.id.bt_preser){

            setUpProportion(wholeType);
        }
    }



}
