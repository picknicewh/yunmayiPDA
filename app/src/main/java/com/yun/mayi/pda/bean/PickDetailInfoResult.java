package com.yun.mayi.pda.bean;

import java.util.List;

/**
 * 作者： wh
 * 时间：  2018/5/10
 * 名称：
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public class PickDetailInfoResult {

   /**
    * originTotalPrice : 1491.7
    * payTotalPrice : 1313.7
    * totalOutPrice : 99
    * list : [{"orderId":"1803311430354818","totalQuantity":"20","outNum":"0","outPrice":"0","productTitle":"BR36SJ清风原木150抽金装3层抽纸8850【连锁】","productId":56492},{"orderId":"1803311430354818","totalQuantity":"2","outNum":"0","outPrice":"0","productTitle":"【单品送】哈尔滨冰爽啤酒500ml*12罐【连锁】","productId":295962},{"orderId":"1803311430354818","totalQuantity":"13","outNum":"1","outPrice":"9900","productTitle":"促销中Jia珈奥鸡尾酒-青柠味 275ml*24瓶","productId":670488}]
    */

   private double originTotalPrice;
   private double payTotalPrice;
   private double totalOutPrice;
   private List<PickDetailInfo> list;

   public double getOriginTotalPrice() {
      return originTotalPrice;
   }

   public void setOriginTotalPrice(double originTotalPrice) {
      this.originTotalPrice = originTotalPrice;
   }

   public double getPayTotalPrice() {
      return payTotalPrice;
   }

   public void setPayTotalPrice(double payTotalPrice) {
      this.payTotalPrice = payTotalPrice;
   }

   public double getTotalOutPrice() {
      return totalOutPrice;
   }

   public void setTotalOutPrice(double totalOutPrice) {
      this.totalOutPrice = totalOutPrice;
   }

   public List<PickDetailInfo> getList() {
      return list;
   }

   public void setList(List<PickDetailInfo> list) {
      this.list = list;
   }

}
