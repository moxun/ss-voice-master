package com.miaomi.fenbei.voice.ui.main.fragment;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.miaomi.fenbei.base.bean.BannerBean;
import com.miaomi.fenbei.base.bean.ChatListBean;


import com.miaomi.fenbei.base.bean.RecommandUserBean;
import com.miaomi.fenbei.base.core.BaseFragment;

import com.miaomi.fenbei.base.core.JoinChatCallBack;
import com.miaomi.fenbei.base.net.Callback;
import com.miaomi.fenbei.base.net.NetService;

import com.miaomi.fenbei.base.util.ImgUtil;


import com.miaomi.fenbei.base.util.ToastUtil;
import com.miaomi.fenbei.base.web.WebActivity;
import com.miaomi.fenbei.base.widget.AllRoomLableView;


import com.miaomi.fenbei.base.widget.banner.Banner;
import com.miaomi.fenbei.room.ChatRoomManager;
import com.miaomi.fenbei.voice.R;

import com.miaomi.fenbei.voice.ui.main.adapter.HomeRecommendedAdapter;

import com.miaomi.fenbei.voice.ui.main.adapter.RoomListAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import org.jetbrains.annotations.NotNull;


import java.util.ArrayList;
import java.util.List;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class PartyRoomListFragment extends BaseFragment {

    private SmartRefreshLayout smartRefreshLayout;
    private RoomListAdapter adapter1;
    private RoomListAdapter adapter2;
    private HomeRecommendedAdapter adapter3;
    private Banner banner;
    private ImageView iv_room_1;
    private ImageView iv_hot_room;
    private ConstraintLayout cl_hot_layout;
    private TextView tv_hot_value;
    private TextView chat_name;
    private AllRoomLableView tv_label;
    private int mPage = 0;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_party;
    }


    public static PartyRoomListFragment newInstance() {
        return new PartyRoomListFragment();
    }

    @Override
    public void initView(@NotNull View view) {
        banner = view.findViewById(R.id.banner);
        iv_room_1 = view.findViewById(R.id.iv_room_1);
        tv_hot_value = view.findViewById(R.id.tv_hot_value);
        chat_name = view.findViewById(R.id.chat_name);
        tv_label = view.findViewById(R.id.tv_label);
        cl_hot_layout = view.findViewById(R.id.cl_hot_layout);
        iv_hot_room = view.findViewById(R.id.iv_hot_room);
        RecyclerView rv1 = view.findViewById(R.id.rv_hot_room_list_1);
        RecyclerView rv2 = view.findViewById(R.id.rv_hot_room_list_2);
        RecyclerView rv3 = view.findViewById(R.id.rv_recommended_room_list);
        smartRefreshLayout = view.findViewById(R.id.refresh_layout);

        adapter1 = new RoomListAdapter(getActivity());
        rv1.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        rv1.setAdapter(adapter1);
        adapter2 = new RoomListAdapter(getActivity());
        rv2.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        rv2.setAdapter(adapter2);

        adapter3 = new HomeRecommendedAdapter(getActivity());
        rv3.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv3.setAdapter(adapter3);

        smartRefreshLayout.setOnRefreshListener(refreshLayout -> {
            getHostList();
            getRecommand(getTYPE_REFRESH());
            getPartyBanner();
        });
//        smartRefreshLayout.setOnLoadMoreListener(refreshLayout -> getRecommand(getTYPE_LOADMROE()));
        getHostList();
        getRecommand(getTYPE_REFRESH());
        getPartyBanner();
        banner.setOnBannerListener(position -> WebActivity.start(getContext(), position.getUrl(), position.getTitle()));
        iv_room_1.setOnClickListener((v) -> {
            if (bean != null && !TextUtils.isEmpty(bean.getChat_room_id()))
                ChatRoomManager.INSTANCE.joinChat(getContext(), bean.getChat_room_id(), new JoinChatCallBack() {

                    @Override
                    public void onFail(@NotNull String msg) {
                        ToastUtil.INSTANCE.error(getContext(), msg);
                    }

                    @Override
                    public void onSuc() {

                    }
                });
        });
    }

    private ChatListBean bean;

    private void getHostList() {
        NetService.Companion.getInstance(getContext()).getHotChats(mPage, new Callback<ArrayList<ChatListBean>>() {
            @Override
            public void onSuccess(int nextPage, ArrayList<ChatListBean> listBeans, int code) {
                if (listBeans.size() > 0) {
                    iv_hot_room.setVisibility(View.VISIBLE);
                    cl_hot_layout.setVisibility(View.VISIBLE);
                    bean = listBeans.get(0);
                    ImgUtil.INSTANCE.loadRoundImg(getContext(), bean.getChat_room_icon(), iv_room_1, 8f, R.drawable.common_default);
                    chat_name.setText(bean.getChat_room_name());
                    tv_hot_value.setText("热度:" + bean.getHot_value());
                    tv_label.setLevel(bean.getLabel());

                    listBeans.remove(0);
                    if (listBeans.size() > 0) {
                        List<ChatListBean> listBeans1;
                        List<ChatListBean> listBeans2 = new ArrayList<>();
                        if (listBeans.size() < 4) {
                            listBeans1 = listBeans.subList(0, listBeans.size());
                        } else if (listBeans.size() == 4) {
                            listBeans1 = listBeans.subList(0, 4);
                        } else {
                            listBeans1 = listBeans.subList(0, 4);
                            listBeans2 = listBeans.subList(4, listBeans.size());
                        }
                        adapter1.setData(listBeans1);
                        if (listBeans2.size() >= 8) {
                            listBeans2 = listBeans2.subList(0, 8);
                        }
                        adapter2.setData(listBeans2);
                    }
                }
                smartRefreshLayout.finishRefresh();
            }

            @Override
            public void noMore() {
                super.noMore();
            }

            @Override
            public void onError(@NotNull String msg, @NotNull Throwable throwable, int code) {
                smartRefreshLayout.finishRefresh(false);
            }

            @Override
            public boolean isAlive() {
                return isLive();
            }
        });
    }

    private void getRecommand(int type) {
        NetService.Companion.getInstance(getContext()).getRecommandUsers(mPage, new Callback<RecommandUserBean>() {
            @Override
            public void onSuccess(int nextPage, RecommandUserBean bean, int code) {
                if (type == getTYPE_REFRESH()) {
                    adapter3.setData(bean.getThree_room());
                    smartRefreshLayout.finishRefresh();
                } else {
                    smartRefreshLayout.finishLoadMore();
                }
                if (bean.getOffset() == 0) {
                    noMore();
                }
                mPage = bean.getOffset();
            }

            @Override
            public void noMore() {
                super.noMore();
                smartRefreshLayout.setNoMoreData(true);
            }

            @Override
            public void onError(@NotNull String msg, @NotNull Throwable throwable, int code) {
                if (type == getTYPE_REFRESH()) {
                    smartRefreshLayout.finishRefresh(false);
//                    loadHelper.setErrorCallback(code, v -> getData(getTYPE_REFRESH()));
                } else {
                    smartRefreshLayout.finishLoadMore(false);
                    ToastUtil.INSTANCE.error(getContext(), msg);
                }
            }

            @Override
            public boolean isAlive() {
                return isLive();
            }
        });
    }

    private final List<BannerBean> partyBannerList = new ArrayList<>();

    private void getPartyBanner() {
        NetService.Companion.getInstance(getContext()).getBanner(getString(R.string.banner_type), new Callback<List<BannerBean>>() {
            @Override
            public void onSuccess(int nextPage, List<BannerBean> bean, int code) {
                if (!bean.isEmpty()) {
                    partyBannerList.clear();
                    partyBannerList.addAll(bean);
                    banner.setImages(partyBannerList).start();
                }
            }

            @Override
            public void onError(@NotNull String msg, @NotNull Throwable throwable, int code) {

            }

            @Override
            public boolean isAlive() {
                return isLive();
            }
        });
    }
//    private void getData(String roomlabelType) {
//        NetService.Companion.getInstance(getActivity()).getRoomListByLabel(roomlabelType, new Callback<List<ChatListBean>>() {
//            @Override
//            public void onSuccess(int nextPage, List<ChatListBean> bean, int code) {
////                roomRv.refreshComplete();
//                smartRefreshLayout.finishRefresh();
//                if (bean.size() == 0) {
//                    loadHelper.setEmptyCallback(R.drawable.common_empty_bg, "空空如也");
//                } else {
//                    loadHelper.bindView(code);
//                }
////                adapter.setData(bean);
//            }
//
//            @Override
//            public void onError(@NotNull String msg, @NotNull Throwable throwable, int code) {
//                smartRefreshLayout.finishRefresh();
//                loadHelper.setErrorCallback(code, v -> getData(roomlabelType));
//            }
//
//            @Override
//            public boolean isAlive() {
//                return isLive();
//            }
//        });
//    }

    @Override
    public void onResume() {
        super.onResume();
        banner.startAutoPlay();
    }

    @Override
    public void onPause() {
        super.onPause();
        banner.stopAutoPlay();
    }
}
