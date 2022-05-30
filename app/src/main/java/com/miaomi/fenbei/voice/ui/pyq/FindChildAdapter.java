package com.miaomi.fenbei.voice.ui.pyq;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.miaomi.fenbei.base.SimplePlayerActivity;
import com.miaomi.fenbei.base.bean.FindBean;
import com.miaomi.fenbei.base.bean.FollowBean;
import com.miaomi.fenbei.base.core.JoinChatCallBack;
import com.miaomi.fenbei.base.core.msg.MsgManager;
import com.miaomi.fenbei.base.core.msg.SendMsgListener;
import com.miaomi.fenbei.base.net.Callback;
import com.miaomi.fenbei.base.net.NetService;
import com.miaomi.fenbei.base.util.DataHelper;
import com.miaomi.fenbei.base.util.ImgUtil;
import com.miaomi.fenbei.base.util.TimeUtil;
import com.miaomi.fenbei.base.util.ToastUtil;
import com.miaomi.fenbei.base.widget.GridSpacingItemDecoration;
import com.miaomi.fenbei.base.widget.HeartMeView;
import com.miaomi.fenbei.base.widget.HeartView;
import com.miaomi.fenbei.base.widget.InRoomView;
import com.miaomi.fenbei.base.widget.LevelView;
import com.miaomi.fenbei.base.widget.SexAndAgeView;
import com.miaomi.fenbei.base.widget.TMVideoView;
import com.miaomi.fenbei.room.ChatRoomManager;
import com.miaomi.fenbei.voice.R;
import com.miaomi.fenbei.voice.ui.mine.user_homepage.BigImgActivity;
import com.miaomi.fenbei.voice.ui.mine.user_homepage.UserHomepageActivity;
import com.miaomi.fenbei.voice.widget.KMSoundView;
import com.scwang.smartrefresh.layout.util.DensityUtil;
import com.tencent.imsdk.TIMMessage;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class FindChildAdapter extends BaseMultiItemQuickAdapter<FindBean, FindChildAdapter.BaseViewholder> {

//    private Context context;
//    private List<FindBean> mList = new ArrayList<>();
    public OnFindItemOprateListener onFindItemOprateListener;

    public void setOnFindItemOprateListener(OnFindItemOprateListener onFindItemOprateListener) {
        this.onFindItemOprateListener = onFindItemOprateListener;
    }

    public FindChildAdapter() {
        super(new ArrayList<>());

        addItemType(FindBean.FIND_ITEM_TYPE_WB, R.layout.item_find_child_wb);
        addItemType(FindBean.FIND_ITEM_TYPE_SP, R.layout.item_find_child_video);
        addItemType(FindBean.FIND_ITEM_TYPE_MP, R.layout.item_find_child_multiple_pic);
        addItemType(FindBean.FIND_ITEM_TYPE_YY, R.layout.item_find_child_sound);
        addItemType(FindBean.FIND_ITEM_TYPE_DT, R.layout.item_find_child_big_pic);
    }




    @Override
    protected void convert(@NonNull BaseViewholder helper, FindBean item) {
        helper.bindData(item,helper.getItemViewType());
        switch (helper.getItemViewType()){
//            case FindBean.FIND_ITEM_TYPE_WB:
//                if (helper instanceof WBViewholder){
//                    ((WBViewholder)helper).bindData(item);
//                }
//                break;
            case FindBean.FIND_ITEM_TYPE_SP:
                TMVideoView tmVideoView = helper.itemView.findViewById(R.id.iv_big_pic);
                tmVideoView.loadImg(item.getVideo_url_cover());
                tmVideoView.setOnClickListener(v -> SimplePlayerActivity.start(helper.itemView.getContext(),item.getVideo_url()));
                break;
            case FindBean.FIND_ITEM_TYPE_MP:
                RecyclerView photosRv = helper.itemView.findViewById(R.id.rv_pic);
                FindChildImgAdapter adapter= new FindChildImgAdapter(helper.itemView.getContext());

                photosRv.setAdapter(adapter);
                if (photosRv.getItemDecorationCount() > 0){
                    photosRv.removeItemDecorationAt(0);
                }
                if (item.getPhotos().size() == 1 || item.getPhotos().size() == 2 || item.getPhotos().size() == 4){
                    photosRv.setLayoutManager(new GridLayoutManager(helper.itemView.getContext(),2));
                    photosRv.addItemDecoration(new GridSpacingItemDecoration(2, DensityUtil.dp2px(5f),false));
                }else{
                    photosRv.setLayoutManager(new GridLayoutManager(helper.itemView.getContext(),3));
                    photosRv.addItemDecoration(new GridSpacingItemDecoration(3, DensityUtil.dp2px(5f),false));


                }
                adapter.setData(item.getPhotos());
                break;
            case FindBean.FIND_ITEM_TYPE_YY:
//                if (helper instanceof SoundViewholder){
//                    ((SoundViewholder)helper).bindData(item);
//                }
                KMSoundView kmSoundView = helper.itemView.findViewById(R.id.km_sound_view);
                kmSoundView.setDuration(item.getSound_duration());
                kmSoundView.setPath(item.getSound_path());
                break;
            case FindBean.FIND_ITEM_TYPE_DT:
//                if (helper instanceof BigPicViewholder){
//                    ((BigPicViewholder)helper).bindData(item);
//                }
                ImageView imageView = helper.itemView.findViewById(R.id.iv_big_pic);
                if (item.getPhotos().size() > 0){
                    ImgUtil.INSTANCE.loadRoundImg(helper.itemView.getContext(),item.getPhotos().get(0),imageView, 8f,R.drawable.base_placeholder_photo_big);
                    imageView.setOnClickListener(v -> helper.itemView.getContext().startActivity(BigImgActivity.getIntent(helper.itemView.getContext(),0,"0", (ArrayList<String>) item.getPhotos())));
                }
                break;
        }
    }






    class BaseViewholder extends BaseViewHolder{
        private ImageView faceIv;
        private TextView nameTv;
        private TextView tv_sea_h;
        private SexAndAgeView ageTv;
        private TextView timeTv;
        private TextView contentTv;
        private HeartView heartView;
        private HeartMeView heartMeView;
        private TextView heartNum;
        private ImageView giftIv;
        private ImageView talkIv;
        private TextView careIv;
//        private WaveView roomStatusIv;
        private InRoomView roomStatusIconIv;
        private ImageView deleteIv;
        private ImageView moreOprateIv;
        private LevelView levelIv;
        private ImageView nobleIv;
        private TextView  commentnumTv;
        private TextView rewardTv;
        public BaseViewholder(View itemView) {
            super(itemView);
            faceIv = itemView.findViewById(R.id.iv_face);
            nameTv = itemView.findViewById(R.id.tv_name);
            ageTv = itemView.findViewById(R.id.tv_age);
            timeTv = itemView.findViewById(R.id.tv_time);
            contentTv = itemView.findViewById(R.id.tv_content);
            heartView = itemView.findViewById(R.id.iv_heart);
            heartMeView = itemView.findViewById(R.id.heart_view);
            heartNum = itemView.findViewById(R.id.tv_heart_num);
            giftIv = itemView.findViewById(R.id.iv_gift);
            rewardTv=itemView.findViewById(R.id.tv_reward);
            talkIv = itemView.findViewById(R.id.iv_talk);
            careIv = itemView.findViewById(R.id.tv_care);
            roomStatusIconIv = itemView.findViewById(R.id.iv_room_status_icon);
//            roomStatusIv = itemView.findViewById(R.id.iv_room_status);
            deleteIv = itemView.findViewById(R.id.iv_delete);
            moreOprateIv = itemView.findViewById(R.id.iv_more_oprate);
            levelIv = itemView.findViewById(R.id.iv_level);
            nobleIv = itemView.findViewById(R.id.medal_iv);
            tv_sea_h=itemView.findViewById(R.id.tv_sea_h);
            commentnumTv=itemView.findViewById(R.id.tv_comment_num);
//            roomStatusIv.setDuration(1500);
//            roomStatusIv.setSpeed(1500);
//            roomStatusIv.setColor(Color.rgb(255, 54, 180));
//            roomStatusIv.setStroke();
//            roomStatusIv.setAlpha(false);
//            roomStatusIv.setInterpolator(new LinearOutSlowInInterpolator());
//            roomStatusIv.setInitialRadius(3f);
        }
        public void bindData(FindBean bean,int type){
            if (!TextUtils.isEmpty(bean.getNoble_rank())){
                nobleIv.setVisibility(View.GONE);
                ImgUtil.INSTANCE.loadImg(itemView.getContext(),bean.getNoble_rank(), nobleIv);
            }else{
                nobleIv.setVisibility(View.GONE);
            }
            if (bean.getCharm_level() != null){
//                levelIv.setCharmLevel(bean.getCharm_level().getGrade());
            }
            if (!bean.isRoom_status()){
//                roomStatusIv.setVisibility(View.INVISIBLE);
                roomStatusIconIv.setVisibility(View.INVISIBLE);
                faceIv.clearAnimation();
//                roomStatusIv.stop();
            }else{
//                roomStatusIv.setVisibility(View.VISIBLE);
                roomStatusIconIv.setVisibility(View.VISIBLE);
//                faceIv.startAnimation(AnimationUtils.loadAnimation(itemView.getContext(), R.anim.home_page_head));
//                roomStatusIv.start();
            }
            commentnumTv.setText(""+bean.getComment_number());
            heartView.setSelected(bean.isHeart_status());
            ImgUtil.INSTANCE.loadFaceIcon(itemView.getContext(),bean.getFace(),faceIv);
            nameTv.setText(bean.getNickname());
            ageTv.setContent(bean.getGender() == 1,bean.getAge());
            timeTv.setText(TimeUtil.longFormatTime(bean.getCreate_time()));
            if (!TextUtils.isEmpty(bean.getDesc())){
                contentTv.setVisibility(View.VISIBLE);
                contentTv.setText(bean.getDesc());
            }else{
                contentTv.setVisibility(View.GONE);
            }
            if (bean.isIs_own()){
                moreOprateIv.setVisibility(View.GONE);
                careIv.setVisibility(View.GONE);

                giftIv.setVisibility(View.GONE);
                rewardTv.setVisibility(View.GONE);
                deleteIv.setVisibility(View.VISIBLE);
            }else{
                moreOprateIv.setVisibility(View.GONE);
                deleteIv.setVisibility(View.GONE);
                rewardTv.setVisibility(View.VISIBLE);
                giftIv.setVisibility(View.VISIBLE);
                if (bean.isFollow_status()){
                    careIv.setBackgroundResource(R.drawable.user_followed);
                    careIv.setVisibility(View.VISIBLE);
                }else{
                    careIv.setBackgroundResource(R.drawable.bg_home_follow);
                    careIv.setVisibility(View.VISIBLE);
                }
            }
            if (bean.getHeart_num() == 0){
                heartNum.setVisibility(View.GONE);
                heartMeView.setVisibility(View.GONE);
            }else{
                heartNum.setVisibility(View.VISIBLE);
//                heartMeView.setVisibility(View.VISIBLE);
//                heartMeView.setContent(bean.getHearts());
                heartNum.setText(bean.getHeart_num()+"");
//                heartNum.setText(bean.getHeart_num() +"人赞了TA");
            }


            tv_sea_h.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onFindItemOprateListener != null){
                        onFindItemOprateListener.onTalk(bean.getUser_id(),bean.getFace());
                    }
                }
            });
            deleteIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onFindItemOprateListener != null){
                        onFindItemOprateListener.onDelate(bean.getId());
                    }
                }
            });
            careIv.setOnClickListener(v -> {
                if (onFindItemOprateListener != null){
                    NetService.Companion.getInstance(mContext).addConcern(DataHelper.INSTANCE.getLoginToken(), bean.getUser_id(), new Callback<FollowBean>() {
                        @Override
                        public void onSuccess(int nextPage, FollowBean beanData, int code) {
                            //                0 未关注 1已关注
                            if (beanData.getIs_follow() == 1) {

                                careIv.setBackgroundResource(R.drawable.user_followed);
                                ToastUtil.INSTANCE.suc(mContext, "关注成功");
                                MsgManager.INSTANCE.sendFollowMsg(bean.getUser_id(), new SendMsgListener() {
                                    @Override
                                    public void onSendSuc(TIMMessage timMessage) {

                                    }

                                    @Override
                                    public void onSendFail(int i, String s) {

                                    }
                                });
                            }else{
                                careIv.setBackgroundResource(R.drawable.bg_home_follow);
                                ToastUtil.INSTANCE.suc(mContext, "取消关注成功");
                            }
                        }

                        @Override
                        public void onError(@NotNull String msg, @NotNull Throwable throwable, int code) {
                            ToastUtil.INSTANCE.suc(mContext, msg);
                        }

                        @Override
                        public boolean isAlive() {
                            return true;
                        }
                    });
                }


            });
            moreOprateIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onFindItemOprateListener != null){
                        onFindItemOprateListener.onMoreOparate(String.valueOf(bean.getUser_id()));
                    }
                }
            });
            giftIv.setOnClickListener(v -> {
                if (onFindItemOprateListener != null){
                    onFindItemOprateListener.onSendGift(String.valueOf(bean.getUser_id()));
                }
            });
            rewardTv.setOnClickListener(v -> {
                        if (onFindItemOprateListener != null){
                            onFindItemOprateListener.onSendGift(String.valueOf(bean.getUser_id()));
                        }
                    }
                    );
