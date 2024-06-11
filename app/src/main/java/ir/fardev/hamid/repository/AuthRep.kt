package ir.fardev.hamid.repository

import android.util.Log
import ir.fardev.hamid.data.OTPRequestBody
import ir.fardev.hamid.data.response.BaseResponse
import ir.fardev.hamid.network.NetworkModule
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class AuthRep {
    private val TAG = AuthRep::class.java.simpleName
    fun getOtp(phoneNumber:String, success:() -> Unit, error :() -> Unit){
        Log.i(TAG, "getOtp: ")
        try {
            val reqBody = OTPRequestBody(phoneNumber,"")
            val otpRequest = NetworkModule.webServiceNormal.getOTP(body = reqBody)
            otpRequest.enqueue(object : Callback<BaseResponse<Any>>{
                override fun onResponse(call: Call<BaseResponse<Any>>, response: Response<BaseResponse<Any>>) {
                    if (response.isSuccessful) {
                        Log.i(TAG, "getOtp => onResponse: success => ${response.code()}")
                        success.invoke()
                    }
                    else
                    {
                        Log.e(TAG, "getOtp => onResponse: failure => ${response.code()}", )
                        error.invoke()
                    }
                }

                override fun onFailure(call: Call<BaseResponse<Any>>, t: Throwable) {
                    Log.e(TAG, "getOtp => onFailure: error => ${t.toString()}" )
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