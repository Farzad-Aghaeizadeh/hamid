package ir.fardev.splash_login.data.response

data class BaseResponse<T>(
    val meta: Meta,
    val result: Result<T>,
    val status: Status
)