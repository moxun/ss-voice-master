package com.miaomi.fenbei.voice.ui.noble;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;

import com.alibaba.android.arouter.launcher.ARouter;
import com.miaomi.fenbei.base.bean.BuyNobleBean;
import com.miaomi.fenbei.base.bean.LocalUserBean;
import com.miaomi.fenbei.base.bean.NobleBean;
import com.miaomi.fenbei.base.core.BaseFragment;
import com.miaomi.fenbei.base.core.dialog.CommonDialog;
import com.miaomi.fenbei.base.net.Callback;
import com.miaomi.fenbei.base.net.NetService;
import com.miaomi.fenbei.base.util.DataHelper;
import com.miaomi.fenbei.base.util.ImgUtil;
import com.miaomi.fenbei.base.util.TimeUtil;
import com.miaomi.fenbei.base.util.ToastUtil;
import com.miaomi.fenbei.base.widget.ALBBPHTextView;
import com.miaomi.fenbei.base.widget.DCBTextView;
import com.miaomi.fenbei.base.widget.xrecyclerview.XRecyclerView;
import com.miaomi.fenbei.room.ChatRoomManager;
import com.miaomi.fenbei.voice.R;
import com.miaomi.fenbei.voice.dialog.OpenVipNobleDialog;
import com.miaomi.fenbei.voice.dialog.RenewVipNobleDialog;
import com.miaomi.fenbei.voice.ui.pay.PayActivity;
import com.opensource.svgaplayer.SVGACallback;
import com.opensource.svgaplayer.SVGADrawable;
import com.opensource.svgaplayer.SVGADynamicEntity;
import com.opensource.svgaplayer.SVGAImageView;
import com.opensource.svgaplayer.SVGAParser;
import com.opensource.svgaplayer.SVGAVideoEntity;

import org.jetbrains.annotations.NotNull;

import java.net.MalformedURLException;
import java.net.URL;

public class NobleCenterFragment extends BaseFragment {

    public final static String TAG_NOBLE_DATA = "TAG_NOBLE_DATA";
    private NobleBean nobleBean;
    private SVGAImageView bgSvgaIv;
    private XRecyclerView mRecyclerView;
    private TextView submitTv;
    private ALBBPHTextView privilegeNumTv;
    private DCBTextView content1Tv;
    private DCBTextView content2Tv;
    NobleCenterAdapter mNobleCenterAdapter;
    //    NobleInvateDialog dialog;
    CommonDialog payDialog;
    private int positon;
    private SVGAImageView svgaImageView;
    private SVGAParser parser;
    private ImageView bgIv;
    private TextView vipLevelTv;
    private DCBTextView openVipLevelTv;

//    public static NobleCenterFragment newInstance(NobleBean bean){
//        NobleCenterFragment fragment = new NobleCenterFragment();
//        Bundle bundle = new Bundle();
//        bundle.putSerializable(TAG_NOBLE_DATA,bean);
//        fragment.setArguments(bundle);
//        return fragment;
//    }

