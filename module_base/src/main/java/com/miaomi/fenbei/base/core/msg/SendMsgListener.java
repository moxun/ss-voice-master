package com.miaomi.fenbei.base.core.msg;

import com.tencent.imsdk.TIMMessage;

public interface SendMsgListener {
    void onSendSuc(TIMMessage timMessage);

    void onSendFail(int i, String s);
}
