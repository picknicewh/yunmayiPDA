package com.yun.mayi.pda.network;

import com.yun.mayi.pda.bean.AgentInfo;
import com.yun.mayi.pda.bean.ConformGoodsResult;
import com.yun.mayi.pda.bean.DrawerInfo;
import com.yun.mayi.pda.bean.LoginInfo;
import com.yun.mayi.pda.bean.Order;
import com.yun.mayi.pda.bean.OrderDetailResult;
import com.yun.mayi.pda.bean.PackBox;
import com.yun.mayi.pda.bean.PackBoxDetail;
import com.yun.mayi.pda.bean.PackBoxResult;
import com.yun.mayi.pda.bean.PackInfoDetailResult;
import com.yun.mayi.pda.bean.PackNoteInfoResult;
import com.yun.mayi.pda.bean.PackOrderResult;
import com.yun.mayi.pda.bean.PayInfo;
import com.yun.mayi.pda.bean.PayResult;
import com.yun.mayi.pda.bean.PickDetailInfoResult;
import com.yun.mayi.pda.bean.PickOrder;
import com.yun.mayi.pda.bean.PickOrderDetail;
import com.yun.mayi.pda.bean.PickOrderDetailResult;
import com.yun.mayi.pda.bean.PickOrderResult;
import com.yun.mayi.pda.bean.ReceiptPrintInfo;
import com.yun.mayi.pda.bean.ReceptOrderInfo;
import com.yun.mayi.pda.bean.RejectVoResult;
import com.yun.mayi.pda.bean.SalemanVo;
import com.yun.mayi.pda.bean.VendorInfo;
import com.yun.mayi.pda.bean.Version;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;
import rx.Observable;

