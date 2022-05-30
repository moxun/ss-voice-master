package com.miaomi.fenbei.room.ui.fragment;

import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.miaomi.fenbei.base.widget.RankTopView23;
import com.miaomi.fenbei.room.ChatRoomManager;
import com.miaomi.fenbei.room.R;
import com.miaomi.fenbei.base.bean.RankBean;
import com.miaomi.fenbei.base.bean.RoomRankBean;
import com.miaomi.fenbei.base.bean.event.UserCardEvent;
import com.miaomi.fenbei.base.bean.UserInfo;
import com.miaomi.fenbei.base.core.BaseLazyFragment;
import com.miaomi.fenbei.base.net.NetService;
import com.miaomi.fenbei.room.ui.adapter.RoomRankAdapter;
import com.miaomi.fenbei.base.net.Callback;
import com.miaomi.fenbei.base.widget.RankTopView1;
import com.miaomi.fenbei.base.widget.xrecyclerview.XRecyclerView;

import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class RoomRankListFragment extends BaseLazyFragment implements XRecyclerView.LoadingListener {

    private final static String RANK_TYPE = "rank_type";
    private final static String DATE_TYPE = "DATE_TYPE";
    public final static int RANK_TYPE_ML = 0;
    public final static int RANK_TYPE_GX = 1;
    private final static int TIME_TYPE_DAY = 0;
    private final static int TIME_TYPE_WEEK = 1;
    private final static int TIME_TYPE_ALL = 2;
    public final static int TIME_TYPE_PRE_DAY = 3;
    public final static int TIME_TYPE_PRE_WEEK = 4;
    private XRecyclerView mRankListXRecyclerView;
    private List<RankBean> mRankList = new ArrayList<>();
    private RoomRankAdapter mRankAdapter;

    private int rankType;
    private RankTopView1 rankTopView1;
    private RankTopView23 rankTopView2;
    private RankTopView23 rankTopView3;
    private int dateType;
    private TextView totalTv;
    private ImageView bottomRankIv;
//    private FrameLayout headLL;



    public static RoomRankListFragment newInstance(int type,int dateType){
        RoomRankListFragment fragment = new RoomRankListFragment();
        Bundle args = new Bundle();
        args.putInt(RANK_TYPE,type);
        args.putInt(DATE_TYPE,dateType);
        fragment.setArguments(args);
        return fragment;
    }




    @Override
    public int getLayoutId() {
        return R.layout.room_fragment_room_rank_list;
    }

    @Override
    public void initView(@NotNull View view) {
        rankType = getArguments().getInt(RANK_TYPE,0);
        dateType = getArguments().getInt(DATE_TYPE,0);
//        View headView = LayoutInflater.from(getMContext()).inflate(R.layout.room_layout_head_rank_list,null,false);
//        headView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT));
        mRankAdapter = new RoomRankAdapter(mRankList,getMContext(),rankType);
        rankTopView1 = view.findViewById(R.id.rank_top_1);
        rankTopView2 = view.findViewById(R.id.rank_top_2);
        rankTopView3 = view.findViewById(R.id.rank_top_3);
        bottomRankIv = view.findViewById(R.id.iv_rank_bottom);
        mRankListXRecyclerView = view.findViewById(R.id.rv_rank_list);
        totalTv = view.findViewById(R.id.tv_total);
//        headLL = view.findViewById(R.id.head_ll);
        mRankListXRecyclerView.setLoadingListener(this);
        mRankListXRecyclerView.setLoadingMoreEnabled(false);
        mRankListXRecyclerView.setLayoutManager(new LinearLayoutManager(getMContext()));
        mRankListXRecyclerView.setAdapter(mRankAdapter);
//        mRankListXRecyclerView.addHeaderView(headView);
//        if (rankType == RANK_TYPE_ML){
//            headLL.setSelected(false);
//        }
//        if (rankType == RANK_TYPE_GX){
//            headLL.setSelected(true);
//        }
    }

    public int getType(){
        if (rankType == RANK_TYPE_ML){
            if (dateType == TIME_TYPE_DAY){
                return 2; //魅力日榜
            }
            if (dateType == TIME_TYPE_WEEK){
                return 1; //魅力周榜
            }
            if (dateType == TIME_TYPE_ALL){
                return 6; //魅力月榜
            }
            if (dateType == TIME_TYPE_PRE_WEEK){
                return 8; //上周榜
            }
            if (dateType == TIME_TYPE_PRE_DAY){
                return 9; //昨日月榜
            }
        }
        if (rankType == RANK_TYPE_GX){
            if (dateType == TIME_TYPE_DAY){
                return 5; //贡献日榜
            }
            if (dateType == TIME_TYPE_WEEK){
                return 4; //贡献周榜
            }
            if (dateType == TIME_TYPE_ALL){
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
            mRankList.addAll(list.subList(3,list.size()));
            rankTopView3.setRankTop(3);
        }
        mRankAdapter.notifyDataSetChanged();
    }

    private void initTopRank(RankTopView1 rankTopView, final RankBean bean){
        rankTopView.setVisibility(View.VISIBLE);
        rankTopView.setImg(bean.getFace());
        rankTopView.setName(bean.getNickname());
        rankTopView.setRankType(rankType);
        if (rankType == RANK_TYPE_ML){
            bottomRankIv.setSelected(false);
            rankTopView.setCharmLevel(bean.getCharm_level().getGrade());
        }else{
            bottomRankIv.setSelected(true);
            rankTopView.setWealthLevel(bean.getWealth_level().getGrade());
        }
        rankTopView.setSex(bean.getGender());
        rankTopView.setContent(bean.getEarning_total());
        rankTopView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserInfo userInfo = new UserInfo();
                userInfo.setUser_id(bean.getUser_id());
                EventBus.getDefault().post(new UserCardEvent(userInfo));
            }
        });
    }

    private void initTopRank(RankTopView23 rankTopView, final RankBean bean){
        rankTopView.setVisibility(View.VISIBLE);
        rankTopView.setImg(bean.getFace());
        rankTopView.setName(bean.getNickname());
        rankTopView.setRankType(rankType);
        if (rankType == RANK_TYPE_ML){
            bottomRankIv.setSelected(false);
            rankTopView.setCharmLevel(bean.getCharm_level().getGrade());
        }else{
            rankTopView.setWealthLevel(bean.getWealth_level().getGrade());
            bottomRankIv.setSelected(true);
        }
        rankTopView.setSex(bean.getGender());
        rankTopView.setContent(bean.getEarning_total());
        rankTopView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserInfo userInfo = new UserInfo();
                userInfo.setUser_id(bean.getUser_id());
                EventBus.getDefault().post(new UserCardEvent(userInfo));
            }
        });
    }


    private void getData(){
        String timeType = "";
        String rankTypeStr = "";
        if (rankType == RANK_TYPE_ML){
            rankTypeStr = "魅力总值:";
        }else{
            rankTypeStr = "财富总值:";
        }
        if (dateType == TIME_TYPE_DAY){
            timeType = "日榜";
        }
        if (dateType == TIME_TYPE_WEEK){
            timeType = "周榜";
        }
        if (dateType == TIME_TYPE_ALL){
            timeType = "月榜";
        }
        if (dateType == TIME_TYPE_PRE_DAY){
            timeType = "昨日";
        }
        if (dateType == TIME_TYPE_PRE_WEEK){
            timeType = "上周";
        }
        final String finalTimeType = timeType;
        final String finalRankTypeStr = rankTypeStr;
        NetService.Companion.getInstance(getMContext()).getRoomRankList(ChatRoomManager.INSTANCE.getRoomId(),getType(), new Callback<RoomRankBean>() {
            @Override
            public void onSuccess(int nextPage, RoomRankBean bean, int code) {
                if (isAlive()){
                    mRankListXRecyclerView.refreshComplete();
                    if (dateType != TIME_TYPE_ALL){
                        totalTv.setText(finalTimeType + finalRankTypeStr  + bean.getTotal());
                    }else{
                        totalTv.setText("");

                    }
                    bindData(bean.getList());
                }
            }

            @Override
            public void onError(@NotNull String msg, @NotNull Throwable throwable, int code) {
                mRankListXRecyclerView.refreshComplete();
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