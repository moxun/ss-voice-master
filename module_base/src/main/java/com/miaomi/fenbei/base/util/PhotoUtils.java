package com.miaomi.fenbei.base.util;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import androidx.fragment.app.Fragment;
import androidx.core.content.FileProvider;


import com.miaomi.fenbei.base.R;
import com.miaomi.fenbei.base.WXImgPickerPresenter;
import com.ypx.imagepicker.ImagePicker;
import com.ypx.imagepicker.bean.ImageItem;
import com.ypx.imagepicker.data.OnImagePickCompleteListener;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static android.os.Environment.DIRECTORY_PICTURES;

public class PhotoUtils {
    public static final int PERMISSION_PHOTO_NUM = 1000;
    public static final int PERMISSION_CAMERA_NUM = 1001;
    public static final int CAMERA_REQUEST = 200;
    public static final int GALLERY_REQUEST = 201;
    public static final int CROP_REQUEST = 202;
    public static final int USER_FACE_REQUEST = 203;
    public static final int USER_FACE_CROP_REQUEST = 204;
    public static final int USER_PHOTO_CROP_REQUEST = 205;

    public static final String PERMISSION_PHOTO = Manifest.permission.WRITE_EXTERNAL_STORAGE;
    public static final String PERMISSION_CAMERA = Manifest.permission.CAMERA;

    private static Uri imgUri;
    private static File imgFile;
    private String cropUrl;
//    private Context mContext;


    public String getCropUrl() {
        return cropUrl;
    }

    private static PhotoUtils instance;

    private PhotoUtils() {
        cropUrl = Environment.getExternalStorageDirectory().getAbsolutePath() +"/crop";
    }

    public static PhotoUtils getInstance() {
        if (instance == null) {
            instance = new PhotoUtils();
        }
        return instance;
    }

    public Uri getImgUri() {
        return imgUri;
    }

    public File getImgFile() {
        return imgFile;
    }

    public static void initImgUri(Context context) {
        if (imgUri == null) {
            imgFile = getImageFile(context);
            imgUri = FileProvider.getUriForFile(context
                    ,context.getString(R.string.common_authorities)
                    ,imgFile);
        }
    }

    private static File getImageFile(Context context) {
        File outputImage = new File(context.getExternalFilesDir(DIRECTORY_PICTURES),
                "crop.jpg");
        if (outputImage.getParentFile() != null && !outputImage.getParentFile().exists()) {
            outputImage.getParentFile().mkdir();
        }
        return outputImage;
    }

    /**
     * 拍照
     * @param context
     */
    public void tokePhoto(Activity context) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imgUri);
        context.startActivityForResult(intent, CAMERA_REQUEST);
    }

    /**
     * 拍照
     * @param context
     */
    public void tokePhoto(Fragment context) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imgUri);
        context.startActivityForResult(intent, CAMERA_REQUEST);
    }

    public interface OnSelectPhotoListener{
        void onSelected(String url);
    }

    public interface OnSelectPhotosListener{
        void onSelected(List<ImageItem> urls);
    }

    /**
     * 相册选择
     * @param context
     */
    public void chooseOriginGallary(Activity context, final OnSelectPhotoListener onSelectPhotoListener) {
        ImagePicker.withMulti(new WXImgPickerPresenter())
                .setMaxCount(1)//设置最大选择数量
                .setColumnCount(4)//设置显示列数
                .showVideo(false)//设置是否加载视频
                .showGif(true)//设置是否加载GIF
                .showCamera(true)//设置是否显示拍照按钮（在列表第一个）
                .showImage(true)//设置是否加载图片
                //调用多选
                .pick(context, new OnImagePickCompleteListener() {
                    @Override
                    public void onImagePickComplete(ArrayList<ImageItem> items) {
                        //处理回调回来的图片信息，主线程
                        String url = items.get(0).getPath();
                        onSelectPhotoListener.onSelected(url);
                    }
                });
    }

    public void chooseGallary(Activity context,boolean isCrop) {
//        chooseGallary(context,isCrop,GALLERY_REQUEST);
    }

    public void chooseGallary(Activity context, final OnSelectPhotoListener onSelectPhotoListener) {
        ImagePicker.withMulti(new WXImgPickerPresenter())
                .setCropRatio(1, 1)//设置剪裁比例   1：1
                .cropSaveFilePath(cropUrl)
                .cropRectMinMargin(100)//设置剪裁边框间距,单位 px
                .setMaxCount(1)//设置最大选择数量
                .setColumnCount(4)//设置显示列数
                .showVideo(false)//设置是否加载视频
                .showGif(true)//设置是否加载GIF
                .showCamera(true)//设置是否显示拍照按钮（在列表第一个）
                .showImage(true)//设置是否加载图片
                .setMaxVideoDuration(120000)//设置视频可选择的最大时长
                //设置只能选择视频或图片
                .setSinglePickImageOrVideoType(true)
                //设置只能选择一个视频
                .setVideoSinglePick(true)
                //设置下次选择需要屏蔽的图片或视频（简单点就是不可重复选择）
                .setShieldList(new ArrayList<String>())
                //调用多选
                .crop(context, new OnImagePickCompleteListener() {
                    @Override
                    public void onImagePickComplete(ArrayList<ImageItem> items) {
                        //处理回调回来的图片信息，主线程
                        String url = items.get(0).getPath();
                        onSelectPhotoListener.onSelected(url);
                    }
                });
    }

    public void chooseAvter(Activity context, final OnSelectPhotoListener onSelectPhotoListener) {
        ImagePicker.withMulti(new WXImgPickerPresenter())
                .setCropRatio(1, 1)//设置剪裁比例   1：1
                .cropSaveFilePath(cropUrl)
                .cropRectMinMargin(100)//设置剪裁边框间距,单位 px
                .setMaxCount(1)//设置最大选择数量
                .setColumnCount(4)//设置显示列数
                .showVideo(false)//设置是否加载视频
                .showGif(true)//设置是否加载GIF
                .showCamera(true)//设置是否显示拍照按钮（在列表第一个）
                .showImage(true)//设置是否加载图片
                .setMaxVideoDuration(120000)//设置视频可选择的最大时长
                //设置只能选择视频或图片
                .setSinglePickImageOrVideoType(true)
                //设置只能选择一个视频
                .setVideoSinglePick(true)
                //设置下次选择需要屏蔽的图片或视频（简单点就是不可重复选择）
                .setShieldList(new ArrayList<String>())
                //调用多选
                .crop(context, new OnImagePickCompleteListener() {
                    @Override
                    public void onImagePickComplete(ArrayList<ImageItem> items) {
                        //处理回调回来的图片信息，主线程
                        String url = items.get(0).getPath();
                        onSelectPhotoListener.onSelected(url);
                    }
                });
    }







    /**
     * 裁剪正方形的图片
     * @param context
     * @param uri
     * @param request
     */
    public void cropRawPhoto(Activity context, Uri uri, int request) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        if (request == CAMERA_REQUEST) {
            intent.setDataAndType(uri, "image/*");
        } else if (request == GALLERY_REQUEST) {
            intent.setDataAndType(getImageContentUri(context, getRealPathFromURI(context, uri)), "image/*");
        }
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("return-data", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imgFile));
        context.startActivityForResult(intent, CROP_REQUEST);
    }


    /**
     * 裁剪头像背景图片
     * @param context
     * @param uri
     * @param request
     */
    public void cropUserFace(Activity context, Uri uri, int request) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        if (request == CAMERA_REQUEST) {
            intent.setDataAndType(uri, "image/*");
        } else if (request == GALLERY_REQUEST) {
            intent.setDataAndType(getImageContentUri(context, getRealPathFromURI(context, uri)), "image/*");
        }
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("return-data", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imgFile));
        context.startActivityForResult(intent, USER_FACE_CROP_REQUEST);
    }

    /**
     * 裁剪房间背景图
     * @param context
     * @param uri
     * @param request
     */
    public void cropRoomBgPhoto(Activity context, Uri uri, int request) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        if (request == CAMERA_REQUEST) {
            intent.setDataAndType(uri, "image/*");
        } else if (request == GALLERY_REQUEST) {
            intent.setDataAndType(getImageContentUri(context, getRealPathFromURI(context, uri)), "image/*");
        }
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 9);
        intent.putExtra("aspectY", 16);
        intent.putExtra("return-data", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imgFile));
        context.startActivityForResult(intent, CROP_REQUEST);
    }

