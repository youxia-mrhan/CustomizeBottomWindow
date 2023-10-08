package com.example.myapplication.widget;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication.R;


/**
 * 顶部 返回bar
 */
public class TopBackBar extends FrameLayout {

    private final String title;

    public TopBackBar(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TopBackBar);
        title = typedArray.getString(R.styleable.TopBackBar_title);
        typedArray.recycle();

        initView();
    }

    private void initView() {
        View topBackBar = View.inflate(getContext(), R.layout.widget_top_back_bar_ba, null);
        addView(topBackBar);

        TextView backTitle = (TextView) topBackBar.findViewById(R.id.back_title);
        backTitle.setText(title);

        View backBtn = topBackBar.findViewById(R.id.back_icon_box);

        backBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity activity = (Activity) getContext();
                activity.finish();
            }
        });
    }

}
