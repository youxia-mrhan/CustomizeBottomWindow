package com.example.myapplication.base;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.myapplication.R;
import com.example.myapplication.util.DisplayUtils;
import com.example.myapplication.util.ShapeUtils;
import com.example.myapplication.widget.BaseView;

/**
 * 下拉框 选中和未选中 样式
 */
public class DownBaseView extends BaseView {

    public DownBaseView(@NonNull Context context) {
        super(context);
    }

    // 选中按钮样式
    public TextView selectedBtn(TextView sortBtn) {
        sortBtn.setPadding(
                DisplayUtils.dp2px(getContext(), 16),
                DisplayUtils.dip2px(getContext(), 6.5f),
                DisplayUtils.dp2px(getContext(), 16),
                DisplayUtils.dip2px(getContext(), 6.5f));
        sortBtn.setTextAppearance(R.style.Font_FFFFFF_13);

        GradientDrawable drawable = ShapeUtils.newShape()
                .setCornerRadius(DisplayUtils.dp2px(getContext(), 16))
                .setColor(getResources().getColor(R.color.color_F9230A, null))
                .getDrawable();
        sortBtn.setBackground(drawable);

        return sortBtn;
    }

    // 未选中按钮样式
    public TextView noSelectedBtn(TextView sortBtn) {
        sortBtn.setPadding(
                DisplayUtils.dp2px(getContext(), 16),
                DisplayUtils.dip2px(getContext(), 6.5f),
                DisplayUtils.dp2px(getContext(), 16),
                DisplayUtils.dip2px(getContext(), 6.5f));
        sortBtn.setTextAppearance(R.style.Font_303133_13);

        GradientDrawable drawable = ShapeUtils.newShape()
                .setCornerRadius(DisplayUtils.dp2px(getContext(), 16))
                .setColor(getResources().getColor(R.color.color_F5F6F7, null))
                .getDrawable();
        sortBtn.setBackground(drawable);

        return sortBtn;
    }

    // 选中字体 + 图标
    public TextView selectedIconText(TextView sortDownText) {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                DisplayUtils.dp2px(getContext(), 48)
        );
        sortDownText.setLayoutParams(layoutParams);

        sortDownText.setGravity(Gravity.CENTER);

        sortDownText.setTextAppearance(R.style.Font_F9230A_15);
        sortDownText.setCompoundDrawablesWithIntrinsicBounds(
                null, null,
                getResources().getDrawable(R.mipmap.jump_red, null), null);
        return sortDownText;
    }

    // 选中字体
    public TextView selectedText(TextView sortDownText) {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                DisplayUtils.dp2px(getContext(), 48)
        );
        sortDownText.setLayoutParams(layoutParams);
        sortDownText.setGravity(Gravity.CENTER_VERTICAL);

        sortDownText.setTextAppearance(R.style.Font_F9230A_15);
        return sortDownText;
    }

    // 未选中字体
    public TextView noSelectedText(TextView sortDownText) {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                DisplayUtils.dp2px(getContext(), 48)
        );
        sortDownText.setLayoutParams(layoutParams);
        sortDownText.setGravity(Gravity.CENTER_VERTICAL);
        sortDownText.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);

        sortDownText.setTextAppearance(R.style.Font_303133_15);
        return sortDownText;
    }

}
