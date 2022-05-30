package com.miaomi.fenbei.imkit.ui.conversation;

import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.miaomi.fenbei.base.bean.BaseBean;
import com.miaomi.fenbei.base.bean.event.ConversationBean;
import com.miaomi.fenbei.base.core.BaseFragment;
import com.miaomi.fenbei.base.core.dialog.CommonDialog;
import com.miaomi.fenbei.base.core.msg.MsgManager;
import com.miaomi.fenbei.base.net.Callback;
import com.miaomi.fenbei.base.net.Data;
import com.miaomi.fenbei.base.net.NetService;
import com.miaomi.fenbei.base.util.DataHelper;
import com.miaomi.fenbei.base.util.LoadHelper;
import com.miaomi.fenbei.base.util.ToastUtil;
import com.miaomi.fenbei.imkit.PrivateChatDialog;
import com.miaomi.fenbei.imkit.R;
import com.miaomi.fenbei.imkit.listener.OnConversationDeleteListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class RoomConversationFragment extends BaseFragment implements OnConversationDeleteListener {

    private RecyclerView conversationRv;
    private ConversationAdapter conversationAdapter;
    private List<ConversationBean> conversationList = new ArrayList<>();
    private long strangerUnReadNum = 0;
    private boolean isFirstStrangerInfo = true;
    private CommonDialog addBlackDialog;
    private LoadHelper loadHelper;
    private View.OnClickListener onclickListener;


    public void setOnclickListener(View.OnClickListener onclickListener) {
        this.onclickListener = onclickListener;
    }

    public static RoomConversationFragment newInstance() {
        return new RoomConversationFragment();
    }






    @Override
    public int getLayoutId() {
        return R.layout.user_fragment_conversation_room;
    }

    @Override
    public void initView(@NotNull View view) {
        EventBus.getDefault().register(this);
        ConversationBean systemConversationBean = new ConversationBean();
        systemConversationBean.setUser_id(MsgManager.INSTANCE.getSystemUid());
        conversationList.add(0,systemConversationBean);
        conversationAdapter = new ConversationAdapter(conversationList, getMContext());
        conversationAdapter.setOnDeleteListener(this);
        conversationRv = view.findViewById(R.id.conversation_rv);
        conversationRv.setLayoutManager(new LinearLayoutManager(getActivity()));
        conversationRv.setAdapter(conversationAdapter);
        loadHelper = new LoadHelper();
        loadHelper.registerLoad(conversationRv);
//        loadHelper.setEmptyCallback(R.drawable.empty_img_message,"没有任何消息～");
        loadHelper.bindView(Data.CODE_SUC);
//        timeTv = view.findViewById(R.id.tv_time);
//        contentTv = view.findViewById(R.id.tv_content);
//        unreadTv = view.findViewById(R.id.tv_unread);
//
//        contentStrangerTv = view.findViewById(R.id.tv_content_stranger);
//        unreadStrangerTv = view.findViewById(R.id.tv_unread_stranger);
//        timeStrangerTv = view.findViewById(R.id.tv_time_stranger);
//        systemRl = view.findViewById(R.id.rl_system);
//        strangerRl = view.findViewById(R.id.rl_stranger);
//
//        strangerRl.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                StrangerConversationActivity.start(getContext());
//            }
//        });
//
//        systemRl.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                MsgManager.INSTANCE.setReadMessageForItemclik(MsgManager.INSTANCE.getSystemUid());
//                ARouter.getInstance().build("/app/systemmessage")
//                        .navigation();
//            }
//        });

//        conversationAdapter.notifyDataSetChanged();
    }




    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRefresh(List<ConversationBean> list) {
        if (list.size() > 0){
            loadHelper.bindView(Data.CODE_SUC);
            ConversationBean strangerConversationBean = new ConversationBean();
            strangerConversationBean.setUser_id("-1");
            ConversationBean systemConversationBean = new ConversationBean();
            systemConversationBean.setUser_id(MsgManager.INSTANCE.getSystemUid());
            conversationList.clear();
            strangerUnReadNum = 0;
            isFirstStrangerInfo = true;
            for (ConversationBean bean:list){
                if (bean.getUser_id().equals(MsgManager.INSTANCE.getSystemUid())){
                    systemConversationBean = bean;
                }else if(bean.getRelation() == 1){
                    conversationList.add(bean);
                }else{
                    if(isFirstStrangerInfo){
                        isFirstStrangerInfo = false;
                        strangerConversationBean.setTime(bean.getTime());
                        strangerConversationBean.setMsgType(bean.getMsgType());
                        strangerConversationBean.setContent(bean.getContent());
                    }
                    strangerUnReadNum = strangerUnReadNum + bean.getUnReadNum();
                }
            }
            conversationList.add(0,systemConversationBean);
            strangerConversationBean.setUnReadNum(strangerUnReadNum);
            conversationList.add(1,strangerConversationBean);
            conversationAdapter.notifyDataSetChanged();
        }
//        else{
//            loadHelper.setEmptyCallback(R.drawable.empty_img_message,"没有任何消息～");
//        }
    }



    @Override
    public void onResume() {
        super.onResume();
    }



    @Override
    public void onStart() {
        super.onStart();
        MsgManager.INSTANCE.getConversationList();
    }

    @Override
    public void onSuc() {
        MsgManager.INSTANCE.getConversationList();
    }

    @Override
    public void onFaile() {
        ToastUtil.INSTANCE.suc(getActivity(), "删除失败");
    }


    @Override
    public void onDeleteFd(final String uid) {
        addBlackDialog = new CommonDialog(getActivity())
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

                        NetService.Companion.getInstance(getMContext()).addBlack(uid, "", new Callback<BaseBean>() {
                            @Override
                            public boolean isAlive() {
                                return isLive();
                            }

                            @Override
                            public void onError(@NotNull String msg, @NotNull Throwable throwable, int code) {
                                ToastUtil.INSTANCE.error(getMContext(), msg);
                            }

                            @Override
                            public void onSuccess(int nextPage, BaseBean bean, int code) {
                                ToastUtil.INSTANCE.suc(getMContext(), "拉黑成功");

                            }
                        });

                        addBlackDialog.dismiss();
                    }
                });
        addBlackDialog.show();
    }

    @Override
    public void onClik(ConversationBean conversationBean) {
//        PrivateChatDialog dialog = new PrivateChatDialog(conversationBean);
//        dialog.show(getFragmentManager());
//        if (onclickListener !=null){
//            onclickListener.onClick(null);
//        }
//        if (Integer.valueOf(conversationBean.getUser_id()) > 7000000){
//            ARouter.getInstance().build("/imkit/privatechat")
//                    .withString("CHAT_ID", conversationBean.getUser_id())
//                    .withString("FROM_USER_NICKNAME", conversationBean.getNickname())
//                    .withString("FROM_USER_AVTER", conversationBean.getFace())
//                    .navigation();
//        }

        ARouter.getInstance().build("/imkit/privatechat")
                .withString("CHAT_ID", conversationBean.getUser_id())
                .withString("FROM_USER_NICKNAME", conversationBean.getNickname())
                .withString("FROM_USER_AVTER", conversationBean.getFace())
                .navigation();

    }

//    public void setOnNewMsgListener(OnNewMsgListener onNewMsgListener) {
//        this.onNewMsgListener = onNewMsgListener;
//    }

}