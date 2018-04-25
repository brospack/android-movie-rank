package kr.ry4nkim.movierank.Utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class Utils {

    // Loading Optimized Images
    public static Bitmap loadImage(URL url) {
        // First decode with inJustDecodeBounds=true to check dimensions
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, 100, 100);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        try {
            return BitmapFactory.decodeStream(url.openConnection().getInputStream(), null, options);
        } catch (IOException e) {

        }
        return null;
    }

    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        int height = options.outHeight;
        int width = options.outWidth;
        int inSampleSize = 2;

        if (height > reqHeight || width > reqWidth) {
            int halfHeight = height / 2;
            int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    public static Bitmap cropCenterBitmap(Bitmap src, int w, int h) {
        if (src == null) {
            return null;
        }

        int width = src.getWidth();
        int height = src.getHeight();

        if (width < w && height < h) {
            return src;
        }

        int x = 0;
        int y = 0;

        if (width > w) {
            x = (width - w) / 2;
        }

        if (height > h) {
            y = (height - h) / 2;
        }

        int cw = w;
        int ch = h;

        if (w > width) {
            cw = width;
        }

        if (h > height) {
            ch = height;
        }

        return Bitmap.createBitmap(src, x, y, cw, ch);
    }

    public static List sortByValue(final Map map) {
        List<String> list = new ArrayList();
        list.addAll(map.keySet());

        Collections.sort(list,new Comparator() {

            public int compare(Object o1,Object o2) {
                Object v1 = map.get(o1);
                Object v2 = map.get(o2);

                return ((Comparable) v2).compareTo(v1);
            }

        });
        Collections.reverse(list);
        return list;
    }

}
