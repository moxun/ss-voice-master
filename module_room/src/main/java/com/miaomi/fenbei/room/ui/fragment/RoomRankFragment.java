package com.miaomi.fenbei.room.ui.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.miaomi.fenbei.base.util.DensityUtil;
import com.miaomi.fenbei.base.widget.ScaleTransitionPagerTitleView;
import com.miaomi.fenbei.room.R;
import com.miaomi.fenbei.base.core.BaseFragment;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class RoomRankFragment extends BaseFragment {

    public final static String ROOM_ID = "room_id";
    public final static String TAB_TYPE = "TAB_TYPE";

//    public final static String RANK_TYPE = "RANK_TYPE";

//    public final static int RANK_TYPE_RADIO_GUARD = 1;
//    public final static int RANK_TYPE_RADIO_CHARM = 2;

    @Override
    public int getLayoutId() {
        return R.layout.room_fragment_room_rank;
    }

    private ViewPager mViewPager;
    private ImageView rankBgIv;
    private List<Fragment> mFragmentList = new ArrayList<>();
    private String[] tabNames = new String[]{"魅力榜","财富榜"};
    private String roomId;
    private int tabType;
    private MagicIndicator mTitleTab;
    //    private int rankRoomType = 0;
    private FrameLayout toolbarFl;

    public static RoomRankFragment newInstance(String roomId, int type){
        RoomRankFragment fragment = new RoomRankFragment();
        Bundle args = new Bundle();
        args.putString(ROOM_ID,roomId);
        args.putInt(TAB_TYPE, type);
//        args.putInt(RANK_TYPE,rankRoomType);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void initView(@NotNull View view) {
        roomId = getArguments().getString(ROOM_ID);
        tabType = getArguments().getInt(TAB_TYPE);
//        rankRoomType = getArguments().getInt(RANK_TYPE);
        rankBgIv = view.findViewById(R.id.rank_bg_iv);
        mViewPager = view.findViewById(R.id.vp_rank);
        mTitleTab = view.findViewById(R.id.tab_layout);
        toolbarFl = view.findViewById(R.id.fl_toolbar);
        initViewPager();
        initTitleTab();
        ViewPagerHelper.bind(mTitleTab, mViewPager);
        mViewPager.setCurrentItem(tabType);
    }

    private void initTitleTab() {
        CommonNavigator commonNavigator =new CommonNavigator(getContext());
        CommonNavigatorAdapter adpter = new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return tabNames.length;
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                ScaleTransitionPagerTitleView simplePagerTitleView =new ScaleTransitionPagerTitleView(context);
                simplePagerTitleView.setText(tabNames[index]);
                simplePagerTitleView.setTextSize(15f);
//                simplePagerTitleView.setPadding(DensityUtil.INSTANCE.dp2px(context,4f),0,DensityUtil.INSTANCE.dp2px(context,14f),0);
                simplePagerTitleView.setNormalColor(Color.parseColor("#ffffff"));


                simplePagerTitleView.setSelectedColor(Color.parseColor("#ffffff"));
                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mViewPager.setCurrentItem(index);
                    }
                });
                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
//                LinePagerIndicator indicator =new LinePagerIndicator(context);
//                float navigatorHeight = DensityUtil.INSTANCE.dp2px(context, 3f);
//                indicator.setLineHeight(navigatorHeight);
//                indicator.setLineWidth(DensityUtil.INSTANCE.dp2px(context, 12f));
//                indicator.setRoundRadius(DensityUtil.INSTANCE.dp2px(context, 1.5f));
//                indicator.setMode(LinePagerIndicator.MODE_EXACTLY);
//                indicator.setColors(Color.parseColor("#ffffff"));
                LinePagerIndicator indicator =new LinePagerIndicator(context);
                float navigatorHeight = DensityUtil.INSTANCE.dp2px(context, 37f);
                indicator.setLineHeight(navigatorHeight);
//                indicator.setLineWidth(DensityUtil.INSTANCE.dp2px(context, 20f));
                indicator.setRoundRadius(navigatorHeight / 2);
                indicator.setMode(LinePagerIndicator.MODE_MATCH_EDGE);
                indicator.setColors(Color.parseColor("#7FFFFFFF"));
                return indicator;
            }
        };
        commonNavigator.setAdapter(adpter);
        commonNavigator.setAdjustMode(true);
        mTitleTab.setNavigator(commonNavigator);
    }

    private void initViewPager() {
        mFragmentList.add(RoomRankTypeFragment.newInstance(RoomRankListFragment.RANK_TYPE_ML));
        mFragmentList.add(RoomRankTypeFragment.newInstance(RoomRankListFragment.RANK_TYPE_GX));
        mViewPager.setAdapter(new MyViewPagerAdapter(getFragmentManager()));
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }
            @Override
            public void onPageSelected(int position) {
                if (position == 0){
                    toolbarFl.setSelected(false);
                    rankBgIv.setBackgroundResource(R.drawable.room_rank_charm_bg);
                }else {

                    toolbarFl.setSelected(true);
                    rankBgIv.setBackgroundResource(R.drawable.room_rank_wealth_bg);
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
