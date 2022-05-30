//package com.miaomi.fenbei.imkit.ui.viewholder.message;
//
//import android.view.View;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//
//import com.alibaba.android.arouter.launcher.ARouter;
//import com.miaomi.fenbei.common.bean.event.C2CMsgBean;
//import com.miaomi.fenbei.common.util.DataHelper;
//import com.miaomi.fenbei.common.util.ImgUtil;
//import com.miaomi.fenbei.common.util.TimeUtil;
//import com.miaomi.fenbei.imkit.R;
//
//
//public class MsgRepalyTextHolder extends MsgBaseHolder {
//    private TextView meTv;
//    private TextView otherTv;
//    private ImageView otherIv;
//    private ImageView meIv;
//    private TextView timeTv;
//    LinearLayout otherLL;
//    RelativeLayout meLL;
//    private TextView readTv;
//
//    public MsgRepalyTextHolder(View itemView) {
//        super(itemView);
//        meTv = itemView.findViewById(R.id.tv_me);
//        otherTv = itemView.findViewById(R.id.tv_other);
//        otherLL = itemView.findViewById(R.id.ll_other);
//        meLL = itemView.findViewById(R.id.ll_me);
//        meIv = itemView.findViewById(R.id.iv_me);
//        otherIv = itemView.findViewById(R.id.iv_other);
//        timeTv = itemView.findViewById(R.id.tv_time);
//        readTv = itemView.findViewById(R.id.tv_read);
//    }
//
//    @Override
//    public void bindData(C2CMsgBean preBean, final C2CMsgBean bean) {
//
//        meIv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ARouter.getInstance().build("/app/userhomepage")
//                        .withString("user_id",DataHelper.INSTANCE.getUserInfo().getUser_id()+"")
//                        .navigation();
//
//            }
//        });
//        otherIv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ARouter.getInstance().build("/app/userhomepage")
//                        .withString("user_id",bean.getSender())
//                        .navigation();
//            }
//        });
//
//        if (preBean == null){
//            timeTv.setVisibility(View.VISIBLE);
//            timeTv.setText(TimeUtil.longFormatTime(bean.getTime()));
//        }else{
//            if (bean.getTime() - preBean.getTime() > 60*5){
//                timeTv.setVisibility(View.VISIBLE);
//                timeTv.setText(TimeUtil.longFormatTime(bean.getTime()));
//            }else{
//                timeTv.setVisibility(View.GONE);
//            }
//        }
//        ImgUtil.INSTANCE.loadFaceIcon(itemView.getContext(), DataHelper.INSTANCE.getUserInfo().getFace(),meIv);
//        ImgUtil.INSTANCE.loadFaceIcon(itemView.getContext(),bean.getFace(),otherIv);
//        if (DataHelper.INSTANCE.getUID() == Integer.parseInt(bean.getSender())){
//            readTv.setVisibility(View.VISIBLE);
//            meTv.setText(bean.getCustomBean().getContent());
//            otherLL.setVisibility(View.GONE);
//            meLL.setVisibility(View.VISIBLE);
//        }else{
//            meTv.setText(bean.getCustomBean().getPreTextMsg());
//            otherTv.setText(bean.getCustomBean().getContent());
//            meLL.setVisibility(View.VISIBLE);
//            otherLL.setVisibility(View.VISIBLE);
//        }
//        if (bean.getIsRead()){
//            readTv.setSelected(true);
//            readTv.setText("已读");
//        }else{
//            readTv.setSelected(false);
//            readTv.setText("未读");
//        }
//    }
//
//    @Override
//    void bindChildData(C2CMsgBean bean) {
//
//    }
//
//}
