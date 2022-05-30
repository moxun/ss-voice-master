package com.miaomi.fenbei.voice.ui.mine.level;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.miaomi.fenbei.base.bean.LevelBean;
import com.miaomi.fenbei.base.core.BaseActivity;
import com.miaomi.fenbei.base.util.DataHelper;
import com.miaomi.fenbei.base.util.ImgUtil;
import com.miaomi.fenbei.base.web.WebActivity;
import com.miaomi.fenbei.base.widget.LevelView;
import com.miaomi.fenbei.base.widget.xrecyclerview.XRecyclerView;
import com.miaomi.fenbei.voice.R;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;

public class NewLevelActivity extends BaseActivity {

    public final static int LEVEL_TYPE_ML = 1;
    public final static int LEVEL_TYPE_GX = 2;
    private ImageView faceIv;
    private TextView nameTv;
    private LevelView levelIv;
    private int levelType;
    private FrameLayout contentFl;
    private TextView tipTv;
    private NewLevelAdapter mlAdapter;
    private XRecyclerView levelRv;
    private LinearLayout userinfoLL;
    private TextView sufferTv;
    private TextView tv_level_sh;
    private TextView tv_level_ml;
    private ProgressBar progressBar;
//    private TextView totalTv;

    public static void start(Context context,int type){
        Intent intent = new Intent(context,NewLevelActivity.class);
        intent.putExtra("level_type",type);
        context.startActivity(intent);
    }
    @Override
    public int getLayoutId() {
        return R.layout.fragment_level;
    }

    @Override
    public void initView() {
        setBaseStatusBar(false,false);
        findViewById(R.id.iv_back).setOnClickListener(v -> finish());
        View headView = LayoutInflater.from(NewLevelActivity.this).inflate(R.layout.level_head,null,false);
        headView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT));
        levelType = getIntent().getIntExtra("level_type",LEVEL_TYPE_ML);
        faceIv = findViewById(R.id.iv_face);
        nameTv = findViewById(R.id.tv_name);
        levelIv = findViewById(R.id.iv_level);
        contentFl = findViewById(R.id.fl_content);
        tipTv = headView.findViewById(R.id.tv_tip);
//        jctxLL = headView.findViewById(R.id.ll_jctx);
        levelRv = findViewById(R.id.level_rv);
        userinfoLL = findViewById(R.id.ll_userinfo);
        sufferTv = findViewById(R.id.tv_suffer);
        progressBar = findViewById(R.id.progress_bar);
        tv_level_sh=findViewById(R.id.tv_level_sh);
        tv_level_ml= findViewById(R.id.tv_level_ml);
//        totalTv = findViewById(R.id.tv_total);
//        progressTv = view.findViewById(R.id.tv_progress);
        findViewById(R.id.iv_explain).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebActivity.start(NewLevelActivity.this,"http://decibel-web.cnciyin.com/grade_explain","等级说明");
            }
        });
        levelRv.setLayoutManager(new LinearLayoutManager(NewLevelActivity.this));
        levelRv.setLoadingMoreEnabled(false);
        levelRv.setPullRefreshEnabled(false);
        levelRv.addHeaderView(headView);
        nameTv.setText(DataHelper.INSTANCE.getUserInfo().getNickname());
        setTitle("等级说明");
        tv_level_sh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_level_sh.setTextColor(Color.parseColor("#FFFFFF"));
                tv_level_sh.setTextSize(14);
//                tv_level_sh.setBackgroundResource(R.drawable.bg_mine_leven_type);
                tv_level_ml.setTextColor(Color.parseColor("#59FFFFFF"));
                tv_level_ml.setTextSize(13);
//                tv_level_ml.setBackgroundResource(R.drawable.bg_mine_leven);
                show_level_type(LEVEL_TYPE_GX);
            }
        });
        tv_level_ml.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_level_ml.setTextColor(Color.parseColor("#FFFFFF"));
                tv_level_ml.setTextSize(14);
