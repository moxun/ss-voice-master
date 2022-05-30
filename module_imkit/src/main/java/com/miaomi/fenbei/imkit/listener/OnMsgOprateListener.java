package com.miaomi.fenbei.imkit.listener;

import com.miaomi.fenbei.base.bean.event.C2CMsgBean;

public interface OnMsgOprateListener {
    void onCopyClick( C2CMsgBean msg);

    void onDeleteMessageClick( C2CMsgBean msg);

    void onRevokeMessageClick( C2CMsgBean msg);
}
