package com.miaomi.fenbei.voice.ui.main.fragment.home;

import android.os.Bundle;
import android.view.View;

import com.miaomi.fenbei.base.bean.ChatListBean;
import com.miaomi.fenbei.base.core.BaseLazyFragment;
import com.miaomi.fenbei.base.net.Callback;
import com.miaomi.fenbei.base.net.NetService;
import com.miaomi.fenbei.base.util.LoadHelper;
import com.miaomi.fenbei.voice.R;
import com.miaomi.fenbei.voice.ui.main.adapter.HomeHotRoomAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class RecommandOtherTypeRoomFragment extends BaseLazyFragment{
    private RecyclerView roomRv;
    private HomeHotRoomAdapter adapter;
    private final static String ROOM_TYPE = "ROOM_TYPE";
    public final static int ROOM_TYPE_XNNY_FEMALE = 7;
    public final static int ROOM_TYPE_XNNY_MAN = 8;
    public final static int ROOM_TYPE_QG = 9;
    public final static int ROOM_TYPE_JY = 10;
    private int roomType;
    private LoadHelper loadHelper;

    public static RecommandOtherTypeRoomFragment newInstance(int type){
        RecommandOtherTypeRoomFragment fragment = new RecommandOtherTypeRoomFragment();
        Bundle args = new Bundle();
        args.putInt(ROOM_TYPE,type);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public int getLayoutId() {
        return R.layout.chatting_fragment_hot;
    }


    @Override
    public void initView(@NotNull View view) {
        roomType = getArguments().getInt(ROOM_TYPE,0);
        roomRv = view.findViewById(R.id.hot_rv);
        adapter = new HomeHotRoomAdapter(getActivity());
        roomRv.setLayoutManager(new GridLayoutManager(getActivity(),3));
        roomRv.setAdapter(adapter);
        loadHelper = new LoadHelper();
        loadHelper.registerLoad(roomRv);
    }




    public void getData(){
        NetService.Companion.getInstance(getActivity()).getRoomListByLabel(""+roomType, new Callback<List<ChatListBean>>() {
            @Override
            public void onSuccess(int nextPage, List<ChatListBean> bean, int code) {
                if (bean.size() == 0){
                    loadHelper.setRoomEmptyCallback(v -> getData());
                }else{
                    loadHelper.bindView(code);
                }
                adapter.setData(bean);
            }

            @Override
            public void onError(@NotNull String msg, @NotNull Throwable throwable, int code) {
                loadHelper.setErrorCallback(code, v -> getData());
            }

            @Override
            public boolean isAlive() {
                return isLive();
            }
        });
    }


    @Override
    public void loadData() {
        getData();
    }
}
