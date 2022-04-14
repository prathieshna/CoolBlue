package nl.coolblue.mobile.domain

import com.google.gson.annotations.SerializedName

//Data class must have at least one parameter
//TODO Discuss with Sidarta on the content, or remove and make it a string Array in ReviewInformation.kt
data class Review(
    @SerializedName("review")
    var review: String? = null
)
