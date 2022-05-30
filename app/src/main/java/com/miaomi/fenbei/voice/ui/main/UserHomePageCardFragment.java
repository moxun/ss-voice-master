package com.miaomi.fenbei.voice.ui.main;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.miaomi.fenbei.base.bean.GiftWallBean;
import com.miaomi.fenbei.base.config.BaseConfig;
import com.miaomi.fenbei.base.core.BaseFragment;
import com.miaomi.fenbei.base.net.Callback;
import com.miaomi.fenbei.base.net.NetService;
import com.miaomi.fenbei.base.util.ToastUtil;
import com.miaomi.fenbei.base.web.WebActivity;
import com.miaomi.fenbei.base.widget.GridSpacingItemDecoration;
import com.miaomi.fenbei.voice.R;
import com.miaomi.fenbei.voice.ui.mine.user_homepage.UserHomepageActivity;
import com.miaomi.fenbei.voice.ui.mine.user_homepage.adapter.AllGiftWallAdapter;
import com.scwang.smartrefresh.layout.util.DensityUtil;

import org.jetbrains.annotations.NotNull;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class UserHomePageCardFragment extends BaseFragment {
    
//    private TextView infoTv;
    private RecyclerView rvGiftRec;
//    private RecyclerView rvGiftSend;
    private AllGiftWallAdapter recGiftAdapter;
//    private AllGiftWallAdapter sendGiftAdapter;
//    private LinearLayout allGiftLLRec;
//    private LinearLayout allGiftLLSend;
    private int mGiftWallPage = 1;
    private String userId = "";
//    private KMSoundView soundView;
//    private LinearLayout soundLL;
private TextView hasGiftTv;
    private TextView startCountTv;
   private List<GiftWallBean.ListBean> listBeanList;
    public static UserHomePageCardFragment newInstance(String userId){
        UserHomePageCardFragment fragment = new UserHomePageCardFragment();
        Bundle bundle = new Bundle();
        bundle.putString("user_id",userId);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public int getLayoutId() {
        return R.layout.fragment_user_home_page_card;
    }

    @Override
    public void initView(@NotNull View view) {
        recGiftAdapter = new AllGiftWallAdapter(getContext());
//        sendGiftAdapter = new AllGiftWallAdapter(getContext());
        userId = getArguments().getString("user_id");
//        infoTv = view.findViewById(R.id.tv_info);
//        infoTv.setText(getArguments().getString("content",""));
        rvGiftRec =  view.findViewById(R.id.rv_gift_rec);
        hasGiftTv=view.findViewById(R.id.tv_has_gift);
        startCountTv=view.findViewById(R.id.tv_start_count);

        view.findViewById(R.id.tv_star_explain).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebActivity.start(getContext(), BaseConfig.URL_STAR_EXPALIN,"集星说明");
            }
        });
//        rvGiftSend =  view.findViewById(R.id.rv_gift_send);

//        allGiftLLRec = view.findViewById(R.id.ll_all_gift_rec);
//        allGiftLLSend = view.findViewById(R.id.ll_all_gift_send);
//        soundLL = view.findViewById(R.id.ll_sound);
//        soundView = view.findViewById(R.id.sound_view);
//        rvGiftRec.setPullRefreshEnabled(false);
//        rvGiftRec.setLoadingMoreEnabled(true);
//        rvGiftRec.setLoadingListener(new XRecyclerView.LoadingListener() {
//            @Override
//            public void onRefresh() {
//                getGiftWall(getTYPE_REFRESH());
//            }
//
//            @Override
//            public void onLoadMore() {
//                getGiftWall(getTYPE_LOADMROE());
//            }
//        });
//        allGiftLLRec.setOnClickListener(v -> AllGiftActivity.start(getActivity(),userId,AllGiftActivity.TYPE_GIFT_GET));
//        allGiftLLSend.setOnClickListener(v -> AllGiftActivity.start(getActivity(),userId,AllGiftActivity.TYPE_GIFT_SEND));
//        rvGiftSend.setLayoutManager(new GridLayoutManager(getContext(),5));
//        rvGiftSend.addItemDecoration(new GridSpacingItemDecoration(5, DensityUtil.dp2px(6f),false));
//        rvGiftSend.setAdapter(sendGiftAdapter);
        listBeanList=new ArrayList<>();
        rvGiftRec.setLayoutManager(new GridLayoutManager(getContext(),3));
        rvGiftRec.addItemDecoration(new GridSpacingItemDecoration(3, DensityUtil.dp2px(15f),false));
        rvGiftRec.setAdapter(recGiftAdapter);
        getGiftWall(getTYPE_REFRESH());
    }

    @Override
    public void onPause() {
        super.onPause();
//        soundView.stop();
    }

//    public void setContent(String content){
//        infoTv.setText(Html.fromHtml(content));
//    }

    public void setSoundView(String url,int duration){
//        if (!TextUtils.isEmpty(url)) {
//            soundView.setVisibility(View.VISIBLE);
//            soundLL.setVisibility(View.VISIBLE);
//            soundView.setPath(url);
//            soundView.setDuration(duration);
//            soundView.hideClose();
//            soundView.atuopaly();
//        } else {
//            soundView.setVisibility(View.GONE);
//            soundLL.setVisibility(View.GONE);
//        }
    }


    private void getGiftWall(int type){
        if (type == getTYPE_REFRESH()){
            mGiftWallPage = 1;
        }

        NetService.Companion.getInstance(getContext()).getGiftWall(mGiftWallPage,userId, new Callback<GiftWallBean>() {
            @Override
            public void onSuccess(int nextPage,  GiftWallBean list, int code) {
                if (type == getTYPE_REFRESH()){
//                    rvGiftRec.refreshComplete();
//                    if (list.size() > 0){
//                        allGiftLLRec.setVisibility(View.VISIBLE);
//                    }
                    if(list.getList().size()>=12){
                        listBeanList=list.getList().subList(0, 12);
                    }else{
                        listBeanList=list.getList();
                    }
                    showView(list.getCount());
                    recGiftAdapter.setData(listBeanList);
                }else{
//                    rvGiftRec.loadMoreComplete();
                    recGiftAdapter.addData(list.getList());
                }
//                mGiftWallPage++;

            }

            @Override
            public void noMore() {
                super.noMore();
//                rvGiftRec.setNoMore(true);
            }

            @Override
            public void onError(@NotNull String msg, @NotNull Throwable throwable, int code) {
                ToastUtil.INSTANCE.suc(getContext(),msg);
            }

            @Override
            public boolean isAlive() {
                return isLive();
            }
        });

//        NetService.Companion.getInstance(getContext()).getSendGiftWall(mGiftWallPage,userId, new Callback<List<GiftWallBean>>() {
//            @Override
//            public void onSuccess(int nextPage, List<GiftWallBean> list, int code) {
//                if (type == getTYPE_REFRESH()){
////                    rvGiftRec.refreshComplete();
//                    if (list.size() > 0){
//                        allGiftLLSend.setVisibility(View.VISIBLE);
//                    }
//                    sendGiftAdapter.setData(list);
//                }else{
////                    rvGiftRec.loadMoreComplete();
//                    sendGiftAdapter.addData(list);
//                }
////                mGiftWallPage++;
//
//            }
//
//            @Override
//            public void noMore() {
//                super.noMore();
////                rvGiftRec.setNoMore(true);
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
    private  void  showView(GiftWallBean.CountBean  countBean){
        hasGiftTv.setText(""+countBean.getHas_gift());
        startCountTv.setText(""+countBean.getStart_count());
    }
}
