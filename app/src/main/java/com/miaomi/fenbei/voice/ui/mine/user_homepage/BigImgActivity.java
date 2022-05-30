package com.miaomi.fenbei.voice.ui.mine.user_homepage;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hjq.permissions.OnPermission;
import com.hjq.permissions.XXPermissions;
import com.miaomi.fenbei.base.bean.BaseBean;
import com.miaomi.fenbei.base.bean.PreviewBean;
import com.miaomi.fenbei.base.core.BaseActivity;
import com.miaomi.fenbei.base.core.dialog.CommonDialog;
import com.miaomi.fenbei.base.net.Callback;
import com.miaomi.fenbei.base.net.NetService;
import com.miaomi.fenbei.base.util.DataHelper;
import com.miaomi.fenbei.base.util.ToastUtil;
import com.miaomi.fenbei.base.widget.FemaleMeetLayoutManager;
import com.miaomi.fenbei.base.widget.OnViewPagerListener;
import com.miaomi.fenbei.voice.R;
import com.miaomi.fenbei.voice.ui.mine.user_homepage.adapter.PreviewAdapter;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;

@Route(path = "/app/bigimg")
public class BigImgActivity extends BaseActivity {

//    private ViewPager vpImg;
//    private ImageView tvDelete;
//    private ImageView tvPreservation;
    private int index;
    private Bitmap bitmap;
    private String userId;
//    private LinearLayout rlBottom;
    private ArrayList<String> imgUrlList;
    private ArrayList<PreviewBean> beans;
    private CommonDialog commonDialog;
    private int seletedPostion;
    private int type;
    public final static int REQUSTCODE_DELETE = 2221;
    private RecyclerView photoRv;
    private PreviewAdapter previewAdapter;
    private TextView numTv;


    public static Intent getIntent(Context context, int index, String userId, ArrayList<String> imgUrlList) {
        Intent intent = new Intent(context, BigImgActivity.class);
        intent.putExtra("index", index);
        intent.putExtra("user_id", userId);
        intent.putStringArrayListExtra("imgUrl_list", imgUrlList);
        return intent;
    }

    public static Intent getPreviewBeanIntent(Context context, int index, String userId, ArrayList<PreviewBean> beans) {
        Intent intent = new Intent(context, BigImgActivity.class);
        intent.putExtra("type", 1);
        intent.putExtra("index", index);
        intent.putExtra("user_id", userId);
        intent.putParcelableArrayListExtra("bean_list", beans);
        return intent;
    }

    @Override
    public int getLayoutId() {
        return R.layout.user_activity_big_picture;
    }

    @Override
    public void initView() {
        setBaseStatusBar(false, false);
        previewAdapter = new PreviewAdapter(this);
        commonDialog = new CommonDialog(this);
        type = getIntent().getIntExtra("type", 0);
        userId = getIntent().getStringExtra("user_id");
        index = getIntent().getIntExtra("index", 0);
        seletedPostion = index;
        photoRv = findViewById(R.id.photo_rv);
        numTv = findViewById(R.id.tv_num);
        FemaleMeetLayoutManager layoutManager = new FemaleMeetLayoutManager(this, OrientationHelper.HORIZONTAL,false);
        photoRv.setLayoutManager(layoutManager);
        photoRv.setAdapter(previewAdapter);
        previewAdapter.setOnItemClickListener(new PreviewAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                finish();
            }

            @Override
            public void onAdd() {

            }
        });
        if (type == 1) {
            beans = getIntent().getParcelableArrayListExtra("bean_list");
            previewAdapter.setPreviewBeanData(beans);
        } else {
            imgUrlList = getIntent().getStringArrayListExtra("imgUrl_list");
            previewAdapter.setData(imgUrlList);
        }
        photoRv.scrollToPosition(index);
        layoutManager.setOnViewPagerListener(new OnViewPagerListener() {
            @Override
            public void onPageRelease(boolean isselected, View view) {

            }

            @Override
            public void onPageSelected(boolean isselected, View view) {

            }
        });
    }


