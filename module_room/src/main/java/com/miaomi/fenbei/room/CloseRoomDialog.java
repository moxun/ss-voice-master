package com.miaomi.fenbei.room;

import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.miaomi.fenbei.base.bean.HomepageBean;
import com.miaomi.fenbei.base.bean.RandomRoomBean;
import com.miaomi.fenbei.base.core.dialog.BaseDialogFragment;
import com.miaomi.fenbei.base.net.Callback;
import com.miaomi.fenbei.base.net.NetService;
import com.miaomi.fenbei.base.util.ToastUtil;
import com.miaomi.fenbei.room.R;
import com.miaomi.fenbei.room.callback.CloseRoomDialogListener;
import com.miaomi.fenbei.room.ui.adapter.RandomRoomAdapter;
import com.miaomi.fenbei.room.ui.adapter.UserOnlineAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CloseRoomDialog extends BaseDialogFragment {

    private CloseRoomDialogListener closeRoomDialogListener;
    private UserOnlineAdapter homepageAdapter;
    private RandomRoomAdapter randomRoomAdapter;
    private RecyclerView userOnlineRv;
    private RecyclerView randomRoomRv;

    public CloseRoomDialog(CloseRoomDialogListener closeRoomDialogListener) {
        this.closeRoomDialogListener = closeRoomDialogListener;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.room_dialog_close;
    }

    @Override
    public void bindView(View v) {
        randomRoomRv = v.findViewById(R.id.rv_random_room);
        randomRoomAdapter = new RandomRoomAdapter(getContext());
        homepageAdapter = new UserOnlineAdapter(getContext());
        userOnlineRv = v.findViewById(R.id.rv_user_online);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        userOnlineRv.setLayoutManager(linearLayoutManager);
        randomRoomRv.setLayoutManager(new LinearLayoutManager(getContext()));
        randomRoomRv.setAdapter(randomRoomAdapter);
        v.findViewById(R.id.tv_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeRoomDialogListener.onClose();
            }
        });
        v.findViewById(R.id.tv_share).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeRoomDialogListener.onShare();
            }
        });
        v.findViewById(R.id.tv_report).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeRoomDialogListener.onReport();
            }
        });
        v.findViewById(R.id.fl_content).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        v.findViewById(R.id.ll_content).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        randomRoomAdapter.setmCheckListener(new CloseRoomDialogListener() {
            @Override
            public void onClose() {

            }

            @Override
            public void onShare() {

            }

            @Override
            public void onReport() {

            }

            @Override
            public void onEnterRoom(String roomId) {
                closeRoomDialogListener.onEnterRoom(roomId);
            }
        });
        homepageAdapter.setmCheckListener(new CloseRoomDialogListener() {
            @Override
            public void onClose() {

            }

            @Override
            public void onShare() {

            }

            @Override
            public void onReport() {

            }

            @Override
            public void onEnterRoom(String roomId) {
                closeRoomDialogListener.onEnterRoom(roomId);
            }
        });
        userOnlineRv.setAdapter(homepageAdapter);
        getData();
        getRandomRoomBean();
    }

    private void getData(){
        NetService.Companion.getInstance(getContext()).getHomepage(new Callback<List<HomepageBean>>() {
            @Override
            public void onSuccess(int nextPage, List<HomepageBean> bean, int code) {
                homepageAdapter.setData(bean);
            }

            @Override
            public void onError(@NotNull String msg, @NotNull Throwable throwable, int code) {
                ToastUtil.INSTANCE.error(getContext(), msg);
            }

            @Override
            public boolean isAlive() {
                return isLive;
            }
        });
    }

    private void getRandomRoomBean(){
        NetService.Companion.getInstance(getContext()).getRandomRoomBean(new Callback<List<RandomRoomBean>>() {
            @Override
            public void onSuccess(int nextPage, List<RandomRoomBean> bean, int code) {
                randomRoomAdapter.setData(bean);
            }

            @Override
            public void onError(@NotNull String msg, @NotNull Throwable throwable, int code) {
                ToastUtil.INSTANCE.error(getContext(), msg);
            }

            @Override
            public boolean isAlive() {
                return isLive;
            }
        });
    }
}
