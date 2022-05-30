package com.miaomi.fenbei.voice.ui.family;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.miaomi.fenbei.base.bean.FamilyCenterInfoBean;
import com.miaomi.fenbei.base.core.BaseActivity;
import com.miaomi.fenbei.base.core.dialog.CommonDialog;
import com.miaomi.fenbei.base.dialog.ApplyDialog;
import com.miaomi.fenbei.base.net.Callback;
import com.miaomi.fenbei.base.net.NetService;
import com.miaomi.fenbei.base.util.ImgUtil;
import com.miaomi.fenbei.base.util.LoadHelper;
import com.miaomi.fenbei.base.util.TextFontUtils;
import com.miaomi.fenbei.base.util.ToastUtil;
import com.miaomi.fenbei.base.widget.DCBTextView;
import com.miaomi.fenbei.voice.R;


import org.jetbrains.annotations.NotNull;

@Route(path = "/app/familyCenter")
public class FamilyCenterActivity extends BaseActivity implements View.OnClickListener {

    private String[] tabNames = new String[]{"家族房间"};

    private ImageView backIv;
    private ImageView outIv;
    TextView applyListTv;
    LinearLayout familyMemberLL;
    TextView inviteTv;
    ImageView editIv;

    public final static String FAMILY_ID = "FAMILY_ID";
    private String familyId = "";
    private FamilyRoomFragment mFamilyRoomFragment;
    private FamilyCenterMemberAdapter mFamilyCenterMemberAdapter;
    private RecyclerView memberRv;
    private TextView memberTv;
    private TextView familyName;
    private TextView familyLeaderNameTv;
    private TextView moneyTotalTv;
    private TextView notiveTv;
    private ImageView iconIv;
    private DCBTextView totalRankTv;
    private DCBTextView monthRankTv;
    private TextView applyTv;
    private LinearLayout rootLL;

    private TextView familyIdTv;

    private TextView familyhomeTv;
    private LoadHelper loadHelper;
    private FamilyCenterInfoBean mBean;
    private LinearLayout memberLL;
    private LinearLayout topLL;
    RecyclerView mFamilyRoomRv;
    private FamilyRoomAdapter mFamilyRoomAdapter;
    public static void start(Context context,String familyId){
        Intent intent = new Intent(context, FamilyCenterActivity.class);
        intent.putExtra(FAMILY_ID,familyId);
        context.startActivity(intent);
    }
    @Override
    public int getLayoutId() {

        return R.layout.activity_family_center;
    }

    @Override
    protected void onResume() {
        super.onResume();
        getData();
    }

    @Override
    public void initView() {
        familyId = getIntent().getStringExtra(FAMILY_ID);
        mFamilyRoomAdapter = new FamilyRoomAdapter(this);
        mFamilyRoomRv = findViewById(R.id.rv_family_room);
        backIv = findViewById(R.id.iv_back);
        inviteTv = findViewById(R.id.tv_invite);
        topLL = findViewById(R.id.ll_top);
        applyListTv = findViewById(R.id.tv_apply_list);
        familyMemberLL = findViewById(R.id.ll_family_member);
        editIv = findViewById(R.id.iv_edit);
        outIv = findViewById(R.id.iv_out);
        memberRv = findViewById(R.id.rv_member);
        memberTv = findViewById(R.id.tv_member);
        familyName = findViewById(R.id.tv_family_name);
        familyLeaderNameTv = findViewById(R.id.tv_leader_name);
        moneyTotalTv = findViewById(R.id.tv_money_total);
        iconIv = findViewById(R.id.iv_icon);
        notiveTv = findViewById(R.id.tv_notice);
        totalRankTv = findViewById(R.id.tv_total_rank);
        monthRankTv = findViewById(R.id.tv_month_rank);
        applyTv = findViewById(R.id.tv_apply);
        rootLL = findViewById(R.id.ll_root);
        memberLL = findViewById(R.id.member_ll);
        familyIdTv=findViewById(R.id.tv_family_id);

       familyhomeTv=findViewById(R.id.tv_family_home);
        mFamilyRoomRv.setLayoutManager(new LinearLayoutManager(this));
        mFamilyRoomRv.setAdapter(mFamilyRoomAdapter);
        familyhomeTv.setOnClickListener(this);

        monthRankTv.setOnClickListener(this);
        totalRankTv.setOnClickListener(this);
        backIv.setOnClickListener(this);
        editIv.setOnClickListener(this);
        inviteTv.setOnClickListener(this);
        applyListTv.setOnClickListener(this);
        familyMemberLL.setOnClickListener(this);
        outIv.setOnClickListener(this);
        applyTv.setOnClickListener(this);

        memberRv.setLayoutManager(new GridLayoutManager(this,4));
        mFamilyCenterMemberAdapter = new FamilyCenterMemberAdapter(this);
        memberRv.setAdapter(mFamilyCenterMemberAdapter);
        loadHelper = new LoadHelper();
        loadHelper.registerLoad(rootLL);
    }

