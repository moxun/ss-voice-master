package com.miaomi.fenbei.voice.ui.mine.user_homepage;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.indicatorlib.base.BaseFragmentAdapter;
import com.google.android.material.appbar.AppBarLayout;
import com.miaomi.fenbei.base.core.JoinChatCallBack;
import com.miaomi.fenbei.base.util.StatusBarUtil;
import com.miaomi.fenbei.base.web.WebActivity;
import com.miaomi.fenbei.base.widget.AppBarStateChangeListener;
import com.miaomi.fenbei.base.widget.InRoomView;
import com.miaomi.fenbei.base.widget.KMToolBar;
import com.miaomi.fenbei.base.widget.SexAndAgeView;
import com.miaomi.fenbei.base.widget.UserHomePageIndicator;
import com.miaomi.fenbei.room.ChatRoomManager;
import com.miaomi.fenbei.room.ui.dialog.ReportDialog;
import com.miaomi.fenbei.base.bean.BaseBean;
import com.miaomi.fenbei.base.bean.FollowBean;
import com.miaomi.fenbei.base.bean.UserHomePageInfoBean;
import com.miaomi.fenbei.base.config.BaseConfig;
import com.miaomi.fenbei.base.core.BaseActivity;
import com.miaomi.fenbei.base.core.dialog.CommonDialog;
import com.miaomi.fenbei.base.core.msg.MsgManager;
import com.miaomi.fenbei.base.core.msg.SendMsgListener;
import com.miaomi.fenbei.base.net.Callback;
import com.miaomi.fenbei.base.net.Data;
import com.miaomi.fenbei.base.net.NetService;
import com.miaomi.fenbei.base.util.CopyUtil;
import com.miaomi.fenbei.base.util.DataHelper;
import com.miaomi.fenbei.base.util.DensityUtil;
import com.miaomi.fenbei.base.util.ImgUtil;
import com.miaomi.fenbei.base.util.ToastUtil;
import com.miaomi.fenbei.base.widget.ClickViewPager;
import com.miaomi.fenbei.base.widget.LevelView;
import com.miaomi.fenbei.base.widget.SexView;
import com.miaomi.fenbei.voice.R;
import com.miaomi.fenbei.voice.ui.ReportActivity;
import com.miaomi.fenbei.voice.ui.main.AllGiftActivity;
import com.miaomi.fenbei.voice.ui.main.UserHomePageCardFragment;
import com.miaomi.fenbei.voice.ui.mine.editinfo.EditUserInfoActivity;
import com.opensource.svgaplayer.SVGACallback;
import com.opensource.svgaplayer.SVGADrawable;
import com.opensource.svgaplayer.SVGADynamicEntity;
import com.opensource.svgaplayer.SVGAImageView;
import com.opensource.svgaplayer.SVGAParser;
import com.opensource.svgaplayer.SVGAVideoEntity;
import com.tencent.imsdk.TIMMessage;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;


@Route(path = "/app/userhomepage")
public class UserHomepageActivity extends BaseActivity implements View.OnClickListener {

    @Autowired(name = PARAMETER_USER_ID)
    public String userId = "";

    public final static String PARAMETER_USER_ID = "user_id";
    public final static String PARAMETER_USER_NAME = "user_name";
    public final static String PARAMETER_USER_FACE = "user_face";

//    private ImageView ivBack;
//    private ImageView ivEdit;
    private PopupWindow popupBigPhoto;
    private TextView tvNickname;
    private SexAndAgeView sexTv;
//    private TextView tvRoomState;
    private TextView tvID;
//    private TextView followNumTv;
    private TextView fansTv;
//    private TextView roomNameTv;
//    private TextView tvAutograph;
//    private RecyclerView rvGift;
    private LinearLayout llBottom;
    private TextView tvFollow;
    private TextView tvChat;
//    private GiftAdapter giftAdapter;
    private UserHomePageInfoBean userHomePageInfoBean;
    private LevelView meiliIv;
    private LevelView gongxianIv;
    private ViewStub noAccountView;
//    private KMSoundView soundView;
//    private ImageView liangIv;
//    private ShadowLayout roomStateLL;
//    private ImageView moreIv;
    private View popView;
    private ImageView nobilityIv;
    private ClickViewPager imgViewPager;
//    private LinearLayout giftWallStatusTv;
//    private ViewPagerIndicator viewPagerIndicator;
    private UserPicPagerAdapter userPicPagerAdapter;
//    private ImageView roomIconIv;
//    private FrameLayout converFl;
//    private TextView titleTv;
//    private TextView infoTv;
//    private TextView giftNumTv;
    private UserHomePageIndicator tabLayout;
    private ViewPager contentViewPager;
    private List<Fragment> mTabList = new ArrayList<>();
    private ImageView faceIv;
//    private LinearLayout inRoomLL;
    private UserHomePageCardFragment userHomePageCardFragment;
//    private ImageView roomIconIv;
//    private ImageView roomGifIv;
    private AppBarLayout appBarLayout;
    private KMToolBar toolbar;
    private SVGAImageView inRoomIv;
    private TextView signTv;
//    private ImageView lecturerIv;

