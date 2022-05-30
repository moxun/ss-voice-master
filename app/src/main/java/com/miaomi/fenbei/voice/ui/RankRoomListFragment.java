package com.miaomi.fenbei.voice.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.android.arouter.launcher.ARouter;
import com.miaomi.fenbei.base.bean.RankRoomBean;
import com.miaomi.fenbei.base.core.BaseLazyFragment;
import com.miaomi.fenbei.base.net.Callback;
import com.miaomi.fenbei.base.net.Data;
import com.miaomi.fenbei.base.net.NetService;
import com.miaomi.fenbei.base.util.ImgUtil;
import com.miaomi.fenbei.base.util.LoadHelper;
import com.miaomi.fenbei.base.util.RouterUrl;
import com.miaomi.fenbei.base.widget.RankRoomTopView1;
import com.miaomi.fenbei.base.widget.RankRoomTopView23;
import com.miaomi.fenbei.base.widget.xrecyclerview.XRecyclerView;
import com.miaomi.fenbei.room.ChatRoomManager;
import com.miaomi.fenbei.voice.R;


import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class RankRoomListFragment extends BaseLazyFragment implements XRecyclerView.LoadingListener {

    private final static String RANK_TYPE = "RANK_TYPE";
    private final static String DATE_TYPE = "DATE_TYPE";

    private XRecyclerView mRankListXRecyclerView;
    private List<RankRoomBean.DataBean> mRankList = new ArrayList<>();
    private RankRoomAdapter mRankAdapter;

    private int rankType;
    private int positionType;
    private LoadHelper loadHelper;
    private RankRoomTopView1 rankTopView1;
    private RankRoomTopView23 rankTopView2;
    private RankRoomTopView23 rankTopView3;
    private LinearLayout headLL;
     private TextView getonTv,roomisTv,totalTv,rechargegoTv;
     private ImageView iconIv;
     private LinearLayout  roommasonryLl;


    public static RankRoomListFragment newInstance(int type, int roomType){
        RankRoomListFragment fragment = new RankRoomListFragment();
        Bundle args = new Bundle();
        args.putInt(RANK_TYPE,type);
        args.putInt(DATE_TYPE,roomType);
        fragment.setArguments(args);
        return fragment;
    }




    @Override
    public int getLayoutId() {
        return R.layout.main_fragment_room_rank_list;
    }

    @Override
    public void initView(@NotNull View view) {
        if (getArguments() != null) {
            rankType = getArguments().getInt(RANK_TYPE,0);
            positionType = getArguments().getInt(DATE_TYPE,0);

        }
        headLL = view.findViewById(R.id.ll_head);
//        View headView = LayoutInflater.from(getMContext()).inflate(R.layout.main_rank_list_head,null,false);
//        headView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT));
        mRankAdapter = new RankRoomAdapter(mRankList,getMContext(),rankType);
        rankTopView1 = view.findViewById(R.id.rank_top_1);
        rankTopView2 = view.findViewById(R.id.rank_top_2);
        rankTopView3 = view.findViewById(R.id.rank_top_3);
        getonTv= view.findViewById(R.id.tv_get_on);
        iconIv= view.findViewById(R.id.iv_icon);
        roomisTv=view.findViewById(R.id.tv_room_is);
        totalTv= view.findViewById(R.id.tv_earning_total);
        rechargegoTv=view.findViewById(R.id.tv_go_recharge);
        roommasonryLl=view.findViewById(R.id.ll_room_masonry);
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

        if (rankType == RankTypeFragment.RANK_TYPE_ROOM){
            if (positionType == RankTypeFragment.TIME_TYPE_DAY){
                return 11; //房间日榜
            }
            if (positionType == RankTypeFragment.TIME_TYPE_WEEK){
                return 11; //房间周榜
            }
            if (positionType == RankTypeFragment.TIME_TYPE_ALL){
                return 12; //房间月榜
            }
        }
        return 0;
    }

    private void bindData(RankRoomBean list){
        mRankList.clear();
        rankTopView1.setVisibility(View.INVISIBLE);
        rankTopView2.setVisibility(View.INVISIBLE);
        rankTopView3.setVisibility(View.INVISIBLE);

        if (!TextUtils.isEmpty( list.getMy().getFace())) {
            ImgUtil.INSTANCE.loadFaceIcon(getContext(), list.getMy().getFace(), iconIv);
        }
        rechargegoTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build(RouterUrl.createChat)
                        .withInt("type", ChatRoomManager.ROOM_TYPE_PERSONAL).navigation(getContext());
            }
        });
        if(list.getMy().getHave_room()==1){
            getonTv.setText("未上榜");
            roomisTv.setText("您还没有房间哦");
            roommasonryLl.setVisibility(View.GONE);
            rechargegoTv.setVisibility(View.VISIBLE);
        }else{
            roommasonryLl.setVisibility(View.VISIBLE);
            rechargegoTv.setVisibility(View.GONE);
            if(list.getMy().getUp()==1){
                getonTv.setText("未上榜");
                roomisTv.setText("您的房间距上榜还差:"+list.getMy().getUp_number());
            }else{
                getonTv.setText(""+list.getMy().getId());
                roomisTv.setText("您的房间距上一名还差:"+list.getMy().getUp_number());
            }
            totalTv.setText(""+list.getMy().getEarning_total());
        }
        if (list.getData().size() >= 1){
            initTopRank(rankTopView1,list.getData().get(0));
            rankTopView1.setRankTop(1);
        }
        if (list.getData().size() >= 2){
            initTopRank(rankTopView2,list.getData().get(1));
            rankTopView2.setRankTop(2);
        }
        if (list.getData().size() >= 3){
            initTopRank(rankTopView3,list.getData().get(2));
            rankTopView3.setRankTop(3);
            mRankList.addAll(list.getData().subList(3,list.getData().size()));
        }
        mRankAdapter.notifyDataSetChanged();
    }

    private void initTopRank(RankRoomTopView1 rankTopView, RankRoomBean.DataBean bean){
        rankTopView.setVisibility(View.VISIBLE);
        rankTopView.setImg(bean.getFace());
        rankTopView.setName(bean.getNickname());
        rankTopView.setTotal(""+bean.getEarning_total());


//        rankTopView.setOnClickListener(v ->
//                ARouter.getInstance().build("/app/userhomepage")
//                .withString("user_id",bean.getUser_id()+"")
//                .withString("user_name",bean.getNickname())
//                .withString("user_face",bean.getFace())
//                .navigation());
    }

    private void initTopRank(RankRoomTopView23 rankTopView, RankRoomBean.DataBean bean){
        rankTopView.setVisibility(View.VISIBLE);
        rankTopView.setImg(bean.getFace());
        rankTopView.setName(bean.getNickname());
        rankTopView.setRankType(rankType);
        rankTopView.setTotal(""+bean.getDistance_total1());
//        if (bean.getDifference() > 0){
//            rankTopView.setContent("距上："+ bean.getDifference());
//
//        }
//        rankTopView.setOnClickListener(v -> ARouter.getInstance().build("/app/userhomepage")
//                .withString("user_id",bean.getUser_id()+"")
//                .withString("user_name",bean.getNickname())
//                .withString("user_face",bean.getFace())
//                .navigation());
    }


    private void getData(){

        NetService.Companion.getInstance(getMContext()).getRankRoomList(getType(), new Callback<RankRoomBean>() {
            @Override
            public void onSuccess(int nextPage, RankRoomBean bean, int code) {
                if (isAlive()){
                    loadHelper.bindView(Data.CODE_SUC);
                    mRankListXRecyclerView.refreshComplete();
                    List<RankRoomBean.DataBean> list = new ArrayList<>();
                    for (int i =0;i<bean.getData().size();i++){
                        if (i >= 1){
                            bean.getData().get(i).setDistance_total1(Long.valueOf(bean.getData().get(i-1).getEarning_total())-Long.valueOf(bean.getData().get(i).getEarning_total()));
                        }
                        list.add(bean.getData().get(i));
                    }
                    bindData(bean);
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
