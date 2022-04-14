package nl.coolblue.mobile.domain

import com.google.gson.annotations.SerializedName

data class SearchResult (
    @SerializedName("products")
    var products: List<Product?>? = null,

    @SerializedName("currentPage")
    var currentPage: Int? = null,

    @SerializedName("pageSize")
    var pageSize: Int? = null,

    @SerializedName("totalResults")
    var totalResults: Long? = null,

    @SerializedName("pageCount" )
    var pageCount: Int? = null
)
