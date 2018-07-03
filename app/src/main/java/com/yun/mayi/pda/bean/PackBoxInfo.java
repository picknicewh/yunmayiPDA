package com.yun.mayi.pda.bean;

/**
 * 作者： wh
 * 时间：  2018/4/20
 * 名称：加盟商--装箱信息
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public class PackBoxInfo {
    /**
     * id : 373
     * box_id : 18011815012649
     * sku : 2
     * num : 15
     * order_mark : 东湖1
     * vendor_id : 100672
     *  agent_number : 99999999
     * box_num : 1
     */
    private int id;
    private String box_id;
    private int sku;
    private int num;
    private String order_mark;
    private int vendor_id;
    private String agent_number;
    private int box_num;
    private  int is_delete;
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBox_id() {
        return box_id;
    }

    public void setBox_id(String box_id) {
        this.box_id = box_id;
    }

    public int getSku() {
        return sku;
    }

    public void setSku(int sku) {
        this.sku = sku;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getOrder_mark() {
        return order_mark;
    }

    public void setOrder_mark(String order_mark) {
        this.order_mark = order_mark;
    }

    public int getVendor_id() {
        return vendor_id;
    }

    public void setVendor_id(int vendor_id) {
        this.vendor_id = vendor_id;
    }

    public String getAgent_number() {
        return agent_number;
    }

    public void setAgent_number(String agent_number) {
        this.agent_number = agent_number;
    }

    public int getBox_num() {
        return box_num;
    }

    public void setBox_num(int box_num) {
        this.box_num = box_num;
    }

    public int getIs_delete() {
        return is_delete;
    }

    public void setIs_delete(int is_delete) {
        this.is_delete = is_delete;
    }
}
