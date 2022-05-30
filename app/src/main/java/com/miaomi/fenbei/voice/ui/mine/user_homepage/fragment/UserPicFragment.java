//package com.miaomi.fenbei.voice.ui.mine.user_homepage.fragment;
//
//import android.os.Bundle;
//import android.view.View;
//import android.widget.ImageView;
//
//import com.miaomi.fenbei.common.bean.PreviewBean;
//import com.miaomi.fenbei.common.core.BaseFragment;
//import com.miaomi.fenbei.common.util.ImgUtil;
//import com.miaomi.fenbei.voice.R;
//
//import org.jetbrains.annotations.NotNull;
//
//public class UserPicFragment  extends BaseFragment {
//
//    private String imgUrl;
//    private ImageView ivImg;
//    private PreviewBean bean;
//    private ImageView palyIv;
//
//    public static UserPicFragment createFragment(PreviewBean bean) {
//        UserPicFragment fragment = new UserPicFragment();
//        Bundle args = new Bundle();
//        args.putParcelable("pb", bean);
//        fragment.setArguments(args);
//        return fragment;
//    }
//    @Override
//    public int getLayoutId() {
//        return R.layout.fragment_user_pic;
//    }
//
//    @Override
//    public void initView(@NotNull View view) {
//        ivImg =  view.findViewById(R.id.iv_img);
//        palyIv = view.findViewById(R.id.iv_video_paly);
//        bean = getArguments().getParcelable("pb");
//        if (bean.getType() == 1){
//            ImgUtil.INSTANCE.loadImg(getMContext(), bean.getUrl(), ivImg,R.drawable.common_cover_plachodler);
//            palyIv.setVisibility(View.VISIBLE);
//        }else{
//            ImgUtil.INSTANCE.loadImg(getMContext(), bean.getUrl(), ivImg,R.drawable.common_cover_plachodler);
//            palyIv.setVisibility(View.GONE);
//        }
//
//    }
//
//
//}
