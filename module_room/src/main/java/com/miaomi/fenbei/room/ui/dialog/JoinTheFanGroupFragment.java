package com.miaomi.fenbei.room.ui.dialog;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.miaomi.fenbei.base.bean.AnchorRankBean;
import com.miaomi.fenbei.base.bean.DtFansBean;
import com.miaomi.fenbei.base.bean.GiftInfoBean;
import com.miaomi.fenbei.base.bean.JoinTheFanGroupBean;
import com.miaomi.fenbei.base.core.BaseFragment;
import com.miaomi.fenbei.base.core.dialog.CommonDialog;
import com.miaomi.fenbei.base.net.Callback;
import com.miaomi.fenbei.base.net.NetService;
import com.miaomi.fenbei.base.util.ToastUtil;
import com.miaomi.fenbei.gift.listener.OnGifiNumSeletedListener;
import com.miaomi.fenbei.room.ChatRoomManager;
import com.miaomi.fenbei.room.R;
import com.miaomi.fenbei.room.ui.callback.OnRadioFansSeletedListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class JoinTheFanGroupFragment extends BaseFragment {

    private JoinTheFanGroupAdapter joinTheFanGroupAdapter;
    private RecyclerView functionRv;
    private ImageView tagIv;
    private int fansType = 1;
    private int dayNumber = 7;
    private TextView dayTv;
    private TextView priceTv;
    private LinearLayout taskLL;
    private DtFansBean mDtFansBean;
    private LinearLayout dayAndPriceLL;
    private TextView buyDesTv;
    private TextView nameTv;
    private ImageView isAttentionIv;
    private ImageView isListenIv;
    private ImageView isSendGiftIv;
    private ImageView isRoomChatIv;
    private TextView lrDaysTv;
    private TextView timeTv;
    private LinearLayout timeLL;
    private TextView joinTv;
    private NestedScrollView nestContent;


    public JoinTheFanGroupFragment(int fansType) {
        this.fansType = fansType;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_join_the_fan_grounp;
    }

    @Override
    public void initView(@NotNull View view) {
        timeTv = view.findViewById(R.id.tv_time);
        nestContent = view.findViewById(R.id.nest_content);
        functionRv = view.findViewById(R.id.rv_function);
        tagIv = view.findViewById(R.id.iv_tag);
        buyDesTv = view.findViewById(R.id.tv_buy_des);
        nameTv = view.findViewById(R.id.tv_name);
        isAttentionIv = view.findViewById(R.id.iv_is_attention);
        isListenIv = view.findViewById(R.id.iv_is_listen);
        isSendGiftIv = view.findViewById(R.id.iv_is_send_gift);
        isRoomChatIv = view.findViewById(R.id.iv_is_room_chat);
        lrDaysTv = view.findViewById(R.id.tv_7_day);
        timeLL = view.findViewById(R.id.ll_time);
        joinTv = view.findViewById(R.id.tv_join);
        if (fansType == JoinTheFanGroupDialog.JOIN_FANS_TYPE_LR){
            joinTv.setVisibility(View.GONE);
        }else{
            joinTv.setVisibility(View.VISIBLE);
        }
        joinTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CommonDialog mCommonDialog = new CommonDialog(getContext());
                mCommonDialog.setTitle("提示");
                mCommonDialog.setContent("是否花费"+priceTv.getText()+"成为"+mDtFansBean.getZhubo_nickname()+"的"+mDtFansBean.getFan_type());
                mCommonDialog.setLeftBt("否", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mCommonDialog.dismiss();
                    }
                });
                mCommonDialog.setRightBt("是", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bugFans();
                        mCommonDialog.dismiss();
                    }
                });
                mCommonDialog.show();
            }
        });
        dayTv = view.findViewById(R.id.tv_day);
        priceTv = view.findViewById(R.id.tv_price);
        taskLL = view.findViewById(R.id.ll_task);
        dayAndPriceLL = view.findViewById(R.id.ll_day_and_price);
        dayAndPriceLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GiftNumSelectPopWindow giftNumSelectPopWindow = new GiftNumSelectPopWindow(getContext(), mDtFansBean.getConfig(), new OnRadioFansSeletedListener() {
                    @Override
                    public void onItemClick(DtFansBean.ConfigBean item) {
                        dayNumber = item.getDay();
                        dayTv.setText(""+item.getDay()+"天");
                        priceTv.setText(""+item.getZuan()+"钻");
                    }
                });
                giftNumSelectPopWindow.show(dayAndPriceLL);
            }
        });
        joinTheFanGroupAdapter = new JoinTheFanGroupAdapter(getContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        functionRv.setLayoutManager(linearLayoutManager);
        functionRv.setAdapter(joinTheFanGroupAdapter);
        taskLL.setVisibility(View.GONE);
        if (fansType == JoinTheFanGroupDialog.JOIN_FANS_TYPE_TG){
            tagIv.setImageResource(R.drawable.icn_dt_fans_tg);
            joinTv.setBackgroundResource(R.drawable.bg_buy_fans_tg);
        }
        if (fansType == JoinTheFanGroupDialog.JOIN_FANS_TYPE_ZA){
            tagIv.setImageResource(R.drawable.icn_dt_fans_za);
            joinTv.setBackgroundResource(R.drawable.bg_buy_fans_za);
        }
        if (fansType == JoinTheFanGroupDialog.JOIN_FANS_TYPE_LR){
            tagIv.setImageResource(R.drawable.icn_dt_fans_lr);
            taskLL.setVisibility(View.VISIBLE);
        }
        if (fansType == JoinTheFanGroupDialog.JOIN_FANS_TYPE_XD){
            tagIv.setImageResource(R.drawable.icn_dt_fans_xd);
            joinTv.setBackgroundResource(R.drawable.bg_buy_fans_xd);
        }
        getData();
    }

    private void getData(){
        NetService.Companion.getInstance(getContext()).getFangroupAnchorFanConfig(ChatRoomManager.INSTANCE.getRoomId(), fansType, new Callback<DtFansBean>() {
            @Override
            public void onSuccess(int nextPage, DtFansBean bean, int code) {
                mDtFansBean = bean;
                if (fansType != JoinTheFanGroupDialog.JOIN_FANS_TYPE_LR){
                    if (bean.getConfig().size() > 0){
                        priceTv.setText(bean.getConfig().get(0).getZuan()+"钻");
                        dayTv.setText(bean.getConfig().get(0).getDay()+"天");
                    }
                    buyDesTv.setVisibility(View.VISIBLE);
                    lrDaysTv.setVisibility(View.GONE);
                    dayAndPriceLL.setVisibility(View.VISIBLE);
                    buyDesTv.setText("购买"+mDtFansBean.getFan_type()+"赠送等价热力值");
                }else{
                    buyDesTv.setVisibility(View.GONE);
                    lrDaysTv.setVisibility(View.VISIBLE);
                    dayAndPriceLL.setVisibility(View.GONE);
                }
                if (mDtFansBean.getIs_fan() == 1){
                    timeLL.setVisibility(View.VISIBLE);
                    timeTv.setText(mDtFansBean.getShen());
                    joinTv.setText("立即续费");
                }else{
                    timeLL.setVisibility(View.GONE);
                    joinTv.setText("立即加入");
                }
                nameTv.setText("成为 "+mDtFansBean.getZhubo_nickname()+" 的"+mDtFansBean.getFan_type());
                joinTheFanGroupAdapter.setData(bean.getImg());
                isAttentionIv.setSelected(bean.getIs_attention() == 1);
                isListenIv.setSelected(bean.getIs_listen() == 1);
                isSendGiftIv.setSelected(bean.getIs_send_gift() == 1);
                isRoomChatIv.setSelected(bean.getIs_room_chat() == 1);
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

    private void bugFans(){
        NetService.Companion.getInstance(getContext()).buyFangroupAnchorFan(ChatRoomManager.INSTANCE.getRoomId(), fansType,dayNumber, new Callback<AnchorRankBean>() {
            @Override
            public void onSuccess(int nextPage, AnchorRankBean bean, int code) {

            }

            @Override
            public void onSuccessHasMsg(@NotNull String msg, AnchorRankBean bean, int code) {
                ToastUtil.INSTANCE.suc(getContext(),msg);
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
