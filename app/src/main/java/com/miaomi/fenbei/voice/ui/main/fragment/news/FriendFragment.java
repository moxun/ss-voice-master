package com.miaomi.fenbei.voice.ui.main.fragment.news;

import android.os.Bundle;
import android.view.View;

import com.miaomi.fenbei.base.bean.FriendsBean;
import com.miaomi.fenbei.base.core.BaseLazyFragment;
import com.miaomi.fenbei.base.net.Callback;
import com.miaomi.fenbei.base.net.Data;
import com.miaomi.fenbei.base.net.NetService;
import com.miaomi.fenbei.base.util.LoadHelper;
import com.miaomi.fenbei.base.util.ToastUtil;
import com.miaomi.fenbei.base.widget.search.SearchView;
import com.miaomi.fenbei.base.widget.xrecyclerview.XRecyclerView;
import com.miaomi.fenbei.voice.R;
import com.miaomi.fenbei.voice.ui.main.adapter.FriendAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;

public class FriendFragment extends BaseLazyFragment implements XRecyclerView.LoadingListener {

    private XRecyclerView followerRv;
    private FriendAdapter adapter;
    private List<FriendsBean> list = new ArrayList<>();
    private final static String FRIEND_TYPE = "FRIEND_TYPE";
    public final static int FRIEND_TYPE_FOLLOW = 0;
    public final static int FRIEND_TYPE_FANS = 1;
    private int friendType = 1;
    private int page = 1;
    private LoadHelper loadHelper;
    private SearchView mSearch;

    public static FriendFragment newInstance(int type){
        FriendFragment fragment = new FriendFragment();
        Bundle args = new Bundle();
        args.putInt(FRIEND_TYPE,type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.user_fragment_follower;
    }

    @Override
    public void initView(@NotNull View view) {
//        EventBus.getDefault().register(this);
        friendType = getArguments().getInt(FRIEND_TYPE);
        adapter = new FriendAdapter(getActivity(),list,friendType);
        followerRv = view.findViewById(R.id.follow_rv);
        followerRv.setLoadingMoreEnabled(true);
        followerRv.setPullRefreshEnabled(true);
        followerRv.setLayoutManager(new LinearLayoutManager(getActivity()));
        followerRv.setLoadingListener(this);
        followerRv.setAdapter(adapter);
        loadHelper = new LoadHelper();
        loadHelper.registerLoad(followerRv);
        mSearch = view.findViewById(R.id.sv_search);
        mSearch.setOnSearchListener(keyWord -> searchFriend(keyWord));
    }

    private void searchFriend(String keyword){
        NetService.Companion.getInstance(getMContext()).searchFriend(friendType,keyword, new Callback<List<FriendsBean>>() {
            @Override
            public void onSuccess(int nextPage, List<FriendsBean> bean, int code) {
                if (bean.size()>0){
                    list.clear();
                    list.addAll(bean);
                    adapter.notifyDataSetChanged();
                }else{
                    ToastUtil.INSTANCE.suc(getContext(),"没有相关用户");
                }
            }

            @Override
            public void onError(@NotNull String msg, @NotNull Throwable throwable, int code) {
                ToastUtil.INSTANCE.suc(getContext(),msg);
            }

            @Override
            public boolean isAlive() {
                return isLive();
            }

            @Override
            public void noMore() {
                super.noMore();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
    }


    private void getData(final int type){
        if (type == getTYPE_REFRESH()){
            followerRv.setLoadingMoreEnabled(true);
            page = 1;
        }
        if (friendType == FRIEND_TYPE_FOLLOW){
            NetService.Companion.getInstance(getContext()).getFollowList(page, new Callback<List<FriendsBean>>() {
                @Override
                public void onSuccess(int nextPage, List<FriendsBean> bean, int code) {
                    loadSuccess(bean,R.drawable.common_empty_bg,"赶紧关注几个感兴趣的主播吧",type);
                }

                @Override
                public void onError(@NotNull String msg, @NotNull Throwable throwable, int code) {
                    loadFaile(code);
                }

                @Override
                public boolean isAlive() {
                    return isLive();
                }

                @Override
                public void noMore() {
                    super.noMore();
                    followerRv.setLoadingMoreEnabled(false);
                }
            });
        }
        if (friendType == FRIEND_TYPE_FANS){
            NetService.Companion.getInstance(getContext()).getFansList(page, new Callback<List<FriendsBean>>() {
                @Override
                public void onSuccess(int nextPage, List<FriendsBean> bean, int code) {
                    loadSuccess(bean,R.drawable.common_empty_bg,"空空如也",type);
                }

                @Override
                public void onError(@NotNull String msg, @NotNull Throwable throwable, int code) {
                    loadFaile(code);
                }

                @Override
                public boolean isAlive() {
                    return isLive();
                }

                @Override
                public void noMore() {
                    super.noMore();
                    followerRv.setLoadingMoreEnabled(false);
                }
            });
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void loadSuccess(List<FriendsBean> dataList,int resId,String msg,int type){
        followerRv.refreshComplete();
        loadHelper.bindView(Data.CODE_SUC);
        if (dataList.size() <= 0){
            //空状态
            loadHelper.setEmptyCallback(resId,msg);
            return;
        }
        page++;
        if (type == getTYPE_REFRESH()){
            list.clear();
        }
        list.addAll(dataList);
        adapter.notifyDataSetChanged();
    }

    private void loadFaile(int code){
        followerRv.refreshComplete();
        loadHelper.setErrorCallback(code, v -> getData(getTYPE_REFRESH()));
    }





    @Override
    public void onRefresh() {
        getData(getTYPE_REFRESH());
    }

    @Override
    public void onLoadMore() {
        getData(getTYPE_LOADMROE());
    }

    @Override
    public void loadData() {
        getData(getTYPE_REFRESH());
    }
}
