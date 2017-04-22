package psd.email;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LevelListDrawable;
import android.os.AsyncTask;
import android.text.Html;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by pengsida on 2017/4/17.
 */

public class HtmlImageGetter implements Html.ImageGetter
{
    @Override
    public Drawable getDrawable(String source) {
        LevelListDrawable d = new LevelListDrawable();
//        Drawable empty = getResources().getDrawable(
//                R.drawable.image_horizontal);
//        d.addLevel(0, 0, empty);
//        d.setBounds(0, 0, PhoneUtils.getScreenWidth(mContext),
//                empty.getIntrinsicHeight());
        new LoadImage().execute(source, d);
        return d;
    }

    class LoadImage extends AsyncTask<Object, Void, Bitmap>
    {
        private LevelListDrawable mDrawable;

        @Override
        protected Bitmap doInBackground(Object... params) {
            String source = (String) params[0];
            mDrawable = (LevelListDrawable) params[1];
            try {
                InputStream is = new URL(source).openStream();
                return BitmapFactory.decodeStream(is);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (bitmap != null) {
                BitmapDrawable d = new BitmapDrawable(bitmap);
                mDrawable.addLevel(1, 1, d);

                /**
                 * 适配图片大小 <br/>
                 * 默认大小：bitmap.getWidth(), bitmap.getHeight()<br/>
                 * 适配屏幕：getDrawableAdapter
                 */
//                mDrawable = getDrawableAdapter(mContext, mDrawable,
//                        bitmap.getWidth(), bitmap.getHeight());

                // mDrawable.setBounds(0, 0, bitmap.getWidth(),
                // bitmap.getHeight());

                mDrawable.setLevel(1);

            }
        }
    }
}
