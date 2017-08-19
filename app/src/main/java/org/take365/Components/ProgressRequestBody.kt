package org.take365.Components

import android.os.Handler
import android.os.Looper

import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.io.InputStream

import okhttp3.MediaType
import okhttp3.RequestBody
import okio.BufferedSink

/**
 * Created by divere on 31/10/2016.
 */

class ProgressRequestBody : RequestBody {
    private var mFile: File? = null
    private var mInputStream: InputStream? = null
    private val mPath: String? = null
    private var mListener: UploadCallbacks? = null

    interface UploadCallbacks {
        fun onProgressUpdate(percentage: Int)
        fun onError()
        fun onFinish()
    }

    constructor(file: File, listener: UploadCallbacks) {
        mFile = file
        mListener = listener
    }

    constructor(inputStream: InputStream, listener: UploadCallbacks) {
        mInputStream = inputStream
        mListener = listener
    }

    override fun contentType(): MediaType {
        // i want to upload only images
        return MediaType.parse("image/*")
    }

    @Throws(IOException::class)
    override fun contentLength(): Long {
        return mFile?.length() ?: (mInputStream?.available()?.toLong() ?: 0)

    }

    @Throws(IOException::class)
    override fun writeTo(sink: BufferedSink) {

        val buffer = ByteArray(DEFAULT_BUFFER_SIZE)
        var `in`: InputStream? = null
        var fileLength: Long = 0
        if (mFile != null) {
            fileLength = mFile!!.length()
            `in` = FileInputStream(mFile)
        }
        if (mInputStream != null) {
            fileLength = mInputStream!!.available().toLong()
            `in` = mInputStream
        }

        if (`in` == null) {
            return
        }

        var uploaded: Long = 0

        try {
            val handler = Handler(Looper.getMainLooper())
            while (true) {
                val read = `in`.read(buffer)
                if (read == -1) return
                // update progress on UI thread
                handler.post(ProgressUpdater(uploaded, fileLength))

                uploaded += read.toLong()
                sink.write(buffer, 0, read)
            }
        } finally {
            `in`.close()
        }
    }

    private inner class ProgressUpdater(private val mUploaded: Long, private val mTotal: Long) : Runnable {

        override fun run() {
            mListener!!.onProgressUpdate((100 * mUploaded / mTotal).toInt())
        }
    }

    companion object {

        private val DEFAULT_BUFFER_SIZE = 2048
    }
}
