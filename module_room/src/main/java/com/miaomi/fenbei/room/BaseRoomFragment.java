package com.miaomi.fenbei.room;

import com.miaomi.fenbei.base.bean.UserInfo;
import com.miaomi.fenbei.base.core.BaseFragment;

public abstract class BaseRoomFragment extends BaseFragment {
    public RoomCommonOprateCallback roomCommonOprateCallback;

    public RoomCommonOprateCallback getRoomCommonOprateCallback() {
        return roomCommonOprateCallback;
    }

    public void setRoomCommonOprateCallback(RoomCommonOprateCallback roomCommonOprateCallback) {
        this.roomCommonOprateCallback = roomCommonOprateCallback;
    }

    abstract void onStartRoom();
    abstract void onCloseRoom();
    abstract void showUserCard(UserInfo userInfo);
    abstract void initPreStatus();
}