//                tv_level_ml.setBackgroundResource(R.drawable.bg_mine_leven_type);
                tv_level_sh.setTextColor(Color.parseColor("#59FFFFFF"));
                tv_level_sh.setTextSize(13);
//                tv_level_sh.setBackgroundResource(R.drawable.bg_mine_leven);
                show_level_type(LEVEL_TYPE_ML);
            }
        });
        ImgUtil.INSTANCE.loadCircleImg(NewLevelActivity.this,DataHelper.INSTANCE.getUserInfo().getFace(),faceIv,R.drawable.common_avter_placeholder);
        show_level_type(levelType);
    }
      public void show_level_type(int levelType){
          if (LEVEL_TYPE_GX == levelType){

              mlAdapter = new NewLevelAdapter(NewLevelActivity.this,true);
              levelRv.setAdapter(mlAdapter);
              contentFl.setSelected(true);
              tipTv.setSelected(true);
              userinfoLL.setSelected(true);
              levelIv.setWealthLevel(DataHelper.INSTANCE.getUserInfo().getWealth_level().getGrade());
              tipTv.setText("等级说明：每1点豪气值=1经验值。");
//            jctxLL.setVisibility(View.GONE);
              sufferTv.setText("距离您下次升级还需: "+DataHelper.INSTANCE.getUserInfo().getWealth_level().getSuffer()+"经验值");
//            float weight = (float)DataHelper.INSTANCE.getUserInfo().getWealth_level().getTotal()/((float)DataHelper.INSTANCE.getUserInfo().getWealth_level().getSuffer()+(float)DataHelper.INSTANCE.getUserInfo().getWealth_level().getTotal());
//            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0,ViewGroup.LayoutParams.WRAP_CONTENT,weight*100);
//            progressTv.setLayoutParams(layoutParams);
//            totalTv.setText(""+DataHelper.INSTANCE.getUserInfo().getWealth_level().getTotal());
//            totalTv.setTranslationX(weight* DensityUtil.INSTANCE.dp2px(NewLevelActivity.this,200-totalTv.getText().length()*5));
              LayerDrawable drawable = (LayerDrawable) progressBar.getProgressDrawable();
              GradientDrawable gradientDrawable = new GradientDrawable();
              gradientDrawable.setColor(Color.parseColor("#FD7F8F"));
              ClipDrawable clipDrawable = new ClipDrawable(gradientDrawable, Gravity.START,ClipDrawable.HORIZONTAL);
              drawable.setDrawableByLayerId(android.R.id.progress,clipDrawable);
              progressBar.setMax((int) (DataHelper.INSTANCE.getUserInfo().getWealth_level().getSuffer()+DataHelper.INSTANCE.getUserInfo().getWealth_level().getTotal()));
              progressBar.setProgress((int) DataHelper.INSTANCE.getUserInfo().getWealth_level().getTotal());
              List<LevelBean> list = new ArrayList<>();
//            String[] names = new String[]{"Lv.1-Lv.10","Lv.11-Lv.20","Lv.21-Lv.30",
//                    "Lv.31-Lv.40","Lv.41-Lv.50","Lv.51-Lv.60"};
//            Integer[] levels = new Integer[]{1,11,21,31,41,51};
//            Integer[] entryEffectsIs = new Integer[]{50,51,52,53,54,55,56,57,58};
              Integer[] exps = new Integer[]{100,300,500,700,900,1400,1900,2400,2900,3400,
                      3900,4900,5900,6900,7900,8900,9900,19900,29900,39900,
                      49900,59900,69900,79900,89900,99900,199900,299900,399900,499900,
                      599900,699900,799900,899900,999900,1999900,2999900,3999900,4999900,5999900,
                      6999900,7999900,8999900,9999900
                      ,11499900,12999900,14499900,15999900,17499900
                      ,19499900, 21499900,23499900,25499900,27499900,29499900,31499900,33499900,35499900,37499900,40499900};
              for (int i =0 ;i<exps.length ;i++){
                  LevelBean bean = new LevelBean();
//                bean.setName(names[i]);
//                bean.setIconLevel(levels[i]);
//                bean.setEntryEffectsId(entryEffectsIs[i]);
                  bean.setTotal(exps[i]);
                  list.add(bean);
              }
              mlAdapter.setData(list);
          }
          if (LEVEL_TYPE_ML == levelType){
              mlAdapter = new NewLevelAdapter(NewLevelActivity.this,false);
              levelRv.setAdapter(mlAdapter);
              userinfoLL.setSelected(false);
              contentFl.setSelected(false);
              tipTv.setSelected(false);
              levelIv.setCharmLevel(DataHelper.INSTANCE.getUserInfo().getCharm_level().getGrade());
              tipTv.setText("等级说明：每1点魅力值=1经验值。");
//            jctxLL.setVisibility(View.GONE);
              sufferTv.setText("距离您下次升级还需: "+DataHelper.INSTANCE.getUserInfo().getCharm_level().getSuffer()+"经验值");
//            float weight = (float)DataHelper.INSTANCE.getUserInfo().getCharm_level().getTotal()/((float)DataHelper.INSTANCE.getUserInfo().getCharm_level().getSuffer()+(float)DataHelper.INSTANCE.getUserInfo().getCharm_level().getTotal());
//            DecimalFormat df=new DecimalFormat("0.00");
//            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0,ViewGroup.LayoutParams.WRAP_CONTENT,weight*100);
//            progressTv.setLayoutParams(layoutParams);
//            totalTv.setText(""+DataHelper.INSTANCE.getUserInfo().getCharm_level().getTotal());
//            totalTv.setTranslationX(weight*DensityUtil.INSTANCE.dp2px(NewLevelActivity.this,200-totalTv.getText().length()*5));
              LayerDrawable drawable = (LayerDrawable) progressBar.getProgressDrawable();
              GradientDrawable gradientDrawable = new GradientDrawable();
              gradientDrawable.setColor(Color.parseColor("#FD7F8F"));
              ClipDrawable clipDrawable = new ClipDrawable(gradientDrawable,Gravity.START,ClipDrawable.HORIZONTAL);
              drawable.setDrawableByLayerId(android.R.id.progress,clipDrawable);
              progressBar.setMax((int) (DataHelper.INSTANCE.getUserInfo().getCharm_level().getSuffer()+DataHelper.INSTANCE.getUserInfo().getCharm_level().getTotal()));
              progressBar.setProgress((int) DataHelper.INSTANCE.getUserInfo().getCharm_level().getTotal());
              List<LevelBean> list = new ArrayList<>();
//            String[] names = new String[]{"Lv.1-Lv.10","Lv.11-Lv.20","Lv.21-Lv.30",
//                    "Lv.31-Lv.40","Lv.41-Lv.50","Lv.51-Lv.60"};
//            Integer[] levels = new Integer[]{1,11,21,31,41,51};
              Integer[] exps = new Integer[]{100,300,500,700,900,1400,1900,2400,2900,3400,
                      3900,4900,5900,6900,7900,8900,9900,19900,29900,39900,
                      49900,59900,69900,79900,89900,99900,199900,299900,399900,499900,
                      599900,699900,799900,899900,999900,1999900,2999900,3999900,4999900,5999900,
                      6999900,7999900,8999900,9999900
                      ,11499900,12999900,14499900,15999900,17499900
                      ,19499900, 21499900,23499900,25499900,27499900,29499900,31499900,33499900,35499900,37499900,40499900};
              for (int i =0 ;i<exps.length ;i++){
                  LevelBean bean = new LevelBean();
//                bean.setName(names[i]);
//                bean.setIconLevel(levels[i]);
                  bean.setTotal(exps[i]);
                  list.add(bean);
              }
              mlAdapter.setData(list);
          }
    }
}
