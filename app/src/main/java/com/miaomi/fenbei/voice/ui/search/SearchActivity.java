package com.miaomi.fenbei.voice.ui.search;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.indicatorlib.base.BaseFragmentAdapter;
import com.example.indicatorlib.views.TabLayout;
import com.google.android.exoplayer2.C;
import com.miaomi.fenbei.base.bean.BaseBean;
import com.miaomi.fenbei.base.bean.FamilyBean;
import com.miaomi.fenbei.base.bean.RoomListBean;
import com.miaomi.fenbei.base.bean.SearchItemBean;
import com.miaomi.fenbei.base.core.BaseActivity;
import com.miaomi.fenbei.base.net.Callback;
import com.miaomi.fenbei.base.net.Data;
import com.miaomi.fenbei.base.net.NetService;
import com.miaomi.fenbei.base.util.LoadHelper;
import com.miaomi.fenbei.base.util.SpSearchUtils;
import com.miaomi.fenbei.base.util.ToastUtil;
import com.miaomi.fenbei.base.widget.ZFlowLayout;
import com.miaomi.fenbei.base.widget.search.SearchView;
import com.miaomi.fenbei.base.widget.xrecyclerview.XRecyclerView;
import com.miaomi.fenbei.voice.R;


import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import org.jetbrains.annotations.NotNull;


@Route(path = "/app/search")
public class SearchActivity extends BaseActivity implements View.OnClickListener,XRecyclerView.LoadingListener{

    XRecyclerView mSearchRecyclerView;
    SearchAdapter mSearchAdapter;
    List<SearchItemBean> mSearchList = new ArrayList<>();
    private  int TYPE_REFRESH = 0;
    private int TYPE_LOADMROE = 1;
    private int TYPE_SEARCH_ROOM = 1;
    private int TYPE_SEARCH_USER = 0;
    LoadHelper loadHelper;
    RecyclerView  friendRv;
    String keyword;
    ZFlowLayout zFlowLayout;
    RoomListAdapter adapter;
    private TextView cleanTv;
    EditText searchEt;
    ImageView delIV;
    private ImageView searchIv;
    private int searchType = TYPE_SEARCH_ROOM;
    private int page = 1;
    private TextView userTv,userLineTv,homeTv,homeLineTv;
    private LinearLayout search_homeLl;
    private LinearLayout search_home_dataLl;
    public static void start(Context context){
        Intent intent = new Intent(context,SearchActivity.class);
        context.startActivity(intent);
    }
    @Override
    public int getLayoutId() {
        return R.layout.main_activity_search;
    }

    @Override
    public void initView() {
        setBaseStatusBar(false, false);
        searchEt=findViewById(R.id.et_search);
        searchIv=findViewById(R.id.iv_search);
        mSearchRecyclerView = findViewById(R.id.rv_search);
        friendRv = findViewById(R.id.rv_friend);
        delIV= findViewById(R.id.iv_del);
        cleanTv=findViewById(R.id.tv_clean);
        zFlowLayout = findViewById(R.id.history_fl);
        search_homeLl=findViewById(R.id.ll_search_home);
        search_home_dataLl=findViewById(R.id.ll_search_home_data);
        userTv=findViewById(R.id.tv_user);
        userLineTv=findViewById(R.id.tv_user_line);
        homeTv=findViewById(R.id.tv_home);
        homeLineTv=findViewById(R.id.tv_home_line);
//        SearchFragment
        mSearchAdapter = new SearchAdapter(mSearchList,SearchActivity.this);
        mSearchRecyclerView.setPullRefreshEnabled(false);
        mSearchRecyclerView.setLoadingMoreEnabled(true);
        mSearchRecyclerView.setLoadingListener(this);
        mSearchRecyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        mSearchRecyclerView.setAdapter(mSearchAdapter);
        loadHelper = new LoadHelper();
        loadHelper.registerLoad(mSearchRecyclerView);
        loadHelper.bindView(Data.CODE_SUC);
        searchIv.setOnClickListener(this);
        delIV.setOnClickListener(this);
        cleanTv.setOnClickListener(this);
        userTv.setOnClickListener(this);
        homeTv.setOnClickListener(this);
        initHistory();
        searchEt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                search_homeLl.setVisibility(View.GONE);
                search_home_dataLl.setVisibility(View.VISIBLE);
                if (!isNullorEmpty(v.getText().toString())) {
                    SpSearchUtils.getInstance(SearchActivity.this).home_save(v.getText().toString());
                    initHistory();
                    mSearchAdapter.setKeyWord(v.getText().toString(),searchType);
                    getData(TYPE_REFRESH);
                }



                return false;
            }
        });
