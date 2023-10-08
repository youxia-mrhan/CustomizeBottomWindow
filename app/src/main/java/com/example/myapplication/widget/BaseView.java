package com.example.myapplication.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


public class BaseView extends FrameLayout {

    // 初始化，确保只执行一次
    private boolean initFlag = false;

    public BaseView(@NonNull Context context) {
        super(context);
    }

    public BaseView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (!initFlag) {
                    initData();
                    initFlag = true;
                }
            }
        });
    }

    // 只执行一次
    public void initData() {}

}
