package com.yun.mayi.pda.bean;

import java.util.List;

public class PackOrderResult {

    /**
     * 订单总数
     */
    private int orderNum;

    /**
     * 商品总数
     */
    private int itemNum;
    /**
     * 已拣货单箱数
     */
    private int boxNum;

    /**
     * 总余额
     */
    private String totalFee;
    /**
     * 订单列表
     */
    private List<Order> orders;

    public int getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(int orderNum) {
        this.orderNum = orderNum;
    }

    public int getItemNum() {
        return itemNum;
    }

    public void setItemNum(int itemNum) {
        this.itemNum = itemNum;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public int getBoxNum() {
        return boxNum;
    }

    public void setBoxNum(int boxNum) {
        this.boxNum = boxNum;
    }

    public String getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(String totalFee) {
        this.totalFee = totalFee;
    }

}
