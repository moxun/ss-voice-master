package com.miaomi.fenbei.voice.ui.main.fragment;

import android.text.TextUtils;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.miaomi.fenbei.base.bean.FamilyIdBean;
import com.miaomi.fenbei.base.bean.MineBean;
import com.miaomi.fenbei.base.config.BaseConfig;
import com.miaomi.fenbei.base.core.BaseFragment;
import com.miaomi.fenbei.base.core.JoinChatCallBack;

import com.miaomi.fenbei.base.net.Callback;
import com.miaomi.fenbei.base.net.NetService;
import com.miaomi.fenbei.base.util.CopyUtil;
import com.miaomi.fenbei.base.util.DataHelper;
import com.miaomi.fenbei.base.util.ImgUtil;
import com.miaomi.fenbei.base.util.RouterUrl;
import com.miaomi.fenbei.base.util.TimeUtil;
import com.miaomi.fenbei.base.util.ToastUtil;
import com.miaomi.fenbei.base.web.WebActivity;
import com.miaomi.fenbei.base.widget.LevelView;
import com.miaomi.fenbei.imkit.ui.PrivateChatActivity;
import com.miaomi.fenbei.room.ChatRoomManager;
import com.miaomi.fenbei.voice.R;
import com.miaomi.fenbei.voice.ui.BlackListActivity;

import com.miaomi.fenbei.voice.ui.GiftHistoryActivity;

import com.miaomi.fenbei.voice.ui.dress.MyDressActivity;
import com.miaomi.fenbei.voice.ui.dress.ShoppingMallActivity;
import com.miaomi.fenbei.voice.ui.main.AllGiftActivity;
import com.miaomi.fenbei.voice.ui.main.FollowRoomActivity;
import com.miaomi.fenbei.voice.ui.mine.CallOnActivity;
import com.miaomi.fenbei.voice.ui.mine.adolescent.AdolescentModelDialog;
import com.miaomi.fenbei.voice.ui.mine.adolescent.AdolescentModelInputDialog;
import com.miaomi.fenbei.voice.ui.mine.feedback.FeedbackActivity;
import com.miaomi.fenbei.voice.ui.mine.level.NewLevelActivity;
import com.miaomi.fenbei.voice.ui.mine.redpack.RedPacketRecordActivity;
import com.miaomi.fenbei.voice.ui.mine.user_homepage.UserHomepageActivity;
import com.miaomi.fenbei.voice.ui.noble.NobleCenterActivity;
import com.miaomi.fenbei.voice.ui.pay.PaymengActivity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import org.jetbrains.annotations.NotNull;

import java.util.Date;
import java.util.Objects;


public class MineFragment extends BaseFragment implements View.OnClickListener {

    //    private TextView nobleCenterTv;
    private ImageView ivUserIcon;
    private TextView tvUserId;
    private TextView tvUserName;
    private MineBean mBean;
    private ImageView liangIv;
    private SmartRefreshLayout mSwipeRefreshLayout;
    private boolean isShowYoungModelDialog = false;
    //    private TextView numKBTv;
//    private TextView numKDTv;
    private TextView numFansTv;
    private TextView numFollowTv;
    private TextView visitNumTv;
    private LevelView meiliIv;
    private LevelView gongxianIv;
    private TextView signTv;



    public static MineFragment newInstance() {
        return new MineFragment();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_mine;
    }


