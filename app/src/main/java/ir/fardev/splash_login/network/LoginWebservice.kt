package ir.fardev.splash_login.network

import ir.fardev.splash_login.HEADER_CLIENT_ID
import ir.fardev.splash_login.data.OTPRequestBody
import ir.fardev.splash_login.data.response.BaseResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface LoginWebservice {
    @POST("auth/v1.0/otp")
    fun getOTP(@Header("clientId") clientId : String = HEADER_CLIENT_ID,@Body body: OTPRequestBody) : Call<BaseResponse<Any>>
}