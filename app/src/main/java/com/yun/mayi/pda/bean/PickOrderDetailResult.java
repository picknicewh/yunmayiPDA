package com.yun.mayi.pda.bean;

import java.util.List;

/**
 * 作者： wh
 * 时间：  2018/4/3
 * 名称：待分拣信息详情类
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public class PickOrderDetailResult {

    private List<PickOrderDetail> detail_list;
    private List<PackBoxDetail2> box_list;
    private List<TallyingInfoDetail> list;
    private double originTotalPrice;
    private double realTotalPrice;
    private int count;

    public List<PickOrderDetail> getDetail_list() {
        return detail_list;
    }

    public void setDetail_list(List<PickOrderDetail> detail_list) {
        this.detail_list = detail_list;
    }

    public List<PackBoxDetail2> getBox_list() {
        return box_list;
    }

    public void setBox_list(List<PackBoxDetail2> box_list) {
        this.box_list = box_list;
    }

    public List<TallyingInfoDetail> getList() {
        return list;
    }

    public void setList(List<TallyingInfoDetail> list) {
        this.list = list;
    }

    public double getOriginTotalPrice() {
        return originTotalPrice;
    }

    public void setOriginTotalPrice(double originTotalPrice) {
        this.originTotalPrice = originTotalPrice;
    }

    public double getRealTotalPrice() {
        return realTotalPrice;
    }

    public void setRealTotalPrice(double realTotalPrice) {
        this.realTotalPrice = realTotalPrice;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
