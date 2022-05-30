package com.miaomi.fenbei.voice.ui.room.music;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.widget.ImageView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.indicatorlib.base.BaseFragmentAdapter;
import com.example.indicatorlib.views.TabLayout;
import com.miaomi.fenbei.base.bean.db.MusicBean;
import com.miaomi.fenbei.base.core.BaseActivity;
import com.miaomi.fenbei.voice.R;
import com.miaomi.fenbei.voice.dialog.MusicAuditionDialog;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

@Route(path = "/app/music")
public class MusicActivity extends BaseActivity implements RoomMusicFragment.OnMusicStatusChange {
    private List<Fragment> mFragmentList = new ArrayList<>();
    private ViewPager mViewPager;
    private ImageView ivBack;
//    ImageView albumIv;
//    TextView musicTv;
//    TextView playBt;
//    TextView musicArtist;
//    ConstraintLayout playLayout;
    TabLayout tabTitle;

//    MediaPlayer mediaPlayer ;
//    boolean isPlay;

    @Override
    public int getLayoutId() {
        return R.layout.activity_music;
    }

    public static void startActivity(Context context){
        Intent intent = new Intent(context,MusicActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void initView() {
        setBaseStatusBar(false,false);
        mViewPager = findViewById(R.id.vp_music);
        tabTitle = findViewById(R.id.tab_layout);
        RoomMusicFragment localMusicFragment = RoomMusicFragment.newInstance(RoomMusicFragment.TYPE_MUSIC_LOCAL);
        localMusicFragment.setOnMusicStatusChange(this);
        RoomMusicFragment hotMusicFragment = RoomMusicFragment.newInstance(RoomMusicFragment.TYPE_MUSIC_HOT);
        hotMusicFragment.setOnMusicStatusChange(this);
        mFragmentList.add(localMusicFragment);
        mFragmentList.add(hotMusicFragment);
        mViewPager.setAdapter(new BaseFragmentAdapter(getSupportFragmentManager(),mFragmentList));
        tabTitle.setViewPager(mViewPager);
        tabTitle.setTitles("本地音乐", "热门曲库");
        tabTitle.setStripColor(Color.WHITE);
        tabTitle.setActiveColor(Color.WHITE);
        tabTitle.setInactiveColor(Color.WHITE);
        tabTitle.setStripType(TabLayout.StripType.POINT);
        tabTitle.setStripGravity(TabLayout.StripGravity.BOTTOM);
        tabTitle.setAnimationDuration(300);
    }

    @Override
    public void onItemSelected(MusicBean bean) {
        MusicAuditionDialog dialog = new MusicAuditionDialog(bean.getName(),bean.getUrl(),MusicActivity.this);
        dialog.show();
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
