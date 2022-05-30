package com.miaomi.fenbei.room;

import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.miaomi.fenbei.base.bean.RoomGiftHistoryBean;
import com.miaomi.fenbei.base.core.BaseLazyFragment;
import com.miaomi.fenbei.base.net.Callback;
import com.miaomi.fenbei.base.net.NetService;
import com.miaomi.fenbei.base.util.ToastUtil;
import com.miaomi.fenbei.base.widget.xrecyclerview.XRecyclerView;
import com.miaomi.fenbei.room.R;
import com.miaomi.fenbei.room.ui.adapter.RoomGiftHistoryAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class RoomGiftHistoryFragment extends BaseLazyFragment {

//    private LoadHelper loadHelper;
    private int mPage = 1;
    private RoomGiftHistoryAdapter adapter;
    private XRecyclerView applyList;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_room_gift_history;
    }

    @Override
    public void initView(@NotNull View view) {
        applyList = view.findViewById(R.id.gift_rv);
        applyList.setLayoutManager(new LinearLayoutManager(getMContext()));
        applyList.setPullRefreshEnabled(true);
        applyList.setLoadingMoreEnabled(true);
        applyList.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                getData(getTYPE_REFRESH());
            }

            @Override
            public void onLoadMore() {
                getData(getTYPE_LOADMROE());
            }
        });
        adapter = new RoomGiftHistoryAdapter(getActivity());
        applyList.setAdapter(adapter);
//        loadHelper = new LoadHelper();
//        loadHelper.registerLoad(applyList);
    }

    private void getData(final int type) {
        if (type == getTYPE_REFRESH()){
            mPage = 1;
        }
        NetService.Companion.getInstance(getContext()).getRoomGiftHistory(mPage, ChatRoomManager.INSTANCE.getRoomId(), new Callback<List<RoomGiftHistoryBean>>() {
            @Override
            public void onSuccess(int nextPage, List<RoomGiftHistoryBean> list, int code) {
                applyList.refreshComplete();
//                if (list.isEmpty()) {
//                    loadHelper.setEmptyCallback(0,"暂无送礼记录~");
//                } else {
//                    loadHelper.bindView(Data.CODE_SUC);
//                }
                if (type == getTYPE_REFRESH()){
                    adapter.setData(list);
                }else{
                    adapter.addData(list);
                }
                mPage++;
            }

            @Override
            public void onError(@NotNull String msg, @NotNull Throwable throwable, int code) {
                applyList.refreshComplete();
//                loadHelper.setErrorCallback(code, new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        getData(getTYPE_REFRESH());
//                    }
//                });
                ToastUtil.INSTANCE.i(getMContext(),msg);
            }

            @Override
            public boolean isAlive() {
                return isLive();
            }
        });
    }

    @Override
    public void loadData() {
        getData(getTYPE_REFRESH());
    }
}
