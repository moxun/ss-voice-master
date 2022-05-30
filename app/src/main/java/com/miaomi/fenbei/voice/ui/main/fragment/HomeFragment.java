package com.miaomi.fenbei.voice.ui.main.fragment;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.miaomi.fenbei.base.bean.BannerBean;
import com.miaomi.fenbei.base.bean.CurrentRoomBean;
import com.miaomi.fenbei.base.bean.MakeFriendBean;
import com.miaomi.fenbei.base.bean.RandomRoomBean;
import com.miaomi.fenbei.base.bean.RecommandUserBean;
import com.miaomi.fenbei.base.core.BaseFragment;

import com.miaomi.fenbei.base.core.JoinChatCallBack;
import com.miaomi.fenbei.base.net.Callback;
import com.miaomi.fenbei.base.net.NetService;
import com.miaomi.fenbei.base.util.ImgUtil;
import com.miaomi.fenbei.base.util.ToastUtil;
import com.miaomi.fenbei.base.web.WebActivity;
import com.miaomi.fenbei.base.widget.GridSpacingItemDecoration;
import com.miaomi.fenbei.base.widget.banner.Banner;
import com.miaomi.fenbei.room.ChatRoomManager;
import com.miaomi.fenbei.voice.R;
import com.miaomi.fenbei.voice.ui.main.MainActivity;
import com.miaomi.fenbei.voice.ui.main.adapter.HomeMsgAdapter;
import com.miaomi.fenbei.voice.ui.main.adapter.HomePartyAdapter;

import com.miaomi.fenbei.voice.ui.search.SearchActivity;
import com.miaomi.fenbei.voice.ui.square.MakingFriendsActivity;

import com.miaomi.fenbei.voice.ui.main.adapter.PlayingUserAdapter;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.scwang.smartrefresh.layout.util.DensityUtil;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HomeFragment extends BaseFragment {
    private SmartRefreshLayout smartRefreshLayout;
    private RecyclerView partyRv;
    private RecyclerView friendRv;
    private Banner banner;
    private List<BannerBean> partyBannerList = new ArrayList();
    private HomePartyAdapter homePartyAdapter;
    private PlayingUserAdapter playingUserAdapter;
    private String roomType = "0";
    private int mPage = 0;
    //    private LoadHelper loadHelper;
    private HomeMsgAdapter makingFriendsAdapter;
    private RecyclerView msgRv;
    private List<MakeFriendBean> mCurMsgList = new ArrayList<>();
    private ConstraintLayout topMsgCl;
    private ImageView topFaceIv;
    private TextView topContentTv;
    private TextView topNickNameTv;
    private MakeFriendBean topMakeFriendBean;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public void initView(@NotNull View view) {
        makingFriendsAdapter = new HomeMsgAdapter(getActivity());
        makingFriendsAdapter.setOnItemClickListner(new HomeMsgAdapter.OnItemClickListner() {
            @Override
            public void onItemClick(@NotNull MakeFriendBean item) {
                if (item != null) {
                    enterRoom(item.getUser_id());
                }
            }
        });
        banner = view.findViewById(R.id.banner);
        smartRefreshLayout = view.findViewById(R.id.refresh_layout);
        partyRv = view.findViewById(R.id.rv_party);
        friendRv = view.findViewById(R.id.rv_friend);
        msgRv = view.findViewById(R.id.rv_msg);
        topMsgCl = view.findViewById(R.id.cl_top_msg);
        topNickNameTv = view.findViewById(R.id.tv_top_name);
        topFaceIv = view.findViewById(R.id.iv_top_face);
        topContentTv = view.findViewById(R.id.tv_top_content);
        msgRv.setLayoutManager(new LinearLayoutManager(getContext()));
        msgRv.setAdapter(makingFriendsAdapter);
        msgRv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                isInTop = !recyclerView.canScrollVertically(-1);
            }
        });
        topMsgCl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (topMakeFriendBean != null) {
                    enterRoom(topMakeFriendBean.getUser_id());
                }
            }
        });
        view.findViewById(R.id.tv_more_room).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) Objects.requireNonNull(getActivity())).setCurrentItem(0);
            }
        });
        view.findViewById(R.id.tv_more_friend).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(MakingFriendsActivity.Companion.getIntent(getContext()));

            }
        });
        view.findViewById(R.id.li_seach).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchActivity.start(getContext());
            }
        });
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                getPartyBanner();
                getFirendList(getTYPE_REFRESH());
            }
        });
        smartRefreshLayout.setOnLoadMoreListener(refreshLayout -> getFirendList(getTYPE_LOADMROE()));
        partyRv.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        homePartyAdapter = new HomePartyAdapter(getContext());
        partyRv.setAdapter(homePartyAdapter);
        playingUserAdapter = new PlayingUserAdapter();