/**
 * 作者： wh
 * 时间：  2017/12/22
 * 名称：
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public interface ApiServer {
    /**
     * 下载文件
     */
    @Streaming
    @GET
    Observable<ResponseBody> downLoadFile(@Url String path);

    /**
     * 上传图片
     */
    @Multipart
    @POST("/common/uploader/image")
    Observable<Result> uploadFile(@Part MultipartBody.Part part, @Part("app_id") RequestBody app_id, @Part("timestamp") RequestBody timestamp, @Part("sign") RequestBody sign);

    /**
     * 版本更新
     */
    @GET("/cashier/version/getAppByPackageName")
    Observable<Result<Version>> getVersion(@QueryMap Map<String, Object> params);

    /**
     * 登录
     */
    @GET("pda/passport/login")
    Observable<Result<LoginInfo>> login(@QueryMap Map<String, Object> params);

    /**
     * 获取拣货单列表-单件
     */
    @GET("wholesale/vendor_order/packList")
    Observable<Result<PackOrderResult>> getPackList(@QueryMap Map<String, Object> params);

    /**
     * 获取拣货单详情
     */
    @GET("/wholesale/vendor_order/getPackOrder")
    Observable<Result<Order>> getPackOrderDetail(@QueryMap Map<String, Object> params);

    /**
     * 获取拣货单详情
     */
    @FormUrlEncoded
    @POST("/pda/vendor_packbox/packing")
    Observable<Result<PackBox>> packBox(@FieldMap Map<String, Object> params);

    /**
     * 分页获取装箱信息列表
     */
    @GET("/pda/vendor_packbox/list")
    Observable<Result<PackBoxResult>> getPackBoxList(@QueryMap Map<String, Object> params);

    /**
     * 获取装箱明细列表
     */
    @GET("/pda/vendor_packbox/get")
    Observable<Result<PackBox>> getPackBoxDetails(@QueryMap Map<String, Object> params);

    /**
     * 装箱打包操作
     */
    @FormUrlEncoded
    @POST("/pda/vendor_packbox/packing")
    Observable<Result> packing(@FieldMap Map<String, Object> params);

    /**
     * 拆箱操作
     */
    @FormUrlEncoded
    @POST("/pda/vendor_packbox/unboxing")
    Observable<Result<String>> unboxing(@FieldMap Map<String, Object> params);

    /**
     * 全缺操作
     */
    @FormUrlEncoded
    @POST("/pda/vendor_packbox/outofstock")
    Observable<Result<String>> outofstock(@FieldMap Map<String, Object> params);

    /**
     * 分页获取取消订单的装箱信息列表
     */
    @GET("/pda/vendor_packbox/cancelList")
    Observable<Result<PackBoxResult>> listCancel(@QueryMap Map<String, Object> params);

    /**
     * * 获取供应商异常订单明细列表
     */
    @GET("/wholesale/vendor_order/listAbnormalOrderDetails")
    Observable<Result<OrderDetailResult>> getLessPack(@QueryMap Map<String, Object> params);

    /**
     * * 获取取消订单装箱明细列表
     */
    @GET("/pda/vendor_packbox/detailsForAbnormalOrder")
    Observable<Result<List<PackBoxDetail>>> getForAbnormalOrderDetails(@QueryMap Map<String, Object> params);

    /**
     * * 获取仓库退货审核列表
     */
    @GET("/wholesale/refund/warehouselist")
    Observable<Result<RejectVoResult>> getWareHouseList(@QueryMap Map<String, Object> params);


    /**
     * * 获取仓库退货审核列表
     */
    @GET("/wholesale/user/salesmansByToken")
    Observable<Result<List<SalemanVo>>> getSalesmansByToken(@QueryMap Map<String, Object> params);

    /**
     * * 仓库打回审核
     */
    @FormUrlEncoded
    @POST("/wholesale/refund/warehousereject")
    Observable<Result<String>> rejectWareHouse(@FieldMap Map<String, Object> params);

    /**
     * 仓库通过审核
     */
    @FormUrlEncoded
    @POST("/wholesale/refund/warehousepass")
    Observable<Result<String>> passWareHouse(@FieldMap Map<String, Object> params);

    /**
     * 送货单扫描
     */
    @GET("/pda/agent_pickorder/orderAssignPicker")
    Observable<Result<String>> OrderAssignPicker(@QueryMap Map<String, Object> params);

    /**
     * 待分拣单
     */
    @GET("/pda/agent_pickorder/toBePickOrder")
    Observable<Result<PickOrderResult>> getPickOrderList(@QueryMap Map<String, Object> params);

    /**
     * 分拣单详情
     */
    @GET("/pda/agent_pickorder/pickOrderDetail")
    Observable<Result<PickOrderDetailResult>> getPickOrderDetail(@QueryMap Map<String, Object> params);

    /**
     * 按货分拣
     */
    @GET("/pda/agent_pickorder/pickByProduct")
    Observable<Result<PickOrderDetailResult>> getPickByProduct(@QueryMap Map<String, Object> params);

    /**
     * 按货分拣详情
     */
    @GET("/pda/agent_pickorder/pickByProductDetail")
    Observable<Result<List<PickOrderDetail>>> getPickByProductDetail(@QueryMap Map<String, Object> params);

    /**
     * 分拣完成
     */
    @FormUrlEncoded
    @POST("/pda/agent_pickorder/pickFinish")
    Observable<Result<String>> pickFinish(@FieldMap Map<String, Object> params);

    /**
     * 送货单(整件) - 读取送货单数据
     */
    @GET("/pda/vendor_packbox/getVendorOrderInfo")
    Observable<Result<PackNoteInfoResult>> getVendorOrderInfo(@QueryMap Map<String, Object> params);

    /**
     * 已拣货(整件) - 列表
     */
    @GET("/pda/vendor_packbox/getVerifyVendorProductList")
    Observable<Result<PackInfoDetailResult>> getVerifyVendorProductList(@QueryMap Map<String, Object> params);

    /**
     * 送货单(整件)  - 确认收货
     */
    @GET("/pda/vendor_packbox/verifyProductNumber")
    Observable<Result<String>> verifyProductNumber(@QueryMap Map<String, Object> params);

    /**
     * 送货单(整件)  - 批量确认收货
     */
    @GET("/pda/vendor_packbox/batchVerifyProductNumber")
    Observable<Result<String>> verifyAllProductNumber(@QueryMap Map<String, Object> params);


    /**
     * 已拣货(整件) - 删除
     */
    @GET("/pda/vendor_packbox/delVerifyVendorProduct")
    Observable<Result<String>> delVerifyVendorProduct(@QueryMap Map<String, Object> params);

    /**
     * 根据平台编号获取供应商信息 - 加盟商操作
     */
    @GET("/pda/agent_pickorder/getVendorInfoByAgentNumber")
    Observable<Result<List<VendorInfo>>> getVendorInfoByAgentNumber(@QueryMap Map<String, Object> params);

    /**
     * 查询详情包中供应商的数据 - 加盟商操作
     */
    @GET("/pda/agent_pickorder/getVerifyProductListByVendor")
    Observable<Result<ConformGoodsResult>> getVerifyProductListByVendor(@QueryMap Map<String, Object> params);

    /**
     * 批量确认收货数量 - 加盟商操作
     */
    @GET("/pda/agent_pickorder/batchVerifyVendorProductNum")
    Observable<Result<String>> verifyVendorAllProductNum(@QueryMap Map<String, Object> params);

    /**
     * 获取多平台数据 - 加盟商操作
     */
    @GET("/pda/agent_pickorder/getDifferentAgentData")
    Observable<Result<List<AgentInfo>>> getDifferentAgentList(@QueryMap Map<String, Object> params);


    /**
     * 已分拣订单详情列表- 加盟商操作
     */
    @FormUrlEncoded
    @POST("/pda/agent_pickorder/pickedDetail")
    Observable<Result<PickDetailInfoResult>> pickedDetail(@FieldMap Map<String, Object> params);

    /**
     * 获取司机(送货员)列表-供应商
     */
   // @FormUrlEncoded
    @GET("/pda/agent_delivery/deliverymanList")
    Observable<Result<List<DrawerInfo>>> getDeliverymanList(@QueryMap Map<String, Object> params);
    /**
      装车 - 分配订单-供应商
     */
    @FormUrlEncoded
    @POST("/pda/agent_delivery/distributeLoading")
    Observable<Result<String>> distributeLoading(@FieldMap Map<String, Object> params);

    /**
     *装车 - 获取分配的订单列表-供应商
     */
    @FormUrlEncoded
    @POST("/pda/agent_delivery/getLoadingOrderList")
    Observable<Result<List<PickOrder>>> getLoadingOrderList(@FieldMap Map<String, Object> params);

    /**
     * 司机点货列表-供应商
     * 司机点货列表/司机发车订单列表
     */
