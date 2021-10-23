package com.yunfeng.lib.http

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

val gson = Gson()

//convert a data class to a map
fun <T> T.toMap(): Map<String, String> {
    return convert()
}

//convert a map to a data class
inline fun <reified T> Map<String, String>.toBean(): T {
    return convert()
}

//convert an object of type I to type O
//包括list
inline fun <I, reified O> I.convert(): O {
    val json = gson.toJson(this)
    return gson.fromJson(json, object : TypeToken<O>() {}.type)
}

inline fun <reified O> String.convert(): O {
    return gson.fromJson(this, object : TypeToken<O>() {}.type)
}

inline fun <reified I> I.toJson(): String {
    return gson.toJson(this)
}