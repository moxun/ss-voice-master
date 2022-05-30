package com.miaomi.fenbei.room.ui.dialog;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.miaomi.fenbei.base.bean.AnchorFansItemBean;
import com.miaomi.fenbei.base.bean.AnchorRankBean;
import com.miaomi.fenbei.base.bean.BaseBean;
import com.miaomi.fenbei.base.core.BaseFragment;
import com.miaomi.fenbei.base.net.Callback;
import com.miaomi.fenbei.base.net.NetService;
import com.miaomi.fenbei.base.util.DataHelper;
import com.miaomi.fenbei.base.util.ImgUtil;
import com.miaomi.fenbei.base.util.ToastUtil;
import com.miaomi.fenbei.base.widget.xrecyclerview.XRecyclerView;
import com.miaomi.fenbei.room.R;
import com.miaomi.fenbei.room.ui.callback.OnEditFansNameListener;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class RadioAnchorRankFragment extends BaseFragment {

    private XRecyclerView xRecyclerView;
    private ImageView faceIv;
    private TextView nameTv;
    private TextView fansTv;
    private TextView weekTv;
    private TextView sortTv;
    private TextView joinContentTv;
    private RadioAnchorRankAdapter radioAnchorRankAdapter;
    public final static int RANK_FANS_TYPE_R = 3;
    public final static int RANK_FANS_TYPE_Z = 2;
    public final static int RANK_FANS_TYPE_ALL = 1;
    private int rankType = 1;
    private String anchorId;
    private int mPage = 1;
    private TextView joinFansTv;
    private TextView desTv;

    public RadioAnchorRankFragment(int rankType, String anchorId) {
        this.rankType = rankType;
        this.anchorId = anchorId;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_radio_anchor_rank;
    }

    @Override
    public void initView(@NotNull View view) {
        radioAnchorRankAdapter = new RadioAnchorRankAdapter(getContext());
        xRecyclerView = view.findViewById(R.id.rv_rank);
        faceIv = view.findViewById(R.id.iv_face);
        joinFansTv = view.findViewById(R.id.tv_join_fans);
        nameTv = view.findViewById(R.id.tv_name);
        fansTv = view.findViewById(R.id.tv_fans);
        weekTv = view.findViewById(R.id.tv_week);
        sortTv = view.findViewById(R.id.tv_sort);
        desTv = view.findViewById(R.id.tv_des);
        joinContentTv = view.findViewById(R.id.tv_join_content);
        xRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        xRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                getData();
            }

            @Override
            public void onLoadMore() {
                getData();
            }
        });
        xRecyclerView.setAdapter(radioAnchorRankAdapter);
        joinFansTv = view.findViewById(R.id.tv_join_fans);
        if (Integer.valueOf(anchorId) == DataHelper.INSTANCE.getUID()) {
            joinFansTv.setText("修改粉丝昵称");
        } else {
            joinFansTv.setText("加入粉丝团");
        }
        if(rankType == RANK_FANS_TYPE_R){
            desTv.setText("剩余时间");
        }else{
            desTv.setText("热力值");
        }
        joinFansTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.valueOf(anchorId) == DataHelper.INSTANCE.getUID()) {
                    EditFansNameDialog dialog = new EditFansNameDialog(getContext());
                    dialog.setOnEditFansNameListener(new OnEditFansNameListener() {
                        @Override
                        public void onChange(String name) {
                            editFangroupName(name);
                        }
                    });
                    dialog.show();
                } else {
                    JoinTheFanGroupDialog dialog = new JoinTheFanGroupDialog();
                    dialog.show(getChildFragmentManager());
                }
            }
        });
        view.findViewById(R.id.cl_content).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RadioAnchorRankDialog dialog = new RadioAnchorRankDialog(anchorId);
                dialog.show(getFragmentManager());
            }
        });
        getData();
    }

    private void getData() {
        NetService.Companion.getInstance(getContext()).getFangroupAnchorFansList(mPage, anchorId, rankType, new Callback<AnchorRankBean>() {
            @Override
            public void onSuccess(int nextPage, AnchorRankBean bean, int code) {
                radioAnchorRankAdapter.setData(bean.getList());
                ImgUtil.INSTANCE.loadCircleImg(getContext(), bean.getInfo().getFace(), faceIv, -1);
                nameTv.setText(bean.getInfo().getNickname());
                fansTv.setText("" + bean.getInfo().getFans_num());
                weekTv.setText("" + bean.getInfo().getHot_value());
                sortTv.setText("" + bean.getInfo().getSort());
                if (Integer.valueOf(anchorId) == DataHelper.INSTANCE.getUID()) {
                    joinContentTv.setText("快来定制粉丝昵称，让粉丝牌独一无二~");
                } else {
                    joinContentTv.setText("主播已有" + bean.getInfo().getFans_num() + "名粉丝，快来加入吧～");
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

    private void editFangroupName(String fanName) {
        NetService.Companion.getInstance(getContext()).editFangroupName(fanName, new Callback<BaseBean>() {
            @Override
            public void onSuccess(int nextPage, BaseBean bean, int code) {

            }

            @Override
            public void onSuccessHasMsg(@NotNull String msg, BaseBean bean, int code) {
                ToastUtil.INSTANCE.i(getContext(), msg);
            }

            @Override
            public void onError(@NotNull String msg, @NotNull Throwable throwable, int code) {
                ToastUtil.INSTANCE.error(getContext(), msg);
            }

            @Override
            public boolean isAlive() {
                return isLive();
            }
        });
    }
}
