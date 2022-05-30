package com.miaomi.fenbei.voice.ui.family;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.miaomi.fenbei.base.bean.FamilyCenterInfoBean;
import com.miaomi.fenbei.base.core.BaseActivity;
import com.miaomi.fenbei.base.core.dialog.CommonDialog;
import com.miaomi.fenbei.base.dialog.ApplyDialog;
import com.miaomi.fenbei.base.net.Callback;
import com.miaomi.fenbei.base.net.NetService;
import com.miaomi.fenbei.base.util.ImgUtil;
import com.miaomi.fenbei.base.util.ToastUtil;
import com.miaomi.fenbei.base.widget.DCBTextView;
import com.miaomi.fenbei.voice.R;


import org.jetbrains.annotations.NotNull;

@Route(path = "/app/familyadminCenter")
public class FamilyAdminCenterActivity extends BaseActivity implements View.OnClickListener {


    public final static String FAMILY_ID = "FAMILY_ID";
    private FamilyCenterInfoBean mBean;
    private String familyId = "";
    private TextView familyLeaderNameTv;
    private ImageView backIv;
    private TextView familyName;
    private TextView moneyTotalTv;
    private ImageView iconIv;
    private TextView notiveTv;
    private DCBTextView totalRankTv;
    private DCBTextView monthRankTv;
    private TextView familyIdTv;
    private LinearLayout applylistLl;
    private TextView editTv,applynumTv;
    private DCBTextView membernumTv,roomnumTv;
    private TextView outfamilyTv;
    private LinearLayout generaLl,monthlyLl,memberLl,roomLl;
    private  TextView adminTv;
    private TextView proportionTv;
    public static void start(Context context,String familyId){
        Intent intent = new Intent(context, FamilyAdminCenterActivity.class);
        intent.putExtra(FAMILY_ID,familyId);
        context.startActivity(intent);
    }
    @Override
    public int getLayoutId() {
        return R.layout.activity_admin_family_center;
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
        backIv = findViewById(R.id.iv_back);
        familyName = findViewById(R.id.tv_family_name);
        familyLeaderNameTv = findViewById(R.id.tv_leader_name);
        moneyTotalTv = findViewById(R.id.tv_money_total);
        iconIv = findViewById(R.id.iv_icon);
        applynumTv=findViewById(R.id.tv_apply_num);
        notiveTv = findViewById(R.id.tv_notice);
        totalRankTv = findViewById(R.id.tv_total_rank);
        monthRankTv = findViewById(R.id.tv_month_rank);
        applylistLl=findViewById(R.id.ll_apply_list);
        familyIdTv=findViewById(R.id.tv_family_id);
        membernumTv=findViewById(R.id.tv_member_num);
        roomnumTv=findViewById(R.id.tv_room_num);
        editTv=findViewById(R.id.tv_edit);
        generaLl=findViewById(R.id.ll_general);
        monthlyLl=findViewById(R.id.ll_monthly);
        memberLl=findViewById(R.id.ll_member);
        roomLl=findViewById(R.id.ll_room);
        adminTv=findViewById(R.id.tv_admin);
        outfamilyTv=findViewById(R.id.tv_outfamily);
        proportionTv=findViewById(R.id.tv_proportion);
        adminTv.setOnClickListener(this);
        backIv.setOnClickListener(this);
        applylistLl.setOnClickListener(this);
        editTv.setOnClickListener(this);
        generaLl.setOnClickListener(this);
        monthlyLl.setOnClickListener(this);
        memberLl.setOnClickListener(this);
        roomLl.setOnClickListener(this);
        outfamilyTv.setOnClickListener(this);
        proportionTv.setOnClickListener(this);


    }

