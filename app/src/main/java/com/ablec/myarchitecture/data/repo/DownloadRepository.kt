package com.ablec.myarchitecture.data.repo

import android.content.Context
import android.net.Uri
import com.ablec.myarchitecture.data.server.api.DownloadApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import okio.Buffer
import okio.BufferedSource
import okio.ForwardingSource
import okio.Source
import okio.buffer
import okio.use
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DownloadRepository @Inject constructor(
    private val apiService: DownloadApiService
) {
    suspend fun downloadFile(
        context: Context,
        url: String,
        targetUri: Uri,
        progressCallback: (Float) -> Unit
    ) = withContext(Dispatchers.IO) {
        runCatching {
            val responseBody = apiService.downloadFile(url)
            val wrappedBody = ProgressResponseBody(responseBody, progressCallback)
            saveToFile(context, wrappedBody, targetUri)
        }
    }

    private fun saveToFile(context: Context, body: ResponseBody, uri: Uri) {
        val outputStream = context.contentResolver.openOutputStream(uri) ?: return
        body.byteStream().use { inputStream ->
            outputStream.use { output ->
                inputStream.copyTo(output)
            }
        }
    }

    class ProgressResponseBody(
        private val responseBody: ResponseBody,
        private val progressCallback: (Float) -> Unit
    ) : ResponseBody() {

        private var bufferedSource: BufferedSource? = null

        override fun contentType() = responseBody.contentType()

        override fun contentLength() = responseBody.contentLength()

        override fun source(): BufferedSource {
            if (bufferedSource == null) {
                bufferedSource = source(responseBody.source()).buffer()
            }
            return bufferedSource!!
        }

        private fun source(source: Source): Source {
            return object : ForwardingSource(source) {
                var totalBytesRead = 0L
                override fun read(sink: Buffer, byteCount: Long): Long {
                    val bytesRead = super.read(sink, byteCount)
                    if (bytesRead != -1L) {
                        totalBytesRead += bytesRead
                    }
                    progressCallback(totalBytesRead / contentLength().toFloat())
                    return bytesRead
                }
            }
        }
    }
}