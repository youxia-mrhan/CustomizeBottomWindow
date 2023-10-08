package com.example.myapplication.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication.R;


/**
 * 搜索栏
 */
public class SearchBarHo extends FrameLayout {

    private View searchBar;

    public SearchBarHo(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {
        searchBar = View.inflate(getContext(), R.layout.widget_search_bar_ho, null);
        addView(searchBar);

        TextView searchEdit = searchBar.findViewById(R.id.search_edit);
        searchEdit.setText("请输入搜索内容");
    }

}
