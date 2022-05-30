package com.miaomi.fenbei.voice.ui;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.miaomi.fenbei.base.bean.BaseBean;
import com.miaomi.fenbei.base.bean.ChatRoomInfo;
import com.miaomi.fenbei.base.bean.RadioStationBean;
import com.miaomi.fenbei.base.bean.RoomGameListBean;
import com.miaomi.fenbei.base.core.BaseActivity;
import com.miaomi.fenbei.base.core.dialog.CommonDialog;
import com.miaomi.fenbei.base.net.Callback;
import com.miaomi.fenbei.base.net.NetService;


import com.miaomi.fenbei.base.util.ToastUtil;
import com.miaomi.fenbei.room.ChatRoomManager;

import com.miaomi.fenbei.voice.R;
import com.miaomi.fenbei.voice.ui.mine.editinfo.EditUserInfoActivity;


public class ColumnListActivity extends BaseActivity {


    public final static String TAG_TYPE = "TAG_TYPE";

    private RecyclerView coulmnRv;
    private TextView rightTv;
    private ColumnListEditAdapter adapter;

    public static void start(Context context,int type){
        Intent intent = new Intent(context, ColumnListActivity.class);
        intent.putExtra(TAG_TYPE,type);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_column_list;
    }

    @Override
    public void initView() {
        setBaseStatusBar(false,false);
        coulmnRv = findViewById(com.miaomi.fenbei.room.R.id.rv_coulmn);
        rightTv=findViewById(R.id.right_tv);
        adapter=new ColumnListEditAdapter(getApplicationContext());
        coulmnRv.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        coulmnRv.setAdapter(adapter);
        rightTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ColumnEditActivity.start(getBaseContext(),null);
            }
        });
        adapter.setOnCheckItemListener(new ColumnListEditAdapter.CheckItemListener() {
            @Override
            public void itemChecked(RadioStationBean radioStationBean) {
                ColumnEditActivity.start(getBaseContext(),radioStationBean);
            }

            @Override
            public void itemDel(int id) {
                CommonDialog mCommonDialog = new CommonDialog(ColumnListActivity.this);
                mCommonDialog.setTitle("友情提示");
                mCommonDialog.setContent("确定删除节目单?");
                mCommonDialog.setLeftBt("取消", v1 -> mCommonDialog.dismiss());
                mCommonDialog.setRightBt("确定", v12 -> {
                    deleteColumn(id);
                    mCommonDialog.dismiss();
                });
                mCommonDialog.show();
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        getData();
    }

    public void getData(){
        NetService.Companion.getInstance(getBaseContext()).getChatRoomInfo(ChatRoomManager.INSTANCE.getRoomId(),new Callback<ChatRoomInfo>() {
            @Override
            public void onSuccess(int nextPage, ChatRoomInfo bean, int code) {
                adapter.setData(bean.getRadio_station());
            }

            @Override
            public void onError( String msg,  Throwable throwable, int code) {

            }

            @Override
            public boolean isAlive() {
                return true;
            }
        });

    }
    public void deleteColumn(int id){
        NetService.Companion.getInstance(getBaseContext()).deleteColumn(id,new Callback<BaseBean>() {
            @Override
            public void onSuccess(int nextPage, BaseBean bean, int code) {
                ToastUtil.INSTANCE.suc(getApplicationContext(), "删除成功");
                getData();
            }

            @Override
            public void onError( String msg,  Throwable throwable, int code) {
                ToastUtil.INSTANCE.error(getApplicationContext(), msg);
            }

            @Override
            public boolean isAlive() {
                return true;
            }
        });

    }
}
