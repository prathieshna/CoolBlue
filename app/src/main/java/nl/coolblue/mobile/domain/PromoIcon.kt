package nl.coolblue.mobile.domain

import com.google.gson.annotations.SerializedName

data class PromoIcon (
    @SerializedName("text")
    var text: String? = null,

    @SerializedName("type")
    var type: PromotionType? = null
)

