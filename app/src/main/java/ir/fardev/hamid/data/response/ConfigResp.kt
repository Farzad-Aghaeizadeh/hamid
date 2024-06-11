package ir.fardev.hamid.data.response

import com.google.gson.annotations.SerializedName

data class ConfigResp(@SerializedName("configuration") val configurationRemoteResp: ConfigurationRemoteResp)

data class ConfigurationRemoteResp(@SerializedName("basic") val basicResp: BasicResp)

data class BasicResp(val vat : String)