//    @FormUrlEncoded
    @GET("/pda/agent_delivery/deliveryTallyingList")
    Observable<Result<PickOrderResult>>  getTallyingList(@QueryMap Map<String, Object> params);
    /**
     * 装车数据／卸车点货数据-供应商
     * 装车数据is_pick=1卸车点货is_pick=2 再次卸货数据is_pick=3
     */
   // @FormUrlEncoded
    @GET("/pda/agent_delivery/deliveryOrderDetail")
    Observable<Result<PickOrderDetailResult>>  getDeliveryOrderDetail(@QueryMap Map<String, Object> params);

    /**
     * 装车完成／卸货完成-供应商
     */
    @FormUrlEncoded
    @POST("/pda/agent_delivery/agentFinishPick")
    Observable<Result<String>>  agentFinishPick(@FieldMap Map<String, Object> params);

    /**
     * 获取订单收款列表 供应商（司机）
     */
    @GET("/pda/agent_delivery/orderReceipts")
    Observable<Result<List<ReceptOrderInfo>>>  getOrderReceiptList(@QueryMap Map<String, Object> params);

    /**
     *收款数据打印 供应商（司机）
     */
    @GET("/pda/agent_delivery/receiptsPrint")
    Observable<Result<ReceiptPrintInfo>>  receiptsPrint(@QueryMap Map<String, Object> params);

    /**
     * 支付宝支付
     */
    @GET("/pda/agent_alipay/pay")
    Observable<Result<PayInfo>>  aliPay(@QueryMap Map<String, Object> params);
    /**
     * 支付查询
     */
    @GET("/pda/agent_alipay/query")
    Observable<Result<PayResult>> queryPayList(@QueryMap Map<String, Object> params);
    /**
     *pda司机送货地址经纬度刷新
     */
    @FormUrlEncoded
    @POST("/pda/agent_delivery/deliverymanAddressRefresh")
    Observable<Result<String>> addressRefresh(@FieldMap Map<String, Object> params);
    /**
     * 供应商--发车操作
     */
    @FormUrlEncoded
    @POST("/pda/agent_delivery/startDelivery")
    Observable<Result<String>> startDelivery(@FieldMap Map<String, Object> params);
    /**
     * 供应商--司机扫描操作
     */
    @FormUrlEncoded
    @POST("/pda/agent_delivery/deliveryScan")
    Observable<Result<String>> deliveryScan(@FieldMap Map<String, Object> params);
}
