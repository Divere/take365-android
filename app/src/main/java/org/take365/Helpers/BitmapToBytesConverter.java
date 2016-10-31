package org.take365.Helpers;

import android.graphics.Bitmap;

import java.io.ByteArrayOutputStream;

/**
 * Created by divere on 31/10/2016.
 */

public class BitmapToBytesConverter {
    public static byte[] convert(Bitmap bitmap) {
        ByteArrayOutputStream imageData = new ByteArrayOutputStream();
        if (bitmap != null) {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, imageData);
        }
        return imageData.toByteArray();
    }
}
