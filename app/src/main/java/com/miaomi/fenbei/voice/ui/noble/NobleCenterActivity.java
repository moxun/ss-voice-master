package com.miaomi.fenbei.voice.ui.noble;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.google.android.material.tabs.TabLayout;
import com.miaomi.fenbei.base.bean.NobleBean;
import com.miaomi.fenbei.base.core.BaseActivity;
import com.miaomi.fenbei.base.net.Callback;
import com.miaomi.fenbei.base.net.NetService;
import com.miaomi.fenbei.base.util.DataHelper;
import com.miaomi.fenbei.base.util.RouterUrl;
import com.miaomi.fenbei.base.util.ToastUtil;
import com.miaomi.fenbei.base.web.WebActivity;
import com.miaomi.fenbei.voice.R;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@Route(path = RouterUrl.nobleCenter)
public class NobleCenterActivity extends BaseActivity {

    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private MyViewPagerAdapter myViewPagerAdapter;
    private ImageView explainTv;
    List<NobleBean> mNobleList = new ArrayList<>();
    public final static String TAG_RANK_ID = "TAG_RANK_ID";
    private int rankId;

//    public static void start(Context context){
//        Intent intent = new Intent(context,NobleCenterActivity.class);
//        context.startActivity(intent);
//    }

    public static void start(Context context,int rankId){
        Intent intent = new Intent(context,NobleCenterActivity.class);
        intent.putExtra(TAG_RANK_ID,rankId);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_noble_center;
    }

    @Override
    public void initView() {
        setBaseStatusBar(false, false);
        rankId = getIntent().getIntExtra(TAG_RANK_ID,0);
        mViewPager = findViewById(R.id.view_pager);
        mTabLayout = findViewById(R.id.tabLayout);
        explainTv = findViewById(R.id.tv_noble_explain);
        myViewPagerAdapter = new MyViewPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(myViewPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        explainTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebActivity.start(NobleCenterActivity.this, DataHelper.INSTANCE.getIMDevelop().getNew_main()+"/html/activity/rule_noble","贵族说明");
            }
        });
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                TextView textView = new TextView(NobleCenterActivity.this);
                float selectedSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, 16, getResources().getDisplayMetrics());
                textView.setGravity(Gravity.CENTER);
                textView.setTextSize(TypedValue.COMPLEX_UNIT_SP,selectedSize);
                textView.setText(tab.getText());
                textView.setTextColor(Color.parseColor("#FFEBCA"));
                tab.setCustomView(textView);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tab.setCustomView(null);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        getData();
    }

    static class MyViewPagerAdapter extends FragmentPagerAdapter {
        List<NobleBean> mList = new ArrayList<>();
        public MyViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public void setList(List<NobleBean> list) {
            mList.clear();
            mList.addAll(list);
            notifyDataSetChanged();
        }

        @Override
        public Fragment getItem(int position) {
            return NobleCenterFragment.newInstance(position);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return mList.get(position).getName();
        }

        @Override
        public int getCount() {
            return mList.size();
        }
    }

    private void getData(){
        NetService.Companion.getInstance(this).getNobleInfo(new Callback<List<NobleBean>>() {
            @Override
            public void onSuccess(int nextPage, List<NobleBean> bean, int code) {
//                List<NobleBean> newNobleList = new ArrayList<>();
//                for (int i =0;i<6;i++){
//                    NobleBean bean1 = new NobleBean();
//                    bean1.setName("国王");
//                    newNobleList.add(bean1);
//                }
                mNobleList = bean;
                myViewPagerAdapter.setList(mNobleList);
                if (rankId >=0){
                    int currentId = 0;
                    for (int i = 0;i<bean.size();i++){
                        if (mNobleList.get(i).getId() == rankId){
                            currentId = i;
                        }
                    }
                    mViewPager.setCurrentItem(currentId);
                }else{
                    mViewPager.setCurrentItem(mNobleList.size() - 1);
                }
            }

            @Override
            public void onError(@NotNull String msg, @NotNull Throwable throwable, int code) {
                ToastUtil.INSTANCE.error(NobleCenterActivity.this,msg);
            }

            @Override
            public boolean isAlive() {
                return isLive();
            }
        });
    }
}
