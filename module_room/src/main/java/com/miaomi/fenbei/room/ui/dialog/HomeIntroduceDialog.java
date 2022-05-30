package com.miaomi.fenbei.room.ui.dialog;

import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.miaomi.fenbei.base.bean.ChatRoomInfo;
import com.miaomi.fenbei.base.bean.FollowResultBean;
import com.miaomi.fenbei.base.bean.MsgType;
import com.miaomi.fenbei.base.bean.RadioStationBean;
import com.miaomi.fenbei.base.bean.RoomGameListBean;
import com.miaomi.fenbei.base.config.BaseConfig;
import com.miaomi.fenbei.base.core.dialog.BaseBottomDialog;

import com.miaomi.fenbei.base.net.Callback;
import com.miaomi.fenbei.base.net.NetService;
import com.miaomi.fenbei.base.util.ImgUtil;
import com.miaomi.fenbei.room.ChatRoomManager;
import com.miaomi.fenbei.room.R;
import com.miaomi.fenbei.room.ui.adapter.ColumnListAdapter;

import java.util.List;


public class HomeIntroduceDialog extends BaseBottomDialog {
    private RecyclerView coulmnRv;
    private TextView titleTv;
    private TextView topicTv;
    private TextView home_idTv;
    private ImageView homeIconIv;
    private ImageView followBtn;
    private ColumnListAdapter adapter;
    private int postion=0;
    @Override
    public int getLayoutRes() {
        return R.layout.room_dialog_home_introduce_list;
    }

    @Override
    public void bindView(View v) {
        coulmnRv = v.findViewById(R.id.rv_coulmn);
        titleTv=v.findViewById(R.id.title_tv);
        homeIconIv=v.findViewById(R.id.home_icon_iv);
        topicTv=v.findViewById(R.id.topic_tv);
        home_idTv=v.findViewById(R.id.home_id_tv);
        followBtn=v.findViewById(R.id.follow_btn);
        adapter=new ColumnListAdapter(getContext());
        coulmnRv.setLayoutManager(new LinearLayoutManager(getContext()));
        coulmnRv.setAdapter(adapter);
        adapter.setOnCheckItemListener(new ColumnListAdapter.CheckItemListener() {
            @Override
            public void itemChecked(RadioStationBean radioStationBean) {
                dismiss();
         HomeIntroduceDetailsDialog homeIntroduceDetailsDialog=new HomeIntroduceDetailsDialog(radioStationBean);
         homeIntroduceDetailsDialog.show(getFragmentManager());
            }
        });
        coulmnRv.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if(postion>2){
                    coulmnRv.smoothScrollToPosition(postion);
                }

            }
        });
        followBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!ChatRoomManager.INSTANCE.isRoomHost()){
                    followChat();
                }

            }
        });
        getData();

    }

    public void getData(){
//
        NetService.Companion.getInstance(getContext()).getChatRoomInfo(ChatRoomManager.INSTANCE.getRoomId(),new Callback<ChatRoomInfo>() {
            @Override
            public void onSuccess(int nextPage, ChatRoomInfo bean, int code) {
//                adapter.setData(bean);
                for(int i=0;i<bean.getRadio_station().size();i++){
                    if(bean.getRadio_station().get(i).getStatus()==1){
                        postion=i;
                    }
                }
                renderView(bean);
                adapter.setData(bean.getRadio_station());

            }

            @Override
            public void onError( String msg,  Throwable throwable, int code) {

            }

            @Override
            public boolean isAlive() {
                return isLive;
            }
        });

    }
    private void followChat() {

        NetService.Companion.getInstance(getContext()).followChat(ChatRoomManager.INSTANCE.getRoomId(),new Callback<FollowResultBean>() {
            @Override
            public void onSuccess(int nextPage, FollowResultBean bean, int code) {
                followBtn.setSelected(bean.getType()==0);
//                ChatRoomManager.INSTANCE.sendMsg(MsgType.ROOM_COLLECT, "", ChatRoomManager.INSTANCE.getRoomId());
            }

            @Override
            public void onError( String msg,  Throwable throwable, int code) {

            }

            @Override
            public boolean isAlive() {
                return isLive;
            }
        });

    }
    //渲染页面
    private void renderView(ChatRoomInfo bean) {
        if (ChatRoomManager.INSTANCE.isRoomHost()) {
            followBtn.setSelected(true);
//            mic_go_btn.visibility = View.GONE
        } else {
            followBtn.setSelected(bean.getHost_info().is_follow()==1);
        }
        titleTv.setText(""+bean.getHost_info().getRadio_station_name());
        topicTv.setText(""+bean.getHost_info().getIntroduction());
        home_idTv.setText("ID:"+ChatRoomManager.INSTANCE.getRoomId());
        ImgUtil.INSTANCE.loadHomeHotImg(getContext(),bean.getHost_info().getIcon(),homeIconIv,6f,-1);
    }

}
