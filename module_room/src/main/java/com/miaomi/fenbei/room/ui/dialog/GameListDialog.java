package com.miaomi.fenbei.room.ui.dialog;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.miaomi.fenbei.base.bean.RoomGameListBean;

import com.miaomi.fenbei.base.config.BaseConfig;
import com.miaomi.fenbei.base.core.dialog.BaseBottomDialog;
import com.miaomi.fenbei.base.net.Callback;
import com.miaomi.fenbei.base.net.NetService;

import com.miaomi.fenbei.base.util.ToastUtil;
import com.miaomi.fenbei.base.web.WebActivity;
import com.miaomi.fenbei.room.ChatRoomManager;
import com.miaomi.fenbei.room.R;
import com.miaomi.fenbei.room.ui.adapter.GameListAdapter;
import com.miaomi.fenbei.room.ui.dialog.xb.GameXBDialog;
import com.miaomi.fenbei.room.ui.dialog.xy.XYDialog;
import com.miaomi.fenbei.room.ui.dialog.zs.ZSHomeDialog;


import org.jetbrains.annotations.NotNull;

import java.util.List;

public class GameListDialog extends BaseBottomDialog {
    private RecyclerView gameRv;
   
    private GameListAdapter adapter;
    @Override
    public int getLayoutRes() {
        return R.layout.room_dialog_game_list;
    }

    @Override
    public void bindView(View v) {
        gameRv = v.findViewById(R.id.rv_game);
     
        adapter=new GameListAdapter(getContext());
        gameRv.setLayoutManager(new GridLayoutManager(getContext(),4));
        gameRv.setAdapter(adapter);
        getData();
        adapter.setOnCheckItemListener(new GameListAdapter.CheckItemListener() {
            @Override
            public void itemChecked(RoomGameListBean roomGameListBean) {
                dismiss();
                switch (roomGameListBean.getMold()){
                    case BaseConfig.BANNER_MOLD_GAME_XX:
                        GameXXDialog gameXXDialog=new GameXXDialog(getContext());
                        gameXXDialog.show();
                        break;
                    case BaseConfig.BANNER_MOLD_GAME_ZS:
                        ZSHomeDialog zsHomeDialog=new ZSHomeDialog();
                        zsHomeDialog.show(getFragmentManager());
                        break;
                    case BaseConfig.BANNER_MOLD_WEB:
                    case BaseConfig.BANNER_MOLD_WEEK_STAR:
                         WebActivity.start(getContext(), roomGameListBean.getUrl(), roomGameListBean.getTitle());
                        break;
                    case  BaseConfig.BANNER_MOLD_SMASH_EGG:
                        XYDialog xyDialog=new XYDialog();
                        xyDialog.show(getFragmentManager());
                        break;
                    case  BaseConfig.BANNER_MOLD_GAME_CB:
                        GameXBDialog gameXBDialog=new GameXBDialog();
                        gameXBDialog.show(getFragmentManager());
                        break;
                    default:
                        ToastUtil.INSTANCE.i(getContext(),"请升级版本查看");
                        break;
                }

            }
        });
    }

    public void getData(){

        NetService.Companion.getInstance(getContext()).gameInfo(getString(R.string.banner_type),"2",ChatRoomManager.INSTANCE.getRoomId(),new Callback<List<RoomGameListBean>>() {
            @Override
            public void onSuccess(int nextPage, List<RoomGameListBean> bean, int code) {
                adapter.setData(bean);

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
