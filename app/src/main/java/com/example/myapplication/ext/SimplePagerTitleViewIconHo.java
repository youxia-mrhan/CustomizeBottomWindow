package com.example.myapplication.ext;

import android.content.Context;
import android.view.Gravity;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.StyleRes;

import com.example.myapplication.util.DisplayUtils;

import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;

/**
 * 扩展 IMeasurablePagerTitleView文本
 * 选中字体加粗 + 图标
 */
public class SimplePagerTitleViewIconHo extends SimplePagerTitleViewIconEx {

    private SimpleParameter simpleParameter;

    private TextView titleText;

    private ImageView icon;

    // 点击了图标，显示不同的图标
    // true：packUpBlackIconResId
    // false：selectedIconResId
    private boolean clickIcon = false;

    // 是否是通过触摸 触发的事件(这个很重要！！！)
    // 因为 MagicIndicator 源码，为了初始化触发Selected，layout中也写了，
    // 导致view重新绘制的时候，会触发onSelected 方法
    //    @Override
    //    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
    //        ...
    //        if (mReselectWhenLayout && mNavigatorHelper.getScrollState() == ScrollState.SCROLL_STATE_IDLE) {
    //            onPageSelected(mNavigatorHelper.getCurrentIndex());
    //            onPageScrolled(mNavigatorHelper.getCurrentIndex(), 0.0f, 0);
    //        }
    //    }
    private boolean isOnTouch = false;

    // 是否是初始化
    private boolean initLayout = false;

    public SimplePagerTitleViewIconHo(Context context, SimpleParameter simpleParameter) {
        super(context);
        this.simpleParameter = simpleParameter;
        initView();
    }

    public void reset() {
        clickIcon = false;
        icon.setImageResource(simpleParameter.selectedIconResId);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                isOnTouch = true;
                if (event.getX() >= icon.getLeft() && event.getX() <= icon.getRight() &&
                        event.getY() >= icon.getTop() && event.getY() <= icon.getBottom()) {
                    simpleParameter.callbackOnClick.iconClick(simpleParameter.tag, clickIcon);
                    clickIcon = !clickIcon;

                    if (!clickIcon) {
                        icon.setImageResource(simpleParameter.selectedIconResId);
                    } else if (clickIcon) {
                        icon.setImageResource(simpleParameter.packUpBlackIconResId);
                    }
                }
                return true;
        }
        return true;
    }

    private void initView() {
        setGravity(Gravity.CENTER);

        titleText = new TextView(getContext());
        addView(titleText);
        LayoutParams textParams = (LayoutParams) titleText.getLayoutParams();
        textParams.setMarginStart(DisplayUtils.dp2px(getContext(), 20));

        icon = new ImageView(getContext());
        addView(icon);
        icon.setPadding(
                DisplayUtils.dp2px(getContext(), 2),
                DisplayUtils.dp2px(getContext(), 5),
                DisplayUtils.dp2px(getContext(), 10),
                DisplayUtils.dp2px(getContext(), 5));

        titleText.setText(simpleParameter.title);
    }

    public boolean isClickIcon() {
        return clickIcon;
    }

    @Override
    public void onDeselected(int index, int totalCount) {
        titleText.setTextAppearance(simpleParameter.normalFontStyleResId);
        icon.setImageResource(simpleParameter.normalIconResId);
        clickIcon = false;
    }

    @Override
    public void onSelected(int index, int totalCount, int previousIndex, IPagerTitleView titleView) {
        if (isOnTouch || !initLayout) {
            SimplePagerTitleViewIconHo view = (SimplePagerTitleViewIconHo) titleView;

            if (view.isClickIcon()) {
                clickIcon = view.isClickIcon();
            }

            // Log.d("TAG", "onSelected------index：" + index + "totalCount：" + totalCount + "previousIndex：" + previousIndex);
            titleText.setTextAppearance(simpleParameter.selectedFontStyleResId);

            // 情况一：点击标题，不显示下拉框
            // 情况二：点击下拉图标，显示下拉框，显示上拉图标
            // 情况三：当前显示了下拉框，点击了标题，切换下拉框内容
            // 情况四：点击了上拉图标，收起
            // 情况五：点击了其他title，当前恢复默认状态

            if (!clickIcon) {
                icon.setImageResource(simpleParameter.selectedIconResId);
            } else if (clickIcon) {
                icon.setImageResource(simpleParameter.packUpBlackIconResId);
            }
            simpleParameter.callbackOnClick.titleClick(index, clickIcon);

            isOnTouch = false;
            initLayout = true;
        }
    }

    public static class SimpleParameter {

        // 标题文本
        private String title;

        // 唯一标识
        private Object tag;

        // 选中时字体的样式
        private @StyleRes int selectedFontStyleResId;

        // 未中时字体的样式
        private @StyleRes int normalFontStyleResId;

        // 选中时的图标
        private @DrawableRes int selectedIconResId;

        // 未选中时的图标
        private @DrawableRes int normalIconResId;

        // 收起下拉图标
        private @DrawableRes int packUpBlackIconResId;

        private CallbackOnClick callbackOnClick;

        public SimpleParameter(String title,
                               Object tag,
                               int selectedFontStyleResId,
                               int normalFontStyleResId,
                               int selectedIcon,
                               int normalIcon,
                               int packUpBlackIconResId,
                               CallbackOnClick callbackOnClick) {
            this.title = title;
            this.tag = tag;
            this.selectedFontStyleResId = selectedFontStyleResId;
            this.normalFontStyleResId = normalFontStyleResId;
            this.selectedIconResId = selectedIcon;
            this.normalIconResId = normalIcon;
            this.packUpBlackIconResId = packUpBlackIconResId;
            this.callbackOnClick = callbackOnClick;
        }

        public static abstract class CallbackOnClick {

            // 点击图标
            public abstract void iconClick(Object tag, boolean clickIcon);

            // 点击标题
            public abstract void titleClick(Object tag, boolean clickIcon);

        }

    }
}