package com.coolblue.miniapp.model.repository

import retrofit2.Response
import java.lang.Exception
import com.coolblue.miniapp.util.Result

abstract class BaseRepository {

    protected suspend fun <T> getResult(call: suspend () -> Response<T>): Result<T> {
        try {
            val response = call()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    return Result.Success(body)
                }
            }
            return Result.Error("${response.code()}" + " " + response.message())
        } catch (e: Exception) {
            return Result.Error(e.message ?: e.toString())
        }
    }
}