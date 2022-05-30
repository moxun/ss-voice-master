package com.miaomi.fenbei.base

import android.app.Activity
import android.content.Intent
import android.graphics.ImageFormat
import android.hardware.Camera
import android.hardware.Camera.CameraInfo
import android.media.MediaRecorder
import android.os.CountDownTimer
import android.view.OrientationEventListener
import android.view.SurfaceHolder
import android.view.View
import android.widget.ImageView
import com.miaomi.fenbei.base.R
import com.miaomi.fenbei.base.core.BaseActivity
import com.miaomi.fenbei.base.util.ImgUtil
import com.miaomi.fenbei.base.util.TimeUtil
import com.miaomi.fenbei.base.util.ToastUtil
import com.shuyu.gsyvideoplayer.GSYVideoManager
import io.agora.rtc.utils.AgoraUtils.getDisplayRotation
import kotlinx.android.synthetic.main.activity_video_record.*
import java.io.File
import java.io.IOException


class VideoRecordActivity : BaseActivity(), View.OnClickListener {
    private var width = 0
    private var height = 0

    private val CAMERA_POST_POSITION = 0
    private val CAMERA_FRONT_POSITION = 1
    private var mHolder: SurfaceHolder? = null
    private var mStartedFlag = false //录像中标志
//    private var timer = 0f //计时器
//    private val maxSec = 60 //视频总时长
    private  var mCamera: Camera?=null
    private lateinit var mRecorder: MediaRecorder
    private var cameraReleaseEnable = true  //回收摄像头
    private var recorderReleaseEnable = false  //回收recorder
    private lateinit var path: String //最终视频路径
    private var isPreviewVideo=false
    private lateinit var mCountDownTimer: CountDownTimer
    private var recordTime = 30
    private var currentTime = 0
    private var currentPosition = CAMERA_POST_POSITION
    private var rotationRecord = 90
    private var rotationFlag = 0
    private lateinit var orientationEventListener:OrientationEventListener


    companion object{
        private val REQUEST_CODE = 6001
        fun start(context: Activity) {
            val intent = Intent(context, VideoRecordActivity::class.java)
            context.startActivityForResult(intent,6001)
        }
    }

    //用于记录视频录制时长
    //待上传相册列表
//    var handler = Handler()
//    var runnable = object : Runnable {
//        override fun run() {
//            timer++
//            Log.i("lzq","timer : "+timer)
//            time.text = TimeUtil.getMinuteAndSecendTime(timer.toInt()/10)
//            circleProgressBar.progress = (timer/maxSec).toInt()
//            if (timer < maxSec) {
//                handler.postDelayed(this, 10)
//            } else {
//                //停止录制 保存录制的流、显示供操作的ui
//                stopRecord()
//            }
//        }
//
//    }


    override fun getLayoutId(): Int {
       return  R.layout.activity_video_record
    }

    override fun initView() {
        setBaseStatusBar(false, false)
        orientationEventListener = object : OrientationEventListener(this) {
            override fun onOrientationChanged(rotation: Int) {
                //录制过程中
                if (mStartedFlag){
                    return
                }
                if (((rotation >= 0) && (rotation <= 30)) || (rotation >= 330)) {
                    // 竖屏拍摄
                    if (rotationFlag != 0) {
                        //这是竖屏视频需要的角度
                        rotationRecord = 90
                        //这是记录当前角度的flag
                        rotationFlag = 0
                    }
                } else if (((rotation >= 230) && (rotation <= 310))) {
                    // 横屏拍摄
                    if (rotationFlag != 90) {
                        //这是正横屏视频需要的角度
                        rotationRecord = 0
                        //这是记录当前角度的flag
                        rotationFlag = 90
                    }
                } else if (rotation > 30 && rotation < 95) {
                    // 反横屏拍摄
                    if (rotationFlag != 270) {
                        //这是反横屏视频需要的角度
                        rotationRecord = 180
                        //这是记录当前角度的flag
                        rotationFlag = 270
                    }
                }
            }
        }
        if (orientationEventListener.canDetectOrientation()) {
            orientationEventListener.enable()
        } else {
            orientationEventListener.disable()//注销
        }
        circleProgressBar.setProgressTotal(recordTime)
        mCountDownTimer = object : CountDownTimer((recordTime * 1000).toLong(), 1000) {
            override fun onTick(millisUntilFinished: Long) {
                currentTime = (millisUntilFinished/1000).toInt()
                time.text = TimeUtil.getRecordTime((recordTime - currentTime).toLong()* 1000)
                circleProgressBar.progress = recordTime - currentTime
            }

            override fun onFinish() {
                stopRecord()
            }
        }
        mBtnRecord.setOnClickListener(this)
        mBtnRecording.setOnClickListener(this)
        mBtnPlay.setOnClickListener(this)
        iv_back.setOnClickListener(this)
//        again_iv.setOnClickListener(this)
        again_tv.setOnClickListener(this)
        audit.setOnClickListener(this)
        video_player.setOnClickListener(this)
        check_camera_status.setOnClickListener(this)

//        String source1 = "http://9890.vod.myqcloud.com/9890_4e292f9a3dd011e6b4078980237cc3d3.f20.mp4";
        //增加title
        video_player.getTitleTextView().setVisibility(View.GONE)
        //设置返回键
        video_player.getBackButton().setVisibility(View.GONE)
        //是否可以滑动调整
        video_player.setIsTouchWiget(false)
        var holder = mSurfaceview.holder
        holder.addCallback(object : SurfaceHolder.Callback {
            override fun surfaceChanged(holder: SurfaceHolder?, format: Int, width: Int, height: Int) {
                if(!isPreviewVideo){
                    mHolder = holder
                    if (mCamera != null) {
                        mCamera?.stopPreview()
                        setStartPreview(holder)
                    }
                }
            }

            override fun surfaceDestroyed(holder: SurfaceHolder?) {
                if(!isPreviewVideo){
                    stopRecord()
                }
            }

            override fun surfaceCreated(holder: SurfaceHolder?) {
                if(!isPreviewVideo){
                    mHolder = holder
                    setStartPreview(holder)
                }
            }
        })
    }


