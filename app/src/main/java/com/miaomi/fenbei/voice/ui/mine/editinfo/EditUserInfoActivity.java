package com.miaomi.fenbei.voice.ui.mine.editinfo;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.view.TimePickerView;
import com.miaomi.fenbei.base.bean.BaseBean;
import com.miaomi.fenbei.base.bean.MineBean;
import com.miaomi.fenbei.base.bean.PreviewBean;
import com.miaomi.fenbei.base.bean.UserHomePageInfoBean;
import com.miaomi.fenbei.base.config.BaseConfig;
import com.miaomi.fenbei.base.core.BaseActivity;
import com.miaomi.fenbei.base.core.CommonLib;
import com.miaomi.fenbei.base.core.dialog.CommonDialog;
import com.miaomi.fenbei.base.net.ApitCallback;
import com.miaomi.fenbei.base.net.Callback;
import com.miaomi.fenbei.base.net.NetService;

import com.miaomi.fenbei.base.util.CityUtils;
import com.miaomi.fenbei.base.util.DataHelper;
import com.miaomi.fenbei.base.util.DensityUtil;
import com.miaomi.fenbei.base.util.ImgUtil;
import com.miaomi.fenbei.base.util.OSSPutFileUtil;
import com.miaomi.fenbei.base.util.PhotoUtils;
import com.miaomi.fenbei.base.util.TimeUtil;
import com.miaomi.fenbei.base.util.ToastUtil;
import com.miaomi.fenbei.voice.R;
import com.miaomi.fenbei.voice.ui.mine.creatgod.CommonRecordDialog;
import com.miaomi.fenbei.voice.ui.mine.user_homepage.BigImgActivity;
import com.miaomi.fenbei.voice.ui.mine.user_homepage.adapter.PhotoAdapter;
import com.miaomi.fenbei.voice.widget.KMSoundView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class EditUserInfoActivity extends BaseActivity implements View.OnClickListener{

    public final static int REQUEST_CODE = 2001;
    private MineBean mineBean;
    private MineBean editedMineBean = new MineBean();
    private ImageView avtetIv;
    private EditText nicknameTv;
    private TextView birthTv;
    private TextView cityTv;
    private EditText autographTv;
    private TextView sexTv;
    private LinearLayout soundLL;
    private LinearLayout birthdayLL;
    private LinearLayout cityLL;
    private KMSoundView soundView;
    private TimePickerView pvCustomTime;
    private CommonDialog saveInfoDialog;
    private RecyclerView photoRv;
    private ImageView backIv;
    ArrayList<PreviewBean> imgList;
    ArrayList<String> photoImgList;
    private PhotoAdapter photoAdapter;
//    private LoadView loadView;
    private ImageView soundMoreiv;
    private TextView saveTv;
    private int recodeTime;


    public static void start(Activity context, String soundUrl, int soundTime, ArrayList<PreviewBean> imgList,int recodeTime) {
        Intent intent = new Intent(context, EditUserInfoActivity.class);
        intent.putParcelableArrayListExtra("img_list",imgList);
        intent.putExtra("sound_url",soundUrl);
        intent.putExtra("sound_time",soundTime);
        intent.putExtra("recodeTime",recodeTime);
        context.startActivityForResult(intent,REQUEST_CODE);
    }

    @Override
    public void initView() {
        setBaseStatusBar(false,false);
        photoImgList = new ArrayList<>();
        String soundUrl = getIntent().getStringExtra("sound_url");
        int soundTime = getIntent().getIntExtra("sound_time", 0);
        imgList = getIntent().getParcelableArrayListExtra("img_list");
        recodeTime = getIntent().getIntExtra("recodeTime",0);
        avtetIv = findViewById(R.id.iv_face);
        sexTv = findViewById(R.id.tv_sex);
        nicknameTv = findViewById(R.id.tv_nickname);
        birthTv = findViewById(R.id.tv_birthday);
        cityTv = findViewById(R.id.tv_city);
        autographTv = findViewById(R.id.tv_autograph);
        soundLL = findViewById(R.id.ll_sound);
        soundView = findViewById(R.id.sv_sound);
        soundView.showClose();
        birthdayLL = findViewById(R.id.ll_birthday);
        cityLL = findViewById(R.id.ll_city);
        photoRv = findViewById(R.id.rv_photo);
        backIv = findViewById(R.id.iv_back);
        soundMoreiv = findViewById(R.id.iv_sound_more);
        saveTv = findViewById(R.id.tv_save);
        saveTv.setOnClickListener(this);
        avtetIv.setOnClickListener(this);
        backIv.setOnClickListener(this);
        cityLL.setOnClickListener(this);
        birthdayLL.setOnClickListener(this);
        soundLL.setOnClickListener(this);
        photoAdapter = new PhotoAdapter(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        photoRv.setLayoutManager(linearLayoutManager);
        photoRv.setAdapter(photoAdapter);
        getUserInfo();
        if (TextUtils.isEmpty(soundUrl)){
            soundView.setVisibility(View.GONE);
            soundMoreiv.setVisibility(View.VISIBLE);
        }else {
            soundMoreiv.setVisibility(View.GONE);
            soundView.setVisibility(View.VISIBLE);
            soundView.setDuration(soundTime);
            soundView.setPath(soundUrl);
            soundView.setDeleteListner(v -> deleteVideo(0));
        }
//        for (PreviewBean item : imgList){
//            if (item.getType() != 2){
//                videoUrl = item.getUrl();
//            }
//        }
        updataImgGallary();
        photoAdapter.setOnItemClickListener(new PhotoAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                startActivityForResult(BigImgActivity.getIntent(EditUserInfoActivity.this,position,String.valueOf(DataHelper.INSTANCE.getUserInfo().getUser_id()), photoImgList)
                        ,BigImgActivity.REQUSTCODE_DELETE);
            }

            @Override
            public void onAdd() {
                PhotoUtils.getInstance().chooseGallary(EditUserInfoActivity.this, url -> updatePhotoList(url));
            }

            @Override
            public void onLongClick(String url) {
                CommonDialog mCommonDialog = new CommonDialog(EditUserInfoActivity.this);
                mCommonDialog.setTitle("????????????");
                mCommonDialog.setContent("??????????????????????????????");
                mCommonDialog.setLeftBt("??????", v1 -> mCommonDialog.dismiss());
                mCommonDialog.setRightBt("??????", v12 -> {
                    deleteImage(url);
                    mCommonDialog.dismiss();
                });
                mCommonDialog.show();
            }
        });
    }

    private void updataImgGallary(){
        NetService.Companion.getInstance(this).getPhotoList(new Callback<List<PreviewBean>>() {
            @Override
            public void onSuccess(int nextPage, List<PreviewBean> bean, int code) {
                photoImgList.clear();
                for (PreviewBean item : bean){
                    photoImgList.add(item.getUrl());
                }
                List<String> list = new ArrayList<>(photoImgList);
                if (list.size() < 4){
                    list.add("");
                }
                photoAdapter.setData(list);
            }

            @Override
            public void onError(@NotNull String msg, @NotNull Throwable throwable, int code) {
                ToastUtil.INSTANCE.suc(EditUserInfoActivity.this,msg);

            }

            @Override
            public boolean isAlive() {
                return isLive();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        soundView.stop();
    }


    private void showSoundDialog(int recordTime){
        if (CommonLib.INSTANCE.isInRoom()){
            ToastUtil.INSTANCE.suc(EditUserInfoActivity.this,"??????????????????????????????~");
            return;
        }
        final CommonRecordDialog dialog = new CommonRecordDialog(recordTime);
        dialog.setOnUploadRecordListener((url, duration) -> {
            uploadVideo(url,0,duration);
            soundView.setPath(url);
            soundView.setDuration(duration);
            dialog.dismiss();

        });
        dialog.show(getSupportFragmentManager());
    }

    private void bingData(){
        //?????????
        if (mineBean.getCheck_state() == 0){
            ImgUtil.INSTANCE.loadRoomIcon(EditUserInfoActivity.this, mineBean.getFace(), avtetIv,8, R.drawable.common_default);
        }else{
            ImgUtil.INSTANCE.loadRoomIcon(EditUserInfoActivity.this, mineBean.getCheck_face(), avtetIv,8, R.drawable.common_default);
        }
        nicknameTv.setText(mineBean.getNickname());
        nicknameTv.setSelection(nicknameTv.getText().length());
        if (!TextUtils.isEmpty(mineBean.getBirth())){
            birthTv.setText(mineBean.getBirth());
        }
        if (!TextUtils.isEmpty(mineBean.getCity())){
            cityTv.setText(mineBean.getCity());
        }
        autographTv.setText(mineBean.getSignature());
        autographTv.setSelection(autographTv.getText().length());
        if (mineBean.getGender() == BaseConfig.USER_INFO_GENDER_MAN){
            sexTv.setText("???");
        }else{
            sexTv.setText("???");
        }
    }
    public boolean isChange(){
        if (mineBean == null){
            return false;
        }
        editedMineBean.setNickname(nicknameTv.getText().toString());
        editedMineBean.setSignature(autographTv.getText().toString());
        if (!editedMineBean.getCity().equals(mineBean.getCity())){
            return true;
        }
        if (!editedMineBean.getBirth().equals(mineBean.getBirth())){
            return true;
        }
        if (!editedMineBean.getNickname().equals(mineBean.getNickname())){
            return true;
        }
        if (!editedMineBean.getSignature().equals(mineBean.getSignature())){
            return true;
        }
        if (!editedMineBean.getFace().equals(mineBean.getFace())){
            return true;
        }
        if (editedMineBean.getGender() != mineBean.getGender()){
            return true;
        }
        return false;
    }

    private void getUserInfo(){
        NetService.Companion.getInstance(this).personalHomepage(String.valueOf(DataHelper.INSTANCE.getUID()), new Callback<UserHomePageInfoBean>() {
            @Override
            public void onSuccess(int nextPage, UserHomePageInfoBean bean, int code) {
//                loadHelper.bindView(Data.CODE_SUC);
                editedMineBean.setSignature(bean.getSignature());
                editedMineBean.setCity(bean.getCity());
                editedMineBean.setNickname(bean.getNickname());
                editedMineBean.setBirth(bean.getBirth());
                editedMineBean.setFace(bean.getFace());
                editedMineBean.setGender(bean.getGender());
                mineBean = new MineBean();
                mineBean.setSignature(bean.getSignature());
                mineBean.setCity(bean.getCity());
                mineBean.setNickname(bean.getNickname());
                mineBean.setBirth(bean.getBirth());
                mineBean.setFace(bean.getFace());
                mineBean.setGender(bean.getGender());
                bingData();
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


    private void editInfo(){
        if (editedMineBean == null){
            return;
        }
        String nickname = nicknameTv.getText().toString();
        String birth = birthTv.getText().toString();
        String city = cityTv.getText().toString();
        String signature = autographTv.getText().toString();
        if (!TextUtils.isEmpty(nickname)){
            editedMineBean.setNickname(nickname);
        }else {
            ToastUtil.INSTANCE.error(EditUserInfoActivity.this,"??????????????????");
            return;
        }
        if (!TextUtils.isEmpty(birth)){
            editedMineBean.setBirth(birth);
        }
        if (!TextUtils.isEmpty(city) && !"?????????".equals(city)){
            editedMineBean.setCity(city);
        }
        if (!TextUtils.isEmpty(signature)) {
            editedMineBean.setSignature(signature);
        }
        NetService.Companion.getInstance(this).editInfo(editedMineBean, new Callback<BaseBean>() {
            @Override
            public void onSuccess(int nextPage, BaseBean bean, int code) {
                editedMineBean.setCheck_state(1);
                DataHelper.INSTANCE.updatalUserInfo(mineBean);
                ToastUtil.INSTANCE.suc(EditUserInfoActivity.this,"????????????");
                setResult(RESULT_OK);
                finish();
            }

            @Override
            public void onError(@NotNull String msg, @NotNull Throwable throwable, int code) {
                ToastUtil.INSTANCE.error(EditUserInfoActivity.this,msg);
            }

            @Override
            public boolean isAlive() {
                return isLive();
            }
        });
    }
    @Override
    public int getLayoutId() {
        return R.layout.activity_edit_user_info;
    }

    private void showCityChoose(){
//        String[] perms = { Manifest.permission.ACCESS_FINE_LOCATION , Manifest.permission.ACCESS_COARSE_LOCATION };
//        if (XXPermissions.isHasPermission(this, perms)) {
//            BDLocationUtils.startLocation(EditUserInfoActivity.this, new BDLocationUtils.OnLoacationListener() {
//                @Override
//                public void onLocation(String city) {
//                    cityTv.setText(city);
//                    editedMineBean.setCity(city);
//                }
//            });
//        } else {
//            XXPermissions.with(this)
//                    .permission(perms)
//                    .request(new OnPermission() {
//
//                        @Override
//                        public void hasPermission(List<String> granted, boolean isAll) {
//
//                        }
//
//                        @Override
//                        public void noPermission(List<String> denied, boolean quick) {
//
//                        }
//                    });
//        }

//        List<HotCity> hotCities = new ArrayList<>();
//        hotCities.add(new HotCity("??????", "??????", "101010100")); //code???????????????
//        hotCities.add(new HotCity("??????", "??????", "101020100"));
//        hotCities.add(new HotCity("??????", "??????", "101280101"));
//        hotCities.add(new HotCity("??????", "??????", "101280601"));
//        hotCities.add(new HotCity("??????", "??????", "101210101"));
//
//        CityPicker.from(this) //activity??????fragment
//                .enableAnimation(true)	//??????????????????????????????
////                .setAnimationStyle(anim)	//???????????????
////                .setLocatedCity(null)  //APP??????????????????????????????null???????????????????????????
//                .setLocatedCity(new LocatedCity("??????", "??????", "101210101")) //APP??????????????????????????????null???????????????????????????
//                .setHotCities(hotCities)	//??????????????????
//                .setOnPickListener(new OnPickListener() {
//                    @Override
//                    public void onPick(int position, City data) {
//                        cityTv.setText(data.getName());
//                        editedMineBean.setCity(data.getName());
//                    }
//
//                    @Override
//                    public void onCancel(){
//                    }
//
//                    @Override
//                    public void onLocate() {
//                        //?????????????????????APP???????????????????????????????????????
////                        new Handler().postDelayed(new Runnable() {
////                            @Override
////                            public void run() {
////                                //??????????????????????????????
////                                CityPicker.getInstance()
////                                        .locateComplete(new LocatedCity("??????", "??????", "101280601"), LocateState.SUCCESS);
////                            }
////                        }, 3000);
//                    }
//                })
//                .show();

        CityUtils.parseData(this,cityTv); //??????????????????ID
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.tv_save){
            editInfo();
        }
        if (id == R.id.ll_sound){
            showSoundDialog(recodeTime);
        }
        if (id == R.id.ll_birthday){
            DensityUtil.INSTANCE.hideSoftKeyboard(this);
            showTimeView();
        }
        if (id == R.id.ll_city){
            showCityChoose();
        }
        if (id == R.id.iv_face){
            if (DataHelper.INSTANCE.getUserInfo().getCheck_state() == 0){
                PhotoUtils.getInstance().chooseAvter(EditUserInfoActivity.this, this::updataAvter);
            }else{
                ToastUtil.INSTANCE.suc(this,"???????????????");
            }
        }
        if (id == R.id.iv_back){
            if (isChange()){
                if (saveInfoDialog == null){
                    saveInfoDialog = new CommonDialog(this)
                            .setContent("???????????????????????????")
                            .setTitle("????????????")
                            .setLeftBt("?????????", v1 -> {
                                saveInfoDialog.dismiss();
                                finish();
                            })
                            .setRightBt("??????", v12 -> {
                                saveInfoDialog.dismiss();
                                editInfo();
                            });
                }
                saveInfoDialog .show();
            }else{
                setResult(RESULT_OK);
                finish();
            }
        }
    }

    private void updataAvter(String imagePath){
        final String fileName = ""+DataHelper.INSTANCE.getUID()+System.currentTimeMillis()+".jpg";
        final OSSPutFileUtil putFileUtil = new OSSPutFileUtil(fileName, imagePath,OSSPutFileUtil.FILE_TYPE_AVATAR);
        putFileUtil.uploadAvterByBitmap(this, new OSSPutFileUtil.OSSCallBack() {
            @Override
            public void onSuc() {
                editedMineBean.setFace(putFileUtil.getUrl());
                isChange();
                ImgUtil.INSTANCE.loadRoomIcon(EditUserInfoActivity.this, editedMineBean.getFace(), avtetIv,8, R.drawable.common_default);
            }
            @Override
            public void onFail(String msg) {
            }
        });

    }

    private void showTimeView(){

        Calendar selectedDate = Calendar.getInstance();//??????????????????
        Calendar startDate = Calendar.getInstance();
        Calendar endData = Calendar.getInstance();
        int startYear = selectedDate.get(Calendar.YEAR) - 18;
        startDate.set(1918, 1, 23);
        endData.set(startYear,selectedDate.get(Calendar.MONTH),selectedDate.get(Calendar.MONDAY));
        pvCustomTime = new TimePickerBuilder(this, (date, v) -> {//??????????????????
            birthTv.setText(TimeUtil.getTime(date.getTime()));
            editedMineBean.setBirth(birthTv.getText().toString());
            isChange();
        }).setDate(endData)
                .setRangDate(startDate, endData)
                .setCancelColor(0xFFFD7F8F)
                .setSubmitColor(0xFFFD7F8F)
                .setContentTextSize(20)
                .setType(new boolean[]{true, true, true, false, false, false})
                .setLabel("???", "???", "???", "???", "???", "???")
                .setLineSpacingMultiplier(1.2f)
                .setTextXOffset(0, 0, 0, 40, 0, -40)
                .isCenterLabel(false) //?????????????????????????????????label?????????false?????????item???????????????label???
                .setDividerColor(0xFFFD7F8F)
                .build();
        pvCustomTime.show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            if (requestCode == BigImgActivity.REQUSTCODE_DELETE){
                updataImgGallary();
            }

        }
    }


    private void updatePhotoList(String imagePath) {
        final String fileName = "" + DataHelper.INSTANCE.getUID() + System.currentTimeMillis() + ".jpg";
        final OSSPutFileUtil putFileUtil = new OSSPutFileUtil(fileName, imagePath, OSSPutFileUtil.FILE_TYPE_PHOTOLIST);
        putFileUtil.uploadFileOriginal(this, new OSSPutFileUtil.OSSCallBack() {
            @Override
            public void onSuc() {
                uploadImage(putFileUtil.getUrl(), 2);
            }

            @Override
            public void onFail(String msg) {
            }
        });

    }


    private void uploadImage(final String imagePath, int type) {
        NetService.Companion.getInstance(EditUserInfoActivity.this).uploadImage(DataHelper.INSTANCE.getLoginToken(), type, imagePath, new Callback<BaseBean>() {
            @Override
            public void onSuccess(int nextPage, BaseBean bean, int code) {
                updataImgGallary();
                ToastUtil.INSTANCE.suc(EditUserInfoActivity.this,"?????????????????????????????????????????????");
            }

            @Override
            public void onError(@NotNull String msg, @NotNull Throwable throwable, int code) {
                ToastUtil.INSTANCE.suc(EditUserInfoActivity.this,msg);
            }

            @Override
            public boolean isAlive() {
                return isLive();
            }
        });
    }

    private void uploadVideo(String path, int type,int duration) {
        soundMoreiv.setVisibility(View.GONE);
        soundView.setVisibility(View.VISIBLE);
        soundView.setPath(path);
        soundView.setDuration(duration);
        NetService.Companion.getInstance(EditUserInfoActivity.this).uploaVideo(type, path,duration, new ApitCallback<BaseBean>() {
            @Override
            public void onSuccess(int nextPage, BaseBean bean, int code) {
                ToastUtil.INSTANCE.suc(EditUserInfoActivity.this,"????????????");
            }

            @Override
            public void onError(@NotNull String msg, @NotNull Throwable throwable, int code) {
                ToastUtil.INSTANCE.suc(EditUserInfoActivity.this,msg);
            }

            @Override
            public boolean isAlive() {
                return isLive();
            }
        });
    }

    private void deleteVideo(final int type) {
        NetService.Companion.getInstance(EditUserInfoActivity.this).deleteVideo(type, new ApitCallback<BaseBean>() {
            @Override
            public void onSuccess(int nextPage, BaseBean bean, int code) {
                ToastUtil.INSTANCE.suc(EditUserInfoActivity.this,"????????????");
                soundView.setVisibility(View.GONE);
                soundMoreiv.setVisibility(View.VISIBLE);
            }

            @Override
            public void onError(@NotNull String msg, @NotNull Throwable throwable, int code) {
                ToastUtil.INSTANCE.suc(EditUserInfoActivity.this,msg);

            }

            @Override
            public boolean isAlive() {
                return isLive();
            }
        });
    }

    private void deleteImage(final String url) {
        if (url.equals(DataHelper.INSTANCE.getUserInfo().getFace())) {
            ToastUtil.INSTANCE.suc(EditUserInfoActivity.this, "??????????????????");
            return;
        }

        NetService.Companion.getInstance(EditUserInfoActivity.this).deleteImage(url, new Callback<BaseBean>() {
            @Override
            public void onSuccess(int nextPage, BaseBean bean, int code) {
                ToastUtil.INSTANCE.suc(EditUserInfoActivity.this, "????????????");
                updataImgGallary();
//                Intent intent = new Intent();
//                intent.putExtra("url", url);
//                setResult(RESULT_OK, intent);
//                finish();

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



}
