package com.miaomi.fenbei.voice.ui.family;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.miaomi.fenbei.base.bean.BaseBean;
import com.miaomi.fenbei.base.bean.FamilyProportionBean;
import com.miaomi.fenbei.base.core.BaseActivity;
import com.miaomi.fenbei.base.net.Callback;
import com.miaomi.fenbei.base.net.NetService;
import com.miaomi.fenbei.base.util.ToastUtil;
import com.miaomi.fenbei.voice.R;

import org.jetbrains.annotations.NotNull;


public class FamilyProportionActivity extends BaseActivity implements View.OnClickListener {


    public final static String FAMILY_ID = "FAMILY_ID";
    public static void start(Context context,String familyId){
        Intent intent = new Intent(context, FamilyProportionActivity.class);
        intent.putExtra(FAMILY_ID,familyId);
        context.startActivity(intent);
    }
    private RadioGroup radioGroup;
    private RadioButton wholeRb;
    private RadioButton partRb;
    private LinearLayout wholeLl;
    private LinearLayout partLl;
    private String familyId;
    private int whole=0;
    private int part=1;
    @Override
    public int getLayoutId() {
        return R.layout.activity_family_proportion;
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public void initView() {
        setBaseStatusBar(false,false);
        familyId = getIntent().getStringExtra(FAMILY_ID);
        radioGroup=findViewById(R.id.rg_proportion);
        wholeLl=findViewById(R.id.ll_whole);
        partLl=findViewById(R.id.ll_part);
        wholeRb= findViewById(R.id.rb_whole);
        partRb=findViewById(R.id.rb_part);
        wholeLl.setOnClickListener(this);
        partLl.setOnClickListener(this);
        wholeRb.setOnClickListener(this);
        partRb.setOnClickListener(this);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {

             //在这里同时可以根据小组定义数据传递到服务器；


          }
      });
        getData();
    }

    private void getData() {

        NetService.Companion.getInstance(this).getFamilyDivided_Type(familyId, new Callback<FamilyProportionBean>() {
            @Override
            public void onSuccess(int nextPage, FamilyProportionBean bean, int code) {

                if (bean.getDivided_type() == 0) {
                    wholeRb.setChecked(true);
                } else {
                    partRb.setChecked(true);
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

        NetService.Companion.getInstance(this).getFamily_Checkout_Divided_Type(familyId,divided_type, new Callback<BaseBean>() {
            @Override
            public void onSuccess(int nextPage, BaseBean bean, int code) {
                ToastUtil.INSTANCE.suc(FamilyProportionActivity.this,"设置成功");
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
        if (id == R.id.ll_whole){
            FamilyWholeProportionActivity.start(this,familyId);
        }
        if (id == R.id.ll_part){
            FamilyPartProportionActivity.start(this,familyId);
        }
        if(id==R.id.rb_whole){
            setUpProportion(whole);
        }
        if(id==R.id.rb_part){
            setUpProportion(part);
        }
    }



}
