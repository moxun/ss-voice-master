package com.miaomi.fenbei.base.core.msg;


import android.content.Context;



import com.google.gson.Gson;
import com.miaomi.fenbei.base.BuildConfig;
import com.miaomi.fenbei.base.bean.CCMLotteryBean;
import com.miaomi.fenbei.base.bean.GameXXBean;
import com.miaomi.fenbei.base.bean.IMDevelopBean;
import com.miaomi.fenbei.base.bean.MsgBean;
import com.miaomi.fenbei.base.bean.MsgType;
import com.miaomi.fenbei.base.bean.RoomInfoBean;
import com.miaomi.fenbei.base.bean.UserInfo;
import com.miaomi.fenbei.base.bean.WealthLevelBean;
import com.miaomi.fenbei.base.bean.event.C2CMsgBean;
import com.miaomi.fenbei.base.bean.event.ConversationBean;
import com.miaomi.fenbei.base.bean.event.UnReadNumBean;
import com.miaomi.fenbei.base.net.Callback;
import com.miaomi.fenbei.base.net.NetService;
import com.miaomi.fenbei.base.util.ConversationUtils;
import com.miaomi.fenbei.base.util.DataHelper;
import com.miaomi.fenbei.base.util.LogUtil;
import com.miaomi.fenbei.base.util.LoginHelper;
import com.tencent.imsdk.TIMCallBack;
import com.tencent.imsdk.TIMConversation;
import com.tencent.imsdk.TIMConversationType;
import com.tencent.imsdk.TIMCustomElem;
import com.tencent.imsdk.TIMElem;
import com.tencent.imsdk.TIMElemType;
import com.tencent.imsdk.TIMFaceElem;
import com.tencent.imsdk.TIMGroupManager;
import com.tencent.imsdk.TIMGroupMemberInfo;
import com.tencent.imsdk.TIMGroupSystemElem;
import com.tencent.imsdk.TIMImage;
import com.tencent.imsdk.TIMImageElem;
import com.tencent.imsdk.TIMImageType;
import com.tencent.imsdk.TIMManager;
import com.tencent.imsdk.TIMMessage;
import com.tencent.imsdk.TIMMessageListener;
import com.tencent.imsdk.TIMSoundElem;
import com.tencent.imsdk.TIMTextElem;
import com.tencent.imsdk.TIMValueCallBack;

import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

public enum MsgManager {

    INSTANCE;
    private Context context;


    public void setContext(Context context) {
        this.context = context;

    }

    private HashSet<MsgListener> msgListeners = new HashSet<>();

    public interface MsgCallBack {
        void onSendSuc();

        void onSendFail(int i, String s);
    }

    public interface ReadMsgCallBack {
        void onSendSuc(int position);

    }

    public interface GronpMemberCallBack {
        void onSuccess(int members);

    }

    public interface ImageMsgCallBack {
        void onSendSuc(TIMMessage timMessage,String url,int height,int width);

        void onSendFail(int i, String s);
    }