    @Override
    public void initView(@NotNull View view) {
        liangIv = view.findViewById(R.id.iv_liang);
        TextView authenticationTv = view.findViewById(R.id.authentication_tv);
        mSwipeRefreshLayout = view.findViewById(R.id.refresh_layout);
        ivUserIcon = view.findViewById(R.id.iv_user_icon);
        visitNumTv = view.findViewById(R.id.tv_visit_count);
        LinearLayout nobleTv = view.findViewById(R.id.ll_noble);
        signTv = view.findViewById(R.id.tv_sign);
        meiliIv = view.findViewById(R.id.iv_meili);
        gongxianIv = view.findViewById(R.id.iv_gongxian);
//        tvMyProfit = view.findViewById(R.id.tv_my_profit);

        TextView tvMyDiamond = view.findViewById(R.id.tv_my_diamond);
        tvUserName = view.findViewById(R.id.tv_user_name);
        tvUserId = view.findViewById(R.id.tv_user_id);
        LinearLayout callOnLL = view.findViewById(R.id.ll_call_on);
        TextView youngModelTv = view.findViewById(R.id.young_model_tv);
//        tvSignIn = view.findViewById(R.id.sign_in_tv);
//        nobleCenterTv = view.findViewById(R.id.iv_noble_center);
//        openBroadcastingStationTv = view.findViewById(R.id.open_broadcasting_station_tv);

        LinearLayout rlUserInformation = view.findViewById(R.id.rl_user_information);
        view.findViewById(R.id.tv_level_charm).setOnClickListener(this);
        view.findViewById(R.id.tv_level_weath).setOnClickListener(this);
        view.findViewById(R.id.tv_follow_room).setOnClickListener(this);
        view.findViewById(R.id.iv_first_luxury).setOnClickListener(this);
//        view.findViewById(R.id.tv_follow_room).setOnClickListener(this);
//        view.findViewById(R.id.tv_user_center).setOnClickListener(this);
        view.findViewById(R.id.black_tv).setOnClickListener(this);
        view.findViewById(R.id.tv_open_live).setOnClickListener(this);
        view.findViewById(R.id.red_pack_history_tv).setOnClickListener(this);
        view.findViewById(R.id.code_tv).setOnClickListener(this);
        view.findViewById(R.id.tv_view_diamond).setOnClickListener(this);
//        view.findViewById(R.id.tv_my_room).setOnClickListener(this);
        TextView shopMallTv = view.findViewById(R.id.tv_shop_mall);
        shopMallTv.setOnClickListener(this);
        youngModelTv.setOnClickListener(this);
        //    private TextView openBroadcastingStationTv;
        TextView anchorCenterTv = view.findViewById(R.id.anchor_center_tv);
        TextView customerTv = view.findViewById(R.id.customer_service_tv);
//        numKBTv = view.findViewById(R.id.tv_num_kb);
//        numKDTv = view.findViewById(R.id.tv_num_kd);
        numFansTv = view.findViewById(R.id.tv_fans_nub);
        numFollowTv = view.findViewById(R.id.tv_follow_nub);

        view.findViewById(R.id.tv_allgif).setOnClickListener(this);
        callOnLL.setOnClickListener(this);
//        tvFollowNub = view.findViewById(R.id.tv_follow_nub);
//        tvFansNub = view.findViewById(R.id.tv_fans_nub);
        view.findViewById(R.id.rl_fans).setOnClickListener(this);
        view.findViewById(R.id.rl_follow).setOnClickListener(this);

        view.findViewById(R.id.setting_tv).setOnClickListener(this);
        view.findViewById(R.id.family_tv).setOnClickListener(this);
        customerTv.setOnClickListener(this);
        authenticationTv.setOnClickListener(this);
//        nobleCenterTv.setOnClickListener(this);
        anchorCenterTv.setOnClickListener(this);
//        tvMyProfit.setOnClickListener(this);
        tvMyDiamond.setOnClickListener(this);
//        rlMyLevel.setOnClickListener(this);
        rlUserInformation.setOnClickListener(this);
        nobleTv.setOnClickListener(this);
//        tvSignIn.setOnClickListener(this);
//        fansRl.setOnClickListener(this);
//        followRl.setOnClickListener(this);
//        openBroadcastingStationTv.setOnClickListener(this);
        view.findViewById(R.id.rl_gift_history).setOnClickListener(this);
        view.findViewById(R.id.id_copy_tv).setOnClickListener(this);
        view.findViewById(R.id.tv_my_dress).setOnClickListener(this);
        mSwipeRefreshLayout.setOnRefreshListener(refreshLayout -> getData());
    }


    @Override
    public void onResume() {
        super.onResume();
        if (!DataHelper.INSTANCE.getLoginToken().isEmpty()) {
            getData();
        }
    }