//            talkIv.setOnClickListener(v -> {
//                if (onFindItemOprateListener != null){
//                    onFindItemOprateListener.onTalk(bean.getUser_id(),bean.getFace());
//                }
//            });
            faceIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (bean.isRoom_status()){
                        ChatRoomManager.INSTANCE.joinChat(itemView.getContext(), bean.getRoom_id(), new JoinChatCallBack() {
                            @Override
                            public void onSuc() {

                            }

                            @Override
                            public void onFail(@NotNull String msg) {

                            }
                        });
                    }else{
                        ARouter.getInstance().build("/app/userhomepage")
                                .withString("user_id",bean.getUser_id()+"")
                                .withString("user_name",bean.getNickname())
                                .withString("user_face",bean.getFace())
                                .navigation();
                    }
                }
            });

            heartView.setOnClickListener(v ->
            {
                if (DataHelper.INSTANCE.getUserInfo() != null){
                    if (onFindItemOprateListener != null){
                        onFindItemOprateListener.onHeart(bean,heartMeView,heartView,heartNum);
                    }
                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

               ARouter.getInstance().build("/app/pyqdetails")

                       .withString("friendscircleId",bean.getId())

                       .navigation();

                }
            });

        }
    }

//    class VideoViewholder extends BaseViewholder{
//        private TMVideoView tmVideoView;
//        public VideoViewholder(View itemView) {
//            super(itemView);
//            tmVideoView = itemView.findViewById(R.id.iv_big_pic);
//        }
//
//        @Override
//        public void bindData(FindBean bean) {
//            super.bindData(bean);
//            tmVideoView.loadImg(bean.getVideo_url_cover());
//            tmVideoView.setOnClickListener(v -> SimplePlayerActivity.start(itemView.getContext(),bean.getVideo_url()));
//        }
//    }

