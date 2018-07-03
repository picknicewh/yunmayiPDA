package com.yun.mayi.pda.widget;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

/**
 * 作者： wh
 * 时间：  2017/12/28
 * 名称：
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public class MyTextWatcher implements TextWatcher {
    private EditText editText;
    private OnTextChangedListener onTextChangedListener;
    public MyTextWatcher(EditText editText,OnTextChangedListener onTextChangedListener) {
        this.editText = editText;
         this.onTextChangedListener = onTextChangedListener;
    }
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        onTextChangedListener.onTextChanged(editText,s,start,before,count);
    }
    @Override
    public void afterTextChanged(Editable s) {}
    public  interface  OnTextChangedListener{
        void onTextChanged(EditText editText,CharSequence s, int start, int before, int count);
    }
}
