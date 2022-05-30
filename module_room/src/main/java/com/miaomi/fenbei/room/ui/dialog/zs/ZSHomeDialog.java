package com.miaomi.fenbei.room.ui.dialog.zs;

import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.google.gson.Gson;
import com.miaomi.fenbei.base.bean.MsgBean;
import com.miaomi.fenbei.base.bean.MsgGiftBean;
import com.miaomi.fenbei.base.bean.MsgType;
import com.miaomi.fenbei.base.bean.ZSIndexBean;
import com.miaomi.fenbei.base.bean.ZSRewardBean;
import com.miaomi.fenbei.base.bean.ZSUseWaterBean;
import com.miaomi.fenbei.base.core.dialog.BaseBottomDialog;
import com.miaomi.fenbei.base.core.msg.MsgListener;
import com.miaomi.fenbei.base.core.msg.MsgManager;
import com.miaomi.fenbei.base.net.Callback;
import com.miaomi.fenbei.base.net.NetService;
import com.miaomi.fenbei.base.util.DataHelper;
import com.miaomi.fenbei.base.util.TimeUtil;
import com.miaomi.fenbei.base.util.ToastUtil;
import com.miaomi.fenbei.room.ChatRoomManager;
import com.miaomi.fenbei.room.R;
import com.miaomi.fenbei.room.widget.TextBannerView;
import com.opensource.svgaplayer.SVGACallback;
import com.opensource.svgaplayer.SVGADrawable;
import com.opensource.svgaplayer.SVGAImageView;
import com.opensource.svgaplayer.SVGAParser;
import com.opensource.svgaplayer.SVGAVideoEntity;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ZSHomeDialog extends BaseBottomDialog implements MsgListener {

    private ImageView treeLevelIv;
    private SVGAImageView svgaWater;
    private SVGAParser parser;
    private SVGAImageView luckSvgaIv;
    private FrameLayout circus_troupeFl,ferris_wheelFl,gift_bearFl,dessert_houseFl,entranceFl;
    private SVGAImageView circus_troupeSvgaIv,ferris_wheelSvgaIv,gift_bearSvgaIv,dessert_houseSvgaIV,entranceSvga;
    private TextBannerView textBannerView;
    private TextView openSettingTv;
    private TextView waterNumTv,rainNumTv;
    private ProgressBar progressBar;
    private TextView progressbarTv;
    private String rule = "";
    private TextView  timeMinTv,timeSecTv;
    private LinearLayout timeLl;
    private int watertype=1;
//    private boolean isShowReward;
//    private TextView timeTv;
//    private ImageView gardenIv;
    private ImageView road1Iv,road2Iv,road3Iv,road4Iv;
    private ImageView iv_water_custom,iv_water_1,iv_water_10,iv_water_100;
    private CountDownTimer countDownTimer;

    @Override
    public int getLayoutRes() {

        return R.layout.room_dialog_zs_home;
    }

    @Override
    public void bindView(View v) {
        MsgManager.INSTANCE.addMsgListener(this);
//        timeTv = v.findViewById(R.id.tv_time);
        progressBar = v.findViewById(R.id.zs_progressbar);
        progressbarTv = v.findViewById(R.id.tv_progressbar);
        progressBar.setMax(100);
        waterNumTv = v.findViewById(R.id.tv_but_water);
        rainNumTv=v.findViewById(R.id.tv_but_rain);
        treeLevelIv = v.findViewById(R.id.iv_tree_level);
//        luckSvgaIv = v.findViewById(R.id.svga_luck_time);
        svgaWater = v.findViewById(R.id.svga_water);
        luckSvgaIv=v.findViewById(R.id.svga_luck);
        textBannerView = v.findViewById(R.id.zs_msg_tb);
        openSettingTv = v.findViewById(R.id.tv_open_setting);
        iv_water_custom= v.findViewById(R.id.iv_water_custom);
//        gardenIv=v.findViewById(R.id.iv_garden);
        iv_water_1= v.findViewById(R.id.iv_water_1);
        iv_water_10= v.findViewById(R.id.iv_water_10);
        iv_water_100=v.findViewById(R.id.iv_water_100);
        timeLl=v.findViewById(R.id.ll_time);
        timeMinTv=v.findViewById(R.id.tv_time_min);
        timeSecTv=v.findViewById(R.id.tv_time_sec);
        road1Iv=v.findViewById(R.id.iv_icon_road_1);
        road2Iv=v.findViewById(R.id.iv_icon_road_2);
        road3Iv=v.findViewById(R.id.iv_icon_road_3);
        road4Iv=v.findViewById(R.id.iv_icon_road_4);

        circus_troupeSvgaIv=v.findViewById(R.id.svga_circus_troupe);
        ferris_wheelSvgaIv=v.findViewById(R.id.svga_ferris_wheel);
        gift_bearSvgaIv=v.findViewById(R.id.svga_gift_bear);
        dessert_houseSvgaIV=v.findViewById(R.id.svga_dessert_house);
        entranceSvga=v.findViewById(R.id.svga_entrance);

        circus_troupeFl=v.findViewById(R.id.fl_circus_troupe);
        ferris_wheelFl=v.findViewById(R.id.fl_ferris_wheel);
        gift_bearFl= v.findViewById(R.id.fl_gift_bear);
        dessert_houseFl= v.findViewById(R.id.fl_dessert_house);
        entranceFl=v.findViewById(R.id.fl_entrance);

        v.findViewById(R.id.iv_zs_record).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ZSRecordDialog().show(getFragmentManager());
            }
        });
        v.findViewById(R.id.tv_but_water).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ZSBuyWaterDialog(getContext(), new WaterBuyCallback() {
                    @Override
                    public void dismiss() {
                        getData();
                    }
                    @Override
                    public void dismisswatering(int waterNumber,int waterType) {

                    }
                }).show();
            }
        });
        v.findViewById(R.id.tv_but_rain).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ZSBuyWaterDialog(getContext(), new WaterBuyCallback() {
                    @Override
                    public void dismiss() {
                        getData();
                    }
                    @Override
                    public void dismisswatering(int waterNumber,int waterType) {

                    }
                }).show();
            }
        });
        iv_water_custom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new ZSBuyCustomWaterDialog(getContext(), watertype,new WaterBuyCallback() {
                    @Override
                    public void dismiss() {
                    }
                    @Override
                    public void dismisswatering(int waterNumber,int waterType) {
                        watertype=waterType;
                        reward(waterNumber);
                    }

                }).show();
            }
        });

        v.findViewById(R.id.cl_content).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSettingTv.setVisibility(View.INVISIBLE);
            }
        });

        v.findViewById(R.id.iv_zs_rule).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ZSRuleDialog(rule).show(getFragmentManager());
            }
        });

        v.findViewById(R.id.iv_zs_rank).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ZSRankDialog().show(getFragmentManager());
            }
        });

        v.findViewById(R.id.iv_zs_gift).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ZSGiftDialog().show(getFragmentManager());
            }
        });
        v.findViewById(R.id.iv_zs_set).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSettingTv.setVisibility(View.VISIBLE);
            }
        });
        openSettingTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (isShowReward){
//                    isShowReward = false;
//                    openSettingTv.setSelected(true);
//                    showZsMessage(0);
//                }else{
//                    isShowReward = true;
//                    openSettingTv.setSelected(false);
//                    showZsMessage(1);
//                }
            }
        });
        iv_water_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reward(1);
            }
        });
        iv_water_10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reward(10);
            }
        });
        iv_water_100.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reward(60);
            }
        });
        getData();
    }
    private void reward(final int number){
        NetService.Companion.getInstance(getContext()).useWater(number,watertype, new Callback<ZSUseWaterBean>() {
            @Override
            public void onSuccess(int nextPage, ZSUseWaterBean bean, int code) {
//                if (isShowReward){
                    for (ZSRewardBean item : bean.getPrize()){
                       if (item.getAmount() >= 199){
                           ChatRoomManager.INSTANCE.sendZSRewardMsg(MsgType.ZS_WINNING_MSG,watertype,
                                   "恭喜 "+ DataHelper.INSTANCE.getUserInfo().getNickname()+" 在庆典时刻中,获得了 "+item.getName()+item.getAmount()+"钻石x"+item.getStock(),
                                   ChatRoomManager.INSTANCE.getRoomId(),"", new MsgGiftBean(0, item.getStock(), "", item.getIcon(),
                                           item.getAmount(), item.getName(),0,0,0,"",""));
                       }
                    }
//                }
                if(bean.getMature()==5) {
                    if (Integer.valueOf(waterNumTv.getText().toString()) - number*5 >= 0) {
                        waterNumTv.setText(String.valueOf(Integer.valueOf(waterNumTv.getText().toString()) - number*5));
                    }
                }else{
                    if (Integer.valueOf(waterNumTv.getText().toString()) - number*2 >= 0) {
                        waterNumTv.setText(String.valueOf(Integer.valueOf(waterNumTv.getText().toString())- number*2));
                    }
                }
                int progressNum = Double.valueOf(bean.getProgress()).intValue();
                progressBar.setProgress(Double.valueOf(bean.getProgress()).intValue());
                progressbarTv.setText(progressNum+"%");
                gardenState(bean.getMature(),false);
                btnState(bean.getMature());
                treeLevelIv.setImageLevel(bean.getMature());
                new ZSAwardDialog(getContext(),watertype,bean.getPrize(), new WaterAgainCallback() {
                    @Override
                    public void smashAgain() {
                        reward(number);
                    }

                    @Override
                    public void dismiss() {

                    }
                }).show();
            }

            @Override
            public void onError(@NotNull String msg, @NotNull Throwable throwable, int code) {
                ToastUtil.INSTANCE.error(getContext(),msg);
                if (code == 1004) {
                    new ZSBuyWaterDialog(getContext(), new WaterBuyCallback() {
                        @Override
                        public void dismiss() {
                            getData();
                        }
                        @Override
                        public void dismisswatering(int waterNumber,int waterType) {

                        }
                    }).show();
//                    ARouter.getInstance().build("/app/pay").navigation();
                }
                if (code == 1002) {
                    getData();
                }
            }

            @Override
            public boolean isAlive() {
                return isLive;
            }
        });
    }


    private  void  btnState(int ImageViewLevel){
        switch (ImageViewLevel){
            case 5:
                watertype=2;
                iv_water_1.setImageDrawable(getResources().getDrawable(R.drawable.room_icon_rain_drop01));
                iv_water_10.setImageDrawable(getResources().getDrawable(R.drawable.room_icon_rain_drop10));
                iv_water_100.setImageDrawable(getResources().getDrawable(R.drawable.room_icon_rain_drop100));
                iv_water_custom.setImageDrawable(getResources().getDrawable(R.drawable.room_icon_rain_custom));
                luckSvgaIv.setVisibility(View.VISIBLE);
                showBgSvgaGiftAnim(luckSvgaIv, "luck.svga",true);
                break;
            default:
                watertype=1;
                iv_water_1.setImageDrawable(getResources().getDrawable(R.drawable.room_icon_water_drop01));
                iv_water_10.setImageDrawable(getResources().getDrawable(R.drawable.room_icon_water_drop10));
                iv_water_100.setImageDrawable(getResources().getDrawable(R.drawable.room_icon_water_drop100));
                iv_water_custom.setImageDrawable(getResources().getDrawable(R.drawable.room_icon_water_custom));
                luckSvgaIv.setVisibility(View.GONE);
                break;
        }
    }
     private  void  gardenState(int ImageViewLevel,Boolean type){
         road1Iv.setSelected(false);
         road2Iv.setSelected(false);
         road3Iv.setSelected(false);
         road4Iv.setSelected(false);

         entranceFl.setVisibility(View.INVISIBLE);
         dessert_houseFl.setVisibility(View.INVISIBLE);
         gift_bearFl.setVisibility(View.INVISIBLE);
         ferris_wheelFl.setVisibility(View.INVISIBLE);
         circus_troupeFl.setVisibility(View.INVISIBLE);

         circus_troupeSvgaIv.setVisibility(View.GONE);
         ferris_wheelSvgaIv.setVisibility(View.GONE);
         gift_bearSvgaIv.setVisibility(View.GONE);
         dessert_houseSvgaIV.setVisibility(View.GONE);
         entranceSvga.setVisibility(View.GONE);
        switch (ImageViewLevel) {
            case 0:
                entranceFl.setVisibility(View.VISIBLE);
                dessert_houseFl.setVisibility(View.VISIBLE);
                gift_bearFl.setVisibility(View.VISIBLE);
                ferris_wheelFl.setVisibility(View.VISIBLE);
                circus_troupeFl.setVisibility(View.VISIBLE);
                break;
            case 1:
                entranceSvga.setVisibility(View.VISIBLE);
                dessert_houseFl.setVisibility(View.VISIBLE);
                gift_bearFl.setVisibility(View.VISIBLE);
                ferris_wheelFl.setVisibility(View.VISIBLE);
                circus_troupeFl.setVisibility(View.VISIBLE);
                showBgSvgaGiftAnim(entranceSvga, "entrance.svga", true);
                break;
            case 2:
                road1Iv.setSelected(true);
                dessert_houseSvgaIV.setVisibility(View.VISIBLE);
                entranceSvga.setVisibility(View.VISIBLE);

                gift_bearFl.setVisibility(View.VISIBLE);
                ferris_wheelFl.setVisibility(View.VISIBLE);
                circus_troupeFl.setVisibility(View.VISIBLE);
                if (type){
                    showBgSvgaGiftAnim(entranceSvga, "entrance.svga", true);
                    }
                showBgSvgaGiftAnim(dessert_houseSvgaIV, "dessert_house.svga",true);
                break;
            case 3:
                road1Iv.setSelected(true);
                road2Iv.setSelected(true);
                dessert_houseSvgaIV.setVisibility(View.VISIBLE);
                entranceSvga.setVisibility(View.VISIBLE);
                gift_bearSvgaIv.setVisibility(View.VISIBLE);

                ferris_wheelFl.setVisibility(View.VISIBLE);
                circus_troupeFl.setVisibility(View.VISIBLE);
                if(type){
                    showBgSvgaGiftAnim(entranceSvga, "entrance.svga",true);
                    showBgSvgaGiftAnim(dessert_houseSvgaIV, "dessert_house.svga",true);
                }
                showBgSvgaGiftAnim(gift_bearSvgaIv, "gift_bear.svga",true);
                break;
            case 4:
                road1Iv.setSelected(true);
                road2Iv.setSelected(true);
                road3Iv.setSelected(true);
                dessert_houseSvgaIV.setVisibility(View.VISIBLE);
                entranceSvga.setVisibility(View.VISIBLE);
                gift_bearSvgaIv.setVisibility(View.VISIBLE);
                circus_troupeSvgaIv.setVisibility(View.VISIBLE);

                ferris_wheelFl.setVisibility(View.VISIBLE);
                if(type) {
                    showBgSvgaGiftAnim(entranceSvga, "entrance.svga", true);
                    showBgSvgaGiftAnim(dessert_houseSvgaIV, "dessert_house.svga", true);
                    showBgSvgaGiftAnim(gift_bearSvgaIv, "gift_bear.svga", true);
                }
                showBgSvgaGiftAnim(circus_troupeSvgaIv, "circus_troupe.svga",true);
                break;
            case 5:
                road1Iv.setSelected(true);
                road2Iv.setSelected(true);
                road3Iv.setSelected(true);
                road4Iv.setSelected(true);

                dessert_houseSvgaIV.setVisibility(View.VISIBLE);
                entranceSvga.setVisibility(View.VISIBLE);
                gift_bearSvgaIv.setVisibility(View.VISIBLE);
                circus_troupeSvgaIv.setVisibility(View.VISIBLE);
                ferris_wheelSvgaIv.setVisibility(View.VISIBLE);
               if(type){
                showBgSvgaGiftAnim(entranceSvga, "entrance.svga",true);
                showBgSvgaGiftAnim(dessert_houseSvgaIV, "dessert_house.svga",true);
                showBgSvgaGiftAnim(gift_bearSvgaIv, "gift_bear.svga",true);
                showBgSvgaGiftAnim(circus_troupeSvgaIv, "circus_troupe.svga",true);
                }
                showBgSvgaGiftAnim(ferris_wheelSvgaIv, "ferris_wheel.svga",true);
                break;
            default:
               break;
        }

     }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        MsgManager.INSTANCE.removeMsgListener(this);

    }

    private void showZsMessage(final int display){
        NetService.Companion.getInstance(getContext()).showZsMessage(display,new Callback<Object>() {
            @Override
            public void onSuccess(int nextPage, Object bean, int code) {
                if (display == 1){
                    ToastUtil.INSTANCE.i(getContext(),"开启对外显示");
                }else{
                    ToastUtil.INSTANCE.i(getContext(),"关闭对外显示");
                }
            }

            @Override
            public void onError(@NotNull String msg, @NotNull Throwable throwable, int code) {

            }

            @Override
            public boolean isAlive() {
                return isLive;
            }
        });
    }

    private void getData( ){
        NetService.Companion.getInstance(getContext()).getZSIndex(new Callback<ZSIndexBean>() {
            @Override
            public void onSuccess(int nextPage, ZSIndexBean bean, int code) {
//                isShowReward = bean.getIs_display_prize() == 1;
//                if (isShowReward){
//                    openSettingTv.setSelected(false);
//                }else{
//                    openSettingTv.setSelected(true);
//                }
                waterNumTv.setText(bean.getWater());
                rule = bean.getRule();
                int progressNum = Double.valueOf(bean.getProgress()).intValue();
                progressBar.setProgress(Double.valueOf(bean.getProgress()).intValue());
                progressbarTv.setText(progressNum+"%");
                List<String> strings = new ArrayList<>();
                for (ZSIndexBean.MessageBean item : bean.getMessage()){
                    strings.add( " 恭喜 <font color='#75C0F0'> "+item.getNickname()+"</font> 获得 <font color='#75C0F0'> "+item.getName()+"* "+item.getCount()+"</font>");
                }
                textBannerView.setDatas(strings,true);
                gardenState(bean.getMature(),true);
                btnState(bean.getMature());
                treeLevelIv.setImageLevel(bean.getMature());
                long millinsinFuture = bean.getExpired_at()*1000;
                if (millinsinFuture > 0){
                    timeLl.setVisibility(View.VISIBLE);
//                    timeTv.setVisibility(View.VISIBLE);
                    if (countDownTimer != null){
                        countDownTimer.cancel();
                    }
                    countDownTimer = new CountDownTimer(millinsinFuture, 1000) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            timeMinTv.setText(TimeUtil.getZSTime(millisUntilFinished));
                            timeSecTv.setText(TimeUtil.getZSTimeSec(millisUntilFinished));
//                            timeTv.setText(TimeUtil.getZSTime(millisUntilFinished));
                        }
                        @Override
                        public void onFinish() {
                            ToastUtil.INSTANCE.suc(getContext(),"庆典已结束,每次游园需2张门票");
                            getData();
                        }
                    };
                    countDownTimer.start();
                }else{
                    timeLl.setVisibility(View.GONE);
//                    timeTv.setVisibility(View.GONE);
                }
            }

            @Override
            public void onError(@NotNull String msg, @NotNull Throwable throwable, int code) {

            }

            @Override
            public boolean isAlive() {
                return isLive;
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null){
            countDownTimer.cancel();
        }
    }

    private void showBgSvgaGiftAnim(final SVGAImageView svgaImageView, String name, final boolean isRepeat) {
            if (parser == null){
                parser = new SVGAParser(getContext());
            }
            svgaImageView.setCallback(new SVGACallback() {
                @Override
                public void onPause() {

                }

                @Override
                public void onFinished() {
                }

                @Override
                public void onRepeat() {
                    if (!isRepeat){
                        svgaImageView.stopAnimation();
                    }
                }

                @Override
                public void onStep(int i, double v) {

                }
            });
            parser.decodeFromAssets(name, new SVGAParser.ParseCompletion() {
                @Override
                public void onComplete(@NotNull SVGAVideoEntity videoItem) {

                    SVGADrawable drawable = new SVGADrawable(videoItem);
                    svgaImageView.setImageDrawable(drawable);
                    svgaImageView.startAnimation();
                }
                @Override
                public void onError() {

                }
            });


    }

    @Override
    public boolean onNewMsg(@NotNull String text) {
        if (TextUtils.isEmpty(text)) {
            return false;
        }
        MsgBean msgBean;
        try {
            msgBean = new Gson().fromJson(text, MsgBean.class);
        } catch (Exception e) {
            return false;
        }

        if (!msgBean.getChatId().equals(DataHelper.INSTANCE.getIMDevelop().getImBigGroupID())) {
            return false;
        }
        if (msgBean.getOpt() ==  MsgType.FULL_SERVICE_TREE_LUCKTIME ){
            ToastUtil.INSTANCE.suc(getContext(),"庆典已开启,每次游园需5张门票！");
            getData();
            return true;
        }
        if (msgBean.getOpt() ==  MsgType.FULL_SERVICE_TREE_PROGRESS){
            int progress = Long.valueOf(msgBean.getTime()).intValue();
//            treeLevelIv.setImageLevel(progress/25 + 1);
//            Log.e("progress","=="+progress/20);
            gardenState(progress/20,false);
            btnState(progress/20);
            progressBar.setProgress(progress);
            progressbarTv.setText(msgBean.getTime()+"%");
            if (progress == 0){

                getData();
            }
            return true;
        }

        return true;
    }
}
