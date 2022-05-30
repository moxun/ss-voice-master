package com.miaomi.fenbei.voice.ui.mine.identity_authentication;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.miaomi.fenbei.base.bean.BaseBean;
import com.miaomi.fenbei.base.bean.LocalUserBean;
import com.miaomi.fenbei.base.core.BaseActivity;
import com.miaomi.fenbei.base.net.Callback;
import com.miaomi.fenbei.base.net.NetService;
import com.miaomi.fenbei.base.util.DataHelper;
import com.miaomi.fenbei.base.util.ImgUtil;
import com.miaomi.fenbei.base.util.OSSPutFileUtil;
import com.miaomi.fenbei.base.util.PhotoUtils;
import com.miaomi.fenbei.base.util.ToastUtil;
import com.miaomi.fenbei.voice.R;

import org.jetbrains.annotations.NotNull;

@Route(path = "/app/identityAuthentication")
public class IdentityAuthenticationActivity extends BaseActivity implements View.OnClickListener {


    private CountDownTimer mCountDownTimer;
    private TextView sendYzmTv;
    private EditText etPhoneNumber;
    private EditText etRealName;
    private EditText etIdCard;
    private EditText etCode;
    private TextView authenticationBt;
    private PhotoUtils photoUtils;
    private ImageView idcardZMIV;
    private ImageView idcardFMIV;
//    private ImageView idcardSCIv;
    private String idcard_front = "";
    private String idcard_reverse = "";
//    private String card_in_hand = "";
    private int selectCardType = 0;//0 正面 1反面 2手持


    public static Intent getIntent(Context context) {
        return new Intent(context, IdentityAuthenticationActivity.class);
    }

    @Override
    public int getLayoutId() {
        return R.layout.user_activity_identity_authentication;
    }

    @Override
    public void initView() {
        setBaseStatusBar(false,false);
        photoUtils = PhotoUtils.getInstance();
        idcardFMIV = findViewById(R.id.iv_idcard_back);
        idcardZMIV = findViewById(R.id.iv_idcard_facade);
        sendYzmTv = findViewById(R.id.tv_send_yzm);
        etPhoneNumber = findViewById(R.id.et_phone);
        etRealName = findViewById(R.id.et_real_name);
        etIdCard = findViewById(R.id.et_id_card);
        etCode = findViewById(R.id.et_code);
        authenticationBt = findViewById(R.id.tv_authentication);
//        idcardSCIv = findViewById(R.id.iv_idcard_in_hand);
        authenticationBt.setOnClickListener(this);
        idcardZMIV.setOnClickListener(this);
        sendYzmTv.setOnClickListener(this);
        idcardFMIV.setOnClickListener(this);
//        idcardSCIv.setOnClickListener(this);
        mCountDownTimer = new CountDownTimer(60000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                sendYzmTv.setTextColor(Color.parseColor("#aaaaaa"));
                sendYzmTv.setText((millisUntilFinished/1000) + "s");
                sendYzmTv.setClickable(false);
            }

            @Override
            public void onFinish() {
                sendYzmTv.setTextColor(Color.parseColor("#FD7F8F"));
                sendYzmTv.setText("获取验证码");
                sendYzmTv.setClickable(true);
            }
        };
    }



    private void submit() {
        String phoneNumber = etPhoneNumber.getText().toString();
        String code = etCode.getText().toString();
        String realName = etRealName.getText().toString();
        String idCardStr = etIdCard.getText().toString();
        if (realName.isEmpty()) {
            ToastUtil.INSTANCE.error(this, "请输入真实姓名");
            return;
        }
        if (idCardStr.isEmpty() || idCardStr.length() < 18) {
            ToastUtil.INSTANCE.error(this, "请输入正确的身份证号码");
            return;
        }
        if (idcard_front.isEmpty() || idcard_reverse.isEmpty()) {
            ToastUtil.INSTANCE.error(this, "请上传身份证");
            return;
        }
        if (TextUtils.isEmpty(phoneNumber) || phoneNumber.length() < 11 ) {
            ToastUtil.INSTANCE.error(IdentityAuthenticationActivity.this,"手机号码格式错误，请重新输入");
            return ;
        }
        NetService.Companion.getInstance(this).submitAuthentication("", idcard_front,idcard_reverse,realName, idCardStr, phoneNumber, code, new Callback<BaseBean>() {
            @Override
            public void onSuccess(int nextPage, BaseBean bean, int code) {
                //设置实名认证审核中
                LocalUserBean data = DataHelper.INSTANCE.getUserInfo();
                if (data != null){
                    data.setIdentify_status(1);
                    DataHelper.INSTANCE.updatalUserInfo(data);
                }
                startActivity(ExamineActivity.getIntent(IdentityAuthenticationActivity.this));
                setResult(RESULT_OK);
                finish();
            }

            @Override
            public void onError(@NotNull String msg, @NotNull Throwable throwable, int code) {
                ToastUtil.INSTANCE.error(IdentityAuthenticationActivity.this, msg);
            }

            @Override
            public boolean isAlive() {
                return isLive();
            }
        });
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.tv_send_yzm){
            sendCode();
        }
        if (id == R.id.tv_authentication){
            submit();
        }
        if (id == R.id.iv_idcard_facade){
            selectCardType = 0;
            photoUtils.chooseOriginGallary(IdentityAuthenticationActivity.this, url -> updataAvter(url));
        }
        if (id == R.id.iv_idcard_back){
            selectCardType = 1;
            photoUtils.chooseOriginGallary(IdentityAuthenticationActivity.this, url -> updataAvter(url));
        }
