package psd.email;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;

/**
 * Created by pengsida on 2017/4/18.
 */

public class PictureUtils
{
    public static Bitmap getScaledBitmap(String path, int destWidth, int destHeight)
    {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);

        float srcwidth = options.outWidth;
        float srcHeight = options.outHeight;

        int inSampleSize = 1;
        if (srcHeight > destHeight || srcwidth > destWidth)
        {
            if (srcwidth > srcHeight)
                inSampleSize = Math.round(srcHeight / destHeight);
            else
                inSampleSize = Math.round(srcwidth / destWidth);
        }

        options = new BitmapFactory.Options();
        options.inSampleSize = inSampleSize;
        return BitmapFactory.decodeFile(path, options);
    }

    public static Bitmap getScaleBitmap(String path, Activity activity)
    {
        Point size = new Point();
        activity.getWindowManager().getDefaultDisplay().getSize(size);
        return getScaledBitmap(path, size.x, size.y);
    }
}
