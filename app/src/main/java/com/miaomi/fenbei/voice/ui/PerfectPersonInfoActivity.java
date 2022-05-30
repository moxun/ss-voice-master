package com.miaomi.fenbei.voice.ui;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.view.TimePickerView;
import com.miaomi.fenbei.base.bean.GetUserRoomIdBean;
import com.miaomi.fenbei.room.ChatRoomManager;
import com.miaomi.fenbei.base.bean.BaseBean;
import com.miaomi.fenbei.base.core.BaseActivity;
import com.miaomi.fenbei.base.core.JoinChatCallBack;
import com.miaomi.fenbei.base.net.Callback;
import com.miaomi.fenbei.base.net.NetService;
import com.miaomi.fenbei.base.util.DataHelper;
import com.miaomi.fenbei.base.util.DensityUtil;
import com.miaomi.fenbei.base.util.ImgUtil;
import com.miaomi.fenbei.base.util.OSSPutFileUtil;
import com.miaomi.fenbei.base.util.PhotoUtils;
import com.miaomi.fenbei.base.util.TimeUtil;
import com.miaomi.fenbei.base.util.ToastUtil;
import com.miaomi.fenbei.voice.R;

import org.jetbrains.annotations.NotNull;

import java.util.Calendar;

public class PerfectPersonInfoActivity extends BaseActivity implements View.OnClickListener {
    private TextView manIv;
    private TextView womenIv;
    private ImageView faceIv;
    private LinearLayout seletedBirthLL;
    private EditText nameEt;
    private TextView submitTv;
    //    public static final String INFO_FACE = "INFO_FACE";
//    public static final String INFO_NAME = "INFO_NAME";
    private String face = "";
    private String name = "";
    private TimePickerView pvCustomTime;
    private TextView birthTv;
    private int sex;
    String birth = "";
    private PhotoUtils photoUtils;
    private EditText codeEt;
    private int roomId = 0;

    public static void start(Context context) {
        Intent intent = new Intent(context, PerfectPersonInfoActivity.class);
        context.startActivity(intent);
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_perfact_person_info;
    }

