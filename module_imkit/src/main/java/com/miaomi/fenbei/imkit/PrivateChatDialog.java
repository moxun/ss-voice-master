package com.miaomi.fenbei.imkit;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Collections;

import com.miaomi.fenbei.base.bean.EmojiGroupBean;
import com.miaomi.fenbei.base.bean.FollowBean;
import com.miaomi.fenbei.base.bean.IsBlackBean;
import com.miaomi.fenbei.base.bean.event.C2CMsgBean;
import com.miaomi.fenbei.base.bean.event.ConversationBean;
import com.miaomi.fenbei.base.bean.event.RefreshUnReadMsgBean;
import com.miaomi.fenbei.base.core.dialog.BaseBottomDialog;
import com.miaomi.fenbei.base.core.msg.MsgManager;
import com.miaomi.fenbei.base.core.msg.MsgUtils;
import com.miaomi.fenbei.base.core.msg.SendMsgListener;
import com.miaomi.fenbei.base.net.Callback;
import com.miaomi.fenbei.base.net.NetService;
import com.miaomi.fenbei.base.util.ConversationUtils;
import com.miaomi.fenbei.base.util.CopyUtil;
import com.miaomi.fenbei.base.util.DataHelper;
import com.miaomi.fenbei.base.util.DensityUtil;
import com.miaomi.fenbei.base.util.PhotoUtils;
import com.miaomi.fenbei.base.util.ToastUtil;
import com.miaomi.fenbei.base.widget.xrecyclerview.XRecyclerView;
import com.miaomi.fenbei.imkit.listener.OnMsgOprateListener;
import com.miaomi.fenbei.imkit.listener.OnSeletedEmojiListener;
import com.miaomi.fenbei.imkit.ui.C2CEmojiFragment;
import com.miaomi.fenbei.imkit.ui.MsgOpratePopWindow;
import com.miaomi.fenbei.imkit.ui.adapter.PrivateChatAdapter;
import com.miaomi.fenbei.imkit.ui.viewholder.message.MsgBaseHolder;
import com.tencent.imsdk.TIMMessage;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.viewpager.widget.ViewPager;

public class PrivateChatDialog extends BaseBottomDialog implements View.OnClickListener,XRecyclerView.LoadingListener, OnSeletedEmojiListener {

    private final static int LOAD_MSG_NUM = 20;
    private XRecyclerView chatRecyclerview;
    private ImageView sendTv;
    private TextView titleTv;
    private EditText contentEt;
    private List<C2CEmojiFragment> emojiFragments = new ArrayList<>();
    private PrivateChatAdapter chatAdapter;
    private List<C2CMsgBean> msgBeanList = new ArrayList<>();
    private PhotoUtils photoUtils;
    private FrameLayout voiceStatusFl;
    private TextView voiceStatusTv;
    private ViewPager emojiVp;
    private int msgCount = LOAD_MSG_NUM;
    private boolean isBlack;
    //    private LinearLayout textInputLL;
    private LinearLayoutManager linearLayoutManager;
    private String mFilePath;
    private File mImageFile;
    private ImageView careIv;
    private boolean isEmojiVpShow;
    private boolean isnitEmoji;
    private String chatId = "";
    private String fromUserAvter = "";
    private String username = "";


    private ConversationBean conversationBean;



    public PrivateChatDialog(ConversationBean conversationBean) {
        this.conversationBean = conversationBean;
    }



//    PrivateGiftFragment privateGiftFragment;

