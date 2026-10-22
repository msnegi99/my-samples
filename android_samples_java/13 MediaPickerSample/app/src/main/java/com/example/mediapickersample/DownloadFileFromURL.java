package com.example.mediapickersample;

import android.net.Uri;
import android.os.AsyncTask;
import android.app.ProgressDialog;
import android.os.Environment;
import android.util.Log;
import android.widget.ImageView;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

public class DownloadFileFromURL extends AsyncTask<String, String, String> {
    ProgressDialog pDialog;
    ImageView imgView;

    public DownloadFileFromURL(ProgressDialog pDialog, ImageView imgView) {
        this.pDialog = pDialog;
        this.imgView = imgView;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... f_url) {
        int count;
        try {
            URL url = new URL(f_url[0]);
            URLConnection conection = url.openConnection();
            conection.connect();

            int lenghtOfFile = conection.getContentLength();
            InputStream input = new BufferedInputStream(url.openStream(), 8192);
            OutputStream output = new FileOutputStream(Environment.getExternalStorageDirectory() + "/download/downloadedfile.jpg");
            byte data[] = new byte[1024];
            long total = 0;

            while ((count = input.read(data)) != -1) {
                total += count;
                publishProgress("" + (int) ((total * 100) / lenghtOfFile));
                output.write(data, 0, count);
            }

            output.flush();
            output.close();
            input.close();
        } catch (Exception e) {
            Log.e("Error: ", e.getMessage());
        }

        return null;
    }

    protected void onProgressUpdate(String... progress) {
        pDialog.setProgress(Integer.parseInt(progress[0]));
    }

    @Override
    protected void onPostExecute(String file_url) {
        pDialog.dismiss();
        String imagePath = Environment.getExternalStorageDirectory() + "/download/downloadedfile.jpg";
        File imgFile = new File(imagePath);
        Uri uri = Uri.fromFile(imgFile);
        imgView.setImageURI(uri);
    }
}