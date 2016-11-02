package org.take365.Components;

import android.os.Handler;
import android.os.Looper;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.BufferedSink;

/**
 * Created by divere on 31/10/2016.
 */

public class ProgressRequestBody extends RequestBody {
    private File mFile;
    private InputStream mInputStream;
    private String mPath;
    private UploadCallbacks mListener;

    private static final int DEFAULT_BUFFER_SIZE = 2048;

    public interface UploadCallbacks {
        void onProgressUpdate(int percentage);
        void onError();
        void onFinish();
    }

    public ProgressRequestBody(final File file, final  UploadCallbacks listener) {
        mFile = file;
        mListener = listener;
    }

    public ProgressRequestBody(final InputStream inputStream, final  UploadCallbacks listener) {
        mInputStream = inputStream;
        mListener = listener;
    }

    @Override
    public MediaType contentType() {
        // i want to upload only images
        return MediaType.parse("image/*");
    }

    @Override
    public long contentLength() throws IOException {
        if(mFile != null){
            return mFile.length();
        }

        if(mInputStream != null) {
            return mInputStream.available();
        }

        return 0;
    }

    @Override
    public void writeTo(BufferedSink sink) throws IOException {

        byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
        InputStream in = null;
        long fileLength = 0;
        if(mFile != null) {
            fileLength = mFile.length();
            in = new FileInputStream(mFile);
        }
        if(mInputStream != null) {
            fileLength = mInputStream.available();
            in = mInputStream;
        }

        if(in == null) {
            return;
        }

        long uploaded = 0;

        try {
            int read;
            Handler handler = new Handler(Looper.getMainLooper());
            while ((read = in.read(buffer)) != -1) {

                // update progress on UI thread
                handler.post(new ProgressUpdater(uploaded, fileLength));

                uploaded += read;
                sink.write(buffer, 0, read);
            }
        } finally {
            in.close();
        }
    }

    private class ProgressUpdater implements Runnable {
        private long mUploaded;
        private long mTotal;
        public ProgressUpdater(long uploaded, long total) {
            mUploaded = uploaded;
            mTotal = total;
        }

        @Override
        public void run() {
            mListener.onProgressUpdate((int)(100 * mUploaded / mTotal));
        }
    }
}
