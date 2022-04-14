package nl.coolblue.mobile.domain

import com.google.gson.annotations.SerializedName

data class Product (
    @SerializedName("productId")
    var productId: Long? = null,

    @SerializedName("productName")
    var productName: String? = null,

    @SerializedName("reviewInformation")
    var reviewInformation: ReviewInformation? = null,

    @SerializedName("USPs")
    var USPs: List<String?>? = null,

    @SerializedName("availabilityState")
    var availabilityState: Int? = null,

    @SerializedName("salesPriceIncVat")
    var salesPriceIncVat: Double? = null,

    @SerializedName("listPriceIncVat")
    var listPriceIncVat: Double? = null,

    @SerializedName("listPriceExVat")
    var listPriceExVat: Double? = null,

    @SerializedName("productImage")
    var productImage: String? = null,

    @SerializedName("coolbluesChoiceInformationTitle")
    var coolbluesChoiceInformationTitle: String? = null,

    @SerializedName("promoIcon")
    var promoIcon: PromoIcon? = null,

    @SerializedName("nextDayDelivery")
    var nextDayDelivery: Boolean? = null
)
