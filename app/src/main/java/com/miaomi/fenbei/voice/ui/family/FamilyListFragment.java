package com.miaomi.fenbei.voice.ui.family;

import android.os.Bundle;
import android.view.View;


import com.miaomi.fenbei.base.bean.FamilyBean;
import com.miaomi.fenbei.base.core.BaseFragment;
import com.miaomi.fenbei.base.dialog.ApplyDialog;
import com.miaomi.fenbei.base.net.Callback;
import com.miaomi.fenbei.base.net.NetService;
import com.miaomi.fenbei.base.util.ToastUtil;
import com.miaomi.fenbei.voice.R;

import org.jetbrains.annotations.NotNull;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class FamilyListFragment extends BaseFragment {
    RecyclerView familyRv;
    FamilyListAdapter adapter;
    public final static String RANK_TYPE = "rank_type";
    private int rankType;



    public static FamilyListFragment newInstance(int type){
        FamilyListFragment fragment = new FamilyListFragment();
        Bundle args = new Bundle();
        args.putInt(RANK_TYPE,type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        getData();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_family_list;
    }

    @Override
    public void initView(@NotNull View view) {
        rankType = getArguments().getInt(RANK_TYPE);
        adapter = new FamilyListAdapter(getMContext());
        adapter.setOnApplyClickListener(new FamilyListAdapter.OnFamilyListClickListener() {
            @Override
            public void onApply(String uid) {
                final ApplyDialog outDialog = new ApplyDialog(getMContext());
                outDialog.setContent("是否确认加入家族？");
                outDialog.setLeftBt("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        outDialog.dismiss();
                    }
                });
                outDialog.setRightBt("确定", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        outDialog.dismiss();
                        joinFamily(uid);
                    }
                });
                outDialog.show();
            }
        });
        familyRv = view.findViewById(R.id.rv_family);

        //添加自定义的分割线
        DividerItemDecoration divider = new DividerItemDecoration(getMContext(), DividerItemDecoration.VERTICAL);
        divider.setDrawable(ContextCompat.getDrawable(getMContext(), R.drawable.base_bg_divider));
        familyRv.addItemDecoration(divider);
        familyRv.setLayoutManager(new LinearLayoutManager(getMContext()));
        familyRv.setAdapter(adapter);
//        getData();
    }
    private void getData(){
        NetService.Companion.getInstance(getMContext()).getFamilyList(rankType, new Callback<FamilyBean>() {
            @Override
            public void onSuccess(int nextPage, FamilyBean bean, int code) {
                adapter.setData(bean.getList());
            }

            @Override
            public void onError(@NotNull String msg, @NotNull Throwable throwable, int code) {

            }

            @Override
            public boolean isAlive() {
                return false;
            }
        });
    }
    private void joinFamily(String familyId){
        NetService.Companion.getInstance(getMContext()).joinFamily(familyId, new Callback<Object>() {
            @Override
            public void onSuccess(int nextPage, Object bean, int code) {
                ToastUtil.INSTANCE.i(getMContext(),"申请已提交，请等待审核");
                getData();
            }

            @Override
            public void onError(@NotNull String msg, @NotNull Throwable throwable, int code) {
                ToastUtil.INSTANCE.i(getMContext(),msg);
            }

            @Override
            public boolean isAlive() {
                return true;
            }
        });
    }
}
