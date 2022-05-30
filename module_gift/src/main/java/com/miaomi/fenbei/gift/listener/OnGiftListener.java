package com.miaomi.fenbei.gift.listener;

import androidx.annotation.NonNull;

import com.miaomi.fenbei.base.bean.MsgGiftBean;
import com.miaomi.fenbei.base.bean.UserInfo;

public interface OnGiftListener {
    void onSendSuccess(@NonNull MsgGiftBean bean,@NonNull UserInfo toUserInfo);
    void onSendFail(@NonNull String msg);
    void onSendExpressGiftSuccess(@NonNull MsgGiftBean bean,@NonNull UserInfo toUserInfo,String note);
    void onSendChest(@NonNull MsgGiftBean bean,@NonNull UserInfo toUserInfo);
    void showPayDialog();
    void showPayActivity();
}
