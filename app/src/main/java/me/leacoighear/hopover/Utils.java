package me.leacoighear.hopover;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created by Tom on 11/03/2015.
 */
public class Utils {
    private static final int DEFAULT_DENSITY = 1024;

    public static Bitmap getDownScaledBitmapAlpha8(Context context, int id) {
        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
        bitmapOptions.inPreferredConfig = Bitmap.Config.ALPHA_8;
        bitmapOptions.inScaled = true;
        bitmapOptions.inDensity = DEFAULT_DENSITY;
        bitmapOptions.inTargetDensity = DEFAULT_DENSITY;
        Bitmap b = BitmapFactory.decodeResource(context.getResources(), id, bitmapOptions);
        b.setDensity(context.getResources().getDisplayMetrics().densityDpi);
        return b;
    }

    public static Bitmap getScaledBitmapAlpha8(Context context, int id) {
        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
        bitmapOptions.inPreferredConfig = Bitmap.Config.ALPHA_8;
        bitmapOptions.inScaled = true;
        bitmapOptions.inDensity = DEFAULT_DENSITY;
        bitmapOptions.inTargetDensity = (int) (getScaleFactor(context) * DEFAULT_DENSITY);
        Bitmap b = BitmapFactory.decodeResource(context.getResources(), id, bitmapOptions);
        b.setDensity(context.getResources().getDisplayMetrics().densityDpi);
        return b;
    }

    private static float getScaleFactor(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels / 512f;
    }
}
