package nl.coolblue.mobile.domain

import com.google.gson.annotations.SerializedName

data class ReviewSummary (
    @SerializedName("reviewAverage")
    var reviewAverage: Float? = null,

    @SerializedName("reviewCount")
    var reviewCount: Int? = null
)
