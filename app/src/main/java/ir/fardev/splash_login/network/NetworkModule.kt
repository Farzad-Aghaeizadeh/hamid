package ir.fardev.splash_login.network

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkModule {
    private val TAG = NetworkModule::class.java.simpleName
    val baseURL = "https://api-ebcom.mci.ir/services/"

    init {
        Log.i(TAG, "init: ")
    }

    val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
        setLevel(HttpLoggingInterceptor.Level.BODY)
    }
    val client = OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build()
    val retrofit =
        Retrofit.Builder().baseUrl(baseURL).client(client).addConverterFactory(GsonConverterFactory.create())
            .build()
    val authWebService = retrofit.create(LoginWebservice::class.java)
}