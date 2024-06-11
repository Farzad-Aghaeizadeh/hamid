package ir.fardev.hamid.data.response

data class BaseResponse<T>(
    val meta: Meta,
    val result: Result<T>,
    val status: Status
)