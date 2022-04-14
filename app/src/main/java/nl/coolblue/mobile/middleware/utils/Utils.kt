package nl.coolblue.mobile.middleware.utils

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import nl.coolblue.mobile.domain.ApiError
import nl.coolblue.mobile.R
import nl.coolblue.mobile.domain.ApiErrorMessage
import okhttp3.ResponseBody

fun parseError(errorResponse: ResponseBody?, code: Int, context: Context): ApiError {
    if (code == 401) return ApiError(401, context.getString(R.string.unauthorized))
    val gSon = Gson()
    val type = object : TypeToken<ApiErrorMessage>() {}.type

    val apiErrorMessage: ApiErrorMessage = gSon.fromJson(errorResponse?.charStream(), type)
    return apiErrorMessage.error
}