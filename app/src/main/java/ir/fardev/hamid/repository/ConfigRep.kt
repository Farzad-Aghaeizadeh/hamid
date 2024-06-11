package ir.fardev.hamid.repository

import android.util.Log
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import ir.fardev.hamid.data.response.BaseResponse
import ir.fardev.hamid.data.response.ConfigResp
import ir.fardev.hamid.data.response.ConfigurationRemoteResp
import ir.fardev.hamid.network.NetworkModule
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception
import kotlin.jvm.internal.Intrinsics.Kotlin

class ConfigRep {
    private val TAG = ConfigRep::class.java.simpleName
    private val ongoingCalls = mutableListOf<Call<*>>()
    fun getConfig(success:(ConfigurationRemoteResp,Int,) -> Unit, error :(String,Int) -> Unit,counter : Int = 0){
        Log.i(TAG, "getConfig: counter =>  $counter")
        try {
            val configRequest = NetworkModule.webServiceNormal.getConfig()
            ongoingCalls.add(configRequest)
            configRequest.enqueue(object : Callback<BaseResponse<ConfigResp>>{
                override fun onResponse(call: Call<BaseResponse<ConfigResp>>, response: Response<BaseResponse<ConfigResp>>) {
                    if (response.isSuccessful) {
                        Log.i(TAG, "getConfig: onResponse: counter =>  $counter success => ${response.code()}")
                        response.body()?.result?.data?.configurationRemoteResp?.let {
                            Log.i(TAG, "getConfig: onResponse: counter =>  $counter success => not null ")
                            success.invoke(it,counter)
                        }?:{
                            // todo : inja bayad daghighan objecti ke null hast ro bargardoonim, daghigh
                            Log.e(TAG, "getConfig: onResponse: counter =>  $counter success => NULL ")
                            error.invoke("something is null",counter)
                        }
                    }
                    else
                    {
                        Log.e(TAG, "getConfig: onResponse: counter =>  $counter failure => ${response.code()}", )
                        error.invoke("response not successful => ${response.code()}",counter)
                    }
                }

                override fun onFailure(call: Call<BaseResponse<ConfigResp>>, t: Throwable) {
                    Log.e(TAG, "getConfig: onFailure: counter =>  $counter error => ${t.toString()}" )
                    error.invoke(t.toString(),counter)
                }

            }


            )
        }
        catch (e:Exception)
        {
            Log.e(TAG, "getConfig: ${e.toString()}" )
            error.invoke(e.toString(),counter)
        }
    }

    fun cancelAllRequests() {
        for (call in ongoingCalls) {
            call.cancel()
        }
        ongoingCalls.clear()
    }

    fun getConfigRxKotlin(): Single<BaseResponse<ConfigResp>> {
        Log.i(TAG, "getConfig")
        return NetworkModule.webServiceRx.getConfigRxKotlin()
    }
    suspend fun getConfigCoroutine(): BaseResponse<ConfigResp> {
        Log.i(TAG, "getConfig")
        return NetworkModule.webServiceCoroutine.getConfigCoroutine()
    }
}