    private void initData(MineBean bean) {
        if (!isShowYoungModelDialog && !DataHelper.INSTANCE.isNewUser()) {
            if (bean.getYoung_pwd() == 0 && !TimeUtil.isToday(new Date(DataHelper.INSTANCE.getYoungModelShowTime()))) {
                new AdolescentModelDialog(getContext()).show();
                DataHelper.INSTANCE.saveYoungModelShowTime(System.currentTimeMillis());
            }
            if (bean.getYoung_pwd() == 1 && TimeUtil.getIsYoungModelTime()) {
                new AdolescentModelInputDialog(getContext()).show();
            }
            isShowYoungModelDialog = true;
        }
//        if (!TimeUtil.isToday(new Date(DataHelper.INSTANCE.getSignInDialogShowTime())) && !DataHelper.INSTANCE.isNewUser()) {
//            getUserSignData();
//            DataHelper.INSTANCE.saveSignInDialogShowTime(System.currentTimeMillis());
//        }
        meiliIv.setCharmLevel(bean.getCharm_level().getGrade());
        gongxianIv.setWealthLevel(bean.getWealth_level().getGrade());
        if (bean.getGood_number_state() == 1) {
            liangIv.setVisibility(View.VISIBLE);
        } else {
            liangIv.setVisibility(View.GONE);
        }
        visitNumTv.setText(String.valueOf(bean.getVisit_count()));
        tvUserName.setText(bean.getNickname());
        tvUserId.setText("ID:" + bean.getGood_number());
//        numKDTv.setText(String.valueOf(bean.getDiamonds()));
//        numKBTv.setText(bean.getEarning());
        numFansTv.setText(bean.getFans_count());
        numFollowTv.setText(bean.getFollower_count());
        signTv.setText(bean.getSignature());
        //审核中
        ImgUtil.INSTANCE.loadFaceIcon(Objects.requireNonNull(getContext()), bean.getFace(), ivUserIcon);
//        switch (bean.getIdentify_status()) {
//            //身份认证：0:未认证,1:审核中,2:已认证
//            case 0:
//            case 1:
//                tvMyProfit.setVisibility(View.GONE);
//                break;
//            case 2:
//                tvMyProfit.setVisibility(View.VISIBLE);
//                break;
//            default:
//                break;
//        }
    }


