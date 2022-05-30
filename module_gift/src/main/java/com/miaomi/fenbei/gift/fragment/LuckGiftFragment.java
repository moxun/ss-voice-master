//package com.miaomi.fenbei.gift.fragment;
//
//import android.os.Bundle;
//
//import com.miaomi.fenbei.gift.GiftManager;
//
//
//public class LuckGiftFragment extends BaseChildFragment {
//
////    private ViewPager mViewpager;
////    private ViewPagerIndicator mViewpagerIndicator;
////    private List<GiftChildFragment> mGiftFragments = new ArrayList<>();
//    public static LuckGiftFragment newInstance(){
//        LuckGiftFragment fragment = new LuckGiftFragment();
//        Bundle bundle = new Bundle();
//        fragment.setArguments(bundle);
//        return fragment;
//    }
////    @Override
////    public int getLayoutId() {
////        return R.layout.gift_fragment_child_common;
////    }
////
////    @Override
////    public void initView(@NotNull View view) {
////        mViewpager = view.findViewById(R.id.vp_gift);
////        mViewpagerIndicator = view.findViewById(R.id.vpi_gift);
////        GiftManager.getInstance().resetLuckGiftList();
////        int pageCount = GiftManager.getInstance().getLuckGiftPageCount();
////        mGiftFragments.clear();
////        mViewpagerIndicator.setViewPager(mViewpager,pageCount);
////        for (int i = 0 ;i<pageCount;i++){
////            GiftChildFragment fragment = GiftChildFragment.newInstance(GiftManager.GIFT_TYPE_LUCK,i);
////            mGiftFragments.add(fragment);
////        }
////        mViewpager.setAdapter(new GiftViewPagerAdapter(getChildFragmentManager(),pageCount,GiftManager.GIFT_TYPE_LUCK));
////    }
//
//    @Override
//    boolean isNeedgetData() {
//        return false;
//    }
//
//    @Override
//    void initData() {
//        GiftManager.getInstance().resetGiftList(GiftManager.GIFT_TYPE_LUCK);
//    }
//
//    @Override
//    int getType() {
//        return GiftManager.GIFT_TYPE_LUCK;
//    }
//
//    @Override
//    int getSize() {
//        return GiftManager.getInstance().getPageCount(GiftManager.GIFT_TYPE_LUCK);
//    }
//
//    @Override
//    boolean isShowEmpty() {
//        return false;
//    }
//}