    MsgManager() {
        TIMManager.getInstance().addMessageListener(new TIMMessageListener() {
            @Override
            public boolean onNewMessages(List<TIMMessage> list) {

                for (TIMMessage msg : list) {
                    if (msg.getConversation().getType() == TIMConversationType.System) {
                        //系统消息
                        for (int i = 0; i < msg.getElementCount(); i++) {
                            TIMElem elem = msg.getElement(i);
                            TIMElemType elemType = elem.getType();
                            if (elemType == TIMElemType.GroupSystem) {
                                TIMGroupSystemElem systemElem = ((TIMGroupSystemElem) elem);
                                if (systemElem.getSubtype().name().equals("TIM_GROUP_SYSTEM_CUSTOM_INFO")) {
                                    for (final MsgListener msgListener : msgListeners) {
                                        msgListener.onNewMsg(new String(systemElem.getUserData()));
                                    }
                                } else if (systemElem.getSubtype().name().equals("TIM_GROUP_SYSTEM_DELETE_GROUP_TYPE")) {
                                    for (final MsgListener msgListener : msgListeners) {
                                        MsgBean bean = new MsgBean(MsgType.DELETE_GROUP, "", systemElem.getGroupId(), "", "", 0, 0, 0L, null, null, null, new RoomInfoBean(), new UserInfo(), new UserInfo(),"",0,new CCMLotteryBean(),new ArrayList<GameXXBean>());
                                        msgListener.onNewMsg(new Gson().toJson(bean));
                                    }
                                }
                            }
                        }
                        continue;
                    }
                    if (msg.getConversation().getType() == TIMConversationType.Group){
                        //群聊消息
                        for (int i = 0; i < msg.getElementCount(); ++i) {
                            TIMElem elem = msg.getElement(i);
                            TIMElemType elemType = elem.getType();
                            if (elemType == TIMElemType.Text) {
                                TIMTextElem textElem = ((TIMTextElem) elem);
                                for (final MsgListener msgListener : msgListeners) {
                                    msgListener.onNewMsg(textElem.getText());
                                }
                            }
                        }
                    }
                    if (msg.getConversation().getType() == TIMConversationType.C2C){
                        getConversationList();
                        //单聊消息
                        final C2CMsgBean bean = MsgUtils.buildAcceptMsg(msg);
                        EventBus.getDefault().post(bean);
                    }
                }
                return false;
            }
        });
    }

    public void getGroupNumSize(String roomId, final GronpMemberCallBack gronpMemberCallBack){
        TIMGroupManager.getInstance().getGroupMembers(roomId, new TIMValueCallBack<List<TIMGroupMemberInfo>>() {
            @Override
            public void onError(int code, String desc) {

            }

            @Override
            public void onSuccess(List<TIMGroupMemberInfo> timGroupMemberInfos) {
                gronpMemberCallBack.onSuccess(timGroupMemberInfos.size());
            }
        });
    }

    public void addMsgListener(MsgListener msgListener) {
        msgListeners.add(msgListener);
    }

    public void removeMsgListener(MsgListener msgListener) {
        msgListeners.remove(msgListener);
    }


    public void createGroup(final String chatId, final MsgCallBack callback) {
        final IMDevelopBean imDevelop = DataHelper.INSTANCE.getIMDevelop();
        LoginHelper.INSTANCE.loginTim(new LoginHelper.Callback() {
            @Override
            public void onSuc() {
                TIMGroupManager.CreateGroupParam param = new TIMGroupManager.CreateGroupParam("AVChatRoom", imDevelop.getPrefix() + chatId);
                param.setGroupId(imDevelop.getPrefix() + chatId);
                TIMGroupManager.getInstance().createGroup(param, new TIMValueCallBack<String>() {
                    @Override
                    public void onError(int code, String desc) {

                        if (code == 10025) {
                            callback.onSendSuc();
                        } else {
                            callback.onSendFail(code, desc);
                        }
                    }

                    @Override
                    public void onSuccess(String s) {
                        callback.onSendSuc();
                    }
                });
            }

            @Override
            public void onFail(@NotNull String msg) {
                callback.onSendFail(6014, msg);
            }
        });


    }

    public void joinGroup(final String chatId, final MsgCallBack callback) {
        final IMDevelopBean imDevelop = DataHelper.INSTANCE.getIMDevelop();
        LoginHelper.INSTANCE.loginTim(new LoginHelper.Callback() {
            @Override
            public void onSuc() {
                TIMGroupManager.getInstance().applyJoinGroup(imDevelop.getPrefix() + chatId, "", new TIMCallBack() {
                    @Override
                    public void onError(int i, String s) {
                        callback.onSendFail(i, s);
                    }

                    @Override
                    public void onSuccess() {
                        callback.onSendSuc();
                    }
                });
            }

            @Override
            public void onFail(@NotNull String msg) {
                callback.onSendFail(6014, msg);
            }
        });


    }


