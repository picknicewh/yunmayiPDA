package com.yun.mayi.pda.utils;

import com.yun.mayi.pda.bean.OrderDetail;
import com.yun.mayi.pda.bean.PackBoxDetail2;
import com.yun.mayi.pda.bean.PickDetailInfo;
import com.yun.mayi.pda.bean.PickOrderDetail;
import com.yun.mayi.pda.bean.PickPrintData;
import com.yun.mayi.pda.bean.TallyingInfoDetail;
import com.yun.mayi.pda.db.BoxInfo;
import com.yun.mayi.pda.db.OrderInfo;

/**
 * 作者： wh
 * 时间：  2018/1/9
 * 名称：
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public class BeanChangeUtil {

    public static OrderInfo orderDetail2orderInfo(OrderDetail orderDetail, String vendorId) {
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setId(String.valueOf(orderDetail.getId()));
        orderInfo.setNum(orderDetail.getNum());
        orderInfo.setOriginalNum(orderDetail.getNum());
        orderInfo.setOrderId(orderDetail.getOrderId());
        orderInfo.setOutOfStockNum(orderDetail.getOutOfStockNum());
        orderInfo.setPacked(orderDetail.isPacked() ? 1 : 0);
        orderInfo.setProductBarCode(orderDetail.getProductBarCode());
        orderInfo.setProductImage(orderDetail.getProductImage());
        orderInfo.setProductSpec(orderDetail.getProductSpec());
        orderInfo.setProductId(String.valueOf(orderDetail.getProductId()));
        orderInfo.setProductNumber(orderDetail.getProductNumber());
        orderInfo.setSkuNum(orderDetail.getSkuNum());
        orderInfo.setVendorId(vendorId);
        orderInfo.setProductPrice(orderDetail.getProductPrice());
        orderInfo.setProductUnit(orderDetail.getProductUnit());
        orderInfo.setProductTitle(orderDetail.getProductTitle());
        orderInfo.setIsChecked(1);
        return orderInfo;
    }

    public static OrderInfo pickOrderDetail2orderInfo(PickOrderDetail orderDetail,int source) {
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setId(String.valueOf(orderDetail.getId()));
        orderInfo.setNum(source==0?orderDetail.getQuantity():orderDetail.getPick_quantity());
        orderInfo.setOriginalNum(orderDetail.getQuantity());
        orderInfo.setOrderId(orderDetail.getOrder_id());
        orderInfo.setOutOfStockNum(orderDetail.getOut_of_stock_num());
        orderInfo.setPacked(0);
        orderInfo.setProductBarCode(orderDetail.getProduct_bar_code());
        orderInfo.setProductImage(orderDetail.getProduct_image());
        orderInfo.setProductSpec(orderDetail.getProduct_spec());
        orderInfo.setProductId(String.valueOf(orderDetail.getProduct_id()));
        orderInfo.setProductNumber(orderDetail.getProduct_number());
        orderInfo.setProductUnit(orderDetail.getProduct_unit());
        orderInfo.setProductTitle(orderDetail.getProduct_title());
        orderInfo.setProductMiddleCode(orderDetail.getProduct_middle_code());
        orderInfo.setProductBoxCode(orderDetail.getProduct_box_code());
        orderInfo.setProductPrice(orderDetail.getProduct_sell_price());
        orderInfo.setIsChecked(1);
        return orderInfo;
    }

    public static BoxInfo packBoxDetail2boxInfo(PackBoxDetail2 packBoxDetail) {
        BoxInfo boxInfo = new BoxInfo();
        boxInfo.setId(String.valueOf(packBoxDetail.getId()));
        boxInfo.setNum(packBoxDetail.getNum());
        boxInfo.setOriginalNum(packBoxDetail.getOrigin_num());
        boxInfo.setOrderId(packBoxDetail.getOrder_id());
        boxInfo.setPacked(0);
        boxInfo.setOrderDetailId(String.valueOf(packBoxDetail.getOrder_detail_id()));
        boxInfo.setBoxId(packBoxDetail.getBox_id());
        boxInfo.setProductBarCode(packBoxDetail.getProduct_bar_code());
        boxInfo.setProductImage(packBoxDetail.getProduct_image());
        boxInfo.setProductSpec(packBoxDetail.getProduct_spec());
        boxInfo.setProductId(String.valueOf(packBoxDetail.getProduct_id()));
        boxInfo.setProductNumber(packBoxDetail.getProduct_number());
        boxInfo.setProductUnit(packBoxDetail.getProduct_unit());
        boxInfo.setProductTitle(packBoxDetail.getProduct_title());
        boxInfo.setProductPrice(packBoxDetail.getProduct_sell_price());
        boxInfo.setBoxNum(packBoxDetail.getBox_num());
        return boxInfo;
    }

    public static PickPrintData orderInfo2PickPrintData(OrderInfo orderInfo) {
        PickPrintData pickPrintData = new PickPrintData();
        pickPrintData.setName(orderInfo.getProductTitle());
        pickPrintData.setOrderNum(orderInfo.getOriginalNum());
        pickPrintData.setPickNum(orderInfo.getNum());
        int lessNum = orderInfo.getOriginalNum() - orderInfo.getNum();
        float lessMoney = (float) (lessNum * (orderInfo.getProductPrice() / 100));
        pickPrintData.setLessMoney(lessMoney);
        return pickPrintData;
    }

    public static PickPrintData boxInfoInfo2PickPrintData(BoxInfo boxInfo) {
        PickPrintData pickPrintData = new PickPrintData();
        pickPrintData.setName(boxInfo.getProductTitle());
        pickPrintData.setOrderNum(boxInfo.getOriginalNum());
        pickPrintData.setPickNum(boxInfo.getNum());
        int lessNum = boxInfo.getOriginalNum() - boxInfo.getNum();
        float lessMoney = (float) (lessNum * (boxInfo.getProductPrice() / 100));
        pickPrintData.setLessMoney(lessMoney);
        return pickPrintData;
    }

    public static PickPrintData PickDetailInfo2PickPrintData(PickDetailInfo boxInfo) {
        PickPrintData pickPrintData = new PickPrintData();
        pickPrintData.setName(boxInfo.getProductTitle());
        pickPrintData.setOrderNum(boxInfo.getTotalQuantity());
        pickPrintData.setPickNum(boxInfo.getTotalQuantity()-boxInfo.getOutNum());
        pickPrintData.setLessMoney(boxInfo.getOutPrice()/100);
        return pickPrintData;
    }
    public static PickPrintData TallyingInfoDetail2PickPrintData(TallyingInfoDetail infoDetail ,int isFirst) {
        PickPrintData pickPrintData = new PickPrintData();
        pickPrintData.setPickNum(isFirst==0?infoDetail.getLoadingQuantity():infoDetail.getDeliverQuantity());
        pickPrintData.setOrderNum(infoDetail.getQuantity());
        float lessMoney = (float) ((infoDetail.getQuantity()-infoDetail.getLoadingQuantity())*infoDetail.getProductPrice()*100);
        pickPrintData.setLessMoney(lessMoney);
        pickPrintData.setName(infoDetail.getProductTitle());
        return pickPrintData;
    }

}
