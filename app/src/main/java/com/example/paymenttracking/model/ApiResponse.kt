package com.example.paymenttracking.model

data class ApiResponse(
    val info: Info,
    val results: List<Result>
)