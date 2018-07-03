package com.yun.mayi.pda.utils;

import java.text.DecimalFormat;

/**
 * Created by ys on 2017/6/13.
 * 转换金钱格式
 */

public class PriceTransfer {

    public static final String chageMoneyToString(Double money){
        if (money != null){
            DecimalFormat decimalFormat = new DecimalFormat("######0.00");
            Double nowMoney = money/100;
            return decimalFormat.format(nowMoney);
        }else{
            return  null;
        }
    }
    public static final String changePersent(Double money){
        if (money != null){
            DecimalFormat decimalFormat = new DecimalFormat("######0.0");
            return decimalFormat.format(money);
        }else{
            return  null;
        }
    }
}
