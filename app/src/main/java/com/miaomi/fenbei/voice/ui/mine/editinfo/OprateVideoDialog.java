//package com.miaomi.fenbei.voice.ui.mine.editinfo;
//
//import android.view.View;
//import android.widget.TextView;
//
//import com.miaomi.fenbei.voice.R;
//
//import me.shaohui.bottomdialog.BottomDialog;
//
//public class OprateVideoDialog extends BottomDialog {
//
//    private TextView deleteTv;
//    private TextView uploadTv;
//    private TextView previewTv;
//    private TextView cancelTv;
//    private OnOprateListner onOprateListner;
//
//    public void setOnOprateListner(OnOprateListner onOprateListner) {
//        this.onOprateListner = onOprateListner;
//    }
//
//    @Override
//    public void bindView(View v) {
//        super.bindView(v);
//        deleteTv = v.findViewById(R.id.tv_delele);
//        deleteTv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (onOprateListner != null){
//                    onOprateListner.onDelete();
//                }
//            }
//        });
//        uploadTv = v.findViewById(R.id.tv_upload);
//        uploadTv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (onOprateListner != null){
//                    onOprateListner.onUpload();
//                }
//            }
//        });
//        previewTv = v.findViewById(R.id.tv_preview);
//        cancelTv = v.findViewById(R.id.tv_cancel);
//        previewTv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (onOprateListner != null){
//                    onOprateListner.onPreview();
//                }
//            }
//        });
//        cancelTv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (onOprateListner != null){
//                    onOprateListner.onCancel();
//                }
//            }
//        });
//    }
//
//    @Override
//    public int getLayoutRes() {
//        return R.layout.dialog_video_oprate;
//    }
//
//    public interface OnOprateListner{
//        void onDelete();
//        void onUpload();
//        void onPreview();
//        void onCancel();
//    }
//}