    public static NobleCenterFragment newInstance(int positon){
        NobleCenterFragment fragment = new NobleCenterFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(TAG_NOBLE_DATA,positon);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public int getLayoutId() {
        return R.layout.fragment_noble_center;
    }

    @Override

    public void initView(@NotNull View view) {
        positon = getArguments().getInt(TAG_NOBLE_DATA);
        nobleBean = ((NobleCenterActivity)getActivity()).mNobleList.get(positon);
        mNobleCenterAdapter = new NobleCenterAdapter(getContext());
        mNobleCenterAdapter.setData(nobleBean.getPrivilege_list());
        svgaImageView = view.findViewById(R.id.iv_svg);
        submitTv = view.findViewById(R.id.tv_submit);
        mRecyclerView = view.findViewById(R.id.recycler_view);
        content1Tv = view.findViewById(R.id.tv_cotent1);
        content2Tv = view.findViewById(R.id.tv_cotent2);
        vipLevelTv = view.findViewById(R.id.vip_level_tv);
//       View headView = LayoutInflater.from(getContext()).inflate(R.layout.head_fragment_noble_center,null,false);
        bgSvgaIv = view.findViewById(R.id.iv_svga_bg);
        bgIv = view.findViewById(R.id.iv_bg);
        privilegeNumTv = view.findViewById(R.id.tv_privilege_num);
        openVipLevelTv=view.findViewById(R.id.open_vip_level_tv);
//        mRecyclerView.addHeaderView(headView);
        mRecyclerView.setPullRefreshEnabled(false);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(),3));
        mRecyclerView.setAdapter(mNobleCenterAdapter);
        svgaImageView.setVisibility(View.GONE);
        submitTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (nobleBean.isOpen_status()){
                    RenewVipNobleDialog renewVipNobleDialog=new RenewVipNobleDialog(nobleBean);
                    renewVipNobleDialog.show(getFragmentManager());
                    renewVipNobleDialog.setOnRenewVip(new RenewVipNobleDialog.oNrenewVipNoble() {
                        @Override
                        public void onPay() {
                            bugNoble("");
                        }
                    });
//                    if (payDialog == null){
//                        payDialog = new CommonDialog(getContext());
//                        payDialog.setTitle("支付");
//                        payDialog.setContent("确认消费"+nobleBean.getRenew_price()+"钻石续费"+nobleBean.getName()+"爵位");
//                        payDialog.setLeftBt("取消", new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                            }
//                        });
//                        payDialog.setRightBt("确认", new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                bugNoble();
//                            }
//                        });
//                    }
//                    payDialog.show();
                }else {
                    OpenVipNobleDialog openVipNobleDialog=new OpenVipNobleDialog(nobleBean);
                    openVipNobleDialog.show(getFragmentManager());
                    openVipNobleDialog.setOnOpenVip(new OpenVipNobleDialog.oNopenVipNoble() {
                        @Override
                        public void onPay(String invite_uid) {
                            bugNoble(invite_uid);
                        }
                    });
//                    CommonDialog upDialog = new CommonDialog(getContext());
//                    upDialog.setTitle("提示");
//                    upDialog.setContent("确认消费"+nobleBean.getPrice()+"钻石开通"+nobleBean.getName()+"爵位？");
//                    upDialog.setLeftBt("取消", it -> upDialog.dismiss());
//                    upDialog.setRightBt("确认", it -> {
//                        bugNoble();
//                        upDialog.dismiss();
//                    });
//                    upDialog.show();
//                    if (dialog == null){
//                        dialog = new NobleInvateDialog(getContext());
//                    }
//                    dialog.setOnEditListener(code -> bugNoble());
//                    dialog.show();
//
//                    dialog.setTitle("确认消费"+nobleBean.getPrice()+"钻石开通"+nobleBean.getName()+"爵位？");
                }
            }
        });

        privilegeNumTv.setText("("+nobleBean.getPrivilege_num()+")");
        vipLevelTv.setText("贵族/" + nobleBean.getName());

        String openVipLevelMsg = "开通"+nobleBean.getName()+"赠送:  <font color=\"#FFFFFF\">"+nobleBean.getRenew_return_diamonds()+"</font> 钻石 + <font color=\"#FFFFFF\">"+nobleBean.getWealth_value()+"</font> 财富值";
        openVipLevelTv.setText(Html.fromHtml(openVipLevelMsg));

        try {
            showBgSvgaGiftAnim();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
//        ImgUtil.INSTANCE.loadRoundImg(getContext(),nobleBean.getBackground_img(),bgIv,6, R.drawable.common_default_round);
        if (nobleBean.isOpen_status()){
            submitTv.setText("立即续费");
                if (nobleBean.getProtect_status() == 0){
                    content1Tv.setText(getSpannableString(true,String.valueOf(nobleBean.getRenew_price()),String.valueOf(nobleBean.getExpired_days())));
                    content2Tv.setSelected(false);
                content2Tv.setText("续费: "+nobleBean.getRenew_price()+" 钻石/30天，送等价钻石");
//                content2Tv.setText(nobleBean.getExpired_time()+"到期");
            }else{
                    content1Tv.setText("贵族已过期");
//                content2Tv.setSelected(true);
//                content2Tv.setText("贵族已过期");
            }
        }else{
            submitTv.setText("立即开通");
            content2Tv.setSelected(false);
            content1Tv.setText(getSpannableString(false,String.valueOf(nobleBean.getPrice()),String.valueOf(nobleBean.getReturn_diamonds())));
//            content2Tv.setText("续费"+nobleBean.getRenew_price()+"钻石/月，赠送"+nobleBean.getRenew_return_diamonds()+"钻石");
            content2Tv.setText("续费: "+nobleBean.getRenew_price()+"钻石/30天， 送等价钻石");
//            content2Tv.setText(getSpannableString(true,String.valueOf(nobleBean.getRenew_price()),String.valueOf(nobleBean.getRenew_return_diamonds())));
        }
    }

    private SpannableString getSpannableString(boolean isOpen,String price,String returnDiamon){
        String text ;
        if (isOpen){
            text = "有效期剩余: "+returnDiamon+" 天";
//            text = "续费"+price+"钻石/月，赠送"+returnDiamon+"钻石";
        }else{
//            text = "首开"+price+"钻石/月，赠送"+returnDiamon+"钻石";
            text = "开通"+nobleBean.getName()+": "+price+" 钻石/30天";
        }
        SpannableString string = new SpannableString(text);
//        string.setSpan(new ForegroundColorSpan(Color.parseColor("#D8A02E")), 2, 2+price.length()+2,
//                Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        if (isOpen){
            string.setSpan(new ForegroundColorSpan(Color.parseColor("#D97030")), 6, text.length()-1,
                    Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        }else{
            string.setSpan(new ForegroundColorSpan(Color.parseColor("#D97030")), 3+nobleBean.getName().length(), text.length()-6,
                    Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        }

        return string;
    }

    private void bugNoble(String invite_uid){

        NetService.Companion.getInstance(getContext()).bugNoble(nobleBean.getId(), ChatRoomManager.INSTANCE.getRoomId(), invite_uid,new Callback<BuyNobleBean>() {
            @Override
            public void onSuccess(int nextPage, BuyNobleBean bean, int code) {
//                if (dialog != null){
//                    dialog.dismiss();
//                }
                ToastUtil.INSTANCE.i(getContext(),bean.getMsg());
                LocalUserBean localUser = DataHelper.INSTANCE.getUserInfo();
                localUser.setNoble_status(true);
                DataHelper.INSTANCE.updatalUserInfo(localUser);
                nobleBean.setOpen_status(true);
                nobleBean.setExpired_time(TimeUtil.getTime(bean.getExpired_at()*1000));
                submitTv.setText("立即续费");
                content1Tv.setText(getSpannableString(true,String.valueOf(nobleBean.getRenew_price()),String.valueOf(nobleBean.getRenew_return_diamonds())));
                content2Tv.setSelected(false);
                content2Tv.setText("续费: "+nobleBean.getRenew_price()+" 钻石/30天，送等价钻石");
//                content2Tv.setText(nobleBean.getExpired_time()+"到期");
                if (!TextUtils.isEmpty(bean.getSvg())){
                    showSvgaGiftAnim(bean.getSvg(),bean.getMsg());
//                    if (!TextUtils.isEmpty(ChatRoomManager.INSTANCE.getRoomId())){
//                        ChatRoomManager.INSTANCE.sendNobleMsg(bean.getSvg(),msg);
//                    }
                }

            }


            @Override
            public void onError(@NotNull String msg, @NotNull Throwable throwable, int code) {
                if (code == 1004){
                    ARouter.getInstance().build("/app/pay").withInt(PayActivity.TAG_SELECTED_POSITION,5).navigation();
                }
                ToastUtil.INSTANCE.i(getContext(),msg);
            }

            @Override
            public boolean isAlive() {
                return isLive();
            }
        });
    }

    private void showSvgaGiftAnim(String url, final String content){
        svgaImageView.setVisibility(View.VISIBLE);
        if (parser == null){
            parser = new SVGAParser(getContext());
        }
        svgaImageView.setCallback(new SVGACallback() {
            @Override
            public void onPause() {

            }

            @Override
            public void onFinished() {
                svgaImageView.setVisibility(View.GONE);
            }

            @Override
            public void onRepeat() {
                svgaImageView.stopAnimation();
            }

            @Override
            public void onStep(int i, double v) {

            }
        });
        try {
            parser.decodeFromURL(new URL(url), new SVGAParser.ParseCompletion() {
                @Override
                public void onComplete(@NotNull SVGAVideoEntity videoItem) {
                    SVGADynamicEntity dynamicItem = new SVGADynamicEntity();
                    SVGADrawable drawable = new SVGADrawable(videoItem, dynamicItem);
                    TextPaint textPaint = new TextPaint();
                    textPaint.setTextSize(28);
                    textPaint.setFakeBoldText(true);
                    textPaint.setARGB(0xff, 0xff, 0xff, 0xff);
                    textPaint.setShadowLayer((float) 0.0, (float) 0.0, (float) 0.0, Color.WHITE);
                    dynamicItem.setDynamicText(content, textPaint, "word");

                    svgaImageView.setImageDrawable(drawable);
                    svgaImageView.startAnimation();
                }
                @Override
                public void onError() {
                }
            });
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    private void showBgSvgaGiftAnim() throws MalformedURLException {
        if (!TextUtils.isEmpty(nobleBean.getBackground_img()) && nobleBean.getBackground_img().endsWith(".svga")){
            bgSvgaIv.setVisibility(View.VISIBLE);
            bgIv.setVisibility(View.GONE);
            if (parser == null){
                parser = new SVGAParser(getContext());
            }
            bgSvgaIv.setCallback(new SVGACallback() {
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
            parser.decodeFromURL(new URL(nobleBean.getBackground_img()), new SVGAParser.ParseCompletion() {
                @Override
                public void onComplete(@NotNull SVGAVideoEntity videoItem) {
                    SVGADrawable drawable = new SVGADrawable(videoItem);
                    bgSvgaIv.setImageDrawable(drawable);
                    bgSvgaIv.startAnimation();
                }
                @Override
                public void onError() {
                }
            });
        }else{
            bgIv.setVisibility(View.VISIBLE);
            ImgUtil.INSTANCE.loadImg(getContext(),nobleBean.getIcon(),bgIv);
            bgSvgaIv.setVisibility(View.GONE);
        }

    }
}
