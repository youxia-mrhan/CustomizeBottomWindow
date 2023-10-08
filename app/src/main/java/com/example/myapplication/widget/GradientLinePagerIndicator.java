package com.example.myapplication.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;

import com.example.myapplication.ext.LinePagerIndicatorEx;

/**
 * 渐变指示器
 */
public class GradientLinePagerIndicator extends LinePagerIndicatorEx {

    private int startColor;

    private int endColor;

    public GradientLinePagerIndicator(Context context, int startColor, int endColor) {
        super(context);
        this.startColor = startColor;
        this.endColor = endColor;
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        LinearGradient lg = new LinearGradient(getLineRect().left, getLineRect().top, getLineRect().right, getLineRect().bottom, new int[]{startColor, endColor}, null, LinearGradient.TileMode.CLAMP);
        getPaint().setShader(lg);
        canvas.drawRoundRect(getLineRect(), getRoundRadius(), getRoundRadius(), getPaint());
    }
}
