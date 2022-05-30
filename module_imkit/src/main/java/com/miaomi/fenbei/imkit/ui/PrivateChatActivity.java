package com.miaomi.fenbei.imkit.ui;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.AnimationDrawable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.google.gson.Gson;
import com.miaomi.fenbei.base.AudioPlayer;
import com.miaomi.fenbei.base.bean.BaseBean;
import com.miaomi.fenbei.base.bean.EmojiGroupBean;
import com.miaomi.fenbei.base.bean.FollowBean;
import com.miaomi.fenbei.base.bean.IsBlackBean;
import com.miaomi.fenbei.base.bean.event.C2CMsgBean;
import com.miaomi.fenbei.base.bean.event.ConversationBean;
import com.miaomi.fenbei.base.bean.event.RefreshUnReadMsgBean;
import com.miaomi.fenbei.base.core.BaseActivity;
import com.miaomi.fenbei.base.core.CommonLib;
import com.miaomi.fenbei.base.core.dialog.CommonDialog;
import com.miaomi.fenbei.base.core.msg.MsgManager;
import com.miaomi.fenbei.base.core.msg.MsgUtils;
import com.miaomi.fenbei.base.core.msg.SendMsgListener;
import com.miaomi.fenbei.base.net.Callback;
import com.miaomi.fenbei.base.net.NetService;
import com.miaomi.fenbei.base.util.ConversationUtils;
import com.miaomi.fenbei.base.util.CopyUtil;
import com.miaomi.fenbei.base.util.DataHelper;
import com.miaomi.fenbei.base.util.DensityUtil;
import com.miaomi.fenbei.base.util.StatusBarUtil;
import com.miaomi.fenbei.base.util.ToastUtil;
import com.miaomi.fenbei.base.util.PhotoUtils;
import com.miaomi.fenbei.base.widget.WaveView;
import com.miaomi.fenbei.base.widget.xrecyclerview.XRecyclerView;
import com.miaomi.fenbei.base.bean.GiftBean;
import com.miaomi.fenbei.gift.fragment.PrivateGiftFragment;
import com.miaomi.fenbei.imkit.R;
import com.miaomi.fenbei.base.bean.C2CCustomBean;
import com.miaomi.fenbei.imkit.listener.OnMsgOprateListener;
import com.miaomi.fenbei.imkit.listener.OnSeletedEmojiListener;
import com.miaomi.fenbei.imkit.ui.adapter.PrivateChatAdapter;
import com.miaomi.fenbei.imkit.ui.viewholder.message.MsgBaseHolder;
import com.tencent.imsdk.TIMManager;
import com.tencent.imsdk.TIMMessage;
import com.tencent.imsdk.TIMMessageListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Route(path = "/imkit/privatechat")
public class PrivateChatActivity extends BaseActivity implements View.OnClickListener,XRecyclerView.LoadingListener,OnSeletedEmojiListener {

