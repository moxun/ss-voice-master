package com.miaomi.fenbei.voice.ui.pyq;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.miaomi.fenbei.base.widget.HeartView;
import com.miaomi.fenbei.voice.dialog.UserOperateDialog;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.tencent.imsdk.TIMMessage;
import com.miaomi.fenbei.base.bean.BaseBean;
import com.miaomi.fenbei.base.bean.FindBean;
import com.miaomi.fenbei.base.bean.FollowBean;
import com.miaomi.fenbei.base.bean.HeartBean;
import com.miaomi.fenbei.base.core.BaseLazyFragment;
import com.miaomi.fenbei.base.core.dialog.CommonDialog;
import com.miaomi.fenbei.base.core.msg.MsgManager;
import com.miaomi.fenbei.base.core.msg.SendMsgListener;
import com.miaomi.fenbei.base.net.Callback;
import com.miaomi.fenbei.base.net.Data;
import com.miaomi.fenbei.base.net.NetService;
import com.miaomi.fenbei.base.util.DataHelper;
import com.miaomi.fenbei.base.util.LoadHelper;
import com.miaomi.fenbei.base.util.ToastUtil;
import com.miaomi.fenbei.base.widget.HeartMeView;
import com.miaomi.fenbei.imkit.ui.PrivateChatActivity;
import com.miaomi.fenbei.voice.R;
import com.miaomi.fenbei.voice.ui.mine.user_homepage.SendPersonGiftDialog;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class FindChildFragment extends BaseLazyFragment {

    private RecyclerView childFindRv;
    private FindChildAdapter findChildAdapter;
    private int mPage = 1;
    private int type = FIND_ITEM_TYPE_GZ;
    private LoadHelper loadHelper;
    private SmartRefreshLayout mSmartRefreshLayout;

    public static final int FIND_ITEM_TYPE_GZ = 1;
    public static final int FIND_ITEM_TYPE_TJ = 2;
    public static final int FIND_ITEM_TYPE_FJ = 3;

    public static FindChildFragment newInstance(int type){
        FindChildFragment fragment = new FindChildFragment();
        Bundle args = new Bundle();
        args.putInt("type",type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragmenta_find_child;
    }


    @Override
    public void initView(@NotNull View view) {
        type = getArguments().getInt("type",FIND_ITEM_TYPE_GZ);
        mSmartRefreshLayout = view.findViewById(R.id.refresh_layout);
        findChildAdapter = new FindChildAdapter();
        findChildAdapter.setOnFindItemOprateListener(new FindChildAdapter.OnFindItemOprateListener() {
            @Override
            public void onSendGift(String uid) {
                if (DataHelper.INSTANCE.getUserInfo() == null){
                    return;
                }
                SendPersonGiftDialog dialog = new SendPersonGiftDialog(uid);
                dialog.show(getFragmentManager());
            }

            @Override
            public void onTalk(String uid,String face) {
                if (DataHelper.INSTANCE.getUserInfo() == null){
                    return;
                }
                PrivateChatActivity.startActivity(getContext(), uid, face);
            }

            @Override
            public void onHeart(FindBean item, HeartMeView heartMeView, HeartView heartView, TextView heartNum) {
                NetService.Companion.getInstance(getContext()).heartPYQ(item.getId(), new Callback<HeartBean>() {
                    @Override
                    public void onSuccess(int nextPage, HeartBean bean, int code) {
//                        List<String> list = item.getHearts();
//                        list.add(DataHelper.INSTANCE.getUserInfo().getFace());
//                        heartNum.setVisibility(View.VISIBLE);
//                        heartMeView.setVisibility(View.VISIBLE);
//                        heartMeView.setContent(item.getHearts());
//                        heartNum.setText((item.getHeart_num() + 1) +"人赞了TA");
//                        heartView.success();
                        if (bean.isHeart_status()){
                            heartNum.setVisibility(View.VISIBLE);
                            heartMeView.setVisibility(View.GONE);
//                            heartMeView.setContent(bean.getHearts());
                            heartNum.setText((item.getHeart_num() + 1) +"");
                            item.setHeart_num(item.getHeart_num() + 1);
                            heartView.success();
                        }else{
                            if (item.getHeart_num() > 0){
                                heartNum.setVisibility(View.VISIBLE);
                                heartMeView.setVisibility(View.GONE);
//                                heartMeView.setContent(bean.getHearts());
                                heartNum.setText((item.getHeart_num() - 1) +"");
                                item.setHeart_num(item.getHeart_num() - 1);
                                heartView.faile();
                            }
                        }
                    }

                    @Override
                    public void onSuccessHasMsg(@NotNull String msg, HeartBean bean, int code) {
                        super.onSuccessHasMsg(msg, bean, code);
                        ToastUtil.INSTANCE.suc(getContext(), msg);
                    }

                    @Override
                    public void onError(@NotNull String msg, @NotNull Throwable throwable, int code) {
                        ToastUtil.INSTANCE.suc(getContext(), msg);
                    }

                    @Override
                    public boolean isAlive() {
                        return isLive();
                    }
                });
            }

//            @Override
//            public void onHeart(String id) {
//                heart(id);
//            }

            @Override
            public void onCare(String uid) {
                follow(uid);

            }

            @Override
            public void onDelate(String id) {
                CommonDialog upDialog = new CommonDialog(getContext());
                upDialog.setTitle("提示");
                upDialog.setContent("真的要删除这条动态吗？");
                upDialog.setLeftBt("取消", v -> upDialog.dismiss());
                upDialog.setRightBt("确认", v -> {
                    upDialog.dismiss();
                    NetService.Companion.getInstance(getContext()).delFriendsCircle(id, new Callback<BaseBean>() {
                        @Override
                        public void onSuccess(int nextPage, BaseBean bean, int code) {
                            getFriendsCircleList(getTYPE_REFRESH());
                        }

                        @Override
                        public void onSuccessHasMsg(@NotNull String msg, BaseBean bean, int code) {
                            super.onSuccessHasMsg(msg, bean, code);
                            ToastUtil.INSTANCE.suc(getContext(), msg);
                        }

                        @Override
                        public void onError(@NotNull String msg, @NotNull Throwable throwable, int code) {
                            ToastUtil.INSTANCE.suc(getContext(), msg);
                        }

                        @Override
                        public boolean isAlive() {
                            return isLive();
                        }
                    });
                });
                upDialog.show();
            }

            @Override
            public void onMoreOparate(String id) {
                UserOperateDialog dialog = new UserOperateDialog();
                dialog.setOnUserOperateClickListener(new UserOperateDialog.OnUserOperateClickListener() {
                    @Override
                    public void onItemClick(int operateType) {
                        if (operateType == UserOperateDialog.USER_OPERATE_GZ){
                        }
                        if (operateType == UserOperateDialog.USER_OPERATE_JB){
                            ToastUtil.INSTANCE.error(getMContext(),"举报成功");
                        }
                    }
                });
                dialog.show(getFragmentManager());
            }
        });
//        findChildAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);
        childFindRv = view.findViewById(R.id.rv_find);
        childFindRv.setLayoutManager(new LinearLayoutManager(getContext()));
        childFindRv.setAdapter(findChildAdapter);
        mSmartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                getFriendsCircleList(getTYPE_REFRESH());
            }
        });
        mSmartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {

            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                getFriendsCircleList(getTYPE_LOADMROE());
            }
        });
        loadHelper = new LoadHelper();
        loadHelper.registerLoad(childFindRv);
    }


    private void getFriendsCircleList(int refreshType){
        if (refreshType == getTYPE_REFRESH()){
            mPage = 1;
        }
        NetService.Companion.getInstance(getContext()).getFriendsCircleList(type,mPage, new Callback<List<FindBean>>() {
            @Override
            public void onSuccess(int nextPage, List<FindBean> list, int code) {
                if (refreshType == getTYPE_REFRESH()) {
                    mSmartRefreshLayout.finishRefresh();
                    if (list.size() > 0){
                        loadHelper.bindView(code);

                            findChildAdapter.setNewData(list);


                    }else{
                        String content2;
                        int resId = 0;
                        if (type == FIND_ITEM_TYPE_GZ){
                            content2 = "空空如也~";
//                            resId = R.drawable.icon_find_gz_empty;
                        }else{
//                            resId = R.drawable.icon_find_tj_empty;
                            content2 = "空空如也~";
                        }
//                        loadHelper.setEmptyCallback(R.drawable.icon_find_gz_empty,"你还没有关注的人？ 还是她们偷懒没更新？\n" +
//                                " 快去推荐里面关注你喜欢的活跃用户吧～");
                        loadHelper.setEmpty2Callback("空空如也~"
                                , content2
                                , resId);
                    }
                } else {
                    mSmartRefreshLayout.finishLoadMore();
                    loadHelper.bindView(Data.CODE_SUC);
                    findChildAdapter.addData(list);
                }
                mPage++;
            }

            @Override
            public void noMore() {
                super.noMore();
                mSmartRefreshLayout.finishLoadMoreWithNoMoreData();
            }

            @Override
            public void onError(@NotNull String msg, @NotNull Throwable throwable, int code) {

                if (refreshType != getTYPE_REFRESH()){
                    mSmartRefreshLayout.finishLoadMore(false);
                    ToastUtil.INSTANCE.suc(getContext(), msg);
                }else{
                    mSmartRefreshLayout.finishRefresh(false);
                    loadHelper.setErrorCallback(code,v -> getFriendsCircleList(getTYPE_REFRESH()));
                }
            }

            @Override
            public boolean isAlive() {
                return isLive();
            }
        });
    }


