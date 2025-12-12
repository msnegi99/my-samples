package com.example.mediapickersample;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.BaseColumns;
import android.provider.MediaStore;
import android.widget.Toast;

import java.io.File;
import java.io.OutputStream;
import java.util.Objects;

public class BitmapUtils {

    private static final String SELECTION = MediaStore.MediaColumns.DATA + "=?";
    private static final String[] PROJECTION = { BaseColumns._ID };

    public static Bitmap loadVideoThumbnail(String videoFilePath, ContentResolver cr) {
        Bitmap bitmap = null;
        Uri uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
        String[] selectionArgs = { videoFilePath };
        Cursor cursor = cr.query(uri, PROJECTION, SELECTION, selectionArgs, null);
        if (cursor.moveToFirst()) {
            // it's the only & first thing in projection, so it is 0
            long videoId = cursor.getLong(0);
            bitmap = MediaStore.Video.Thumbnails.getThumbnail(cr, videoId, MediaStore.Images.Thumbnails.MICRO_KIND, null);
        }
        cursor.close();
        return bitmap;
    }

    public static boolean saveImageToGallery(Context context, Bitmap bitmap, String name) {
        OutputStream fos;
        boolean status = false;

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                ContentResolver resolver = context.getContentResolver();
                ContentValues contentValues = new ContentValues();
                contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, "Image_" + name + ".jpg");
                contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg");
                contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES + File.separator + "TestFolder");
                Uri imageUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);

                fos = (OutputStream) resolver.openOutputStream(Objects.requireNonNull(imageUri));

                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                Objects.requireNonNull(fos);

                //Toast.makeText(this, "Image Saved", Toast.LENGTH_SHORT).show();
                status = true;
            }
        } catch (Exception e) {
            //Toast.makeText(this, "Image Not Saved \n" + e.getMessage(), Toast.LENGTH_SHORT).show();
            status = false;
        }

        return status;
    }
}
