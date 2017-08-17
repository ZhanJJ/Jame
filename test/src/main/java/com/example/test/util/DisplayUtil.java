package com.example.test.util;

import android.content.Context;

/**
 * Created by James on 2016/8/24.
 */
public class DisplayUtil {
    public static int dp2px(Context context, float dipValue) {
        float scale = context.getResources().getDisplayMetrics().density;
//        TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dipValue, context.getResources().getDisplayMetrics());
        return (int) (dipValue * scale + 0.5f);
    }

    public static int px2dp(Context context, float pxValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static int sp2px(Context context, float spValue) {
        float scale = context.getResources().getDisplayMetrics().scaledDensity;
//        TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spValue, context.getResources().getDisplayMetrics());
        return (int) (spValue * scale + 0.5f);
    }

    public static int px2sp(Context context, float pxValue) {
        float scale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / scale + 0.5f);
    }
}
