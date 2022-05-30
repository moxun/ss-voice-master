package com.miaomi.fenbei.voice.ui.mine.user_homepage.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.miaomi.fenbei.base.bean.PreviewBean;
import com.miaomi.fenbei.base.core.BaseFragment;
import com.miaomi.fenbei.base.util.ImgUtil;
import com.miaomi.fenbei.voice.R;

import org.jetbrains.annotations.NotNull;

public class BigFragment extends BaseFragment {

    private String imgUrl;
    private ImageView ivImg;
    private PreviewBean bean;
    private ImageView palyIv;
//    private MEGSYVideoPlayer detailPlayer;

    public static BigFragment createFragment(String imgUrl) {
        BigFragment fragment = new BigFragment();
        Bundle args = new Bundle();
        args.putString("img_url", imgUrl);
        fragment.setArguments(args);
        return fragment;
    }

    public static BigFragment createFragment(PreviewBean bean) {
        BigFragment fragment = new BigFragment();
        Bundle args = new Bundle();
        args.putParcelable("pb", bean);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.user_fragment_big_img;
    }

    @Override
    public void initView(@NotNull View view) {
        ivImg = view.findViewById(R.id.iv_img);
        palyIv = view.findViewById(R.id.iv_video_paly);
        if (getArguments() != null) {
            imgUrl = getArguments().getString("img_url");
            bean = getArguments().getParcelable("pb");
        }
        if (!TextUtils.isEmpty(imgUrl)) {
            palyIv.setVisibility(View.GONE);
            ivImg.setVisibility(View.VISIBLE);
            ImgUtil.INSTANCE.loadImg(getMContext(), imgUrl, ivImg);
        } else {
            if (bean != null) {
                ImgUtil.INSTANCE.loadImg(getMContext(), bean.getUrl(), ivImg);
            }
        }
    }
}


