package com.miaomi.fenbei.voice.dialog;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.miaomi.fenbei.base.bean.ChatRoomInfo;
import com.miaomi.fenbei.base.bean.NobleBean;
import com.miaomi.fenbei.base.core.dialog.BaseBottomDialog;
import com.miaomi.fenbei.base.util.ImgUtil;
import com.miaomi.fenbei.base.widget.DCBTextView;
import com.miaomi.fenbei.voice.R;


/**
 * 续费贵族
 */
public class RenewVipNobleDialog extends BaseBottomDialog {
   private ImageView cleanIv;
   private ImageView bgIv;
   private TextView  nameTv;
   private TextView  expired_timeTv;
   private DCBTextView renew_priceTv;
   private DCBTextView renew_return_diamondsTv;
   private DCBTextView fu_renew_priceTv;
   private ImageView   payIv;
    private NobleBean nobleBean=new NobleBean();
    public RenewVipNobleDialog(NobleBean nobleBean) {
        this.nobleBean = nobleBean;
    }
    @Override
    public int getLayoutRes() {
        return R.layout.dialog_renew_vip;
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
                if (onrenewVipNoble!=null){
                    dismiss();
                    onrenewVipNoble.onPay();
                }
            }
        });
    }


    private void renderView() {
        ImgUtil.INSTANCE.loadImg(getContext(),nobleBean.getIcon(),bgIv);
        nameTv.setText(nobleBean.getName());
        expired_timeTv.setText("到期时间:"+nobleBean.getExpired_time());
        renew_priceTv.setText(""+nobleBean.getRenew_price());
        renew_return_diamondsTv.setText(""+nobleBean.getRenew_return_diamonds());
        fu_renew_priceTv.setText(nobleBean.getRenew_price()+" /30天");
      }
    oNrenewVipNoble onrenewVipNoble=null;

    public void setOnRenewVip(oNrenewVipNoble onrenewVipNoble){
        this. onrenewVipNoble = onrenewVipNoble;
    }
    public interface oNrenewVipNoble{
        void onPay();
    }
}
