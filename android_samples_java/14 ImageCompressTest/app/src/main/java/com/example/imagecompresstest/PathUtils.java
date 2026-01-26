package com.example.imagecompresstest;

import android.annotation.SuppressLint;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

public class PathUtils {

    Context context;

    @SuppressLint("NewApi")
    public Uri getLocalPath(Context context, Uri uri)  {
        this.context = context;

        Uri realUri = uri;
        final boolean needToCheckUri = Build.VERSION.SDK_INT >= 19;
        String selection = null;
        String[] selectionArgs = null;
        String type = null;
        Uri finalUri = null;
        // Uri is different in versions after KITKAT (Android 4.4), we need to
        // deal with different Uris.
        if (needToCheckUri && DocumentsContract.isDocumentUri(context.getApplicationContext(), uri)) {
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                return Uri.parse(Environment.getExternalStorageDirectory() + "/" + split[1]);
            } else if (isDownloadsDocument(uri)) {
                //final String id = DocumentsContract.getDocumentId(uri).replace("raw:", "");
                //uri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
                final String id;
                Cursor cursor = null;
                try {
                    cursor = context.getContentResolver().query(uri, new String[]{MediaStore.MediaColumns.DISPLAY_NAME}, null, null, null);
                    if (cursor != null && cursor.moveToFirst()) {
                        String fileName = cursor.getString(0);
                        String path = Environment.getExternalStorageDirectory().toString() + "/Download/" + fileName;
                        if (!TextUtils.isEmpty(path)) {
                            return Uri.parse(path);     //-- local file path
                        }
                    }
                    if (cursor != null) cursor.close();
                    id = DocumentsContract.getDocumentId(uri);
                    if (!TextUtils.isEmpty(id)) {
                        if (id.startsWith("raw:")) {
                            return Uri.parse(id.replaceFirst("raw:", ""));
                        }
                        String[] contentUriPrefixesToTry = new String[]{
                                "content://downloads/public_downloads",
                                "content://downloads/my_downloads"
                        };
                        for (String contentUriPrefix : contentUriPrefixesToTry) {
                            try {
                                final Uri contentUri = ContentUris.withAppendedId(Uri.parse(contentUriPrefix), Long.valueOf(id));
                                return Uri.parse(getDataColumn(context, contentUri, null, null));
                            } catch (NumberFormatException e) {
                                //In Android 8 and Android P the id is not a number
                                return Uri.parse(uri.getPath().replaceFirst("^/document/raw:", "").replaceFirst("^raw:", ""));
                            }
                        }
                    }
                }catch (Exception e){}
            } else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                type = split[0];

                selection = "_id=?";
                selectionArgs = new String[]{split[1]};
                String[] projection = {MediaStore.Images.Media.DATA};

                if ("image".equals(type)) {
                    finalUri = getActualPath(context, MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection, selectionArgs, projection);
                } else if ("video".equals(type)) {
                    finalUri = getActualPath(context, MediaStore.Video.Media.EXTERNAL_CONTENT_URI, selection, selectionArgs, projection);
                } else if ("audio".equals(type)) {
                    finalUri = getActualPath(context, MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, selection, selectionArgs, projection);
                } else if("document".equals(type)){
                    //-- documents path can not be accessed, documents can be uploaded with InputStream only.
                }
            }else if (isGooglePhotosUri(uri)) {
                return Uri.parse(uri.getLastPathSegment());
            }else if (isGoogleDriveUri(uri)) {
                return Uri.parse(getDriveFilePath(uri));
            }else if ("file".equalsIgnoreCase(uri.getScheme())) {
                return uri;
            }
        }

        return finalUri;
    }

    //-- sub functions

    private String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};

        try {
            cursor = context.getContentResolver().query(uri, projection,
                    selection, selectionArgs, null);

            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        }
        finally {
            if (cursor != null)
                cursor.close();
        }

        return null;
    }

    public Uri getActualPath(Context context, Uri uri, String selection, String [] selectionArgs, String[] projection){
        Uri uurl = null;
        Cursor cursor = null;
        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            //int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
            if (cursor.moveToFirst()) {
                uurl = Uri.parse(cursor.getString(column_index));
                return uurl;
            }
        } catch (Exception e) {
            System.out.println("Data" + " " + e.toString());
        }
        return uurl;
    }

    private String getDriveFilePath(Uri uri) {
        Uri returnUri = uri;
        Cursor returnCursor = context.getContentResolver().query(returnUri, null, null, null, null);
        /*
         * Get the column indexes of the data in the Cursor,
         *     * move to the first row in the Cursor, get the data,
         *     * and display it.
         * */
        int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
        int sizeIndex = returnCursor.getColumnIndex(OpenableColumns.SIZE);
        returnCursor.moveToFirst();
        String name = (returnCursor.getString(nameIndex));
        String size = (Long.toString(returnCursor.getLong(sizeIndex)));
        File file = new File(context.getCacheDir(), name);
        try {
            InputStream inputStream = context.getContentResolver().openInputStream(uri);
            FileOutputStream outputStream = new FileOutputStream(file);
            int read = 0;
            int maxBufferSize = 1 * 1024 * 1024;
            int bytesAvailable = inputStream.available();

            //int bufferSize = 1024;
            int bufferSize = Math.min(bytesAvailable, maxBufferSize);

            final byte[] buffers = new byte[bufferSize];
            while ((read = inputStream.read(buffers)) != -1) {
                outputStream.write(buffers, 0, read);
            }
            Log.e("File Size", "Size " + file.length());
            inputStream.close();
            outputStream.close();
            Log.e("File Path", "Path " + file.getPath());
            Log.e("File Size", "Size " + file.length());
        } catch (Exception e) {
            Log.e("Exception", e.getMessage());
        }
        return file.getPath();
    }

    public boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    public boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    public boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    private  boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }

    private static boolean isGoogleDriveUri(Uri uri) {
        return "com.google.android.apps.docs.storage".equals(uri.getAuthority()) || "com.google.android.apps.docs.storage.legacy".equals(uri.getAuthority());
    }

    public boolean isWhatsAppFile(Uri uri){
        return "com.example.mediapickersample.provider.media".equals(uri.getAuthority());
    }


}