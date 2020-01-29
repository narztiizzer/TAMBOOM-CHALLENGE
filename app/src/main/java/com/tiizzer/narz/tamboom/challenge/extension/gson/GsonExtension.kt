package com.tiizzer.narz.tamboom.challenge.extension.gson

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

inline fun <reified T> Gson.parse(json: String) = this.fromJson<T>(json, object: TypeToken<T>() {}.type)
inline fun <reified T> Gson.stringify(instance: T) = this.toJson(instance, object: TypeToken<T>() {}.type)