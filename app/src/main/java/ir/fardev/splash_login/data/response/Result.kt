package ir.fardev.splash_login.data.response

data class Result<T>(
    val data: T,
    val status: Status
)