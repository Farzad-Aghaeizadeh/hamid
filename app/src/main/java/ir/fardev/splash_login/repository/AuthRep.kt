package ir.fardev.splash_login.repository

import android.util.Log
import ir.fardev.splash_login.data.OTPRequestBody
import ir.fardev.splash_login.data.response.BaseResponse
import ir.fardev.splash_login.network.NetworkModule
import kotlinx.coroutines.flow.flow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception
import kotlin.math.log

class AuthRep {
    private val TAG = AuthRep::class.java.simpleName
    fun getOtp(phoneNumber:String, success:() -> Unit, error :() -> Unit){
        Log.i(TAG, "getOtp: ")
        try {
            val reqBody = OTPRequestBody(phoneNumber,"")
            val result = NetworkModule.authWebService.getOTP(body = reqBody)
            result.enqueue(object : Callback<BaseResponse<Any>>{
                override fun onResponse(call: Call<BaseResponse<Any>>, response: Response<BaseResponse<Any>>) {
                    if (response.isSuccessful) {
                        Log.i(TAG, "onResponse: success => ${response.code()}")
                        success.invoke()
                    }
                    else
                    {
                        Log.e(TAG, "onResponse: failure => ${response.code()}", )
                        error.invoke()
                    }
                }

                override fun onFailure(call: Call<BaseResponse<Any>>, t: Throwable) {
                    Log.e(TAG, "onFailure: error => ${t.toString()}" )
                    error.invoke()
                }

            }


            )
        }
        catch (e:Exception)
        {
            Log.e(TAG, "getOtp: ${e.toString()}" )
            error.invoke()
        }
    }
}