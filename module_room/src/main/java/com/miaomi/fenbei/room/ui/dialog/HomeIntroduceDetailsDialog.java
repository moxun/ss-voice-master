package com.miaomi.fenbei.room.ui.dialog;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.miaomi.fenbei.base.bean.RadioStationBean;
import com.miaomi.fenbei.base.bean.RoomGameListBean;
import com.miaomi.fenbei.base.core.dialog.BaseBottomDialog;
import com.miaomi.fenbei.base.util.ImgUtil;
import com.miaomi.fenbei.room.R;
import com.miaomi.fenbei.room.ui.adapter.ColumnListAdapter;

public class HomeIntroduceDetailsDialog extends BaseBottomDialog {
   
  private ImageView columnsIv;
 private  RadioStationBean radioStationBean=new RadioStationBean();
    public HomeIntroduceDetailsDialog(RadioStationBean radioStationBean) {
        this.radioStationBean = radioStationBean;
    }
    private  ImageView view_columnsIv;
    private TextView   coulmn_titleTv;
    private TextView  coulmn_timeTv;
    private ImageView  avterIv;
    private TextView   nameTv;
    private TextView  fan_numTv;
    private  TextView  coulmn_topicTv;
    private  TextView  coulmn_introductionTv;
    @Override
    public int getLayoutRes() {
        return R.layout.room_dialog_home_introduce_details;
    }

    @Override
    public void bindView(View v) {
        columnsIv=v.findViewById(R.id.iv_view_columns);
        view_columnsIv=v.findViewById(R.id.iv_column_icon);
        coulmn_titleTv= v.findViewById(R.id.tv_coulmn_title);
        coulmn_timeTv=v.findViewById(R.id.tv_coulmn_time);
        avterIv=v.findViewById(R.id.iv_avter);
        nameTv=v.findViewById(R.id.tv_name);
        fan_numTv=v.findViewById(R.id.tv_fan_num);
        coulmn_topicTv=v.findViewById(R.id.tv_coulmn_topic);
        coulmn_introductionTv= v.findViewById(R.id.tv_coulmn_introduction);
        getData();
        columnsIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                HomeIntroduceDialog homeIntroduceDialog=new HomeIntroduceDialog();
                homeIntroduceDialog.show(getFragmentManager());
            }
        });
    }

    public void getData(){
       if(radioStationBean!=null){
        coulmn_titleTv.setText(radioStationBean.getRadio_name());
        coulmn_timeTv.setText("本档时间:"+radioStationBean.getTime_period_start()+"-"+radioStationBean.getTime_period_end());
        if(!TextUtils.isEmpty( radioStationBean.getIcon())) {
            ImgUtil.INSTANCE.loadHomeHotImg(getContext(), radioStationBean.getIcon(), view_columnsIv, 6f, -1);
        }
        if(!TextUtils.isEmpty(radioStationBean.getFace())) {
            ImgUtil.INSTANCE.loadFaceIcon(getContext(),radioStationBean.getFace(), avterIv);
        }
        nameTv.setText(radioStationBean.getNickname());
        fan_numTv.setText(""+radioStationBean.getFans());
        coulmn_topicTv.setText("今日话题:"+radioStationBean.getToday_topic());
        coulmn_introductionTv.setText(radioStationBean.getIntroduction());
       }
    }

}