//        playingUserAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);
//        friendRv.addItemDecoration(new GridSpacingItemDecoration(2, DensityUtil.dp2px(9f), false));
        friendRv.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        friendRv.addItemDecoration(new GridSpacingItemDecoration(2, DensityUtil.dp2px(9f), false));
        friendRv.setAdapter(playingUserAdapter);
//        loadHelper = new LoadHelper();
//        loadHelper.registerLoad(friendRv);
        getPartyBanner();
//        getParty(roomType);
        getFirendList(getTYPE_REFRESH());
        banner.setOnBannerListener(position -> WebActivity.start(getContext(), position.getUrl(), position.getTitle()));
        banner.setOnBannerChangeLisetner(() -> itemMove());

    }

    private void getPartyBanner() {
        NetService.Companion.getInstance(getContext()).getBanner(getString(R.string.banner_type), new Callback<List<BannerBean>>() {
            @Override
            public void onSuccess(int nextPage, List<BannerBean> bean, int code) {
                if (!bean.isEmpty()) {
                    partyBannerList.clear();
                    partyBannerList.addAll(bean);
                    banner.setImages(partyBannerList)
                            .start();
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

    private void enterRoom(int userId) {
        NetService.Companion.getInstance(getContext()).getCurrentRoom(userId, new Callback<CurrentRoomBean>() {
            @Override
            public void onSuccess(int nextPage, CurrentRoomBean bean, int code) {
                if (!TextUtils.isEmpty(bean.getRoom_id())) {
                    ChatRoomManager.INSTANCE.joinChat(getContext(), bean.getRoom_id(), new JoinChatCallBack() {
                        @Override
                        public void onSuc() {

                        }

                        @Override
                        public void onFail(@NotNull String msg) {
                            ToastUtil.INSTANCE.error(getContext(), msg);
                        }
                    });
                } else {
                    ToastUtil.INSTANCE.error(getContext(), "当前不在房间");
                }
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

    private void getFirendList(int type) {
        NetService.Companion.getInstance(getContext()).getRecommandUsers(mPage, new Callback<RecommandUserBean>() {
            @Override
            public void onSuccess(int nextPage, RecommandUserBean bean, int code) {
                if (type == getTYPE_REFRESH()) {
//
                    homePartyAdapter.setData(bean.getThree_room());
                    smartRefreshLayout.finishRefresh();
                    playingUserAdapter.setNewData(bean.getUsers());
                } else {
                    smartRefreshLayout.finishLoadMore();
                    playingUserAdapter.addData(bean.getUsers());
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

        NetService.Companion.getInstance(getContext()).getSquareHeadInfo(new Callback<MakeFriendBean>() {
            @Override
            public void onSuccess(int nextPage, MakeFriendBean bean, int code) {

                if (bean != null && !TextUtils.isEmpty(bean.getFace())) {
                    topMakeFriendBean = bean;
                    topMsgCl.setVisibility(View.VISIBLE);
                    ImgUtil.INSTANCE.loadFaceIcon(getContext(), bean.getFace(), topFaceIv);
                    topContentTv.setText(bean.getContent());
                    topNickNameTv.setText(bean.getNickname());
                } else {
                    topMsgCl.setVisibility(View.GONE);
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


        NetService.Companion.getInstance(getContext()).getMakeFriengMsgList(new Callback<List<MakeFriendBean>>() {
            @Override
            public void onSuccess(int nextPage, List<MakeFriendBean> bean, int code) {
                if (bean.size() >= 3) {
                    mCurMsgList.clear();
                    mCurMsgList.addAll(bean);
                    makingFriendsAdapter.setRealData(mCurMsgList.subList(0, 3));
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

    @Override
    public void onResume() {
        super.onResume();
        isHidden = false;
        banner.startAutoPlay();
    }

    @Override
    public void onPause() {
        super.onPause();
        isHidden = true;
        banner.stopAutoPlay();
    }

    private boolean isInTop = true;
    private boolean isHidden = true;

    private void itemMove() {
        if (!isInTop) {
            return;
        }
        if (isHidden) {
            return;
        }
        if (smartRefreshLayout.isRefreshing()) {
            return;
        }
        if (mCurMsgList.size() <= 2) {
            return;
        }
        int rvFromPosition = 0;
        int rvToPosition = 2;
        makingFriendsAdapter.getData().remove(rvFromPosition);
        makingFriendsAdapter.getData().add(rvToPosition, mCurMsgList.get(0));
        makingFriendsAdapter.notifyItemMoved(rvFromPosition, rvToPosition);
        makingFriendsAdapter.notifyItemRangeChanged(getRvToPosition(rvFromPosition, rvToPosition), Math.abs(rvFromPosition - rvToPosition) + 1);
        mCurMsgList.add(mCurMsgList.get(0));
        mCurMsgList.remove(0);
    }


}
