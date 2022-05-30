package com.miaomi.fenbei.base;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.Toast;

import com.miaomi.fenbei.base.R;
import com.miaomi.fenbei.base.util.ImgUtil;
import com.ypx.imagepicker.adapter.multi.MultiGridAdapter;
import com.ypx.imagepicker.bean.ImageItem;
import com.ypx.imagepicker.bean.PickerUiConfig;
import com.ypx.imagepicker.presenter.IMultiPickerBindPresenter;

import java.util.ArrayList;

public class WXImgPickerPresenter implements IMultiPickerBindPresenter {

    @Override
    public void displayListImage(ImageView imageView, ImageItem item, int size) {
        ImgUtil.INSTANCE.loadImg(imageView.getContext(),item.path,imageView);
    }

    @Override
    public void displayPerViewImage(ImageView imageView, String url) {
        ImgUtil.INSTANCE.loadImg(imageView.getContext(),url,imageView);
    }

    @Override
    public PickerUiConfig getUiConfig(Context context) {
        PickerUiConfig config = new PickerUiConfig();
        //是否沉浸式状态栏，状态栏颜色将根据TopBarBackgroundColor指定，
        // 并动态更改状态栏图标颜色
        config.setImmersionBar(true);
        //设置主题色
        config.setThemeColor(Color.parseColor("#09C768"));
        //设置选中和未选中时图标
        config.setSelectedIconID(R.mipmap.picker_wechat_select);
        config.setUnSelectIconID(R.mipmap.picker_wechat_unselect);
        //设置返回图标以及返回图标颜色
        config.setBackIconID(R.mipmap.picker_icon_back_black);
        config.setBackIconColor(Color.BLACK);
        //设置标题栏背景色和对齐方式，设置标题栏文本颜色
        config.setTitleBarBackgroundColor(Color.parseColor("#F1F1F1"));
        config.setTitleBarGravity(Gravity.START);
        config.setTitleColor(Color.BLACK);
        //设置标题栏右上角完成按钮选中和未选中样式，以及文字颜色
//        int r = ViewSizeUtils.dp(context, 2);
//        config.setOkBtnSelectBackground(CornerUtils.cornerDrawable(Color.parseColor("#09C768"), r));
//        config.setOkBtnUnSelectBackground(CornerUtils.cornerDrawable(Color.parseColor("#B4ECCE"), r));
        config.setOkBtnSelectTextColor(Color.parseColor("#222222"));
        config.setOkBtnUnSelectTextColor(Color.parseColor("#F0222222"));
        config.setOkBtnText("完成");
        //设置选择器背景色
        config.setPickerBackgroundColor(Color.WHITE);
        //设置选择器item背景色
        config.setPickerItemBackgroundColor(Color.parseColor("#484848"));
        //设置底部栏颜色
        config.setBottomBarBackgroundColor(Color.parseColor("#333333"));
        //设置拍照按钮图标和背景色
        config.setCameraIconID(R.mipmap.picker_ic_camera);
        config.setCameraBackgroundColor(Color.parseColor("#484848"));

        //标题栏模式，从标题栏选择相册
        config.setPickStyle(PickerUiConfig.PICK_STYLE_TITLE);
        //设置选择器自定义item样式
//        config.setPickerItemView(new CustomPickerItem(context));
        return config;
    }

    @Override
    public void tip(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void imageItemClick(Context context, ImageItem imageItem, ArrayList<ImageItem> selectImageList,
                               ArrayList<ImageItem> allSetImageList, MultiGridAdapter adapter) {
        tip(context, "我是自定义的图片点击事件");
    }
}
