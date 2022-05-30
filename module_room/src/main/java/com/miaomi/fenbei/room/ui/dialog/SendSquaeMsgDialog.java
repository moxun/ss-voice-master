package com.miaomi.fenbei.room.ui.dialog;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.miaomi.fenbei.base.bean.BaseBean;
import com.miaomi.fenbei.base.core.dialog.BaseBottomDialog;
import com.miaomi.fenbei.base.core.dialog.CommonDialog;
import com.miaomi.fenbei.base.net.Callback;
import com.miaomi.fenbei.base.net.NetService;
import com.miaomi.fenbei.room.R;

import org.jetbrains.annotations.NotNull;

public class SendSquaeMsgDialog extends BaseBottomDialog {

    private EditText contentEt;
    private TextView sendTv;
    private TextView openFullTv;
    private ImageView openFullIv;
    private boolean isOpenSendTopMsg = false;
    private String TYPE_COMMON_MSG = "0";
    private String TYPE_TOP_MSG = "1";

    @Override
    public int getLayoutRes() {
        return R.layout.dialog_send_squae_msg;
    }

    @Override
    public void bindView(View v) {
        contentEt = v.findViewById(R.id.word_chat_btn);
        sendTv = v.findViewById(R.id.send_btn);
        openFullTv = v.findViewById(R.id.tv_open);
        openFullIv = v.findViewById(R.id.open_iv);

        openFullIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isOpenSendTopMsg) {
                    isOpenSendTopMsg = false;
                    openFullTv.setSelected(false);
                    openFullIv.setSelected(false);
                } else {
                    isOpenSendTopMsg = true;
                    openFullTv.setSelected(true);
                    openFullIv.setSelected(true);
                }
            }
        });
        openFullTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isOpenSendTopMsg) {
                    isOpenSendTopMsg = false;
                    openFullIv.setSelected(false);
                    openFullTv.setSelected(false);
                } else {
                    isOpenSendTopMsg = true;
                    openFullTv.setSelected(true);
                    openFullIv.setSelected(true);
                }
            }
        });

        sendTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isOpenSendTopMsg) {
                    final CommonDialog upDialog = new CommonDialog(getContext());
                    upDialog.setTitle("友情提示");
                    upDialog.setContent("本条交友消息需花费50钻石，是否发送？");
                    upDialog.setLeftBt("取消", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            upDialog.dismiss();
                        }
                    });
                    upDialog.setRightBt("确定", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            sendChat(TYPE_TOP_MSG, contentEt.getText().toString());
                            upDialog.dismiss();
                        }
                    });
                    upDialog.show();
                } else {
                    sendChat(TYPE_COMMON_MSG, contentEt.getText().toString());
                }
            }
        });
    }

    private void sendChat(String type, String msg) {
        if (TextUtils.isEmpty(msg)) {
            return;
        }
        NetService.Companion.getInstance(getContext()).chatSquare(type, msg, new Callback<BaseBean>() {
            @Override
            public void onSuccess(int nextPage, BaseBean bean, int code) {
                contentEt.setText("");
                contentEt.clearFocus();
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