    private void bindData(){
        if (mBean.getHost_info().getFamily_type() == 4){
            applyTv.setVisibility(View.GONE);
            outIv.setVisibility(View.GONE);

        }
        if (mBean.getHost_info().getFamily_type() == 0){
            applyTv.setVisibility(View.VISIBLE);
            outIv.setVisibility(View.GONE);

        }
        if (mBean.getHost_info().getJoin_type()== 1||mBean.getHost_info().getJoin_status()==1){
            applyTv.setVisibility(View.VISIBLE);
            applyTv.setTextColor(Color.parseColor("#FFFFFF"));
            applyTv.setBackground(getResources().getDrawable(R.drawable.bg_out_family));
            applyTv.setText("等待审核中");
            applyTv.setClickable(false);

        }
        if (mBean.getHost_info().getFamily_type() == 5){
            applyTv.setVisibility(View.VISIBLE);
            outIv.setVisibility(View.GONE);
            applyTv.setTextColor(Color.parseColor("#FFFFFF"));
            applyTv.setBackground(getResources().getDrawable(R.drawable.bg_out_family));
            applyTv.setText("已申请");
            applyTv.setClickable(false);

        }
        if (mBean.getHost_info().getFamily_type() == 1){
            applyTv.setVisibility(View.VISIBLE);
            outIv.setVisibility(View.GONE);
            applyTv.setTextColor(Color.parseColor("#FFFFFF"));
            applyTv.setBackground(getResources().getDrawable(R.drawable.common_button_enabled_style_new));
            applyTv.setText("申请退出");
            applyTv.setClickable(true);

        }
        if(mBean.getRoom_info().size()<=0){
            familyhomeTv.setVisibility(View.INVISIBLE);
        }else{
            familyhomeTv.setVisibility(View.VISIBLE);
        }
        if (mFamilyRoomAdapter != null && mBean.getRoom_info() != null){

            mFamilyRoomAdapter.setData(mBean.getRoom_info());
        }

        mFamilyCenterMemberAdapter.setData(mBean.getFamily_info());
        familyIdTv.setText(""+mBean.getHost_info().getFamily_id());
        familyhomeTv.setText("家族房间("+mBean.getHost_info().getRoom_num()+")");
        memberTv.setText("家族人员（"+mBean.getHost_info().getMember()+"）");
        familyName.setText(mBean.getHost_info().getFamily_name());
        familyLeaderNameTv.setText("ID："+mBean.getHost_info().getFamily_id()+"           族长："+mBean.getHost_info().getNickname());
        moneyTotalTv.setText(""+mBean.getHost_info().getMoney_total());
        if(mBean.getHost_info().getMonth_rank().equals("无排名")){
            monthRankTv.setText("月榜未上榜");
        }else{
            TextFontUtils.setHighlightFont(mBean.getHost_info().getMonth_rank(),"月榜第 "+mBean.getHost_info().getMonth_rank()+" 名",monthRankTv);
        }
        if(mBean.getHost_info().getTotal_rank().equals("无排名")){
            totalRankTv.setText("总榜未上榜");

        }else{
            TextFontUtils.setHighlightFont(mBean.getHost_info().getTotal_rank(),"总榜第 "+mBean.getHost_info().getTotal_rank()+" 名",totalRankTv);

        }

        ImgUtil.INSTANCE.loadCircleImg(this,mBean.getHost_info().getIcon(),iconIv,R.drawable.common_avter_placeholder);
        if (TextUtils.isEmpty(mBean.getHost_info().getNote())){
//            notiveTv.setVisibility(View.INVISIBLE);
            notiveTv.setText("公告: ");
        }else{
//            notiveTv.setVisibility(View.VISIBLE);
            notiveTv.setText("公告: "+mBean.getHost_info().getNote());
        }
        notiveTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CommonDialog dialog = new CommonDialog(FamilyCenterActivity.this);
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
                loadHelper.bindView(code);
                mBean = bean;
                bindData();
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

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id==R.id.tv_family_home){
            FamilyHomeActivity.start(this,familyId,mBean.getHost_info().getFamily_type());
        }
        if (id == R.id.tv_apply_list){
            FamilyApplyListActivity.start(this,familyId);
        }

