package com.miaomi.fenbei.voice.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.miaomi.fenbei.base.core.BaseActivity;
import com.miaomi.fenbei.base.util.DensityUtil;
import com.miaomi.fenbei.base.widget.ScaleTransitionPagerTitleView;
import com.miaomi.fenbei.voice.R;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;

public class RankActivity extends BaseActivity {

    private ImageView rankBgIv;
    private ViewPager mViewPager;
    private List<Fragment> mFragmentList = new ArrayList<>();
    private MagicIndicator mTablayout;
    private FrameLayout toolbarFl;
    private RankTypeFragment mlRankListFragment;
    private RankTypeFragment gxRankListFragment;
    private RankRoomTypeFragment roomRankListFragment;
    private String[] tabNames = new String[]{"魅力榜", "财富榜","房间榜"};
    private int position;
    public final static int TAB_POSITION_ML = 0;
    public final static int TAB_POSITION_CF = 1;


    public static void start(Context context, int position) {
        Intent intent = new Intent(context, RankActivity.class);
        intent.putExtra("position", position);
        context.startActivity(intent);
    }


    @Override
    public int getLayoutId() {
        return R.layout.main_fragment_rank;
    }

    @Override
    public void initView() {
        setBaseStatusBar(false, false);
        position = getIntent().getIntExtra("position", TAB_POSITION_ML);
        rankBgIv = findViewById(R.id.rank_bg_iv);
        mTablayout = findViewById(R.id.tab_layout);
        mViewPager = findViewById(R.id.vp_rank);
        toolbarFl = findViewById(R.id.fl_toolbar);
        mlRankListFragment = RankTypeFragment.newInstance(RankTypeFragment.RANK_TYPE_ML);
        gxRankListFragment = RankTypeFragment.newInstance(RankTypeFragment.RANK_TYPE_GX);
        roomRankListFragment= RankRoomTypeFragment.newInstance(RankTypeFragment.RANK_TYPE_ROOM);
        initTitleTab();
        initContentVp();
        ViewPagerHelper.bind(mTablayout, mViewPager);
        mViewPager.setCurrentItem(position);
    }

    private void initTitleTab() {
        CommonNavigator commonNavigator = new CommonNavigator(this);
        CommonNavigatorAdapter adpter = new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return tabNames.length;
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                ScaleTransitionPagerTitleView simplePagerTitleView = new ScaleTransitionPagerTitleView(context);
                simplePagerTitleView.setText(tabNames[index]);
                simplePagerTitleView.setTextSize(18f);
//                simplePagerTitleView.setPadding(DensityUtil.INSTANCE.dp2px(context,4f),0,DensityUtil.INSTANCE.dp2px(context,14f),0);
                simplePagerTitleView.setNormalColor(Color.parseColor("#ffffff"));
                simplePagerTitleView.setSelectedColor(Color.parseColor("#ffffff"));
                simplePagerTitleView.setOnClickListener(v -> mViewPager.setCurrentItem(index));
                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                float navigatorHeight = DensityUtil.INSTANCE.dp2px(context, 3f);
                indicator.setLineHeight(navigatorHeight);
                indicator.setLineWidth(DensityUtil.INSTANCE.dp2px(context, 12f));
                indicator.setRoundRadius(DensityUtil.INSTANCE.dp2px(context, 1.5f));
                indicator.setMode(LinePagerIndicator.MODE_EXACTLY);
                indicator.setColors(Color.parseColor("#ffffff"));
                return indicator;
            }
        };
        commonNavigator.setAdapter(adpter);
        commonNavigator.setAdjustMode(true);
        mTablayout.setNavigator(commonNavigator);
    }

    private void initContentVp() {
        mFragmentList.add(mlRankListFragment);
        mFragmentList.add(gxRankListFragment);
        mFragmentList.add(roomRankListFragment);
        mViewPager.setOffscreenPageLimit(mFragmentList.size());
        mViewPager.setAdapter(new MyViewPagerAdapter(getSupportFragmentManager()));
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    toolbarFl.setSelected(false);
                    rankBgIv.setBackgroundResource(R.drawable.room_rank_charm_bg);
                } else if (position == 1) {
                    toolbarFl.setSelected(true);
                    rankBgIv.setBackgroundResource(R.drawable.room_rank_wealth_bg);
                }
                else{
                    toolbarFl.setSelected(true);
                    rankBgIv.setBackgroundResource(R.drawable.home_rank_room_bg);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

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