    @Override
    public void initView() {
        setBaseStatusBar(false, false);
        photoUtils = PhotoUtils.getInstance();
        if (DataHelper.INSTANCE.getUserInfo() != null) {
            face = DataHelper.INSTANCE.getUserInfo().getFace();
            name = DataHelper.INSTANCE.getUserInfo().getNickname();
            sex = DataHelper.INSTANCE.getUserInfo().getGender();
            birth = DataHelper.INSTANCE.getUserInfo().getBirth();
        }
        findViewById(R.id.right_tv).setOnClickListener(this);
        manIv = findViewById(R.id.iv_sex_selet_man);
        womenIv = findViewById(R.id.iv_sex_selet_women);
        faceIv = findViewById(R.id.iv_face);
        nameEt = findViewById(R.id.name_et);
        submitTv = findViewById(R.id.tv_submit);
        birthTv = findViewById(R.id.tv_birth);
        codeEt = findViewById(R.id.et_code);
        seletedBirthLL = findViewById(R.id.ll_select_birth);
        seletedBirthLL.setOnClickListener(this);
        manIv.setOnClickListener(this);
        womenIv.setOnClickListener(this);
        submitTv.setOnClickListener(this);
        faceIv.setOnClickListener(this);
        if (DataHelper.INSTANCE.isFromThird()) {
            nameEt.setText(name);
        }
        birthTv.setText(birth);
        if (sex == 1) {
            manIv.setSelected(true);
            womenIv.setSelected(false);
        } else {
            manIv.setSelected(false);
            womenIv.setSelected(true);
        }
        getData();
        ImgUtil.INSTANCE.loadCircleImg(PerfectPersonInfoActivity.this, face, faceIv, R.drawable.common_avter_placeholder);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.iv_sex_selet_man) {
            sex = 1;
            manIv.setSelected(true);
            womenIv.setSelected(false);
        }
        if (id == R.id.right_tv){
            DataHelper.INSTANCE.saveIsNewUser(false);
            finish();
        }
        if (id == R.id.iv_sex_selet_women) {
            sex = 2;
            manIv.setSelected(false);
            womenIv.setSelected(true);
        }
        if (id == R.id.ll_select_birth) {
            DensityUtil.INSTANCE.hideSoftKeyboard(this);
            showTimeView();
        }
        if (id == R.id.tv_submit) {
            submit();
        }
        if (id == R.id.iv_face) {
            photoUtils.chooseAvter(this, url -> updataAvter(url));
        }
    }

    private void submit() {
        String nickName = nameEt.getText().toString();
        String code = codeEt.getText().toString();
        if (TextUtils.isEmpty(nickName)) {
            ToastUtil.INSTANCE.error(this, "请输入昵称");
            return;
        }
        NetService.Companion.getInstance(PerfectPersonInfoActivity.this).pecfectInfo(nickName, sex, birth, code, face, new Callback<BaseBean>() {
            @Override
            public void onSuccess(int nextPage, BaseBean bean, int code) {
                DataHelper.INSTANCE.saveIsNewUser(false);
                if (roomId != 0) {
                    ChatRoomManager.INSTANCE.joinChat(PerfectPersonInfoActivity.this,""+ roomId, new JoinChatCallBack() {
                        @Override
                        public void onSuc() {
                            finish();
                        }

                        @Override
                        public void onFail(@NotNull String msg) {
                            finish();
                        }
                    });

                } else {
                    finish();
                }
            }

            @Override
            public void onError(@NotNull String msg, @NotNull Throwable throwable, int code) {
                ToastUtil.INSTANCE.suc(PerfectPersonInfoActivity.this, msg);
            }

            @Override
            public boolean isAlive() {
                return isLive();
            }
        });
    }

    private void getData(){
        NetService.Companion.getInstance(this).getUserRoomId(new Callback<GetUserRoomIdBean>() {
            @Override
            public void onSuccess(int nextPage, GetUserRoomIdBean bean, int code) {
                roomId = bean.getRoom_id();
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

    private void showTimeView() {


        Calendar selectedDate = Calendar.getInstance();//系统当前时间
        Calendar startDate = Calendar.getInstance();
        Calendar endData = Calendar.getInstance();
        int startYear = selectedDate.get(Calendar.YEAR) - 18;
        startDate.set(1918, 1, 23);
        endData.set(startYear, selectedDate.get(Calendar.MONTH), selectedDate.get(Calendar.MONDAY));
        pvCustomTime = new TimePickerBuilder(this, (date, v) -> {//选中事件回调
            birthTv.setText(TimeUtil.getTime(date.getTime()));
            birth = birthTv.getText().toString();
        }).setDate(endData)
                .setRangDate(startDate, endData)
                .setCancelColor(0xFFFD7F8F)
                .setSubmitColor(0xFFFD7F8F)
                .setContentTextSize(20)
                .setType(new boolean[]{true, true, true, false, false, false})
                .setLabel("年", "月", "日", "时", "分", "秒")
                .setLineSpacingMultiplier(1.2f)
                .setTextXOffset(0, 0, 0, 40, 0, -40)
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setDividerColor(0xFFFD7F8F)
                .build();
        pvCustomTime.show();

    }


    private void updataAvter(String imagePath) {
        final String fileName = "" + DataHelper.INSTANCE.getUID() + System.currentTimeMillis() + ".jpg";
        final OSSPutFileUtil putFileUtil = new OSSPutFileUtil(fileName, imagePath, OSSPutFileUtil.FILE_TYPE_AVATAR);
        putFileUtil.uploadAvterByBitmap(this, new OSSPutFileUtil.OSSCallBack() {
            @Override
            public void onSuc() {
                face = putFileUtil.getUrl();
                ImgUtil.INSTANCE.loadFaceIcon(PerfectPersonInfoActivity.this, face, faceIv);
            }

            @Override
            public void onFail(String msg) {
            }
        });

    }




}
