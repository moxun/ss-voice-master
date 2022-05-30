package com.miaomi.fenbei.voice.ui.main.fragment.rank;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.alibaba.android.arouter.launcher.ARouter;
import com.miaomi.fenbei.base.bean.RankBean;
import com.miaomi.fenbei.base.core.BaseLazyFragment;
import com.miaomi.fenbei.base.net.Callback;
import com.miaomi.fenbei.base.net.Data;
import com.miaomi.fenbei.base.net.NetService;
import com.miaomi.fenbei.base.util.LoadHelper;
import com.miaomi.fenbei.base.widget.RankTopView1;
import com.miaomi.fenbei.base.widget.RankTopView23;
import com.miaomi.fenbei.base.widget.xrecyclerview.XRecyclerView;
import com.miaomi.fenbei.voice.R;
import com.miaomi.fenbei.voice.ui.RankTypeFragment;
import com.miaomi.fenbei.voice.ui.main.adapter.RankAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;

public class RankListFragment extends BaseLazyFragment implements XRecyclerView.LoadingListener {

    private final static String RANK_TYPE = "RANK_TYPE";
    private final static String DATE_TYPE = "DATE_TYPE";

    private XRecyclerView mRankListXRecyclerView;
    private List<RankBean> mRankList = new ArrayList<>();
    private RankAdapter mRankAdapter;

    private int rankType;
    private int positionType;
    private LoadHelper loadHelper;
    private RankTopView1 rankTopView1;
    private RankTopView23 rankTopView2;
    private RankTopView23 rankTopView3;
    private LinearLayout headLL;
    private ImageView bottomIv;



    public static RankListFragment newInstance(int type,int roomType){
        RankListFragment fragment = new RankListFragment();
        Bundle args = new Bundle();
        args.putInt(RANK_TYPE,type);
        args.putInt(DATE_TYPE,roomType);
        fragment.setArguments(args);
        return fragment;
    }




    @Override
    public int getLayoutId() {
        return R.layout.main_fragment_rank_list;
    }

    @Override
    public void initView(@NotNull View view) {
        if (getArguments() != null) {
            rankType = getArguments().getInt(RANK_TYPE,0);
            positionType = getArguments().getInt(DATE_TYPE,0);

        }
        bottomIv = view.findViewById(R.id.iv_rank_bottom);
        headLL = view.findViewById(R.id.ll_head);
//        View headView = LayoutInflater.from(getMContext()).inflate(R.layout.main_rank_list_head,null,false);
//        headView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT));
        mRankAdapter = new RankAdapter(mRankList,getMContext(),rankType);
        rankTopView1 = view.findViewById(R.id.rank_top_1);
        rankTopView2 = view.findViewById(R.id.rank_top_2);
        rankTopView3 = view.findViewById(R.id.rank_top_3);
        mRankListXRecyclerView = view.findViewById(R.id.rv_rank_list);
        mRankListXRecyclerView.setLoadingListener(this);
        mRankListXRecyclerView.setPullRefreshEnabled(true);
        mRankListXRecyclerView.setLoadingMoreEnabled(false);
        mRankListXRecyclerView.setLayoutManager(new LinearLayoutManager(getMContext()));
        mRankListXRecyclerView.setAdapter(mRankAdapter);
//        mRankListXRecyclerView.addHeaderView(headView);
        loadHelper = new LoadHelper();
        loadHelper.registerLoad(mRankListXRecyclerView);
        headLL.setSelected(rankType == RankTypeFragment.RANK_TYPE_GX);

    }



    public int getType(){
        if (rankType == RankTypeFragment.RANK_TYPE_ML){
            if (positionType == RankTypeFragment.TIME_TYPE_DAY){
                return 4; //魅力日榜
            }
            if (positionType == RankTypeFragment.TIME_TYPE_WEEK){
                return 1; //魅力周榜
            }
            if (positionType == RankTypeFragment.TIME_TYPE_ALL){
                return 6; //魅力月榜
            }
        }
        if (rankType == RankTypeFragment.RANK_TYPE_GX){
            if (positionType == RankTypeFragment.TIME_TYPE_DAY){
                return 5; //贡献日榜
            }
            if (positionType == RankTypeFragment.TIME_TYPE_WEEK){
                return 3; //贡献周榜
            }
            if (positionType == RankTypeFragment.TIME_TYPE_ALL){
                return 7; //贡献月榜
            }
        }
        return 0;
    }

