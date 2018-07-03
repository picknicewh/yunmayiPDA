package com.yun.mayi.pda.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * 作者： wh
 * 时间：  2018/1/17
 * 名称：
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public class Text  extends View{

    public Text(Context context) {
        super(context);
    }

    public Text(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Text(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    private Paint paint = new Paint();
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


    }
}
