package com.miaomi.fenbei.gift.fragment;

import androidx.viewpager.widget.ViewPager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.miaomi.fenbei.base.core.BaseFragment;
import com.miaomi.fenbei.base.widget.GridSpacingItemDecoration;
import com.miaomi.fenbei.gift.GiftManager;
import com.miaomi.fenbei.gift.R;
import com.miaomi.fenbei.gift.adapter.GiftAdapter;
import com.miaomi.fenbei.gift.adapter.ViewPagerAdapter;
import com.miaomi.fenbei.base.bean.GiftBean;
import com.miaomi.fenbei.gift.listener.OnGiftItemClickListener;
import com.miaomi.fenbei.gift.widget.ViewPagerIndicator;
import com.scwang.smartrefresh.layout.util.DensityUtil;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseChildFragment extends BaseFragment {
    protected ViewPager mViewpager;
    protected ViewPagerIndicator mViewpagerIndicator;
    private List<RecyclerView> mRecyclerviews = new ArrayList<>();
    private int currentPage;
    protected TextView mEmptyTextView;
    private GiftAdapter mAdapter;
    private List<GiftBean.DataBean> mList;

    @Override
    public void initView(@NotNull View view) {
        mViewpager = view.findViewById(R.id.vp_gift);
        mViewpagerIndicator = view.findViewById(R.id.vpi_gift);
        mEmptyTextView = view.findViewById(R.id.tv_gift_empty);
        initData();
        if (isShowEmpty()){
            mEmptyTextView.setVisibility(View.VISIBLE);
        }else{
            mEmptyTextView.setVisibility(View.GONE);
        }
        if (!isNeedgetData()){
            getGirdViewList(getType(),getSize());
        }
        mViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                currentPage = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
    abstract boolean isNeedgetData();
    abstract void initData();
    abstract int getType();
    abstract int getSize();
    abstract boolean isShowEmpty();

    public void refreshCommonGift(){
        int size = getSize();
        final int giftType = getType();
        mRecyclerviews.clear();
        for (int i =0 ;i<size;i++){
            mList = new ArrayList<>(GiftManager.getInstance().getGiftPageList(giftType, i));
            RecyclerView giftRv = (RecyclerView) LayoutInflater.from(getMContext()).inflate(R.layout.gift_fragment_gift_child, mViewpager, false);
            mAdapter = new GiftAdapter(getContext(),mList);
            mAdapter.setTag(giftType);
            giftRv.addItemDecoration(new GridSpacingItemDecoration(4, DensityUtil.dp2px(6f), false));
            giftRv.setLayoutManager(new GridLayoutManager(getContext(), 4));
            giftRv.setAdapter(mAdapter);
            mAdapter.setGiftItemClickListener(new OnGiftItemClickListener() {
                @Override
                public void onItemClick(GiftBean.DataBean bean) {
                    if (getParentFragment() instanceof GiftFragment){
                        if (bean.getId() != GiftManager.getInstance().getSelectedGift(giftType).getId()){
                            ((GiftFragment)getParentFragment()).hideGiftNum();
                        }
                    }
                    GiftManager.getInstance().setSelectedGift(giftType,bean);
                }

                @Override
                public void onRefresh() {
                    for (int j =0 ;j<mRecyclerviews.size();j++){
                        if (currentPage != j){
                            mRecyclerviews.get(j).getAdapter().notifyDataSetChanged();
                        }
                    }
                }

                @Override
                public void onItemSend(GiftBean.DataBean bean) {
                    if (getParentFragment() instanceof GiftFragment){
                        ((GiftFragment)getParentFragment()).sendGift(giftType,1);
                    }
                    if (getParentFragment() instanceof PrivateGiftFragment){
                        ((PrivateGiftFragment)getParentFragment()).sendGift(bean);
                    }
                }

                @Override
                public void onItemLongClick() {
                    if (getParentFragment() instanceof GiftFragment){
                        ((GiftFragment)getParentFragment()).showGiftNum();
                    }
//                    if (getParentFragment() instanceof PrivateGiftFragment){
//                        ((PrivateGiftFragment)getParentFragment()).sendGift(bean);
//                    }
                }
            });
            mRecyclerviews.add(giftRv);
        }
        mViewpagerIndicator.setSelectedTab(R.drawable.common_gray_radius);
        mViewpagerIndicator.setUnSelectedTab(R.drawable.common_white_radius);
        mViewpagerIndicator.setViewPager(mViewpager,size);
        mViewpager.setAdapter(new ViewPagerAdapter(mRecyclerviews));
    }

    public void getGirdViewList(final int giftType, int size){
        mRecyclerviews.clear();
        for (int i =0 ;i<size;i++){
            mList = new ArrayList<>(GiftManager.getInstance().getGiftPageList(giftType, i));
            RecyclerView giftRv = (RecyclerView) LayoutInflater.from(getMContext()).inflate(R.layout.gift_fragment_gift_child, mViewpager, false);
            mAdapter = new GiftAdapter(getContext(),mList);
            mAdapter.setTag(giftType);
            giftRv.addItemDecoration(new GridSpacingItemDecoration(4, DensityUtil.dp2px(6f), false));
            giftRv.setLayoutManager(new GridLayoutManager(getContext(), 4));
            giftRv.setAdapter(mAdapter);
            mAdapter.setGiftItemClickListener(new OnGiftItemClickListener() {
                @Override
                public void onItemClick(GiftBean.DataBean bean) {
                    if (getParentFragment() instanceof GiftFragment){
                        if (bean.getId() != GiftManager.getInstance().getSelectedGift(giftType).getId()){
                            ((GiftFragment)getParentFragment()).hideGiftNum();
                        }
                    }
                    GiftManager.getInstance().setSelectedGift(giftType,bean);
                }

                @Override
                public void onRefresh() {
                    for (int j = 0 ;j<mRecyclerviews.size();j++){
                        if (currentPage != j){
                            mRecyclerviews.get(j).getAdapter().notifyDataSetChanged();
                        }
                    }
                }

                @Override
                public void onItemSend(GiftBean.DataBean bean) {
                    if (getParentFragment() instanceof GiftFragment){
                        ((GiftFragment)getParentFragment()).sendGift(giftType,1);
                    }
                    if (getParentFragment() instanceof PrivateGiftFragment){
                        ((PrivateGiftFragment)getParentFragment()).sendGift(bean);
                    }
                }

                @Override
                public void onItemLongClick() {
                    if (getParentFragment() instanceof GiftFragment){
                        ((GiftFragment)getParentFragment()).showGiftNum();
                    }
                }
            });
            mRecyclerviews.add(giftRv);
        }
//        if (getType() == GiftManager.GIFT_TYPE_COMMON_PRIVATE){
//            mViewpagerIndicator.setSelectedTab(R.drawable.gift_yuandian_hei);
//            mViewpagerIndicator.setUnSelectedTab(R.drawable.gift_yuandian_hui);
//        }
        mViewpagerIndicator.setViewPager(mViewpager,size);
        mViewpager.setAdapter(new ViewPagerAdapter(mRecyclerviews));
    }

    public void refresh(int id,int num,int price){
        int surplus = GiftManager.getInstance().getPackTotal() - num*price;
        assert getParentFragment() != null;
        ((GiftFragment)getParentFragment()).setPackTotal(String.valueOf(surplus));
        GiftAdapter adapter = (GiftAdapter) mRecyclerviews.get(currentPage).getAdapter();
        for(GiftBean.DataBean bean:adapter.getList()){
            if (id == bean.getId() ){
                int endNum = bean.getNumber() - num;
                if (endNum == 0){
                    adapter.getList().remove(bean);
                }else{
                    bean.setNumber(endNum);
                }
                break;
            }
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public int getLayoutId() {
        return R.layout.gift_fragment_child_pack;
    }


}