    public void joinBigGroup() {
        LoginHelper.INSTANCE.loginTim(new LoginHelper.Callback() {
            @Override
            public void onSuc() {
                TIMGroupManager.getInstance().quitGroup(DataHelper.INSTANCE.getIMDevelop().getImBigGroupID(), new TIMCallBack() {
                    @Override
                    public void onError(int i, String s) {

                    }

                    @Override
                    public void onSuccess() {
                        TIMGroupManager.getInstance().applyJoinGroup(DataHelper.INSTANCE.getIMDevelop().getImBigGroupID(), "", new TIMCallBack() {
                            @Override
                            public void onError(int i, String s) {
                                LogUtil.INSTANCE.e("errorCode:" + i + "msg:" + s);
                            }

                            @Override
                            public void onSuccess() {
                                LogUtil.INSTANCE.d("GROUPID:" + DataHelper.INSTANCE.getIMDevelop().getImBigGroupID() + "\nBIG GROUP JOIN SUCCEED");
                            }
                        });
                    }
                });
            }

            @Override
            public void onFail(@NotNull String msg) {
            }
        });
    }

    public void quitGroup(final String chatId, final MsgCallBack callBack) {
        final IMDevelopBean imDevelop = DataHelper.INSTANCE.getIMDevelop();
        TIMGroupManager.getInstance().quitGroup(imDevelop.getPrefix() + chatId, new TIMCallBack() {
            @Override
            public void onError(int i, String s) {
                callBack.onSendFail(i, s);
            }

            @Override
            public void onSuccess() {
                callBack.onSendSuc();
            }
        });
    }

    public void sendFollowMsg(final String chatId, final SendMsgListener callback){
        MsgManager.INSTANCE.sendC2CText(chatId, "我想认识你，趁风不注意~", callback);
    }


    public void sendTextMsg(final String chatId, final String msgText, final MsgCallBack callback) {
        LoginHelper.INSTANCE.loginTim(new LoginHelper.Callback() {
            @Override
            public void onSuc() {
                TIMConversation conversation = TIMManager.getInstance().getConversation(TIMConversationType.Group, DataHelper.INSTANCE.getIMDevelop().getPrefix() + chatId);
                TIMMessage msg = new TIMMessage();
                TIMTextElem elem = new TIMTextElem();
                elem.setText(msgText);
                msg.addElement(elem);
                conversation.sendMessage(msg, new TIMValueCallBack<TIMMessage>() {
                    @Override
                    public void onError(int i, String s) {
                        callback.onSendFail(i, s);
                    }

                    @Override
                    public void onSuccess(TIMMessage timMessage) {
                        callback.onSendSuc();
                    }
                });
            }

            @Override
            public void onFail(@NotNull String msg) {
                callback.onSendFail(6014, msg);
            }
        });
    }

    public void sendC2CImage(final String chatId, final String imagePath, final ImageMsgCallBack callback) {
        LoginHelper.INSTANCE.loginTim(new LoginHelper.Callback() {
            @Override
            public void onSuc() {
                TIMConversation conversation = TIMManager.getInstance().getConversation(TIMConversationType.C2C,
                        DataHelper.INSTANCE.getIMDevelop().getPrefix() + chatId);
                //构造一条消息
                TIMMessage msg = new TIMMessage();
                //添加图片
                TIMImageElem elem = new TIMImageElem();
                elem.setPath(imagePath);
                //将 elem 添加到消息
                if(msg.addElement(elem) != 0) {
                    return;
                }
                //发送消息
                conversation.sendMessage(msg, new TIMValueCallBack<TIMMessage>() {//发送消息回调
                    @Override
                    public void onError(int code, String desc) {//发送消息失败
                        callback.onSendFail(code,desc);
                    }

                    @Override
                    public void onSuccess(TIMMessage msg) {//发送消息成功
                        for (int i = 0; i < msg.getElementCount(); ++i) {
                            TIMElem elem = msg.getElement(i);
                            TIMElemType elemType = elem.getType();
                            if (elemType == TIMElemType.Image) {
                                //图片消息
                                TIMImageElem imageElem = ((TIMImageElem) elem);
                                for (final TIMImage image : imageElem.getImageList()) {
                                    if (image.getType() == TIMImageType.Thumb) {
                                        callback.onSendSuc(msg,image.getUrl(),(int) image.getHeight(), (int) image.getWidth());
                                    }
                                }
                            }
                        }
                    }
                });
            }

            @Override
            public void onFail(@NotNull String msg) {
                callback.onSendFail(6014, msg);
            }
        });
    }

