//package com.miaomi.fenbei.voice.ui;
//
//import android.content.Context;
//import android.content.Intent;
//
//import com.example.indicatorlib.base.BaseFragmentAdapter;
//import com.miaomi.fenbei.voice.BaseActivity;
//import com.miaomi.fenbei.base.widget.KMPartyRoomIndicator;
//import com.miaomi.fenbei.voice.R;
//import com.miaomi.fenbei.voice.ui.main.fragment.home.AllHotRoomFragment;
//import com.miaomi.fenbei.voice.ui.main.fragment.home.RecommandOtherTypeRoomFragment;
//import com.miaomi.fenbei.voice.ui.main.fragment.home.AllOtherTypeRoomFragment;
//
//import java.util.ArrayList;
//
//import androidx.annotation.NonNull;
//import androidx.fragment.app.Fragment;
//import androidx.viewpager.widget.ViewPager;
//
//public class AllRoomActivity extends BaseActivity {
//
//    private ViewPager mViewPager;
//    private KMPartyRoomIndicator mTabLayout;
//    ArrayList<Fragment> mTabList = new ArrayList<>();
//
//
//    public static void start(@NonNull Context context) {
//        final Intent intent = new Intent(context, AllRoomActivity.class);
//        context.startActivity(intent);
//    }
//
//    @Override
//    public int getLayoutId() {
//        return R.layout.activity_all_room;
//    }
//
//    @Override
//    public void initView() {
//        mViewPager = findViewById(R.id.vp_room);
//        mTabLayout = findViewById(R.id.nts_top);
//        mTabList.add(AllHotRoomFragment.newInstance());
//        mTabList.add(AllOtherTypeRoomFragment.newInstance(RecommandOtherTypeRoomFragment.ROOM_TYPE_XNNY_FEMALE));
//        mTabList.add(AllOtherTypeRoomFragment.newInstance(RecommandOtherTypeRoomFragment.ROOM_TYPE_XNNY_MAN));
//        mTabList.add(AllOtherTypeRoomFragment.newInstance(RecommandOtherTypeRoomFragment.ROOM_TYPE_QG));
//        mTabList.add(AllOtherTypeRoomFragment.newInstance(RecommandOtherTypeRoomFragment.ROOM_TYPE_JY));
//        mViewPager.setOffscreenPageLimit(mTabList.size());
//
//        BaseFragmentAdapter adapter = new BaseFragmentAdapter(getSupportFragmentManager(),mTabList);
//        mViewPager.setAdapter(adapter);
//        mTabLayout.setViewPager(mViewPager);
//    }
//
//}
