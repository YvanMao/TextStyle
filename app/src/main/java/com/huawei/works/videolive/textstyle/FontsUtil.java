package com.huawei.works.videolive.textstyle;

import android.content.Context;
import android.graphics.Typeface;


public class FontsUtil {

    public static FontsUtil fontsUtil;

    private Context mContext;
    private static Typeface charTypeface;

    public FontsUtil(Context context) {
        this.mContext = context;
        charTypeface = Typeface.createFromAsset(mContext.getAssets(), "fonts/048-CAT978.ttf");

    }

    /**
     * 概述：字体单例，避免反复读取
     *
     * @param context
     * @return
     */
    public static FontsUtil getInstance(Context context) {
        if (fontsUtil == null) {
            fontsUtil = new FontsUtil(context);
        }
        return fontsUtil;
    }

    /**
     * 概述：获取英文字母的字体typefacespan
     *
     * @return
     */
    public MyTypefaceSpan getMyCharTypefaceSpan() {
        return new MyTypefaceSpan(charTypeface);
    }


}