    public void sendC2CText(final String chatId, final String msgText, final SendMsgListener callback) {
        LoginHelper.INSTANCE.loginTim(new LoginHelper.Callback() {
            @Override
            public void onSuc() {
                TIMConversation conversation = TIMManager.getInstance().getConversation(TIMConversationType.C2C, DataHelper.INSTANCE.getIMDevelop().getPrefix() + chatId);
                TIMMessage msg = new TIMMessage();
                TIMTextElem elem = new TIMTextElem();
                elem.setText(msgText);
                msg.addElement(elem);
                conversation.sendMessage(msg, new TIMValueCallBack<TIMMessage>() {
                    @Override
                    public void onError(int i, String s) {
                        callback.onSendFail(i, s);
                    }

                    @Override
                    public void onSuccess(TIMMessage timMessage) {
                        callback.onSendSuc(timMessage);
                    }
                });
            }

            @Override
            public void onFail(@NotNull String msg) {
                callback.onSendFail(6014, msg);
            }
        });
    }

    public void sendC2CSound(final String chatId, final String filePath, final int duration, final SendMsgListener callback){
        LoginHelper.INSTANCE.loginTim(new LoginHelper.Callback() {
            @Override
            public void onSuc() {
                TIMConversation conversation = TIMManager.getInstance().getConversation(TIMConversationType.C2C,
                        DataHelper.INSTANCE.getIMDevelop().getPrefix() + chatId);
                TIMMessage msg = new TIMMessage();
                //添加语音
                TIMSoundElem elem = new TIMSoundElem();
                elem.setPath(filePath); //填写语音文件路径
                elem.setDuration(duration);  //填写语音时长

                //将 elem 添加到消息
                if(msg.addElement(elem) != 0) {
                    return;
                }
                conversation.sendMessage(msg, new TIMValueCallBack<TIMMessage>() {
                    @Override
                    public void onError(int i, String s) {
                        callback.onSendFail(i, s);
                    }

                    @Override
                    public void onSuccess(TIMMessage timMessage) {
                        callback.onSendSuc(timMessage);
                    }
                });
            }

            @Override
            public void onFail(@NotNull String msg) {
                callback.onSendFail(6014, msg);
            }
        });
    }

    public void sendCustomMsg(String chatId, String content, final SendMsgListener callback){
        TIMMessage msg = new TIMMessage();
        TIMConversation conversation = TIMManager.getInstance().getConversation(TIMConversationType.C2C, DataHelper.INSTANCE.getIMDevelop().getPrefix() + chatId);
        TIMCustomElem elem = new TIMCustomElem();
        elem.setData(content.getBytes());      //自定义 byte[]

        if(msg.addElement(elem) != 0) {
            return;
        }
        conversation.sendMessage(msg, new TIMValueCallBack<TIMMessage>() {
            @Override
            public void onError(int i, String s) {
                callback.onSendFail(i, s);
            }

            @Override
            public void onSuccess(TIMMessage timMessage) {
                callback.onSendSuc(timMessage);
            }
        });
    }


    public void sendC2CEmoji(final String chatId, final String url, final int index, final SendMsgListener callback){
        LoginHelper.INSTANCE.loginTim(new LoginHelper.Callback() {
            @Override
            public void onSuc() {
                TIMConversation conversation = TIMManager.getInstance().getConversation(TIMConversationType.C2C, DataHelper.INSTANCE.getIMDevelop().getPrefix() + chatId);
                //构造一条消息
                TIMMessage msg = new TIMMessage();
                //添加表情
                TIMFaceElem elem = new TIMFaceElem();
                elem.setData(url.getBytes()); //自定义 byte[]
                elem.setIndex(index);   //自定义表情索引

                if (msg.addElement(elem) != 0) {
                    return;
                }

                //发送消息
                conversation.sendMessage(msg, new TIMValueCallBack<TIMMessage>() {//发送消息回调
                    @Override
                    public void onError(int code, String desc) {//发送消息失败
                        //错误码 code 和错误描述 desc，可用于定位请求失败原因
                        //错误码 code 含义请参见错误码表
                        callback.onSendFail(6014, desc);
                    }

                    @Override
                    public void onSuccess(TIMMessage msg) {//发送消息成功
                        callback.onSendSuc(msg);
                    }
                });
            }

            @Override
            public void onFail(@NotNull String msg) {
                callback.onSendFail(6014, msg);
            }
        });
    }

