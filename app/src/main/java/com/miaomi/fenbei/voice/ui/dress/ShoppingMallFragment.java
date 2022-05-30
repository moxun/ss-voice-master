package com.miaomi.fenbei.voice.ui.dress;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.miaomi.fenbei.base.bean.BaseBean;
import com.miaomi.fenbei.base.bean.DressItemBean;
import com.miaomi.fenbei.base.bean.ShopMallItemBean;
import com.miaomi.fenbei.base.config.BaseConfig;
import com.miaomi.fenbei.base.core.BaseLazyFragment;
import com.miaomi.fenbei.base.core.dialog.CommonDialog;
import com.miaomi.fenbei.base.net.Callback;
import com.miaomi.fenbei.base.net.NetService;
import com.miaomi.fenbei.base.util.ToastUtil;
import com.miaomi.fenbei.voice.R;
import com.opensource.svgaplayer.SVGACallback;
import com.opensource.svgaplayer.SVGADrawable;
import com.opensource.svgaplayer.SVGADynamicEntity;
import com.opensource.svgaplayer.SVGAImageView;
import com.opensource.svgaplayer.SVGAParser;
import com.opensource.svgaplayer.SVGAVideoEntity;

import org.jetbrains.annotations.NotNull;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ShoppingMallFragment extends BaseLazyFragment {

    private RecyclerView xRecyclerView;
    private ShoppingMallAdapter mAdapter;
    private int dressType = BaseConfig.DRESS_TYPE_ZWK;
    private SVGAImageView svgaImageView;

    public static ShoppingMallFragment newInstance(int type) {
        ShoppingMallFragment fragment = new ShoppingMallFragment();
        Bundle args = new Bundle();
        args.putInt("dressType", type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_dress_seat;
    }

    @Override
    public void initView(@NotNull View view) {
        dressType = getArguments().getInt("dressType",BaseConfig.DRESS_TYPE_ZWK);
        xRecyclerView = view.findViewById(R.id.rv_dress);
        svgaImageView = view.findViewById(R.id.iv_svga);
        xRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new ShoppingMallAdapter(getContext(),dressType);
        mAdapter.setOnDressItemClickListener(this::itemClick);
        xRecyclerView.setAdapter(mAdapter);

    }

    protected void getData(){
        NetService.Companion.getInstance(getContext()).getShoppingMall(dressType,new Callback<List<ShopMallItemBean>>() {
            @Override
            public void onSuccess(int nextPage, List<ShopMallItemBean> list, int code) {
                mAdapter.setData(list);
            }

            @Override
            public void onError(@NotNull String msg, @NotNull Throwable throwable, int code) {
                ToastUtil.INSTANCE.suc(getContext(),msg);
            }

            @Override
            public boolean isAlive() {
                return isLive();
            }
        });
    }

    private void bug(int dressid){
        NetService.Companion.getInstance(getContext()).buyDress(dressid,dressType, new Callback<BaseBean>() {
            @Override
            public void onSuccess(int nextPage, BaseBean bean, int code) {
                ToastUtil.INSTANCE.suc(getContext(),"购买成功");
            }

            @Override
            public void onError(@NotNull String msg, @NotNull Throwable throwable, int code) {
                ToastUtil.INSTANCE.error(getContext(),msg);
            }

            @Override
            public boolean isAlive() {
                return isLive();
            }
        });
    }

    protected void itemClick(DressItemBean bean){
        if (dressType == BaseConfig.DRESS_TYPE_ZJ){
            showSvgaGiftAnim(getContext(),svgaImageView,bean.getImg());
        }
        if (bean.getPrice() == 0){
            ToastUtil.INSTANCE.error(getContext(),bean.getNote());
            return;
        }
        CommonDialog mCommonDialog = new CommonDialog(getContext());
        mCommonDialog.setTitle("友情提示");
        mCommonDialog.setContent("确定购买【" + bean.getName() + "】吗？");
        mCommonDialog.setLeftBt("取消", v12 -> mCommonDialog.dismiss());
        mCommonDialog.setRightBt("确定", v1 -> {
            bug(bean.getId());
            mCommonDialog.dismiss();
        });
        mCommonDialog.show();
    }

    private void showSvgaGiftAnim(Context context, SVGAImageView svgaImageView, String url){
        svgaImageView.setLoops(1);
        SVGAParser parser = new SVGAParser(context);
        svgaImageView.setCallback(new SVGACallback() {
            @Override
            public void onPause() {

            }

            @Override
            public void onFinished() {
            }

            @Override
            public void onRepeat() {
            }

            @Override
            public void onStep(int i, double v) {

            }
        });
        try {
            parser.decodeFromURL(new URL(url), new SVGAParser.ParseCompletion() {
                @Override
                public void onComplete(@NotNull SVGAVideoEntity videoItem) {
                    SVGADynamicEntity dynamicItem = new SVGADynamicEntity();
                    SVGADrawable drawable = new SVGADrawable(videoItem, dynamicItem);
                    svgaImageView.setImageDrawable(drawable);
                    svgaImageView.startAnimation();
                }
                @Override
                public void onError() {
                }
            });
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void loadData() {
        getData();
    }
}
