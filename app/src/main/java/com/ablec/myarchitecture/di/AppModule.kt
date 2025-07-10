package com.ablec.myarchitecture.di


import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.ablec.module_base.db.AppDatabase
import com.ablec.myarchitecture.data.server.api.TestApiService
import com.ablec.myarchitecture.data.server.api.UploadApiService
import com.blankj.utilcode.util.LogUtils
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun provideDataBase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getInstance(context)
    }

    private val Application.globalDateStore: DataStore<Preferences> by preferencesDataStore(
        name = "data-store"
    )

    @Singleton
    @Provides
    fun provideGlobalStore(@ApplicationContext context: Context): DataStore<Preferences> {
        if (context !is Application){
            throw IllegalArgumentException("context must be application")
        }
        return context.globalDateStore
    }

    @Singleton
    @Provides
    fun provideIoDispatcher() = Dispatchers.IO

    @UploadOkHttpClient
    @Singleton
    @Provides
    fun provideUploadClient(@ApplicationContext context: Context): OkHttpClient {
        val loggingInterceptor =
            HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
        return OkHttpClient().newBuilder()
            //应用拦截器 日志，header,重试，token刷新
            .addInterceptor(loggingInterceptor)
            .build()
    }


    @NormalOkHttpClient
    @Singleton
    @Provides
    fun provideNormalClient(@ApplicationContext context: Context): OkHttpClient {
        return OkHttpClient()
            .newBuilder()
            .addInterceptor(
                HttpLoggingInterceptor { LogUtils.d(it) }
                    .setLevel(HttpLoggingInterceptor.Level.BODY)
            )
//            .addInterceptor(ChuckerInterceptor(BaseApplication.instance))
            .connectTimeout(
                5L,
                TimeUnit.SECONDS
            )
            .readTimeout(
                5L,
                TimeUnit.SECONDS
            )
            .writeTimeout(
                5L,
                TimeUnit.SECONDS
            )
            .retryOnConnectionFailure(false)
            .build()
    }


    @Singleton
    @Provides
    fun provideApiService(@NormalOkHttpClient okHttpClient: OkHttpClient): TestApiService {
        val retrofit = Retrofit.Builder()
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("http://sendflume.droicloud.com:8600/")
            .build()
        return retrofit.create(TestApiService::class.java)
    }


    @Singleton
    @Provides
    fun provideUploadApiService(@UploadOkHttpClient okHttpClient: OkHttpClient): UploadApiService {
        val retrofit = Retrofit.Builder()
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("http://sendflume.droicloud.com:8600/")
            .build()
        return retrofit.create(UploadApiService::class.java)
    }


    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class NormalOkHttpClient

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class UploadOkHttpClient
}


