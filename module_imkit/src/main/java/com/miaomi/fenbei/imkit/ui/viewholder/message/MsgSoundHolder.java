package com.miaomi.fenbei.imkit.ui.viewholder.message;

import android.graphics.drawable.AnimationDrawable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.miaomi.fenbei.base.AudioPlayer;
import com.miaomi.fenbei.base.bean.event.C2CMsgBean;
import com.miaomi.fenbei.base.util.DataHelper;
import com.miaomi.fenbei.base.util.DensityUtil;
import com.miaomi.fenbei.base.util.ToastUtil;
import com.miaomi.fenbei.imkit.R;
import com.tencent.imsdk.TIMCallBack;
import com.tencent.imsdk.TIMSoundElem;

import java.io.File;

public class MsgSoundHolder extends MsgBaseHolder{
    private static int AUDIO_MIN_WIDTH ;
    private static int AUDIO_MAX_WIDTH ;
    private ImageView soundMeIv;
    private ImageView soundOtherIv;
    private LinearLayout meFl;
    private LinearLayout otherFl;
    private TextView spaceTvMe;
    private TextView spaceTvOther;
    private TextView redTv;
    public MsgSoundHolder(View itemView) {
        super(itemView);
        AUDIO_MIN_WIDTH = DensityUtil.INSTANCE.dp2px(itemView.getContext(),60);
        AUDIO_MAX_WIDTH = DensityUtil.INSTANCE.dp2px(itemView.getContext(),250);
        otherFl = itemView.findViewById(R.id.fl_other);
        meFl = itemView.findViewById(R.id.fl_me);
        soundMeIv = itemView.findViewById(R.id.iv_sound_me);
        spaceTvMe = itemView.findViewById(R.id.tv_space_me);
        spaceTvOther = itemView.findViewById(R.id.tv_space_other);
        soundOtherIv = itemView.findViewById(R.id.iv_sound_other);
        redTv = itemView.findViewById(R.id.tv_red);
    }


    @Override
    void bindChildData(final C2CMsgBean bean) {
        if (TextUtils.isEmpty(bean.getSoundPath())){
            getSound(bean, (TIMSoundElem) bean.getElement());
        }
        int duration = (int) bean.getDuration();
        if (duration == 0) {
            duration = 1;
        }
        meFl.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (onItemOprateListener != null){
                    onItemOprateListener.onLongClik(meFl,bean);
                }
                return true;
            }
        });
        otherFl.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (onItemOprateListener != null){
                    onItemOprateListener.onLongClik(otherFl,bean);
                }
                return true;
            }
        });
        meFl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bean.getSend_status() == C2CMsgBean.SEND_STATUS_ING){
                    ToastUtil.INSTANCE.suc(itemView.getContext(),"发送中...");
                }
                startPaly(bean.getSoundPath(),true);

            }
        });
        otherFl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redTv.setVisibility(View.GONE);
                bean.setCustomInt(C2CMsgBean.SOUND_STATUS_READ);
                startPaly(bean.getSoundPath(),false);
            }
        });
        if (bean.getCustomInt() == C2CMsgBean.SOUND_STATUS_READ){
            redTv.setVisibility(View.GONE);
        }else{
            redTv.setVisibility(View.VISIBLE);
        }
        int width;
        if (DataHelper.INSTANCE.getUID() == Integer.parseInt(bean.getSender())){
            width = AUDIO_MIN_WIDTH + DensityUtil.INSTANCE.dp2px(itemView.getContext(),duration * 10);
            if (width > AUDIO_MAX_WIDTH) {
                width = AUDIO_MAX_WIDTH;
            }
            meFl.setLayoutParams(new LinearLayout.LayoutParams(width,DensityUtil.INSTANCE.dp2px(itemView.getContext(),38)));
            if (bean.getSend_status() == C2CMsgBean.SEND_STATUS_ING){
                spaceTvMe.setText("0＂");
            }else{
                spaceTvMe.setText(duration +"＂");
            }
        }else{

            spaceTvOther.setText(duration +"＂");
            width = AUDIO_MIN_WIDTH + DensityUtil.INSTANCE.dp2px(itemView.getContext(),duration * 10);
            if (width > AUDIO_MAX_WIDTH) {
                width = AUDIO_MAX_WIDTH;
            }

            otherFl.setLayoutParams(new LinearLayout.LayoutParams(width,DensityUtil.INSTANCE.dp2px(itemView.getContext(),38)));
        }
    }


    private void startPaly(final String url, final boolean isSlef){
        if (AudioPlayer.getInstance().isPlaying()) {
            AudioPlayer.getInstance().stopPlay();
            return;
        }
        if (TextUtils.isEmpty(url)) {
            ToastUtil.INSTANCE.suc(itemView.getContext(),"语音文件还未下载完成");
            return;
        }
        final AnimationDrawable animationDrawable;
        if (isSlef){
            soundMeIv.setImageResource(R.drawable.voice_play_me);
            animationDrawable = (AnimationDrawable) soundMeIv.getDrawable();
        }else{
            soundOtherIv.setImageResource(R.drawable.voice_play_other);
            animationDrawable = (AnimationDrawable) soundOtherIv.getDrawable();
        }

        animationDrawable.start();
        AudioPlayer.getInstance().startPlay(url, new AudioPlayer.Callback() {
            @Override
            public void onCompletion(Boolean success) {
                animationDrawable.stop();
                if (isSlef){
                    soundMeIv.setImageResource(R.drawable.chat_icon_sound_me);
                }else{
                    soundOtherIv.setImageResource(R.drawable.chat_icon_sound_other);
                }
            }
        });

    }

    private void getSound(final C2CMsgBean msgInfo, TIMSoundElem soundElemEle) {
        final String path = AudioPlayer.RECORD_DOWNLOAD_DIR + soundElemEle.getUuid();
        File file = new File(path);
        if (!file.exists()) {
            soundElemEle.getSoundToFile(path, new TIMCallBack() {
                @Override
                public void onError(int code, String desc) {
                }

                @Override
                public void onSuccess() {
                    msgInfo.setSoundPath(path);
                }
            });
        } else {
            msgInfo.setSoundPath(path);
        }
    }
}
