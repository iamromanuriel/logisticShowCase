package com.example.logisticshowcase.data.Api.model_reponse

data class BaseResponse<T> (
    val ok: Boolean,
    val message: String,
    val data: T
)