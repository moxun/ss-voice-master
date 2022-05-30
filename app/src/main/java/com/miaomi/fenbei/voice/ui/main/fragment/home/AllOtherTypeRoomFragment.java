package com.miaomi.fenbei.voice.ui.main.fragment.home;

import android.os.Bundle;
import android.view.View;

import com.miaomi.fenbei.base.bean.ChatListBean;
import com.miaomi.fenbei.base.core.BaseLazyFragment;
import com.miaomi.fenbei.base.net.Callback;
import com.miaomi.fenbei.base.net.NetService;
import com.miaomi.fenbei.base.util.LoadHelper;
import com.miaomi.fenbei.base.widget.GridSpacingItemDecoration;
import com.miaomi.fenbei.base.widget.xrecyclerview.XRecyclerView;
import com.miaomi.fenbei.voice.R;
import com.miaomi.fenbei.voice.ui.main.adapter.PartyRoomListAdapter;
import com.scwang.smartrefresh.layout.util.DensityUtil;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class AllOtherTypeRoomFragment extends BaseLazyFragment implements XRecyclerView.LoadingListener {

    private RecyclerView roomRv;
    private PartyRoomListAdapter adapter;
    private LoadHelper loadHelper;
    private final static String ROOM_TYPE = "ROOM_TYPE";
    private int roomType;

    public static AllOtherTypeRoomFragment newInstance(int type) {
        AllOtherTypeRoomFragment fragment = new AllOtherTypeRoomFragment();
        Bundle args = new Bundle();
        args.putInt(ROOM_TYPE, type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.room_fragment_all_room;
    }

    @Override
    public void initView(@NotNull View view) {
        roomType = getArguments().getInt(ROOM_TYPE, 0);
        roomRv = view.findViewById(R.id.hot_rv);
//        roomRv.setLoadingListener(this);
//        roomRv.setLoadingMoreEnabled(false);
//        roomRv.setPullRefreshEnabled(true);
        adapter = new PartyRoomListAdapter(getActivity());
        roomRv.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        roomRv.addItemDecoration(new GridSpacingItemDecoration(2, DensityUtil.dp2px(9f),false));

        roomRv.setAdapter(adapter);
        loadHelper = new LoadHelper();
        loadHelper.registerLoad(roomRv);
    }

    private void getData() {
        NetService.Companion.getInstance(getActivity()).getRoomListByLabel(""+roomType, new Callback<List<ChatListBean>>() {
            @Override
            public void onSuccess(int nextPage, List<ChatListBean> bean, int code) {
//                roomRv.refreshComplete();
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
                loadHelper.setErrorCallback(code, v -> getData());
            }

            @Override
            public boolean isAlive() {
                return isLive();
            }
        });
    }

    @Override
    public void onRefresh() {
        getData();
    }

    @Override
    public void onLoadMore() {

    }

    @Override
    public void loadData() {
        getData();
    }
}
