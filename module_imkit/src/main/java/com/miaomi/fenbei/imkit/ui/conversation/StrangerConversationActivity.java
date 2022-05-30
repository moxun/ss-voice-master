package com.miaomi.fenbei.imkit.ui.conversation;

import android.content.Context;
import android.content.Intent;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.miaomi.fenbei.base.bean.BaseBean;
import com.miaomi.fenbei.base.bean.event.ConversationBean;
import com.miaomi.fenbei.base.core.BaseActivity;
import com.miaomi.fenbei.base.core.dialog.CommonDialog;
import com.miaomi.fenbei.base.core.msg.MsgManager;
import com.miaomi.fenbei.base.net.Callback;
import com.miaomi.fenbei.base.net.Data;
import com.miaomi.fenbei.base.util.DataHelper;
import com.miaomi.fenbei.base.util.LoadHelper;
import com.miaomi.fenbei.base.net.NetService;
import com.miaomi.fenbei.base.util.ToastUtil;
import com.miaomi.fenbei.imkit.listener.OnConversationDeleteListener;
import com.miaomi.fenbei.imkit.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class StrangerConversationActivity extends BaseActivity implements OnConversationDeleteListener {

    private RecyclerView conversationRv;
    private ConversationAdapter conversationAdapter;
    private List<ConversationBean> conversationList = new ArrayList<>();
    LoadHelper loadHelper;

    public static void start(Context context){
        Intent intent = new Intent(context,StrangerConversationActivity.class);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.user_activity_conversation_stranger;
    }

    @Override
    public void initView() {
        setBaseStatusBar(false,false);
        EventBus.getDefault().register(this);
        conversationAdapter = new ConversationAdapter(conversationList, this);
        conversationAdapter.setOnDeleteListener(this);
        conversationRv = findViewById(R.id.conversation_rv);
        conversationRv.setLayoutManager(new LinearLayoutManager(this));
        conversationRv.setAdapter(conversationAdapter);
        loadHelper = new LoadHelper();
        loadHelper.registerLoad(conversationRv);
        loadHelper.bindView(Data.CODE_SUC);
        MsgManager.INSTANCE.getConversationList();
    }



//    private void refreshData(){
//        users.clear();
//        for (ConversationBean bean : MsgManager.INSTANCE.getAllCoversation()) {
//            users.add(bean.getUser_id().substring(bean.getUser_id().indexOf("_") + 1));
//        }
//        getData(users,true);
////        if (users.size() > 0) {
////            getData(users.toString(),true);
////        } else {
//////            conversationSwl.setRefreshing(false);
////            loadHelper.setEmptyCallback(R.drawable.user_empty_fans, "空空如也");
////            conversationList.clear();
////            conversationAdapter.notifyDataSetChanged();
////        }
//    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRefresh(List<ConversationBean> list) {
        conversationList.clear();
        for (ConversationBean bean:list){
            if(!bean.getUser_id().equals(MsgManager.INSTANCE.getSystemUid())&&bean.getRelation() == 0) {
                conversationList.add(bean);
            }
        }
        if (conversationList.size() == 0){
            loadHelper.setEmptyCallback(R.drawable.common_empty_bg,"没有任何消息～");
        }else{
            loadHelper.bindView(Data.CODE_SUC);
        }

        conversationAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();
        MsgManager.INSTANCE.getConversationList();
    }

    @Override
    public void onSuc() {
        MsgManager.INSTANCE.getConversationList();
    }

    @Override
    public void onFaile() {
        ToastUtil.INSTANCE.suc(this, "删除失败");
    }

    CommonDialog addBlackDialog;

    @Override
    public void onDeleteFd(final String uid) {
        addBlackDialog = new CommonDialog(this)
                .setContent("拉黑后，你将不再收到对方信息，同时对方无法加入你的房间哦")
                .setTitle("友情提示")
                .setLeftBt("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        addBlackDialog.dismiss();
                    }
                })
                .setRightBt("确定", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        NetService.Companion.getInstance(StrangerConversationActivity.this).addBlack(uid, "", new Callback<BaseBean>() {
                            @Override
                            public boolean isAlive() {
                                return isLive();
                            }

                            @Override
                            public void onError(@NotNull String msg, @NotNull Throwable throwable, int code) {
                                ToastUtil.INSTANCE.error(StrangerConversationActivity.this, msg);
                            }

                            @Override
                            public void onSuccess(int nextPage, BaseBean bean, int code) {
                                ToastUtil.INSTANCE.suc(StrangerConversationActivity.this, "拉黑成功");

                            }
                        });

                        addBlackDialog.dismiss();
                    }
                });
        addBlackDialog.show();
    }

    @Override
    public void onClik(ConversationBean conversationBean) {

        ARouter.getInstance().build("/imkit/privatechat")
                .withString("CHAT_ID", conversationBean.getUser_id())
                .withString("FROM_USER_NICKNAME", conversationBean.getNickname())
                .withString("FROM_USER_AVTER", conversationBean.getFace())
                .navigation();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


}
