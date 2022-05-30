package com.miaomi.fenbei.imkit.ui.viewholder.message;

import android.view.View;
import android.widget.TextView;

import com.miaomi.fenbei.base.bean.event.C2CMsgBean;
import com.miaomi.fenbei.base.util.DataHelper;
import com.miaomi.fenbei.imkit.R;

public class MsgRedEnvelopeHolder extends MsgBaseHolder {
    private final TextView otherMoney;
    private final TextView meMoney;

    public MsgRedEnvelopeHolder(View itemView) {
        super(itemView);
        otherMoney = itemView.findViewById(R.id.tv_other_money);
        meMoney = itemView.findViewById(R.id.tv_me_money);
    }

    @Override
    void bindChildData(final C2CMsgBean bean) {
        if (DataHelper.INSTANCE.getUID() == Integer.parseInt(bean.getSender())) {
            meMoney.setText(bean.getCustomBean().getContent());
        } else {
            otherMoney.setText(bean.getCustomBean().getContent());
        }
    }

}