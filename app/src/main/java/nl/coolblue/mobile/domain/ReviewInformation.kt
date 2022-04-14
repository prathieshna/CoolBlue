package nl.coolblue.mobile.domain

import com.google.gson.annotations.SerializedName

data class ReviewInformation (
    @SerializedName("reviews")
    var reviews: List<Review>? = null,

    @SerializedName("reviewSummary")
    var reviewSummary: ReviewSummary? = null
)
