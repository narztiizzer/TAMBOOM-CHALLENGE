package com.tiizzer.narz.tamboom.challenge.model

data class DonateResult(
    val success: Boolean = false,
    val error_code: String?,
    val error_message: String?
)