    private InRoomView inRoomView;
    private TextView  moreTv;
    public static Intent getIntent(Context context, String userId) {
        Intent intent = new Intent(context, UserHomepageActivity.class);
        intent.putExtra(PARAMETER_USER_ID, userId);
        return intent;
    }

    @Override
    public int getLayoutId() {
        return R.layout.user_activity_homepage;
    }

    @Override
    public void initView() {
        ARouter.getInstance().inject(this);
        StatusBarUtil.setStatusBarTextColor(this,true);
        userId = getIntent().getStringExtra(PARAMETER_USER_ID);
        if (TextUtils.isEmpty(userId)) {
            if (DataHelper.INSTANCE.getUserInfo() != null) {
                userId = String.valueOf(DataHelper.INSTANCE.getUserInfo().getUser_id());
            }
        }
        findViewById(R.id.tv_send_gift).setOnClickListener(this);
//        findViewById(R.id.tv_star_explain).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                WebActivity.start(UserHomepageActivity.this,BaseConfig.URL_STAR_EXPALIN,"集星说明");
//            }
//        });
//        infoTv = findViewById(R.id.tv_info);
        signTv = findViewById(R.id.tv_sign);
        tabLayout = findViewById(R.id.tab_layout);
        contentViewPager = findViewById(R.id.view_pager);
//        roomNameTv = findViewById(R.id.tv_room_name);
//        lecturerIv = findViewById(R.id.iv_lecturer);
//        converFl = findViewById(R.id.fl_conver);
//        AmplifyScrollView amplifyScrollView = findViewById(R.id.scroll_view);
//        giftNumTv = findViewById(R.id.tv_gift_num);
//        titleTv = findViewById(R.id.tv_title);
        faceIv = findViewById(R.id.iv_face);
        toolbar = findViewById(R.id.toolbar);
        inRoomIv = findViewById(R.id.iv_in_room);
//        roomIconIv = findViewById(R.id.iv_room_face);
//        soundView = findViewById(R.id.sv_sound);
//        roomIconIv = findViewById(R.id.iv_room_icon);
        appBarLayout = findViewById(R.id.app_bar_layout);
//        giftWallStatusTv = findViewById(R.id.tv_giftwall_status);
        noAccountView = findViewById(R.id.no_account);
//        moreIv = findViewById(R.id.iv_more);
//        liangIv = findViewById(R.id.iv_liang);
//        ivBack = findViewById(R.id.iv_back);
//        ivEdit = findViewById(R.id.iv_edit);
//        viewPagerIndicator = findViewById(R.id.view_paget_indicator);
        tvNickname = findViewById(R.id.tv_nickname);
        sexTv = findViewById(R.id.tv_sex);
//        inRoomLL = findViewById(R.id.ll_in_room);
//        roomGifIv = findViewById(R.id.iv_room_gif);
        inRoomView = findViewById(R.id.irv_in_room);
//        inRoomLL.setOnClickListener(this);
//        tvRoomState = findViewById(R.id.tv_room_state);
//        roomStateLL = findViewById(R.id.ll_room_state);
//        roomStateLL.setVisibility(View.GONE);
        tvID = findViewById(R.id.tv_id);
        imgViewPager = findViewById(R.id.vp_cover);
//        followNumTv = findViewById(R.id.tv_follow_num);
        fansTv = findViewById(R.id.tv_fans);
//        tvAutograph = findViewById(R.id.tv_autograph);
        nobilityIv = findViewById(R.id.nobility_iv);
        moreTv=findViewById(R.id.tv_more);

        popView = getLayoutInflater().inflate(R.layout.popupwindow_more, null);
        popView.findViewById(R.id.tv_report).setOnClickListener(v -> report());
        popView.findViewById(R.id.tv_addblack).setOnClickListener(v -> addBlack());
//        rvGift = findViewById(R.id.rv_gift);
        llBottom = findViewById(R.id.ll_bottom);
        tvChat = findViewById(R.id.tv_talk);
        tvFollow = findViewById(R.id.tv_follow);
        meiliIv = findViewById(R.id.iv_meili);
        gongxianIv = findViewById(R.id.iv_gongxian);
//        giftNumTv.setOnClickListener(this);
//        giftWallStatusTv.setOnClickListener(this);
//        moreIv.setOnClickListener(this);
//        roomStateLL.setOnClickListener(this);
//        ivBack.setOnClickListener(this);
//        ivUpload.setOnClickListener(this);
//        ivEdit.setOnClickListener(this);
        tvFollow.setOnClickListener(this);
        tvChat.setOnClickListener(this);
        moreTv.setOnClickListener(this);
        inRoomIv.setOnClickListener(this);
        findViewById(R.id.fl_face).setOnClickListener(this);
//        findViewById(R.id.id_copy_tv).setOnClickListener(this);
//        viewPagerIndicator.setViewPager(imgViewPager);
//        giftAdapter = new GiftAdapter(this);
//        rvGift.setLayoutManager(new GridLayoutManager(this, 5));
//        rvGift.setAdapter(giftAdapter);
        llBottom.setVisibility(View.GONE);
        imgViewPager.setOnItemClickListner(position -> startActivity(BigImgActivity.getPreviewBeanIntent(UserHomepageActivity.this, position, userId, userHomePageInfoBean.getImg_url_list())));
        userPicPagerAdapter = new UserPicPagerAdapter();
        imgViewPager.setAdapter(userPicPagerAdapter);
        tvNickname.setText(getIntent().getStringExtra(PARAMETER_USER_NAME));
        getData();
//        getGiftWall();
        userHomePageCardFragment = UserHomePageCardFragment.newInstance(userId);
        mTabList.add(userHomePageCardFragment);
        mTabList.add(UserTrendsFragment.newInstance(userId));
        contentViewPager.setAdapter(new BaseFragmentAdapter(getSupportFragmentManager(), mTabList));
        tabLayout.setViewPager(contentViewPager);
        appBarLayout.addOnOffsetChangedListener(new AppBarStateChangeListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state) {
                if( state == State.EXPANDED ) {

                    //展开状态
                    toolbar.changeToolbarStatus(true);
                    llBottom.setVisibility(View.VISIBLE);
                    if (Integer.parseInt(userId) != DataHelper.INSTANCE.getUID()) {
                        llBottom.setVisibility(View.VISIBLE);
                    }

                }else if(state == State.COLLAPSED){
                    //折叠状态
                    toolbar.changeToolbarStatus(false);
                    llBottom.setVisibility(View.GONE);
                }else {
                    //中间状态
//                    llBottom.setVisibility(View.VISIBLE);
//                    toolbar.setNavigationIcon(android.support.v7.appcompat.R.drawable.abc_ic_ab_back_material);

                }
            }
        });
        toolbar.setOnToolbarOparate(new KMToolBar.OnToolbarOparate() {
            @Override
            public void onBack() {
                finish();
            }

            @Override
            public void onEdit() {
                EditUserInfoActivity.start(UserHomepageActivity.this,
                        userHomePageInfoBean.getVoice_url(),
                        userHomePageInfoBean.getVoice_time(),
                        userHomePageInfoBean.getImg_url_list(),
                        userHomePageInfoBean.getVoice_limit_time());
            }

            @Override
            public void onMore(View view) {
                popupBigPhoto = new PopupWindow(popView, DensityUtil.INSTANCE.dp2px(UserHomepageActivity.this, 90),
                        DensityUtil.INSTANCE.dp2px(UserHomepageActivity.this, 82), true);
                popupBigPhoto.setOutsideTouchable(true);
                popupBigPhoto.showAsDropDown(view);
            }
        });
