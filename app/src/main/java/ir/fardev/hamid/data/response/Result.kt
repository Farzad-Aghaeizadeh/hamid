package ir.fardev.hamid.data.response

data class Result<T>(
    val data: T,
    val status: Status
)