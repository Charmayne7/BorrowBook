package com.example.charm.borrowbook;

import android.annotation.TargetApi;
import android.content.ContentResolver;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.util.Log;

import java.io.IOException;

public class BitmapResolver {
    private final static String TAG = "BitmapResolver";

    @SuppressWarnings("deprecation")
    private static Bitmap getBitmapLegacy(@NonNull ContentResolver contentResolver, @NonNull Uri fileUri){
        Bitmap bitmap = null;

        try {
            bitmap = MediaStore.Images.Media.getBitmap(contentResolver, fileUri);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bitmap;
    }

    @TargetApi(Build.VERSION_CODES.P)
    private static Bitmap getBitmapImageDecoder(@NonNull ContentResolver contentResolver, @NonNull Uri fileUri){
        Bitmap bitmap = null;

        try {
            bitmap = ImageDecoder.decodeBitmap(ImageDecoder.createSource(contentResolver, fileUri));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bitmap;
    }

    public static Bitmap getBitmap(@NonNull ContentResolver contentResolver, Uri fileUri){
        if (fileUri == null){
            Log.i(TAG, "returning null because URI was null");
            return null;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P){
            return getBitmapImageDecoder(contentResolver, fileUri);
        } else{
            return getBitmapLegacy(contentResolver, fileUri);
        }
    }
}
