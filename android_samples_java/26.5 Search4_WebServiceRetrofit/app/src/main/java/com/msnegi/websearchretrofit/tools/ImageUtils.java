package com.msnegi.websearchretrofit.tools;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.util.Base64;

import androidx.annotation.NonNull;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ImageUtils {

    public static String APP_IMAGE_FOLDER = "MyAllSamples";

    private static int getOrientation(String path) {
        int rotate = 0;

        try {
            File imageFile = new File(path);
            ExifInterface exif = new ExifInterface(
                    imageFile.getAbsolutePath());
            int orientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_270:
                    rotate = 270;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    rotate = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_90:
                    rotate = 90;
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rotate;
    }

    private static Bitmap getBitmap(@NonNull Uri uri, @NonNull Context mcontext, int rotate) {

        InputStream in = null;
        try {
            // final int IMAGE_MAX_SIZE = 1200000; // 1.2MP
            final int IMAGE_MAX_SIZE = 120000;
            in = mcontext.getContentResolver().openInputStream(uri);

            // Decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(in, null, o);
            in.close();

            int scale = 1;
            while ((o.outWidth * o.outHeight) * (1 / Math.pow(scale, 2)) >
                    IMAGE_MAX_SIZE) {
                scale++;
            }
            Bitmap b = null;
            in = mcontext.getContentResolver().openInputStream(uri);
            if (scale > 1) {
                scale--;
                // scale to max possible inSampleSize that still yields an image
                // larger than target
                o = new BitmapFactory.Options();
                o.inSampleSize = scale;
                b = BitmapFactory.decodeStream(in, null, o);

                // resize to desired dimensions
                int height = b.getHeight();
                int width = b.getWidth();
                double y = Math.sqrt(IMAGE_MAX_SIZE
                        / (((double) width) / height));
                double x = (y / height) * width;

                Bitmap scaledBitmap = Bitmap.createScaledBitmap(b, (int) x,
                        (int) y, true);
                if (rotate != 0) {
                    Matrix matrix = new Matrix();
                    matrix.postRotate(rotate);
                    scaledBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0,
                            scaledBitmap.getWidth(),
                            scaledBitmap.getHeight(), matrix, true);
                }
//                b.recycle();
                return scaledBitmap;

//                System.gc();
            } else {
                b = BitmapFactory.decodeStream(in);
            }
            in.close();

            return b;
        } catch (Exception e) {
            return null;
        }
    }

    public static String getBase64String(Bitmap bm) {
        // picture.setImageBitmap(bm);
        if(bm!=null) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.JPEG, 100, baos); //bm is the bitmap object
            byte[] b = baos.toByteArray();
            return Base64.encodeToString(b, Base64.DEFAULT);
        }
        return null;
    }

    public static byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }

    public static String getBase64StringFromBitmap(Bitmap bm) {
        if(bm!=null) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.JPEG, 90, baos);
            byte[] imageInByte = baos.toByteArray();
            long lengthbmp = imageInByte.length / 1024;
            LogManager.d("ImageSize", "In KB = " + lengthbmp);
            return Base64.encodeToString(imageInByte, Base64.DEFAULT);
        }
        return null;
    }

    public static Bitmap getBitmapFromPath(Context ctx, String path) {
        Bitmap dynamicBitmapimage = null;
        try {
            File dynamicImage = new File(path);
            dynamicBitmapimage = getBitmap(Uri.fromFile(dynamicImage), ctx, getOrientation(dynamicImage.getAbsolutePath()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dynamicBitmapimage;
    }


    public static String saveToInternalStorage(Context context, Bitmap bitmapImage, String imageId) {
        ContextWrapper cw = new ContextWrapper(context.getApplicationContext());
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir(APP_IMAGE_FOLDER, Context.MODE_PRIVATE);
        // Create imageDir
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mypath = new File(directory, "RetailerImage" + timeStamp + imageId + ".jpg");

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 80, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return mypath.getPath();
    }


    public static void saveToInternalStorage(Bitmap bitmapImage, String path) {
        File mypath = new File(path);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 90, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static Bitmap decodeFile(String path) {
        String strMyImagePath = null;
        Bitmap scaledBitmap = null;

        try {
            // Part 1: Decode image

            Bitmap unscaledBitmap = ScalingUtilities.decodeFile(path, 2160, 3840, ScalingUtilities.ScalingLogic.FIT);
            if (unscaledBitmap.getWidth() >= 2160 && unscaledBitmap.getHeight() >= 3840) {
                // Part 2: Scale image
                scaledBitmap = ScalingUtilities.createScaledBitmap(unscaledBitmap, 2160, 3840, ScalingUtilities.ScalingLogic.FIT);
            } else if (unscaledBitmap.getWidth() >= 1440 && unscaledBitmap.getHeight() >= 2560) {
                // Part 2: Scale image
                scaledBitmap = ScalingUtilities.createScaledBitmap(unscaledBitmap, 1440, 2560, ScalingUtilities.ScalingLogic.FIT);
            } else if (unscaledBitmap.getWidth() >= 1080 && unscaledBitmap.getHeight() >= 1920) {
                // Part 2: Scale image
                scaledBitmap = ScalingUtilities.createScaledBitmap(unscaledBitmap, 1080, 1920, ScalingUtilities.ScalingLogic.FIT);
            } else {
                scaledBitmap = ScalingUtilities.createScaledBitmap(unscaledBitmap, unscaledBitmap.getWidth(), unscaledBitmap.getHeight(), ScalingUtilities.ScalingLogic.FIT);
            }
            if (scaledBitmap != null) {
                return scaledBitmap;
            }
        } catch (Exception e) {

        }
        return null;
    }

}