    //获取Camera
    private fun getCamera(position: Int): Camera? {
        var camera: Camera?
        try {
            camera = Camera.open(position)
        } catch (e: java.lang.Exception) {
            camera = null
            e.printStackTrace()
        }
        return camera
    }


    override fun onDestroy() {
        super.onDestroy()
        releaseCamera()
        mCountDownTimer.cancel()
        GSYVideoManager.releaseAllVideos()
        orientationEventListener.disable()
    }

    //启动相机浏览
    private fun setStartPreview(
        holder: SurfaceHolder?
    ) {
        try {
            mCamera = getCamera(currentPosition)
            val parameters = mCamera?.parameters
            parameters?.pictureFormat = ImageFormat.JPEG
            //后置摄像头自动聚焦
//            if (currentPosition == CAMERA_POST_POSITION){
//                parameters?.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO)
//            }
            //自动对焦
            val focusMode = parameters!!.supportedFocusModes
            if (focusMode.contains(Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO)) {
                parameters.focusMode = Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO
                mCamera!!.cancelAutoFocus()
            }
            val sizeList =
                parameters?.supportedPreviewSizes //获取所有支持的camera尺寸
            val optimalPreviewSize =
                getCloselyPreSize(mSurfaceview.getWidth(), mSurfaceview.getHeight(),sizeList)
            width=optimalPreviewSize!!.width
            height=optimalPreviewSize!!.height
//            width=optimalPreviewSize!!.height
//            height=optimalPreviewSize!!.width
            parameters?.setPreviewSize(width, height) //把camera.size赋值到parameters
            parameters?.setPictureSize(width, height)
            mCamera?.parameters = parameters
            mCamera?.setPreviewDisplay(holder)
            mCamera?.setDisplayOrientation(90)
            mCamera?.startPreview()
            if(record_finish.visibility==View.VISIBLE){
                record_hint1.visibility=View.VISIBLE
                record_hint2.visibility=View.VISIBLE
                circleProgressBar.visibility=View.VISIBLE
                record_finish.visibility=View.GONE
                mBtnRecording.visibility=View.GONE
                mBtnPlay.visibility=View.GONE
                mBtnRecord.visibility=View.VISIBLE
                time.text = ""
                cover.visibility=View.GONE
                record_hint1.setText(R.string.record_hint1)
                circleProgressBar.progress = 0
            }

        } catch (e: IOException) {
            e.printStackTrace()
        }
    }


