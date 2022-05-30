package com.miaomi.fenbei.gift;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.miaomi.fenbei.base.bean.GiftBean;
import com.miaomi.fenbei.base.bean.GiftInfoBean;
import com.miaomi.fenbei.base.util.ImgUtil;

import java.util.List;

import androidx.annotation.NonNull;

public class ExpressGiftDialog extends Dialog {

    private OnExpressListener onExpressListener;
    private GiftBean.DataBean selectedGift;
    private List<GiftInfoBean.ListBean> userList;
    private EditText contentEt;
    private ImageView faceIv;
    private TextView nickNameTv;

    public ExpressGiftDialog(@NonNull Context context,OnExpressListener onExpressListener,GiftBean.DataBean selectedGift,List<GiftInfoBean.ListBean> userList) {
        super(context, R.style.common_dialog);
        this.onExpressListener = onExpressListener;
        this.selectedGift = selectedGift;
        this.userList = userList;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gift_dialog_express_gift);
        contentEt = findViewById(R.id.et_content);
        faceIv = findViewById(R.id.iv_face);
        nickNameTv = findViewById(R.id.tv_nick_name);
        if (userList.size() > 1){
            faceIv.setVisibility(View.GONE);
            nickNameTv.setText(userList.get(0).getNickname()+"等"+userList.size()+"人");
        }else{
            faceIv.setVisibility(View.VISIBLE);
            ImgUtil.INSTANCE.loadFaceIconNoCheck(getContext(),userList.get(0).getFace(),faceIv);
            nickNameTv.setText(userList.get(0).getNickname());
        }
        findViewById(R.id.tv_express).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                String note = contentEt.getText().toString();
                onExpressListener.onExpress(note,selectedGift,userList);
            }
        });
        findViewById(R.id.iv_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    @Override
    public void show() {
        super.show();
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT;
        getWindow().getDecorView().setPadding(0, 0, 0, 0);
        getWindow().setAttributes(layoutParams);
    }

    public interface OnExpressListener{
        void onExpress(String note,GiftBean.DataBean selectedGift,List<GiftInfoBean.ListBean> userList);
    }
}
