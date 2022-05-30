//package com.miaomi.fenbei.voice.ui.main.fragment.home;
//
//import android.os.Bundle;
//import android.view.View;
//
//import com.miaomi.fenbei.base.bean.ChatListBean;
//import com.miaomi.fenbei.base.core.BaseLazyFragment;
//import com.miaomi.fenbei.voice.Callback;
//import com.miaomi.fenbei.voice.NetService;
//import com.miaomi.fenbei.voice.LoadHelper;
//import com.miaomi.fenbei.base.widget.xrecyclerview.XRecyclerView;
//import com.miaomi.fenbei.voice.R;
//import com.miaomi.fenbei.voice.ui.main.adapter.ChatFollowListAdapter;
//import com.miaomi.fenbei.voice.ui.main.adapter.HomeRoomAdapter;
//
//import org.jetbrains.annotations.NotNull;
//
//import java.util.List;
//
//import androidx.recyclerview.widget.LinearLayoutManager;
//
//public class HomeOtherRoomFragment extends BaseLazyFragment implements XRecyclerView.LoadingListener {
//
//    private XRecyclerView roomRv;
//    private HomeRoomAdapter adapter;
//    private LoadHelper loadHelper;
//    private final static String ROOM_TYPE = "ROOM_TYPE";
//    private int roomType;
//
//    public static HomeOtherRoomFragment newInstance(int type) {
//        HomeOtherRoomFragment fragment = new HomeOtherRoomFragment();
//        Bundle args = new Bundle();
//        args.putInt(ROOM_TYPE, type);
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    @Override
//    public int getLayoutId() {
//        return R.layout.fragment_recyclerview;
//    }
//
//    @Override
//    public void initView(@NotNull View view) {
//        roomType = getArguments().getInt(ROOM_TYPE, 0);
//        roomRv = view.findViewById(R.id.x_reclclerview);
//        roomRv.setLoadingListener(this);
//        roomRv.setLoadingMoreEnabled(false);
//        roomRv.setPullRefreshEnabled(true);
//        adapter = new HomeRoomAdapter(getActivity());
//        roomRv.setLayoutManager(new LinearLayoutManager(getActivity()));
//        roomRv.setAdapter(adapter);
//        loadHelper = new LoadHelper();
//        loadHelper.registerLoad(roomRv);
//    }
//
//    private void getData() {
//        NetService.Companion.getInstance(getActivity()).getRoomListByLabel(roomType, new Callback<List<ChatListBean>>() {
//            @Override
//            public void onSuccess(int nextPage, List<ChatListBean> bean, int code) {
//                roomRv.refreshComplete();
//                if (bean.size() == 0) {
//                    loadHelper.setRoomEmptyCallback(v -> getData());
//                } else {
//                    loadHelper.bindView(code);
//                }
//                adapter.setData(bean);
//            }
//
//            @Override
//            public void onError(@NotNull String msg, @NotNull Throwable throwable, int code) {
//                roomRv.refreshComplete();
//                loadHelper.setErrorCallback(code, v -> getData());
//            }
//
//            @Override
//            public boolean isAlive() {
//                return isLive();
//            }
//        });
//    }
//
//    @Override
//    public void onRefresh() {
//        getData();
//    }
//
//    @Override
//    public void onLoadMore() {
//
//    }
//
//    @Override
//    public void loadData() {
//        getData();
//    }
//}