//

        adapter = new RoomListAdapter(this);
        friendRv.setLayoutManager(new LinearLayoutManager(this));
        friendRv.setAdapter(adapter);
        getRoomRandListData();
    }
    private void getRoomRandListData(){
        NetService.Companion.getInstance(this).RoomRandList(new Callback<List<RoomListBean.DataBean>>() {
            @Override
            public void onSuccess(int nextPage, List<RoomListBean.DataBean> bean, int code) {
                adapter.setData(bean);
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

    /**
     * 初始化  历史记录列表
     */
    private void initHistory() {

        final String[] data = SpSearchUtils.getInstance(SearchActivity.this).home_getHistoryList();
        ViewGroup.MarginLayoutParams layoutParams = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(10, 10, 10, 10);

        zFlowLayout.removeAllViews();
        for (int i = 0; i < data.length; i++) {
            if (isNullorEmpty(data[i])) {

                return;
            }
            //有数据往下走
            final int j = i;
            //添加分类块
            View paramItemView = getLayoutInflater().inflate(R.layout.adapter_search_keyword, null);
            TextView keyWordTv = paramItemView.findViewById(R.id.tv_content);
            keyWordTv.setText(data[j]);
            zFlowLayout.addView(paramItemView, layoutParams);
            keyWordTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    search_homeLl.setVisibility(View.GONE);
                    search_home_dataLl.setVisibility(View.VISIBLE);
                    searchEt.setText(data[j]);
                    if (!isNullorEmpty(data[j])) {
                        SpSearchUtils.getInstance(SearchActivity.this).home_save(searchEt.getText().toString());
                        getData(TYPE_REFRESH);
                    }
                    //点击事件
                }
            });
            // initautoSearch();
        }
    }
    private boolean isNullorEmpty(String str) {
        return str == null || "".equals(str);
    }

    private void getData(final int type){
        if (type == TYPE_REFRESH){
            page = 1;
            keyword = searchEt.getText().toString();
        }
        mSearchAdapter.setKeyWord(keyword,searchType);
        NetService.Companion.getInstance(getBaseContext()).search(page, keyword,searchType, new Callback<List<SearchItemBean>>() {
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
                ToastUtil.INSTANCE.suc(getBaseContext(),msg);
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
    @Override
    public void onClick(View v) {
        int id = v.getId();

        if(id==R.id.tv_clean){
           finish();
        }
        if(id==R.id.iv_search){
            searchEt.setText(null);
        }
        if(id==R.id.tv_user){

            searchType=TYPE_SEARCH_USER;
            userTv.setTextColor(Color.parseColor("#FFFFFF"));
            homeTv.setTextColor(Color.parseColor("#7FFFFFFF"));
           userLineTv.setVisibility(View.VISIBLE);
           homeLineTv.setVisibility(View.GONE);
            getData(TYPE_REFRESH);
        }
        if(id==R.id.tv_home){
            searchType=TYPE_SEARCH_ROOM;
            userTv.setTextColor(Color.parseColor("#7FFFFFFF"));
            homeTv.setTextColor(Color.parseColor("#FFFFFF"));
            userLineTv.setVisibility(View.GONE);
            homeLineTv.setVisibility(View.VISIBLE);
            getData(TYPE_REFRESH);
        }
        if(id==R.id.iv_del){
            SpSearchUtils.getInstance(SearchActivity.this).home_cleanHistory();
            initHistory();
        }
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
