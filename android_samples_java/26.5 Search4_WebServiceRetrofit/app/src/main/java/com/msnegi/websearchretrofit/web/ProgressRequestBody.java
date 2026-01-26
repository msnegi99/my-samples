package com.msnegi.websearchretrofit.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.BufferedSink;

public class ProgressRequestBody extends RequestBody {

    private File mFile;
    private String mPath;
    private UploadCallbacks mListener;

    private static final int DEFAULT_BUFFER_SIZE = 2048;

    @Override
    public MediaType contentType() {
        return null;
    }

    @Override
    public long contentLength() throws IOException {
        return mFile.length();
    }

    public ProgressRequestBody(final File file,  final  UploadCallbacks listener) {
        mFile = file;
        mListener = listener;
    }

    @Override
    public void writeTo(BufferedSink sink) throws IOException {
        long fileLength = mFile.length();
        byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
        FileInputStream in = new FileInputStream(mFile);
        long uploaded = 0;

        try {
            int read;
            while ((read = in.read(buffer)) != -1) {
                uploaded += read;
                sink.write(buffer, 0, read);
                int percentage = (int) (( uploaded / (float) mFile.length()) * 100);

                //use interface for updating activity
                mListener.onProgressUpdate(percentage);
            }

        } finally {
            in.close();
        }
    }

    public interface UploadCallbacks {
        void onProgressUpdate(int percentage);
        void onError();
        void onFinish();
    }
}
