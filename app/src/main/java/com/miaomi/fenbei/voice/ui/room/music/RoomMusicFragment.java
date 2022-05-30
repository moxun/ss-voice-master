package com.miaomi.fenbei.voice.ui.room.music;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.miaomi.fenbei.room.util.MusicUtil;
import com.miaomi.fenbei.base.bean.BaseBean;
import com.miaomi.fenbei.base.bean.HotMusicSearchBean;
import com.miaomi.fenbei.base.bean.NetMusciBean;
import com.miaomi.fenbei.base.bean.db.MusicBean;
import com.miaomi.fenbei.base.core.BaseFragment;
import com.miaomi.fenbei.base.net.Callback;
import com.miaomi.fenbei.base.net.NetService;
import com.miaomi.fenbei.base.util.CopyUtil;
import com.miaomi.fenbei.base.util.DataHelper;
import com.miaomi.fenbei.base.util.ToastUtil;
import com.miaomi.fenbei.base.widget.search.SearchView;
import com.miaomi.fenbei.base.widget.xrecyclerview.XRecyclerView;
import com.miaomi.fenbei.voice.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;

public class RoomMusicFragment extends BaseFragment {
    XRecyclerView mRv;
    RoomMusicAdapter adapter;
    ArrayList<NetMusciBean> mList =new ArrayList<>();
    SearchView mSearch;
    LinearLayout uploadLL;
    TextView uploadTv;
    private int musicType;
    private int page = 1;
    private TextView emptyView;
    List<MusicBean> oldList = new ArrayList<>();

    private final static int TYPE_REFRESH = 0;
    private final static int TYPE_LOADMROE = 1;

    public final static String TYPE_MUSIC = "TYPE_MUSIC";
    public final static int TYPE_MUSIC_LOCAL = 1;
    public final static int TYPE_MUSIC_HOT = 2;
    public final static int TYPE_MUSIC_UPLOAD = 3;

    public static RoomMusicFragment newInstance(int type){
        RoomMusicFragment fragment = new RoomMusicFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(TYPE_MUSIC,type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_hot_music;
    }

    @Override
    public void initView(@NotNull View view) {
        musicType = getArguments().getInt(TYPE_MUSIC);
        adapter = new RoomMusicAdapter(getMContext());
        adapter.setMusicType(musicType);
        mRv = view.findViewById(R.id.music_list);
        mSearch = view.findViewById(R.id.sv_search);
        uploadTv = view.findViewById(R.id.tv_upload);
        uploadLL = view.findViewById(R.id.ll_upload);
        emptyView = view.findViewById(R.id.empty_view);
        uploadTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CopyUtil.copy(uploadTv.getText().toString(),getMContext());
                ToastUtil.INSTANCE.suc(getContext(),"已复制");
            }
        });
//        if (BuildConfig.DEBUG){
//            uploadTv.setText("http://mier2.hanyutiancheng.vip/music?token="+DataHelper.INSTANCE.getLoginToken());
//        }else{
//            uploadTv.setText("http://106.14.41.173/music?token="+DataHelper.INSTANCE.getLoginToken());
//        }
        uploadTv.setText(DataHelper.INSTANCE.getIMDevelop().getNew_main()+"/music?token="+DataHelper.INSTANCE.getLoginToken());
        if (TYPE_MUSIC_HOT == musicType){
            mRv.setLoadingMoreEnabled(true);
            mRv.setPullRefreshEnabled(true);
            mRv.setLoadingListener(new XRecyclerView.LoadingListener() {
                @Override
                public void onRefresh() {
                    getData(TYPE_REFRESH);
                }

                @Override
                public void onLoadMore() {
                    getData(TYPE_LOADMROE);
                }
            });
        }else{
            mRv.setLoadingMoreEnabled(false);
            mRv.setPullRefreshEnabled(false);
        }
        mRv.setLayoutManager(new LinearLayoutManager(getMContext()));
        mRv.setAdapter(adapter);
        adapter.setOnItemClickListener(new RoomMusicAdapter.OnItemClickListener() {
            @Override
            public void onAdd(MusicBean bean) {
               if (musicType == TYPE_MUSIC_UPLOAD){
                   addMusic(bean);
               }else if(musicType == TYPE_MUSIC_LOCAL){
                   int code = MusicUtil.addMusic(bean);
                   if (code == 1){
                       ToastUtil.INSTANCE.suc(getMContext(),"已添加至播放列表");
                   }else if (code == 0){
                       ToastUtil.INSTANCE.suc(getMContext(),"已添加至播放列表");
                   }else{
                       ToastUtil.INSTANCE.suc(getMContext(),"请勿重复添加");
                   }
               }else{
                   addMusic(bean);
               }
            }

            @Override
            public void onItemClick(MusicBean bean) {
                if (onMusicStatusChange != null){
                    onMusicStatusChange.onItemSelected(bean);
                }
            }
        });
        if (musicType == TYPE_MUSIC_LOCAL){
            mSearch.setVisibility(View.GONE);
            uploadLL.setVisibility(View.GONE);
            getLoacaData();
        }else if(musicType == TYPE_MUSIC_UPLOAD){
            mSearch.setVisibility(View.GONE);
            uploadLL.setVisibility(View.VISIBLE);
            getData(TYPE_REFRESH);
        }else{
            mSearch.setVisibility(View.VISIBLE);
            uploadLL.setVisibility(View.GONE);
            mSearch.setHint("请输入歌名进行搜索");
            mSearch.setOnSearchListener(new SearchView.OnSearchListener() {
                @Override
                public void onSearch(String keyWord) {
                    searchHotMusic(keyWord);
                }
            });
            mSearch.setOnClearListener(new SearchView.OnClearListener() {
                @Override
                public void onClear() {
                    adapter.setData(oldList);
                }
            });
            getData(TYPE_REFRESH);
        }
    }

    OnMusicStatusChange onMusicStatusChange;


    public void setOnMusicStatusChange(OnMusicStatusChange onMusicStatusChange) {
        this.onMusicStatusChange = onMusicStatusChange;
    }

    public interface OnMusicStatusChange{
        void onItemSelected(MusicBean bean);
    }

    private void getLoacaData(){
        MusicUtil.scanMusic2(getMContext(), new MusicUtil.LocalScanMusicCallback() {
            @Override
            public void onFinish(ArrayList<MusicBean> musicList) {
                if(musicList.size() <= 0){
                    emptyView.setVisibility(View.VISIBLE);
                }else{
                    emptyView.setVisibility(View.GONE);
                    adapter.setData(musicList);
                }
            }
        });
    }

