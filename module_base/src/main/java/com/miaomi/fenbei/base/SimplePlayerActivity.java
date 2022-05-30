package com.miaomi.fenbei.base;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.view.View;
import android.widget.ImageView;

import com.miaomi.fenbei.base.R;
import com.miaomi.fenbei.base.core.BaseActivity;
import com.miaomi.fenbei.base.util.ImgUtil;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.utils.OrientationUtils;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;


public class SimplePlayerActivity extends BaseActivity {

    private StandardGSYVideoPlayer videoPlayer;

    private OrientationUtils orientationUtils;

    private String url;

    public static void start(Context context,String url){
        Intent intent = new Intent(context,SimplePlayerActivity.class);
        intent.putExtra("url",url);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.base_activity_simple_player;
    }

    @Override
    public void initView() {
        init();
    }

    private void init() {
        url = getIntent().getStringExtra("url");
        setBaseStatusBar(false, false);

        videoPlayer =  (StandardGSYVideoPlayer)findViewById(R.id.video_player);

//        String source1 = "http://9890.vod.myqcloud.com/9890_4e292f9a3dd011e6b4078980237cc3d3.f20.mp4";
        videoPlayer.setUp(url, true, "返回");

        //增加封面
        ImageView imageView = new ImageView(this);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        ImgUtil.INSTANCE.loadImg(this,url,imageView);
//        imageView.setImageResource(R.drawable.ic_app_logo);
        videoPlayer.setThumbImageView(imageView);
        //增加title
        videoPlayer.getTitleTextView().setVisibility(View.VISIBLE);
        //设置返回键
        videoPlayer.getBackButton().setVisibility(View.VISIBLE);
        //设置旋转
        orientationUtils = new OrientationUtils(this, videoPlayer);
        //设置全屏按键功能,这是使用的是选择屏幕，而不是全屏
        videoPlayer.getFullscreenButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orientationUtils.resolveByClick();
            }
        });
        //是否可以滑动调整
        videoPlayer.setIsTouchWiget(true);
        //设置返回按键功能
        videoPlayer.getBackButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        videoPlayer.startPlayLogic();
    }


    @Override
    protected void onPause() {
        super.onPause();
        videoPlayer.onVideoPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        videoPlayer.onVideoResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        GSYVideoManager.releaseAllVideos();
        if (orientationUtils != null)
            orientationUtils.releaseListener();
    }

    @Override
    public void onBackPressed() {
        //先返回正常状态
        if (orientationUtils.getScreenType() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            videoPlayer.getFullscreenButton().performClick();
            return;
        }
        //释放所有
        videoPlayer.setVideoAllCallBack(null);
        super.onBackPressed();
    }
}
