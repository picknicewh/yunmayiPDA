package com.yun.mayi.pda.bean;

import java.util.List;

/**
 * 作者： wh
 * 时间：  2018/5/2
 * 名称：
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public class PickPrintDataResult {
    private PickOrder pickOrder;
    private List<PickPrintData> pickPrintDataList;
    private double originTotalPrice;
    private double realTotalPrice;
    private double totalLessPrice;
    public PickOrder getPickOrder() {
        return pickOrder;
    }

    public void setPickOrder(PickOrder pickOrder) {
        this.pickOrder = pickOrder;
    }

    public List<PickPrintData> getPickPrintDataList() {
        return pickPrintDataList;
    }

    public void setPickPrintDataList(List<PickPrintData> pickPrintDataList) {
        this.pickPrintDataList = pickPrintDataList;
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

    public double getTotalLessPrice() {
        return totalLessPrice;
    }

    public void setTotalLessPrice(double totalLessPrice) {
        this.totalLessPrice = totalLessPrice;
    }
}
