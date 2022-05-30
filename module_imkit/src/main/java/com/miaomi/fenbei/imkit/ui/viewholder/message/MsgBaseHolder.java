package com.miaomi.fenbei.imkit.ui.viewholder.message;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.miaomi.fenbei.base.bean.event.C2CMsgBean;
import com.miaomi.fenbei.base.util.DataHelper;
import com.miaomi.fenbei.base.util.ImgUtil;
import com.miaomi.fenbei.base.util.TimeUtil;
import com.miaomi.fenbei.imkit.R;

public abstract class MsgBaseHolder extends RecyclerView.ViewHolder{

    private ImageView otherIv;
    private ImageView meIv;
    private TextView timeTv;
    LinearLayout otherLL;
    RelativeLayout meLL;
    private TextView readTv;

    public MsgBaseHolder(View itemView) {
        super(itemView);
        otherLL = itemView.findViewById(R.id.ll_other);
        meLL = itemView.findViewById(R.id.ll_me);
        meIv = itemView.findViewById(R.id.iv_me);
        otherIv = itemView.findViewById(R.id.iv_other);
        timeTv = itemView.findViewById(R.id.tv_time);
        readTv = itemView.findViewById(R.id.tv_read);
    }
    public void bindData(C2CMsgBean preBean,final C2CMsgBean bean){
        meIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build("/app/userhomepage")
                        .withString("user_id",bean.getSender()+"")
                        .navigation();

            }
        });
        otherIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build("/app/userhomepage")
                        .withString("user_id",bean.getSender())
                        .navigation();
            }
        });
//        meLL.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//                if (onItemOprateListener != null){
//                    onItemOprateListener.onLongClik(meLL,bean);
//                }
//                return true;
//            }
//        });
//        otherLL.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//                if (onItemOprateListener != null){
//                    onItemOprateListener.onLongClik(otherLL,bean);
//                }
//                return true;
//            }
//        });
        if (DataHelper.INSTANCE.getUID() == Integer.parseInt(bean.getSender())){
            otherLL.setVisibility(View.GONE);
            meLL.setVisibility(View.VISIBLE);
            readTv.setVisibility(View.VISIBLE);
            if (bean.getIsRead()){
                readTv.setSelected(true);
                readTv.setText("已读");
            }else{
                readTv.setSelected(false);
                readTv.setText("未读");
            }
            ImgUtil.INSTANCE.loadFaceIcon(itemView.getContext(),DataHelper.INSTANCE.getUserInfo().getFace(),meIv);
        }else{
            meLL.setVisibility(View.GONE);
            otherLL.setVisibility(View.VISIBLE);
            readTv.setVisibility(View.GONE);
            ImgUtil.INSTANCE.loadFaceIcon(itemView.getContext(),bean.getFace(),otherIv);
        }
        if (preBean == null){
            timeTv.setVisibility(View.VISIBLE);
            timeTv.setText(TimeUtil.longFormatTime(bean.getTime()));
        }else{
            if (bean.getTime() - preBean.getTime() > 60*5){
                timeTv.setVisibility(View.VISIBLE);
                timeTv.setText(TimeUtil.longFormatTime(bean.getTime()));
            }else{
                timeTv.setVisibility(View.GONE);
            }
        }
        bindChildData(bean);
    }

    protected boolean isSelf(C2CMsgBean bean){
        return DataHelper.INSTANCE.getUID() == Integer.parseInt(bean.getSender());
    }

    OnItemOprateListener onItemOprateListener;
    public interface OnItemOprateListener{
        void onLongClik(View view,C2CMsgBean bean);
    }
    public void setOnItemOprateListener(OnItemOprateListener onItemOprateListener){
        this.onItemOprateListener = onItemOprateListener;
    }

    abstract void bindChildData(C2CMsgBean bean);
}