//        if (id == R.id.iv_idcard_in_hand){
//            selectCardType = 2;
//            photoUtils.chooseOriginGallary(IdentityAuthenticationActivity.this, url -> updataAvter(url));
//        }
    }

    private void sendCode(){
        String phone = etPhoneNumber.getText().toString();
        if (TextUtils.isEmpty(phone) || phone.length() != 11 ) {
            ToastUtil.INSTANCE.error(IdentityAuthenticationActivity.this,"手机号码格式错误，请重新输入");
            return ;
        }
        NetService.Companion.getInstance(this).getCode(phone, 4, new Callback<BaseBean>() {
            @Override
            public void onSuccess(int nextPage, BaseBean bean, int code) {
                ToastUtil.INSTANCE.suc(IdentityAuthenticationActivity.this, "验证码发送成功");
                mCountDownTimer.start();
            }

            @Override
            public void onError(@NotNull String msg, @NotNull Throwable throwable, int code) {
                ToastUtil.INSTANCE.error(IdentityAuthenticationActivity.this, msg);
            }

            @Override
            public boolean isAlive() {
                return isLive();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCountDownTimer.cancel();
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == RESULT_OK){
//            if (requestCode == PhotoUtils.GALLERY_REQUEST) {
//                final String url = data.getStringExtra(AlbumActivity.RESULT_PICTURE);
//                updataAvter(url);
////                tokePhotoUtils.cropRawPhoto(this, Uri.parse(url), TokePhotoUtils.GALLERY_REQUEST);
//            }
////            if (requestCode == TokePhotoUtils.CROP_REQUEST){
////                updataAvter(tokePhotoUtils.getImgFile().getAbsolutePath());
////            }
//        }
//    }

    private void updataAvter(String imagePath){
        final String fileName = ""+ DataHelper.INSTANCE.getUID()+System.currentTimeMillis()+".jpg";
        final OSSPutFileUtil putFileUtil = new OSSPutFileUtil(fileName, imagePath,OSSPutFileUtil.FILE_TYPE_FACE_ID_CARD);
        putFileUtil.uploadFileOriginal(this, new OSSPutFileUtil.OSSCallBack() {
            @Override
            public void onSuc() {

                if (selectCardType ==0){
                    idcard_front = putFileUtil.getUrl();
                    ImgUtil.INSTANCE.loadImg(IdentityAuthenticationActivity.this,putFileUtil.getUrl(),idcardZMIV,R.drawable.common_banner_plachodler);
                }
                if (selectCardType ==1){
                    idcard_reverse = putFileUtil.getUrl();
                    ImgUtil.INSTANCE.loadImg(IdentityAuthenticationActivity.this,putFileUtil.getUrl(),idcardFMIV,R.drawable.common_banner_plachodler);
                }
//                if (selectCardType ==2){
//                    card_in_hand = putFileUtil.getUrl();
//                    ImgUtil.INSTANCE.loadImg(IdentityAuthenticationActivity.this,putFileUtil.getUrl(),idcardSCIv,R.drawable.common_banner_plachodler);
//                }
            }
            @Override
            public void onFail(String msg) {
                Log.e("msg","==="+msg);
            }
        });

    }
}