//    private void upLoadMusic(final MusicBean bean){
//        if (!TextUtils.isEmpty(DataHelper.INSTANCE.getLocalUser().getNickname())){
//            bean.setUploader(DataHelper.INSTANCE.getLocalUser().getNickname());
//        }else{
//            bean.setUploader("");
//        }
//        ToastUtil.INSTANCE.suc(getMContext(),"开始上传请耐心等待");
//        final String fileName = "" + DataHelper.INSTANCE.getUID() + System.currentTimeMillis()
//                + bean.getUrl().substring(bean.getUrl().length()-4,bean.getUrl().length());
//        final OSSPutFileUtil putFileUtil = new OSSPutFileUtil(fileName, bean.getUrl(), OSSPutFileUtil.FILE_TYPE_PHOTOLIST);
//        putFileUtil.uploadFileOriginal(getActivity(), new OSSPutFileUtil.OSSCallBack() {
//            @Override
//            public void onSuc() {
//                addMusic(bean);
//                ToastUtil.INSTANCE.suc(getMContext(),"上传成功");
//            }
//
//            @Override
//            public void onFail(String msg) {
//                ToastUtil.INSTANCE.suc(getMContext(),"上传失败 :"+msg);
//            }
//        });
//    }

    private void getData(final int type){
        if (type == TYPE_REFRESH){
            page = 1;
        }
        if (musicType == TYPE_MUSIC_HOT){
            NetService.Companion.getInstance(getMContext()).getHotMusicList(page, new Callback<List<MusicBean>>() {
                @Override
                public void onSuccess(int nextPage, List<MusicBean> list, int code) {
                    if (type == TYPE_REFRESH){
                        mRv.refreshComplete();
                        oldList.clear();
                        oldList.addAll(list);
                        adapter.setData(list);
                    }else{
                        mRv.loadMoreComplete();
                        oldList.addAll(list);
                        adapter.addData(list);
                    }
                    page ++;
                }

                @Override
                public void onError(@NotNull String msg, @NotNull Throwable throwable, int code) {
                    mRv.refreshComplete();
                }


                @Override
                public boolean isAlive() {
                    return isLive();
                }

                @Override
                public void noMore() {
                    super.noMore();
                    mRv.setNoMore(true);
                }
            });
        }else{
            NetService.Companion.getInstance(getMContext()).getUserUploadMusic( new Callback<List<MusicBean>>() {
                @Override
                public void onSuccess(int nextPage, List<MusicBean> list, int code) {
                    mRv.refreshComplete();
                    if (type == TYPE_REFRESH){
                        adapter.setData(list);
                    }else{
                        adapter.addData(list);
                    }
                }

                @Override
                public void onError(@NotNull String msg, @NotNull Throwable throwable, int code) {
                    mRv.refreshComplete();
                }

                @Override
                public boolean isAlive() {
                    return isLive();
                }


            });
        }
    }
    private void addMusic(MusicBean bean){
        String singer = "";
        String uploader = "";
        if (bean.getSinger() != null){
            singer = bean.getSinger();
        }
        if (bean.getUploader() != null){
            uploader = bean.getUploader();
        }
        NetService.Companion.getInstance(getMContext()).addMusic(bean.getUrl(), bean.getName()
                , bean.getSize(), singer, uploader, new Callback<BaseBean>() {
                    @Override
                    public void onSuccess(int nextPage, BaseBean bean, int code) {
                        ToastUtil.INSTANCE.suc(getMContext(),"已添加至播放列表");
                    }

                    @Override
                    public void onError(@NotNull String msg, @NotNull Throwable throwable, int code) {
                        ToastUtil.INSTANCE.suc(getMContext(),msg);
                    }

                    @Override
                    public boolean isAlive() {
                        return isLive();
                    }

                    @Override
                    public void noMore() {
                        super.noMore();
                    }
                });
    }

    private void searchHotMusic(String keyword){
        NetService.Companion.getInstance(getMContext()).searcheHotMusic(keyword, new Callback<HotMusicSearchBean>() {
            @Override
            public void onSuccess(int nextPage, HotMusicSearchBean bean, int code) {
                adapter.setData(bean.getList());
            }

            @Override
            public void onError(@NotNull String msg, @NotNull Throwable throwable, int code) {
                ToastUtil.INSTANCE.suc(getMContext(),msg);
            }

            @Override
            public boolean isAlive() {
                return isLive();
            }
        });
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
