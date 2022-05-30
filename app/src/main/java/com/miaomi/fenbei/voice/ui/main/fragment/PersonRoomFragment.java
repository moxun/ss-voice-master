//package com.miaomi.fenbei.voice.ui.main.fragment;
//
//import android.os.Bundle;
//import android.view.View;
//import android.widget.RelativeLayout;
//
//import com.chad.library.adapter.base.BaseQuickAdapter;
//import com.miaomi.fenbei.base.bean.HomepageBean;
//import com.miaomi.fenbei.base.bean.RecommandUserBean;
//import com.miaomi.fenbei.base.core.BaseLazyFragment;
//import com.miaomi.fenbei.base.core.msg.MsgManager;
//import com.miaomi.fenbei.base.net.Callback;
//import com.miaomi.fenbei.base.net.NetService;
//import com.miaomi.fenbei.base.util.LoadHelper;
//import com.miaomi.fenbei.base.util.ToastUtil;
//import com.miaomi.fenbei.room.ui.adapter.UserOnlineAdapter;
//import com.miaomi.fenbei.voice.R;
//import com.miaomi.fenbei.voice.ui.main.adapter.PersonRoomAdapter;
//import com.miaomi.fenbei.voice.ui.main.adapter.PlayingUserAdapter;
//import com.scwang.smartrefresh.layout.SmartRefreshLayout;
//
//import org.jetbrains.annotations.NotNull;
//
//import java.util.List;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//public class PersonRoomFragment extends BaseLazyFragment {
//
//    private RecyclerView roomRv;
//    private PersonRoomAdapter adapter;
//    private RecyclerView onLineRv;
//    private RecyclerView userRv;
//    private RelativeLayout ret_follow;
//    private PlayingUserAdapter playingUserAdapter;
//    private LoadHelper loadHelper;
//    private int mPage = 0;
//    private SmartRefreshLayout refreshLayout;
//    private UserOnlineAdapter homepageAdapter;
//    public static PersonRoomFragment newInstance(int type) {
//        PersonRoomFragment fragment = new PersonRoomFragment();
//        Bundle args = new Bundle();
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    @Override
//    public int getLayoutId() {
//        return R.layout.fragment_person_room;
//    }
//
//    @Override
//    public void initView(@NotNull View view) {
//        roomRv = view.findViewById(R.id.hot_rv);
//        refreshLayout = view.findViewById(R.id.refresh_layout);
//        onLineRv = view.findViewById(R.id.rv_online_user);
//        userRv = view.findViewById(R.id.x_reclclerview);
//        ret_follow=view.findViewById(R.id.rlt_follow);
//        homepageAdapter = new UserOnlineAdapter(getContext());
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
//        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
//        onLineRv.setLayoutManager(linearLayoutManager);
//        onLineRv.setAdapter(homepageAdapter);
//        playingUserAdapter = new PlayingUserAdapter();
//        playingUserAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);
//        userRv.setLayoutManager(new LinearLayoutManager(getContext()));
//        userRv.setAdapter(playingUserAdapter);
//
//        refreshLayout.setOnRefreshListener(refreshLayout -> {
//            getData(getTYPE_REFRESH());
//            MsgManager.INSTANCE.getConversationList();
//            refreshLayout.finishRefresh(true);
//        });
////        adapter = new PersonRoomAdapter();
////        refreshLayout.setOnRefreshListener(refreshLayout -> getData(getTYPE_REFRESH()));
////        refreshLayout.setOnLoadMoreListener(refreshLayout -> getData(getTYPE_LOADMROE()));
////        roomRv.setLayoutManager(new GridLayoutManager(getActivity(), 2));
////        roomRv.addItemDecoration(new GridSpacingItemDecoration(2, DensityUtil.INSTANCE.dp2px(getContext(), 10f), false));
////        roomRv.setAdapter(adapter);
//        loadHelper = new LoadHelper();
//        loadHelper.registerLoad(userRv);
//    }
//
////    private void getData(int type) {
////        if (type == getTYPE_REFRESH()) {
////            mPage = 1;
////        }
////        NetService.Companion.getInstance(getActivity()).getPersonRoomList(mPage, new Callback<List<PersonRoomItemBean>>() {
////            @Override
////            public void onSuccess(int nextPage, List<PersonRoomItemBean> list, int code) {
//////                roomRv.refreshComplete();
////                if (type == getTYPE_REFRESH()) {
////                    refreshLayout.finishRefresh(true);
//////                    if (list.size() == 0) {
//////                        loadHelper.setEmptyCallback(R.drawable.common_empty_bg, "空空如也");
//////                    } else {
//////                        loadHelper.bindView(code);
//////                    }
////                    adapter.setNewData(list);
////                } else {
////                    refreshLayout.finishLoadMore(true);
////                    adapter.addData(list);
////                }
////                mPage = nextPage;
////            }
////
////            @Override
////            public void noMore() {
////                super.noMore();
////                refreshLayout.finishLoadMoreWithNoMoreData();
////            }
////
////            @Override
////            public void onError(@NotNull String msg, @NotNull Throwable throwable, int code) {
////                if (type == getTYPE_REFRESH()) {
////                    refreshLayout.finishRefresh(false);
//////                    loadHelper.setErrorCallback(code, v -> getData(getTYPE_REFRESH()));
////                } else {
////                    refreshLayout.finishLoadMore(false);
////                }
////            }
////
////            @Override
////            public boolean isAlive() {
////                return isLive();
////            }
////        });
////    }
//
//    private void getData(int type){
//        NetService.Companion.getInstance(getContext()).getHomepage(new Callback<List<HomepageBean>>() {
//            @Override
//            public void onSuccess(int nextPage, List<HomepageBean> bean, int code) {
//                if (bean.size() == 0){
//                    ret_follow.setVisibility(View.GONE);
////                    onLineRv.setVisibility(View.GONE);
//                }else {
//                    ret_follow.setVisibility(View.VISIBLE);
////                    onLineRv.setVisibility(View.VISIBLE);
//                }
//                homepageAdapter.setData(bean);
//            }
//
//            @Override
//            public void onError(@NotNull String msg, @NotNull Throwable throwable, int code) {
//                ToastUtil.INSTANCE.error(getContext(), msg);
//            }
//
//            @Override
//            public boolean isAlive() {
//                return isLive();
//            }
//        });
//
//        NetService.Companion.getInstance(getContext()).getRecommandUsers(mPage,new Callback<RecommandUserBean>() {
//            @Override
//            public void onSuccess(int nextPage, RecommandUserBean bean, int code) {
//                if (type == getTYPE_REFRESH()){
//                    if (bean.getUsers().size() == 0){
//                        loadHelper.setEmptyCallback(R.drawable.common_empty_bg, "空空如也");
//                    }else{
//                        loadHelper.bindView(code);
//                    }
//                    refreshLayout.finishRefresh();
//                    playingUserAdapter.setNewData(bean.getUsers());
//                }else{
//                    refreshLayout.finishLoadMore();
//                    playingUserAdapter.addData(bean.getUsers());
//                }
//                if (bean.getOffset() == 0){
//                    noMore();
//                }
//                mPage = bean.getOffset();
//            }
//
//            @Override
//            public void noMore() {
//                super.noMore();
//                refreshLayout.setNoMoreData(true);
//            }
//
//            @Override
//            public void onError(@NotNull String msg, @NotNull Throwable throwable, int code) {
//                if (type == getTYPE_REFRESH()){
//                    refreshLayout.finishRefresh(false);
////                    loadHelper.setErrorCallback(code, v -> getData(getTYPE_REFRESH()));
//                }else{
//                    refreshLayout.finishLoadMore(false);
//                    ToastUtil.INSTANCE.error(getContext(),msg);
//                }
//            }
//
//            @Override
//            public boolean isAlive() {
//                return isLive();
//            }
//        });
//
//    }
//    @Override
//    public void loadData() {
////        getData(getTYPE_REFRESH());
//        getData(getTYPE_REFRESH());
//    }
//}
