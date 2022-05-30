package com.miaomi.fenbei.voice.ui.mine.user_homepage;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.miaomi.fenbei.base.bean.PreviewBean;
import com.miaomi.fenbei.base.util.ImgUtil;
import com.miaomi.fenbei.voice.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

public class UserPicPagerAdapter extends PagerAdapter {

    private List<PreviewBean> mList = new ArrayList<>();

    public void setData(List<PreviewBean> list){
        mList.clear();
        mList.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.fragment_user_pic,container,false);
        ImageView img = view.findViewById(R.id.iv_img);
        ImageView status = view.findViewById(R.id.iv_video_paly);
        container.addView(view);
        ImgUtil.INSTANCE.loadImg(container.getContext(), mList.get(position).getUrl(), img, R.drawable.common_default);

       if (mList.get(position).getType() == 1){
           status.setVisibility(View.VISIBLE);
       }else{
           status.setVisibility(View.GONE);
       }

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

}


//        extends FragmentPagerAdapter {
//
//    private List<UserPicFragment> mList = new ArrayList<>();
//    private FragmentManager mFragmentManager;
//
//    public UserPicPagerAdapter(FragmentManager fm) {
//        super(fm);
//        mFragmentManager = fm;
//    }
//
//    public void setData(List<UserPicFragment> mimgUrlList){
//        mList.clear();
//        mList.addAll(mimgUrlList);
//        notifyDataSetChanged();
//    }
//
//    @Override
//    public Fragment getItem(int position) {
//        return mList.get(position);
//    }
//    public void setFragments(List<UserPicFragment> newFragmentList) {
//        FragmentTransaction ft = mFragmentManager.beginTransaction();//获得FragmentTransaction 事务
//        for (Fragment f : this.mList) {
//            ft.remove(f); //遍历删除fragment
//        }
//        ft.commit();
//        ft = null;
//        mFragmentManager.executePendingTransactions();
//        this.mList.clear();
//        this.mList.addAll(newFragmentList);//重新赋值
//        notifyDataSetChanged();
//    }
//
//    @Override
//    public int getCount() {
//        return mList.size();
//    }
//}