//    /**
//     * 裁剪正方形的图片
//     * @param context
//     * @param uri
//     * @param request
//     */
//    public void cropPhoto(Fragment context, Uri uri, int request) {
//        Intent intent = new Intent("com.android.camera.action.CROP");
//        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
//        if (request == CAMERA_REQUEST) {
//            intent.setDataAndType(uri, "image/*");
//        } else if (request == GALLERY_REQUEST) {
//            intent.setDataAndType(getImageContentUri(mContext, getRealPathFromURI(mContext, uri)), "image/*");
//        }
//        intent.putExtra("crop", "true");
//        intent.putExtra("return-data", false);
//        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imgFile));
//        context.startActivityForResult(intent, CROP_REQUEST);
//    }


    private String getRealPathFromURI(Context context, Uri contentURI) {
        String result;
        Cursor cursor = context.getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) {
            result = contentURI.toString();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }

    private Uri getImageContentUri(Context context, String path) {
        Cursor cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Images.Media._ID},
                MediaStore.Images.Media.DATA + "=? ",
                new String[]{path}, null);
        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(cursor.getColumnIndex(MediaStore.Images.Media._ID));
            Uri baseUri = Uri.parse("content://media/external/images/media");
            return Uri.withAppendedPath(baseUri, "" + id);
        } else {
            ContentValues contentValues = new ContentValues(1);
            contentValues.put(MediaStore.Images.Media.DATA, path);
            return context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
        }
    }
    public void choosePhoto(Activity context,ArrayList<ImageItem> seletedPhotos, final OnSelectPhotosListener onSelectPhotoListener) {
        ImagePicker.withMulti(new WXImgPickerPresenter())
                .setMaxCount(9 - seletedPhotos.size())//设置最大选择数量
                .setColumnCount(4)//设置显示列数
                .showVideo(true)//设置是否加载视频
                .showGif(true)//设置是否加载GIF
                .showCamera(true)//设置是否显示拍照按钮（在列表第一个）
                .showImage(true)//设置是否加载图片
                .setMaxVideoDuration(60000)//设置视频可选择的最大时长
                //设置只能选择一个视频
                .setVideoSinglePick(true)
                //设置只能选择视频或图片
                .setSinglePickImageOrVideoType(true)
                //设置下次选择需要屏蔽的图片或视频（简单点就是不可重复选择）
                .setShieldList(seletedPhotos)
                //调用多选
                .pick(context, new OnImagePickCompleteListener() {
                    @Override
                    public void onImagePickComplete(ArrayList<ImageItem> items) {
                        //处理回调回来的图片信息，主线程
                        onSelectPhotoListener.onSelected(items);
                    }
                });
    }
}
