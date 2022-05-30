package com.miaomi.fenbei.voice.ui;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.miaomi.fenbei.base.bean.FriendsBean;
import com.miaomi.fenbei.base.core.BaseActivity;
import com.miaomi.fenbei.base.net.Callback;
import com.miaomi.fenbei.base.net.Data;
import com.miaomi.fenbei.base.net.NetService;
import com.miaomi.fenbei.base.util.LoadHelper;
import com.miaomi.fenbei.base.util.ToastUtil;
import com.miaomi.fenbei.base.widget.search.SearchView;
import com.miaomi.fenbei.base.widget.xrecyclerview.XRecyclerView;
import com.miaomi.fenbei.voice.R;
import com.miaomi.fenbei.voice.ui.main.adapter.FriendAdapter;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;

import org.jetbrains.annotations.NotNull;

@Route(path = "/app/friend")
public class FriendActivity extends BaseActivity implements XRecyclerView.LoadingListener {
    private List<FriendsBean> list = new ArrayList<>();
    private LoadHelper loadHelper;
    private SearchView mSearch;
    private XRecyclerView followerRv;
    private TextView titleTv;
    private FriendAdapter adapter;
    private int friendType = 1;
    private int page = 1;
    public final static int FRIEND_TYPE_HAOYOU = 2;
    public final static int FRIEND_TYPE_FOLLOW = 0;
    public final static int FRIEND_TYPE_FANS = 1;

    public static void start(Context context, boolean isSelectFans) {
        Intent intent = new Intent(context, FriendActivity.class);
        intent.putExtra("isSelectFans", isSelectFans);
        context.startActivity(intent);
    }

    @Override
    public void initView() {
        setBaseStatusBar(false, false);
        titleTv = findViewById(R.id.tv_title);
        friendType = getIntent().getIntExtra("friendtype", 0);
        if (friendType == 1) {
            friendType = FRIEND_TYPE_FANS;
            titleTv.setText("我的粉丝");
        } else if (friendType == 0) {
            friendType = FRIEND_TYPE_FOLLOW;
            titleTv.setText("我的关注");
        } else {
            titleTv.setText("我的好友");
            friendType = FRIEND_TYPE_HAOYOU;
        }
        followerRv = findViewById(R.id.follow_rv);
        adapter = new FriendAdapter(this, list, friendType);
        followerRv.setLoadingMoreEnabled(true);
        followerRv.setPullRefreshEnabled(true);
        followerRv.setLayoutManager(new LinearLayoutManager(this));
        followerRv.setLoadingListener(this);
        followerRv.setAdapter(adapter);
        loadHelper = new LoadHelper();
        loadHelper.registerLoad(followerRv);
        mSearch = findViewById(R.id.sv_search);
        mSearch.setHint("请输入昵称或者ID");
        mSearch.setOnSearchListener(new SearchView.OnSearchListener() {
            @Override
            public void onSearch(String keyWord) {
                if(friendType==FRIEND_TYPE_HAOYOU){
                    getData(getTYPE_REFRESH(),keyWord);
                }else{
                    if(TextUtils.isEmpty(keyWord)){
                        ToastUtil.INSTANCE.suc(getBaseContext(), "搜索关键字不能为空");
                    }else{
                        searchFriend(keyWord);
                    }

                }

            }
        });
//        mSearch.setOnSearchListener(keyWord ->
//
//        );
        getData(getTYPE_REFRESH(),"");
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_friend;
    }

    private void searchFriend(String keyword) {
        NetService.Companion.getInstance(this).searchFriend(friendType, keyword, new Callback<List<FriendsBean>>() {
            @Override
            public void onSuccess(int nextPage, List<FriendsBean> bean, int code) {
                if (bean.size() > 0) {
                    list.clear();
                    list.addAll(bean);
                    adapter.notifyDataSetChanged();
                } else {
                    ToastUtil.INSTANCE.suc(getBaseContext(), "没有相关用户");
                }
            }

            @Override
            public void onError(@NotNull String msg, @NotNull Throwable throwable, int code) {
                ToastUtil.INSTANCE.suc(getBaseContext(), msg);
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

    private void getData(final int type,String keyword) {
        if (type == getTYPE_REFRESH()) {
            followerRv.setLoadingMoreEnabled(true);
            page = 1;
        }
        if (friendType == FRIEND_TYPE_FOLLOW) {
            NetService.Companion.getInstance(this).getFollowList(page, new Callback<List<FriendsBean>>() {
                @Override
                public void onSuccess(int nextPage, List<FriendsBean> bean, int code) {
                    loadSuccess(bean, R.drawable.common_empty_bg, "赶紧关注几个感兴趣的主播吧", type);
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
        if (friendType == FRIEND_TYPE_FANS) {
            NetService.Companion.getInstance(this).getFansList(page, new Callback<List<FriendsBean>>() {
                @Override
                public void onSuccess(int nextPage, List<FriendsBean> bean, int code) {
                    loadSuccess(bean, R.drawable.common_empty_bg, "空空如也", type);
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

        if (friendType == FRIEND_TYPE_HAOYOU) {
            NetService.Companion.getInstance(this).getFriends(page,keyword, new Callback<List<FriendsBean>>() {
                @Override
                public void onSuccess(int nextPage, List<FriendsBean> bean, int code) {
                    loadSuccess(bean, R.drawable.common_empty_bg, "空空如也", type);
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

    private void loadSuccess(List<FriendsBean> dataList, int resId, String msg, int type) {
        followerRv.refreshComplete();
        loadHelper.bindView(Data.CODE_SUC);
        if (dataList.size() <= 0) {
            //空状态
            loadHelper.setEmptyCallback(resId, msg);
            return;
        }
        page++;
        if (type == getTYPE_REFRESH()) {
            list.clear();
        }
        list.addAll(dataList);
        adapter.notifyDataSetChanged();
    }

    private void loadFaile(int code) {
        followerRv.refreshComplete();
        loadHelper.setErrorCallback(code, v -> getData(getTYPE_REFRESH(),""));
    }

    @Override
    public void onRefresh() {
        getData(getTYPE_REFRESH(),"");
    }

    @Override
    public void onLoadMore() {
        getData(getTYPE_LOADMROE(),"");
    }


}
