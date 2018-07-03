package com.yun.mayi.pda.bean;

import java.util.List;

/**
 * 作者： wh
 * 时间：  2018/4/20
 * 名称：加盟商确认收货详情
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public class ConformGoodsResult {

    /**
     * skuNum : 5
     * totalNum : 31
     * box_list : [{"id":373,"box_id":"18011815012649","sku":2,"num":15,"order_mark":"东湖1","vendor_id":100672}]
     * pack_list : [{"id":3026,"box_id":0,"vendor_id":100672,"operator_id":0,"order_id":"0","order_mark":"东湖4",
     * "order_detail_id":0,"product_id":528,"product_title":"测试商品2","product_number":"21212","product_bar_code":"
     * ","product_middle_code":"","product_box_code":"","product_spec":"500ML","product_unit":"瓶","product_image":
     * "/upload/2015/04/03/5483483cf2e7a70c52642ac988a51985.png","origin_num":3,"num":3,"receive_num":1,"receive_operator_id":100672,
     * "receive_time":"2018-04-14 15:30:11","state":1,"agent_number":"57100000","is_delete":0,"order_create_time":"2018-04-09 00:00:00",
     * "create_time":"2018-04-14 14:32:41","modify_time":"2018-04-14 15:30:11"},{"id":3027,"box_id":0,"vendor_id":100672,"operator_id":0,
     * "order_id":"0","order_mark":"东湖2","order_detail_id":0,"product_id":534,"product_title":"测试商品商品","product_number":"544564",
     * "product_bar_code":"","product_middle_code":"","product_box_code":"","product_spec":"45","product_unit":"",
     * "product_image":"/upload/2014/11/13/5c776dac7059645523861bec12f2b2f8.png","origin_num":10,"num":10,"receive_num":0,"receive_operator_id":0,"receive_time":"0000-00-00 00:00:00","state":1,"agent_number":"57100000","is_delete":0,"order_create_time":"2018-04-09 00:00:00","create_time":"2018-04-14 14:32:41","modify_time":"2018-04-14 14:32:41"},{"id":3028,"box_id":0,"vendor_id":100672,"operator_id":0,"order_id":"0","order_mark":"东湖3","order_detail_id":0,"product_id":528,"product_title":"测试商品2","product_number":"21212","product_bar_code":"","product_middle_code":"","product_box_code":"","product_spec":"500ML","product_unit":"瓶","product_image":"/upload/2015/04/03/5483483cf2e7a70c52642ac988a51985.png","origin_num":3,"num":3,"receive_num":0,"receive_operator_id":0,"receive_time":"0000-00-00 00:00:00","state":1,"agent_number":"57100000","is_delete":0,"order_create_time":"2018-04-09 00:00:00","create_time":"2018-04-14 15:34:28","modify_time":"2018-04-14 15:34:28"}]
     */

    private int skuNum;
    private int totalNum;
    private int totalBoxNum;
    private int originSkuNum;
    private int originTotalNum;
    private int originTotalBoxNum;
    private List<PackBoxInfo> box_list;
    private List<PackDetailInfo> pack_list;

    public int getTotalBoxNum() {
        return totalBoxNum;
    }

    public void setTotalBoxNum(int totalBoxNum) {
        this.totalBoxNum = totalBoxNum;
    }

    public int getSkuNum() {
        return skuNum;
    }

    public void setSkuNum(int skuNum) {
        this.skuNum = skuNum;
    }

    public int getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(int totalNum) {
        this.totalNum = totalNum;
    }

    public List<PackBoxInfo> getBox_list() {
        return box_list;
    }

    public void setBox_list(List<PackBoxInfo> box_list) {
        this.box_list = box_list;
    }

    public List<PackDetailInfo> getPack_list() {
        return pack_list;
    }

    public void setPack_list(List<PackDetailInfo> pack_list) {
        this.pack_list = pack_list;
    }

    public int getOriginSkuNum() {
        return originSkuNum;
    }

    public void setOriginSkuNum(int originSkuNum) {
        this.originSkuNum = originSkuNum;
    }

    public int getOriginTotalNum() {
        return originTotalNum;
    }

    public void setOriginTotalNum(int originTotalNum) {
        this.originTotalNum = originTotalNum;
    }

    public int getOriginTotalBoxNum() {
        return originTotalBoxNum;
    }

    public void setOriginTotalBoxNum(int originTotalBoxNum) {
        this.originTotalBoxNum = originTotalBoxNum;
    }
}
