package com.yun.mayi.pda.bean;

import java.util.List;

/**
 * 总订单
 */
public class Order {

    /**
     * 总订单ID
     */
    private String id;

    /**
     * 订单标识
     */
    private String mark;
    /**
     * 项目条数
     */
    private int skuNum;
    private int boxNum;
    /**
     * 子订单列表
     */
    private List<OrderDetail> details;
    /**
     * 工供应商id
     */
    private int vendorId;
    /**
     * 打包箱列表
     */
    private List<PackBox> boxes;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public int getSkuNum() {
        return skuNum;
    }

    public void setSkuNum(int skuNum) {
        this.skuNum = skuNum;
    }

    public List<OrderDetail> getDetails() {
        return details;
    }

    public void setDetails(List<OrderDetail> details) {
        this.details = details;
    }

    public List<PackBox> getBoxes() {
        return boxes;
    }

    public void setBoxes(List<PackBox> boxes) {
        this.boxes = boxes;
    }

    public int getVendorId() {
        return vendorId;
    }

    public void setVendorId(int vendorId) {
        this.vendorId = vendorId;
    }

    public int getBoxNum() {
        return boxNum;
    }

    public void setBoxNum(int boxNum) {
        this.boxNum = boxNum;
    }
}
