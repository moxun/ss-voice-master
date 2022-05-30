package com.miaomi.fenbei.imkit.ui.conversation;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

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
import com.miaomi.fenbei.imkit.listener.OnConversationDeleteListener;
import com.miaomi.fenbei.imkit.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ConversationFragment extends BaseFragment implements OnConversationDeleteListener {

    private RecyclerView conversationRv;
    private ConversationAdapter conversationAdapter;
    private List<ConversationBean> conversationList = new ArrayList<>();
//    private long strangerUnReadNum = 0;
//    private boolean isFirstStrangerInfo = true;
    private CommonDialog addBlackDialog;
    private LoadHelper loadHelper;
    private TextView strangerUnReadNumTv;
    public static ConversationFragment newInstance() {
        return new ConversationFragment();
    }





    @Override
    public int getLayoutId() {
        return R.layout.user_fragment_conversation;
    }

    @Override
    public void initView(@NotNull View view) {
        EventBus.getDefault().register(this);
//        ConversationBean systemConversationBean = new ConversationBean();
//        systemConversationBean.setUser_id(MsgManager.INSTANCE.getSystemUid());
//        conversationList.add(0,systemConversationBean);
        conversationAdapter = new ConversationAdapter(conversationList, getMContext());
        conversationAdapter.setOnDeleteListener(this);
        strangerUnReadNumTv = view.findViewById(R.id.tv_unread_assess);
        conversationRv = view.findViewById(R.id.conversation_rv);
        conversationRv.setLayoutManager(new LinearLayoutManager(getActivity()));
        conversationRv.setAdapter(conversationAdapter);
        loadHelper = new LoadHelper();
        loadHelper.registerLoad(conversationRv);
        loadHelper.bindView(Data.CODE_SUC);
        view.findViewById(R.id.ll_new_system).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MsgManager.INSTANCE.setReadMessageForItemclik(MsgManager.INSTANCE.getSystemUid());
                ARouter.getInstance().build("/app/systemmessage")
                        .navigation();
            }
        });
        view.findViewById(R.id.ll_new_assess).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MsgManager.INSTANCE.setReadMessageForItemclik(MsgManager.INSTANCE.getSystemUid());
                ARouter.getInstance().build("/app/friend")
                        .withInt("friendtype",2)
                        .navigation();
            }
        });
        view.findViewById(R.id.ll_new_follow).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                FriendActivity.start(getContext(), false);
                ARouter.getInstance().build("/app/friend")
                        .withInt("friendtype",0)
                        .navigation();
            }
        });
        view.findViewById(R.id.ll_new_fans).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build("/app/friend")
                        .withInt("friendtype",1)
                        .navigation();
            }
        });
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
//            ConversationBean strangerConversationBean = new ConversationBean();
//            strangerConversationBean.setUser_id("-1");
            ConversationBean systemConversationBean = new ConversationBean();
            systemConversationBean.setUser_id(MsgManager.INSTANCE.getSystemUid());
            conversationList.clear();
//            strangerUnReadNum = 0;
//            isFirstStrangerInfo = true;

//            for (ConversationBean bean:list){
//                if (!bean.getUser_id().equals(MsgManager.INSTANCE.getSystemUid())){
//                    conversationList.add(bean);
//                }
//            }
            for (ConversationBean bean:list){
                if (bean.getUser_id().equals(MsgManager.INSTANCE.getSystemUid())){
                    systemConversationBean = bean;
                }else{
                    conversationList.add(bean);
                }
            }
            if (systemConversationBean.getUnReadNum() >= 1){
                strangerUnReadNumTv.setVisibility(View.VISIBLE);
                strangerUnReadNumTv.setText(String.valueOf(systemConversationBean.getUnReadNum()));
            }else{
                strangerUnReadNumTv.setVisibility(View.GONE);
            }
//            conversationList.add(0,systemConversationBean);
//            strangerConversationBean.setUnReadNum(strangerUnReadNum);
//            conversationList.add(1,strangerConversationBean);
            conversationAdapter.notifyDataSetChanged();
        }
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
        ARouter.getInstance().build("/imkit/privatechat")
                .withString("CHAT_ID", conversationBean.getUser_id())
                .withString("FROM_USER_NICKNAME", conversationBean.getNickname())
                .withString("FROM_USER_AVTER", conversationBean.getFace())
                .navigation();
    }


    }