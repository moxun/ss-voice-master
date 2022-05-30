package com.miaomi.fenbei.voice.ui.family;

import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


import com.miaomi.fenbei.base.bean.MineBean;
import com.miaomi.fenbei.base.core.BaseActivity;
import com.miaomi.fenbei.base.dialog.CreateFamilyDialog;
import com.miaomi.fenbei.base.net.Callback;
import com.miaomi.fenbei.base.net.NetService;
import com.miaomi.fenbei.base.util.DataHelper;
import com.miaomi.fenbei.base.util.ImgUtil;
import com.miaomi.fenbei.base.util.OSSPutFileUtil;
import com.miaomi.fenbei.base.util.PhotoUtils;
import com.miaomi.fenbei.base.util.ToastUtil;
import com.miaomi.fenbei.voice.R;

import org.jetbrains.annotations.NotNull;

public class CreateFamilyActivity extends BaseActivity {

    private EditText familyNameEt;
    private EditText familyNoticeEt;
//    private EditText wechatEt;
//    private EditText numEt;
//    private EditText platformEt;
private TextView chatfmlimitNoticeTv;
    private TextView applyTv;
    private TextView chatfmlimitTv;
    private ImageView iconIv;
    private PhotoUtils tokePhotoUtils;
    private String icon;
    private MineBean mBean;
    public static void start(Context context){
        Intent intent = new Intent(context, CreateFamilyActivity.class);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_create_family;
    }

    @Override
    public void initView() {
        setBaseStatusBar(false,false);
        tokePhotoUtils = PhotoUtils.getInstance();
        familyNameEt = findViewById(R.id.et_fm_name);
//        wechatEt = findViewById(R.id.et_wechat);
//        numEt = findViewById(R.id.et_num);
//        platformEt = findViewById(R.id.et_platform);
        chatfmlimitNoticeTv=findViewById(R.id.chat_fm_notice_limit);
        familyNoticeEt=findViewById(R.id.et_fm_notice);
        applyTv = findViewById(R.id.tv_apply);
        iconIv = findViewById(R.id.iv_icon);
        chatfmlimitTv=findViewById(R.id.chat_fm_limit);
        findViewById(R.id.iv_back).setOnClickListener(v -> finish());
        iconIv.setOnClickListener(v -> tokePhotoUtils.chooseGallary(CreateFamilyActivity.this, url -> updataAvter(url)));
        applyTv.setOnClickListener(v -> createFamily());
        familyNoticeEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                chatfmlimitNoticeTv.setText(s.toString().length()+"/200");
            }
        });
        familyNameEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                chatfmlimitTv.setText(s.toString().length()+"/15");
            }
        });
        NetService.Companion.getInstance(CreateFamilyActivity.this).getMineInfo(this.getPackageName(), new Callback<MineBean>() {
            @Override
            public void onSuccess(int nextPage, MineBean bean, int code) {
                mBean = bean;

            }

            @Override
            public void onError(@NotNull String msg, @NotNull Throwable throwable, int code) {

            }

            @Override
            public boolean isAlive() {
                return isLive();
            }
        });
    }

    private void createFamily(){
        if (TextUtils.isEmpty(icon)){
            ToastUtil.INSTANCE.error(this,"头像不能为空");
            return;
        }
        String familyName = familyNameEt.getText().toString();
        if (TextUtils.isEmpty(familyName)){
            ToastUtil.INSTANCE.error(this,"家族名称不能为空");
            return;
        }
        String familyNotice= familyNoticeEt.getText().toString();
//        String wechat = wechatEt.getText().toString();
        if (TextUtils.isEmpty(familyNotice)){
            ToastUtil.INSTANCE.error(this,"公告不能为空");
            return;
        }
//        String platform = platformEt.getText().toString();
//        if (TextUtils.isEmpty(platform)){
//            ToastUtil.INSTANCE.error(this,"合作机构不能为空");
//            return;
//        }
//        String num = numEt.getText().toString();
//        if (TextUtils.isEmpty(num)){
//            ToastUtil.INSTANCE.error(this,"人数不能为空");
//            return;
//        }

        CreateFamilyDialog outDialog = new CreateFamilyDialog(CreateFamilyActivity.this);
        outDialog.setFamilyName(familyName);
        outDialog.setIconUrl(icon);
        outDialog.setNotice(familyNotice);
        if(mBean!=null){
            outDialog.setPatriarch(mBean.getNickname());
        }

        outDialog.setLeftBt("取消", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                outDialog.dismiss();
            }
        });
        outDialog.setRightBt("确定", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                outDialog.dismiss();
                NetService.Companion.getInstance(CreateFamilyActivity.this).createFamily(familyName, icon,familyNotice, new Callback<Object>() {
                    @Override
                    public void onSuccess(int nextPage, Object bean, int code) {
                        ToastUtil.INSTANCE.suc(CreateFamilyActivity.this,"创建成功");
                        finish();
                    }

                    @Override
                    public void onError(@NotNull String msg, @NotNull Throwable throwable, int code) {
                        ToastUtil.INSTANCE.i(CreateFamilyActivity.this,msg);
                    }

                    @Override
                    public boolean isAlive() {
                        return isLive();
                    }
                });
            }
        });
        outDialog.show();

    }

    private void updataAvter(String imagePath){
        final String fileName = ""+ DataHelper.INSTANCE.getUID()+System.currentTimeMillis()+".jpg";
        final OSSPutFileUtil putFileUtil = new OSSPutFileUtil(fileName, imagePath,OSSPutFileUtil.FILE_TYPE_AVATAR);
        putFileUtil.uploadRoomIconByBitmap(this, new OSSPutFileUtil.OSSCallBack() {
            @Override
            public void onSuc() {
                icon = putFileUtil.getUrl();
                ImgUtil.INSTANCE.loadCircleImg(CreateFamilyActivity.this,icon,iconIv, R.drawable.common_avter_placeholder);
            }
            @Override
            public void onFail(String msg) {
            }
        });

    }
}
