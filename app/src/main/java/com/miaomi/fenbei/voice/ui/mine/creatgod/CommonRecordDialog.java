package com.miaomi.fenbei.voice.ui.mine.creatgod;

import android.os.CountDownTimer;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.miaomi.fenbei.base.AudioPlayer;
import com.miaomi.fenbei.base.core.dialog.BaseBottomDialog;
import com.miaomi.fenbei.base.util.DataHelper;
import com.miaomi.fenbei.base.util.OSSPutFileUtil;
import com.miaomi.fenbei.base.util.TimeUtil;
import com.miaomi.fenbei.base.util.ToastUtil;
import com.miaomi.fenbei.base.widget.CircularProgressButton;
import com.miaomi.fenbei.voice.R;

public class CommonRecordDialog extends BaseBottomDialog {

//    private float mStartRecordY;
//    private boolean mAudioCancel;
    private CountDownTimer mCountDownTimer;
    private CountDownTimer mAudiCountDownTimer;
    private CircularProgressButton recordIv;
    private ImageView saveIv;
    private ImageView rerecordIv;
    private TextView recordTv;
    private OnUploadRecordListener onUploadRecordListener;
    private TextView titleTv;
//    private ImageView recordGifLeft;
//    private ImageView recordGifRight;
    private ImageView auditionIv;
    private TextView rereocrdTv;
    private TextView saveTv;
    private int recordTime = 15;


    public CommonRecordDialog(int recordTime) {
        if (recordTime <=0){
            this.recordTime = 15;
        }else{
            this.recordTime = recordTime;
        }
    }

    public void setOnUploadRecordListener(OnUploadRecordListener onUploadRecordListener) {
        this.onUploadRecordListener = onUploadRecordListener;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.dialog_record_great_god;
    }

    @Override
    public void bindView(View v) {
        recordIv = v.findViewById(R.id.iv_record);
        recordIv.setProgressTotal(recordTime);
        saveIv = v.findViewById(R.id.iv_save);
        rerecordIv = v.findViewById(R.id.iv_rerecord);
        recordTv = v.findViewById(R.id.tv_record);
        titleTv = v.findViewById(R.id.tv_title);
//        recordGifLeft = v.findViewById(R.id.iv_record_gif_left);
//        recordGifRight = v.findViewById(R.id.iv_record_gif_right);
        auditionIv = v.findViewById(R.id.iv_audition);
        rereocrdTv = v.findViewById(R.id.tv_rerecord);
        saveTv = v.findViewById(R.id.tv_save);
//        ImgUtil.INSTANCE.loadGif(getContext(),R.drawable.gif_record_play,recordGifLeft);
//        ImgUtil.INSTANCE.loadGif(getContext(),R.drawable.gif_record_play,recordGifRight);
        auditionIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPaly(AudioPlayer.getInstance().getPath());
            }
        });
        rerecordIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AudioPlayer.getInstance().isPlaying()) {
                    AudioPlayer.getInstance().stopPlay();
                }
                statusRecord();
            }
        });
        saveIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updataRecord(AudioPlayer.getInstance().getPath());
            }
        });
        recordIv.setOnTouchListener(new RecordOnTouchListener());
        mCountDownTimer = new CountDownTimer(1000*recordTime,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                titleTv.setText(TimeUtil.getRecordTime(recordTime*1000-millisUntilFinished));
                recordIv.setProgress((int) (recordTime-millisUntilFinished/1000));
            }

            @Override
            public void onFinish() {
            }
        };
        statusRecord();
    }


    class RecordOnTouchListener implements View.OnTouchListener {

        @Override
        public boolean onTouch(View v, MotionEvent motionEvent) {
            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    mCountDownTimer.start();
                    startRecording();
                    AudioPlayer.getInstance().startRecord(new AudioPlayer.Callback() {
                        @Override
                        public void onCompletion(Boolean success) {
                            recordComplete(success);
                        }
                    },recordTime);
                    recordTv.setText("麦克风正在输入");
                    break;
                case MotionEvent.ACTION_CANCEL:
                case MotionEvent.ACTION_UP:
                    recordTv.setText("重新录音");
                    mCountDownTimer.cancel();
                    stopRecording();
                    AudioPlayer.getInstance().stopRecord();
                    break;
                default:
                    break;
            }
            return true;
        }
    }

    private void startPaly(final String url){
        if (AudioPlayer.getInstance().isPlaying()) {
            auditionIv.setSelected(false);
            AudioPlayer.getInstance().stopPlay();
            return;
        }
        auditionIv.setSelected(true);
//        mAudiCountDownTimer = new CountDownTimer(AudioPlayer.getInstance().getDuration(),1000) {
//            @Override
//            public void onTick(long millisUntilFinished) {
////                titleTv.setText(TimeUtil.getRecordTime(AudioPlayer.getInstance().getDuration()-millisUntilFinished)+" / "+(TimeUtil.getRecordTime(AudioPlayer.getInstance().getDuration())));
//            }
//
//            @Override
//            public void onFinish() {
////                titleTv.setText("按住录音");
//            }
//        };
//        mAudiCountDownTimer.start();
//        titleTv.setText("00:00 / "+(TimeUtil.getRecordTime(AudioPlayer.getInstance().getDuration())));
        AudioPlayer.getInstance().startPlay(url, new AudioPlayer.Callback() {
            @Override
            public void onCompletion(Boolean success) {
                auditionIv.setSelected(false);
            }
        });

    }

    private void startRecording() {
        AudioPlayer.getInstance().stopPlay();
//        titleTv.setText("松手停止");
    }
    private void stopRecording() {
        titleTv.setText("按住录音");
    }
