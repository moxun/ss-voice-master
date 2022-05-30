//package com.miaomi.fenbei.voice.ui.pyq;
//
//import android.view.View;
//import android.widget.ImageView;
//
//import com.example.indicatorlib.base.BaseFragmentAdapter;
//import com.miaomi.fenbei.voice.BaseFragment;
//import com.miaomi.fenbei.base.widget.TMFindIndicator;
//import com.miaomi.fenbei.voice.R;
//
//import org.jetbrains.annotations.NotNull;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import androidx.fragment.app.Fragment;
//import androidx.viewpager.widget.ViewPager;
//
//public class FindFragment extends BaseFragment {
//    private TMFindIndicator tmFindIndicator;
//    private ViewPager viewPager;
//    private List<Fragment> mTabList = new ArrayList<>();
//    private ImageView putMsgIv;
//
//    @Override
//    public int getLayoutId() {
//        return R.layout.fragment_find;
//    }
//
//    public static FindFragment newInstance(){
//        return new FindFragment();
//    }
//
//    @Override
//    public void initView(@NotNull View view) {
//        tmFindIndicator = view.findViewById(R.id.tab_layout);
//        viewPager = view.findViewById(R.id.vp_find);
//        putMsgIv = view.findViewById(R.id.iv_put_msg);
//        mTabList.add(FindChildFragment.newInstance(FindChildFragment.FIND_ITEM_TYPE_GZ));
//        mTabList.add(FindChildFragment.newInstance(FindChildFragment.FIND_ITEM_TYPE_TJ));
//        mTabList.add(FindChildFragment.newInstance(FindChildFragment.FIND_ITEM_TYPE_FJ));
//        viewPager.setOffscreenPageLimit(mTabList.size());
//        viewPager.setAdapter(new BaseFragmentAdapter(getFragmentManager(), mTabList));
//        tmFindIndicator.setViewPager(viewPager);
//        putMsgIv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                PublishPyqActivity.start(getActivity());
//            }
//        });
//        viewPager.setCurrentItem(1);
//    }
//}