    public final static String CHAT_ID = "CHAT_ID";
    public final static String FROM_USER_AVTER = "FROM_USER_AVTER";
    public final static String FROM_USER_NICKNAME = "FROM_USER_NICKNAME";
    private static final int REQUEST_CAMERA = 0x500;
    private final static int LOAD_MSG_NUM = 20;
    private String chatId = "";
    private String username = "";
    private String fromUserAvter = "";
    private RecyclerView chatRecyclerview;
    private TextView sendTv;
    private TextView titleTv;
    private EditText contentEt;
    private List<C2CEmojiFragment> emojiFragments = new ArrayList<>();
    private PrivateChatAdapter chatAdapter;
    private List<C2CMsgBean> msgBeanList = new ArrayList<>();
    private PhotoUtils photoUtils;
    private LinearLayout moreLL;
    private ImageView pictureLL;
    private ImageView emojiLL;
    private ImageView takePhotoLL;
    private ImageView statusVoiceIv;
    private Button recordIv;
    private FrameLayout voiceStatusFl;
    private TextView voiceStatusTv;
    private FrameLayout recordFl;
    private ViewPager emojiVp;
    private int msgCount = LOAD_MSG_NUM;
    private boolean isBlack;
    private float mStartRecordY;
    private boolean mAudioCancel;
//    private LinearLayout textInputLL;
    private ImageView voiceLL;
    private LinearLayoutManager linearLayoutManager;
    private String mFilePath;
    private File mImageFile;
    private AnimationDrawable mVolumeAnim;
    private ImageView userInfoIv;
    private boolean isEmojiVpShow;
    private boolean isRecordShow;
    private boolean isnitEmoji;
    private FrameLayout giftFl;
    private ImageView giftIv;
    private TextView blackTv;
    private WaveView recordWv;
   private Dialog moreDialog;
    private CommonDialog addBlackDialog;
    public static void startActivity(Context context,String chatId,String fromUserAvter){
        Intent intent = new Intent(context,PrivateChatActivity.class);
        intent.putExtra(CHAT_ID,chatId);
        intent.putExtra(FROM_USER_AVTER,fromUserAvter);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.chatting_activity_c2c_chat;
    }