//
//    @Override
//    public void onClick(View view) {
//        int i = view.getId();
//        if (i == R.id.tv_preservation) {
//            applicationAuthority();
//        } else if (i == R.id.tv_delete) {
//            if (type == 1) {
//                deleteImage(beans.get(seletedPostion).getUrl());
//            } else {
//                deleteImage(imgUrlList.get(seletedPostion));
//            }
//        } else if (i == R.id.iv_close) {
//            finish();
//        }
//    }

    private void deleteImage(final String url) {
         if (url.equals(DataHelper.INSTANCE.getUserInfo().getFace())) {
            ToastUtil.INSTANCE.suc(BigImgActivity.this, "不能删除头像");
            return;
         }

        NetService.Companion.getInstance(BigImgActivity.this).deleteImage(url, new Callback<BaseBean>() {
            @Override
            public void onSuccess(int nextPage, BaseBean bean, int code) {
                ToastUtil.INSTANCE.suc(BigImgActivity.this, "删除成功");
                Intent intent = new Intent();
                intent.putExtra("url", url);
                setResult(RESULT_OK, intent);
                finish();
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


    private void saveBmp2Gallery(Bitmap bmp, String picName) {
        if (bmp == null){
            return;
        }
        String fileName = null;
        //系统相册目录
        String galleryPath = Environment.getExternalStorageDirectory()
                + File.separator + Environment.DIRECTORY_DCIM
                + File.separator + "Camera" + File.separator;


        // 声明文件对象
        File file = null;
        // 声明输出流
        FileOutputStream outStream = null;

        try {
            // 如果有目标文件，直接获得文件对象，否则创建一个以filename为名称的文件
            file = new File(galleryPath, picName+System.currentTimeMillis() + ".jpg");

            // 获得文件相对路径
            fileName = file.toString();
            // 获得输出流，如果文件中有内容，追加内容
            outStream = new FileOutputStream(fileName);
            if (null != outStream) {
                bmp.compress(Bitmap.CompressFormat.PNG, 90, outStream);
            }

        } catch (Exception e) {
            e.getStackTrace();
        } finally {
            try {
                if (outStream != null) {
                    outStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //通知相册更新
        MediaStore.Images.Media.insertImage(getContentResolver(),
                bmp, fileName, null);
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri uri = Uri.fromFile(file);
        intent.setData(uri);
        sendBroadcast(intent);
        ToastUtil.INSTANCE.suc(this, "下载完成");
    }

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0x11){
                saveBmp2Gallery(bitmap, "homepage");
            }
        }
    };

    boolean isRunnning;

    private void returnBitMap(final String url) {
        if (isRunnning){
            return;
        }
        isRunnning = true;
        new Thread(new Runnable() {
            @Override
            public void run() {
                URL imageurl = null;

                try {
                    imageurl = new URL(url);
                    bitmap = BitmapFactory.decodeStream(imageurl.openStream());
//                    handler.sendEmptyMessage(0x11);
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            saveBmp2Gallery(bitmap, "homepage");
                        }
                    });
                    isRunnning = false;
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    private void applicationAuthority() {
        String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (XXPermissions.isHasPermission(this, perms)) {
            if (type == 1) {
                returnBitMap(beans.get(seletedPostion).getUrl());
            } else {
                returnBitMap(imgUrlList.get(seletedPostion));
            }
        } else {
            XXPermissions.with(this)
                    .permission(perms)
                    .request(new OnPermission() {

                        @Override
                        public void hasPermission(List<String> granted, boolean isAll) {
                            if (type == 1) {
                                returnBitMap(beans.get(seletedPostion).getUrl());
                            } else {
                                returnBitMap(imgUrlList.get(seletedPostion));
                            }
                        }

                        @Override
                        public void noPermission(List<String> denied, boolean quick) {

                        }
                    });
        }
    }
}



