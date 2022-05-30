package com.miaomi.fenbei.voice.ui.dress;

import android.content.Context;
import android.content.Intent;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.indicatorlib.base.BaseFragmentAdapter;
import com.miaomi.fenbei.base.BigMicSeatView;
import com.miaomi.fenbei.base.MicSeatView;
import com.miaomi.fenbei.base.bean.BaseBean;
import com.miaomi.fenbei.base.bean.DressItemBean;
import com.miaomi.fenbei.base.bean.MineBean;
import com.miaomi.fenbei.base.config.BaseConfig;
import com.miaomi.fenbei.base.core.BaseActivity;
import com.miaomi.fenbei.base.core.dialog.CommonDialog;
import com.miaomi.fenbei.base.net.Callback;
import com.miaomi.fenbei.base.net.NetService;
import com.miaomi.fenbei.base.util.DataHelper;
import com.miaomi.fenbei.base.util.ImgUtil;
import com.miaomi.fenbei.base.util.ToastUtil;
import com.miaomi.fenbei.base.widget.KMDressIndicator;
import com.miaomi.fenbei.base.widget.LevelView;
import com.miaomi.fenbei.voice.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

public class MyDressActivity extends BaseActivity implements OnDressItemClickListener {

    //    private ImageView faceIv;
    private BigMicSeatView iconIv;
    private ViewPager mViewPager;
    private KMDressIndicator kmDressIndicator;
    private TextView noUseTv;
    private int dressType;
    private TextView nameTv;
    private LevelView gongxianIv;
    private LevelView meiliIv;
    private TextView signTv;
    private ImageView avterIv;

    public static void start(Context context) {
        Intent intent = new Intent(context, MyDressActivity.class);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_my_adress;
    }

    @Override
    public void initView() {
        setBaseStatusBar(false, false);
        findViewById(R.id.iv_back).setOnClickListener(v -> finish());
        avterIv = findViewById(R.id.iv_avter);
//        faceIv = findViewById(R.id.iv_face);
        iconIv = findViewById(R.id.iv_icon);
        noUseTv = findViewById(R.id.tv_my_no_use);
        nameTv = findViewById(R.id.tv_name);
        gongxianIv = findViewById(R.id.iv_gongxian);
        meiliIv = findViewById(R.id.iv_meili);
        signTv = findViewById(R.id.tv_sign);

        iconIv.loadSeat(DataHelper.INSTANCE.getUserInfo().getSeat_frame(),DataHelper.INSTANCE.getUserInfo().getReplaceArr());

        mViewPager = findViewById(R.id.view_pager);
        kmDressIndicator = findViewById(R.id.tab_layout);
        kmDressIndicator.setViewPager(mViewPager,new String[]{"头像框", "进场特效","座驾","勋章"});
        List<Fragment> mFragmentList = new ArrayList<>();
        MyDressFragment fragment1 = MyDressFragment.newInstance(BaseConfig.DRESS_TYPE_ZWK);
        fragment1.setOnDressItemClickListener(this);
        mFragmentList.add(fragment1);

        MyDressFragment fragment2 = MyDressFragment.newInstance(BaseConfig.DRESS_TYPE_JCTX);
        fragment2.setOnDressItemClickListener(this);
        mFragmentList.add(fragment2);

        MyDressFragment fragment3 = MyDressFragment.newInstance(BaseConfig.DRESS_TYPE_ZJ);
        fragment3.setOnDressItemClickListener(this);
        mFragmentList.add(fragment3);

        MyDressFragment fragment4 = MyDressFragment.newInstance(BaseConfig.DRESS_TYPE_XZ);
        fragment4.setOnDressItemClickListener(this);
        mFragmentList.add(fragment4);

        noUseTv.setText("不使用头像框");
        mViewPager.setOffscreenPageLimit(mFragmentList.size());
        mViewPager.setAdapter(new BaseFragmentAdapter(getSupportFragmentManager(), mFragmentList));
        noUseTv.setOnClickListener(v -> use(0));
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                dressType = position;
                if (dressType == BaseConfig.DRESS_TYPE_ZWK) {
                    noUseTv.setText("不使用头像框");
                }
                if (dressType == BaseConfig.DRESS_TYPE_JCTX) {
                    noUseTv.setText("不使用进场特效");
                }
                if (dressType == BaseConfig.DRESS_TYPE_ZJ) {
                    noUseTv.setText("不使用座驾");
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        getData();
    }


    private void use(int dressId) {
        NetService.Companion.getInstance(MyDressActivity.this).useDress(dressId, dressType, new Callback<BaseBean>() {
            @Override
            public void onSuccess(int nextPage, BaseBean bean, int code) {
                if (dressId > 0) {
                    ToastUtil.INSTANCE.suc(MyDressActivity.this, "使用成功");
                } else {
                    iconIv.loadSeat("");
                    ToastUtil.INSTANCE.suc(MyDressActivity.this, "取消成功");
                }
            }

            @Override
            public void onError(@NotNull String msg, @NotNull Throwable throwable, int code) {
                ToastUtil.INSTANCE.error(MyDressActivity.this, msg);
            }

            @Override
            public boolean isAlive() {
                return isLive();
            }
        });
    }

    protected void itemClick(DressItemBean bean) {
        if (dressType == BaseConfig.DRESS_TYPE_ZWK) {
            iconIv.loadSeat(bean.getImg());
        }
        CommonDialog mCommonDialog = new CommonDialog(MyDressActivity.this);
        mCommonDialog.setTitle("友情提示");
        mCommonDialog.setContent("确定使用【" + bean.getName() + "】吗？");
        mCommonDialog.setLeftBt("取消", v1 -> mCommonDialog.dismiss());
        mCommonDialog.setRightBt("确定", v12 -> {
            use(bean.getUser_decorate_id());
            mCommonDialog.dismiss();
        });
        mCommonDialog.show();
    }


    @Override
    public void onClick(DressItemBean bean) {
        itemClick(bean);
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
