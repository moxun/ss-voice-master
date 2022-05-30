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

public class EditFamilyActivity extends BaseActivity {


    private ImageView iconIv;
    private PhotoUtils tokePhotoUtils;
    private TextView noticeLimitTv,chatfmlimitTv;
    private EditText noticeEt;
    public final static String FAMILY_ID = "FAMILY_ID";
    public final static String FAMILY_NAME = "FAMILY_NAME";
    public final static String FAMILY_ICON = "FAMILY_ICON";
    public final static String FAMILY_NOTE = "FAMILY_NOTE";
    public final static String FAMILY_NICKNAME = "FAMILY_NICKNAME";
    private String familyId = "";
    private String familyName = "";
    private String familyIcon = "";
    private String familyNote = "";
    private String familynickName = "";
    private EditText familyNameEt;
    private TextView saveTv;


    public static void start(Context context,String familyId,String familyName,String familyIcon,String familyNote,String nickName){
        Intent intent = new Intent(context, EditFamilyActivity.class);
        intent.putExtra(FAMILY_ID,familyId);
        intent.putExtra(FAMILY_NAME,familyName);
        intent.putExtra(FAMILY_ICON,familyIcon);
        intent.putExtra(FAMILY_NOTE,familyNote);
        intent.putExtra(FAMILY_NICKNAME,nickName);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_edit_family;
    }

    @Override
    public void initView() {
        setBaseStatusBar(false,false);
        familyId = getIntent().getStringExtra(FAMILY_ID);
        familyName = getIntent().getStringExtra(FAMILY_NAME);
        familyIcon = getIntent().getStringExtra(FAMILY_ICON);
        familyNote = getIntent().getStringExtra(FAMILY_NOTE);
        familynickName = getIntent().getStringExtra(FAMILY_NICKNAME);
        tokePhotoUtils = PhotoUtils.getInstance();
        iconIv = findViewById(R.id.iv_icon);
        noticeLimitTv = findViewById(R.id.chat_notice_limit);
        noticeEt = findViewById(R.id.chat_notice_et);
        familyNameEt = findViewById(R.id.et_family_name);
        saveTv = findViewById(R.id.tv_save);
        chatfmlimitTv=findViewById(R.id.chat_fm_limit);
        saveTv.setOnClickListener(v -> editFamily());
        iconIv.setOnClickListener(v -> tokePhotoUtils.chooseGallary(EditFamilyActivity.this, url -> updataAvter(url)));
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
        noticeEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                noticeLimitTv.setText(s.toString().length()+"/200");
            }
        });
        ImgUtil.INSTANCE.loadCircleImg(this,familyIcon,iconIv,R.drawable.common_avter_placeholder);
        noticeEt.setText(familyNote);
        familyNameEt.setText(familyName);
        familyNameEt.setSelection(familyName.length());
    }

    private void updataAvter(String imagePath){
        final String fileName = ""+ DataHelper.INSTANCE.getUID()+System.currentTimeMillis()+".jpg";
        final OSSPutFileUtil putFileUtil = new OSSPutFileUtil(fileName, imagePath,OSSPutFileUtil.FILE_TYPE_AVATAR);
        putFileUtil.uploadRoomIconByBitmap(this, new OSSPutFileUtil.OSSCallBack() {
            @Override
            public void onSuc() {
                familyIcon = putFileUtil.getUrl();
                ImgUtil.INSTANCE.loadCircleImg(EditFamilyActivity.this,familyIcon,iconIv, R.drawable.common_avter_placeholder);
            }
            @Override
            public void onFail(String msg) {
            }
        });
    }
    private void editFamily(){
        String familyName = familyNameEt.getText().toString();
        String note = noticeEt.getText().toString();
        if (TextUtils.isEmpty(familyName)){
            ToastUtil.INSTANCE.i(this,"家族名称不能为空");
            return;
        }
        if (TextUtils.isEmpty(note)){
            ToastUtil.INSTANCE.i(this,"家族公告不能为空");
            return;
        }
//        CreateFamilyDialog outDialog = new CreateFamilyDialog(EditFamilyActivity.this);
//        outDialog.setFamilyName(familyName);
//        outDialog.setIconUrl(familyIcon);
//        outDialog.setNotice(note);
//        outDialog.setPatriarch(familynickName);
//        outDialog.setLeftBt("取消", new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                outDialog.dismiss();
//            }
//        });
//        outDialog.setRightBt("确定", new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                outDialog.dismiss();
                NetService.Companion.getInstance(EditFamilyActivity.this).editFamily(familyId,familyName, familyIcon, note, new Callback<Object>() {
                    @Override
                    public void onSuccess(int nextPage, Object bean, int code) {
                        ToastUtil.INSTANCE.suc(EditFamilyActivity.this,"保存成功");
                        finish();
                    }

                    @Override
                    public void onError(@NotNull String msg, @NotNull Throwable throwable, int code) {
                        ToastUtil.INSTANCE.suc(EditFamilyActivity.this,msg);
                    }

                    @Override
                    public boolean isAlive() {
                        return isLive();
                    }
                });
//            }
//        });
//        outDialog.show();
    }
}