    //开始录制
    private fun startRecord() {
        if (!mStartedFlag) {
            currentTime = 0
            mCountDownTimer.start()
            mStartedFlag = true
            mBtnPlay.visibility = View.GONE
            //开始计时
//            handler.postDelayed(runnable, 1000)
            recorderReleaseEnable = true
            mRecorder = MediaRecorder()
            mRecorder.reset()
            mCamera?.unlock()
            mRecorder = MediaRecorder().apply {
                setCamera(mCamera)
                // 设置音频源与视频源 这两项需要放在setOutputFormat之前
                setAudioSource(MediaRecorder.AudioSource.CAMCORDER)
                setVideoSource(MediaRecorder.VideoSource.CAMERA)
                //设置输出格式
                setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
                //这两项需要放在setOutputFormat之后 IOS必须使用ACC
                setAudioEncoder(MediaRecorder.AudioEncoder.AAC)  //音频编码格式
                //使用MPEG_4_SP格式在华为P20 pro上停止录制时会出现
                setVideoEncoder(MediaRecorder.VideoEncoder.H264)  //视频编码格式
                //设置最终出片分辨率
                setVideoSize(width, height)
                setVideoFrameRate(30)
                setVideoEncodingBitRate(3 * 1024 * 1024)
                val frontRotation: Int
                frontRotation = if (rotationRecord == 180) {
                    //反向的前置
                    180
                } else {
                    //正向的前置
                    if (rotationRecord == 0) 270 - frontOri else frontOri //录制下来的视屏选择角度，此处为前置
                }
                setOrientationHint(if (currentPosition == CAMERA_FRONT_POSITION) frontRotation else rotationRecord)
//                if (currentPosition == CAMERA_FRONT_POSITION){
//                    setOrientationHint(270)
//                }else{
//                    setOrientationHint(90)
//                }
                //设置记录会话的最大持续时间（毫秒）
                setMaxDuration(30 * 1000)
                setPreviewDisplay(mHolder?.getSurface())
            }
            path =  cacheDir.path + File.separator + "VideoRecorder"
            if (path != null) {
                var dir = File(path)
                if (!dir.exists()) {
                    dir.mkdir()
                }
                path = dir.absolutePath + "/" +System.currentTimeMillis() + ".mp4"
                mRecorder.apply {
                    setOutputFile(path)
                    prepare()
                    start()
                }
            }
            time.visibility=View.VISIBLE
            record_hint1.setText(R.string.record_hint3)
            mBtnRecording.visibility=View.VISIBLE
            mBtnRecord.visibility=View.GONE
        }
    }

    //播放录像
    private fun playRecord() {
        if (cameraReleaseEnable) {
            mCamera?.apply {
                lock()
                stopPreview()
                release()
            }
            cameraReleaseEnable = false
        }
        mBtnPlay.visibility = View.INVISIBLE
//        val intent = Intent(Intent.ACTION_VIEW)
//        val file = File(path)
//        val uri = Uri.fromFile(file)
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
//            val contentUri: Uri =
//                FileProvider.getUriForFile(this, BaseConfig.BASE_FP, file)
//            intent.setDataAndType(contentUri, "video/*")
//        } else {
//            intent.setDataAndType(uri, "video/*")
//        }
//        startActivityForResult(intent,10001)
        //增加封面
        val imageView = ImageView(this)
        imageView.scaleType = ImageView.ScaleType.CENTER_CROP
        ImgUtil.loadImg(this,path,imageView)
        video_player.setThumbImageView(imageView)
        video_player.setUp(path, true, "")
        video_player.startPlayLogic()
        isPreviewVideo=true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(10001==requestCode){
            if (!mStartedFlag) {
                mBtnPlay.visibility = View.VISIBLE
                mBtnPlay.postDelayed({isPreviewVideo=false},500)
            }
        }
    }

    private var frontOri = 0
    /**
     * 旋转前置摄像头为正的
     */
    private fun frontCameraRotate() {
        val info = CameraInfo()
        Camera.getCameraInfo(1, info)
        val degrees = getDisplayRotation(this)
        var result: Int
        if (info.facing == CameraInfo.CAMERA_FACING_FRONT) {
            result = (info.orientation + degrees) % 360
            result = (360 - result) % 360 // compensate the mirror
        } else { // back-facing
            result = (info.orientation - degrees + 360) % 360
        }
        frontOri = info.orientation
    }

    //结束录制
    private fun stopRecord() {
        if (mStartedFlag) {
            currentTime = 0
            mCountDownTimer.cancel()
            mStartedFlag = false
            try {
                mRecorder.apply {
                    stop()
                    reset()
                    null
                }
                recorderReleaseEnable = false
                releaseCamera()
                cameraReleaseEnable = false
                mBtnPlay.visibility = View.INVISIBLE
            } catch (e: java.lang.RuntimeException) {
                recorderReleaseEnable = false
            }
//            ImgUtil.loadImg(this,path,cover)
            time.visibility=View.GONE
            record_hint1.visibility=View.GONE
            record_hint2.visibility=View.GONE
            circleProgressBar.visibility=View.GONE
            mBtnRecording.visibility=View.GONE
            mBtnRecord.visibility=View.GONE
            record_finish.visibility=View.VISIBLE
            val imageView = ImageView(this)
            imageView.scaleType = ImageView.ScaleType.CENTER_CROP
            ImgUtil.loadImg(this,path,imageView)
            video_player.setThumbImageView(imageView)
            video_player.setUp(path, true, "")
            video_player.startPlayLogic()
        }
    }

