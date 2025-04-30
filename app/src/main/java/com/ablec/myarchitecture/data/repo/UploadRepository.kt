package com.ablec.myarchitecture.data.repo

import android.content.Context
import android.net.Uri
import com.ablec.module_base.http.handleApiCall
import com.ablec.myarchitecture.data.server.api.UploadApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okio.BufferedSink
import retrofit2.http.QueryMap
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @Description:    普通图片上传
 * @Author:         haoshuaihui
 * @CreateDate:     2020/12/29 19:07
 */
@Singleton
class UploadRepository @Inject constructor(
    private val apiService: UploadApiService
) {

    suspend fun uploadImage(
        context: Context,
        uri: Uri,
        @QueryMap map: Map<String, String>,
        progressCallback: (Float) -> Unit
    ) = withContext(Dispatchers.IO) {
        val file = uriToFile(context, uri)
        val body = ProgressRequestBody(file, "image/*", progressCallback)
        val part = MultipartBody.Part.createFormData("file", file.name, body)
        handleApiCall { apiService.uploadImage(part, map) }
    }

    class ProgressRequestBody(
        private val file: File,
        private val contentType: String,
        private val progressCallback: (Float) -> Unit
    ) : RequestBody() {

        override fun contentType(): MediaType? = contentType.toMediaTypeOrNull()

        override fun contentLength(): Long = file.length()

        override fun writeTo(sink: BufferedSink) {
            val length = contentLength()
            val buffer = ByteArray(DEFAULT_BUFFER_SIZE)
            file.inputStream().use { inputStream ->
                var uploaded = 0L
                var read: Int
                while (inputStream.read(buffer).also { read = it } != -1) {
                    progressCallback(uploaded / length.toFloat())
                    sink.write(buffer, 0, read)
                    uploaded += read
                }
            }
        }
    }

    private fun uriToFile(context: Context, uri: Uri): File {
        val inputStream = context.contentResolver.openInputStream(uri)!!
        val file = File(context.cacheDir, "${System.currentTimeMillis()}.jpg")
        file.outputStream().use { outputStream ->
            inputStream.copyTo(outputStream)
        }
        return file
    }

}