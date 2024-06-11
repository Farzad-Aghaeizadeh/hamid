package ir.fardev.hamid.network

import io.reactivex.rxjava3.core.Single
import ir.fardev.hamid.HEADER_CLIENT_ID
import ir.fardev.hamid.data.OTPRequestBody
import ir.fardev.hamid.data.response.BaseResponse
import ir.fardev.hamid.data.response.ConfigResp
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Url

interface WebService {
    @POST("auth/v1.0/otp")
    fun getOTP(@Header("clientId") clientId : String = HEADER_CLIENT_ID,@Body body: OTPRequestBody) : Call<BaseResponse<Any>>
    @GET
    fun getConfig(@Url url: String = "https://static-ebcom.mci.ir/static/app/ewano/ewano-config.json") : Call<BaseResponse<ConfigResp>>

    @GET
    fun getConfigRxKotlin(@Url url: String = "https://static-ebcom.mci.ir/static/app/ewano/ewano-config.json") : Single<BaseResponse<ConfigResp>>
    @GET
    suspend fun getConfigCoroutine(@Url url: String = "https://static-ebcom.mci.ir/static/app/ewano/ewano-config.json") : BaseResponse<ConfigResp>


}