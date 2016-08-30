package com.challenge.n26.nikitabitcoin.misc.util;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.util.TypedValue;

/**
 * Created by 805640 on 30.08.2016.
 */

public class ResUtils {
    @ColorInt
    public static int getThemeColor
            (
                    @NonNull final Context context,
                    @AttrRes final int attributeColor
            )
    {
        final TypedValue value = new TypedValue();
        context.getTheme().resolveAttribute(attributeColor, value, true);
        return value.data;
    }

}
