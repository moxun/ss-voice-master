package com.miaomi.fenbei.base.util;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.common.auth.OSSCustomSignerCredentialProvider;
import com.alibaba.sdk.android.oss.internal.OSSAsyncTask;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.google.gson.Gson;
import com.miaomi.fenbei.base.BuildConfig;
import com.miaomi.fenbei.base.bean.OSSSignBean;

import java.io.ByteArrayOutputStream;
import java.net.URLEncoder;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OSSPutFileUtil {
    private String mLocalPath;
    private String mBucket;
    private String mObject;


    public final static int FILE_TYPE_AVATAR = 1;
    public final static int FILE_TYPE_IMG = 2;
    public final static int FILE_TYPE_PHOTOLIST = 3;
    public final static int FILE_TYPE_FACE_IMG = 4;
    public final static int FILE_TYPE_FACE_ID_CARD = 5;
    public final static int FILE_TYPE_RECARD = 6;
    public final static int FILE_TYPE_GREAT_GOD = 7;
    public final static int FILE_TYPE_USER_VIDEO = 8;

    public interface OSSCallBack {
        void onSuc();

        void onFail(String msg);
    }

    public OSSPutFileUtil(String fileName, String localPath, int type) {
        mBucket = "kimis";

        String remotePath;
        switch (type) {
            case FILE_TYPE_AVATAR:
                remotePath = "img/avatar/" + fileName;
                break;
            case FILE_TYPE_IMG:
                remotePath = "img/" + fileName;
                break;
            case FILE_TYPE_PHOTOLIST:
                remotePath = "img/homepage/photolist/" + fileName;
                break;
            case FILE_TYPE_FACE_IMG:
                remotePath = "img/homepage/face_img/" + fileName;
                break;
            case FILE_TYPE_FACE_ID_CARD:
                remotePath = "img/idcard/" + fileName;
                break;
            case FILE_TYPE_RECARD:
                remotePath = "img/record/" + fileName;
                break;
            case FILE_TYPE_GREAT_GOD:
                remotePath = "img/greatgod/" + fileName;
                break;
            case FILE_TYPE_USER_VIDEO:
                remotePath = "img/greatgod/" + fileName;
                break;
            default:
                remotePath = "img/" + fileName;
                break;
        }
        mObject = (BuildConfig.DEBUG ?  "im_test":"im" ) + "/" + remotePath;
        mLocalPath = localPath;
    }

    public String getUrl() {
        return "http://" +DataHelper.INSTANCE.getIMDevelop().getUpload() +  "/" + mObject;
    }

    public void uploadFileOriginal(final Activity ctx, final OSSCallBack callBack) {

        OSSCustomSignerCredentialProvider provider = new OSSCustomSignerCredentialProvider() {
            @Override
            public String signContent(String content) {
                OkHttpClient okHttpClient = new OkHttpClient();
                String url = null;
                try {
                    url = DataHelper.INSTANCE.getIMDevelop().getNew_main() + "/apis/" + "info/get_app_sign?content=" + URLEncoder.encode(content, "UTF-8");
                    Request request = new Request.Builder()
                            .url(url)
                            .build();
                    Call call = okHttpClient.newCall(request);
                    Response response = null;
                    response = call.execute();
                    String string = response.body().string();
                    OSSSignBean token = new Gson().fromJson(string, OSSSignBean.class);
                    return token.getData();
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }
        };
        OSSClient tClient = new OSSClient(ctx, DataHelper.INSTANCE.getIMDevelop().getUpload(), provider);

        final PutObjectRequest put = new PutObjectRequest(mBucket, mObject, mLocalPath);

        tClient.asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
            @Override
            public void onSuccess(PutObjectRequest request, PutObjectResult result) {

                ctx.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        callBack.onSuc();
                    }
                });
            }

            @Override
            public void onFailure(PutObjectRequest request, ClientException clientException, ServiceException serviceException) {
                if (clientException != null) {
                    clientException.printStackTrace();
                }
                if (serviceException != null) {

//                    Log.e("ErrorCode", serviceException.getErrorCode());
//                    Log.e("RequestId", serviceException.getRequestId());
//                    Log.e("HostId", serviceException.getHostId());
//                    Log.e("RawMessage", serviceException.getRawMessage());
                }
                ctx.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        callBack.onFail("上传失败");
                    }
                });
            }
        });
    }


    public void uploadAvterByBitmap(final Activity ctx, final OSSCallBack callBack) {
        uploadFile(ctx,callBack);
    }
    public void uploadRoomIconByBitmap(final Activity ctx, final OSSCallBack callBack) {
        uploadFile(ctx,callBack);
    }

    private void uploadFile(final Activity ctx, final OSSCallBack callBack){
        OSSCustomSignerCredentialProvider provider = new OSSCustomSignerCredentialProvider() {
            @Override
            public String signContent(String content) {
                OkHttpClient okHttpClient = new OkHttpClient();
                String url = null;
                try {
                    url = DataHelper.INSTANCE.getIMDevelop().getNew_main() + "/apis/" + "info/get_app_sign?content=" + URLEncoder.encode(content, "UTF-8");

                    Request request = new Request.Builder()
                            .url(url)
                            .build();
                    Call call = okHttpClient.newCall(request);
                    Response response = null;
                    response = call.execute();
                    String string = response.body().string();
                    OSSSignBean token = new Gson().fromJson(string, OSSSignBean.class);
                    return token.getData();
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }
        };
        OSSClient tClient = new OSSClient(ctx, DataHelper.INSTANCE.getIMDevelop().getUpload(), provider);

        final PutObjectRequest put = new PutObjectRequest(mBucket, mObject
                , bitmap2Bytes(createBitmap(mLocalPath)));

        OSSAsyncTask task = tClient.asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
            @Override
            public void onSuccess(PutObjectRequest request, PutObjectResult result) {

                ctx.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        callBack.onSuc();
                    }
                });
            }

            @Override
            public void onFailure(PutObjectRequest request, ClientException clientException, ServiceException serviceException) {
                if (clientException != null) {
                    clientException.printStackTrace();
                }
                if (serviceException != null) {
//                    Log.e("ErrorCode", serviceException.getErrorCode());
//                    Log.e("RequestId", serviceException.getRequestId());
//                    Log.e("HostId", serviceException.getHostId());
//                    Log.e("RawMessage", serviceException.getRawMessage());
                }
                ctx.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        callBack.onFail("上传失败");
                    }
                });
            }
        });
    }



    public static byte[]  bitmap2Bytes(Bitmap image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 90;
        while (baos.toByteArray().length / 1024 > 80 && options >10) {
            baos.reset(); // 重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
            options -= 10;
        }
        return baos.toByteArray();
    }



    public Bitmap createBitmap(String path) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        Bitmap bitmap = BitmapFactory.decodeFile(path, newOpts);// 此时返回bm为空
        return bitmap;// 压缩好比例大小后再进行质量压缩
    }

}
