package com.miaomi.fenbei.base;

import android.content.Context;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.facebook.drawee.view.SimpleDraweeView;
import com.miaomi.fenbei.base.bean.ReplaceArrBean;
import com.miaomi.fenbei.base.util.DensityUtil;
import com.miaomi.fenbei.base.util.ImgUtil;
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

public class BigMicSeatView extends FrameLayout {
    private SVGAImageView svgaImageView;
    private SVGAParser parser;
    private SimpleDraweeView mMicSeat1;
    private Context mContext;

    public BigMicSeatView(@NonNull Context context) {
        super(context);
        this.mContext = context;
        init();
    }

    public BigMicSeatView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        init();
    }

    public BigMicSeatView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        init();
    }

    private void init() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.view_mic_seat_big, this, true);
        mMicSeat1 = view.findViewById(R.id.item_mic_seat);
        svgaImageView = view.findViewById(R.id.item_mic_seat_svga);
    }

    public void loadSeat(String url, List<ReplaceArrBean> replaceArr) {
        if (url.isEmpty() || url.equals("0")) {
            setVisibility(View.INVISIBLE);
        } else {
            setVisibility(View.VISIBLE);
            if (url.endsWith(".svga")) {
                svgaImageView.setVisibility(View.VISIBLE);
                mMicSeat1.setVisibility(View.INVISIBLE);
                showSvgaGiftAnim(url, replaceArr);
            } else {
                svgaImageView.setVisibility(View.INVISIBLE);
                mMicSeat1.setVisibility(View.VISIBLE);
                mMicSeat1.setBackgroundResource(0);
                ImgUtil.INSTANCE.loadGifOrWebp(mContext, url, mMicSeat1, Integer.MAX_VALUE);
            }

        }
    }

    public void loadSeat(String url) {
        if (url.isEmpty() || url.equals("0")) {
            setVisibility(View.INVISIBLE);
        } else {
            setVisibility(View.VISIBLE);
            if (url.endsWith(".svga")) {
                svgaImageView.setVisibility(View.VISIBLE);
                mMicSeat1.setVisibility(View.INVISIBLE);
                showSvgaGiftAnim(url,null);
            } else {
                svgaImageView.setVisibility(View.INVISIBLE);
                mMicSeat1.setVisibility(View.VISIBLE);
                mMicSeat1.setBackgroundResource(0);
                ImgUtil.INSTANCE.loadGifOrWebp(mContext, url, mMicSeat1, Integer.MAX_VALUE);
            }

        }
    }


    private void showSvgaGiftAnim(String url,final List<ReplaceArrBean> replaceArr) {
        svgaImageView.setVisibility(View.VISIBLE);
        parser = new SVGAParser(getContext());
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
                    SVGADynamicEntity dynamicEntity = new SVGADynamicEntity();
                    TextPaint textPaint = new TextPaint();
                    textPaint.setTextSize(DensityUtil.INSTANCE.dp2px(getContext(), 10f));
                    textPaint.setARGB(0xFF, 0xFF, 0xFF, 0xFF);
                    if (replaceArr != null) {
                        for (ReplaceArrBean item : replaceArr) {
                            if (item.getType() == 1) {
                                dynamicEntity.setDynamicText(item.getValue(), textPaint, item.getName());
                            } else {
                                dynamicEntity.setDynamicImage(item.getValue(), item.getName());
                            }
                        }
                    }
                    SVGADrawable drawable = new SVGADrawable(videoItem, dynamicEntity);
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
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();

    }

}