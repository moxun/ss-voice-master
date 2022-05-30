package com.miaomi.fenbei.room;

import com.miaomi.fenbei.base.bean.UserInfo;

public interface RoomCommonOprateCallback {
    void onFinish();
    void onShowInputDialog(boolean isShow,int selectedType);
    void onShowMsgDialog();
    void onEnterRoom(UserInfo userInfo);
}
