package com.example.myapplicationsixth.Utils

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.Response


object HandlerApiResponses {

    suspend fun <T> safeApiCall(
        dispatcher: CoroutineDispatcher,
        apiCall: suspend () -> Response<T>
    ) : ApiResult<T> {

        return withContext(dispatcher) {
            try {
                val response = apiCall.invoke()

                if (response.isSuccessful){
                    ApiResult.Success(data = response.body()!!)
                } else {
                    ApiResult.Error(message = response.message())
                }

            } catch (e: Exception) {
                ApiResult.Error("Error message")
            }
        }
    }
}