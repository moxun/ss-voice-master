package com.miaomi.fenbei.voice.ui.family;

import android.view.View;


import com.miaomi.fenbei.base.bean.FamilyCenterInfoBean;
import com.miaomi.fenbei.base.core.BaseFragment;
import com.miaomi.fenbei.voice.R;

import org.jetbrains.annotations.NotNull;


import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class FamilyRoomFragment extends BaseFragment {
    RecyclerView mFamilyRoomRv;
    private FamilyRoomAdapter mFamilyRoomAdapter;

    public static FamilyRoomFragment newInstance(){
        return new FamilyRoomFragment();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_family_room;
    }

    @Override
    public void initView(@NotNull View view) {
        mFamilyRoomAdapter = new FamilyRoomAdapter(getMContext());
        mFamilyRoomRv = view.findViewById(R.id.rv_family_room);
        mFamilyRoomRv.setLayoutManager(new LinearLayoutManager(getMContext()));
        mFamilyRoomRv.setAdapter(mFamilyRoomAdapter);
    }
    public void updata(List<FamilyCenterInfoBean.RoomInfoBean> list){
        if (mFamilyRoomAdapter != null && list != null){
            mFamilyRoomAdapter.setData(list);
        }
    }
}
