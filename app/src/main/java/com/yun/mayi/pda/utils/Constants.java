package com.yun.mayi.pda.utils;

import java.util.UUID;

/**
 * Created by ys on 2017/6/13.
 */

public class Constants {
    /**
     ** quantity : 3---->下单数量
     * pickQuantity : 0---->加盟商分拣数量
     * deliverQuantity : 3---->司机配送数量
     * loadingQuantity : 3---->加盟商装车数量
     * finishQuantity : 3---->收款数量
     */
    public static final UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    public static final int HOME_WORKBAR = 0;
    public static final int HOME_MESSAGE = 1;
    public static final int HOME_SETTIG = 2;
    /**
     * wifi连接
     */
    public static final String ACTION_WIFION = "action.com.yun.mayi.pda.wifion";
    /**
     * wifi断开
     */
    public static final String ACTION_WIFIOFF = "action.com.yun.mayi.pda.wifioff";

    /**
     * 蓝牙UUID
     */
    public static UUID SPP_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    public static  String ACTION_DISPLAY_SCAN_RESULT = "techain.intent.action.DISPLAY_SCAN_RESULT";

    //启用蓝牙
    public static final int BLUE_TOOTH_OPEN = 1000;
    //禁用蓝牙
    public static final int BLUE_TOOTH_CLOSE = BLUE_TOOTH_OPEN + 1;
    //搜索蓝牙
    public static final int BLUE_TOOTH_SEARTH = BLUE_TOOTH_CLOSE + 1;
    //被搜索蓝牙
    public static final int BLUE_TOOTH_MY_SEARTH = BLUE_TOOTH_SEARTH + 1;
    //关闭蓝牙连接
    public static final int BLUE_TOOTH_CLEAR = BLUE_TOOTH_MY_SEARTH + 1;

    /**
     * 拣货状态 待拣货
     */
    public static final int WAIT_PACK = 0;
    /**
     * 拣货状态 已拣货
     */
    public static final int HAS_PACK = 1;
    /**
     * 箱详情 取消订单
     */
    public static final int CANCLEORDER = 0;
    /**
     * 箱详情 装箱详情
     */
    public static final int PACKEDDETAIL = 1;

    /**
     * 扫描枪广播
     */
    public static final String SCN_CUST_ACTION_SCODE = "com.android.server.scannerservice.broadcast";
    public static final String SCN_CUST_EX_SCODE = "scannerdata";
    /**
     * 定位后台服务
     */
    public static final String START_LOCATIONS_ERVICE = "module.common.LocationService.startlocation";

    /**
     * 退货审核
     * 状态3未审核
     */
    public static final int STATE_REJECT_UN_HECK = 3;
    /**
     * 4已打回
     */
    public static final int STATR_REJECT_BACK = 4;
    /**
     * 5成功退货
     */
    public static final int STATE_REJECT_CHECK_SUCCESS = 5;

    /**
     * 供应商确认收货
     */
    public static final int SOURE_CONFORM_GOODS = 1;
    /**
     * 供应商退货审核
     */
    public static final int SOURE_REJECT_CHECK = 2;
    /**
     * 供应商待拣货单
     */
    public static final int SOURE_WAIT_PACK = 3;
    /**
     * 供应商按货分拣
     */
    public static final int SOURE_GOODS_PACK = 4;
    /**
     * 供应商分拣管理
     */
    public static final int SOURE_MANAGER_PACK = 5;
    /**
     * 供应商装箱点货
     */
    public static final int SOURE_DRAWER_TALLYING = 6;

    /**
     * 供应商装箱点货
     */
    public static final int SOURE_LOAD_CAR = 7;
    /**
     * 订单收款
     */
    public static final int SOURE_GATHERING = 8;
    /**
     * 供应商-司机发车
     */

    public static final int SOURE_DEPART = 9;
    /**
     * 司机--订单管理
     */
    public static final int SOURE_ORDER_MANAGER = 10;
    /**
     * 收货确认---类型整件
     */
    public static final int TYPE_TOTAL = 1;
    /**
     * 收货确认---类型单件
     */
    public static final int TYPE_SINGLE = 0;

