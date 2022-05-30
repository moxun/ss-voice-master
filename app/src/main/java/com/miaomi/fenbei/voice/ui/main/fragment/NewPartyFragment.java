//package com.miaomi.fenbei.voice.ui.main.fragment;
//
//import android.graphics.Color;
//import android.util.Log;
//import android.view.Gravity;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.widget.LinearLayout;
//import android.widget.TableLayout;
//
//import com.example.indicatorlib.base.BaseFragmentAdapter;
//import com.example.indicatorlib.views.TabLayout;
//import com.miaomi.fenbei.base.bean.BannerBean;
//import com.miaomi.fenbei.base.bean.HttpPageDataBean;
//import com.miaomi.fenbei.base.bean.PersonRoomItemBean;
//import com.miaomi.fenbei.voice.BaseFragment;
//import com.miaomi.fenbei.voice.Callback;
//import com.miaomi.fenbei.voice.NetService;
//import com.miaomi.fenbei.voice.DisplayUtil;
//import com.miaomi.fenbei.base.web.WebActivity;
//import com.miaomi.fenbei.base.widget.KMRoomIndicator;
//import com.miaomi.fenbei.base.widget.banner.Banner;
//import com.miaomi.fenbei.base.widget.banner.listener.OnBannerListener;
//import com.miaomi.fenbei.base.widget.xrecyclerview.XRecyclerView;
//import com.miaomi.fenbei.voice.R;
//import com.miaomi.fenbei.voice.ui.AllRoomActivity;
//import com.miaomi.fenbei.voice.ui.RankActivity;
//import com.miaomi.fenbei.voice.ui.main.adapter.PersonRoomAdapter;
//import com.miaomi.fenbei.voice.ui.main.fragment.home.RecommandHotRoomFragment;
//import com.miaomi.fenbei.voice.ui.main.fragment.home.RecommandOtherTypeRoomFragment;
//import com.miaomi.fenbei.voice.ui.search.SearchActivity;
//import com.scwang.smartrefresh.layout.SmartRefreshLayout;
//import com.scwang.smartrefresh.layout.api.RefreshLayout;
//import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
//import com.scwang.smartrefresh.layout.util.DensityUtil;
//
//import org.jetbrains.annotations.NotNull;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import androidx.annotation.NonNull;
//import androidx.fragment.app.Fragment;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
//import androidx.viewpager.widget.ViewPager;
//
//public class NewPartyFragment extends BaseFragment {
//
//    private List<BannerBean> partyBannerList =new ArrayList();
//    private PersonRoomAdapter adapter;
//    private List<Fragment> mFragmentList = new ArrayList();
//    private View headView;
//    private int mPage = 1;
//    private RecommandHotRoomFragment hotFragment;
//    private RecommandOtherTypeRoomFragment bigTypeRoomFragment1;
//    private RecommandOtherTypeRoomFragment bigTypeRoomFragment2;
//    private RecommandOtherTypeRoomFragment bigTypeRoomFragment3;
//    private RecommandOtherTypeRoomFragment bigTypeRoomFragment4;
//    private boolean isInTop = true;
//    private ViewPager viewPager;
//    private Banner banner;
//    private RecyclerView roomRv;
//    private KMRoomIndicator tabLayout;
//    private SwipeRefreshLayout smartRefreshLayout;
//    private boolean isHidden = true;
//
//
//    private void refreshHotRoom() {
//        if (viewPager.getCurrentItem() == 0) {
//            hotFragment.loadData(getTYPE_REFRESH());
//        }
//        if (viewPager.getCurrentItem() == 1) {
//            bigTypeRoomFragment1.getData();
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
//    }
//
//
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
//
//    private void getPartyBanner() {
//        NetService.Companion.getInstance(getContext()).getBanner(getString(R.string.banner_type), new Callback<List<BannerBean>>() {
//            @Override
//            public void onSuccess(int nextPage, List<BannerBean> bean, int code) {
//                if (!bean.isEmpty()) {
//                    partyBannerList.clear();
//                    partyBannerList.addAll(bean);
//                    banner.setImages(partyBannerList)
//                            .start();
//                }
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
//
//
//    private void getPersonRoomList(int type) {
//        if (type == getTYPE_REFRESH()) {
//            mPage = 1;
//        }
//        NetService.Companion.getInstance(getContext()).getPersonRoomList(mPage, new Callback<HttpPageDataBean<PersonRoomItemBean>>() {
//            @Override
//            public void onSuccess(int nextPage, HttpPageDataBean<PersonRoomItemBean> bean, int code) {
//                if (type == getTYPE_REFRESH()) {
//                    adapter.setNewData(bean.getList());
//                    smartRefreshLayout.setRefreshing(false); //传入false表示刷新失败
//                } else {
//                    smartRefreshLayout.setRefreshing(false);
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
//
//    @Override
//    public void onResume() {
//        super.onResume();
//        isHidden = false;
//        banner.startAutoPlay();
//    }
//
//    @Override
//    public void onPause() {
//        super.onPause();
//        isHidden = true;
//        banner.stopAutoPlay();
//    }
//
//    @Override
//    public int getLayoutId() {
//        return R.layout.fragment_party;
//    }
//
//
//    public static NewPartyFragment newInstance() {
//        return new NewPartyFragment();
//    }
//
//    @Override
//    public void initView(@NotNull View view) {
//        hotFragment = RecommandHotRoomFragment.newInstance();
//        bigTypeRoomFragment1 = RecommandOtherTypeRoomFragment.newInstance(RecommandOtherTypeRoomFragment.ROOM_TYPE_XNNY_FEMALE);
//        bigTypeRoomFragment2 = RecommandOtherTypeRoomFragment.newInstance(RecommandOtherTypeRoomFragment.ROOM_TYPE_XNNY_MAN);
//        bigTypeRoomFragment3 = RecommandOtherTypeRoomFragment.newInstance(RecommandOtherTypeRoomFragment.ROOM_TYPE_QG);
//        bigTypeRoomFragment4 = RecommandOtherTypeRoomFragment.newInstance(RecommandOtherTypeRoomFragment.ROOM_TYPE_JY);
//        headView = LayoutInflater.from(getContext()).inflate(R.layout.head_fragment_party, null);
//        headView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
//        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(DisplayUtil.INSTANCE.getScreenWidth(getContext()) - DensityUtil.dp2px(30f)
//                , DisplayUtil.INSTANCE.getScreenWidth(getContext()) / 3);
//        layoutParams.gravity = Gravity.CENTER_HORIZONTAL;
//        roomRv = view.findViewById(R.id.recycler_view);
//        viewPager = headView.findViewById(R.id.view_pager);
//        banner = headView.findViewById(R.id.banner);
//        tabLayout = headView.findViewById(R.id.tab_layout);
//        smartRefreshLayout = view.findViewById(R.id.refresh_layout);
//        smartRefreshLayout.setColorSchemeColors(Color.parseColor("#FD7F8F"));
//        banner.setLayoutParams(layoutParams);
//        banner.setOnBannerListener(position -> WebActivity.start(getContext(), position.getUrl(), position.getTitle()));
//
//        adapter =new PersonRoomAdapter();
//        adapter.setHeaderView(headView);
//        roomRv.setLayoutManager(new LinearLayoutManager(getContext()));
//        roomRv.setAdapter(adapter);
//        roomRv.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
//                isInTop = !recyclerView.canScrollVertically(-1);
//            }
//        });
//        mFragmentList.clear();
//        mFragmentList.add(hotFragment);
//        mFragmentList.add(bigTypeRoomFragment1);
//        mFragmentList.add(bigTypeRoomFragment2);
//        mFragmentList.add(bigTypeRoomFragment3);
//        mFragmentList.add(bigTypeRoomFragment4);
////        initCommonNavigator()
//        viewPager.setOffscreenPageLimit(mFragmentList.size());
//        viewPager.setAdapter(new BaseFragmentAdapter(getChildFragmentManager(), mFragmentList));
//        banner.setOnBannerChangeLisetner(() -> itemMove(0, adapter.getData().size() - 1));
//        tabLayout.setViewPager(viewPager);
//        smartRefreshLayout.setOnRefreshListener(() -> {
//            getPartyBanner();
//            getPersonRoomList(getTYPE_REFRESH());
//            refreshHotRoom();
//        });
//
//        getPersonRoomList(getTYPE_REFRESH());
//        getPartyBanner();
//
//        headView.findViewById(R.id.tv_more_room).setOnClickListener(v -> AllRoomActivity.start(getContext()));
//        view.findViewById(R.id.fl_search).setOnClickListener(v -> SearchActivity.start(getContext()));
//        view.findViewById(R.id.rank_btn).setOnClickListener(v -> RankActivity.start(getContext()));
//    }
//}
