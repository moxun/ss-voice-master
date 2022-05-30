//package com.miaomi.fenbei.voice.ui;
//
//import android.content.Context;
//import android.content.Intent;
//import android.text.TextUtils;
//import android.widget.EditText;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.miaomi.fenbei.voice.BaseActivity;
//import com.miaomi.fenbei.voice.Callback;
//import com.miaomi.fenbei.voice.NetService;
//import com.miaomi.fenbei.voice.DataHelper;
//import com.miaomi.fenbei.base.util.TimeUtil;
//import com.miaomi.fenbei.voice.ToastUtil;
//import com.miaomi.fenbei.voice.R;
//
//import org.jetbrains.annotations.NotNull;
//
//public class InviteCodeActivity extends BaseActivity {
//    private ImageView backIv;
//    private TextView submitTv;
//    private TextView timeTv;
//    private EditText inviteCodeEt;
//
//    public static void start(Context context){
//        Intent intent = new Intent(context,InviteCodeActivity.class);
//        context.startActivity(intent);
//    }
//
//    @Override
//    public int getLayoutId() {
//        return R.layout.activity_invite_code;
//    }
//
//    @Override
//    public void initView() {
//        setBaseStatusBar(false,false);
//        submitTv = findViewById(R.id.tv_bind);
//        backIv = findViewById(R.id.iv_back);
//        timeTv = findViewById(R.id.tv_time);
//        inviteCodeEt = findViewById(R.id.et_invite_code);
//        submitTv.setOnClickListener(v -> submit());
//        backIv.setOnClickListener(v -> finish());
//        if (DataHelper.INSTANCE.getUserInfo() != null){
//            timeTv.setText("剩余时间："+TimeUtil.getTimeSFM(DataHelper.INSTANCE.getUserInfo().getCode_time()));
//        }
//    }
//    private void submit(){
//        if (TextUtils.isEmpty(inviteCodeEt.getText().toString())){
//            ToastUtil.INSTANCE.suc(InviteCodeActivity.this,"请输入邀请码");
//            return;
//        }
//        NetService.Companion.getInstance(InviteCodeActivity.this).bindInviteCode(inviteCodeEt.getText().toString(), new Callback<Object>() {
//            @Override
//            public boolean isAlive() {
//                return isLive();
//            }
//
//            @Override
//            public void onError(@NotNull String msg, @NotNull Throwable throwable, int code) {
//                ToastUtil.INSTANCE.suc(InviteCodeActivity.this,msg);
//            }
//
//            @Override
//            public void onSuccess(int nextPage, Object bean, int code) {
//                ToastUtil.INSTANCE.suc(InviteCodeActivity.this,"绑定成功");
//            }
//        });
//    }
//}