    private void bindData(){
        if (mBean.getHost_info().getFamily_type() == 2){
            outfamilyTv.setBackground(getResources().getDrawable(R.drawable.common_button_enabled_style));
            outfamilyTv.setText("退出家族");
            adminTv.setVisibility(View.GONE);
            proportionTv.setVisibility(View.GONE);
            outfamilyTv.setTextColor(Color.parseColor("#ED52F9"));
        }else if(mBean.getHost_info().getFamily_type() == 3){
            outfamilyTv.setBackground(getResources().getDrawable(R.drawable.common_button_enabled_style));
            outfamilyTv.setText("解散家族");
            outfamilyTv.setTextColor(Color.parseColor("#ED52F9"));
        }
        if (mBean.getHost_info().getJoin_type()== 1||mBean.getHost_info().getDisband_type()==1){

            if(mBean.getHost_info().getFamily_type()==2&&mBean.getHost_info().getJoin_type()== 1){
                outfamilyTv.setVisibility(View.VISIBLE);
                outfamilyTv.setTextColor(Color.parseColor("#FFFFFF"));
                outfamilyTv.setBackground(getResources().getDrawable(R.drawable.bg_out_family));
                outfamilyTv.setClickable(false);
                outfamilyTv.setText("等待审核中");
            }
            if(mBean.getHost_info().getFamily_type()==3&&mBean.getHost_info().getDisband_type()==1){
                outfamilyTv.setVisibility(View.VISIBLE);
                outfamilyTv.setTextColor(Color.parseColor("#FFFFFF"));
                outfamilyTv.setBackground(getResources().getDrawable(R.drawable.bg_out_family));
                outfamilyTv.setClickable(false);
                outfamilyTv.setText("解散家族申请已提交");
            }

        }

        if(mBean.getHost_info().getNotice_number()<=0){
            applynumTv.setVisibility(View.GONE);
        }else{
            applynumTv.setText(""+mBean.getHost_info().getNotice_number());
        }
        familyIdTv.setText(""+mBean.getHost_info().getFamily_id());
        membernumTv.setText(""+mBean.getHost_info().getManager_num());
        roomnumTv.setText(""+mBean.getHost_info().getRoom_num());
        familyName.setText(mBean.getHost_info().getFamily_name());
        familyLeaderNameTv.setText("ID："+mBean.getHost_info().getFamily_id()+"           族长："+mBean.getHost_info().getNickname());
        moneyTotalTv.setText(""+mBean.getHost_info().getMoney_total());
        if(mBean.getHost_info().getMonth_rank().equals("无排名")){

            monthRankTv.setText("未上榜");
        }else{
            monthRankTv.setText(""+mBean.getHost_info().getMonth_rank());
        }
        if(mBean.getHost_info().getTotal_rank().equals("无排名")){
            totalRankTv.setText("未上榜");
        }else{
            totalRankTv.setText(""+mBean.getHost_info().getTotal_rank());
        }

        ImgUtil.INSTANCE.loadCircleImg(this,mBean.getHost_info().getIcon(),iconIv,R.drawable.common_avter_placeholder);
//        if (TextUtils.isEmpty(mBean.getHost_info().getNote())){
////            notiveTv.setVisibility(View.INVISIBLE);
//            notiveTv.setText("公告:"+mBean.getHost_info().getNote());
//        }else{
////            notiveTv.setVisibility(View.VISIBLE);
//
//        }
        notiveTv.setText("公告:"+mBean.getHost_info().getNote());
        notiveTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CommonDialog dialog = new CommonDialog(FamilyAdminCenterActivity.this);
                dialog.setTitle("公告");
                dialog.setContent(mBean.getHost_info().getNote());
                dialog.setRightBt("确定", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
    }


    private void getData(){
        if (familyId == null){
            familyId = "";
        }
        NetService.Companion.getInstance(this).getFamilyInfo(familyId, new Callback<FamilyCenterInfoBean>() {
            @Override
            public void onSuccess(int nextPage, FamilyCenterInfoBean bean, int code) {
//                loadHelper.bindView(code);
                mBean = bean;
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

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.iv_back){
            finish();
        }
        if(id==R.id.tv_proportion){
            FamilyProportionActivity.start(this,familyId);
        }
        if(id==R.id.ll_apply_list){
            FamilyApplyListActivity.start(this,familyId);
        }
        if (id == R.id.tv_edit){
            EditFamilyActivity.start(this,String.valueOf(mBean.getHost_info().getFamily_id()),mBean.getHost_info().getFamily_name(),
                    mBean.getHost_info().getIcon(),mBean.getHost_info().getNote(),mBean.getHost_info().getNickname());
        }
        if(id==R.id.ll_general){
         FamilyListActivity.start(this,false,0);
        }
        if(id==R.id.ll_monthly){
            FamilyListActivity.start(this,false,1);
        }
        if(id==R.id.ll_member){
            FamilyMemberActivity.start(this,familyId,mBean.getHost_info().getFamily_type());

        }
        if(id==R.id.ll_room){
            FamilyHomeActivity.start(this,familyId,mBean.getHost_info().getFamily_type());
        }

        if(id==R.id.tv_admin){
           FamilyRemoreAdminActivity.start(this,familyId,mBean.getHost_info().getFamily_type());
        }
        if(id==R.id.tv_outfamily){
            if (mBean.getHost_info().getFamily_type() == 3){
                String fId=familyIdTv.getText().toString();
                final ApplyDialog outDialog = new ApplyDialog(FamilyAdminCenterActivity.this);
//                outDialog.setTitle("提示");
                outDialog.setOutIcon("3");
                outDialog.setMsg("您的申请将会由后台审核，通过后方可解散");
                outDialog.setContent("是否确认解散家族?");
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
                        dissolutionFamily(fId);
                    }
                });
                outDialog.show();

            }else if(mBean.getHost_info().getFamily_type() == 2){
                final ApplyDialog outDialog = new ApplyDialog(FamilyAdminCenterActivity.this);
//                outDialog.setTitle("提示");
                outDialog.setOutIcon("1");
                outDialog.setContent("您确定要退出家族么？");
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
                        outFamily();
                    }
                });
                outDialog.show();

            }
        }
    }


    private void outFamily(){
        NetService.Companion.getInstance(this).outFamilyApply( new Callback<Object>() {
            @Override
            public void onSuccess(int nextPage, Object bean, int code) {

                outfamilyTv.setTextColor(Color.parseColor("#FFFFFF"));
                outfamilyTv.setBackground(getResources().getDrawable(R.drawable.bg_out_family));
                outfamilyTv.setText("等待审核中");
                outfamilyTv.setClickable(false);
            }
            @Override
            public void onError(@NotNull String msg, @NotNull Throwable throwable, int code) {
                ToastUtil.INSTANCE.error(FamilyAdminCenterActivity.this,msg);
            }

            @Override
            public boolean isAlive() {
                return isLive();
            }
        });
    }

    private void dissolutionFamily(String fId){
        NetService.Companion.getInstance(this).dissolutionFamilyApply(fId, new Callback<Object>() {
            @Override
            public void onSuccess(int nextPage, Object bean, int code) {

                outfamilyTv.setTextColor(Color.parseColor("#FFFFFF"));
                outfamilyTv.setBackground(getResources().getDrawable(R.drawable.bg_out_family));
                outfamilyTv.setText("解散家族申请已提交");
                outfamilyTv.setClickable(false);
            }
            @Override
            public void onError(@NotNull String msg, @NotNull Throwable throwable, int code) {
                ToastUtil.INSTANCE.error(FamilyAdminCenterActivity.this,msg);
            }

            @Override
            public boolean isAlive() {
                return isLive();
            }
        });
    }

    private void shareFamily(){
//        Share.INSTANCE.wxShare(this,mBean.getHost_info().getFamily_name(), DataHelper.INSTANCE.getLoginToken());
    }
}
