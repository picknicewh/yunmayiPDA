package com.yun.mayi.pda.module.join.reject;

import com.yun.mayi.pda.base.BaseContract;
import com.yun.mayi.pda.bean.RejectVo;

import java.util.List;

/**
 * 作者： wh
 * 时间：  2018/3/16
 * 名称：退货审核详情页
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public interface RejectCheckDetailContract
{
   interface View extends BaseContract.View{
       /**
        * 开始时间
        */
       String getBeginTime();
       /**
        * 结束时间
        */
       String getEndTime();
       /**
        * 业务员ID
        */
       int getSalesmanId();


       /**
        *  状态，0全部，3未审核，4已打回，5成功退货
        */
       int getState();
       /**
        * 关键字
        */
       String getKeyWord();
       /**
        * 页码
        */
       int getPage();
       /**
        * 备注
        */
       String getRemark();
       /**
        * 退货ID列表，用英文逗号','隔开
        */
       String getIdStr();
       /**
        * 退货数量，用英文逗号','隔开
        */
       String getNumStr();
       /**
        * 退货金额，用英文逗号','隔开
        */
       String getFeeStr();
       void setRejectVoList(List<RejectVo> rejectVoList);
   }
   interface Presenter extends BaseContract.Presenter<View>{
       void getRejectCheckList();
       void rejectWareHouse();
       void passWareHouse();
   }

}
