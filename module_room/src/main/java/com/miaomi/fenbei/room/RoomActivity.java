package com.miaomi.fenbei.room;

import android.Manifest;
import android.content.Intent;

import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.indicatorlib.base.BaseFragmentAdapter;
import com.facebook.drawee.view.SimpleDraweeView;
import com.hjq.permissions.OnPermission;
import com.hjq.permissions.XXPermissions;
import com.imuxuan.floatingview.FloatingView;
import com.miaomi.fenbei.base.bean.BaseBean;
import com.miaomi.fenbei.base.bean.CurrentRoomBean;
import com.miaomi.fenbei.base.bean.UserInfo;
import com.miaomi.fenbei.base.core.BaseActivity;
import com.miaomi.fenbei.base.core.JoinChatCallBack;

import com.miaomi.fenbei.base.net.Callback;
import com.miaomi.fenbei.base.net.NetService;

import com.miaomi.fenbei.base.util.ImgUtil;
import com.miaomi.fenbei.base.util.RouterUrl;
import com.miaomi.fenbei.base.util.ToastUtil;
import com.miaomi.fenbei.base.widget.WordListSelectedView;
import com.miaomi.fenbei.gift.GiftManager;
import com.miaomi.fenbei.gift.OnTopNotifyClick;
import com.miaomi.fenbei.gift.widget.GiftAnimView;
import com.miaomi.fenbei.room.R;
import com.miaomi.fenbei.room.callback.CloseRoomDialogListener;
import com.miaomi.fenbei.room.ui.callback.RecordVolumeObservable;
import com.miaomi.fenbei.room.ui.dialog.InputDialog;
import com.miaomi.fenbei.room.ui.dialog.MsgListDialog;
import com.miaomi.fenbei.room.ui.dialog.ReportDialog;
import com.miaomi.fenbei.room.ui.dialog.RoomUserFragment;
import com.miaomi.fenbei.room.ui.dialog.SendSquaeMsgDialog;
import com.miaomi.fenbei.room.ui.dialog.ShareDialog;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Route(path = RouterUrl.partyRoom)
public class RoomActivity extends RoomBaseActivity {
    private ViewPager mContentViewPager;
    private SimpleDraweeView chatBg;
    private RoomCommonOprateCallback mRoomCommonOprateCallback;
    private GiftAnimView giftView;
    private ShareDialog shareDialog = new ShareDialog();
    private BaseFragmentAdapter baseFragmentAdapter;
    private List<Fragment> mFragmentList;
    private BaseRoomFragment baseRoomFragment;
    private InputDialog inputDialog = new InputDialog();
    private SendSquaeMsgDialog sendSquaeMsgDialog = new SendSquaeMsgDialog();
    private MsgListDialog msgListDialog = new MsgListDialog(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    });
    private RoomUserFragment roomUserFragment;
    private RoomGiftHistoryFragment roomGiftHistoryFragment;

    @Override
    public int getLayoutId() {
        return R.layout.room_activity_room;
    }

    @Override
    public void initView() {
        setBaseStatusBar(false, false);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        mContentViewPager = findViewById(R.id.view_pager);
        chatBg = findViewById(R.id.chat_bg);
        giftView = findViewById(R.id.gift_view);
        mRoomCommonOprateCallback = new RoomCommonOprateCallback() {
            @Override
            public void onFinish() {
                CloseRoomDialog dialog = new CloseRoomDialog(new CloseRoomDialogListener() {
                    @Override
                    public void onClose() {
                        finish();
                    }

                    @Override
                    public void onShare() {
                        shareDialog.show(getSupportFragmentManager());
                    }

                    @Override
                    public void onReport() {

                        ARouter.getInstance().build("/app/report")
                                .withString("USER_ID", ChatRoomManager.INSTANCE.getRoomId())
                                .withInt("REPORT_OBJECT", 0)
                                .navigation(getBaseContext());
                    }

                    @Override
                    public void onEnterRoom(String roomId) {
                        ChatRoomManager.INSTANCE.joinChat(RoomActivity.this, roomId, new JoinChatCallBack() {
                            @Override
                            public void onSuc() {
                                finish();
                            }

                            @Override
                            public void onFail(@NotNull String msg) {
                                ToastUtil.INSTANCE.error(RoomActivity.this, msg);
                            }
                        });
                    }
                });
                dialog.show(getSupportFragmentManager());
            }

            @Override
            public void onShowInputDialog(boolean isShow, int selectView) {
                if (isShow) {
                    if (selectView == WordListSelectedView.WSV_SELECTED_GC) {
                        sendSquaeMsgDialog.show(getSupportFragmentManager());
                    } else {
                        inputDialog.show(getSupportFragmentManager());
                    }
                } else {
                    inputDialog.dismiss();
                    sendSquaeMsgDialog.dismiss();
                }
            }

            @Override
            public void onShowMsgDialog() {
                msgListDialog.show(getSupportFragmentManager());
            }

            @Override
            public void onEnterRoom(UserInfo userInfo) {
                enterRoom(userInfo.getUser_id());
            }
        };
        GiftManager.getInstance().setGiftAnimView(giftView);
        giftView.setOnTopNotifyClick(new OnTopNotifyClick() {
            @Override
            public void onChange(String roomId) {
                if (roomId.equals(ChatRoomManager.INSTANCE.getRoomId())) {
                    ToastUtil.INSTANCE.suc(RoomActivity.this, "已在该房间");
                } else {
                    ChatRoomManager.INSTANCE.joinChat(RoomActivity.this, roomId, new JoinChatCallBack() {
                        @Override
                        public void onSuc() {
                            finish();
                        }

                        @Override
                        public void onFail(@NotNull String msg) {

                        }
                    });
                }
            }
        });
        setNeedFloatingView(false);
        roomUserFragment = new RoomUserFragment(new RoomUserFragment.OnItemClickListener() {
            @Override
            public void onItemClick(@NotNull UserInfo userInfo) {
                if (baseRoomFragment != null) {
                    mContentViewPager.setCurrentItem(1);
                    baseRoomFragment.showUserCard(userInfo);
                }
            }
        });
        roomGiftHistoryFragment = new RoomGiftHistoryFragment();
        mFragmentList = new ArrayList<>();
        mFragmentList.add(roomUserFragment);
        if (ChatRoomManager.INSTANCE.getMChatRoomInfo().getHost_info().getRoom_type() == ChatRoomManager.ROOM_TYPE_LABOR_UNION) {
            baseRoomFragment = new PartyRoomFragment();
        } else if (ChatRoomManager.INSTANCE.getMChatRoomInfo().getHost_info().getRoom_type() == ChatRoomManager.ROOM_TYPE_PERSONAL) {
            baseRoomFragment = new PartyRoomFragment();
        } else if (ChatRoomManager.INSTANCE.getMChatRoomInfo().getHost_info().getRoom_type() == ChatRoomManager.ROOM_TYPE_RADIO) {
            baseRoomFragment = new RadioRoomFragment();
        } else {
            baseRoomFragment = new BlindRoomFragment();
        }
        baseRoomFragment.setRoomCommonOprateCallback(mRoomCommonOprateCallback);
        mFragmentList.add(baseRoomFragment);
        mFragmentList.add(roomGiftHistoryFragment);
        baseFragmentAdapter = new BaseFragmentAdapter(getSupportFragmentManager(), mFragmentList);
        mContentViewPager.setOffscreenPageLimit(3);
        mContentViewPager.setAdapter(baseFragmentAdapter);
        mContentViewPager.setCurrentItem(1);
        requestRecordPermission();
        ImgUtil.INSTANCE.loadWebpGif(RoomActivity.this, ChatRoomManager.INSTANCE.getMChatRoomInfo().getHost_info().getBackdrop(), chatBg, R.drawable.common_bg_room_default);
        ChatRoomManager.INSTANCE.disPatchInRoom();
        FloatingView.get().remove();
        start();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        ChatRoomManager.INSTANCE.disPatchInRoom();
        if (baseRoomFragment != null) {
            baseRoomFragment.initPreStatus();
        }
        if (roomGiftHistoryFragment != null) {
            roomGiftHistoryFragment.loadData();
        }
        if (roomUserFragment != null) {
            roomUserFragment.loadData();
        }
//        mFragmentList.clear();
//        mFragmentList.add(new RoomUserFragment(new RoomUserFragment.OnItemClickListener() {
//            @Override
//            public void onItemClick(@NotNull UserInfo userInfo) {
//                if (baseRoomFragment != null){
//                    mContentViewPager.setCurrentItem(1);
//                    baseRoomFragment.showUserCard(userInfo);
//                }
//            }
//        }));
//        if (ChatRoomManager.INSTANCE.getMChatRoomInfo().getHost_info().getRoom_type() == ChatRoomManager.ROOM_TYPE_LABOR_UNION){
//            baseRoomFragment = new PartyRoomFragment();
//        }else if (ChatRoomManager.INSTANCE.getMChatRoomInfo().getHost_info().getRoom_type() == ChatRoomManager.ROOM_TYPE_PERSONAL){
//            baseRoomFragment = new PartyRoomFragment();
//        }else{
//            baseRoomFragment = new BlindRoomFragment();
//        }
//        baseRoomFragment.setRoomCommonOprateCallback(mRoomCommonOprateCallback);
//        mFragmentList.add(baseRoomFragment);
//        mFragmentList.add(new RoomGiftHistoryFragment());
//        baseFragmentAdapter.notifyDataSetChanged();
    }

    private void requestRecordPermission() {
        if (!XXPermissions.isHasPermission(RoomActivity.this, Manifest.permission.RECORD_AUDIO)) {
            XXPermissions.with(RoomActivity.this)
                    .permission(Manifest.permission.RECORD_AUDIO)
                    .request(new OnPermission() {
                        @Override
                        public void hasPermission(List<String> granted, boolean isAll) {

                        }

                        @Override
                        public void noPermission(List<String> denied, boolean quick) {

                        }
                    });
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ChatRoomManager.INSTANCE.disPatchMinWindow();
    }

    @Override
    public void finish() {
        super.finish();
        stop();
        GiftManager.getInstance().clearAnim();
        ChatRoomManager.INSTANCE.disPatchMinWindow();
        if (baseRoomFragment != null) {
            baseRoomFragment.onCloseRoom();
        }
    }


    private void enterRoom(int userId) {
        NetService.Companion.getInstance(RoomActivity.this).getCurrentRoom(userId, new Callback<CurrentRoomBean>() {
            @Override
            public void onSuccess(int nextPage, CurrentRoomBean bean, int code) {
                if (!TextUtils.isEmpty(bean.getRoom_id())) {
                    if (bean.getRoom_id().equals(ChatRoomManager.INSTANCE.getRoomId())) {
                        ToastUtil.INSTANCE.suc(RoomActivity.this, "已在该房间");
                    } else {
                        ChatRoomManager.INSTANCE.joinChat(RoomActivity.this, bean.getRoom_id(), new JoinChatCallBack() {
                            @Override
                            public void onSuc() {
                                finish();
                            }

                            @Override
                            public void onFail(@NotNull String msg) {

                            }
                        });
                    }
                } else {
                    ToastUtil.INSTANCE.error(RoomActivity.this, "当前不在房间");
                }
            }

            @Override
            public void onError(@NotNull String msg, @NotNull Throwable throwable, int code) {
                ToastUtil.INSTANCE.error(RoomActivity.this, msg);
            }

            @Override
            public boolean isAlive() {
                return isLive();
            }
        });
    }

}
