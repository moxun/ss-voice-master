package com.miaomi.fenbei.voice.ui;

import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.miaomi.fenbei.base.bean.BaseBean;
import com.miaomi.fenbei.base.bean.RadioStationBean;
import com.miaomi.fenbei.base.core.BaseActivity;
import com.miaomi.fenbei.base.dialog.TimeRangePickerDialog;
import com.miaomi.fenbei.base.net.Callback;
import com.miaomi.fenbei.base.net.NetService;
import com.miaomi.fenbei.base.util.DataHelper;
import com.miaomi.fenbei.base.util.ImgUtil;
import com.miaomi.fenbei.base.util.OSSPutFileUtil;
import com.miaomi.fenbei.base.util.PhotoUtils;
import com.miaomi.fenbei.base.util.ToastUtil;
import com.miaomi.fenbei.room.ChatRoomManager;
import com.miaomi.fenbei.voice.R;
import com.miaomi.fenbei.voice.ui.mine.editinfo.EditUserInfoActivity;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ColumnEditActivity extends BaseActivity {


    public final static String TAG_TYPE = "TAG_TYPE";
private  RadioStationBean radioStationBean=new RadioStationBean();
   private ImageView chatIconIv;
   private String mIcon= "";
   private EditText column_titleEt;
    private EditText topicEt;
    private EditText host_idEt;
    private EditText et_introductionEt;
    private TextView data_timeTv;
    private TextView topic_numTv;
    private TextView column_title_numTv;
    private TextView introductionTv;
    private TextView createColumnChatBtn;


    public static void start(Context context, RadioStationBean radioStationBean){
        Intent intent = new Intent(context, ColumnEditActivity.class);
//        intent.putExtra(TAG_TYPE,type);
        intent.putExtra("radioStationBean",radioStationBean);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_column_edit;
    }

    @Override
    public void initView() {
        setBaseStatusBar(false,false);
        radioStationBean= (RadioStationBean) getIntent().getSerializableExtra("radioStationBean");
        chatIconIv= findViewById(R.id.chat_icon);
        column_titleEt=findViewById(R.id.et_column_title);
        column_title_numTv= findViewById(R.id.tv_column_title_num);
        data_timeTv=findViewById(R.id.tv_data_time);
        topicEt=findViewById(R.id.et_topic);
        topic_numTv=findViewById(R.id.tv_topic_num);
        host_idEt=findViewById(R.id.et_host_id);
        et_introductionEt=findViewById(R.id.et_introduction);
        introductionTv=findViewById(R.id.tv_introduction_num);
        createColumnChatBtn=findViewById(R.id.create_column_chat);
        getData();
        chatIconIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhotoUtils.getInstance().chooseAvter(ColumnEditActivity.this, new PhotoUtils.OnSelectPhotoListener() {
                    @Override
                    public void onSelected(String url) {
                        uploadImg(url);
                    }
                });
            }
        });
        createColumnChatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createColumn();
            }
        });
        data_timeTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateDialog();
            }
        });
    }
    private void uploadImg(String imagePath) {
        String fileName = DataHelper.INSTANCE.getUID()+""+ System.currentTimeMillis() + ".jpg";
        OSSPutFileUtil putFileUtil =new  OSSPutFileUtil(fileName, imagePath,OSSPutFileUtil.FILE_TYPE_AVATAR) ;
        putFileUtil.uploadRoomIconByBitmap(this, new OSSPutFileUtil.OSSCallBack() {
            @Override
            public void onSuc() {
               mIcon = putFileUtil.getUrl();
                ImgUtil.INSTANCE.loadRoundImg(getApplicationContext(), putFileUtil.getUrl(), chatIconIv, 8F, R.drawable.common_default);
            }

            @Override
            public void onFail(String msg) {
                ToastUtil.INSTANCE.error(getApplicationContext(), msg);
            }
        });
    }

    private  void  DateDialog(){
        TimeRangePickerDialog dialog = new TimeRangePickerDialog(this,"00:00-00:00", new TimeRangePickerDialog.ConfirmAction() {
            @Override
            public void onLeftClick() {

            }

            @Override
            public void onRightClick(String startAndEndTime) {
                data_timeTv.setText(startAndEndTime);
            }
        });
        dialog.show();



    }
    private void getData(){
        if(radioStationBean!=null){
            column_titleEt.setText(radioStationBean.getRadio_name());
            data_timeTv.setText(radioStationBean.getTime_period_start()+"-"+radioStationBean.getTime_period_end());
            topicEt.setText(radioStationBean.getToday_topic());
            host_idEt.setText(radioStationBean.getUser_id()+"");
            et_introductionEt.setText(radioStationBean.getIntroduction());
            mIcon =radioStationBean.getIcon();
            column_title_numTv.setText(column_titleEt.getText().length()+"/8");
            topic_numTv.setText(topicEt.getText().length()+"/20");
            introductionTv.setText(et_introductionEt.getText().length()+"/100");
            ImgUtil.INSTANCE.loadRoundImg(this, radioStationBean.getIcon(), chatIconIv, 8F, R.drawable.common_default);
        }

        column_titleEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                column_title_numTv.setText(column_titleEt.getText().length()+"/8");
            }
        });
        topicEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                topic_numTv.setText(topicEt.getText().length()+"/20");
            }
        });
        et_introductionEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
            @Override
            public void afterTextChanged(Editable s) {
                introductionTv.setText(et_introductionEt.getText().length()+"/100");
            }
        });
    }
  private  void  createColumn(){
      String columnTitle= column_titleEt.getText().toString();
      String dataTime= data_timeTv.getText().toString();
      String Topic= topicEt.getText().toString();
      String hostId= host_idEt.getText().toString();
      String Introduction= et_introductionEt.getText().toString();
      if (TextUtils.isEmpty(mIcon)) {
          ToastUtil.INSTANCE.suc(this, "请选择栏目封面");
          return;
      }
      if(TextUtils.isEmpty(columnTitle)){
          ToastUtil.INSTANCE.suc(this, "请填写栏目名称");
          return;
      }
      if(TextUtils.isEmpty(dataTime)){
          ToastUtil.INSTANCE.suc(this, "请填写开厅时间");
          return;
      }
      if(TextUtils.isEmpty(Topic)){
          ToastUtil.INSTANCE.suc(this, "请填写今天话题");
          return;
      }
      if(TextUtils.isEmpty(hostId)){
          ToastUtil.INSTANCE.suc(this, "请填写主播Id");
          return;
      }
      if(TextUtils.isEmpty(Introduction)){
          ToastUtil.INSTANCE.suc(this, "请填写栏目简介");
          return;
      }
      String[] time=dataTime.split("-");
      if(radioStationBean!=null){
          updateColumnInfo(radioStationBean.getId(),radioStationBean.getRoom_id(),mIcon,columnTitle,time[0],time[1],Topic,Introduction,hostId);
      }else{
          addColumnInfo(ChatRoomManager.INSTANCE.getRoomId(),mIcon,columnTitle,time[0],time[1],Topic,Introduction,hostId);
      }


  }
    private void addColumnInfo(String roomid,String icon,String radio_name,String startTime,String endTime,String today_topic,String introduction,String userid ){
        NetService.Companion.getInstance(this).radioRommColumnAdd(roomid,icon,radio_name,startTime,endTime,today_topic,introduction,userid,new Callback<BaseBean>() {
            @Override
            public boolean isAlive() {
                return isLive();
            }

            @Override
            public void onError(@NotNull String msg, @NotNull Throwable throwable, int code) {
                ToastUtil.INSTANCE.suc(ColumnEditActivity.this,msg);
            }

            @Override
            public void onSuccess(int nextPage, BaseBean bean, int code) {
                ToastUtil.INSTANCE.suc(ColumnEditActivity.this,"新增成功");
                finish();
            }
        });
    }
  private void updateColumnInfo(int id,int roomid,String icon,String radio_name,String startTime,String endTime,String today_topic,String introduction,String userid ){
        NetService.Companion.getInstance(this).radioRommColumnEdit(id,roomid,icon,radio_name,startTime,endTime,today_topic,introduction,userid,new Callback<BaseBean>() {
            @Override
            public boolean isAlive() {
                return isLive();
            }

            @Override
            public void onError(@NotNull String msg, @NotNull Throwable throwable, int code) {
                ToastUtil.INSTANCE.suc(ColumnEditActivity.this,msg);
            }

            @Override
            public void onSuccess(int nextPage, BaseBean bean, int code) {
                ToastUtil.INSTANCE.suc(ColumnEditActivity.this,"编辑成功");
                finish();
            }
        });
  }
}
