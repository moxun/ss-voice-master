//package com.miaomi.fenbei.voice.ui.main.fragment;
//
//import android.view.View;
//
//import com.example.indicatorlib.base.BaseFragmentAdapter;
//import com.miaomi.fenbei.base.bean.CallOnBean;
//import com.miaomi.fenbei.base.bean.PreviewBean;
//import com.miaomi.fenbei.voice.BaseFragment;
//import com.miaomi.fenbei.voice.Callback;
//import com.miaomi.fenbei.voice.NetService;
//import com.miaomi.fenbei.base.widget.KMHomeIndicator;
//import com.miaomi.fenbei.voice.R;
//import com.miaomi.fenbei.voice.ui.main.fragment.home.AllOtherTypeRoomFragment;
//import com.miaomi.fenbei.voice.ui.main.fragment.home.HomeOtherRoomFragment;
//import com.miaomi.fenbei.voice.ui.main.fragment.home.RecommandOtherTypeRoomFragment;
//import com.miaomi.fenbei.voice.ui.search.SearchActivity;
//
//import org.jetbrains.annotations.NotNull;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import androidx.fragment.app.Fragment;
//import androidx.viewpager.widget.ViewPager;
//
//public class HomePageFragment extends BaseFragment {
//
//    private KMHomeIndicator tabLyout;
//    private ViewPager viewPager;
//    private List<Fragment> mTabList = new ArrayList<>();
//
//    public static HomePageFragment newInstance() {
//        return new HomePageFragment();
//    }
//
//    @Override
//    public int getLayoutId() {
//        return R.layout.fragment_home_page;
//    }
//
//    @Override
//    public void initView(@NotNull View view) {
//
//        viewPager = view.findViewById(R.id.view_pager);
//        tabLyout = view.findViewById(R.id.tab_layout);
//        view.findViewById(R.id.fl_search).setOnClickListener(v -> SearchActivity.start(getContext()));
//        mTabList.add(PlayingUserFragment.newInstance());
////        mTabList.add(HomeOtherRoomFragment.newInstance(RecommandOtherTypeRoomFragment.ROOM_TYPE_XNNY_FEMALE));
////        mTabList.add(HomeOtherRoomFragment.newInstance(RecommandOtherTypeRoomFragment.ROOM_TYPE_XNNY_MAN));
////        mTabList.add(HomeOtherRoomFragment.newInstance(RecommandOtherTypeRoomFragment.ROOM_TYPE_QG));
////        mTabList.add(HomeOtherRoomFragment.newInstance(RecommandOtherTypeRoomFragment.ROOM_TYPE_JY));
//        viewPager.setOffscreenPageLimit(mTabList.size());
//        viewPager.setAdapter(new BaseFragmentAdapter(getFragmentManager(), mTabList));
////        tabLyout.setViewPager(viewPager);
//    }
//
//
//
//}