        if (id == R.id.ll_family_member){
            FamilyMemberActivity.start(this,familyId,mBean.getHost_info().getFamily_type());
        }
        if (id == R.id.tv_invite){
//            shareFamily();
        }
        if (id == R.id.iv_edit){
//            EditFamilyActivity.start(this,String.valueOf(mBean.getHost_info().getFamily_id()),mBean.getHost_info().getFamily_name(),
//                    mBean.getHost_info().getIcon(),mBean.getHost_info().getNote());
        }
        if (id == R.id.iv_back){
            finish();
        }

        if (id == R.id.tv_apply){
            if (mBean.getHost_info().getFamily_type() == 0){
                final ApplyDialog outDialog = new ApplyDialog(FamilyCenterActivity.this);
                outDialog.setContent("是否确认加入家族？");
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
                        joinFamily();
                    }
                });
                outDialog.show();
            }
            if (mBean.getHost_info().getFamily_type() == 1){
                final ApplyDialog outDialog = new ApplyDialog(FamilyCenterActivity.this);
                outDialog.setContent("是否确认退出家族？");
                outDialog.setOutIcon("1");
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
        if (id == R.id.tv_month_rank){
            if (mBean.getHost_info().getFamily_type() == 4){
                finish();
            }else{
                FamilyListActivity.start(this,false,1);
            }
        }
        if (id == R.id.tv_total_rank){
            if (mBean.getHost_info().getFamily_type() == 4){
                finish();
            }else{
                FamilyListActivity.start(this,false,0);
            }
        }
    }


    private void outFamily(){
//        String.valueOf(mBean.getHost_info().getFamily_type()),
        NetService.Companion.getInstance(this).outFamilyApply( new Callback<Object>() {
            @Override
            public void onSuccess(int nextPage, Object bean, int code) {
                applyTv.setTextColor(Color.parseColor("#FFFFFF"));
                applyTv.setBackground(getResources().getDrawable(R.drawable.bg_out_family));
                applyTv.setText("等待审核中");
                applyTv.setClickable(false);
                ToastUtil.INSTANCE.i(FamilyCenterActivity.this,"申请已提交，请等待审核");
            }

            @Override
            public void onError(@NotNull String msg, @NotNull Throwable throwable, int code) {
                ToastUtil.INSTANCE.error(FamilyCenterActivity.this,msg);
            }

            @Override
            public boolean isAlive() {
                return isLive();
            }
        });
    }

    private void joinFamily(){
        NetService.Companion.getInstance(this).joinFamily(familyId, new Callback<Object>() {
            @Override
            public void onSuccess(int nextPage, Object bean, int code) {
                applyTv.setTextColor(Color.parseColor("#FFFFFF"));
                applyTv.setBackground(getResources().getDrawable(R.drawable.bg_out_family));
                applyTv.setText("已申请");
                applyTv.setClickable(false);
                ToastUtil.INSTANCE.i(FamilyCenterActivity.this,"您的加入申请已提交，请耐心等待");
            }

            @Override
            public void onError(@NotNull String msg, @NotNull Throwable throwable, int code) {
                ToastUtil.INSTANCE.i(FamilyCenterActivity.this,msg);
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