    @Override
    protected void onResume() {
        super.onResume();
        isBlack(chatId);
    }


    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void initView() {
        EventBus.getDefault().register(this);
        StatusBarUtil.setColorNoTranslucent(this,Color.parseColor("#642192"));
//        setBaseStatusBar(false, false);
//        StatusBarUtil.setStatusBarTextColor(this,true);
        photoUtils = PhotoUtils.getInstance();
        chatId = getIntent().getStringExtra(CHAT_ID);
        fromUserAvter = getIntent().getStringExtra(FROM_USER_AVTER);
        username = getIntent().getStringExtra(FROM_USER_NICKNAME);
        chatRecyclerview = findViewById(R.id.rv_chat);
        userInfoIv = findViewById(R.id.iv_user_info);
        sendTv = findViewById(R.id.tv_send);
        contentEt = findViewById(R.id.et_content);
        blackTv = findViewById(R.id.tv_black);
        titleTv = findViewById(R.id.main_tv);
        statusVoiceIv = findViewById(R.id.iv_voice_status);
        moreLL = findViewById(R.id.ll_more);
        pictureLL = findViewById(R.id.ll_picture);
        emojiLL = findViewById(R.id.ll_emoji);
        takePhotoLL = findViewById(R.id.ll_take_photo);
        recordIv = findViewById(R.id.chat_voice_input);
        recordFl = findViewById(R.id.fl_record);
//        textInputLL = findViewById(R.id.chat_text_input);
        voiceStatusFl = findViewById(R.id.fl_voice_status);
        voiceStatusTv = findViewById(R.id.tv_voice_status);
        emojiVp = findViewById(R.id.vp_emoji);
        voiceLL = findViewById(R.id.ll_voice);
        giftFl = findViewById(R.id.fl_gift);
        giftIv = findViewById(R.id.iv_gift);
        recordWv = findViewById(R.id.wv_record);
        recordWv.setDuration(1500);
        recordWv.setSpeed(1000);
        recordWv.setColor(Color.parseColor("#19ED52F9"));
//        recordWv.setColor(Color.rgb(253, 127, 143));
        recordWv.setStyle(Paint.Style.FILL);
        recordWv.setInterpolator(new LinearOutSlowInInterpolator());
        recordWv.setInitialRadius(5f);

        giftIv.setOnClickListener(this);
        voiceLL.setOnClickListener(this);
        userInfoIv.setOnClickListener(this);
        voiceLL.setSelected(true);
        recordIv.setOnTouchListener(new RecordOnTouchListener());
        takePhotoLL.setOnClickListener(this);
        emojiLL.setOnClickListener(this);
        pictureLL.setOnClickListener(this);
        sendTv.setOnClickListener(this);
        titleTv.setText(username);
        chatAdapter = new PrivateChatAdapter(this,msgBeanList);
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        chatRecyclerview.setLayoutManager(linearLayoutManager);
        chatRecyclerview.setAdapter(chatAdapter);
        contentEt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    hideEmoji();
                    hideRecordFl();
                    hideGiftTab();
                    chatRecyclerview.scrollToPosition(chatAdapter.getItemCount());
                }
            }
        });
        contentEt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideEmoji();
                hideRecordFl();
            }
        });
        chatAdapter.setOnItemOprateListener(new MsgBaseHolder.OnItemOprateListener() {
            @Override
            public void onLongClik(View v,final C2CMsgBean bean) {
                boolean canCopy = bean.getMsgType() == C2CMsgBean.Text;
                MsgOpratePopWindow popWindow = new MsgOpratePopWindow(PrivateChatActivity.this, canCopy,bean, new OnMsgOprateListener() {
                    @Override
                    public void onCopyClick(C2CMsgBean msg) {
                        CopyUtil.copy(msg.getTxtContent(),PrivateChatActivity.this);
                        ToastUtil.INSTANCE.suc(PrivateChatActivity.this,"已复制");
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
        if (privateGiftFragment == null){
            privateGiftFragment = PrivateGiftFragment.newInstance(chatId);
        }
        privateGiftFragment.setOnSendSuccess(new PrivateGiftFragment.OnSendSuccess() {
            @Override
            public void onSuc(GiftBean.DataBean gift) {
                int allPrice = gift.getNumber() * gift.getPrice();
                sendGiftMsg("送你"+gift.getNumber()+"个【"+gift.getName()+"】",allPrice+"钻石",gift.getIcon());
            }

            @Override
            public void onFail(String msg) {
                ToastUtil.INSTANCE.suc(PrivateChatActivity.this,msg);
            }
        });
        getSupportFragmentManager().beginTransaction().add(R.id.fl_gift,privateGiftFragment).commit();
    }
    private boolean isShowGift = false;
    private void changeGiftTabStatus(){
        if (isShowGift){
            hideGiftTab();
        }else {
            showGiftTab();
        }
    }

    private void hideGiftTab(){
        isShowGift = false;
        giftFl.setVisibility(View.GONE);
    }
    private void showGiftTab(){
        isShowGift = true;
        if (privateGiftFragment != null){
            privateGiftFragment.refresh();
        }
        giftFl.setVisibility(View.VISIBLE);
    }

    PrivateGiftFragment privateGiftFragment;

    //初始化私聊表情
    private void initEmoji(){
        emojiFragments.clear();
        NetService.Companion.getInstance(PrivateChatActivity.this).getPersonEmojiList(new Callback<List<EmojiGroupBean>>() {
            @Override
            public void onSuccess(int nextPage, List<EmojiGroupBean> bean, int code) {
                isnitEmoji = true;

                if (bean.size() > 0){
                    for (int i = 0; i<bean.get(0).getEmoji_item().size();i++){
                        final C2CEmojiFragment c2CEmojiFragment = C2CEmojiFragment.newInstance(bean.get(0).getEmoji_item().get(i));
                        c2CEmojiFragment.setOnSeletedEmoji(PrivateChatActivity.this);
                        emojiFragments.add(c2CEmojiFragment);
                    }
                }
                emojiVp.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
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

            @Override
            public void onError(@NotNull String msg, @NotNull Throwable throwable, int code) {

            }

            @Override
            public boolean isAlive() {
                return isLive();
            }
        });
    }

    //刷新个人信息
    private void updataUserInfo(){
        NetService.Companion.getInstance(PrivateChatActivity.this).getConversationUserInfo(chatId, new Callback<List<ConversationBean>>() {
            @Override
            public void onSuccess(int nextPage, List<ConversationBean> list, int code) {
                if (list.size() > 0){
//                    if (list.get(0).getRelation() == 1){
//                        careIv.setVisibility(View.GONE);
//                    }else{
//                        careIv.setVisibility(View.VISIBLE);
//                    }
                    titleTv.setText(list.get(0).getNickname());
                    ConversationUtils.updataConversation(list.get(0),list.get(0).getWealth_level().getGrade());
                }
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

    private void startRecording() {
        AudioPlayer.getInstance().stopPlay();
        voiceStatusFl.setVisibility(View.VISIBLE);
        statusVoiceIv.setImageResource(R.drawable.record_anim);
        mVolumeAnim = (AnimationDrawable) statusVoiceIv.getDrawable();
        mVolumeAnim.start();
        voiceStatusTv.setText("手指上滑，取消发送");
        recordWv.start();
    }

    private void stopRecording() {
        mVolumeAnim.stop();
        voiceStatusFl.setVisibility(View.GONE);
        recordWv.stop();
    }
    private void cancelRecording() {
        statusVoiceIv.setImageResource(R.drawable.chat_icon_recording_cancel);
        voiceStatusTv.setText("松开手指，取消发送");
        recordWv.stop();
    }

    public void recordFailed(){
        voiceStatusTv.setText("录音失败");
        recordWv.stop();
    }

    public void recordTooShort(){
        voiceStatusTv.setText("说话时间太短");
        recordWv.stop();
    }

    private void recordComplete(boolean success) {
        int duration = AudioPlayer.getInstance().getDuration();
        if (!success || duration == 0) {
            recordFailed();
            return;
        }
        if (mAudioCancel) {
            cancelRecording();
            return;
        }
        if (duration < 1000) {
            recordTooShort();
            return;
        }
        stopRecording();
        sendSoundMsg(duration/1000);
    }

    private void sendSoundMsg(final int duration){
        if (isBlack){
            ToastUtil.INSTANCE.suc(this,"你已被对方拉入黑名单，无法进行私聊");
            return;
        }
        final C2CMsgBean bean = MsgUtils.buildSendMsg(C2CMsgBean.Sound,AudioPlayer.getInstance().getPath());
        bean.setDuration(duration);
        bean.setSend_status(C2CMsgBean.SEND_STATUS_ING);
        bean.setTag(System.currentTimeMillis());
        updateMsg(bean);
        MsgManager.INSTANCE.sendC2CSound(chatId, AudioPlayer.getInstance().getPath(), duration, new SendMsgListener() {

            @Override
            public void onSendSuc(TIMMessage timMessage) {
                bean.setTimMessage(timMessage);
                refreshMsg(bean,C2CMsgBean.SEND_STATUS_SUCCESS);
                updataSendMsgInfo();
            }

            @Override
            public void onSendFail(int i, String s) {
                refreshMsg(bean,C2CMsgBean.SEND_STATUS_FAILE);
            }
        });
    }

    private void isBlack(String userId){
        NetService.Companion.getInstance(this).isBlack(userId, new Callback<IsBlackBean>() {
            @Override
            public void onSuccess(int nextPage, IsBlackBean bean, int code) {
                if (bean.getBlack() == 1){
                    isBlack = true;
                    blackTv.setVisibility(View.VISIBLE);
                }else{
                    blackTv.setVisibility(View.GONE);
                    isBlack = false;
                }
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

    private void initHistoryMessage(){
        msgCount = LOAD_MSG_NUM;
        getHistoryMsgList();
    }

    private void getHistoryMsgList(){
        MsgManager.INSTANCE.getMessageList(chatId, msgCount, new MsgManager.GetLocalMessageCallBack() {
            @Override
            public void onSendSuc(List<C2CMsgBean> list,long unReadCount) {
//                chatRecyclerview.refreshComplete();
                if (list.size()>0){
                    Collections.reverse(list);
                    msgBeanList.clear();
                    for (int i = 0;i<list.size();i++){
                        if (DataHelper.INSTANCE.getUID() == Integer.parseInt(list.get(i).getSender())){
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
//                chatRecyclerview.refreshComplete();

            }
        });
    }

    private void sendEmojiMsg(final String path){
        if (isBlack){
            ToastUtil.INSTANCE.suc(this,"你已被对方拉入黑名单，无法进行私聊");
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
                        updataSendMsgInfo();
                    }

                    @Override
                    public void onSendFail(int i, String s) {
                        ToastUtil.INSTANCE.suc(PrivateChatActivity.this,s);
                        refreshMsg(bean,C2CMsgBean.SEND_STATUS_FAILE);
                    }
                });
    }

    private void sendTextMsg(final String msg){
        if (TextUtils.isEmpty(msg)){
            ToastUtil.INSTANCE.suc(this,"发送内容不能为空");
            return;
        }
        if (isBlack){
            ToastUtil.INSTANCE.suc(this,"你已被对方拉入黑名单，无法进行私聊");
            return;
        }
        final C2CMsgBean bean = MsgUtils.buildSendMsg(C2CMsgBean.Text,msg);
        updateMsg(bean);
        MsgManager.INSTANCE.sendC2CText(chatId, msg, new SendMsgListener() {

            @Override
            public void onSendSuc(TIMMessage timMessage) {
                bean.setTimMessage(timMessage);
                refreshMsg(bean,C2CMsgBean.SEND_STATUS_SUCCESS);
                updataSendMsgInfo();
            }

            @Override
            public void onSendFail(int i, String s) {
                ToastUtil.INSTANCE.suc(PrivateChatActivity.this,s);
                refreshMsg(bean,C2CMsgBean.SEND_STATUS_FAILE);
            }
        });
    }

    private void sendGiftMsg(String content,String price,String giftUrl){
        if (isBlack){
            ToastUtil.INSTANCE.suc(this,"你已被对方拉入黑名单，无法进行私聊");
            return;
        }
        C2CCustomBean c2CGiftBean = new C2CCustomBean();
        c2CGiftBean.setMsgType(C2CMsgBean.CUSTOM_CHAT_GIFT);
        c2CGiftBean.setContent(content);
        c2CGiftBean.setPrice(price);
        c2CGiftBean.setGiftUrl(giftUrl);
        String sendContent = new Gson().toJson(c2CGiftBean);
        final C2CMsgBean bean = MsgUtils.buildSendMsg(C2CMsgBean.Custom,sendContent);
        updateMsg(bean);
        MsgManager.INSTANCE.sendCustomMsg(chatId, sendContent, new SendMsgListener() {

            @Override
            public void onSendSuc(TIMMessage timMessage) {
                refreshMsg(bean,C2CMsgBean.SEND_STATUS_SUCCESS);
                updataSendMsgInfo();
            }

            @Override
            public void onSendFail(int i, String s) {
                ToastUtil.INSTANCE.suc(PrivateChatActivity.this,s);
                refreshMsg(bean,C2CMsgBean.SEND_STATUS_FAILE);
            }
        });
    }

    private void sendReply(String content,String preString){
        if (isBlack){
            ToastUtil.INSTANCE.suc(this,"你已被对方拉入黑名单，无法进行私聊");
            return;
        }
        C2CCustomBean c2CGiftBean = new C2CCustomBean();
        c2CGiftBean.setMsgType(C2CMsgBean.CUSTOM_CHAT_REPLY);
        c2CGiftBean.setContent(content);
        c2CGiftBean.setPreTextMsg(preString);
        String sendContent = new Gson().toJson(c2CGiftBean);
        final C2CMsgBean bean = MsgUtils.buildSendMsg(C2CMsgBean.Custom,sendContent);
        updateMsg(bean);
        MsgManager.INSTANCE.sendCustomMsg(chatId, sendContent, new SendMsgListener() {

            @Override
            public void onSendSuc(TIMMessage timMessage) {
                refreshMsg(bean,C2CMsgBean.SEND_STATUS_SUCCESS);
                updataSendMsgInfo();
            }

            @Override
            public void onSendFail(int i, String s) {
                ToastUtil.INSTANCE.suc(PrivateChatActivity.this,s);
                refreshMsg(bean,C2CMsgBean.SEND_STATUS_FAILE);
            }
        });
    }

    private void sendSayHello(String content){
        if (isBlack){
            ToastUtil.INSTANCE.suc(this,"你已被对方拉入黑名单，无法进行私聊");
            return;
        }
        C2CCustomBean c2CGiftBean = new C2CCustomBean();
        c2CGiftBean.setMsgType(C2CMsgBean.CUSTOM_CHAT_HELLO);
        c2CGiftBean.setContent(content);
        String sendContent = new Gson().toJson(c2CGiftBean);
        final C2CMsgBean bean = MsgUtils.buildSendMsg(C2CMsgBean.Custom,sendContent);
        updateMsg(bean);
        MsgManager.INSTANCE.sendCustomMsg(chatId, sendContent, new SendMsgListener() {

            @Override
            public void onSendSuc(TIMMessage timMessage) {
                refreshMsg(bean,C2CMsgBean.SEND_STATUS_SUCCESS);
                updataSendMsgInfo();
            }

            @Override
            public void onSendFail(int i, String s) {
                ToastUtil.INSTANCE.suc(PrivateChatActivity.this,s);
                refreshMsg(bean,C2CMsgBean.SEND_STATUS_FAILE);
            }
        });
    }

    private void sendImgMsg(final String path){
        if (isBlack){
            ToastUtil.INSTANCE.suc(this,"你已被对方拉入黑名单，无法进行私聊");
            return;
        }
        final C2CMsgBean bean = MsgUtils.buildSendMsg(C2CMsgBean.Image,path);
        bean.setWidth(DensityUtil.INSTANCE.dp2px(PrivateChatActivity.this,200));
        bean.setHeight(DensityUtil.INSTANCE.dp2px(PrivateChatActivity.this,200));
        updateMsg(bean);
        MsgManager.INSTANCE.sendC2CImage(chatId, path, new MsgManager.ImageMsgCallBack() {
            @Override
            public void onSendSuc(TIMMessage timMessage,String url,int height,int width) {
                bean.setTimMessage(timMessage);
                initHistoryMessage();
                updataSendMsgInfo();
            }

            @Override
            public void onSendFail(int i, String s) {
                ToastUtil.INSTANCE.suc(PrivateChatActivity.this,s);
                initHistoryMessage();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
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

    private void hideRecordFl(){
        isRecordShow = false;
        recordFl.setVisibility(View.GONE);
    }
    private void showRecordFl(){
        isRecordShow = true;
        recordFl.setVisibility(View.VISIBLE);
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        emojiVp.setVisibility(View.GONE);
        if (id == R.id.tv_send){
            sendTextMsg(contentEt.getText().toString());
            contentEt.setText("");
        }
        if (id == R.id.ll_picture){
            photoUtils.chooseOriginGallary(this, new PhotoUtils.OnSelectPhotoListener() {
                @Override
                public void onSelected(String url) {
                    if (!TextUtils.isEmpty(url)){
                        sendImgMsg(url);
                    }
                }
            });
        }
        if (id == R.id.ll_emoji){

            if (!isnitEmoji){
               return;
            }

            DensityUtil.INSTANCE.hideSoftKeyboard(this);
            // TODO: 2019/10/17 表情
            hideGiftTab();
            hideRecordFl();
            if (isEmojiVpShow){
                hideEmoji();
            }else{
                showEmoji();
            }
        }
//        if (id == R.id.ll_take_photo){
//            requestPermissionCamera();
//        }
        if (id == R.id.iv_gift){
            DensityUtil.INSTANCE.hideSoftKeyboard(this);
            hideEmoji();
            hideRecordFl();
            changeGiftTabStatus();
        }
        if (id == R.id.ll_voice){
            if (CommonLib.INSTANCE.isInRoom()){
                ToastUtil.INSTANCE.suc(PrivateChatActivity.this,"房间中，请退出后使用~");
                return;
            }
            hideGiftTab();
            hideEmoji();
            if (isRecordShow){
                hideRecordFl();
            }else{
                showRecordFl();
            }
        }
        if (id == R.id.iv_user_info){
            showDialog();
//            ARouter.getInstance().build("/app/userhomepage")
//                    .withString("user_id",chatId)
//                    .withString("user_name","")
//                    .withString("user_face","")
//                    .navigation();
        }
    }

    /**
     * 显示更多弹出框
     */
     private void showDialog() {
                 if (moreDialog == null) {
                     initMoreDialog();
                     }
         moreDialog.show();
            }
    /**
     * 初始化更多弹出框
    */
     private void initMoreDialog() {
         moreDialog = new Dialog(this, R.style.DialogFullScreen);
         moreDialog.setCanceledOnTouchOutside(true);
         moreDialog.setCancelable(true);
         Window window = moreDialog.getWindow();
         window.setGravity(Gravity.BOTTOM);
         window.setWindowAnimations(R.style.dialogAnimStyle1);
         View view = View.inflate(this, R.layout.dialog_msg_more, null);
         view.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
           @Override
          public void onClick(View view) {
               if (moreDialog != null && moreDialog.isShowing()) {
                     moreDialog.dismiss();
               }
            }
       });
         view.findViewById(R.id.tv_report).setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 if (moreDialog != null && moreDialog.isShowing()) {
                     moreDialog.dismiss();

                     ARouter.getInstance().build("/app/report")
                             .withString("USER_ID", chatId)
                             .withInt("REPORT_OBJECT", 1)
                             .navigation();
                 }
             }
         });
         view.findViewById(R.id.tv_block).setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 if (moreDialog != null && moreDialog.isShowing()) {
                     addBlackDialog = new CommonDialog(PrivateChatActivity.this)
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

                                     NetService.Companion.getInstance(getBaseContext()).addBlack(chatId, "", new Callback<BaseBean>() {
                                         @Override
                                         public boolean isAlive() {
                                             return isLive();
                                         }

                                         @Override
                                         public void onError(@NotNull String msg, @NotNull Throwable throwable, int code) {
                                             ToastUtil.INSTANCE.error(getBaseContext(), msg);
                                         }

                                         @Override
                                         public void onSuccess(int nextPage, BaseBean bean, int code) {
                                             ToastUtil.INSTANCE.suc(getBaseContext(), "拉黑成功");

                                         }
                                     });

                                     addBlackDialog.dismiss();

                                 }
                             });
                     addBlackDialog.show();
                 }
             }
         });
         view.findViewById(R.id.tv_clean).setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 if (moreDialog != null && moreDialog.isShowing()) {
                     addBlackDialog = new CommonDialog(PrivateChatActivity.this)
                             .setContent("确定清空聊天记录?")
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

                                     for (int i =0;i<msgBeanList.size();i++){
                                         msgBeanList.get(i).getTimMessage().remove();
                                         chatAdapter.notifyItemRemoved(i);

                                     }

                                     msgBeanList.clear();
                                     chatAdapter.notifyDataSetChanged();
                                 }
                             });
                     addBlackDialog.show();


//                     if (MsgManager.INSTANCE.deleteConversationAndLocalMsgs(chatId)) {
//                         ToastUtil.INSTANCE.suc(getBaseContext(), "清除成功");
//                     }else{
//                         ToastUtil.INSTANCE.suc(getBaseContext(), "清除失败");
//                     }
                 }
             }
         });
         window.setContentView(view);
         window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);//设置横向全屏
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
        chatAdapter.notifyItemInserted(msgBeanList.size());
        chatRecyclerview.scrollToPosition(chatAdapter.getItemCount()-1);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == PhotoUtils.GALLERY_REQUEST) {
//            if (data == null){
//                return;
//            }
//            final String url = data.getStringExtra(AlbumActivity.RESULT_PICTURE);
//            if (!TextUtils.isEmpty(url)){
//                sendImgMsg(url);
//            }
//        }
        if (requestCode == REQUEST_CAMERA) {
            if (!TextUtils.isEmpty(mFilePath) && mImageFile.length()>0) {
                sendImgMsg(mFilePath);
            }
        }
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
//    @Override
//    public void onPermissionsGranted(int requestCode, List<String> perms) {
//
//    }
//
//    @Override
//    public void onPermissionsDenied(int requestCode, List<String> perms) {
//        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
//            new AppSettingsDialog.Builder(this)
//                    .setTitle("需要权限")
//                    .setRationale("你拒绝了我们的权限申请，我们不能进行正常的操作，是否去设置权限？")
//                    .build()
//                    .show();
//        }
//    }
//    @AfterPermissionGranted(PhotoUtils.PERMISSION_CAMERA_NUM)
//    public void requestPermissionCamera() {
//        if (XXPermissions.isHasPermission(this, Manifest.permission.CAMERA)){
//            mFilePath = Environment.getExternalStorageDirectory().getPath() + "/" + String.valueOf(System.currentTimeMillis()) + "photo.jpg";
//            mImageFile = new File(mFilePath);
//            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//            //加载路径
//            Uri uri;
//            if (android.os.Build.VERSION.SDK_INT < 24) {
//                uri = Uri.fromFile(mImageFile);
//            } else {
//                ContentValues contentValues = new ContentValues(1);
//                contentValues.put(MediaStore.Images.Media.DATA, mFilePath);
//                uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
//            }
//            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
//            startActivityForResult(intent, REQUEST_CAMERA);
//        }
//        if (EasyPermissions.hasPermissions(this, PhotoUtils.PERMISSION_CAMERA)) {
//        } else {
//            EasyPermissions.requestPermissions(
//                    this,
//                    "需要获取你的相机",
//                    PhotoUtils.PERMISSION_CAMERA_NUM, PhotoUtils.PERMISSION_CAMERA);
//        }
//    }
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
//    }
    class RecordOnTouchListener implements View.OnTouchListener {

        @Override
        public boolean onTouch(View v, MotionEvent motionEvent) {
            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    mAudioCancel = true;
                    mStartRecordY = motionEvent.getY();
                    startRecording();
                    voiceStatusFl.setVisibility(View.VISIBLE);
                    voiceStatusTv.setText("松开结束");
                    AudioPlayer.getInstance().startRecord(new AudioPlayer.Callback() {
                        @Override
                        public void onCompletion(Boolean success) {
                            recordComplete(success);
                        }
                    });
                    break;
                case MotionEvent.ACTION_MOVE:
                    if (motionEvent.getY() - mStartRecordY < -100) {
                        mAudioCancel = true;
                        cancelRecording();
                    } else {
                        if (mAudioCancel) {
                            startRecording();
                        }
                        mAudioCancel = false;
                    }
                    break;
                case MotionEvent.ACTION_CANCEL:
                case MotionEvent.ACTION_UP:
                    mAudioCancel = motionEvent.getY() - mStartRecordY < -100;

                    stopRecording();
                    AudioPlayer.getInstance().stopRecord();
                    break;
                default:
                    break;
            }
            return false;
        }
    }

    private void updataSendMsgInfo(){
        if (CommonLib.INSTANCE.isInRoom()){
            NetService.Companion.getInstance(PrivateChatActivity.this).updataSendMsgInfo(chatId, CommonLib.INSTANCE.getRoomId(), new Callback<String>() {
                @Override
                public void onSuccess(int nextPage, String bean, int code) {
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
    }

    private void follow() {
        NetService.Companion.getInstance(PrivateChatActivity.this).addConcern(DataHelper.INSTANCE.getLoginToken(), chatId, new Callback<FollowBean>() {
            @Override
            public void onSuccess(int nextPage, FollowBean bean, int code) {
                ToastUtil.INSTANCE.suc(PrivateChatActivity.this,"关注成功");
                userInfoIv.setVisibility(View.GONE);
                sendTextMsg("我想认识你，趁风不注意~");
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

    @Override
    protected void onStart() {
        super.onStart();
        initHistoryMessage();
    }

}
