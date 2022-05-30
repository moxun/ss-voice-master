package com.miaomi.fenbei.base.core.msg;


import com.google.gson.Gson;
import com.miaomi.fenbei.base.bean.C2CCustomBean;
import com.miaomi.fenbei.base.bean.event.C2CMsgBean;
import com.miaomi.fenbei.base.util.DataHelper;
import com.tencent.imsdk.TIMCustomElem;
import com.tencent.imsdk.TIMElem;
import com.tencent.imsdk.TIMElemType;
import com.tencent.imsdk.TIMFaceElem;
import com.tencent.imsdk.TIMImage;
import com.tencent.imsdk.TIMImageElem;
import com.tencent.imsdk.TIMImageType;
import com.tencent.imsdk.TIMMessage;
import com.tencent.imsdk.TIMSoundElem;
import com.tencent.imsdk.TIMTextElem;

import java.io.UnsupportedEncodingException;

public class MsgUtils {
    static C2CMsgBean buildAcceptMsg(TIMMessage timMessage){
        C2CMsgBean msgBean = new C2CMsgBean();
        for (int i = 0; i < timMessage.getElementCount(); ++i) {
            TIMElem elem = timMessage.getElement(i);
            TIMElemType elemType = elem.getType();
            msgBean.setSender(timMessage.getSender());
            msgBean.setTime(timMessage.timestamp());
            msgBean.setTimMessage(timMessage);
            msgBean.setUniqueId(timMessage.getMsgUniqueId());
            msgBean.setIsRead(timMessage.isPeerReaded());
            if (elemType == TIMElemType.Text) {
                TIMTextElem textElem = ((TIMTextElem) elem);
                msgBean.setMsgType(C2CMsgBean.Text);
                msgBean.setTxtContent(textElem.getText());
                break;
            }
            if(elemType == TIMElemType.Image){
                //图片消息
                msgBean.setMsgType(C2CMsgBean.Image);
                TIMImageElem imageElem = ((TIMImageElem) elem);
                for(final TIMImage image : imageElem.getImageList()) {
                    if (image.getType() == TIMImageType.Thumb) {
                        msgBean.setThumbnailImg(image.getUrl());
                        msgBean.setHeight((int) image.getHeight());
                        msgBean.setWidth((int) image.getWidth());
                    }
                    if (image.getType() == TIMImageType.Original) {
                        msgBean.setOriginalImg(image.getUrl());
                        msgBean.setHeight((int) image.getHeight());
                        msgBean.setWidth((int) image.getWidth());
                    }
                }
                break;
            }
            if (elemType == TIMElemType.Sound){
                TIMSoundElem soundElemEle = ((TIMSoundElem) elem);
                msgBean.setMsgType(C2CMsgBean.Sound);
                msgBean.setDuration(soundElemEle.getDuration());
                msgBean.setElement(soundElemEle);
                break;
            }
            if (elemType == TIMElemType.Face){
                TIMFaceElem faceElem = ((TIMFaceElem) elem);
                msgBean.setMsgType(C2CMsgBean.Emoji);
                try {
                    String url = new String(faceElem.getData(),"UTF-8");
                    msgBean.setEmojiUrl(url);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                break;
            }
            if (elemType == TIMElemType.Custom){
                TIMCustomElem customElem = (TIMCustomElem) elem;
                String content = "";
                try {
                    content = new String(customElem.getData(),"UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                msgBean.setMsgType(C2CMsgBean.Custom);
                try {
                    C2CCustomBean customBean = new Gson().fromJson(content, C2CCustomBean.class);
                    msgBean.setCustomBean(customBean);
                    msgBean.setMsgType(customBean.getRealMsgType());
                    msgBean.setTxtContent(customBean.getContent());
                }catch (Exception e){
                }
            }
        }
        return msgBean;
    }

    public static C2CMsgBean buildSendMsg(int type,String content){
        C2CMsgBean msgBean = new C2CMsgBean();
        msgBean.setSender(String.valueOf(DataHelper.INSTANCE.getUserInfo().getUser_id()));
        msgBean.setFace(DataHelper.INSTANCE.getUserInfo().getFace());
        msgBean.setMsgType(type);
        msgBean.setTime(System.currentTimeMillis()/1000);
        if (type == C2CMsgBean.Image){
            msgBean.setThumbnailImg(content);
            msgBean.setOriginalImg(content);
        }
        if (type == C2CMsgBean.Text){
            msgBean.setTxtContent(content);
        }
        if (type == C2CMsgBean.Emoji){
            msgBean.setEmojiUrl(content);
        }
        if (type == C2CMsgBean.Sound){
            msgBean.setSoundPath(content);
        }
        if (type == C2CMsgBean.Custom){
            try {
                C2CCustomBean customBean = new Gson().fromJson(content, C2CCustomBean.class);
                msgBean.setCustomBean(customBean);
            }catch (Exception e){
            }
        }
        return msgBean;
    }
}
