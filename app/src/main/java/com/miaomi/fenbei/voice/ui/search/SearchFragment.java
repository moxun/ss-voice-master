package com.miaomi.fenbei.voice.ui.search;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.miaomi.fenbei.base.bean.SearchItemBean;
import com.miaomi.fenbei.base.core.BaseActivity;
import com.miaomi.fenbei.base.core.BaseFragment;
import com.miaomi.fenbei.base.net.Callback;
import com.miaomi.fenbei.base.net.Data;
import com.miaomi.fenbei.base.net.NetService;
import com.miaomi.fenbei.base.util.LoadHelper;
import com.miaomi.fenbei.base.util.ToastUtil;
import com.miaomi.fenbei.base.widget.search.SearchView;
import com.miaomi.fenbei.base.widget.xrecyclerview.XRecyclerView;
import com.miaomi.fenbei.voice.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;

public class SearchFragment extends BaseFragment implements View.OnClickListener,XRecyclerView.LoadingListener {
    XRecyclerView mSearchRecyclerView;
    SearchAdapter mSearchAdapter;
    SearchView mSearchView;
    LoadHelper loadHelper;
    List<SearchItemBean> mSearchList = new ArrayList<>();
    public final static int TYPE_REFRESH = 0;
    public final static int TYPE_LOADMROE = 1;
    public final static String TYPE_SEARCH = "TYPE_SEARCH";
    public final static int TYPE_SEARCH_USER_RADIO = 3;
    public final static int TYPE_SEARCH_ROOM_RADIO = 2;
    public final static int TYPE_SEARCH_ROOM = 1;
    public final static int TYPE_SEARCH_USER = 0;
    private int page = 1;
    private int searchType;
    String keyword;
    private TextView searchTv;

    public static SearchFragment newInstance(int type){
        SearchFragment fragment = new SearchFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(TYPE_SEARCH,type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.main_fragment_search;
    }

    @Override
    public void initView(@NotNull View view) {
        searchType = getArguments().getInt(TYPE_SEARCH);
        mSearchRecyclerView = view.findViewById(R.id.rv_search);
        mSearchView = view.findViewById(R.id.sv_search);
        searchTv = view.findViewById(R.id.tv_search);
        searchTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSearchAdapter.setKeyWord(mSearchView.getKeyword(),searchType);
                getData(TYPE_REFRESH);
            }
        });
        mSearchView.setOnSearchListener(new SearchView.OnSearchListener() {
            @Override
            public void onSearch(String keyWord) {
                mSearchAdapter.setKeyWord(keyWord,searchType);
                getData(TYPE_REFRESH);
            }
        });
        mSearchAdapter = new SearchAdapter(mSearchList, (BaseActivity) getMContext());
        mSearchRecyclerView.setPullRefreshEnabled(false);
        mSearchRecyclerView.setLoadingMoreEnabled(true);
        mSearchRecyclerView.setLoadingListener(this);
        mSearchRecyclerView.setLayoutManager(new LinearLayoutManager(getMContext()));
        mSearchRecyclerView.setAdapter(mSearchAdapter);
        loadHelper = new LoadHelper();
        loadHelper.registerLoad(mSearchRecyclerView);
        loadHelper.bindView(Data.CODE_SUC);
        if (searchType == TYPE_SEARCH_ROOM_RADIO) {
            mSearchView.setHint("请输入电台名称或ID");
        }else if (searchType == TYPE_SEARCH_ROOM){
            mSearchView.setHint("请输入房间名称或ID");
        }else if(TYPE_SEARCH_USER_RADIO == searchType){
            mSearchView.setHint("请输入主播昵称或ID");
        }else{
            mSearchView.setHint("请输入用户昵称或ID");
        }
        mSearchView.setFocusable(true);
        mSearchView.setFocusableInTouchMode(true);
    }

//    @Override
//    public void onWindowFocusChanged(boolean hasFocus) {
//        super.onWindowFocusChanged(hasFocus);
//        getMContext().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
//    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

    }
    private void bindData(List<SearchItemBean> list){
        if (list.size() == 0){
            loadHelper.bindView(Data.CODE_EMPTY);
            return;
        }
        mSearchList.clear();
        mSearchList.addAll(list);
        mSearchAdapter.notifyDataSetChanged();
    }
    private void addData(List<SearchItemBean> list){
        mSearchList.addAll(list);
        mSearchAdapter.notifyDataSetChanged();
    }
    private void getData(final int type){
        if (type == TYPE_REFRESH){
            page = 1;
            keyword = mSearchView.getKeyword();
        }
        NetService.Companion.getInstance(getMContext()).search(page, keyword,searchType, new Callback<List<SearchItemBean>>() {
            @Override
            public void onSuccess(int nextPage, List<SearchItemBean> bean, int code) {
                loadHelper.bindView(code);
                mSearchRecyclerView.refreshComplete();
                if (type == TYPE_REFRESH){
                    bindData(bean);
                }
                if (type == TYPE_LOADMROE){
                    addData(bean);
                }
                page ++;
            }

            @Override
            public void onError(@NotNull String msg, @NotNull Throwable throwable, int code) {
                mSearchRecyclerView.refreshComplete();
                ToastUtil.INSTANCE.suc(getMContext(),msg);
            }

            @Override
            public boolean isAlive() {
                return isLive();
            }

            @Override
            public void noMore() {
                super.noMore();
                mSearchRecyclerView.setLoadingMoreEnabled(false);
            }
        });
    }

    @Override
    public void onRefresh() {
        getData(TYPE_REFRESH);
    }

    @Override
    public void onLoadMore() {
        getData(TYPE_LOADMROE);
    }
}
