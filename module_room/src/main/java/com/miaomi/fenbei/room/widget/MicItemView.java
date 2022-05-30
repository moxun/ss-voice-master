package com.miaomi.fenbei.room.widget;

import android.content.Context;
import android.graphics.Color;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.miaomi.fenbei.base.bean.ReplaceArrBean;
import com.miaomi.fenbei.base.util.DensityUtil;
import com.miaomi.fenbei.base.widget.WaveView;
import com.miaomi.fenbei.room.R;
import com.miaomi.fenbei.base.bean.EmojiItemBean;
import com.miaomi.fenbei.base.bean.UserInfo;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator;

public class MicItemView extends ConstraintLayout {

    private Context mContext;
    private ImageView mIcon1;
    private SimpleDraweeView mEmoji1;
//    private SimpleDraweeView mBigEmoji1;
    private TextView mNameTv;
    private WaveView mWaterWaveView1;
    private ImageView mMickLock1;
    private TextView mMicStatus1;
    private LinearLayout mMicCharmLayout1;
    private TextView mMicCharmtv1;
//    private TextView micPosition;
    private TextView defaultMicPosition;
    private int position;
    private SVGAImageView svgaImageView;
    private SVGAParser parser;
    private SimpleDraweeView mMicSeat1;

    public MicItemView(@NonNull Context context) {
        super(context);
        this.mContext = context;
        init();
    }

