package com.miaomi.fenbei.gift.widget;
import android.content.Context;
import androidx.viewpager.widget.ViewPager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.miaomi.fenbei.gift.R;


/**
 * Created by lzq on 2018/3/6.
 */

public class ViewPagerIndicator extends FrameLayout implements ViewPager.OnPageChangeListener{
    /**
     *圆点数量
     */
    private int mTabCount;
    private Context mContext;
    private float mTranslationX;
    ImageView orageImageView;
    private int unSelectedTab = R.drawable.common_white_radius;
    private int selectedTab = R.drawable.common_gray_radius;
//    private int unSelectedTab = R.drawable.gift_yuandian_bai;
//    private int selectedTab = R.drawable.gift_yuandian_hei;


    public void setUnSelectedTab(int unSelectedTab) {
        this.unSelectedTab = unSelectedTab;
    }

    public void setSelectedTab(int selectedTab) {
        this.selectedTab = selectedTab;
    }

    public ViewPagerIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;

    }
    public ViewPagerIndicator(Context context) {
        super(context);

    }


    public void setViewPager(ViewPager viewPager,int count){
        viewPager.setOnPageChangeListener(this);
        mTabCount = count;
        addTabItem();
    }
    public void addTabItem(){
        removeAllViews();
        LinearLayout linearLayout = new LinearLayout(mContext);
        FrameLayout.LayoutParams fLayoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        linearLayout.setLayoutParams(fLayoutParams);
        linearLayout.setGravity(Gravity.CENTER_VERTICAL);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        if (mTabCount == 1){
            setVisibility(View.GONE);
        }else{
            setVisibility(View.VISIBLE);
        }
        for (int i=0;i<mTabCount;i++){
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(4,4,4,4);
            ImageView imageView = new ImageView(mContext);
            imageView.setImageDrawable(mContext.getResources().getDrawable(unSelectedTab));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setLayoutParams(layoutParams);
            linearLayout.addView(imageView);
        }
        orageImageView = new ImageView(mContext);
        orageImageView.setImageDrawable(mContext.getResources().getDrawable(selectedTab));
        orageImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        FrameLayout.LayoutParams f1LayoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        f1LayoutParams.setMargins(4,4,4,4);
        orageImageView.setLayoutParams(f1LayoutParams);
        addView(linearLayout);
        addView(orageImageView);
    }
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        mTranslationX = getWidth() / mTabCount * (position + positionOffset);
        orageImageView.setTranslationX(mTranslationX);
        if (onSelectedPageListener != null){
            onSelectedPageListener.onScroll(position,positionOffset,positionOffsetPixels);
        }
    }

    @Override
    public void onPageSelected(int position) {
        if (onSelectedPageListener != null){
            onSelectedPageListener.onSelected(position);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }
    OnSelectedPageListener onSelectedPageListener;
    public interface OnSelectedPageListener{
        void onSelected(int position);
        void onScroll(int position, float positionOffset, int positionOffsetPixels);
    }
    public void setOnSelectedPageListener(OnSelectedPageListener onSelectedPageListener){
        this.onSelectedPageListener = onSelectedPageListener;
    }
}