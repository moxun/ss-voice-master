package com.miaomi.fenbei.voice.ui.mine.message;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.miaomi.fenbei.base.bean.MessageBean;
import com.miaomi.fenbei.base.core.BaseActivity;
import com.miaomi.fenbei.base.core.dialog.CommonDialog;
import com.miaomi.fenbei.base.net.Callback;
import com.miaomi.fenbei.base.net.Data;
import com.miaomi.fenbei.base.net.NetService;
import com.miaomi.fenbei.base.util.DataHelper;
import com.miaomi.fenbei.base.util.LoadHelper;
import com.miaomi.fenbei.base.util.ToastUtil;
import com.miaomi.fenbei.base.widget.xrecyclerview.XRecyclerView;
import com.miaomi.fenbei.voice.R;
import com.miaomi.fenbei.voice.ui.mine.message.adapter.MessageAdapter;

import org.jetbrains.annotations.NotNull;

import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.List;

@Route(path = "/app/systemmessage")
public class SystemMessageActivity extends BaseActivity implements XRecyclerView.LoadingListener{

    private static final int TYPE_REFRESH = 0;
    private static final int TYPE_LOADMORE = 1;

    private XRecyclerView rvMessage;
    private ImageView backBtn;
    private MessageAdapter mMessageAdapter;
    private CommonDialog mCommonDialog;
    private LoadHelper loadHelper;
    private  ImageView tvEmpty;

    private int page = 1;

    public static Intent getIntent(Context context) {
        return new Intent(context, SystemMessageActivity.class);
    }
    @Override
    public int getLayoutId() {
        return R.layout.user_activity_system_message;
    }

    @Override
    public void initView() {
        setBaseStatusBar(false,false);
        loadHelper = new LoadHelper();
        rvMessage = findViewById(R.id.rv_message);
        tvEmpty = findViewById(R.id.right_iv);
        backBtn = findViewById(R.id.back_btn);
        backBtn.setOnClickListener(v -> {
            setResult(RESULT_OK);
            finish();
        });

        loadHelper.registerLoad(rvMessage);
        mCommonDialog = new CommonDialog(this);
        mCommonDialog.setTitle("清空消息");
        mCommonDialog.setContent("您确定要清空消息内容吗？");
        mCommonDialog.setLeftBt("取消", v -> mCommonDialog.dismiss());
        mCommonDialog.setRightBt("确定", v -> {
//                clean();MsgManager.INSTANCE.getSystemUid()
//
            mCommonDialog.dismiss();
        });
        tvEmpty.setVisibility(View.GONE);
        tvEmpty.setBackgroundResource(R.drawable.icon_clear_conversation_msg);
        tvEmpty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               mCommonDialog.show();
            }
        });
        mMessageAdapter = new MessageAdapter();
        rvMessage.setAdapter(mMessageAdapter);
        rvMessage.setLayoutManager(new LinearLayoutManager(SystemMessageActivity.this));
        rvMessage.setLoadingMoreEnabled(false);
        rvMessage.setPullRefreshEnabled(true);
        rvMessage.setLoadingListener(this);
        getData(TYPE_REFRESH,1);
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_OK);
        finish();
    }

    private void getData(final int type, final int mPage){
        NetService.Companion.getInstance(this).getMessageInfo(DataHelper.INSTANCE.getLoginToken(), mPage, new Callback<List<MessageBean>>() {
            @Override
            public void onSuccess(int nextPage, List<MessageBean> list, int code) {
                page = nextPage;
                if (page == 1) {
                    rvMessage.setLoadingMoreEnabled(false);
                } else {
                    rvMessage.setLoadingMoreEnabled(true);
                }
                if (type == TYPE_REFRESH) {
                    if (list.size() == 0){
                        loadHelper.setEmptyCallback(0,"暂无通知~");
                    }else{
                        loadHelper.bindView(code);
                    }
                    mMessageAdapter.setData(list);
                    rvMessage.refreshComplete();
                } else {
                    loadHelper.bindView(code);
                    mMessageAdapter.addData(list);
                    rvMessage.loadMoreComplete();
                }
            }
            @Override
            public void onError(@NotNull String msg, @NotNull Throwable throwable, int code) {
                ToastUtil.INSTANCE.error(SystemMessageActivity.this,msg);
                loadHelper.setErrorCallback(code, v -> {
                    loadHelper.bindView(Data.CODE_LOADING);
                    onRefresh();
                });
            }

            @Override
            public boolean isAlive() {
                return isLive();
            }
        });
    }

    @Override
    public void onRefresh() {
        getData(TYPE_REFRESH,1);
    }

    @Override
    public void onLoadMore() {
        getData(TYPE_LOADMORE,page);
    }
}