//    class BigPicViewholder extends BaseViewholder{
//        private ImageView imageView;
//        public BigPicViewholder(View itemView) {
//            super(itemView);
//            imageView = itemView.findViewById(R.id.iv_big_pic);
//        }
//
//        @Override
//        public void bindData(FindBean bean) {
//            super.bindData(bean);
//            if (bean.getPhotos().size() > 0){
//                ImgUtil.INSTANCE.loadRoundImg(itemView.getContext(),bean.getPhotos().get(0),imageView, 8f,R.drawable.base_placeholder_photo_big);
//                imageView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        itemView.getContext().startActivity(BigImgActivity.getIntent(itemView.getContext(),0,"0", (ArrayList<String>) bean.getPhotos()));
//                    }
//                });
//            }
//        }
//    }

//    class MultiplePicViewholder extends BaseViewholder{
//        private RecyclerView photosRv;
//        private FindChildImgAdapter adapter;
//        public MultiplePicViewholder(View itemView) {
//            super(itemView);
//            photosRv = itemView.findViewById(R.id.rv_pic);
//            adapter = new FindChildImgAdapter(itemView.getContext());
//            photosRv.setAdapter(adapter);
//        }
//
//        @Override
//        public void bindData(FindBean bean) {
//            super.bindData(bean);
//            photosRv.setAdapter(adapter);
//            if (photosRv.getItemDecorationCount() > 0){
//                photosRv.removeItemDecorationAt(0);
//            }
//            if (bean.getPhotos().size() == 1 || bean.getPhotos().size() == 2 || bean.getPhotos().size() == 4){
//                photosRv.setLayoutManager(new GridLayoutManager(itemView.getContext(),2));
//                photosRv.addItemDecoration(new GridSpacingItemDecoration(2, DensityUtil.dp2px(5f),false));
//            }else{
//                photosRv.setLayoutManager(new GridLayoutManager(itemView.getContext(),3));
//                photosRv.addItemDecoration(new GridSpacingItemDecoration(3, DensityUtil.dp2px(5f),false));
//
//
//            }
//            adapter.setData(bean.getPhotos());
//        }
//    }
//    class SoundViewholder extends BaseViewholder{
//        KMSoundView kmSoundView;
//        public SoundViewholder(View itemView) {
//            super(itemView);
//            kmSoundView = itemView.findViewById(R.id.km_sound_view);
//        }
//
//        @Override
//        public void bindData(FindBean bean) {
//            super.bindData(bean);
//            kmSoundView.setDuration(bean.getSound_duration());
//            kmSoundView.setPath(bean.getSound_path());
//        }
//    }
//
//    class WBViewholder extends BaseViewholder{
//        public WBViewholder(View itemView) {
//            super(itemView);
//        }
//
//        @Override
//        public void bindData(FindBean bean) {
//            super.bindData(bean);
//        }
//    }
    public interface OnFindItemOprateListener{
        void onSendGift(String uid);
        void onTalk(String uid, String face);
        void onHeart(FindBean item, HeartMeView heartMeView, HeartView heartView, TextView heartNum);
        void onCare(String uid);
        void onDelate(String id);
        void onMoreOparate(String id);
    }
}