    /**
     * 获取系统账号UID
     * @return
     */
    public String getSystemUid(){
        if (BuildConfig.DEBUG){
            return "6001";
        }else{
            return "6001";
        }
    }


    public interface GetLocalMessageCallBack {
        void onSendSuc(List<C2CMsgBean> list,long unReadNum);

        void onSendFail(int i, String s);
    }

    public void getMessageList(final String chatId, final int count, final GetLocalMessageCallBack callBack){
        LoginHelper.INSTANCE.loginTim(new LoginHelper.Callback() {
            @Override
            public void onSuc() {
                //获取会话扩展实例
                final TIMConversation con = TIMManager.getInstance().getConversation(TIMConversationType.C2C, DataHelper.INSTANCE.getIMDevelop().getPrefix() + chatId);
                //获取此会话的消息
                con.getMessage(count, //获取此会话最近的 10 条消息
                        null, //不指定从哪条消息开始获取 - 等同于从最新的消息开始往前
                        new TIMValueCallBack<List<TIMMessage>>() {//回调接口
                            @Override
                            public void onError(int code, String desc) {//获取消息失败
                                //接口返回了错误码 code 和错误描述 desc，可用于定位请求失败原因
                                callBack.onSendFail(code, desc);
                            }

                            @Override
                            public void onSuccess(List<TIMMessage> msgs) {//获取消息成功
                                List<C2CMsgBean> msgStrs = new ArrayList<>();
                                for(TIMMessage msg : msgs) {
                                    final C2CMsgBean bean = MsgUtils.buildAcceptMsg(msg);
                                    msgStrs.add(bean);
                                }
                                callBack.onSendSuc(msgStrs,con.getUnreadMessageNum());
                            }
                        });
            }

            @Override
            public void onFail(@NotNull String msg) {
                callBack.onSendFail(6014, msg);
            }
        });
    }
    private long unReadNUm = 0;
    List<ConversationBean> mConversationList = new ArrayList<>();
    public void getConversationList(){
        unReadNUm = 0;
        mConversationList.clear();
        ArrayList<String> userIds = new ArrayList<>();
        for (TIMConversation bean : TIMManager.getInstance().getConversationList()){

            if(bean.getType() == TIMConversationType.C2C){
                if (bean.getLastMsg() != null){
                    String userId = bean.getPeer().substring(bean.getPeer().indexOf("_") + 1);
                    final ConversationBean conversationBean = new ConversationBean();

                    TIMMessage msg = bean.getLastMsg();
                    conversationBean.setUser_id(userId);
                    conversationBean.setUnReadNum(bean.getUnreadMessageNum());
                    conversationBean.setTime(msg.timestamp());
                    conversationBean.setNickname(userId);
                    conversationBean.setFace("");
                    conversationBean.setGender(1);
                    conversationBean.setRelation(1);
                    unReadNUm = unReadNUm + bean.getUnreadMessageNum();
                    ConversationBean userInfo = null;
                    if (!bean.getPeer().substring(bean.getPeer().indexOf("_") + 1).equals("admin")){
                        userInfo = ConversationUtils.getConversation(Long.parseLong(bean.getPeer().substring(bean.getPeer().indexOf("_") + 1)));

                    }
                    if (userInfo == null){
                        userIds.add(userId);
                    }else{
                        conversationBean.setNickname(userInfo.getNickname());
                        conversationBean.setFace(userInfo.getFace());
                        conversationBean.setGender(userInfo.getGender());
                        conversationBean.setAge(userInfo.getAge());
                        conversationBean.setRelation(userInfo.getRelation());
                        WealthLevelBean bean1 = new WealthLevelBean();
                        bean1.setGrade(userInfo.getLevel());
                        conversationBean.setWealth_level(bean1);
                    }
                    for (int i = 0; i < msg.getElementCount(); ++i) {
                        TIMElem elem = msg.getElement(i);
                        TIMElemType elemType = elem.getType();
                        if (elemType == TIMElemType.Text) {
                            TIMTextElem textElem = ((TIMTextElem) elem);
                            conversationBean.setMsgType(C2CMsgBean.Text);
                            conversationBean.setContent(textElem.getText());
                            break;
                        } else if(elemType == TIMElemType.Image){
                            //图片消息
                            TIMImageElem imageElem = ((TIMImageElem) elem);
                            if (imageElem.getImageList().size()>0){
                                TIMImage image = imageElem.getImageList().get(0);
                                conversationBean.setMsgType(C2CMsgBean.Image);
                                conversationBean.setContent(image.getUrl());
                            }
                            break;
                        }else if (elemType  == TIMElemType.Face){
                            conversationBean.setMsgType(C2CMsgBean.Emoji);
                        }else if (elemType  == TIMElemType.Sound){
                            conversationBean.setMsgType(C2CMsgBean.Sound);
                        }else if (elemType == TIMElemType.Custom){
                            C2CMsgBean customBean = MsgUtils.buildAcceptMsg(msg);
                            conversationBean.setMsgType(customBean.getMsgType());
                            conversationBean.setContent(customBean.getTxtContent());
                        }
                    }
                    mConversationList.add(conversationBean);
                }
            }
        }
        if (userIds.size() > 0){
            NetService.Companion.getInstance(context).getConversationUserInfos(userIds.toString(), new Callback<List<ConversationBean>>() {
                @Override
                public void onSuccess(int nextPage, List<ConversationBean> list, int code) {
                    if (list.size() > 0){
                        for (int i = 0 ;i<list.size();i++){
                            ConversationUtils.addConversation(list.get(i),list.get(i).getWealth_level().getGrade());
                        }
                        mConversationList.clear();
                        for (TIMConversation bean : TIMManager.getInstance().getConversationList()){
                            if(bean.getType() == TIMConversationType.C2C){
                                if (bean.getLastMsg() != null){
                                    String userId = bean.getPeer().substring(bean.getPeer().indexOf("_") + 1);
                                    final ConversationBean conversationBean = new ConversationBean();
                                    TIMMessage msg = bean.getLastMsg();
                                    conversationBean.setUser_id(userId);
                                    conversationBean.setUnReadNum(bean.getUnreadMessageNum());
                                    conversationBean.setTime(msg.timestamp());
                                    conversationBean.setNickname(userId);
                                    conversationBean.setFace("");
                                    conversationBean.setGender(1);
                                    conversationBean.setRelation(1);
                                    unReadNUm = unReadNUm + bean.getUnreadMessageNum();
                                    final ConversationBean userInfo = ConversationUtils.getConversation(Long.valueOf(bean.getPeer().substring(bean.getPeer().indexOf("_") + 1)));
                                    if (userInfo != null){
                                        conversationBean.setNickname(userInfo.getNickname());
                                        conversationBean.setFace(userInfo.getFace());
                                        conversationBean.setGender(userInfo.getGender());
                                        conversationBean.setRelation(userInfo.getRelation());
                                        WealthLevelBean bean1 = new WealthLevelBean();
                                        bean1.setGrade(userInfo.getLevel());
                                        conversationBean.setWealth_level(bean1);
                                    }
                                    for (int i = 0; i < msg.getElementCount(); ++i) {
                                        TIMElem elem = msg.getElement(i);
                                        TIMElemType elemType = elem.getType();
                                        if (elemType == TIMElemType.Text) {
                                            TIMTextElem textElem = ((TIMTextElem) elem);
                                            conversationBean.setMsgType(C2CMsgBean.Text);
                                            conversationBean.setContent(textElem.getText());
                                            break;
                                        } else if(elemType == TIMElemType.Image){
                                            //图片消息
                                            TIMImageElem imageElem = ((TIMImageElem) elem);
                                            if (imageElem.getImageList().size()>0){
                                                TIMImage image = imageElem.getImageList().get(0);
                                                conversationBean.setMsgType(C2CMsgBean.Image);
                                                conversationBean.setContent(image.getUrl());
                                            }
                                            break;
                                        }else if (elemType  == TIMElemType.Face){
                                            conversationBean.setMsgType(C2CMsgBean.Emoji);
                                        }else if (elemType  == TIMElemType.Sound){
                                            conversationBean.setMsgType(C2CMsgBean.Sound);
                                        }else if (elemType == TIMElemType.Custom){
                                            C2CMsgBean customBean = MsgUtils.buildAcceptMsg(msg);
                                            conversationBean.setMsgType(customBean.getMsgType());
                                            conversationBean.setContent(customBean.getTxtContent());
                                        }
                                    }
                                    mConversationList.add(conversationBean);
                                }
                            }
                        }
                        EventBus.getDefault().post(mConversationList);
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
        Collections.sort(mConversationList);
        EventBus.getDefault().post(new UnReadNumBean(unReadNUm));
        EventBus.getDefault().post(mConversationList);
    }



    public boolean deleteConversationAndLocalMsgs(String peer){
        return TIMManager.getInstance().deleteConversationAndLocalMsgs(TIMConversationType.C2C, DataHelper.INSTANCE.getIMDevelop().getPrefix()+peer);
    }
    public boolean deleteConversation(String peer){

        return TIMManager.getInstance().deleteConversation(TIMConversationType.C2C, DataHelper.INSTANCE.getIMDevelop().getPrefix()+peer);
    }
    private void setReadMessage(final int position, String peer,TIMConversationType type, final ReadMsgCallBack callback){
        //对单聊会话已读上报
        TIMConversation conversation = TIMManager.getInstance().getConversation(
                type,
                peer);                      //会话对方用户帐号
        //将此会话的所有消息标记为已读
        conversation.setReadMessage(null, new TIMCallBack() {
            @Override
            public void onError(int code, String desc) {
                callback.onSendSuc(position);
            }

            @Override
            public void onSuccess() {
                callback.onSendSuc(position);
            }
        });

    }

    public void setReadMessageForItemclik(String peer){
        //对单聊会话已读上报
        TIMConversation conversation = TIMManager.getInstance().getConversation(
                TIMConversationType.C2C,    //会话类型：单聊
                DataHelper.INSTANCE.getIMDevelop().getPrefix()+peer);                      //会话对方用户帐号
        //将此会话的所有消息标记为已读
        conversation.setReadMessage(null, new TIMCallBack() {
            @Override
            public void onError(int code, String desc) {
            }

            @Override
            public void onSuccess() {
            }
        });

    }

    public void setAllReadMessage(final ReadMsgCallBack callBack){
        final List<TIMConversation> list = TIMManager.getInstance().getConversationList();
        if (list.size() == 0){
            callBack.onSendSuc(0);
            return;
        }
        for (int i = 0 ;i<list.size();i++){
            setReadMessage(i, list.get(i).getPeer(),list.get(i).getType(), new ReadMsgCallBack() {
                @Override
                public void onSendSuc(int position) {
                    if (position >= (list.size() - 1)){
                        callBack.onSendSuc(0);
                        getConversationList();
                    }
                }

            });
        }
    }

//    public long getAllUnReadMsg(){
//        long unReadNum = 0;
//        List<TIMConversation> list = TIMManager.getInstance().getConversationList();
//        for (TIMConversation bean : list){
//            if(bean.getType() == TIMConversationType.C2C){
//                unReadNum = unReadNum + bean.getUnreadMessageNum();
//            }
//        }
//        return unReadNum;
//    }
}
