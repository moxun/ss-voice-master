package com.miaomi.fenbei.voice.ui;

import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.miaomi.fenbei.base.bean.BaseBean;
import com.miaomi.fenbei.base.bean.ExpressItemBean;
import com.miaomi.fenbei.base.core.BaseActivity;
import com.miaomi.fenbei.base.net.Callback;
import com.miaomi.fenbei.base.net.NetService;
import com.miaomi.fenbei.base.util.ToastUtil;
import com.miaomi.fenbei.base.widget.xrecyclerview.XRecyclerView;
import com.miaomi.fenbei.room.widget.GridRadioGroup;
import com.miaomi.fenbei.voice.R;
import com.miaomi.fenbei.voice.ui.mine.user_homepage.UserHomepageActivity;

import org.jetbrains.annotations.NotNull;

import java.util.List;
@Route(path ="/app/report")
public class ReportActivity extends BaseActivity {



    public final static String USER_ID = "USER_ID";
    public final static String REPORT_OBJECT = "REPORT_OBJECT";
    private String usre_id;
    public static void start(Context context,String userid,int report_object){
        Intent intent = new Intent(context, ReportActivity.class);
        intent.putExtra(USER_ID,userid);
        intent.putExtra(REPORT_OBJECT,report_object);
        context.startActivity(intent);
    }
  private RadioGroup reportRaDioGp;
    private RadioButton reportRb1,reportRb2,reportRb3,reportRb4;
    private TextView saveTv;
    private int type;
    private  int reportObject;
    private EditText noticeEt;
    private TextView noticeLimitTv;
    @Override
    public int getLayoutId() {
        return R.layout.activity_report;
    }

    @Override
    public void initView() {
        ARouter.getInstance().inject(this);
        setBaseStatusBar(false,false);


        usre_id = getIntent().getStringExtra(USER_ID);
        reportObject = getIntent().getIntExtra(REPORT_OBJECT,0);
        reportRaDioGp=findViewById(R.id.report_radio_group);
        noticeLimitTv = findViewById(R.id.chat_notice_limit);
        noticeEt = findViewById(R.id.chat_notice_et);
        reportRb1=findViewById(R.id.report_1);
        reportRb2=findViewById(R.id.report_2);
        reportRb3=findViewById(R.id.report_3);
        reportRb4=findViewById(R.id.report_4);
        saveTv= findViewById(R.id.tv_save);
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
        reportRaDioGp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                //在这里同时可以根据小组定义数据传递到服务器；
                if(checkedId==R.id.report_1){
                    type=0;
                }else if(checkedId==R.id.report_2){
                    type=1;
                }else if(checkedId==R.id.report_3){
                    type=2;
                }else{
                    type=3;
                }
            }
        });
        saveTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NetService.Companion.getInstance(ReportActivity.this).report(usre_id, reportObject, type, new Callback<BaseBean>() {
                    @Override
                    public void onSuccess(int nextPage, BaseBean bean, int code) {
                        ToastUtil.INSTANCE.suc(ReportActivity.this, "举报成功");
                       finish();
                    }

                    @Override
                    public void onError(@NotNull String msg, @NotNull Throwable throwable, int code) {
                        ToastUtil.INSTANCE.suc(ReportActivity.this, msg);
                    }

                    @Override
                    public boolean isAlive() {
                        return isLive();
                    }
                });
            }

        });
    }

}
