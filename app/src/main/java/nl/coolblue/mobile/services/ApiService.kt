package nl.coolblue.mobile.services

import nl.coolblue.mobile.middleware.retrofit.RetrofitClient
import nl.coolblue.mobile.domain.SearchResult
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET( "search")
    fun getProductsBySearch(
        @Query("query") query: String,
        @Query("page") page: Int
    ): Call<SearchResult?>
}

fun apiService(): ApiService {
    val retrofit = RetrofitClient.instance
    return retrofit.create(ApiService::class.java)
}