package com.miaomi.fenbei.voice.ui.main.fragment;

import android.view.View;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.indicatorlib.base.BaseFragmentAdapter;
import com.miaomi.fenbei.base.bean.BannerBean;
import com.miaomi.fenbei.base.bean.RankBean;
import com.miaomi.fenbei.base.bean.RoomlabelBean;
import com.miaomi.fenbei.base.core.BaseFragment;
import com.miaomi.fenbei.base.core.JoinChatCallBack;
import com.miaomi.fenbei.base.net.Callback;
import com.miaomi.fenbei.base.net.NetService;
import com.miaomi.fenbei.base.util.ToastUtil;
import com.miaomi.fenbei.base.web.WebActivity;
import com.miaomi.fenbei.base.widget.KMHomeRankTopThreeView;
import com.miaomi.fenbei.base.widget.banner.Banner;
import com.miaomi.fenbei.room.ChatRoomManager;
import com.miaomi.fenbei.voice.R;
import com.miaomi.fenbei.voice.ui.main.fragment.home.AllHotRoomFragment;
import com.miaomi.fenbei.voice.ui.main.fragment.home.AllOtherTypeRoomFragment;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class HomeRoomFragment extends BaseFragment {

    private List<BannerBean> partyBannerList = new ArrayList();
    //    private PersonRoomAdapter adapter;
//    private List<Fragment> mFragmentList = new ArrayList();
//    private View headView;
//    private int mPage = 1;
//    private AllHotRoomFragment hotFragment;
//    private List<AllOtherTypeRoomFragment> allOtherTypeRoomFragments = new ArrayList<>();
//    private AllOtherTypeRoomFragment bigTypeRoomFragment1;
//    private AllOtherTypeRoomFragment bigTypeRoomFragment2;
//    private AllOtherTypeRoomFragment bigTypeRoomFragment3;
//    private AllOtherTypeRoomFragment bigTypeRoomFragment4;
    private List<Fragment> mFragmentList = new ArrayList();
    //    private boolean isInTop = true;
    private ViewPager viewPager;
    private Banner banner;
    private LinearLayout li_seach;

    private SmartRefreshLayout smartRefreshLayout;
//    private KMHomeRankTopThreeView mlKMTopThree;
//    private KMHomeRankTopThreeView cfKMTopThree;
    private BaseFragmentAdapter baseFragmentAdapter;
//    private boolean isHidden = true;


    private void refreshHotRoom() {
        if (viewPager.getCurrentItem() == 0) {
            ((AllHotRoomFragment)mFragmentList.get(viewPager.getCurrentItem())).loadData(getTYPE_REFRESH());
        }else{
            ((AllOtherTypeRoomFragment)mFragmentList.get(viewPager.getCurrentItem())).loadData();
        }
//        if (viewPager.getCurrentItem() == 1) {
//            bigTypeRoomFragment1.loadData();
//        }
//        if (viewPager.getCurrentItem() == 2) {
//            bigTypeRoomFragment2.loadData();
//        }
//        if (viewPager.getCurrentItem() == 3) {
//            bigTypeRoomFragment3.loadData();
//        }
//        if (viewPager.getCurrentItem() == 4) {
//            bigTypeRoomFragment4.loadData();
//        }
    }


//    private void itemMove(int fromPosition,int toPosition) {
//        if (!isInTop) {
//            return;
//        }
//        if (isHidden) {
//            return;
//        }
//        if (smartRefreshLayout.isRefreshing()) {
//            return;
//        }
//        if (adapter.getData().size() <= 2) {
//            return;
//        }
//        int rvFromPosition = fromPosition + 1;
//        int rvToPosition = toPosition + 1;
//        adapter.getData().add(toPosition, adapter.getData().remove(fromPosition));
//        adapter.notifyItemMoved(rvFromPosition, rvToPosition);
//        adapter.notifyItemRangeChanged(getRvToPosition(rvFromPosition,rvToPosition), Math.abs(rvFromPosition - rvToPosition) + 1);
//    }

    private void getPartyBanner() {
        NetService.Companion.getInstance(getContext()).getBanner(getString(R.string.banner_type), new Callback<List<BannerBean>>() {
            @Override
            public void onSuccess(int nextPage, List<BannerBean> bean, int code) {
                if (!bean.isEmpty()) {
                    smartRefreshLayout.finishRefresh();
                    partyBannerList.clear();
                    partyBannerList.addAll(bean);
                    banner.setImages(partyBannerList)
                            .start();
                }
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


//    private void getPersonRoomList(int type) {
//        if (type == getTYPE_REFRESH()) {
//            mPage = 1;
//        }
//        NetService.Companion.getInstance(getContext()).getPersonRoomList(mPage, new Callback<HttpPageDataBean<PersonRoomItemBean>>() {
//            @Override
//            public void onSuccess(int nextPage, HttpPageDataBean<PersonRoomItemBean> bean, int code) {
//                if (type == getTYPE_REFRESH()) {
//                    adapter.setNewData(bean.getList());
//                } else {
//                    adapter.addData(bean.getList());
//                }
//                mPage++;
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

    @Override
    public void onResume() {
        super.onResume();
//        isHidden = false;
        banner.startAutoPlay();
    }

    @Override
    public void onPause() {
        super.onPause();
//        isHidden = true;
        banner.stopAutoPlay();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home;
    }


    public static HomeRoomFragment newInstance() {
        return new HomeRoomFragment();
    }

    @Override
    public void initView(@NotNull View view) {
//        hotFragment = AllHotRoomFragment.newInstance();
        viewPager = view.findViewById(R.id.content_vp);
        banner = view.findViewById(R.id.banner);

//        mlKMTopThree = view.findViewById(R.id.ml_rank_top_three);
//        cfKMTopThree = view.findViewById(R.id.cf_rank_top_three);
        smartRefreshLayout = view.findViewById(R.id.refresh_layout);
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                if (mFragmentList.size() > 0){
                    refreshHotRoom();
                }else{
                    getRoomlabel();
                }
                getPartyBanner();
//                getRankTopThree();
            }
        });
        banner.setOnBannerListener(position -> WebActivity.start(getContext(), position.getUrl(), position.getTitle()));

        getPartyBanner();
//        view.findViewById(R.id.ll_macth_room).setOnClickListener(v -> getRecommandRoom());
//        view.findViewById(R.id.ll_macth_talk).setOnClickListener(v ->    MatchUserActivity.start(Objects.requireNonNull(getActivity())));
//        view.findViewById(R.id.ll_macth_express).setOnClickListener(v -> ExpressRecordActivity.start(getActivity()));
//        view.findViewById(R.id.ll_macth_freely).setOnClickListener(v ->     startActivity(MakingFriendsActivity.Companion.getIntent(getContext())));
//        view.findViewById(R.id.ml_rank_btn).setOnClickListener(v -> RankActivity.start(getContext(), RankActivity.TAB_POSITION_ML));
//        view.findViewById(R.id.cf_rank_btn).setOnClickListener(v -> RankActivity.start(getContext(), RankActivity.TAB_POSITION_CF));
//        getRankTopThree();
        getRoomlabel();
    }

    private void getRoomlabel(){
        NetService.Companion.getInstance(getContext()).getRoomLabel(ChatRoomManager.ROOM_TYPE_LABOR_UNION, new Callback<List<RoomlabelBean>>() {
            @Override
            public void onSuccess(int nextPage, List<RoomlabelBean> bean, int code) {
                smartRefreshLayout.finishRefresh();
                mFragmentList.clear();
                mFragmentList.add(AllHotRoomFragment.newInstance());
                List<String> tabNames = new ArrayList<>();
                tabNames.add("热门");
                for (RoomlabelBean item : bean){
                    tabNames.add(item.getName());
                    mFragmentList.add(AllOtherTypeRoomFragment.newInstance(item.getId()));
                }
                baseFragmentAdapter = new BaseFragmentAdapter(getChildFragmentManager(),mFragmentList);
                viewPager.setAdapter(baseFragmentAdapter);
                viewPager.setOffscreenPageLimit(mFragmentList.size());

                baseFragmentAdapter.notifyDataSetChanged();
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


    private void intRankTopThree(List<RankBean> ranklist, KMHomeRankTopThreeView view){
        view.showToRank();
        if (ranklist.isEmpty()){
            return;
        }
        if (ranklist.size() == 1){
            view.showImg1(ranklist.get(0).getFace());
            view.showImg2("");
            view.showImg3("");
            return;
        }
        if (ranklist.size() == 2){
            view.showImg1(ranklist.get(0).getFace());
            view.showImg2(ranklist.get(1).getFace());
            view.showImg3("");
            return;
        }
        view.showImg1(ranklist.get(0).getFace());
        view.showImg2(ranklist.get(1).getFace());
        view.showImg3(ranklist.get(2).getFace());
    }
    private void getRecommandRoom(){

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
                ToastUtil.INSTANCE.error(getContext(),msg);
            }

            @Override
            public boolean isAlive() {
                return isLive();
            }
        });
    }
}