    /**
     * 支付宝支付业务：入参app_id
     */
    public static final String APPID = "2018052960319247";
    /**
     * 支付宝账户登录授权业务：入参pid值
     */
    public static final String PID = "";
    /**
     * 支付宝账户登录授权业务：入参target_id值
     */
    public static final String TARGET_ID = "";

    public static final String RSA2_PRIVATE = "";
    public static final String RSA_PRIVATE = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQC96m5Myf8oucSDrlVUjRNaAid7N7awK6pvYdjOyeAdZEEfENC/XsBsTqr9NrLQYovCJDiiUhWtsm1SwdDcpc+WR6oFsCzt0qL48FxN6/5+uIxeEZkSQu+jCEkf9tIcLbEFO8vfE7ZOnBpnAOh7QBfj2UGMWYYmKbgCQquzRXahlKFEh6mW0OvMesjNtFQnNfDrRRdpxBz2emT1csA2eJplqE6KhSO2R88wtCZj9sG998tyEH+a4TzqLN+Gm5Fjcmtw96AZbIO8ZXM8RipkzgTXQ220h4HZc31d3lOPzb8BzcZvYhhjjJYV8WVkJF8iehG2B3R/XjOEP+VRm/ANrl5jAgMBAAECggEBAKySeegNJW88XvXJVICgMRV5H6c32Sn8g8LyhrBhgOU0VHiGLym2T+QbGAU/zvzMj3K6tzvImypbGDw7Ii9d/GPyeuzwSeNRpAXRZA7qgzdjOym/k/ikGl1rCgehoQs5+cFntLngj6ie8N7oVFiqxQqmQInoT54eCrcDHkKovEPV2ntJaJU/Mj2cHQJFOf67d+C169FX5rysLOkiwD1UV5g12dBJtfdQ7BWZr7KnVkHbNNQSByQw9Oa5uoTug6aTziMgSuoooAByCiqEawJBY6NuJ24CcWvYp4dVnCPj83FB/u9eCh/4dpiokbYtkMi1pwc6jN1yhQXpTYxmgXwuqQECgYEA3LeQNLONJgVVJ/pMv2xD5EGbZO+jnIU1YqBUdvaJMMlSdfPgiAQQ68/k+6CWotDq15IKwwAOYPoCXqrUGMws6J6FB8OsIUxtqesHrQi3+a3zXJBgWhgaCB+sbVwFdDQCiUXvanyiuTII34Mn0/pX3J0Avi2mwExg9fMmZ2VUB28CgYEA3EZhovntw92lK35h9QFr9+hUxL3KbEUrPn4LQzJdpWVA3ABGNv9bMIWDWTSgUr8q+xmhecrdwY/yyTiX+c6biOnoj8Lj+BBsZFzfwWxnCpclptLP8cUoZzY6jPQXbT2J0/23fM0tuIAuOLZ7wc9hJHdiypMysZDPlQk5Fch4/k0CgYAVeSZmejF5SXuHcCqXVQkl49wibc4VJECVX9PX3xRp0qxbHWby1LJwvsEgWfdNz9WJM4QohJDhjnE8lJId939HAmUe5EKS2sSwi1Ur2HVNrUbH8qPtH/soXyx/VzyQmZ7FjDBGw+lw6xVB6ZiuXuS9aejwl7BpdQTZ6NYLqSgLsQKBgHi/KARjxp9In6MA64eXmOzTcaxLcWv6WqMuCtRyBn2EzJIMoHDDd+RYO9EplPznCBOPUEHoH7lcRDVjjYOFayMZPb+YkMjJj/FQK+PmnXFju2MASrb4YZZ6VowvvavHQQdxHCUQgBCUS0IRXjO2j0SjIQd0uyQ1AmVrIdy8AP89AoGBAJibp9EtNXicrHCrWmL1vdFl5+YC5VNLcyZe1HSWDgdnuIDHjyiqU2ivaYsMOCRwit3asSJJ+6jQJP7K8Dp8RFDpWZyKjs879ZK3MjfZQGCWhKi6yHnOrVfreXo+3kLbkZ4dlhR+rXJu2LjrAIMro+lMRw1N+0LGRl6J88AEkJR2";

    public static final int SDK_PAY_FLAG = 1;
}
