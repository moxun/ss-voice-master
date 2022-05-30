package com.miaomi.fenbei.voice.ui.dress;

import android.content.Context;
import android.content.Intent;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.indicatorlib.base.BaseFragmentAdapter;
import com.miaomi.fenbei.base.bean.DiamondsBean;
import com.miaomi.fenbei.base.bean.MineBean;
import com.miaomi.fenbei.base.config.BaseConfig;
import com.miaomi.fenbei.base.core.BaseActivity;
import com.miaomi.fenbei.base.net.Callback;
import com.miaomi.fenbei.base.net.NetService;
import com.miaomi.fenbei.base.util.ImgUtil;
import com.miaomi.fenbei.base.widget.KMDressIndicator;
import com.miaomi.fenbei.base.widget.LevelView;
import com.miaomi.fenbei.voice.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

public class ShoppingMallActivity extends BaseActivity {
//    private RecyclerView xRecyclerView;
//    private DressAdapter mFamilyLevelAdapter;
//    private ImageView faceIv;
//    private MicSeatView iconIv;
//    private ShopMallBean selectedAvter;
//    private TextView moneyTv;
    private ViewPager mViewPager;
    private KMDressIndicator kmDressIndicator;
    private ImageView avterIv;
    private TextView  nameTv;
    private LevelView gongxianIv;
    private LevelView meiliIv;
    private TextView  signTv;
    public static void start(Context context){
        Intent intent = new Intent(context,ShoppingMallActivity.class);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_shopping_mall;
    }

    @Override
    protected void onResume() {
        super.onResume();
//        getDiamonds();
        getData();
    }

    @Override
    public void initView() {
        setBaseStatusBar(false,false);
        findViewById(R.id.tv_my_adress).setOnClickListener(v -> {
            MyDressActivity.start(ShoppingMallActivity.this);
        });
        findViewById(R.id.iv_back).setOnClickListener(v -> finish());
//        moneyTv = findViewById(R.id.tv_money);
        avterIv=findViewById(R.id.iv_avter);
        nameTv=findViewById(R.id.tv_name);
        gongxianIv=findViewById(R.id.iv_gongxian);
        meiliIv=findViewById(R.id.iv_meili);
        signTv=findViewById(R.id.tv_sign);
        mViewPager = findViewById(R.id.view_pager);
        kmDressIndicator = findViewById(R.id.tab_layout);
        kmDressIndicator.setViewPager(mViewPager,new String[]{"头像框", "进场特效","座驾"});
        List<Fragment> mFragmentList = new ArrayList<>();
        mFragmentList.add(ShoppingMallFragment.newInstance(BaseConfig.DRESS_TYPE_ZWK));
        mFragmentList.add(ShoppingMallFragment.newInstance(BaseConfig.DRESS_TYPE_JCTX));
        mFragmentList.add(ShoppingMallFragment.newInstance(BaseConfig.DRESS_TYPE_ZJ));
//        mFragmentList.add(ShoppingMallFragment.newInstance(BaseConfig.DRESS_TYPE_XZ));
        mViewPager.setOffscreenPageLimit(mFragmentList.size());
        mViewPager.setAdapter(new BaseFragmentAdapter(getSupportFragmentManager(),mFragmentList));
    }
    private void getData() {
        NetService.Companion.getInstance(getBaseContext()).getMineInfo(getPackageName(), new Callback<MineBean>() {
            @Override
            public void onSuccess(int nextPage, MineBean bean, int code) {

                initData(bean);

            }

            @Override
            public void onError(@NotNull String msg, @NotNull Throwable throwable, int code) {


            }

            @Override
            public boolean isAlive() {
                return isLive();
            }
        });
    }

    private void initData(MineBean bean) {
        ImgUtil.INSTANCE.loadFaceIcon(this, bean.getFace(), avterIv);
        meiliIv.setCharmLevel(bean.getCharm_level().getGrade());
        gongxianIv.setWealthLevel(bean.getWealth_level().getGrade());
        nameTv.setText(bean.getNickname());
        signTv.setText(bean.getSignature());

    }

}