    public MicItemView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        init();
    }

    public MicItemView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        init();
    }


    private void init(){
        View view = LayoutInflater.from(mContext).inflate(R.layout.room_layout_mic_item_view,this,true);
        mWaterWaveView1 = view.findViewById(R.id.item_mic_water);
//        micPosition = view.findViewById(R.id.item_tv_mic);
        defaultMicPosition = view.findViewById(R.id.tv_default_position);
        mIcon1 = view.findViewById(R.id.item_mic_icon);
        mNameTv = view.findViewById(R.id.item_mic_text);
        mMicCharmLayout1 = view.findViewById(R.id.item_mic_charm_layout);
        mMicCharmtv1 = view.findViewById(R.id.item_mic_charm_tv);
        mMickLock1 = view.findViewById(R.id.item_mic_lock);
        mMicStatus1 = view.findViewById(R.id.item_mic_status);
        mEmoji1 = view.findViewById(R.id.item_emoji_icon);
        mMicSeat1 = view.findViewById(R.id.item_mic_seat);
        svgaImageView = view.findViewById(R.id.item_mic_seat_svga);
//        mBigEmoji1 = view.findViewById(R.id.item_big_emoji_icon);

        mWaterWaveView1.setDuration(1500);
        mWaterWaveView1.setSpeed(500);
        mWaterWaveView1.setColor(Color.parseColor("#FBD5FF"));
        mWaterWaveView1.setStroke();
        mWaterWaveView1.setInterpolator(new LinearOutSlowInInterpolator());
        mWaterWaveView1.setInitialRadius(5f);
    }


    public void showEmoji(EmojiItemBean emojiItemBean){
        if (mEmoji1.getVisibility() == View.VISIBLE){
            return;
        }
//        if (emojiItemBean.getEmoji_id() ==  105 && emojiItemBean.getEmoji_group_id() == 1){
//            mEmoji1.setVisibility(View.VISIBLE);
//            DiceUtil.INSTANCE.showDice( mEmoji1,emojiItemBean.getEmoji_result());
//        } else if (emojiItemBean.getEmoji_id() == 103){
//            mEmoji1.setVisibility(View.VISIBLE);
//            BaodengUtil.INSTANCE.showBaodeng(mEmoji1);
//        }
//        else {
//            mEmoji1.setVisibility(View.VISIBLE);
//            ImgUtil.INSTANCE.loadGifOrWebp(mContext, emojiItemBean.getEmoji_gif(), mEmoji1, 1);
//        }

        mEmoji1.setVisibility(View.VISIBLE);
        ImgUtil.INSTANCE.loadGifOrWebp(mContext, emojiItemBean.getEmoji_gif(), mEmoji1, 1);
    }

    public void showVoiceWave(){
        mWaterWaveView1.setVisibility(VISIBLE);
        mWaterWaveView1.start();
    }

    public void stopVoiceWave(){
        mWaterWaveView1.stop();
    }

    public void clearItemMike(){
        mMicCharmtv1.setText("0");
    }

    public int getCharm(){
        return Integer.parseInt(mMicCharmtv1.getText().toString());
    }

    public void setCharm(int charm){
        mMicCharmtv1.setText(String.valueOf(charm));
    }


    public void setMicPosition(int position){
        this.position = position;
//        micPosition.setText(String.valueOf(position));
        defaultMicPosition.setText(position+"号麦");
        if (position == 8){
            defaultMicPosition.setTextColor(Color.parseColor("#FFDD7E"));
            defaultMicPosition.setText("老板位");
//            mMicSeat1.setVisibility(View.VISIBLE);
//            ImgUtil.INSTANCE.loadImg(mContext, R.drawable.icoc_room_mic_8, mMicSeat1);
        }
    }

    public void updata(UserInfo userInfo){
        if (userInfo.getUser_id() <= 0) {
            mWaterWaveView1.setVisibility(INVISIBLE);
//            mMicStatus1.setVisibility(INVISIBLE);
//            micPosition.setVisibility(VISIBLE);
            defaultMicPosition.setVisibility(VISIBLE);
//            mMicCharmLayout1.setVisibility(INVISIBLE);
//            mNameTv.setVisibility(INVISIBLE);
            mNameTv.setText("");
        } else {
            defaultMicPosition.setVisibility(INVISIBLE);
//            micPosition.setVisibility(GONE);
//            mMicStatus1.setVisibility(VISIBLE);
//            mMicCharmLayout1.setVisibility(VISIBLE);
//            mNameTv.setVisibility(VISIBLE);
            mNameTv.setText(userInfo.getNickname());
//            mMicSeat1.setVisibility(View.VISIBLE);
//            mMicSeat1.setBackgroundResource(0);
//            showSvgaGiftAnim("http://im-file-oss.miaorubao.com/im/img/gift_icon/1599804014_698.svga");
//            ImgUtil.INSTANCE.loadGifOrWebp(mContext, "http://im-file-oss.gulajue.cn/im/img/gift_icon/1602082679_722.gif", mMicSeat1, Integer.MAX_VALUE);
        }
        if (userInfo.getSeat_frame() == null || userInfo.getSeat_frame().isEmpty() || userInfo.getSeat_frame().equals("0")) {
            mMicSeat1.setVisibility(View.INVISIBLE);
            svgaImageView.setVisibility(View.INVISIBLE);
        } else {
            if (userInfo.getSeat_frame().endsWith(".svga")){
                svgaImageView.setVisibility(View.VISIBLE);
                mMicSeat1.setVisibility(View.INVISIBLE);
                showSvgaGiftAnim(userInfo.getSeat_frame(),userInfo.getReplaceArr());
            }else{
                svgaImageView.setVisibility(View.INVISIBLE);
                mMicSeat1.setVisibility(View.VISIBLE);
                mMicSeat1.setBackgroundResource(0);
                ImgUtil.INSTANCE.loadGifOrWebp(mContext, userInfo.getSeat_frame(), mMicSeat1, Integer.MAX_VALUE);
            }

        }
//        if (userInfo.getGender() == 1){
//            micPosition.setSelected(true);
//        }else {
//            micPosition.setSelected(false);
//        }
        if (userInfo.getStatus() == 0) {
            mMicStatus1.setVisibility(View.INVISIBLE);
        } else {
            mMicStatus1.setVisibility(View.VISIBLE);
        }

        if (userInfo.getMic_speaking()) {
            mWaterWaveView1.setVisibility(VISIBLE);
            mWaterWaveView1.start();
        } else {
            mWaterWaveView1.stop();
        }
        if (userInfo.getUser_id() == -1) {
            mMickLock1.setVisibility(VISIBLE);
            mIcon1.setVisibility(INVISIBLE);
        } else {
            mMickLock1.setVisibility(INVISIBLE);
            mIcon1.setVisibility(VISIBLE);
        }
        if (userInfo.getUser_id() <= 0) {
//            mMicStatus1.setVisibility(INVISIBLE);
            if (position == 8 ){
                ImgUtil.INSTANCE.loadCircleImg(mContext, R.drawable.room_icon_mic_8, mIcon1, R.drawable.room_icon_mic_8);
            }else{
                ImgUtil.INSTANCE.loadCircleImg(mContext, R.drawable.chatting_mic_default, mIcon1, R.drawable.chatting_mic_default);
            }
        } else {
            ImgUtil.INSTANCE.loadCircleImg(mContext, userInfo.getFace(), mIcon1, R.drawable.chatting_mic_default);
        }
    }

    public void clearItem(){
        mEmoji1.setVisibility(View.INVISIBLE);
    }

    private void showSvgaGiftAnim(String url,final List<ReplaceArrBean> replaceArr){
        svgaImageView.setVisibility(View.VISIBLE);
        if (parser == null){
            parser = new SVGAParser(getContext());
        }
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
}
