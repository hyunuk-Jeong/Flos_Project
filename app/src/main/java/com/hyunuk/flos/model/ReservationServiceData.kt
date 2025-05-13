package com.hyunuk.flos.model

data class ReservationServiceData (
    val mainCategory : String,
    val subCategory : String,
    val price:Int?,
    val salePrice:Int?,
    val content :String
)