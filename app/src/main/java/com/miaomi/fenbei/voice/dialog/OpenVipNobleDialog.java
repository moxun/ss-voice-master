package com.miaomi.fenbei.voice.dialog;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


import com.miaomi.fenbei.base.bean.ChatRoomInfo;

import com.miaomi.fenbei.base.bean.NobleBean;

import com.miaomi.fenbei.base.core.dialog.BaseBottomDialog;
import com.miaomi.fenbei.base.util.ImgUtil;
import com.miaomi.fenbei.base.widget.DCBTextView;
import com.miaomi.fenbei.voice.R;


/**
 * 开通贵族
 */
public class OpenVipNobleDialog extends BaseBottomDialog {
    private ImageView cleanIv;
    private ImageView bgIv;
    private TextView nameTv;
    private TextView  expired_timeTv;
    private DCBTextView renew_priceTv;
    private DCBTextView renew_return_diamondsTv;
    private DCBTextView fu_renew_priceTv;
    private DCBTextView wealth_valueTv;
    private EditText   invite_uidEt;
    private ImageView   payIv;
    private  NobleBean nobleBean=new NobleBean();
    public OpenVipNobleDialog(NobleBean nobleBean) {
        this.nobleBean = nobleBean;
    }
    @Override
    public int getLayoutRes() {
        return R.layout.dialog_open_vip;
    }

    @Override
    public void bindView(View v) {
        cleanIv=v.findViewById(R.id.iv_clean);
        bgIv=v.findViewById(R.id.iv_bg);
        nameTv=v.findViewById(R.id.tv_name);
        expired_timeTv=v.findViewById(R.id.tv_expired_time);
        renew_priceTv=v.findViewById(R.id.tv_renew_price);
        renew_return_diamondsTv=v.findViewById(R.id.tv_renew_return_diamonds);
        fu_renew_priceTv=v.findViewById(R.id.tv_fu_renew_price);
        wealth_valueTv=v.findViewById(R.id.tv_wealth_value);
        invite_uidEt=v.findViewById(R.id.et_invite_uid);
        payIv=v.findViewById(R.id.iv_pay);
        renderView();
        cleanIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
          dismiss();
            }
        });
        payIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onopenVipNoble!=null){
                    dismiss();

                    onopenVipNoble.onPay(invite_uidEt.getText().toString());
                }
            }
        });
    }


    private void renderView() {
        ImgUtil.INSTANCE.loadImg(getContext(),nobleBean.getIcon(),bgIv);
        nameTv.setText(nobleBean.getName());
        expired_timeTv.setText("到期时间:"+nobleBean.getExpired_time());
        renew_priceTv.setText(""+nobleBean.getPrice());
        renew_return_diamondsTv.setText(""+nobleBean.getRenew_return_diamonds());
        fu_renew_priceTv.setText(nobleBean.getPrice()+" /30天");
        wealth_valueTv.setText(nobleBean.getWealth_value()+"");
    }
    oNopenVipNoble onopenVipNoble=null;

    public void setOnOpenVip(oNopenVipNoble onopenVipNoble){
       this. onopenVipNoble = onopenVipNoble;
    }
    public interface oNopenVipNoble{
        void onPay(String invite_uid);
    }
}
