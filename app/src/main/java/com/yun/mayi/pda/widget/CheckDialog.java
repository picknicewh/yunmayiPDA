package com.yun.mayi.pda.widget;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.yun.mayi.pda.R;
import com.yun.mayi.pda.utils.G;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 作者： wh
 * 时间：  2017/12/28
 * 名称：
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public class CheckDialog extends AlertDialog implements MyTextWatcher.OnTextChangedListener {
    /**
     * 第一个密码
     */
    @BindView(R.id.et_password_one)
    EditText etPasswordOne;
    /**
     * 第二个密码
     */
    @BindView(R.id.et_password_two)
    EditText etPasswordTwo;
    /**
     * 第三个密码
     */
    @BindView(R.id.et_password_three)
    EditText etPasswordThree;
    /**
     * 第四个密码
     */
    @BindView(R.id.et_password_four)
    EditText etPasswordFour;
    /**
     * 回调监听
     */
    private onPassWordCatchListener onPassWordCatchListener;

    public CheckDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_check);
        setCanceledOnTouchOutside(false);
        ButterKnife.bind(this);
        etPasswordOne.addTextChangedListener(new MyTextWatcher(etPasswordOne, this));
        etPasswordTwo.addTextChangedListener(new MyTextWatcher(etPasswordTwo, this));
        etPasswordThree.addTextChangedListener(new MyTextWatcher(etPasswordThree, this));
        etPasswordFour.addTextChangedListener(new MyTextWatcher(etPasswordFour, this));
    }

    @OnClick({R.id.tv_cancel, R.id.tv_conform})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.tv_cancel:
                clean();
                break;
            case R.id.tv_conform:
                String one = etPasswordOne.getText().toString();
                String two = etPasswordTwo.getText().toString();
                String three = etPasswordThree.getText().toString();
                String four = etPasswordFour.getText().toString();
                if (G.isEmteny(one) || G.isEmteny(two) || G.isEmteny(three) || G.isEmteny(four)) {
                    G.showToast(getContext(), "请输入4位数密码");
                    return;
                }
                if (onPassWordCatchListener != null) {
                    String password = one + two + three + four;
                    onPassWordCatchListener.onCatch(password);
                }
                break;
        }
        dismiss();
    }

    public void clean() {
        etPasswordOne.setText("");
        etPasswordTwo.setText("");
        etPasswordThree.setText("");
        etPasswordFour.setText("");
        etPasswordOne.setFocusable(true);
        etPasswordOne.requestFocus();
    }

    @Override
    public void onTextChanged(EditText editText, CharSequence s, int start, int before, int count) {
        if (!G.isEmteny(editText.getText().toString())) {
            switch (editText.getId()) {
                case R.id.et_password_one:
                    etPasswordTwo.setFocusable(true);
                    etPasswordTwo.requestFocus();
                    break;
                case R.id.et_password_two:
                    etPasswordThree.setFocusable(true);
                    etPasswordThree.requestFocus();
                    break;
                case R.id.et_password_three:
                    etPasswordFour.setFocusable(true);
                    etPasswordFour.requestFocus();
                    break;
                case R.id.et_password_four:
                    etPasswordFour.setFocusable(true);
                    etPasswordFour.requestFocus();
                    break;
            }
        }
    }

    public void setOnPassWordCatchListener(onPassWordCatchListener onPassWordCatchListener) {
        this.onPassWordCatchListener = onPassWordCatchListener;
    }

    public interface onPassWordCatchListener {
        void onCatch(String password);
    }
}
