package com.miaomi.fenbei.imkit.ui.viewholder.message;

import android.view.View;
import android.widget.TextView;

import com.miaomi.fenbei.base.bean.event.C2CMsgBean;
import com.miaomi.fenbei.base.util.DataHelper;
import com.miaomi.fenbei.imkit.R;

public class MsgUnSupportHolder extends MsgBaseHolder{
    private TextView otherTv;
    private TextView meTv;
    public MsgUnSupportHolder(View itemView) {
        super(itemView);
        otherTv = itemView.findViewById(R.id.tv_other);
        meTv = itemView.findViewById(R.id.tv_me);
    }

    @Override
    void bindChildData(final C2CMsgBean bean) {
        meTv.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (onItemOprateListener != null){
                    onItemOprateListener.onLongClik(meTv,bean);
                }
                return true;
            }
        });
        otherTv.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (onItemOprateListener != null){
                    onItemOprateListener.onLongClik(otherTv,bean);
                }
                return true;
            }
        });
        if (DataHelper.INSTANCE.getUID() == Integer.parseInt(bean.getSender())){
            meTv.setText("[当前版本不支持此消息请升级查看]");
        }else{
            otherTv.setText("[当前版本不支持此消息请升级查看]");
        }
        meTv.setTextIsSelectable(true);
        otherTv.setTextIsSelectable(true);
    }

}
