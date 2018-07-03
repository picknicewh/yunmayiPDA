package com.yun.mayi.pda.bean;

/**
 * 作者： wh
 * 时间：  2018/4/4
 * 名称：
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public class PickPrintData {
    private String name;
    private int orderNum;
    private int pickNum;
    private double  lessMoney ;
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(int orderNum) {
        this.orderNum = orderNum;
    }

    public int getPickNum() {
        return pickNum;
    }

    public void setPickNum(int pickNum) {
        this.pickNum = pickNum;
    }

    public double getLessMoney() {
        return lessMoney;
    }

    public void setLessMoney(double lessMoney) {
        this.lessMoney = lessMoney;
    }
}