    private void bindData(List<RankBean> list){
        mRankList.clear();
        rankTopView1.setVisibility(View.INVISIBLE);
        rankTopView2.setVisibility(View.INVISIBLE);
        rankTopView3.setVisibility(View.INVISIBLE);
        if (list.size() >= 1){
            initTopRank(rankTopView1,list.get(0));
            rankTopView1.setRankTop(1);
        }
        if (list.size() >= 2){
            initTopRank(rankTopView2,list.get(1));
            rankTopView2.setRankTop(2);
        }
        if (list.size() >= 3){
            initTopRank(rankTopView3,list.get(2));
            rankTopView3.setRankTop(3);
            mRankList.addAll(list.subList(3,list.size()));
        }
        mRankAdapter.notifyDataSetChanged();
    }

    private void initTopRank(RankTopView1 rankTopView, RankBean bean){
        rankTopView.setVisibility(View.VISIBLE);
        rankTopView.setImg(bean.getFace());
        rankTopView.setName(bean.getNickname());
        rankTopView.setRankType(rankType);
        if (rankType == RankTypeFragment.RANK_TYPE_ML){
            rankTopView.setCharmLevel(bean.getCharm_level().getGrade());
            bottomIv.setSelected(false);
        }else{
            rankTopView.setWealthLevel(bean.getWealth_level().getGrade());
            bottomIv.setSelected(true);
        }
        rankTopView.setSex(bean.getGender());
        if (bean.getDistance_total1() > 0){
            rankTopView.setContent(String.valueOf(bean.getDistance_total1()));

        }
        rankTopView.setOnClickListener(v -> ARouter.getInstance().build("/app/userhomepage")
                .withString("user_id",bean.getUser_id()+"")
                .withString("user_name",bean.getNickname())
                .withString("user_face",bean.getFace())
                .navigation());
    }

    private void initTopRank(RankTopView23 rankTopView, RankBean bean){
        rankTopView.setVisibility(View.VISIBLE);
        rankTopView.setImg(bean.getFace());
        rankTopView.setName(bean.getNickname());
        rankTopView.setRankType(rankType);
        if (rankType == RankTypeFragment.RANK_TYPE_ML){
            rankTopView.setCharmLevel(bean.getCharm_level().getGrade());
        }else{
            rankTopView.setWealthLevel(bean.getWealth_level().getGrade());
        }
        rankTopView.setSex(bean.getGender());
        if (bean.getDistance_total1() > 0){
            rankTopView.setContent("距上："+ bean.getDistance_total1());

        }
        rankTopView.setOnClickListener(v -> ARouter.getInstance().build("/app/userhomepage")
                .withString("user_id",bean.getUser_id()+"")
                .withString("user_name",bean.getNickname())
                .withString("user_face",bean.getFace())
                .navigation());
    }


    private void getData(){
        NetService.Companion.getInstance(getMContext()).getRankList(getType(), new Callback<List<RankBean>>() {
            @Override
            public void onSuccess(int nextPage, List<RankBean> bean, int code) {
                if (isAlive()){
                    loadHelper.bindView(Data.CODE_SUC);
                    mRankListXRecyclerView.refreshComplete();
                    List<RankBean> list = new ArrayList<>();
                    for (int i =0;i<bean.size();i++){
                        if (i >= 1){
                            bean.get(i).setDistance_total1(Integer.valueOf(bean.get(i-1).getEarning_total().substring(4))-Integer.valueOf(bean.get(i).getEarning_total().substring(4)));
                        }
                        list.add(bean.get(i));
                    }
                    bindData(list);
                }
            }

            @Override
            public void onError(@NotNull String msg, @NotNull Throwable throwable, int code) {
                mRankListXRecyclerView.refreshComplete();
                loadHelper.setErrorCallback(code, v -> getData());
            }

            @Override
            public boolean isAlive() {
                return isLive();
            }
        });

    }


    @Override
    public void onRefresh() {
        getData();
    }

    @Override
    public void onLoadMore() {
        getData();
    }

    @Override
    public void loadData() {
        getData();
    }
}