//    private void cancelRecording() {
//        ToastUtil.INSTANCE.suc(getContext(),"录音取消");
//    }

    public void recordFailed(){
        recordIv.setProgress(0);
        ToastUtil.INSTANCE.suc(getContext(),"录音失败");

    }

    public void recordTooShort(){
        recordIv.setProgress(0);
        ToastUtil.INSTANCE.suc(getContext(),"录制必须超过3秒");
    }

    private void recordComplete(boolean success) {
        int duration = AudioPlayer.getInstance().getDuration();
        if (duration < 3000) {
            recordTooShort();
            return;
        }
        if (!success) {
            recordFailed();
        }
        statusAudition();
    }

    private void statusRecord(){
        titleTv.setText("按住录音");
        recordTv.setText("按住说话3~"+recordTime+"s");
        rereocrdTv.setVisibility(View.GONE);
        saveTv.setVisibility(View.GONE);
        auditionIv.setSelected(false);
        auditionIv.setVisibility(View.GONE);
        rerecordIv.setVisibility(View.VISIBLE);
        recordIv.setProgress(0);
        saveIv.setVisibility(View.GONE);
        rerecordIv.setVisibility(View.GONE);
    }

    private void statusAudition(){
        titleTv.setText("试听");
        recordTv.setText("试听");
        rereocrdTv.setVisibility(View.VISIBLE);
        saveTv.setVisibility(View.VISIBLE);
        rerecordIv.setVisibility(View.INVISIBLE);
        auditionIv.setSelected(false);
        auditionIv.setVisibility(View.VISIBLE);
        saveIv.setVisibility(View.VISIBLE);
        rerecordIv.setVisibility(View.VISIBLE);
    }

    private void updataRecord(String path){
        final String fileName = "record_"+ DataHelper.INSTANCE.getUID()+"_"+System.currentTimeMillis() +".m4a";
        final OSSPutFileUtil putFileUtil = new OSSPutFileUtil(fileName, path,OSSPutFileUtil.FILE_TYPE_RECARD);
        putFileUtil.uploadFileOriginal(getActivity(), new OSSPutFileUtil.OSSCallBack() {
            @Override
            public void onSuc() {
                if (onUploadRecordListener != null){
                    onUploadRecordListener.onUploadSuccess(putFileUtil.getUrl(),AudioPlayer.getInstance().getDuration()/1000);
                }
            }
            @Override
            public void onFail(String msg) {
            }
        });

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        AudioPlayer.getInstance().stopPlay();
        if (mAudiCountDownTimer != null){
            mAudiCountDownTimer.cancel();
        }
        mCountDownTimer.cancel();
    }
    public interface OnUploadRecordListener{
        void onUploadSuccess(String url,int duration);
    }
}
