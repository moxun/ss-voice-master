package com.miaomi.fenbei.gift.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.miaomi.fenbei.base.bean.DiamondsBean;
import com.miaomi.fenbei.base.bean.GiftNumSelectBean;
import com.miaomi.fenbei.base.core.BaseFragment;
import com.miaomi.fenbei.base.net.Callback;
import com.miaomi.fenbei.gift.GiftManager;
import com.miaomi.fenbei.gift.R;
import com.miaomi.fenbei.base.bean.GiftBean;
import com.miaomi.fenbei.base.net.NetService;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class PrivateGiftFragment extends BaseFragment {

    private TextView moneyTv;
//    private TextView sendTv;
    private String chatId = "";
//    private TextView sumTv;
    public final static String TAG_CHAT_ID = "TAG_CHAT_ID";
//    private LinearLayout sumLL;
    private List<GiftNumSelectBean> mSumlist = new ArrayList<>();
    private int sendGiftNum = 1;
//    private TextView payTv;
    private CommonPrivateGiftFragment commonPrivateGiftFragment;


    public static PrivateGiftFragment newInstance(String chatId) {
        PrivateGiftFragment fragment = new PrivateGiftFragment();
        Bundle bundle = new Bundle();
        bundle.putString(TAG_CHAT_ID, chatId);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public int getLayoutId() {
        return R.layout.gift_layout_gift_private;
    }

    @Override
    public void initView(@NotNull View view) {
        chatId = getArguments().getString(TAG_CHAT_ID);
        moneyTv = view.findViewById(R.id.tv_money);
//        sendTv = view.findViewById(R.id.tv_send);
//        sumLL = view.findViewById(R.id.ll_sum);
//        sumTv = view.findViewById(R.id.tv_sum);
//        payTv = view.findViewById(R.id.tv_money);
//        mSumlist.clear();
//        mSumlist.addAll(Arrays.asList("1","10","38","66","99","188","520","1314"));
        moneyTv.setSelected(true);
        moneyTv.setText(String.valueOf(GiftManager.getInstance().getAccountBalance()));
        commonPrivateGiftFragment = new CommonPrivateGiftFragment();
        getChildFragmentManager().beginTransaction().add(R.id.fl_gift, commonPrivateGiftFragment).commit();
//        sendTv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                sendGift(GiftManager.getInstance().getSelectedGift(GiftManager.GIFT_TYPE_COMMON_PRIVATE));
//            }
//        });
//        sumTv.setText("1");
//        sumLL.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                GiftNumSelectPopWindow numGiftNumSelectPopWindow = new GiftNumSelectPopWindow(getContext(), mSumlist, new OnGifiNumSeletedListener() {
//                    @Override
//                    public void onItemClick(String item) {
//                        sumTv.setText(item);
//                        sendGiftNum = Integer.valueOf(item);
//                    }
//                });
//                numGiftNumSelectPopWindow.show(sumLL);
//            }
//        });
//        payTv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ARouter.getInstance().build("/app/pay").navigation();
//            }
//        });
        getDiamonds();
    }

    public OnSendSuccess onSendSuccess;

    public interface OnSendSuccess {
        void onSuc(GiftBean.DataBean gift);

        void onFail(String msg);
    }

    public void setOnSendSuccess(OnSendSuccess onSendSuccess) {
        this.onSendSuccess = onSendSuccess;
    }

    private void getDiamonds() {
        NetService.Companion.getInstance(getContext()).getDiamonds(new Callback<DiamondsBean>() {
            @Override
            public void onSuccess(int nextPage, DiamondsBean bean, int code) {
                GiftManager.getInstance().setAccountBalance(Integer.parseInt(bean.getBalance()));
                moneyTv.setText(bean.getBalance());
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

    public void sendGift(final GiftBean.DataBean gift) {
        if (gift == null) {
            return;
        }
        gift.setNumber(sendGiftNum);
        NetService.Companion.getInstance(getContext()).sendPrivateGift(chatId, String.valueOf(gift.getId()), String.valueOf(gift.getNumber()), new Callback<DiamondsBean>() {
            @Override
            public void onSuccess(int nextPage, DiamondsBean bean, int code) {
                GiftManager.getInstance().setAccountBalance(Integer.parseInt(bean.getBalance()));
                moneyTv.setText(bean.getBalance());
                if (onSendSuccess != null) {
                    onSendSuccess.onSuc(gift);
                }

            }

            @Override
            public void onError(@NotNull String msg, @NotNull Throwable throwable, int code) {
                if (onSendSuccess != null) {
                    onSendSuccess.onFail(msg);
                }
            }

            @Override
            public boolean isAlive() {
                return isLive();
            }
        });
    }

    public void refresh() {
        GiftManager.getInstance().resetGiftList(GiftManager.GIFT_TYPE_COMMON_PRIVATE);
        sendGiftNum = 1;
        commonPrivateGiftFragment.refreshCommonGift();
    }
}
