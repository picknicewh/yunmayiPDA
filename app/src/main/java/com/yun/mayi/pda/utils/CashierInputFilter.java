package com.yun.mayi.pda.utils;

import android.text.InputFilter;
import android.text.Spanned;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 作者： wh
 * 时间：  2017/8/2
 * 名称：金额输入框编辑过滤
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public class CashierInputFilter implements InputFilter {
    Pattern mPattern;
    //输入的最大金额
    private static final int MAX_VALUE = Integer.MAX_VALUE;
    //小数点后的位数
    private static final int POINTER_LENGTH = 2;
    private static final String POINTER = ".";
    private static final String ZERO = "0";
    public CashierInputFilter() {
        mPattern = Pattern.compile("([0-9]|\\.)*");
    }
    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        String sourceText = source.toString();
        String destText = dest.toString();

        //验证删除等按键
        if (G.isEmteny(sourceText)) {
            return "";
        }
        Matcher matcher = mPattern.matcher(source);
        if (!matcher.matches()){
            return "";
        }
        //已经输入小数点的情况下，只能输入数字
        if(destText.contains(POINTER)) {
            if (!matcher.matches()) {
                return "";
            } else {
                if (POINTER.equals(source)) { //只能输入一个小数点
                    return "";
                }
            }
            //验证小数点精度，保证小数点后只能输入两位
            int index = destText.indexOf(POINTER);
            int length = dend - index;
            if (length > POINTER_LENGTH) {
                return dest.subSequence(dstart, dend);
            }
        }
        //验证输入金额的大小
        double sumText = Double.parseDouble(destText + sourceText);
        if (sumText > MAX_VALUE) {
            return dest.subSequence(dstart, dend);
        }
        return dest.subSequence(dstart, dend) + sourceText;
    }
}
