/*
package com.yun.mayi.pda.module.drawer.location;

import android.graphics.BitmapFactory;
import android.os.Bundle;

import com.amap.api.location.AMapLocation;
import com.amap.api.maps.AMap;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.route.BusRouteResult;
import com.amap.api.services.route.DriveRouteResult;
import com.amap.api.services.route.RidePath;
import com.amap.api.services.route.RideRouteResult;
import com.amap.api.services.route.RideStep;
import com.amap.api.services.route.RouteSearch;
import com.amap.api.services.route.WalkRouteResult;
import com.yun.mayi.pda.R;
import com.yun.mayi.pda.base.BaseMvpActivity;
import com.yun.mayi.pda.utils.G;

import java.util.ArrayList;
import java.util.List;
*/
/**
 * 作者： wh
 * 时间： 2018/06/07
 * 名称：司机定位界面
 * 版本说明：
 * 附加注释：
 * 主要接口：
 *//*

public class LocationActivity extends BaseMvpActivity<LocationPresenter> implements AMap.OnCameraChangeListener, LocationContract.View, RouteSearch.OnRouteSearchListener {

    */
/**
     * 地图显示控件
     *//*

   // @BindView(R.id.mapView)
 //   MapView mapView;
    */
/**
     * 地图api
     *//*

//    private AMap aMap;
    */
/**
     * 添加覆盖物
     *//*

    private MarkerOptions markerOptions;
    */
/**
     * 城市
     *//*

    private String city;
    */
/**
     * 当前定位位置
     *//*

    private LatLng currentLatLng;
    */
/**
     * 骑行路线
     *//*

    private RouteSearch mRouteSearch;
    */
/**
     * 起点
     *//*

    private LatLonPoint mStartPoint;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_location;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      */
/*  mapView.onCreate(savedInstanceState);
        aMap = mapView.getMap();
        aMap.setTrafficEnabled(true);// 显示实时交通状况
        aMap.setOnCameraChangeListener(this);
        //设置缩放级别
        aMap.moveCamera(CameraUpdateFactory.zoomTo(17));
        aMap.getUiSettings().setRotateGesturesEnabled(false);*//*

        mRouteSearch = new RouteSearch(this);
        mRouteSearch.setRouteSearchListener(this);
        initMarkerOptions();
        if (mPresenter != null) {
            mPresenter.location();

        }
    }

    @Override
    public void initView() {
        setLeftTextId(R.string.workBar);
        setLeftIcon(R.mipmap.ic_back_white);
        setTitleTextId(R.string.drawer_position);
    }

    @Override
    protected void initInject() {
        mDaggerActivityComponent.inject(this);
    }


    @Override
    public void onCameraChange(CameraPosition cameraPosition) {
  //    aMap.moveCamera(CameraUpdateFactory.changeLatLng(cameraPosition.target));
    }

    @Override
    protected void onResume() {
        super.onResume();
   //     mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
     //   mapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
      //  mapView.onDestroy();
        if (mPresenter != null) {
            mPresenter.destroy();
        }
    }

    private void initMarkerOptions() {
        markerOptions = new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                .decodeResource(getResources(), R.mipmap.ic_map_drawer))).draggable(true);
    //    aMap.addMarker(markerOptions);
    }

    @Override
    public void onCameraChangeFinish(CameraPosition cameraPosition) {
    //    aMap.moveCamera(CameraUpdateFactory.changeLatLng(cameraPosition.target));
        G.log("xxxxxxxxxxxx("+cameraPosition.target.latitude+","+cameraPosition.target.longitude+")");
    }

    @Override
    public String getKeyWord() {
        return "三塘沁园";
    }

    @Override
    public String getCity() {
        return city;
    }

    @Override
    public void setPoiItem(PoiItem poiItem) {
        RouteSearch.FromAndTo fromAndTo = new RouteSearch.FromAndTo(mStartPoint, poiItem.getLatLonPoint());
        RouteSearch.RideRouteQuery query = new RouteSearch.RideRouteQuery(fromAndTo, MODE_APPEND);
        mRouteSearch.calculateRideRouteAsyn(query);
    }


    @Override
    public void setAMapLocation(AMapLocation amapLocation) {
        if (amapLocation != null) {
            if (amapLocation.getErrorCode() == 0) {
                amapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
                //将地图移动到定位点
                mStartPoint = new LatLonPoint(amapLocation.getLatitude(), amapLocation.getLongitude());
                currentLatLng = new LatLng(amapLocation.getLatitude(), amapLocation.getLongitude());
           //     aMap.moveCamera(CameraUpdateFactory.changeLatLng(currentLatLng));
                markerOptions.position(currentLatLng);
         //       aMap.addMarker(markerOptions);
                city = amapLocation.getCity();//城市信息
                if (mPresenter != null) {
                    mPresenter.doSearchByKeyWord();
                }
              */
/*  amapLocation.getLatitude();//获取纬度
                amapLocation.getLongitude();//获取经度
                amapLocation.getAccuracy();//获取精度信息
                amapLocation.getAddress();//地址，如果option中设置isNeedAddress为false，则没有此结果，网络定位结果中会有地址信息，GPS定位不返回地址信息。
                amapLocation.getCountry();//国家信息
                amapLocation.getProvince();//省信息

                amapLocation.getDistrict();//城区信息
                amapLocation.getStreet();//街道信息
                amapLocation.getStreetNum();//街道门牌号信息
                amapLocation.getCityCode();//城市编码
                amapLocation.getAdCode();//地区编码
                amapLocation.getAoiName();//获取当前定位点的AOI信息
                amapLocation.getBuildingId();//获取当前室内定位的建筑物Id
                amapLocation.getFloor();//获取当前室内定位的楼层
                amapLocation.getGpsAccuracyStatus();//获取GPS的当前状态
                //获取定位时间
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = new Date(amapLocation.getTime());
                df.format(date);*//*

            } else {
                //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                G.log("AmapError" + "location Error, ErrCode:"
                        + amapLocation.getErrorCode() + ", errInfo:"
                        + amapLocation.getErrorInfo());
            }
        }
    }

    @Override
    public void onBusRouteSearched(BusRouteResult busRouteResult, int i) {

    }

    @Override
    public void onDriveRouteSearched(DriveRouteResult driveRouteResult, int i) {

    }

    @Override
    public void onWalkRouteSearched(WalkRouteResult walkRouteResult, int i) {

    }

    @Override
    public void onRideRouteSearched(RideRouteResult result, int rCode) {
        if (rCode == 1000) {//成功
            List<RidePath> list_path = result.getPaths();
            for (int i = 0; i < list_path.size(); i++) {
                List<RideStep> list_step = list_path.get(i).getSteps();
                for (int j = 0; j < list_step.size(); j++) {
                    List<LatLonPoint> listlatlone = list_step.get(j).getPolyline();
                    //画线
                    List<LatLng> latLngs = new ArrayList<>();
                    for (int k = 0; k < listlatlone.size(); k++) {
                        latLngs.add(new LatLng(listlatlone.get(k).getLatitude(), listlatlone.get(k).getLongitude()));
                    }
                 */
/*   aMap.addPolyline(new PolylineOptions().
                            addAll(latLngs)
                            .width(5)//设置线宽度
                            .setUseTexture(true)//设置纹理贴图
                            .setDottedLine(false)//设置虚线
                            .color(0xff517efd));//设置线的颜色*//*

                }
            }
        } else {
            G.showToast(this, "定位失败");
        }
    }
}
*/
