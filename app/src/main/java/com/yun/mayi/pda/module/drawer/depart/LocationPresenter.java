/*
package com.yun.mayi.pda.module.drawer.location;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.yun.mayi.pda.application.YunmayiApplication;
import com.yun.mayi.pda.base.BasePresenter;
import com.yun.mayi.pda.utils.G;

import java.util.List;

import javax.inject.Inject;

*/
/**
 * 作者： wh
 * 时间：  2018/3/30
 * 名称：
 * 版本说明：
 * 附加注释：
 * 主要接口：
 *//*

public class LocationPresenter extends BasePresenter<LocationContract.View> implements LocationContract.Presenter, AMapLocationListener, PoiSearch.OnPoiSearchListener {
    */
/**
     * 声明mlocationClient对象
     *//*

    public AMapLocationClient mlocationClient;
    */
/**
     * 声明mLocationOption对象
     *//*

    public AMapLocationClientOption mLocationOption = null;
    */
/**
     * Poi查询条件类
     *//*

    private PoiSearch.Query query;
    */
/**
     * POI搜索
     *//*

    private PoiSearch poiSearch;

    @Inject
    public LocationPresenter() {
        initLoc();
    }

    @Override
    public void location() {
        mlocationClient.startLocation();
    }

    @Override
    public void doSearchByKeyWord() {
        query = new PoiSearch.Query(mView.getKeyWord(), "", mView.getCity());
        query.setPageSize(5);
        poiSearch = new PoiSearch(YunmayiApplication.getInstance().getApplicationContext(), query);
        poiSearch.setOnPoiSearchListener(this);
        poiSearch.searchPOIAsyn();
    }


    private void initLoc() {
        //初始化定位参数
        mLocationOption = new AMapLocationClientOption();
        mlocationClient = new AMapLocationClient(YunmayiApplication.getInstance().getApplicationContext());
        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);
        //设置是否只定位一次,默认为false
        mLocationOption.setOnceLocation(false);
        //设置是否允许模拟位置,默认为false，不允许模拟位置
        mLocationOption.setMockEnable(false);
        //设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.setInterval(2000);
        //给定位客户端对象设置定位参数
        mlocationClient.setLocationOption(mLocationOption);
        mlocationClient.setLocationListener(this);
    }

    @Override
    public void destroy() {
        mlocationClient.stopLocation();
        mlocationClient.onDestroy();
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        G.log("XEXEXE" + "定位监听");
        mView.setAMapLocation(aMapLocation);
    }

    @Override
    public void onPoiSearched(PoiResult result, int rCode) {
        if (rCode == AMapException.CODE_AMAP_SUCCESS) {
            if (result != null && result.getQuery() != null) {  // 搜索poi的结果
                if (result.getQuery().equals(query)) {  // 是否是同一条
                    List<PoiItem> poiItems = result.getPois();// 取得第一页的poiitem数据，页数从数字0开始
                    if (poiItems.size() > 0) {
                        if (rCode == AMapException.CODE_AMAP_SUCCESS) {
                            PoiItem poiItem = poiItems.get(0);
                            mView.setPoiItem(poiItem);
                        }
                    }
                }
            } else {
                mView.showMessage("搜索失败");
            }
        }
    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {

    }
}
*/
