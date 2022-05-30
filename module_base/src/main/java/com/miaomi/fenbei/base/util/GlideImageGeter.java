package com.miaomi.fenbei.base.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Created by pengyabo on 2017/12/20.
 * 图片处理
 */
public class GlideImageGeter implements Html.ImageGetter {
    private HashSet<Target> targets;
    private HashSet<GifDrawable> gifDrawables;
    private final Context mContext;
    private final TextView mTextView;
    private int suitHight = 15;

    public void recycle() {
        targets.clear();
        for (GifDrawable gifDrawable : gifDrawables) {
            gifDrawable.setCallback(null);
            gifDrawable.recycle();
        }
        gifDrawables.clear();
    }

    public GlideImageGeter(Context context, TextView textView) {
        this.mContext = context;
        this.mTextView = textView;
        targets = new HashSet<>();
        gifDrawables = new HashSet<>();
//        mTextView.setTag(R.id.tv_content_msg_img, this);
    }

    public GlideImageGeter(Context context, TextView textView,int suitHight) {
        this.mContext = context;
        this.mTextView = textView;
        this.suitHight = suitHight;
        targets = new HashSet<>();
        gifDrawables = new HashSet<>();
//        mTextView.setTag(R.id.tv_content_msg_img, this);
    }

    List<String> tempImgKeys = new ArrayList<>();

    public GlideImageGeter(Context context, TextView textView, int suitHight, List<String> tempImgKey) {
        tempImgKeys.addAll(tempImgKey);
        this.mContext = context;
        this.mTextView = textView;
        this.suitHight = suitHight;
        targets = new HashSet<>();
        gifDrawables = new HashSet<>();
//        mTextView.setTag(R.id.tv_content_msg_img, this);
    }

    @Override
    public Drawable getDrawable(String url) {
        final URLDrawable urlDrawable = new URLDrawable();
        for(String s : tempImgKeys){
            if(url.contains(s)){
                return  urlDrawable;
            }
        }
        RequestBuilder load;
        final Target target;
        if (isGif(url)) {
            load = Glide.with(mContext).asGif().load(url);
            target = new GifTarget(urlDrawable);
        } else {
            load = Glide.with(mContext).asBitmap().load(url);
            target = new BitmapTarget(urlDrawable, url);
        }
        targets.add(target);
        load.into(target);
        return urlDrawable;
    }

    private static boolean isGif(String path) {
        int index = path.lastIndexOf('.');
        return index > 0 && "gif".toUpperCase().equals(path.substring(index + 1).toUpperCase());
    }

    private class GifTarget extends SimpleTarget<GifDrawable> {
        private final URLDrawable urlDrawable;


        private GifTarget(URLDrawable urlDrawable) {
            this.urlDrawable = urlDrawable;

        }

        @Override
        public void onResourceReady(GifDrawable resource, Transition<? super GifDrawable> glideAnimation) {
            int w = ScreenUtil.getScreenWidth();
            int hh = resource.getIntrinsicHeight();
            int ww = resource.getIntrinsicWidth();
            int high = hh * (w - 50) / ww;
            Rect rect = new Rect(20, 20, w - 30, high);
            resource.setBounds(rect);
            urlDrawable.setBounds(rect);
            urlDrawable.setDrawable(resource);
            gifDrawables.add(resource);
            resource.setCallback(mTextView);
            resource.start();
            resource.setLoopCount(GifDrawable.LOOP_FOREVER);
            mTextView.setText(mTextView.getText());
            mTextView.invalidate();
        }
    }

    private class BitmapTarget extends SimpleTarget<Bitmap> {
        private final URLDrawable urlDrawable;
        private String mUrl;

        private BitmapTarget(URLDrawable urlDrawable, String url) {
            this.urlDrawable = urlDrawable;
            this.mUrl = url;
        }

        @Override
        public void onResourceReady(Bitmap resource, Transition<? super Bitmap> glideAnimation) {
            Drawable drawable = new BitmapDrawable(resource);
            int width = resource.getWidth();
            int height = resource.getHeight();
            float type = width * 1f / height;
            setBounds(drawable, type);
            mTextView.setText(mTextView.getText());
            mTextView.invalidate();
        }

        private void setBounds(Drawable drawable, float type) {
            int heights = DensityUtil.INSTANCE.dp2px(mContext, suitHight);
            int widths = (int) (heights * type);
            drawable.setBounds(0, 0, widths, heights);
            urlDrawable.setBounds(0, 0, widths, heights);
            urlDrawable.setDrawable(drawable);
        }
    }
}
