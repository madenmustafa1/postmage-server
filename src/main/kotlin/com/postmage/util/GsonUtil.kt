package com.postmage.util

import com.google.gson.Gson
import com.postmage.repo.sendErrorData

object GsonUtil {
    private val gson = Gson()

    fun <T> gsonToJson(model: T): String {
        return gson.toJson(model)
    }
}