    //释放资源
    private fun releaseCamera() {
        if (mCamera != null) {
            mCamera!!.setPreviewCallback(null)
            mCamera!!.stopPreview()
            mCamera!!.release()
            mCamera = null
        }
    }


    override fun onClick(v: View?) {
        when(v?.id){
            R.id.mBtnRecord->{
                if (!mStartedFlag){
                    startRecord()
                }
            }

            R.id.mBtnRecording->{
                if (mStartedFlag) {
                    if (currentTime > 5) {
                        stopRecord()
                    }else{
                        ToastUtil.suc(this,getString(R.string.record_toast_hint))
                    }
//                    stopRecord()
                }
            }

            R.id.mBtnPlay->{
                playRecord()
            }

            R.id.iv_back->{
                finish()
            }

            R.id.check_camera_status->{
                if (!mStartedFlag){
                    mCamera?.stopPreview()
                    mCamera?.release()

                    if (CAMERA_FRONT_POSITION == currentPosition){
                        currentPosition = CAMERA_POST_POSITION
                    }else{
                        currentPosition =CAMERA_FRONT_POSITION
                        frontCameraRotate()
                    }
                    mCamera = getCamera(currentPosition)
                    val parameters = mCamera?.parameters
                    parameters?.pictureFormat = ImageFormat.JPEG
                    //后置摄像头自动聚焦
//                    if (currentPosition == CAMERA_POST_POSITION){
//                        parameters?.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO)
//                    }
                    //自动对焦

                    //自动对焦
                    val focusMode = parameters!!.supportedFocusModes
                    if (focusMode.contains(Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO)) {
                        parameters.focusMode = Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO
                        mCamera!!.cancelAutoFocus()
                    }
//                    parameters?.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO)
                    val sizeList =
                            parameters?.supportedPreviewSizes //获取所有支持的camera尺寸
                    val optimalPreviewSize =
                            getCloselyPreSize(mSurfaceview.getWidth(), mSurfaceview.getHeight(),sizeList)
                    width=optimalPreviewSize!!.width
                    height=optimalPreviewSize!!.height
                    parameters?.setPreviewSize(width, height) //把camera.size赋值到parameters
                    parameters?.setPictureSize(width, height)
                    mCamera?.parameters = parameters
                    mCamera?.setPreviewDisplay(mHolder)
                    mCamera?.setDisplayOrientation(90)
                    mCamera?.startPreview()
                }else{
                    ToastUtil.suc(this,"录制中请先暂停")
                }
            }

            R.id.again_tv->{
                if (!mStartedFlag){
                    mCamera = getCamera(currentPosition)
                    setStartPreview(mHolder)
                }
            }

            R.id.audit->{
                var intent = Intent()
                intent.putExtra("path",path)
                intent.putExtra("w",width)
                intent.putExtra("h",height)
                setResult(Activity.RESULT_OK,intent)
                finish()
            }
            R.id.video_player ->{

            }
        }
    }

    fun getCloselyPreSize( surfaceWidth: Int,  surfaceHeight: Int , preSizeList: List<Camera.Size>?):Camera.Size? {
        val ReqTmpWidth: Int
        val ReqTmpHeight: Int
        // 当屏幕为垂直的时候需要把宽高值进行调换，保证宽大于高
        ReqTmpWidth = surfaceHeight
        ReqTmpHeight = surfaceWidth
//         else {
//            ReqTmpWidth = surfaceWidth
//            ReqTmpHeight = surfaceHeight
//        }
        //先查找preview中是否存在与surfaceview相同宽高的尺寸
        for (size in preSizeList!!) {
            if (size.width == ReqTmpWidth && size.height == ReqTmpHeight) {
                return size
            }
        }
        // 得到与传入的宽高比最接近的size
        val reqRatio = ReqTmpWidth.toFloat() / ReqTmpHeight
        var curRatio: Float
        var deltaRatio: Float
        var deltaRatioMin = Float.MAX_VALUE
        var retSize: Camera.Size? = null
        for (size in preSizeList) {
            curRatio = size.width.toFloat() / size.height
            deltaRatio = Math.abs(reqRatio - curRatio)
            if (deltaRatio < deltaRatioMin) {
                deltaRatioMin = deltaRatio
                retSize = size
            }
        }
        return retSize
    }

    override fun onPause() {
        super.onPause()
        video_player.onVideoPause()
    }

    override fun onResume() {
        super.onResume()
        video_player.onVideoResume()
    }



}