//    private void getFriendsCircleList(int refreshType,SwipeRefreshLayout layout){
//        if (refreshType == getTYPE_REFRESH()){
//            mPage = 1;
//        }
//        NetService.Companion.getInstance(getContext()).getFriendsCircleList(type,mPage, new Callback<List<FindBean>>() {
//            @Override
//            public void onSuccess(int nextPage, List<FindBean> list, int code) {
//                layout.setRefreshing(false);
//                if (refreshType == getTYPE_REFRESH()) {
//                    mSmartRefreshLayout.finishRefresh();
//                    if (list.size() > 0){
//                        loadHelper.bindView(code);
//                        findChildAdapter.setNewData(list);
//                    }else{
//                        String content2;
//                        int resId = 0;
//                        if (type == FIND_ITEM_TYPE_GZ){
//                            content2 = "你还没有关注的人？ 还是她们偷懒没更新？\n" +
//                                    "快去推荐里面关注你喜欢的活跃用户吧～";
////                            resId = R.drawable.icon_find_gz_empty;
//                        }else{
////                            resId = R.drawable.icon_find_tj_empty;
//                            content2 = "还没有发布动态，快来第一个发布，成为全场最靓的仔";
//                        }
////                        loadHelper.setEmptyCallback(R.drawable.icon_find_gz_empty,"你还没有关注的人？ 还是她们偷懒没更新？\n" +
////                                " 快去推荐里面关注你喜欢的活跃用户吧～");
//                        loadHelper.setFindEmptyCallback("空空如也"
//                                , content2
//                                , resId, new FindEmptyRrfreshListener() {
//                                    @Override
//                                    public void onRefresh(SwipeRefreshLayout layout) {
//                                        getFriendsCircleList(getTYPE_REFRESH(),layout);
//                                    }
//                                });
//                    }
//                } else {
//                    mSmartRefreshLayout.finishLoadMore();
//                    loadHelper.bindView(code);
//                    findChildAdapter.addData(list);
//                }
//                mPage++;
//            }
//
//            @Override
//            public void noMore() {
//                super.noMore();
//                childFindRv.setNoMore(true);
//            }
//
//            @Override
//            public void onError(@NotNull String msg, @NotNull Throwable throwable, int code) {
//                layout.setRefreshing(false);
//                childFindRv.refreshComplete();
//                if (refreshType != getTYPE_REFRESH()){
//                    ToastUtil.INSTANCE.suc(getContext(), msg);
//                }else{
//                    loadHelper.setErrorCallback(code,v -> getFriendsCircleList(getTYPE_REFRESH()));
//                }
//            }
//
//            @Override
//            public boolean isAlive() {
//                return isLive();
//            }
//        });
//    }

    private void follow(String uid) {
        NetService.Companion.getInstance(getContext()).addConcern(DataHelper.INSTANCE.getLoginToken(), uid, new Callback<FollowBean>() {
            @Override
            public void onSuccess(int nextPage, FollowBean bean, int code) {
                //                0 未关注 1已关注
                if (bean.getIs_follow() == 1) {
                    ToastUtil.INSTANCE.suc(getContext(), "关注成功");
                    MsgManager.INSTANCE.sendFollowMsg(uid, new SendMsgListener() {
                        @Override
                        public void onSendSuc(TIMMessage timMessage) {

                        }

                        @Override
                        public void onSendFail(int i, String s) {

                        }
                    });
                }
            }

            @Override
            public void onError(@NotNull String msg, @NotNull Throwable throwable, int code) {
                ToastUtil.INSTANCE.suc(getContext(), msg);
            }

            @Override
            public boolean isAlive() {
                return isLive();
            }
        });
    }


//    private void heart(String id) {
//        NetService.Companion.getInstance(getContext()).heartPYQ(id, new Callback<BaseBean>() {
//            @Override
//            public void onSuccess(int nextPage, BaseBean bean, int code) {
//                //                0 未关注 1已关注
//                ToastUtil.INSTANCE.suc(getContext(), "点赞成功");
//            }
//
//            @Override
//            public void onError(@NotNull String msg, @NotNull Throwable throwable, int code) {
//                ToastUtil.INSTANCE.suc(getContext(), msg);
//            }
//
//            @Override
//            public boolean isAlive() {
//                return isLive();
//            }
//        });
//    }

    @Override
    public void loadData() {
        getFriendsCircleList(getTYPE_REFRESH());
    }

    @Override
    public void onStart() {
        super.onStart();

        getFriendsCircleList(getTYPE_REFRESH());
    }
}
