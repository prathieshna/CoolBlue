package nl.coolblue.mobile.domain

import com.google.gson.annotations.SerializedName

enum class PromotionType (val value: String)  {
    @SerializedName("coolblues-choice")
    COOLBLUES_CHOICE("coolblues-choice"),
    @SerializedName("action-price")
    ACTION_PRICE("action-price")
}