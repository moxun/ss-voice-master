package com.miaomi.fenbei.voice.ui.main.fragment;

import android.view.View;
import android.widget.FrameLayout;

import com.miaomi.fenbei.base.bean.HomepageBean;
import com.miaomi.fenbei.base.core.BaseFragment;
import com.miaomi.fenbei.base.core.dialog.LoadingDialog;
import com.miaomi.fenbei.base.core.msg.MsgManager;
import com.miaomi.fenbei.base.net.Callback;
import com.miaomi.fenbei.base.net.NetService;
import com.miaomi.fenbei.base.util.ToastUtil;
import com.miaomi.fenbei.imkit.ui.conversation.ConversationFragment;
import com.miaomi.fenbei.voice.R;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class NewsFragment extends BaseFragment {
    private FrameLayout contentFl;
    private ConversationFragment fragment;
//    private RecyclerView onLineRv;
//    private UserOnlineAdapter homepageAdapter;
//    private SmartRefreshLayout refreshLayout;
//    private RelativeLayout ret_follow;
    @Override
    public int getLayoutId() {
        return R.layout.main_fragment_news;
    }

    public static NewsFragment newInstance(){
        return new NewsFragment();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void initView(@NotNull View view) {
//        homepageAdapter = new UserOnlineAdapter(getContext());
//        onLineRv = view.findViewById(R.id.rv_online_user);
//        refreshLayout = view.findViewById(R.id.refresh_layout);
//        ret_follow=view.findViewById(R.id.rlt_follow);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
//        onLineRv.setLayoutManager(linearLayoutManager);
//        onLineRv.setAdapter(homepageAdapter);
        if (fragment == null){
            fragment = ConversationFragment.newInstance();
        }
        contentFl = view.findViewById(R.id.fl_content);
        view.findViewById(R.id.iv_clear).setOnClickListener(v -> {
            LoadingDialog dialog = new LoadingDialog(getContext());
            dialog.show();
            dialog.setCancelable(true);

            MsgManager.INSTANCE.setAllReadMessage(position -> {
                dialog.dismiss();
                MsgManager.INSTANCE.getConversationList();
                ToastUtil.INSTANCE.suc(getContext(),"清理成功");
            });

        });
//        view.findViewById(R.id.tv_read_type).setOnClickListener(v -> {
//            LoadingDialog dialog = new LoadingDialog(getContext());
//            dialog.show();
//            dialog.setCancelable(true);
//            MsgManager.INSTANCE.setAllReadMessage(position -> {
//                dialog.dismiss();
//                ToastUtil.INSTANCE.suc(getContext(),"清理成功");
//            });
//        });
//        refreshLayout.setOnRefreshListener(refreshLayout -> {
////            getData();
//            MsgManager.INSTANCE.getConversationList();
//            refreshLayout.finishRefresh(true);
//        });
        if (getFragmentManager() != null) {
            getFragmentManager().beginTransaction().add(R.id.fl_content,fragment).show(fragment).commit();
        }
//        getData();
    }




}
