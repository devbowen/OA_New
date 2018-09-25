package com.even.newoa.ui.fragment.location;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.CircleOptions;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;
import com.even.newoa.R;
import com.even.newoa.base.BaseFragment;
import com.even.newoa.ui.customView.ProgressButton;

/**
 * A simple {@link Fragment} subclass.
 */
public class LocationFragment extends BaseFragment {
    private static final String TAG = "LocationFragment";
    private static final double CENTER_LAT = 43.831574;
    private static final double CENTER_LNG = 125.276039;
    private static final int SIGN_RADIUS = 100;
    public LocationClient mLocationClient;
    private LatLng center = new LatLng(CENTER_LAT, CENTER_LNG);
    private LatLng currentLL;
    private MapView mMapView;
    private BaiduMap baiduMap;
    private ProgressButton signBtn;
    private TextView locationTv;
    private TextView distanceTv;
    private boolean isFirstLocate;
    private String addr;
    private String locationDescribe;
    private double latitude;
    private double longitude;
    private double distance;

    private BDAbstractLocationListener myLocationListener = new BDAbstractLocationListener() {

        @Override
        public void onReceiveLocation(BDLocation location) {
            //获取详细地址信息
            addr = location.getAddrStr();
            latitude = location.getLatitude();
            longitude = location.getLongitude();
            Log.d(TAG, "latitude: " + latitude + " longitude: " + longitude);

            //获取位置描述信息
            locationDescribe = location.getLocationDescribe();

            currentLL = new LatLng(latitude, longitude);
            distance = getDistance(currentLL);

            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    locationTv.setText(addr + "，" + locationDescribe);
                    if (distance <= SIGN_RADIUS) {
                        distanceTv.setText("您已达目的地");
                    } else {
                        distanceTv.setText("距离目的地" + Long.toString(Math.round(distance)) + "米");
                    }
                }
            });
            setMyLocation();
        }
    };

    public LocationFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_location, container, false);

        initViews(view);
        initEvents();
        getLocation();
        return view;
    }

    private void initViews(View view) {
        isFirstLocate = true;
        mMapView = view.findViewById(R.id.bmapView);
        signBtn = view.findViewById(R.id.btn_sign_in);
        locationTv = view.findViewById(R.id.tv_location);
        distanceTv = view.findViewById(R.id.tv_distance);

        baiduMap = mMapView.getMap();
        //开启定位图层
        baiduMap.setMyLocationEnabled(true);
        setCircleOptions();
    }

    private void initEvents() {
        signBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //locationTv.setText();
            }
        });
    }


    private void getLocation() {
        mLocationClient = new LocationClient(getContext());
        mLocationClient.registerLocationListener(myLocationListener);

        LocationClientOption option = new LocationClientOption();
        //可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        //如果开发者想利用定位SDK获得的经纬度直接在百度地图上标注，请选择坐标类型BD09ll
        option.setCoorType("bd09ll");
        //可选，默认0，即仅定位一次，设置发起连续定位请求的间隔需要大于等于1000ms才是有效的
        option.setScanSpan(3000);
        //可选，设置是否需要地址信息，默认不需要
        option.setIsNeedAddress(true);
        //可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.setIgnoreKillProcess(true);
        //可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到
        option.setIsNeedLocationDescribe(true);
        mLocationClient.setLocOption(option);
        mLocationClient.start();
    }

    private void setMyLocation() {
        if (isFirstLocate) {
            LatLng ll = new LatLng(latitude, longitude);
            MapStatusUpdate update = MapStatusUpdateFactory.newLatLng(ll);
            baiduMap.animateMapStatus(update);
            update = MapStatusUpdateFactory.zoomTo(16f);
            baiduMap.animateMapStatus(update);
            isFirstLocate = false;
        }

        baiduMap.setMyLocationConfiguration(new MyLocationConfiguration(MyLocationConfiguration.LocationMode.NORMAL, true, null));

        baiduMap.setMyLocationData(new MyLocationData.Builder()
                .latitude(latitude)
                .longitude(longitude)
                .build());
    }

    /**
     * 设置打卡目标范围圈
     */
    private void setCircleOptions() {

        OverlayOptions ooCircle = new CircleOptions().fillColor(0x8308D5DC).center(center).radius(SIGN_RADIUS);
        baiduMap.addOverlay(ooCircle);
    }

    private double getDistance(LatLng currentLL) {
        return DistanceUtil.getDistance(center, currentLL);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        baiduMap.setMyLocationEnabled(false);
        mMapView.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }
}
