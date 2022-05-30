package com.miaomi.fenbei.room.ui.dialog;

import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.miaomi.fenbei.base.bean.AnchorRankBean;
import com.miaomi.fenbei.base.core.dialog.BaseBottomDialog;
import com.miaomi.fenbei.base.net.Callback;
import com.miaomi.fenbei.base.net.NetService;
import com.miaomi.fenbei.base.widget.xrecyclerview.XRecyclerView;
import com.miaomi.fenbei.room.R;

import org.jetbrains.annotations.NotNull;


public class RadioAnchorRankDialog extends BaseBottomDialog {
    private XRecyclerView xRecyclerView;
    private RadioAnchorRankAdapter radioAnchorRankAdapter;
    private String anchorId;
    private int mPage = 1;

    public RadioAnchorRankDialog(String anchorId) {
        this.anchorId = anchorId;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.dialog_radio_anchor;
    }

    @Override
    public void bindView(View v) {
        radioAnchorRankAdapter = new RadioAnchorRankAdapter(getContext());
        xRecyclerView = v.findViewById(R.id.rv_rank);
        v.findViewById(R.id.back_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        xRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        xRecyclerView.setAdapter(radioAnchorRankAdapter);
        getData();
    }

    private void getData(){
        NetService.Companion.getInstance(getContext()).getFangroupAnchorList(mPage, anchorId, new Callback<AnchorRankBean>() {
            @Override
            public void onSuccess(int nextPage, AnchorRankBean bean, int code) {
                radioAnchorRankAdapter.setData(bean.getList());
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
}
