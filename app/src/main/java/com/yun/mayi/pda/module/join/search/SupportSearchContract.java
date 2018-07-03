package com.yun.mayi.pda.module.join.search;

import com.yun.mayi.pda.base.BaseContract;
import com.yun.mayi.pda.bean.SalemanVo;
import com.yun.mayi.pda.bean.VendorInfo;

import java.util.List;

/**
 * 作者： wh
 * 时间：  2018/3/16
 * 名称：
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public interface SupportSearchContract {
    interface View extends BaseContract.View {
        String getToken();

        String getAgentNumber();

        void setSaleManList(List<SalemanVo> saleManList);

        void setVendorInfoList(List<VendorInfo> vendorInfoList);
    }

    interface Pesenter extends BaseContract.Presenter<View> {
        void getSalesmansByToken();

        void getVendorInfoByAgentNumber();
    }
}
