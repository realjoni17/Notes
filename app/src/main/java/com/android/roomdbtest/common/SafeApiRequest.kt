package com.android.roomdbtest.common

import android.util.Log
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Response

open class SafeApiRequest {
    suspend fun <T : Any> safeApiRequest(call: suspend () -> Response<T>): T {
        val response = call.invoke()
        if (response.isSuccessful) {
            return response.body()!!
        } else {
            val responseErr = response.errorBody()?.string()
            val message = StringBuilder()
            responseErr.let {
                try {
                    message.append(JSONObject(it).getString("error"))
                } catch (e: JSONException) {
                }
            }
            Log.d("TAG", "safeApiRequest: ${message.toString()}")
            throw Exception(message.toString())
        }
    }

}