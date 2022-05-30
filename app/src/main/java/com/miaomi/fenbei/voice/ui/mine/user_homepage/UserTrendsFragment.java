package com.miaomi.fenbei.voice.ui.mine.user_homepage;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.miaomi.fenbei.base.bean.BaseBean;
import com.miaomi.fenbei.base.bean.FindBean;
import com.miaomi.fenbei.base.bean.FollowBean;
import com.miaomi.fenbei.base.bean.HeartBean;
import com.miaomi.fenbei.base.core.BaseFragment;
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
import com.miaomi.fenbei.base.widget.HeartView;
import com.miaomi.fenbei.imkit.ui.PrivateChatActivity;
import com.miaomi.fenbei.voice.R;
import com.miaomi.fenbei.voice.dialog.UserOperateDialog;
import com.miaomi.fenbei.voice.ui.pyq.FindChildAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.tencent.imsdk.TIMMessage;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class UserTrendsFragment extends BaseFragment {

    private RecyclerView rvGift;
//    private AllGiftWallAdapter giftAdapter;
    private FindChildAdapter findChildAdapter;
    private int mGiftWallPage = 1;
    private SmartRefreshLayout mSmartRefreshLayout;
    private String userId = "";
    private LoadHelper loadHelper;

    @Override
    public int getLayoutId() {
        return R.layout.base_fragment_list;
    }

    public static UserTrendsFragment newInstance(String userId){
        UserTrendsFragment fragment = new UserTrendsFragment();
        Bundle bundle = new Bundle();
        bundle.putString("user_id",userId);
        fragment.setArguments(bundle);
        return fragment;
    }


    private void getGiftWall(int type){
        if (type == getTYPE_REFRESH()){
            mGiftWallPage = 1;
        }

        NetService.Companion.getInstance(getContext()).getFriendsCircleListByUid(userId,mGiftWallPage, new Callback<List<FindBean>>() {
            @Override
            public void onSuccess(int nextPage, List<FindBean> list, int code) {
                if (type == getTYPE_REFRESH()) {
                    if (list.size() > 0){
                        loadHelper.bindView(code);
                    }else{
                        loadHelper.bindView(Data.CODE_EMPTY);
                    }
                    mSmartRefreshLayout.finishRefresh();
                    findChildAdapter.setNewData(list);
                } else {
                    mSmartRefreshLayout.finishLoadMore();
                    findChildAdapter.addData(list);
                }
                mGiftWallPage++;
            }

            @Override
            public void noMore() {
                super.noMore();
                mSmartRefreshLayout.finishLoadMoreWithNoMoreData();
            }

            @Override
            public void onError(@NotNull String msg, @NotNull Throwable throwable, int code) {

                if (type != getTYPE_REFRESH()){
                    mSmartRefreshLayout.finishLoadMore(false);
                    ToastUtil.INSTANCE.suc(getContext(), msg);
                }else{
                    loadHelper.bindView(code);
                    mSmartRefreshLayout.finishRefresh(false);
                }
            }

            @Override
            public boolean isAlive() {
                return isLive();
            }
        });
//        NetService.Companion.getInstance(getContext()).getGiftWall(mGiftWallPage,userId, new Callback<GiftWallBean>() {
//            @Override
//            public void onSuccess(int nextPage, GiftWallBean bean, int code) {
//                if (type == getTYPE_REFRESH()){
////                    rvGift.refreshComplete();
//                    giftAdapter.setData(bean.getList());
//                }else{
////                    rvGift.loadMoreComplete();
//                    giftAdapter.addData(bean.getList());
//                }
//                mGiftWallPage++;
//
//            }
//
//            @Override
//            public void noMore() {
//                super.noMore();
////                rvGift.setNoMore(true);
//            }
//
//            @Override
//            public void onError(@NotNull String msg, @NotNull Throwable throwable, int code) {
//                ToastUtil.INSTANCE.suc(getContext(),msg);
//            }
//
//            @Override
//            public boolean isAlive() {
//                return isLive();
//            }
//        });
    }

    @Override
    public void initView(@NotNull View view) {
        userId = getArguments().getString("user_id");
        mSmartRefreshLayout = view.findViewById(R.id.refresh_layout);
        rvGift =  view.findViewById(R.id.rv_gift);
        findChildAdapter = new FindChildAdapter();
        rvGift.setLayoutManager(new LinearLayoutManager(getContext()));
        rvGift.setAdapter(findChildAdapter);
        loadHelper = new LoadHelper();
        loadHelper.registerLoad(rvGift);
//        findChildAdapter.setOnFindItemOprateListener(new FindChildAdapter.OnFindItemOprateListener() {
//            @Override
//            public void onSendGift(String uid) {
//
//            }
//
//            @Override
//            public void onTalk(String uid, String face) {
//
//            }
//
//            @Override
//            public void onHeart(FindBean item, HeartMeView heartMeView, HeartView heartView, TextView heartNum) {
//                NetService.Companion.getInstance(getContext()).heartPYQ(item.getId(), new Callback<HeartBean>() {
//                    @Override
//                    public void onSuccess(int nextPage, HeartBean bean, int code) {
////                        List<String> list = item.getHearts();
////                        list.add(DataHelper.INSTANCE.getUserInfo().getFace());
////                        heartNum.setVisibility(View.VISIBLE);
////                        heartMeView.setVisibility(View.VISIBLE);
////                        heartMeView.setContent(item.getHearts());
////                        heartNum.setText((item.getHeart_num() + 1) +"人赞了TA");
////                        heartView.success();
//                        if (bean.isHeart_status()){
//                            heartNum.setVisibility(View.VISIBLE);
//                            heartMeView.setVisibility(View.VISIBLE);
//                            heartMeView.setContent(bean.getHearts());
//                            heartNum.setText((item.getHeart_num() + 1) +"人赞了TA");
//                            item.setHeart_num(item.getHeart_num() + 1);
//                            heartView.success();
//                        }else{
//                            if (item.getHeart_num() > 0){
//                                heartNum.setVisibility(View.VISIBLE);
//                                heartMeView.setVisibility(View.VISIBLE);
//                                heartMeView.setContent(bean.getHearts());
//                                heartNum.setText((item.getHeart_num() - 1) +"人赞了TA");
//                                item.setHeart_num(item.getHeart_num() - 1);
//                                heartView.faile();
//                            }
//                        }
//                    }
//
//                    @Override
//                    public void onSuccessHasMsg(@NotNull String msg, HeartBean bean, int code) {
//                        super.onSuccessHasMsg(msg, bean, code);
//                        ToastUtil.INSTANCE.suc(getContext(), msg);
//                    }
//
//                    @Override
//                    public void onError(@NotNull String msg, @NotNull Throwable throwable, int code) {
//                        ToastUtil.INSTANCE.suc(getContext(), msg);
//                    }
//
//                    @Override
//                    public boolean isAlive() {
//                        return isLive();
//                    }
//                });
//            }
//
//            @Override
//            public void onCare(String uid) {
//
//            }
//
//            @Override
//            public void onDelate(String id) {
//                CommonDialog upDialog = new CommonDialog(getContext());
//                upDialog.setTitle("提示");
//                upDialog.setContent("真的要删除这条动态吗？");
//                upDialog.setLeftBt("取消", v -> upDialog.dismiss());
//                upDialog.setRightBt("确认", v -> {
//                    upDialog.dismiss();
//                    NetService.Companion.getInstance(getContext()).delFriendsCircle(id, new Callback<BaseBean>() {
//                        @Override
//                        public void onSuccess(int nextPage, BaseBean bean, int code) {
////                        List<String> list = item.getHearts();
////                        list.add(DataHelper.INSTANCE.getUserInfo().getFace());
////                        heartNum.setVisibility(View.VISIBLE);
////                        heartMeView.setVisibility(View.VISIBLE);
////                        heartMeView.setContent(item.getHearts());
////                        heartNum.setText((item.getHeart_num() + 1) +"人赞了TA");
////                        heartView.success();
//                            getGiftWall(getTYPE_REFRESH());
//                        }
//
//                        @Override
//                        public void onSuccessHasMsg(@NotNull String msg, BaseBean bean, int code) {
//                            super.onSuccessHasMsg(msg, bean, code);
//                            ToastUtil.INSTANCE.suc(getContext(), msg);
//                        }
//
//                        @Override
//                        public void onError(@NotNull String msg, @NotNull Throwable throwable, int code) {
//                            ToastUtil.INSTANCE.suc(getContext(), msg);
//                        }
//
//                        @Override
//                        public boolean isAlive() {
//                            return isLive();
//                        }
//                    });
//                });
//                upDialog.show();
//            }
//
//            @Override
//            public void onMoreOparate(String id) {
//
//            }
//        });
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
                            heartMeView.setVisibility(View.VISIBLE);
                            heartMeView.setContent(bean.getHearts());
                            heartNum.setText((item.getHeart_num() + 1) +"人赞了TA");
                            item.setHeart_num(item.getHeart_num() + 1);
                            heartView.success();
                        }else{
                            if (item.getHeart_num() > 0){
                                heartNum.setVisibility(View.VISIBLE);
                                heartMeView.setVisibility(View.VISIBLE);
                                heartMeView.setContent(bean.getHearts());
                                heartNum.setText((item.getHeart_num() - 1) +"人赞了TA");
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
                upDialog.setTitle("友情提示");
                upDialog.setContent("真的要删除这条动态吗？");
                upDialog.setLeftBt("取消", v -> upDialog.dismiss());
                upDialog.setRightBt("确认", v -> {
                    upDialog.dismiss();
                    NetService.Companion.getInstance(getContext()).delFriendsCircle(id, new Callback<BaseBean>() {
                        @Override
                        public void onSuccess(int nextPage, BaseBean bean, int code) {
//                        List<String> list = item.getHearts();
//                        list.add(DataHelper.INSTANCE.getUserInfo().getFace());
//                        heartNum.setVisibility(View.VISIBLE);
//                        heartMeView.setVisibility(View.VISIBLE);
//                        heartMeView.setContent(item.getHearts());
//                        heartNum.setText((item.getHeart_num() + 1) +"人赞了TA");
//                        heartView.success();
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
        getGiftWall(getTYPE_REFRESH());
    }

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
}
