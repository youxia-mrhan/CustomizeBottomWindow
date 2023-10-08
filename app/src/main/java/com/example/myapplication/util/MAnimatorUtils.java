package com.example.myapplication.util;

import android.animation.LayoutTransition;
import android.animation.ObjectAnimator;

/**
 * 动画工具类
 */
public class MAnimatorUtils {

    public static LayoutTransition mLayoutTransition(int duration) {
        ObjectAnimator addAnimator = ObjectAnimator.ofFloat(null, "translationX", 1).
                setDuration(duration);
        LayoutTransition layoutTransition = new LayoutTransition();
        layoutTransition.setAnimator(LayoutTransition.APPEARING, addAnimator);

        return layoutTransition;
    }

}
