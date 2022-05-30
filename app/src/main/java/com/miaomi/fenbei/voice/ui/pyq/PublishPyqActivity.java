package com.miaomi.fenbei.voice.ui.pyq;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.SparseArray;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hjq.permissions.OnPermission;
import com.hjq.permissions.XXPermissions;
import com.miaomi.fenbei.base.VideoRecordActivity;
import com.miaomi.fenbei.base.SimplePlayerActivity;
import com.miaomi.fenbei.base.bean.BaseBean;
import com.miaomi.fenbei.base.bean.FindBean;
import com.miaomi.fenbei.base.core.BaseActivity;
import com.miaomi.fenbei.base.core.CommonLib;
import com.miaomi.fenbei.base.core.dialog.LoadingDialog;
import com.miaomi.fenbei.base.net.Callback;
import com.miaomi.fenbei.base.net.NetService;
import com.miaomi.fenbei.base.util.OSSPutFileUtil;
import com.miaomi.fenbei.base.util.PhotoUtils;
import com.miaomi.fenbei.base.util.StatusBarUtil;
import com.miaomi.fenbei.base.util.ToastUtil;
import com.miaomi.fenbei.base.widget.GridSpacingItemDecoration;
import com.miaomi.fenbei.base.widget.TMVideoView;
import com.miaomi.fenbei.voice.R;
import com.miaomi.fenbei.voice.ui.mine.creatgod.CommonRecordDialog;
import com.miaomi.fenbei.voice.widget.KMSoundView;
import com.scwang.smartrefresh.layout.util.DensityUtil;
import com.ypx.imagepicker.bean.ImageItem;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class PublishPyqActivity extends BaseActivity implements View.OnClickListener{

    private ImageView soundIv;
    private ImageView videoIv;
    private ImageView photoIv;
    private ArrayList<ImageItem> selectedPhotos = new ArrayList<>();
    private PublishPyqImgAdapter publishPyqImgAdapter;
    private RecyclerView picRv;
    private String soundUrl = "";
    private String videoUrl = "";
    private int videoH = 0;
    private int videoW = 0;
    private int duration = 0;
    private LinearLayout sendTypeLL;
    private LinearLayout soundLl;
    private TMVideoView videoView;
    private TextView pulishTv;
    private String content = "";
    private EditText contentEt;
    private KMSoundView soundView;
    private ImageView deleteRecordIv;
    private TextView recordAgainTv;
    private int type = FindBean.FIND_ITEM_TYPE_WB;
    private LoadingDialog loadingDialog;
    private TextView textNumTv;

    private SparseArray<String> uoploadUrls = new SparseArray<>();

    public static void start(Context context) {
        Intent intent = new Intent(context, PublishPyqActivity.class);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_publish_pyq;
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshView();
    }

    private void refreshView(){
        refreshBt();
        sendTypeLL.setVisibility(View.GONE);
        picRv.setVisibility(View.GONE);
        soundLl.setVisibility(View.GONE);
        videoView.setVisibility(View.GONE);
        if (!TextUtils.isEmpty(soundUrl)){
            type = FindBean.FIND_ITEM_TYPE_YY;
            soundLl.setVisibility(View.VISIBLE);
            return;
        }
        if (!TextUtils.isEmpty(videoUrl)){
            type = FindBean.FIND_ITEM_TYPE_SP;
            videoView.setVisibility(View.VISIBLE);
            return;
        }
        if (selectedPhotos.size() > 0){
            type = FindBean.FIND_ITEM_TYPE_MP;
            picRv.setVisibility(View.VISIBLE);
            return;
        }
        type = FindBean.FIND_ITEM_TYPE_WB;
        sendTypeLL.setVisibility(View.VISIBLE);
    }

    private void refreshBt(){
        if (!TextUtils.isEmpty(soundUrl) || !TextUtils.isEmpty(videoUrl)
                || selectedPhotos.size() > 0 || !TextUtils.isEmpty(content)){
            pulishTv.setBackgroundResource(R.drawable.common_button_enabled_style_new);
            pulishTv.setTextColor(Color.parseColor("#FFFFFF"));
            pulishTv.setSelected(true);

        }else{
            pulishTv.setTextColor(Color.parseColor("#7FFFFFFF"));
            pulishTv.setBackgroundResource(R.drawable.common_button_enabled_style);
            pulishTv.setSelected(false);
        }
    }

    @Override
    public void initView() {
        StatusBarUtil.setStatusBarTextColor(this,true);
        publishPyqImgAdapter = new PublishPyqImgAdapter(PublishPyqActivity.this);
        publishPyqImgAdapter.setOnOprateListener(new PublishPyqImgAdapter.OnOprateListener() {
            @Override
            public void onAdd() {
                choosePhoto();
            }

            @Override
            public void onDelete(int position) {
                selectedPhotos.remove(position);
                publishPyqImgAdapter.setData(selectedPhotos);
                refreshView();
            }
        });
        contentEt = findViewById(R.id.et_content);
        soundIv = findViewById(R.id.iv_sound);
        photoIv = findViewById(R.id.iv_photo);
        videoIv = findViewById(R.id.iv_video);
        soundLl = findViewById(R.id.ll_sound);
        textNumTv = findViewById(R.id.tv_text_num);
        picRv = findViewById(R.id.rv_pic);
        sendTypeLL = findViewById(R.id.ll_select_type);
        videoView = findViewById(R.id.video_view);
        pulishTv = findViewById(R.id.right_tv);
        soundView = findViewById(R.id.sound_view);
        deleteRecordIv = findViewById(R.id.iv_delete_record);
        recordAgainTv = findViewById(R.id.tv_record_again);
        videoView.setOnClickListener(this);
        videoView.setCloseLisener(v -> {
            videoUrl = "";
            videoView.loadImg(videoUrl);
            refreshView();
        });
        picRv.setLayoutManager(new GridLayoutManager(this,3));
        picRv.addItemDecoration(new GridSpacingItemDecoration(3, DensityUtil.dp2px(12f),false));
        picRv.setAdapter(publishPyqImgAdapter);
        contentEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                content = s.toString();
                textNumTv.setText(content.length() +"/200");
                refreshBt();
            }
        });
        photoIv.setOnClickListener(this);
        videoIv.setOnClickListener(this);
        soundIv.setOnClickListener(this);
        pulishTv.setOnClickListener(this);
        deleteRecordIv.setOnClickListener(this);
        recordAgainTv.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.iv_photo){
            choosePhoto();
        }

        if (id == R.id.iv_video){
//            PhotoUtils.getInstance().chooseVideo(PublishPyqActivity.this,new PhotoUtils.OnSelectPhotoListener(){
//                @Override
//                public void onSelected(String url) {
//                    videoUrl = url;
//                    videoView.loadImg(videoUrl);
//                    refreshView();
//                }
//            });
            showVideo();
        }
        if (id == R.id.iv_sound){
            showSoundDialog(30);
        }
        if (id == R.id.right_tv){
            if (pulishTv.isSelected()){
                dealData();
            }
        }
        if (id == R.id.iv_delete_record){
            soundUrl = "";
            duration = 0;
            refreshView();
        }
        if (id == R.id.tv_record_again){
            showSoundDialog(30);
        }
        if (id == R.id.video_view){
            SimplePlayerActivity.start(PublishPyqActivity.this,videoUrl);
        }
    }

    private void showSoundDialog(int recordTime){
        if (CommonLib.INSTANCE.isInRoom()){
            ToastUtil.INSTANCE.suc(PublishPyqActivity.this,"房间中，请退出后使用~");
            return;
        }
        final CommonRecordDialog dialog = new CommonRecordDialog(recordTime);
        dialog.setOnUploadRecordListener((url, duration) -> {
            dialog.dismiss();
            soundUrl = url;
            this.duration = duration;
            soundView.setPath(url);
            soundView.setDuration(duration);
            refreshView();
        });
        dialog.show(getSupportFragmentManager());
    }

    private void choosePhoto(){
        PhotoUtils.getInstance().choosePhoto(PublishPyqActivity.this,selectedPhotos,new PhotoUtils.OnSelectPhotosListener(){

            @Override
            public void onSelected(List<ImageItem> urls) {
                if (urls.size() > 0){
                    String item = urls.get(0).getPath();
                    if (urls.get(0).isVideo()){
                        videoUrl = item;
                        videoView.loadImg(videoUrl);
                    }else{
                        selectedPhotos.addAll(urls);
                        publishPyqImgAdapter.setData(selectedPhotos);
                    }
                    refreshView();
                }

            }
        });
    }

    private void dealData(){
        if (loadingDialog == null){
            loadingDialog = new LoadingDialog(PublishPyqActivity.this);
        }
        loadingDialog.show();
        ArrayList<String> urls = new ArrayList<>();
        if (type == FindBean.FIND_ITEM_TYPE_WB){
            submit(urls);
            return;
        }
        if (type == FindBean.FIND_ITEM_TYPE_SP){
            updateVideo(videoUrl, new UpLoadSuccessCallback() {
                @Override
                public void onSuccess(int index,String url) {
                    urls.add(url);
                    submit(urls);
                }

                @Override
                public void onFaile() {
                    loadingDialog.loginDismiss();
                }
            });
            return;
        }
        if (type == FindBean.FIND_ITEM_TYPE_MP){
            if (selectedPhotos.size() > 0){
                int position = 0;
                for (ImageItem selectedPhoto : selectedPhotos){
                    updatePhotoList(position,selectedPhoto.getPath(), new UpLoadSuccessCallback() {
                        @Override
                        public void onSuccess(int index,String url) {
                            uoploadUrls.put(index,url);
                            if (uoploadUrls.size() >= selectedPhotos.size()){
                                for (int i = 0;i<selectedPhotos.size();i++){{
                                    urls.add(uoploadUrls.get(i));
                                }}
                                submit(urls);
                            }
                        }

                        @Override
                        public void onFaile() {
                            loadingDialog.loginDismiss();
                        }
                    });
                    position++;
                }
            }else {
                loadingDialog.loginDismiss();
            }
            return;
        }
        if (type == FindBean.FIND_ITEM_TYPE_YY){
            urls.add(soundUrl);
            submit(urls);
        }
    }

    public void submit(ArrayList<String> urls){
        NetService.Companion.getInstance(PublishPyqActivity.this).issueFriendsCircle(type, duration, content, urls, new Callback<BaseBean>() {
            @Override
            public void onSuccess(int nextPage, BaseBean bean, int code) {
                loadingDialog.loginDismiss();
            }

            @Override
            public void onSuccessHasMsg(@NotNull String msg, BaseBean bean, int code) {
                ToastUtil.INSTANCE.suc(PublishPyqActivity.this,msg);
                finish();
            }

            @Override
            public void onError(@NotNull String msg, @NotNull Throwable throwable, int code) {
                loadingDialog.loginDismiss();
                ToastUtil.INSTANCE.suc(PublishPyqActivity.this,msg);
            }

            @Override
            public boolean isAlive() {
                return isLive();
            }
        });
    }

    private void updatePhotoList(int position,String imagePath,UpLoadSuccessCallback callback) {
        new Thread(() -> {
            final String fileName = position+"_pyq_" + System.currentTimeMillis() + ".jpg";
            final OSSPutFileUtil putFileUtil = new OSSPutFileUtil(fileName, imagePath, OSSPutFileUtil.FILE_TYPE_GREAT_GOD);
            putFileUtil.uploadFileOriginal(PublishPyqActivity.this, new OSSPutFileUtil.OSSCallBack() {
                @Override
                public void onSuc() {
                    callback.onSuccess(position,putFileUtil.getUrl());
                }

                @Override
                public void onFail(String msg) {
                    callback.onFaile();
                }
            });
        }).start();
    }

    private void updateVideo(String imagePath,UpLoadSuccessCallback callback) {
        if (!imagePath.endsWith("mp4")){
            ToastUtil.INSTANCE.suc(PublishPyqActivity.this,"目前只支持mp4格式");
            callback.onFaile();
            return;
        }
        new Thread(() -> {
//            final String fileName = "pyq_" +System.currentTimeMillis() + ".mp4?w="+videoW+"&h="+videoH;
            final String fileName = "pyq_" +System.currentTimeMillis() + ".mp4";
            final OSSPutFileUtil putFileUtil = new OSSPutFileUtil(fileName, imagePath, OSSPutFileUtil.FILE_TYPE_USER_VIDEO);
            putFileUtil.uploadFileOriginal(PublishPyqActivity.this, new OSSPutFileUtil.OSSCallBack() {
                @Override
                public void onSuc() {
                    callback.onSuccess(0,putFileUtil.getUrl());
                }

                @Override
                public void onFail(String msg) {
                    callback.onFaile();
                }
            });
        }).start();
    }

    interface UpLoadSuccessCallback{
        void onSuccess(int position,String url);
        void onFaile();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 6001){
            if (data != null) {
                videoUrl = data.getStringExtra("path");
                videoW = data.getIntExtra("w",0);
                videoH = data.getIntExtra("h",0);
                videoView.loadImg(videoUrl);
                refreshView();
            }
        }
    }

    private void showVideo(){
        String[] perms = { Manifest.permission.CAMERA};
        if (XXPermissions.isHasPermission(this, perms)) {
            VideoRecordActivity.Companion.start(this);
        } else {
            XXPermissions.with(this)
                    .permission(perms)
                    .request(new OnPermission() {

                        @Override
                        public void hasPermission(List<String> granted, boolean isAll) {
                            VideoRecordActivity.Companion.start(PublishPyqActivity.this);
                        }

                        @Override
                        public void noPermission(List<String> denied, boolean quick) {

                        }
                    });
        }
    }
}
