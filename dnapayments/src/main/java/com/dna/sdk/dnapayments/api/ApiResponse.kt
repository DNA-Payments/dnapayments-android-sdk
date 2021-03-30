package com.dna.sdk.dnapayments.api

import androidx.annotation.Keep
import com.dna.sdk.dnapayments.models.network.ErrorResponse
import com.google.gson.Gson
import okhttp3.ResponseBody
import retrofit2.Response
@Keep
@Suppress("unused")
sealed class ApiResponse<out T> {

    /**
     * API Success response class from retrofit.
     *
     * [data] is optional. (There are responses without data)
     */
    @Keep
    class Success<T>(response: Response<T>) : ApiResponse<T>() {
        val data: T? = response.body()
        override fun toString() = "[ApiResponse.Success]: $data"
    }

    /**
     * API Failure response class.
     *
     * ## Throw Exception case.
     * Gets called when an unexpected exception occurs while creating the request or processing the response.
     *
     * ## API Network format error case.
     * API communication conventions do not match or applications need to handle errors.
     *
     * ## API Network Excepttion error case.
     * Gets called when an unexpected exception occurs while creating the request or processing the response.
     */
    @Keep
    sealed class Failure<out T> {
        @Keep
        class Error<out T>(response: Response<out T>) : ApiResponse<T>() {
            val responseBody: ResponseBody? = response.errorBody()
            val error = try {
                    Gson().fromJson(responseBody?.string(), ErrorResponse::class.java)
            } catch (e: java.lang.Exception) {
                ErrorResponse(-1,"Error occurred. Please, try again later")
            }
        }
        @Keep
        class Exception<out T>(exception: Throwable) : ApiResponse<T>() {
            val error = ErrorResponse(-1,"Error occurred. Please, try again later")
        }
    }
    @Keep
    companion object {
        /**
         * ApiResponse Factory
         *
         * [Failure] factory function. Only receives [Throwable] arguments.
         */
        fun <T> error(ex: Throwable) = Failure.Exception<T>(ex)

        /**
         * ApiResponse Factory
         *
         * [f] Create ApiResponse from [retrofit2.Response] returning from the block.
         * If [retrofit2.Response] has no errors, it will create [ApiResponse.Success]
         * If [retrofit2.Response] has errors, it will create [ApiResponse.Failure.Error]
         */
        fun <T> of(f: () -> Response<T>): ApiResponse<T> = try {
            val response = f()
            if (response.isSuccessful) {
                Success(response)
            } else {
                Failure.Error(response)
            }
        } catch (ex: Exception) {
            Failure.Exception(ex)
        }

        fun <T> create(response: Response<T>): ApiResponse<T> = try {
            if (response.isSuccessful) {
                Success(response)
            } else {
                Failure.Error(response)
            }
        } catch (ex: Exception) {
            Failure.Exception(ex)
        }
    }
}
@Keep
fun <T> ApiResponse.Failure.Error<T>.errorResponse() = error
@Keep
fun <T> ApiResponse.Failure.Exception<T>.errorResponse() = error
