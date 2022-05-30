package com.miaomi.fenbei.voice.ui.main.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.miaomi.fenbei.base.bean.MakeFriendBean;
import com.miaomi.fenbei.base.bean.RecommandUserBean;
import com.miaomi.fenbei.base.core.BaseFragment;
import com.miaomi.fenbei.base.core.JoinChatCallBack;
import com.miaomi.fenbei.base.net.Callback;
import com.miaomi.fenbei.base.net.NetService;
import com.miaomi.fenbei.base.util.ImgUtil;
import com.miaomi.fenbei.base.util.LoadHelper;
import com.miaomi.fenbei.base.util.ToastUtil;
import com.miaomi.fenbei.base.widget.DanMuViewGroup;
import com.miaomi.fenbei.room.ChatRoomManager;
import com.miaomi.fenbei.voice.R;
import com.miaomi.fenbei.voice.ui.ExpressRecordActivity;
import com.miaomi.fenbei.voice.ui.main.MatchUserActivity;
import com.miaomi.fenbei.voice.ui.main.adapter.PlayingUserAdapter;
import com.miaomi.fenbei.voice.ui.pyq.PYQActivity;
import com.miaomi.fenbei.voice.ui.search.SearchActivity;
import com.miaomi.fenbei.voice.ui.square.MakingFriendsActivity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class PlayingUserFragment extends BaseFragment {
    private PlayingUserAdapter adapter;
    private RecyclerView userRv;
    private LoadHelper loadHelper;
    private ImageView pyqLL;
    private int mPage = 0;
    private SmartRefreshLayout mSmartREfreshLayout;
    private FrameLayout searchFl;
    private ImageView matchTalkLL;
    private ImageView matchRoomLL;
    private ImageView matchPressLL;
    private DanMuViewGroup danMuViewGroup;
    private ImageView topMsgHeadIv;
    private ImageView topMsgHeadSeatIv;
    private TextView topMsgNickName;

    public static PlayingUserFragment newInstance() {
        return new PlayingUserFragment();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_playinguser;
    }

    @Override
    public void initView(@NotNull View view) {
        userRv = view.findViewById(R.id.x_reclclerview);
        mSmartREfreshLayout = view.findViewById(R.id.refresh_layout);
        View headView = LayoutInflater.from(getContext()).inflate(R.layout.head_playing_user,null);
        danMuViewGroup = headView.findViewById(R.id.dmView);
        matchTalkLL = headView.findViewById(R.id.ll_macth_talk);
        topMsgHeadIv = headView.findViewById(R.id.header_iv);
        topMsgHeadSeatIv = headView.findViewById(R.id.header_iv_seat);
        topMsgNickName = headView.findViewById(R.id.top_msg_user_nick_tv);
        pyqLL = headView.findViewById(R.id.ll_pyq);
        matchRoomLL = headView.findViewById(R.id.ll_macth_room);
        matchPressLL = headView.findViewById(R.id.ll_macth_express);
        searchFl = view.findViewById(R.id.fl_search);
        adapter = new PlayingUserAdapter();
        adapter.setHeaderView(headView);
        adapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);
        userRv.setLayoutManager(new LinearLayoutManager(getContext()));
        userRv.setAdapter(adapter);
        loadHelper = new LoadHelper();
        loadHelper.registerLoad(userRv);
        getData(getTYPE_REFRESH());
        matchPressLL.setOnClickListener(v -> ExpressRecordActivity.start(getActivity()));
        matchTalkLL.setOnClickListener(v -> {
//            if (DataHelper.INSTANCE.getUserInfo() != null){
//                getRecommandUser();
//            }
            MatchUserActivity.start(Objects.requireNonNull(getActivity()));
        });
        matchRoomLL.setOnClickListener(v -> getRecommandRoom());
        pyqLL.setOnClickListener(v -> PYQActivity.start(getActivity()));
        searchFl.setOnClickListener(v -> SearchActivity.start(getActivity()));
        mSmartREfreshLayout.setOnRefreshListener(refreshLayout -> getData(getTYPE_REFRESH()));
        mSmartREfreshLayout.setOnLoadMoreListener(refreshLayout -> getData(getTYPE_LOADMROE()));
        danMuViewGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(MakingFriendsActivity.Companion.getIntent(getContext()));
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        danMuViewGroup.resumeAnim();
    }

    @Override
    public void onPause() {
        super.onPause();
        danMuViewGroup.pauseAnim();
    }

    private void getData(int type){
        if (type == getTYPE_REFRESH()){
            mPage = 0;
            NetService.Companion.getInstance(getContext()).getMakeFriengMsgList(new Callback<List<MakeFriendBean>>() {
                @Override
                public void onSuccess(int nextPage, List<MakeFriendBean> bean, int code) {
                    danMuViewGroup.clear();
                    for (int i =0; i < bean.size();i++){
                        danMuViewGroup.addView(bean.get(i).getFace(),bean.get(i).getContent());
                    }
                    danMuViewGroup.startAnim();
                }

                @Override
                public void onError(@NotNull String msg, @NotNull Throwable throwable, int code) {

                }

                @Override
                public boolean isAlive() {
                    return isLive();
                }
            });

            NetService.Companion.getInstance(getContext()).getSquareHeadInfo(new Callback<MakeFriendBean>() {
                @Override
                public void onSuccess(int nextPage, MakeFriendBean bean, int code) {
                    ImgUtil.INSTANCE.loadFaceIcon(Objects.requireNonNull(getContext()),bean.getFace(),topMsgHeadIv);
                    topMsgHeadSeatIv.setSelected(true);
                    topMsgNickName.setText(bean.getNickname());
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
        NetService.Companion.getInstance(getContext()).getRecommandUsers(mPage,new Callback<RecommandUserBean>() {
            @Override
            public void onSuccess(int nextPage, RecommandUserBean bean, int code) {
                if (type == getTYPE_REFRESH()){
                    if (bean.getUsers().size() == 0){
                        loadHelper.setEmptyCallback(R.drawable.common_empty_bg, "空空如也");
                    }else{
                        loadHelper.bindView(code);
                    }
                    mSmartREfreshLayout.finishRefresh();
                    adapter.setNewData(bean.getUsers());
                }else{
                    mSmartREfreshLayout.finishLoadMore();
                    adapter.addData(bean.getUsers());
                }
                if (bean.getOffset() == 0){
                    noMore();
                }
                mPage = bean.getOffset();
            }

            @Override
            public void noMore() {
                super.noMore();
                mSmartREfreshLayout.setNoMoreData(true);
            }

            @Override
            public void onError(@NotNull String msg, @NotNull Throwable throwable, int code) {
                if (type == getTYPE_REFRESH()){
                    mSmartREfreshLayout.finishRefresh(false);
//                    loadHelper.setErrorCallback(code, v -> getData(getTYPE_REFRESH()));
                }else{
                    mSmartREfreshLayout.finishLoadMore(false);
                    ToastUtil.INSTANCE.error(getContext(),msg);
                }
            }

            @Override
            public boolean isAlive() {
                return isLive();
            }
        });

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
