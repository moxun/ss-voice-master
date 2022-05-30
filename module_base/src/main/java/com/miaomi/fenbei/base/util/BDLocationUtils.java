package com.miaomi.fenbei.base.util;//package com.miaomi.fenbei.base.util;
//
//import android.content.Context;
//
//import com.baidu.location.BDAbstractLocationListener;
//import com.baidu.location.BDLocation;
//import com.baidu.location.LocationClient;
//import com.baidu.location.LocationClientOption;
//
//public class BDLocationUtils {
//    public static void startLocation(Context context, final OnLoacationListener onLoacationListener){
//        final LocationClient mLocationClient =new LocationClient(context);
//        //声明LocationClient类
//        //声明LocationClient类
//        mLocationClient.registerLocationListener(new BDAbstractLocationListener() {
//            @Override
//            public void onReceiveLocation(BDLocation bdLocation) {
//                String city = bdLocation.getCity();
//                mLocationClient.stop();
//                if (onLoacationListener != null){
//                    onLoacationListener.onLocation(city);
//                }
//            }
//        });
//        //注册监听函数
//        //注册监听函数
//        LocationClientOption option =new LocationClientOption();
//
//        option.setIsNeedAddress(true);
//
//        option.setNeedNewVersionRgc(true);
//
//        mLocationClient.setLocOption(option);
//        mLocationClient.start();
//    }
//    public interface OnLoacationListener{
//        void onLocation(String city);
//    }
//}
