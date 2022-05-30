package com.miaomi.fenbei.voice.widget;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.miaomi.fenbei.base.AudioPlayer;
import com.miaomi.fenbei.voice.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class KMSoundView extends FrameLayout {
    private Context context;
    private TextView timeTv;
    private ImageView deleteIv;
    private String path;
    private ImageView playStatusIv;
    private ImageView iv_status;
    public KMSoundView(@NonNull Context context) {
        super(context);
        this.context = context;
        init();

    }

    public KMSoundView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public KMSoundView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    private void init(){
        LayoutInflater.from(context).inflate(R.layout.layout_view_sound,this,true);
        timeTv = findViewById(R.id.tv_time);
        playStatusIv = findViewById(R.id.iv_play_status);
        iv_status=findViewById(R.id.iv_status);

        iv_status.setOnClickListener(v ->
        startPaly(path)
    );
        deleteIv = findViewById(R.id.iv_delate);
        deleteIv.setVisibility(GONE);
        playStatusIv.setImageResource(R.drawable.icon_sound_view_play_1);
    }

    public void setDeleteListner(OnClickListener onClickListener){
        deleteIv.setOnClickListener(onClickListener);
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setDuration(int duration){
        timeTv.setText(duration+"“");
    }

    public void hideClose(){
        deleteIv.setVisibility(GONE);
    }
    public void showClose(){
        deleteIv.setVisibility(VISIBLE);
    }
    public void atuopaly(){
        if (!TextUtils.isEmpty(path)){
            startPaly(path);
        }
    }
    AnimationDrawable animationDrawable;

    private void startPaly(final String url){

        if (AudioPlayer.getInstance().isPlaying()) {
//            playStatusIv.setImageResource(R.drawable.icon_sound_view_play_1);
            iv_status.setImageResource(R.drawable.icon_start_paly);
            playStatusIv.setVisibility(GONE);
            AudioPlayer.getInstance().stopPlay();
            return;
        }
        iv_status.setImageResource(R.drawable.icon_stop_play);
        playStatusIv.setVisibility(VISIBLE);
        playStatusIv.setImageResource(R.drawable.anim_sound_view_play);
        animationDrawable = (AnimationDrawable) playStatusIv.getDrawable();
        animationDrawable.start();
        AudioPlayer.getInstance().startPlay(url, success ->
        start_success()
        );

    }
    public void start_success(){
        iv_status.setImageResource(R.drawable.icon_start_paly);
        playStatusIv.setVisibility(GONE);
    }
    public void stop(){
        AudioPlayer.getInstance().stopPlay();
    }

}
