package com.miaomi.fenbei.gift.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.miaomi.fenbei.base.bean.DiamondsBean;
import com.miaomi.fenbei.base.bean.GiftNumSelectBean;
import com.miaomi.fenbei.base.core.BaseFragment;
import com.miaomi.fenbei.base.net.NetService;
import com.miaomi.fenbei.base.util.DataHelper;
import com.miaomi.fenbei.base.util.ImgUtil;
import com.miaomi.fenbei.base.util.ToastUtil;
import com.miaomi.fenbei.base.web.WebActivity;
import com.miaomi.fenbei.base.widget.KMGiftTypeIndicator;
import com.miaomi.fenbei.gift.ExpressGiftDialog;
import com.miaomi.fenbei.gift.GiftManager;
import com.miaomi.fenbei.gift.GiftNumDialog;
import com.miaomi.fenbei.gift.R;
import com.miaomi.fenbei.gift.adapter.GiftNumAdapter;
import com.miaomi.fenbei.gift.adapter.GiftViewPagerAdapter;
import com.miaomi.fenbei.gift.adapter.UserListAdapter;
import com.miaomi.fenbei.base.bean.ChestGiftBean;
import com.miaomi.fenbei.base.bean.GiftBean;
import com.miaomi.fenbei.base.bean.GiftInfoBean;
import com.miaomi.fenbei.gift.callback.ChestGiftSendCallback;
import com.miaomi.fenbei.gift.callback.CommonGiftSendCallback;
import com.miaomi.fenbei.gift.callback.ExpressGiftSendCallback;
import com.miaomi.fenbei.gift.callback.PackGiftSendCallback;
import com.miaomi.fenbei.gift.listener.OnGifiNumSeletedListener;
import com.miaomi.fenbei.gift.listener.OnGiftListener;
import com.miaomi.fenbei.gift.listener.OnGiftSendCallback;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class GiftFragment extends BaseFragment implements View.OnClickListener {
    private final static int RADIO_ROOM_GIFT_PAGE_SIZE = 3;
    private final static int TAG_SEND_ALL_GIFT = -1;
    private ViewPager mFragmentViewpager;
    private List<GiftInfoBean.ListBean> mMicUserlist = new ArrayList<>();
    private List<GiftNumSelectBean> mSumlist = new ArrayList<>();
    private RecyclerView mUserListRecyclerview;
    private UserListAdapter mUserListAdapter;
    private RecyclerView giftNumRv;
    //    private TextView sumTv;
//    private int sendGiftNum;
//    private LinearLayout sumLL;
    private TextView moneyTv;
    private int mSelectedGiftType;
    //    private TextView sendTv;
    private BaseChildFragment giftPackFragment;
    //    private BaseChildFragment guardFragment;
    private TextView allSeletedCbk;
    private OnGiftListener onGiftListener;
    private List<GiftInfoBean.ListBean> mGiftUserList = new ArrayList<>();
    //    private LinearLayout payLL;ll_pay
    private LinearLayout micUserLL;
    private LinearLayout userInfoLL;
    private ImageView faceIv;
    private TextView nickNameTv;
    private LinearLayout contentLL;
    private TextView pacTotalTv;
    private KMGiftTypeIndicator giftTypeInr;
    //    private LinearLayout sumLL;
    private RelativeLayout bxExplianTv;
    private GiftNumAdapter giftNumAdapter;
    private ImageView longClickIv;


    public static GiftFragment newInstance() {
        GiftFragment fragment = new GiftFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    public void setOnGiftListener(OnGiftListener onGiftListener) {
        this.onGiftListener = onGiftListener;
    }

    @Override
    public int getLayoutId() {
        return R.layout.gift_layout_gift_all;
    }

    @Override
    public void initView(@NotNull View view) {
        giftNumAdapter = new GiftNumAdapter(getContext());
//        sumTv = view.findViewById(R.id.tv_sum);
//        sumLL = view.findViewById(R.id.ll_sum);
//        payLL = view.findViewById(R.id.ll_pay);
        giftNumRv = view.findViewById(R.id.rv_gift_num);
        bxExplianTv = view.findViewById(R.id.tv_bx_explain);
        mUserListRecyclerview = view.findViewById(R.id.rv_user);
        mFragmentViewpager = view.findViewById(R.id.vp_fragment);
        moneyTv = view.findViewById(R.id.tv_money);
        giftTypeInr = view.findViewById(R.id.gift_type);
        longClickIv = view.findViewById(R.id.iv_tips_long_click);
//        sendTv = view.findViewById(R.id.tv_send);
        allSeletedCbk = view.findViewById(R.id.iv_all_selected);
        micUserLL = view.findViewById(R.id.ll_mic);
        userInfoLL = view.findViewById(R.id.ll_user_info);
        faceIv = view.findViewById(R.id.iv_face);
        nickNameTv = view.findViewById(R.id.tv_nick_name);
        contentLL = view.findViewById(R.id.ll_content);
        pacTotalTv = view.findViewById(R.id.tv_pack_total);
        allSeletedCbk.setOnClickListener(this);
//        payLL.setOnClickListener(this);
        giftNumRv.setVisibility(GONE);
//        sendTv.setOnClickListener(this);
//        sumTv.setOnClickListener(this);
        bxExplianTv.setOnClickListener(this);
        moneyTv.setOnClickListener(this);

        mUserListAdapter = new UserListAdapter(getContext());
        mUserListAdapter.setOnSeletedChangeListener(new UserListAdapter.OnSeletedChangeListener() {
            @Override
            public void onSeletedChange(List<GiftInfoBean.ListBean> seletedList) {
                if (mUserListAdapter.isAllSeleted()) {
                    allSeletedCbk.setSelected(true);
                } else {
                    allSeletedCbk.setSelected(false);
                }
            }
        });

        mUserListRecyclerview.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        mUserListRecyclerview.setAdapter(mUserListAdapter);
        initMicList();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        giftNumRv.setLayoutManager(linearLayoutManager);
//        guardTabTv.setVisibility(GONE);
        giftNumRv.setAdapter(giftNumAdapter);
        mFragmentViewpager.setOffscreenPageLimit(RADIO_ROOM_GIFT_PAGE_SIZE);
        mFragmentViewpager.setAdapter(new GiftViewPagerAdapter(getChildFragmentManager(), RADIO_ROOM_GIFT_PAGE_SIZE));
        giftTypeInr.setViewPager(mFragmentViewpager, new String[]{"普通", "盲盒", "背包"}, "#999999", "#ED52F9");
        changeTab(GiftManager.GIFT_TYPE_COMMON);
        mFragmentViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                if (position == 0) {
                    changeTab(GiftManager.GIFT_TYPE_COMMON);
                }

                if (position == 1) {
                    changeTab(GiftManager.GIFT_TYPE_CHEST);
                }
                if (position == 2) {
                    changeTab(GiftManager.GIFT_TYPE_PACK);
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        giftNumAdapter.setOnSpinerItemClickListener(new OnGifiNumSeletedListener() {
            @Override
            public void onItemClick(String item) {
                int sendGiftNum;
                if (item.equals("全部")) {
                    sendGiftNum = TAG_SEND_ALL_GIFT;
                } else {
                    sendGiftNum = Integer.parseInt(item);
                }
                hideGiftNum();
                sendGift(mSelectedGiftType, sendGiftNum);
            }
        });
        if (DataHelper.INSTANCE.getConfigLongClickTips()) {
            longClickIv.setVisibility(VISIBLE);
        } else {
            longClickIv.setVisibility(GONE);
        }
    }

    public void showGiftNum() {
        if (mSelectedGiftType != GiftManager.GIFT_TYPE_CHEST) {
            new GiftNumDialog(getMContext(),new GiftNumDialog.OnGiftNum(){
                @Override
                public void onSend(int sendGiftNum) {
                    sendGift(mSelectedGiftType, sendGiftNum);
                }
            }).show();
//            giftNumRv.setVisibility(VISIBLE);
//            longClickIv.setVisibility(GONE);
//            DataHelper.INSTANCE.saveConfigLongClickTips(false);
        }
    }

    public void hideGiftNum() {
        giftNumRv.setVisibility(GONE);
    }

    private boolean isUserInMic() {
        for (int i = 0; i < mMicUserlist.size(); i++) {
            //不默认选中房主
            if (mMicUserlist.get(i).getUser_id() == GiftManager.getInstance().getUserId()) {
                if (GiftManager.getInstance().isNeedSeleted()) {
                    mMicUserlist.get(i).setSelected(true);
                }
                return true;
            }
        }
        return false;
    }

    private void initMicList() {
        mMicUserlist.clear();
        mMicUserlist.addAll(GiftManager.getInstance().getMicUserList());
        if (mMicUserlist.size() > 0) {
            if (isUserInMic()) {
                contentLL.setSelected(false);
                micUserLL.setVisibility(VISIBLE);
                userInfoLL.setVisibility(GONE);
//            //麦上用户不能给自己送礼
//            for (GiftInfoBean.ListBean item:mMicUserlist){
//                if (item.getUser_id() == DataHelper.INSTANCE.getUserInfo().getUser_id()){
//                    mMicUserlist.remove(item);
//                    break;
//                }
//            }
                mUserListAdapter.setData(mMicUserlist);
            } else {
                contentLL.setSelected(true);
                ImgUtil.INSTANCE.loadCircleImg(getMContext(), GiftManager.getInstance().getFace(), faceIv, R.drawable.common_avter_placeholder);
                nickNameTv.setText(GiftManager.getInstance().getNickName());
                userInfoLL.setVisibility(VISIBLE);
                micUserLL.setVisibility(GONE);
                GiftInfoBean.ListBean bean = new GiftInfoBean.ListBean();
                bean.setSelected(true);
                bean.setNickname(GiftManager.getInstance().getNickName());
                bean.setUser_id(GiftManager.getInstance().getUserId());
                bean.setFace(GiftManager.getInstance().getFace());
                bean.setMystery(GiftManager.getInstance().getMystery());
                //不在麦上用户
                bean.setType(9);
                mMicUserlist.clear();
                mMicUserlist.add(bean);
            }
        } else {
            micUserLL.setVisibility(GONE);
            userInfoLL.setVisibility(GONE);
        }
    }

    @SuppressLint("SetTextI18n")
    private void changeTab(int type) {
        mSelectedGiftType = type;
        moneyTv.setSelected(false);
        bxExplianTv.setVisibility(View.INVISIBLE);
        if (type == GiftManager.GIFT_TYPE_NOBLE) {
            moneyTv.setSelected(true);
//            sumTv.setVisibility(VISIBLE);
            moneyTv.setText("" + GiftManager.getInstance().getAccountBalance());
//            sumTv.setText("1");
//            sendGiftNum = 1;
            initGiftNumList(true);
            pacTotalTv.setVisibility(View.INVISIBLE);

        }

        if (type == GiftManager.GIFT_TYPE_GUARD) {
            moneyTv.setSelected(true);
//            sumTv.setVisibility(GONE);
            moneyTv.setText("" + GiftManager.getInstance().getAccountBalance());
//            sumTv.setText("1");
//            sendGiftNum = 1;
            initGiftNumList(true);
            pacTotalTv.setVisibility(View.INVISIBLE);
        }
        if (type == GiftManager.GIFT_TYPE_COMMON) {
            moneyTv.setSelected(true);
//            sumTv.setVisibility(VISIBLE);
            moneyTv.setText("" + GiftManager.getInstance().getAccountBalance());
//            sumTv.setText("1");
//            sendGiftNum = 1;
            initGiftNumList(false);
            pacTotalTv.setVisibility(View.INVISIBLE);
        }
        if (type == GiftManager.GIFT_TYPE_PACK) {
            moneyTv.setSelected(true);
//            sumTv.setVisibility(VISIBLE);
            moneyTv.setText("" + GiftManager.getInstance().getAccountBalance());
//            sumTv.setText("1");
//            sendGiftNum = 1;
            initGiftNumList(true);
            pacTotalTv.setVisibility(VISIBLE);
        }
        if (type == GiftManager.GIFT_TYPE_CHEST || type == GiftManager.GIFT_TYPE_EXPRESS) {
            moneyTv.setSelected(true);
//            sumTv.setVisibility(GONE);
            moneyTv.setText("" + GiftManager.getInstance().getAccountBalance());
//            sumTv.setText("1");
//            sendGiftNum = 1;
            pacTotalTv.setVisibility(View.INVISIBLE);
        }
        if (type == GiftManager.GIFT_TYPE_CHEST) {
            bxExplianTv.setVisibility(VISIBLE);
        }
    }

    void setPackTotal(String msg) {
        GiftManager.getInstance().setPackTotal(Integer.parseInt(msg));
        pacTotalTv.setText(String.format("总价值：%s钻石", msg));
    }

    private void initGiftNumList(boolean needAll) {
        mSumlist.clear();
        List<String> nums = Arrays.asList("1314", "520", "188", "99", "66", "10", "1");
        List<String> dess = Arrays.asList("一生一世", "我爱你", "要抱抱", "长长久久", "一切顺利", "十全十美", "一心一意");
        if (needAll) {
            GiftNumSelectBean bean = new GiftNumSelectBean();
            bean.setNum("全部");
            bean.setDes("");
            mSumlist.add(bean);
        }
        for (int i = 0; i < nums.size(); i++) {
            GiftNumSelectBean bean = new GiftNumSelectBean();
            bean.setNum(nums.get(i));
            bean.setDes(dess.get(i));
            mSumlist.add(bean);
        }
        giftNumAdapter.setDatas(mSumlist);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
//        if (id == R.id.tv_sum){
//            GiftNumSelectPopWindow numGiftNumSelectPopWindow = new GiftNumSelectPopWindow(getContext(), mSumlist, new OnGifiNumSeletedListener() {
//                @Override
//                public void onItemClick(String item) {
//                    sumTv.setText(item);
//                    if (item.equals("全部")){
//                        sendGiftNum = TAG_SEND_ALL_GIFT;
//                    }else{
//                        sendGiftNum = Integer.parseInt(item);
//                    }
//                }
//            });
//            numGiftNumSelectPopWindow.show(sumLL);
//        }
//        if (id == R.id.tv_send){
//            sendGift(mSelectedGiftType);
//        }


        if (id == R.id.iv_all_selected) {
            if (allSeletedCbk.isSelected()) {
                mUserListAdapter.setAllUnSeleted();
                allSeletedCbk.setSelected(false);
            } else {
                mUserListAdapter.setAllSeleted();
                allSeletedCbk.setSelected(true);
            }
        }
        if (id == R.id.tv_money) {
            if (onGiftListener != null) {
                onGiftListener.showPayActivity();
            }
        }
        if (id == R.id.tv_bx_explain) {
            //显示宝箱礼物
            WebActivity.start(getContext(), "http://decibel-web.cnciyin.com/blindBox", "盲盒说明");

        }
    }


    void setPackFragment(BaseChildFragment fragment) {
        giftPackFragment = fragment;
    }

//    void setGuardFragment(BaseChildFragment fragment){
//        guardFragment = fragment;
//    }

    public void sendGift(int type, int number) {
        GiftBean.DataBean selectedGift = null;
        if (DataHelper.INSTANCE.getIsYoungModelSetting() == 1) {
            ToastUtil.INSTANCE.suc(getMContext(), "青少年模式，请关闭后使用~");
            return;
        }
        List<GiftInfoBean.ListBean> userLisr = getUserList();
        if (userLisr.size() == 0) {
            ToastUtil.INSTANCE.suc(getMContext(), "请选择收礼人");
            return;
        }
        selectedGift = GiftManager.getInstance().getSelectedGift(type);
        if (selectedGift == null) {
            ToastUtil.INSTANCE.suc(getMContext(), "请选择礼物");
            return;
        }

        int num = number;
        //送全部礼物
        if (num == TAG_SEND_ALL_GIFT) {
            num = selectedGift.getNumber();
        }
//        if (type == GiftManager.GIFT_TYPE_LUCK){
//            sendLuckGift(selectedGift,num,userLisr);
//        }
        if (type == GiftManager.GIFT_TYPE_COMMON) {
            sendCommonGift(selectedGift, num, userLisr);
        }
        if (type == GiftManager.GIFT_TYPE_PACK) {
            sendPackGift(selectedGift, num, userLisr);
        }
        if (type == GiftManager.GIFT_TYPE_GUARD) {
            sendGuardGift(selectedGift, num, userLisr);
        }
        if (type == GiftManager.GIFT_TYPE_CHEST) {
            sendChestGift(selectedGift, num, userLisr);
        }
        if (type == GiftManager.GIFT_TYPE_NOBLE) {
            sendNobleGift(selectedGift, num, userLisr);
        }
        if (type == GiftManager.GIFT_TYPE_EXPRESS) {
            ExpressGiftDialog dialog = new ExpressGiftDialog(getContext(), new ExpressGiftDialog.OnExpressListener() {

                @Override
                public void onExpress(String note, GiftBean.DataBean gift, List<GiftInfoBean.ListBean> users) {
                    sendExPressGift(gift, note, users);
                }
            }, selectedGift, userLisr);
            dialog.show();
        }
    }


    private void sendPackGift(final GiftBean.DataBean selectedGift, final int sendNum, final List<GiftInfoBean.ListBean> userList) {
        PackGiftSendCallback callback = new PackGiftSendCallback(selectedGift, sendNum, userList);
        callback.setOnGiftListener(onGiftListener);
        callback.setOnGiftSendCallback(new OnGiftSendCallback<DiamondsBean>() {
            @Override
            public void onSuccess(DiamondsBean o) {
                giftPackFragment.refresh(selectedGift.getId(), sendNum * userList.size(), selectedGift.getPrice());
            }
        });
        NetService.Companion.getInstance(getMContext()).givePackGift(GiftManager.getInstance().getRoomId()
                , sendNum
                , String.valueOf(DataHelper.INSTANCE.getUID())
                , getUserUids(userList)
                , String.valueOf(selectedGift.getId()), callback);
    }


    private List<GiftInfoBean.ListBean> getUserList() {
        mGiftUserList.clear();
        for (GiftInfoBean.ListBean userInfo : mMicUserlist) {
            if (userInfo.isSelected()) {
                mGiftUserList.add(userInfo);
            }
        }
        return mGiftUserList;
    }

    //参数格式{"0":10356}
    private String getUserUids(List<GiftInfoBean.ListBean> list) {
        JSONObject object = new JSONObject();
        for (GiftInfoBean.ListBean userInfo : list) {
            if (userInfo.isSelected()) {
                try {
                    object.put(String.valueOf(userInfo.getType()), userInfo.getUser_id());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        return object.toString();
    }

    private void sendChestGift(final GiftBean.DataBean selectedGift, final int num, final List<GiftInfoBean.ListBean> userList) {
        ChestGiftSendCallback callback = new ChestGiftSendCallback(selectedGift, num, userList);
        callback.setOnGiftListener(onGiftListener);
        callback.setOnGiftSendCallback(new OnGiftSendCallback<ChestGiftBean>() {
            @Override
            public void onSuccess(ChestGiftBean giftBean) {
                GiftManager.getInstance().setAccountBalance(giftBean.getBalance());
                moneyTv.setText("" + giftBean.getBalance());
            }
        });
        NetService.Companion.getInstance(getMContext()).giveChestGift(GiftManager.getInstance().getRoomId()
                , num
                , String.valueOf(DataHelper.INSTANCE.getUID())
                , getUserUids(userList)
                , String.valueOf(selectedGift.getId()), callback);
    }

    private void sendGuardGift(final GiftBean.DataBean selectedGift, final int num, final List<GiftInfoBean.ListBean> userList) {
        CommonGiftSendCallback commonGiftSendCallback = new CommonGiftSendCallback(selectedGift, num, userList);
        commonGiftSendCallback.setOnGiftListener(onGiftListener);
        commonGiftSendCallback.setOnGiftSendCallback(new OnGiftSendCallback<DiamondsBean>() {
            @Override
            public void onSuccess(DiamondsBean diamondsBean) {
//                if (guardFragment != null){
//                    guardFragment.refresh(selectedGift.getId(),0,0);
//                }
            }
        });
//        NetService.Companion.getInstance(getMContext()).giveGuardGiftDT(GiftManager.getInstance().getRoomId()
//                ,num
//                ,String.valueOf(DataHelper.INSTANCE.getUID())
//                , String.valueOf(userList.get(0).getUser_id())
//                , String.valueOf(selectedGift.getId())
//                , commonGiftSendCallback);
    }

    private void sendNobleGift(final GiftBean.DataBean selectedGift, final int num, final List<GiftInfoBean.ListBean> userList) {
        if (!DataHelper.INSTANCE.getUserInfo().getNoble_status()) {
            ToastUtil.INSTANCE.suc(getMContext(), "开通贵族才能赠送哦～");
            return;
        }
        CommonGiftSendCallback callback = new CommonGiftSendCallback(selectedGift, num, userList);
        callback.setOnGiftListener(onGiftListener);
        callback.setOnGiftSendCallback(new OnGiftSendCallback<DiamondsBean>() {
            @Override
            public void onSuccess(DiamondsBean diamondsBean) {
                if (!TextUtils.isEmpty(diamondsBean.getBalance())) {
                    GiftManager.getInstance().setAccountBalance(Integer.valueOf(diamondsBean.getBalance()));
                    moneyTv.setText("" + diamondsBean.getBalance());
                }
            }
        });
        NetService.Companion.getInstance(getMContext()).giveNobleGift(GiftManager.getInstance().getRoomId()
                , num
                , String.valueOf(DataHelper.INSTANCE.getUID())
                , getUserUids(userList)
                , String.valueOf(selectedGift.getId()), callback);
    }

    private void sendCommonGift(final GiftBean.DataBean selectedGift, final int num, final List<GiftInfoBean.ListBean> userList) {
        CommonGiftSendCallback callback = new CommonGiftSendCallback(selectedGift, num, userList);
        callback.setOnGiftListener(onGiftListener);
        callback.setOnGiftSendCallback(new OnGiftSendCallback<DiamondsBean>() {
            @Override
            public void onSuccess(DiamondsBean diamondsBean) {
                if (!TextUtils.isEmpty(diamondsBean.getBalance())) {
                    GiftManager.getInstance().setAccountBalance(Integer.valueOf(diamondsBean.getBalance()));
                    moneyTv.setText("" + diamondsBean.getBalance());
                }
            }
        });
        NetService.Companion.getInstance(getMContext()).giveCommonGift(GiftManager.getInstance().getRoomId()
                , num
                , String.valueOf(DataHelper.INSTANCE.getUID())
                , getUserUids(userList)
                , String.valueOf(selectedGift.getId())
                , callback);
    }

    private void sendExPressGift(final GiftBean.DataBean selectedGift, String note, final List<GiftInfoBean.ListBean> userList) {
        ExpressGiftSendCallback callback = new ExpressGiftSendCallback(selectedGift, 1, userList, note);
        callback.setOnGiftListener(onGiftListener);
        callback.setOnGiftSendCallback(new OnGiftSendCallback<DiamondsBean>() {
            @Override
            public void onSuccess(DiamondsBean diamondsBean) {
                if (!TextUtils.isEmpty(diamondsBean.getBalance())) {
                    GiftManager.getInstance().setAccountBalance(Integer.parseInt(diamondsBean.getBalance()));
                    moneyTv.setText("" + diamondsBean.getBalance());
                }
            }
        });
        NetService.Companion.getInstance(getMContext()).giveExpressGift(GiftManager.getInstance().getRoomId()
                , 1
                , String.valueOf(DataHelper.INSTANCE.getUID())
                , getUserUids(userList)
                , String.valueOf(selectedGift.getId())
                , note
                , callback);
    }


}
