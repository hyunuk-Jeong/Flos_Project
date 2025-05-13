package com.hyunuk.flos.util

fun convertPriceToString(price:Int):String{
    return "%,d원".format(price)
}