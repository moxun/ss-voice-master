package com.miaomi.fenbei.voice.ui.family;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.miaomi.fenbei.base.bean.FamilyIdBean;
import com.miaomi.fenbei.base.core.BaseActivity;
import com.miaomi.fenbei.base.core.dialog.CommonDialog;
import com.miaomi.fenbei.base.net.Callback;
import com.miaomi.fenbei.base.net.NetService;
import com.miaomi.fenbei.base.util.DataHelper;
import com.miaomi.fenbei.base.util.ImgUtil;
import com.miaomi.fenbei.base.util.RouterUrl;
import com.miaomi.fenbei.base.util.ToastUtil;
import com.miaomi.fenbei.base.widget.QYXFamilyRankIndicator;
import com.miaomi.fenbei.voice.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import org.jetbrains.annotations.NotNull;

@Route(path = "/app/familyList")
public class FamilyListActivity extends BaseActivity implements View.OnClickListener {

    private String[] tabNames = new String[]{"家族月榜","家族总榜"};
    private List<Fragment> mFragmentList = new ArrayList<>();
    private QYXFamilyRankIndicator mTablayout;
    private ViewPager mViewPager;

    private ImageView backIv;
    private TextView creatFamilyTv;
    private ImageView searchbtnIv;
    private TextView meFamilyTv;

    private boolean canCreate;
    private final static String CAN_CREATE= "CAN_CREATE";
    private final static String FRAGMENT_INDEX= "FRAGMENT_INDEX";
    private int index;
    private  int familyrole=0;
    private String family_id;
    private ImageView familyIv;
    public static void start(Context context,boolean canCreate,int index){
        Intent intent = new Intent(context,FamilyListActivity.class);
        intent.putExtra(CAN_CREATE,canCreate);
        intent.putExtra(FRAGMENT_INDEX,index);
        context.startActivity(intent);
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_family_list;
    }

    @Override
    protected void onResume() {
        super.onResume();
        isInFamily();
    }

    @Override
    public void initView() {
        setBaseStatusBar(false,false);
        index = getIntent().getIntExtra(FRAGMENT_INDEX,0);
        canCreate = getIntent().getBooleanExtra(CAN_CREATE,false);
        mFragmentList.add(FamilyListFragment.newInstance(0));
        mFragmentList.add(FamilyListFragment.newInstance(1));
        mTablayout = findViewById(R.id.tl_family);
        mViewPager = findViewById(R.id.vp_family);
        searchbtnIv=findViewById(R.id.iv_search_btn);
        backIv = findViewById(R.id.iv_back);
        creatFamilyTv = findViewById(R.id.tv_creat_family);
        meFamilyTv= findViewById(R.id.tv_me_family);
        familyIv=findViewById(R.id.iv_family);
        searchbtnIv.setOnClickListener(this);
        backIv.setOnClickListener(this);
        meFamilyTv.setOnClickListener(this);
        creatFamilyTv.setOnClickListener(this);
        mViewPager.setAdapter(new MyViewPagerAdapter(getSupportFragmentManager()));
        mTablayout.setViewPager(mViewPager);

        if (canCreate){
            creatFamilyTv.setVisibility(View.VISIBLE);
        }else{
            creatFamilyTv.setVisibility(View.GONE);
        }
        mViewPager.setCurrentItem(index);
    }
    CommonDialog mCommonDialog;
    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.iv_search_btn){
            FamilySearchActivity.start(this);
        }
        if (id == R.id.iv_back){
            finish();
        }
        if(id==R.id.tv_me_family){
            if (familyrole==0) {
                FamilyMeActivity.start(getBaseContext());
            }else{
                if(familyrole==1||familyrole==2){
                    FamilyAdminCenterActivity.start(FamilyListActivity.this,String.valueOf(family_id));
                }else{
                    FamilyCenterActivity.start(FamilyListActivity.this,String.valueOf(family_id));
                }
            }

        }
        if (id == R.id.tv_creat_family){
            if (DataHelper.INSTANCE.getUserInfo().getIdentify_status() == 2){
                CreateFamilyActivity.start(this);
            }else{
                ARouter.getInstance().build(RouterUrl.notcertified).navigation();
//                mCommonDialog = new CommonDialog(this);
//                mCommonDialog.setTitle("提示");
//                mCommonDialog.setContent("创建家族需先完成实名认证及手机绑定");
//                mCommonDialog.setLeftBt("取消", new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        mCommonDialog.dismiss();
//                    }
//                });
//                mCommonDialog.setRightBt("去绑定", new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        mCommonDialog.dismiss();
//                        finish();
//                    }
//                });
//                mCommonDialog.show();
            }
        }
    }
    private void isInFamily(){
        NetService.Companion.getInstance(getBaseContext()).getFamilyId(new Callback<FamilyIdBean>() {
            @Override
            public void onSuccess(int nextPage, FamilyIdBean bean, int code) {
                familyrole = bean.getRole();

                if (familyrole==0) {
                    creatFamilyTv.setVisibility(View.VISIBLE);
                    family_id=bean.getFamily_id();
                    familyIv.setBackground(getBaseContext().getResources().getDrawable(R.drawable.f_icon_me));
                }else{
                    creatFamilyTv.setVisibility(View.GONE);
                    family_id=bean.getFamily_id();
                    ImgUtil.INSTANCE.loadFaceIcon(FamilyListActivity.this, bean.getFamily_face(), familyIv);

                }

//
            }

            @Override
            public void onError(@NotNull String msg, @NotNull Throwable throwable, int code) {
                ToastUtil.INSTANCE.i(getBaseContext(), msg);
            }

            @Override
            public boolean isAlive() {
                return isLive();
            }
        });
    }
    public class MyViewPagerAdapter extends FragmentPagerAdapter {

        public MyViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return tabNames[position];
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }
    }
}