    //初始化私聊表情
    private void initEmoji(){
        emojiFragments.clear();
        NetService.Companion.getInstance(getContext()).getPersonEmojiList(new Callback<List<EmojiGroupBean>>() {
            @Override
            public void onSuccess(int nextPage,List<EmojiGroupBean> bean, int code) {
                if (isLive){
                    isnitEmoji = true;
                    if (bean.size() > 0){
                        for (int i = 0; i<bean.get(0).getEmoji_item().size();i++){
                            final C2CEmojiFragment c2CEmojiFragment = C2CEmojiFragment.newInstance(bean.get(0).getEmoji_item().get(i));
                            c2CEmojiFragment.setOnSeletedEmoji(PrivateChatDialog.this);
                            emojiFragments.add(c2CEmojiFragment);
                        }
                    }
                    emojiVp.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {
                        @Override
                        public Fragment getItem(int position) {
                            return emojiFragments.get(position);
                        }

                        @Override
                        public int getCount() {
                            return emojiFragments.size();
                        }
                    });
                }
            }

            @Override
            public void onError(@NotNull String msg, @NotNull Throwable throwable, int code) {

            }

            @Override
            public boolean isAlive() {
                return isLive;
            }
        });
    }

    //刷新个人信息
    private void updataUserInfo(){
        NetService.Companion.getInstance(getContext()).getConversationUserInfo(chatId, new Callback<List<ConversationBean>>() {
            @Override
            public void onSuccess(int nextPage, List<ConversationBean> list, int code) {
                if (list.size() > 0){
                    if (list.get(0).getRelation() == 1){
                        careIv.setVisibility(View.GONE);
                    }else{
                        careIv.setVisibility(View.VISIBLE);
                    }
                    titleTv.setText(list.get(0).getNickname());
                    ConversationUtils.updataConversation(list.get(0),list.get(0).getWealth_level().getGrade());
                }
            }

            @Override
            public void onError(@NotNull String msg, @NotNull Throwable throwable, int code) {
            }

            @Override
            public boolean isAlive() {
                return true;
            }
        });
    }


    private void isBlack(String userId){
        NetService.Companion.getInstance(getContext()).isBlack(userId, new Callback<IsBlackBean>() {
            @Override
            public void onSuccess(int nextPage, IsBlackBean bean, int code) {
                if (bean.getBlack() == 1){
                    isBlack = true;
                    ToastUtil.INSTANCE.error(getContext(),"你已被对方拉入黑名单，无法进行私聊。");
                }else{
                    isBlack = false;
                }
            }

            @Override
            public void onError(@NotNull String msg, @NotNull Throwable throwable, int code) {

            }

            @Override
            public boolean isAlive() {
                return true;
            }
        });
    }

    private void initHistoryMessage(){
        msgCount = LOAD_MSG_NUM;
        getHistoryMsgList();
    }

    private void getHistoryMsgList(){
        MsgManager.INSTANCE.getMessageList(chatId, msgCount, new MsgManager.GetLocalMessageCallBack() {
            @Override
            public void onSendSuc(List<C2CMsgBean> list,long unReadCount) {
                chatRecyclerview.refreshComplete();
                if (list.size()>0){
                    Collections.reverse(list);
                    msgBeanList.clear();
                    for (int i = 0;i<list.size();i++){
                        if (DataHelper.INSTANCE.getUID() == Integer.valueOf(list.get(i).getSender())){
                            list.get(i).setFace(DataHelper.INSTANCE.getUserInfo().getFace());
                        }else{
                            list.get(i).setFace(fromUserAvter);
                        }
                    }
                    msgBeanList.addAll(list);
                    chatAdapter.notifyDataSetChanged();
                    if (msgCount == LOAD_MSG_NUM){
                        chatRecyclerview.scrollToPosition(chatAdapter.getItemCount());
                    }
                }
            }

            @Override
            public void onSendFail(int i, String s) {
                chatRecyclerview.refreshComplete();

            }
        });
    }

    private void sendEmojiMsg(final String path){
        if (isBlack){
            ToastUtil.INSTANCE.suc(getContext(),"你已被对方拉入黑名单，无法进行私聊");
            return;
        }
        final C2CMsgBean bean = MsgUtils.buildSendMsg(C2CMsgBean.Emoji,path);
        updateMsg(bean);
        MsgManager.INSTANCE.sendC2CEmoji(chatId, path,
                1, new SendMsgListener() {


                    @Override
                    public void onSendSuc(TIMMessage timMessage) {
                        bean.setTimMessage(timMessage);
                        refreshMsg(bean,C2CMsgBean.SEND_STATUS_SUCCESS);
                    }

                    @Override
                    public void onSendFail(int i, String s) {
                        ToastUtil.INSTANCE.suc(getContext(),s);
                        refreshMsg(bean,C2CMsgBean.SEND_STATUS_FAILE);
                    }
                });
    }

    private void sendTextMsg(final String msg){
        if (TextUtils.isEmpty(msg)){
            ToastUtil.INSTANCE.suc(getContext(),"发送内容不能为空");
            return;
        }
        if (isBlack){
            ToastUtil.INSTANCE.suc(getContext(),"你已被对方拉入黑名单，无法进行私聊");
            return;
        }
        final C2CMsgBean bean = MsgUtils.buildSendMsg(C2CMsgBean.Text,msg);
        updateMsg(bean);
        MsgManager.INSTANCE.sendC2CText(chatId, msg, new SendMsgListener() {

            @Override
            public void onSendSuc(TIMMessage timMessage) {
                bean.setTimMessage(timMessage);
                refreshMsg(bean,C2CMsgBean.SEND_STATUS_SUCCESS);
            }

            @Override
            public void onSendFail(int i, String s) {
                ToastUtil.INSTANCE.suc(getContext(),s);
                refreshMsg(bean,C2CMsgBean.SEND_STATUS_FAILE);
            }
        });
    }


    private void sendImgMsg(final String path){
        if (isBlack){
            ToastUtil.INSTANCE.suc(getContext(),"你已被对方拉入黑名单，无法进行私聊");
            return;
        }
        final C2CMsgBean bean = MsgUtils.buildSendMsg(C2CMsgBean.Image,path);
        bean.setWidth(DensityUtil.INSTANCE.dp2px(getContext(),200));
        bean.setHeight(DensityUtil.INSTANCE.dp2px(getContext(),200));
        updateMsg(bean);
        MsgManager.INSTANCE.sendC2CImage(chatId, path, new MsgManager.ImageMsgCallBack() {
            @Override
            public void onSendSuc(TIMMessage timMessage,String url,int height,int width) {
                bean.setTimMessage(timMessage);
                initHistoryMessage();
            }

            @Override
            public void onSendFail(int i, String s) {
                ToastUtil.INSTANCE.suc(getContext(),s);
                initHistoryMessage();
            }
        });
    }

    @Override
    public void dismiss() {
        super.dismiss();
        EventBus.getDefault().unregister(this);
    }

    private void hideEmoji(){
        isEmojiVpShow = false;
        emojiVp.setVisibility(View.GONE);
    }
    private void showEmoji(){
        isEmojiVpShow = true;
        emojiVp.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        emojiVp.setVisibility(View.GONE);
        if (id == R.id.tv_send){
            sendTextMsg(contentEt.getText().toString());
            contentEt.setText("");
        }
        if (id == R.id.ll_emoji){
            if (!isnitEmoji){
                return;
            }
            DensityUtil.INSTANCE.hideSoftKeyboard(getActivity());
            // TODO: 2019/10/17 表情
            if (isEmojiVpShow){
                hideEmoji();
            }else{
                showEmoji();
            }
        }

        if (id == R.id.iv_care){
            follow();
        }
        if (id == R.id.back_btn){
            dismiss();
        }
        if (id == R.id.ll_picture){
            photoUtils.chooseOriginGallary(getActivity(), new PhotoUtils.OnSelectPhotoListener() {
                @Override
                public void onSelected(String url) {
                    if (!TextUtils.isEmpty(url)){
                        sendImgMsg(url);
                    }
                }
            });
        }
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onNewMsg(C2CMsgBean bean) {
        if (bean.getSender().equals(chatId) || bean.getSender().equals(DataHelper.INSTANCE.getUserInfo().getUser_id())){
            bean.setFace(fromUserAvter);
            updateMsg(bean);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRefreshMsgList(RefreshUnReadMsgBean bean){
        if (bean.getUid().equals(chatId)){
            for (C2CMsgBean item:msgBeanList){
                item.setIsRead(true);
            }
            chatAdapter.notifyDataSetChanged();
        }
    }

    private void refreshMsg(C2CMsgBean bean,int send_status){
        for (C2CMsgBean item:msgBeanList){
            if (item.getTag() == bean.getTag()){
                if (send_status == C2CMsgBean.SEND_STATUS_SUCCESS){
                    item = bean;
                    item.setSend_status(send_status);
                    item.setTag(0);
                    break;
                }else{
                    msgBeanList.remove(item);
                    break;
                }
            }
        }
        chatAdapter.notifyDataSetChanged();
        chatRecyclerview.scrollToPosition(chatAdapter.getItemCount()-1);
    }

    private void updateMsg(C2CMsgBean bean){
        MsgManager.INSTANCE.setReadMessageForItemclik(chatId);
        msgBeanList.add(msgBeanList.size(),bean);
        chatAdapter.notifyDataSetChanged();
        chatRecyclerview.scrollToPosition(chatAdapter.getItemCount()-1);
    }



    @Override
    public void onRefresh() {
        msgCount = msgCount + LOAD_MSG_NUM;
        getHistoryMsgList();
    }

    @Override
    public void onLoadMore() {

    }

    @Override
    public void onSeletedEmoji(String url) {
        sendEmojiMsg(url);
    }

    private void follow() {
        NetService.Companion.getInstance(getContext()).addConcern(DataHelper.INSTANCE.getLoginToken(), chatId, new Callback<FollowBean>() {
            @Override
            public void onSuccess(int nextPage, FollowBean bean, int code) {
                ToastUtil.INSTANCE.suc(getContext(),"关注成功");
                careIv.setVisibility(View.GONE);
                sendTextMsg("我想认识你，趁风不注意~");
            }

            @Override
            public void onError(@NotNull String msg, @NotNull Throwable throwable, int code) {

            }

            @Override
            public boolean isAlive() {
                return true;
            }
        });
    }

    @Override
    public int getLayoutRes() {
        return R.layout.dialog_room_c2c_chat;
    }

    @Override
    public void bindView(View v) {
        EventBus.getDefault().register(this);
        chatId = conversationBean.getUser_id();
        fromUserAvter = conversationBean.getFace();
        username = conversationBean.getNickname();
        photoUtils = PhotoUtils.getInstance();
        v.findViewById(R.id.back_btn).setOnClickListener(this);
        v.findViewById(R.id.ll_picture).setOnClickListener(this);
        v.findViewById(R.id.ll_emoji).setOnClickListener(this);
        chatRecyclerview = v.findViewById(R.id.rv_chat);
        careIv = v.findViewById(R.id.iv_care);
        sendTv = v.findViewById(R.id.tv_send);
        contentEt = v.findViewById(R.id.et_content);
        titleTv = v.findViewById(R.id.main_tv);
//        textInputLL = v.findViewById(R.id.chat_text_input);
        voiceStatusFl = v.findViewById(R.id.fl_voice_status);
        voiceStatusTv = v.findViewById(R.id.tv_voice_status);
        emojiVp = v.findViewById(R.id.vp_emoji);
        careIv.setOnClickListener(this);
        sendTv.setOnClickListener(this);
        titleTv.setText(username);
        chatAdapter = new PrivateChatAdapter(getContext(),msgBeanList);
        chatRecyclerview.setPullRefreshEnabled(true);
        chatRecyclerview.setLoadingMoreEnabled(false);
        chatRecyclerview.setLoadingListener(this);
        linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setStackFromEnd(true);
        chatRecyclerview.setLayoutManager(linearLayoutManager);
        chatRecyclerview.setAdapter(chatAdapter);
        contentEt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    hideEmoji();
                    chatRecyclerview.scrollToPosition(chatAdapter.getItemCount());
                }
            }
        });
        contentEt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideEmoji();
            }
        });
        chatAdapter.setOnItemOprateListener(new MsgBaseHolder.OnItemOprateListener() {
            @Override
            public void onLongClik(View v,final C2CMsgBean bean) {
                boolean canCopy = bean.getMsgType() == C2CMsgBean.Text;
                MsgOpratePopWindow popWindow = new MsgOpratePopWindow(getContext(), canCopy,bean, new OnMsgOprateListener() {
                    @Override
                    public void onCopyClick(C2CMsgBean msg) {
                        CopyUtil.copy(msg.getTxtContent(),getContext());
                        ToastUtil.INSTANCE.suc(getContext(),"已复制");
                    }

                    @Override
                    public void onDeleteMessageClick(C2CMsgBean msg) {
                        if (bean.getTimMessage() == null){
                            return;
                        }
                        if (bean.getTimMessage().remove()){
                            for (int i =0;i<msgBeanList.size();i++){
                                if (msgBeanList.get(i).getUniqueId() == bean.getUniqueId()){
                                    chatAdapter.notifyItemRemoved(i);
                                    msgBeanList.remove(msgBeanList.get(i));
                                    break;
                                }
                            }
                            chatAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onRevokeMessageClick(C2CMsgBean msg) {

                    }
                });
                popWindow.show(v);
            }
        });
        initEmoji();
        updataUserInfo();
        initHistoryMessage();
    }


}
