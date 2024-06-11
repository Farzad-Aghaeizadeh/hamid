package ir.fardev.hamid.network

import android.util.Log
import com.facebook.stetho.okhttp3.StethoInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object NetworkModule {
    private val TAG = NetworkModule::class.java.simpleName
    val daynamicBaseURL = "https://sandbox-ebcom.mci.ir/services/"

    init {
        Log.i(TAG, "init: ")
    }

//    val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
//        setLevel(HttpLoggingInterceptor.Level.BODY)
//    }

    val clientNormal = OkHttpClient.Builder().addNetworkInterceptor(StethoInterceptor()).build()
    val retrofitNormal = Retrofit.Builder().baseUrl(daynamicBaseURL).client(clientNormal).addConverterFactory(GsonConverterFactory.create()).build()
    val webServiceNormal = retrofitNormal.create(WebService::class.java)

    val clientRx = OkHttpClient.Builder().addNetworkInterceptor(StethoInterceptor()).build()
    val retrofitRx = Retrofit.Builder().baseUrl(daynamicBaseURL).client(clientRx).addCallAdapterFactory(RxJava3CallAdapterFactory.create()).addConverterFactory(GsonConverterFactory.create()).build()
    val webServiceRx = retrofitRx.create(WebService::class.java)

    val clientCoroutine = OkHttpClient.Builder().addNetworkInterceptor(StethoInterceptor()).build()
    val retrofitCoroutine = Retrofit.Builder().baseUrl(daynamicBaseURL).client(clientCoroutine).addConverterFactory(GsonConverterFactory.create()).build()
    val webServiceCoroutine = retrofitCoroutine.create(WebService::class.java)

}