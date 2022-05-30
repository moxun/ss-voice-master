package com.miaomi.fenbei.voice.ui.main.fragment;

import android.view.View;


import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.miaomi.fenbei.base.bean.ChatListBean;

import com.miaomi.fenbei.base.bean.RoomlabelBean;
import com.miaomi.fenbei.base.core.BaseFragment;
import com.miaomi.fenbei.base.core.JoinChatCallBack;
import com.miaomi.fenbei.base.net.Callback;
import com.miaomi.fenbei.base.net.NetService;
import com.miaomi.fenbei.base.util.LoadHelper;
import com.miaomi.fenbei.base.util.ToastUtil;

import com.miaomi.fenbei.room.ChatRoomManager;
import com.miaomi.fenbei.voice.R;

import com.miaomi.fenbei.voice.ui.main.adapter.VoiceCircleRoomListAdapter;
import com.miaomi.fenbei.voice.ui.main.adapter.VoiceCircleTabAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;


import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class VoiceCircleFragment extends BaseFragment implements VoiceCircleTabAdapter.CheckItemListener {


    private SmartRefreshLayout smartRefreshLayout;

    private RecyclerView voicecircletabRv;
    private RecyclerView voicecircleHotRv;
    private VoiceCircleTabAdapter circleTabAdapter;
    private VoiceCircleRoomListAdapter adapter;

    private RoomlabelBean roomlabelBean = new RoomlabelBean();
    private List<RoomlabelBean> list;
    private LoadHelper loadHelper;
    private String roomType = "";

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_voice_circle;
    }


    public static VoiceCircleFragment newInstance() {
        return new VoiceCircleFragment();
    }

    @Override
    public void initView(@NotNull View view) {
        voicecircletabRv = view.findViewById(R.id.rv_voice_circle_tab);
        voicecircleHotRv = view.findViewById(R.id.rv_voice_circle_hot);
        smartRefreshLayout = view.findViewById(R.id.refresh_layout);
        list = new ArrayList<>();
        roomlabelBean.setName("");
        list.add(roomlabelBean);
        circleTabAdapter = new VoiceCircleTabAdapter(getContext(), this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        voicecircletabRv.setLayoutManager(linearLayoutManager);
        voicecircletabRv.setAdapter(circleTabAdapter);
        adapter = new VoiceCircleRoomListAdapter(getActivity());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        voicecircleHotRv.setLayoutManager(layoutManager);
        voicecircleHotRv.setAdapter(adapter);
        loadHelper = new LoadHelper();
        loadHelper.registerLoad(voicecircleHotRv);
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {

                getData(roomType);
            }
        });

        getRoomlabel();
        getData(roomType);
    }

    private void getRoomlabel() {

        NetService.Companion.getInstance(getContext()).getRoomLabel(ChatRoomManager.ROOM_TYPE_PERSONAL, new Callback<List<RoomlabelBean>>() {
            @Override
            public void onSuccess(int nextPage, List<RoomlabelBean> bean, int code) {
                smartRefreshLayout.finishRefresh();
                list.addAll(bean);
                circleTabAdapter.setData(list);
            }

            @Override
            public void onError(@NotNull String msg, @NotNull Throwable throwable, int code) {
                smartRefreshLayout.finishRefresh();
            }

            @Override
            public boolean isAlive() {
                return isLive();
            }
        });
    }


//    private void getRankTopThree(){
//        NetService.Companion.getInstance(getContext()).getRankTopThree(new Callback<PartyRankTopThree>() {
//            @Override
//            public void onSuccess(int nextPage, PartyRankTopThree bean, int code) {
//                intRankTopThree(bean.getMl(),mlKMTopThree);
//                intRankTopThree(bean.getGx(),cfKMTopThree);
//            }
//
//            @Override
//            public void onError(@NotNull String msg, @NotNull Throwable throwable, int code) {
//
//            }
//
//            @Override
//            public boolean isAlive() {
//                return isLive();
//            }
//        });
//    }

    private void getData(String roomlabelType) {
        NetService.Companion.getInstance(getActivity()).getPersionalRoomListByLabel(roomlabelType, new Callback<List<ChatListBean>>() {
            @Override
            public void onSuccess(int nextPage, List<ChatListBean> bean, int code) {
//                roomRv.refreshComplete();
                smartRefreshLayout.finishRefresh();
                if (bean.size() == 0) {
                    loadHelper.setEmptyCallback(R.drawable.common_empty_bg, "空空如也");
                } else {
                    loadHelper.bindView(code);
                }
                adapter.setData(bean);
            }

            @Override
            public void onError(@NotNull String msg, @NotNull Throwable throwable, int code) {
//                roomRv.refreshComplete();
                smartRefreshLayout.finishRefresh();
                loadHelper.setErrorCallback(code, v -> getData(roomlabelType));
            }

            @Override
            public boolean isAlive() {
                return isLive();
            }
        });
    }


    private void getRecommandRoom() {

        NetService.Companion.getInstance(getContext()).getRecommandRoom(new Callback<String>() {
            @Override
            public void onSuccess(int nextPage, String roomId, int code) {
                ChatRoomManager.INSTANCE.joinChat(getContext(), roomId, new JoinChatCallBack() {
                    @Override
                    public void onSuc() {

                    }

                    @Override
                    public void onFail(@NotNull String msg) {

                    }
                });
            }

            @Override
            public void onError(@NotNull String msg, @NotNull Throwable throwable, int code) {
                ToastUtil.INSTANCE.error(getContext(), msg);
            }

            @Override
            public boolean isAlive() {
                return isLive();
            }
        });
    }

    @Override
    public void itemChecked(String id) {
        getData(id);
    }
}