    private void getData() {
        NetService.Companion.getInstance(getContext()).getMineInfo(getContext().getPackageName(), new Callback<MineBean>() {
            @Override
            public void onSuccess(int nextPage, MineBean bean, int code) {
                mSwipeRefreshLayout.finishRefresh(true);
                mBean = bean;
                initData(bean);
                DataHelper.INSTANCE.saveIsYoungModelSetting(bean.getYoung_pwd());
                DataHelper.INSTANCE.updatalUserInfo(bean);
//                if (DataHelper.INSTANCE.currentCity.equals(mBean.getCity())) {
//                    return;
//                }
//                mBean.setCity(DataHelper.class);
//                NetService.Companion.getInstance(getContext()).editInfo(mBean, new Callback<BaseBean>() {
//                    @Override
//                    public void onSuccess(int nextPage, BaseBean bean, int code) {
//                        DataHelper.INSTANCE.updatalUserInfo(mBean);
//                    }
//
//                    @Override
//                    public void onError(@NotNull String msg, @NotNull Throwable throwable, int code) {
//                    }
//
//                    @Override
//                    public boolean isAlive() {
//                        return isLive();
//                    }
//                });
//                BDLocationUtils.startLocation(getContext(), new BDLocationUtils.OnLoacationListener() {
//                    @Override
//                    public void onLocation(String city) {
//                        if (city.equals(mBean.getCity())) {
//                            return;
//                        }
//                        mBean.setCity(city);
//                        NetService.Companion.getInstance(getContext()).editInfo(mBean, new Callback<BaseBean>() {
//                            @Override
//                            public void onSuccess(int nextPage, BaseBean bean, int code) {
//                                DataHelper.INSTANCE.saveLocalUser(mBean);
//                            }
//
//                            @Override
//                            public void onError(@NotNull String msg, @NotNull Throwable throwable, int code) {
//                            }
//
//                            @Override
//                            public boolean isAlive() {
//                                return isLive();
//                            }
//                        });
//                    }
//                });
            }

            @Override
            public void onError(@NotNull String msg, @NotNull Throwable throwable, int code) {
                mSwipeRefreshLayout.finishRefresh(true);

            }

            @Override
            public boolean isAlive() {
                return isLive();
            }
        });
    }

//    private void getUserSignData() {
//        NetService.Companion.getInstance(getContext()).getUserSignData(new Callback<SignatureBean>() {
//            @Override
//            public void onSuccess(int nextPage, SignatureBean bean, int code) {
//                if (bean.getSignature_data().get(bean.getSign_offset()) == SignatureBean.SIGN_IN_TYPE_SIGN) {
//                    new SignInDialog(getContext(), bean).show();
//                }
//            }
//
//            @Override
//            public void onError(@NotNull String msg, @NotNull Throwable throwable, int code) {
//                ToastUtil.INSTANCE.e(getContext(), msg);
//            }
//
//            @Override
//            public boolean isAlive() {
//                return isLive();
//            }
//        });
//    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_allgif:
                AllGiftActivity.start(getMContext(),String.valueOf(DataHelper.INSTANCE.getUID()));
                break;
            case R.id.iv_first_luxury:
                WebActivity.start(getContext(), BaseConfig.H5_URL +"recharge_first/homepage?token="+DataHelper.INSTANCE.getLoginToken(),"首充送礼");
                break;
            case R.id.rl_follow:
                ARouter.getInstance().build("/app/friend")
                        .withInt("friendtype",0)
                        .navigation();
//                FriendActivity.start(getContext(), false);
                break;
            case R.id.rl_fans:
                ARouter.getInstance().build("/app/friend")
                        .withInt("friendtype",1)
                        .navigation();
//                FriendActivity.start(getContext(), true);
                break;
//            case R.id.tv_user_center:
            case R.id.rl_user_information:
                startActivity(UserHomepageActivity.getIntent(getMContext(), String.valueOf(DataHelper.INSTANCE.getUID())));
                break;
            case R.id.tv_my_diamond:
                PaymengActivity.start(getContext());
                break;
            case R.id.tv_view_diamond:
                PaymengActivity.start(getContext());
                break;
//            case R.id.iv_homepage:
//                startActivity(UserHomepageActivity.getIntent(getMContext(), DataHelper.INSTANCE.getUID() + ""));
//                break;
            case R.id.tv_level_weath:
                NewLevelActivity.start(getContext(), NewLevelActivity.LEVEL_TYPE_GX);
                break;
            case R.id.tv_level_charm:
                NewLevelActivity.start(getContext(), NewLevelActivity.LEVEL_TYPE_ML);
//                RedPacketRecordActivity.start(getMContext());
                break;
            case R.id.tv_shop_mall:
                ShoppingMallActivity.start(getContext());
//                ARouter.getInstance().build("/app/dress").navigation();
                break;
            case R.id.rl_gift_history:
                GiftHistoryActivity.start(getMContext());
                break;
            case R.id.anchor_center_tv:
                startActivity(FeedbackActivity.getIntent(getContext()));
                break;
            case R.id.ll_noble:
                if (mBean == null){
                    return;
                }
//                ToastUtil.INSTANCE.error(getContext(),"贵族待开放~");
                NobleCenterActivity.start(getContext(), mBean.getRank_id());
                break;
            case R.id.setting_tv:
                ARouter.getInstance().build(RouterUrl.userSetting)
                        .withString("customServiceQq", "")
                        .withString("familyEntryQq", "")
                        .navigation();
                break;
            case R.id.id_copy_tv:
                if (mBean == null){
                    return;
                }
                CopyUtil.copy(mBean.getGood_number() + "", getContext());
                break;
            case R.id.tv_my_dress:
                MyDressActivity.start(getContext());
                break;
            case R.id.customer_service_tv:
                PrivateChatActivity.startActivity(getContext(), "6001", "");
                break;
            case R.id.tv_follow_room:
                FollowRoomActivity.start(getContext());
                break;
            case R.id.ll_call_on:
                CallOnActivity.start(getContext());
                break;
            case R.id.young_model_tv:
                ARouter.getInstance().build(RouterUrl.adolescentModel).navigation();
                break;
            case R.id.authentication_tv:
                if (mBean == null){
                    return;
                }
                if (mBean.getIdentify_status() == 0) {
                    ARouter.getInstance().build(RouterUrl.identityAuthentication).navigation();
                }
                if (mBean.getIdentify_status() == 1) {
                    ARouter.getInstance().build(RouterUrl.examine).navigation();
                }
                if (mBean.getIdentify_status() == 2) {
                    ARouter.getInstance().build(RouterUrl.authenticationDetail).navigation();
                }
                break;
            case R.id.black_tv:
                startActivity(BlackListActivity.Companion.getIntent(getContext()));
                break;
            case R.id.tv_open_live:
                if (mBean == null){
                    return;
                }
                openLive();
                break;
            case R.id.family_tv:
                if (mBean == null){
                    return;
                }
                isInFamily();
                break;
            case R.id.red_pack_history_tv:
                //红包记录
                RedPacketRecordActivity.start(getContext());
                break;
            case R.id.code_tv:
                //邀请码
//                InviteCodeActivity.start(getContext());
                break;
            default:
                break;
        }
    }


    private void openLive() {
        if (mBean != null) {
            String roomId = mBean.getRoom_id();
            if (!TextUtils.isEmpty(roomId) && !roomId.equals("0")) {
                ChatRoomManager.INSTANCE.joinChat(getContext(), roomId, new JoinChatCallBack() {
                    @Override
                    public void onSuc() {

                    }
                    @Override
                    public void onFail(@NotNull String msg) {
                        ToastUtil.INSTANCE.i(getContext(),msg);
                    }
                });
            } else {
                ARouter.getInstance().build(RouterUrl.createChat)
                        .withInt("type", ChatRoomManager.ROOM_TYPE_PERSONAL).navigation(getContext());
//                CreateRoomDialog dialog = new CreateRoomDialog(new CreateRoomDialog.OnCreateRoomLisener() {
//                    @Override
//                    public void onCreatePerson() {
//                        ARouter.getInstance().build(RouterUrl.createChat)
//                                .withInt("type", ChatRoomManager.ROOM_TYPE_PERSONAL).navigation(getContext());
//                    }
//
//                    @Override
//                    public void onCreateParty() {
//                        ARouter.getInstance().build(RouterUrl.createChat)
//                                .withInt("type", ChatRoomManager.ROOM_TYPE_LABOR_UNION).navigation(getContext());
//                    }
//                });
//                dialog.show(getChildFragmentManager());
            }
        }

    }

    private void isInFamily(){
        NetService.Companion.getInstance(getContext()).getFamilyId(new Callback<FamilyIdBean>() {
            @Override
            public void onSuccess(int nextPage, FamilyIdBean bean, int code) {
                String familyId = bean.getFamily_id();
                if (!TextUtils.isEmpty(familyId)&& !familyId.equals("0")) {
                    ARouter.getInstance().build("/app/familyList").withBoolean("CAN_CREATE", false).navigation(getMContext());
                }else{
                    ARouter.getInstance().build("/app/familyList").withBoolean("CAN_CREATE", true).navigation(getMContext());
                }
//                if (!TextUtils.isEmpty(familyId)) {
//                    ARouter.getInstance().build("/app/familyCenter").withString("FAMILY_ID", familyId).navigation(getMContext());
//                } else {
//                    ARouter.getInstance().build("/app/familyList").withBoolean("CAN_CREATE", true).navigation(getMContext());
//                }
            }

            @Override
            public void onError(@NotNull String msg, @NotNull Throwable throwable, int code) {
                ToastUtil.INSTANCE.i(getMContext(), msg);
            }

            @Override
            public boolean isAlive() {
                return isLive();
            }
        });
    }


}
