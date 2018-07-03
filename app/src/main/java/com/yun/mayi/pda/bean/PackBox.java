package com.yun.mayi.pda.bean;

import java.util.List;

/**
 * 打包箱
 */
public class PackBox {

    /**
     * ID
     */
    private long id;

    /**
     * 箱ID
     */
    private long boxId;

    /**
     * 供应商ID
     */
    private long vendorId;

    /**
     * 订单ID
     */
    private String orderId;

    /**
     * 订单标识
     */
    private String orderMark;

    /**
     * 箱中商品总数
     */
    private int itemNum;
    /**
     * 公司
     */
    private String vendorCompany;
    /**
     * 城市
     */
    private String agentCompany;

    private int boxNum;
    /**
     * 明细列表
     */
    private List<PackBoxDetail> details;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getBoxId() {
        return boxId;
    }

    public void setBoxId(long boxId) {
        this.boxId = boxId;
    }

    public long getVendorId() {
        return vendorId;
    }

    public void setVendorId(long vendorId) {
        this.vendorId = vendorId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderMark() {
        return orderMark;
    }

    public void setOrderMark(String orderMark) {
        this.orderMark = orderMark;
    }

    public int getItemNum() {
        return itemNum;
    }

    public void setItemNum(int itemNum) {
        this.itemNum = itemNum;
    }

    public List<PackBoxDetail> getDetails() {
        return details;
    }

    public void setDetails(List<PackBoxDetail> details) {
        this.details = details;
    }

    public String getVendorCompany() {
        return vendorCompany;
    }

    public void setVendorCompany(String vendorCompany) {
        this.vendorCompany = vendorCompany;
    }

    public String getAgentCompany() {
        return agentCompany;
    }

    public void setAgentCompany(String agentCompany) {
        this.agentCompany = agentCompany;
    }

    public int getBoxNum() {
        return boxNum;
    }

    public void setBoxNum(int boxNum) {
        this.boxNum = boxNum;
    }
}
