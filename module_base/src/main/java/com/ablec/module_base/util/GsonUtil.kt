package com.ablec.module_base.util

import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import com.google.gson.TypeAdapter
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter

val gson = GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()
val parser = JsonParser()


inline fun <I, reified O> I.convert(): O {
    val json = gson.toJson(this)
    return json.fromJson()
}

inline fun <reified O> String.fromJson(): O {
    return gson.fromJson(this, object : TypeToken<O>() {}.type)
}

inline fun <reified I> I.toJson(): String {
    return gson.toJson(this)
}


public class RawStringJsonAdapter : TypeAdapter<String>() {
    override fun write(out: JsonWriter?, value: String?) {
        out?.jsonValue(value)
    }

    override fun read(reader: JsonReader?): String {
        return parser.parse(reader).toString()
    }
}