//        amplifyScrollView.setOnSlideListener(new AmplifyScrollView.OnSlideListener() {
//            @Override
//            public void onShowGallary() {
//                startActivity(BigImgActivity.getPreviewBeanIntent(UserHomepageActivity.this, 0, userId, userHomePageInfoBean.getImg_url_list()));
//            }
//
//            @Override
//            public void onShowPersonInfo() {
//            }
//
//            @Override
//            public void onTransTop(float aphle) {
//                if (aphle == 1){
//                    ivBack.setSelected(false);
//                    moreIv.setSelected(false);
//                    ivEdit.setSelected(false);
//                    titleTv.setVisibility(View.GONE);
//                }else{
//                    ivBack.setSelected(true);
//                    moreIv.setSelected(true);
//                    ivEdit.setSelected(true);
//                    titleTv.setVisibility(View.VISIBLE);
//                }
//                converFl.setAlpha(1-aphle);
//            }
//        });
    }

    private void userVisit(){
        NetService.Companion.getInstance(this).userVisit( userId, new Callback<String>() {
            @Override
            public void onSuccess(int nextPage, String bean, int code) {

            }

            @Override
            public void onError(@NotNull String msg, @NotNull Throwable throwable, int code) {

            }

            @Override
            public boolean isAlive() {
                return true;
            }
        });
    }

    private void report() {
        ReportActivity.start(UserHomepageActivity.this,userId,1);
//        ReportDialog dialog = new ReportDialog(UserHomepageActivity.this, 1, new ReportDialog.CallBack() {
//            @Override
//            public void onCancel(@NotNull ReportDialog dialog) {
//
//            }
//
//            @Override
//            public void onSubmit(@NotNull final ReportDialog dialog, int type) {
//                NetService.Companion.getInstance(UserHomepageActivity.this).report(userId, 1, type, new Callback<BaseBean>() {
//                    @Override
//                    public void onSuccess(int nextPage, BaseBean bean, int code) {
//                        ToastUtil.INSTANCE.suc(UserHomepageActivity.this, "举报成功");
//                        popupBigPhoto.dismiss();
//                        dialog.dismiss();
//                    }
//
//                    @Override
//                    public void onError(@NotNull String msg, @NotNull Throwable throwable, int code) {
//                        ToastUtil.INSTANCE.suc(UserHomepageActivity.this, msg);
//                    }
//
//                    @Override
//                    public boolean isAlive() {
//                        return isLive();
//                    }
//                });
//            }
//        });
//        dialog.show();
    }

    private void addBlack() {
        CommonDialog addBlackDialog =new CommonDialog(UserHomepageActivity.this);
        addBlackDialog.setContent("拉黑后，你将不再收到对方信息，同时对方无法加入你的房间哦~");
        addBlackDialog.setTitle("友情提示");
        addBlackDialog.setLeftBt("取消", v -> addBlackDialog.dismiss());
        addBlackDialog.setRightBt("拉黑", v -> NetService.Companion.getInstance(UserHomepageActivity.this).addBlack(userId, "", new Callback<BaseBean>() {
            @Override
            public boolean isAlive() {
                return isLive();
            }

            @Override
            public void onError(@NotNull String msg, @NotNull Throwable throwable, int code) {
                ToastUtil.INSTANCE.error(UserHomepageActivity.this, msg);
            }

            @Override
            public void onSuccess(int nextPage, BaseBean bean, int code) {
                ToastUtil.INSTANCE.suc(UserHomepageActivity.this, "拉黑成功");
                popupBigPhoto.dismiss();

            }
        }));
        addBlackDialog.show();
    }


    private void bindData(UserHomePageInfoBean bean) {
//        if (bean.getGood_number_state() == 1) {
//            tvID.show("" + bean.getGood_number(),true);
//        } else {
//            tvID.show("" + bean.getGood_number(),false);
//
//        }
//        followNumTv.setText(String.valueOf(bean.getFollow_number()));
        signTv.setText(bean.getSignature());
        tvID.setText("ID：" + bean.getUser_id());
        fansTv.setText(bean.getFans_number() + "粉丝");
        if (!TextUtils.isEmpty(bean.getMedal())) {
            nobilityIv.setVisibility(View.VISIBLE);
            ImgUtil.INSTANCE.loadImg(this, bean.getMedal(), nobilityIv);
        } else {
            nobilityIv.setVisibility(View.GONE);
        }
        toolbar.setTitle(bean.getNickname() +"的主页");
        tvNickname.setText(bean.getNickname());
//        sexIv.setSeleted(bean.getGender());
        sexTv.setContent(bean.getGender() == BaseConfig.USER_INFO_GENDER_MAN,bean.getAge());
        //是否访问自己的主页
        if (Integer.parseInt(userId) == DataHelper.INSTANCE.getUID()) {
            llBottom.setVisibility(View.GONE);
            tvFollow.setVisibility(View.GONE);
        } else {
            userVisit();
            //是否已关注，1:是，0：否
            if (bean.getFollow_status() == 1) {
                tvFollow.setVisibility(View.VISIBLE);
                tvFollow.setBackgroundResource(R.drawable.user_followed);
            } else {
                tvFollow.setVisibility(View.VISIBLE);
                tvFollow.setBackgroundResource(R.drawable.bg_home_follow);
            }
            llBottom.setVisibility(View.VISIBLE);
        }
        toolbar.changeToolbarRightStatus(Integer.parseInt(userId) == DataHelper.INSTANCE.getUID());
        if (bean.getEnter_room_id() != 0) {
            inRoomView.setVisibility(View.VISIBLE);
            inRoomIv.setVisibility(View.VISIBLE);
            showSvgaGiftAnim(UserHomepageActivity.this,inRoomIv,"svga_in_room.svga");
//            ImgUtil.INSTANCE.loadFaceIcon(UserHomepageActivity.this,bean.getRoom_icon(),roomIconIv);
//            ImgUtil.INSTANCE.loadGif(UserHomepageActivity.this,R.drawable.icon_inroom_anim,roomGifIv);
//            roomNameTv.setText("【ID："+bean.getEnter_room_id()+"】 "+bean.getRoom_name());
        } else {
            inRoomView.setVisibility(View.GONE);
            inRoomIv.setVisibility(View.GONE);
        }
//        ImgUtil.INSTANCE.loadImg(UserHomepageActivity.this,bean.getLecturer(),lecturerIv);

        userPicPagerAdapter.setData(bean.getImg_url_list());
        if (bean.getImg_url_list().size() > 0) {
            ImgUtil.INSTANCE.loadFaceIcon(this,bean.getImg_url_list().get(0).getUrl(),faceIv);
        }
        if (bean.getImg_url_list().size() > 1) {
            imgViewPager.setCurrentItem(0);
//            viewPagerIndicator.setVisibility(View.VISIBLE);
//            viewPagerIndicator.init(bean.getImg_url_list().size());
        } else {
//            viewPagerIndicator.setVisibility(View.GONE);
        }
        meiliIv.setCharmLevel(bean.getCharm_level().getGrade());
        gongxianIv.setWealthLevel(bean.getWealth_level().getGrade());
//        String sex = "女";
//        if (bean.getGender() == BaseConfig.USER_INFO_GENDER_MAN){
//            sex = "男";
//        }

//        String spannableString = "<font color='#999999'>性别  </font>" +"&nbsp&nbsp&nbsp&nbsp"+
//                "<font color='#2B2B2B'>" + sex + "</font>" +
//                "<br/>" +
//                "<font color='#999999'>生日  </font>" +"&nbsp&nbsp&nbsp&nbsp"+
//                "<font color='#2B2B2B'>" + bean.getBirth() + "</font>" +
//                "<br/>" +
//                "<font color='#999999'>年龄  </font>" +"&nbsp&nbsp&nbsp&nbsp"+
//                "<font color='#2B2B2B'>" + bean.getAge() + "</font>" +
//                "<br/>" ;
//        if (TextUtils.isEmpty(bean.getCity())){
//            spannableString = spannableString + "<font color='#999999'>签名  </font>" +"&nbsp&nbsp&nbsp&nbsp"+
//                    "<font color='#2B2B2B'>" + bean.getSignature() + "</font>";
//        }else{
//            spannableString = spannableString + "<font color='#999999'>城市  </font>" +"&nbsp&nbsp&nbsp&nbsp"+
//                    "<font color='#2B2B2B'>" + bean.getCity() + "</font>"+
//                    "<br/>" +
//                    "<font color='#999999'>签名  </font>" +"&nbsp&nbsp&nbsp&nbsp"+
//                    "<font color='#2B2B2B'>" + bean.getSignature() + "</font>";
//        }
//        userHomePageCardFragment.setContent(spannableString);
//        userHomePageCardFragment.setSoundView(bean.getVoice_url(),bean.getVoice_time());
    }

    private void getData() {
        if (TextUtils.isEmpty(userId)) {
            return;
        }
        NetService.Companion.getInstance(this).personalHomepage(userId, new Callback<UserHomePageInfoBean>() {
            @Override
            public void onSuccess(int nextPage, UserHomePageInfoBean bean, int code) {
                userHomePageInfoBean = bean;
                bindData(bean);
            }

            @Override
            public void onError(@NotNull String msg, @NotNull Throwable throwable, int code) {
                if (code == Data.CODE_ACCOUNT_LOGOFF) {
                    View view = noAccountView.inflate();
                    setBaseStatusBar(false, true);
                    ((TextView) view.findViewById(R.id.main_tv)).setText(getTitle().toString());
                    view.findViewById(R.id.back_btn).setOnClickListener(v -> finish());
                } else {

                }
                ToastUtil.INSTANCE.suc(UserHomepageActivity.this, msg);
            }

            @Override
            public boolean isAlive() {
                return isLive();
            }
        });
    }



    private void follow() {
        NetService.Companion.getInstance(UserHomepageActivity.this).addConcern(DataHelper.INSTANCE.getLoginToken(), userId, new Callback<FollowBean>() {
            @Override
            public void onSuccess(int nextPage, FollowBean bean, int code) {
//                0 未关注 1已关注
                if (bean.getIs_follow() == 1) {

//                    tvFollow.setText("已关注");
                    tvFollow.setBackgroundResource(R.drawable.user_followed);
                    ToastUtil.INSTANCE.suc(UserHomepageActivity.this, "关注成功");
                    MsgManager.INSTANCE.sendFollowMsg(userId, new SendMsgListener() {
                        @Override
                        public void onSendSuc(TIMMessage timMessage) {

                        }

                        @Override
                        public void onSendFail(int i, String s) {

                        }
                    });
                } else {
                    tvFollow.setBackgroundResource(R.drawable.bg_home_follow);
                    ToastUtil.INSTANCE.suc(UserHomepageActivity.this, "取消关注成功");

//                    tvFollow.setText("关注");
//                    tvFollow.setBackgroundResource(R.drawable.user_chat_bg);
                }
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


    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.iv_back) {
            finish();
        }
        if (i == R.id.tv_send_gift){
            SendPersonGiftDialog dialog = new SendPersonGiftDialog(userId);
            dialog.show(getSupportFragmentManager());
        }
        if (userHomePageInfoBean == null) {
            return;
        }
        if(i==R.id.tv_more){
            AllGiftActivity.start(getBaseContext(),userId);
        }
        if (i == R.id.tv_follow) {
            follow();
        } else if (i == R.id.tv_talk) {
            if (DataHelper.INSTANCE.getUserInfo().getWealth_level().getGrade() == 0 && DataHelper.INSTANCE.getUserInfo().getCharm_level().getGrade() == 0){
                ToastUtil.INSTANCE.i(UserHomepageActivity.this,"财富或魅力等级大于0级才能私聊");
                return;
            }
            ARouter.getInstance().build("/imkit/privatechat")
                    .withString("CHAT_ID", userId + "")
                    .withString("FROM_USER_NICKNAME", userHomePageInfoBean.getNickname())
                    .withString("FROM_USER_AVTER", userHomePageInfoBean.getFace())
                    .navigation();
        }
        else if (i == R.id.id_copy_tv) {
            CopyUtil.copy(userHomePageInfoBean.getGood_number() + "", UserHomepageActivity.this);
        }else if (i == R.id.iv_in_room){
            int roomId = userHomePageInfoBean.getEnter_room_id();
            ChatRoomManager.INSTANCE.joinChat(UserHomepageActivity.this, String.valueOf(roomId), new JoinChatCallBack() {
                @Override
                public void onSuc() {
                    finish();
                }

                @Override
                public void onFail(@NotNull String msg) {

                }
            });
        }else if (i == R.id.fl_face){
            int roomId = userHomePageInfoBean.getEnter_room_id();
            if (roomId > 0){
                ChatRoomManager.INSTANCE.joinChat(UserHomepageActivity.this, String.valueOf(roomId), new JoinChatCallBack() {
                    @Override
                    public void onSuc() {
                        finish();
                    }

                    @Override
                    public void onFail(@NotNull String msg) {

                    }
                });
            }
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == EditUserInfoActivity.REQUEST_CODE) {
            getData();
        }
    }

    private void showSvgaGiftAnim(Context context, SVGAImageView svgaImageView, String url){
        SVGAParser parser = new SVGAParser(context);
        svgaImageView.setCallback(new SVGACallback() {
            @Override
            public void onPause() {

            }

            @Override
            public void onFinished() {
            }

            @Override
            public void onRepeat() {
            }

            @Override
            public void onStep(int i, double v) {

            }
        });
        parser.decodeFromAssets(url, new SVGAParser.ParseCompletion() {
            @Override
            public void onComplete(@NotNull SVGAVideoEntity videoItem) {
                SVGADynamicEntity dynamicItem = new SVGADynamicEntity();
                SVGADrawable drawable = new SVGADrawable(videoItem, dynamicItem);
                svgaImageView.setImageDrawable(drawable);
                svgaImageView.startAnimation();
            }
            @Override
            public void onError() {
            }
        });
    }
}
