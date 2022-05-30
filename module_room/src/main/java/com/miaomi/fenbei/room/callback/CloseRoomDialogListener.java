package com.miaomi.fenbei.room.callback;

public interface CloseRoomDialogListener {
    void onClose();
    void onShare();
    void onReport();
    void onEnterRoom(String roomId);
}
