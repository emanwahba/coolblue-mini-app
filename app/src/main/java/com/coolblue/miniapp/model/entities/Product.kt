package com.coolblue.miniapp.model.entities

data class Product(
    val productId: Int,
    val productName: String,
    val reviewInformation: ReviewInformation,
    val uSPs: List<String>,
    val availabilityState: Int,
    val salesPriceIncVat: Double,
    val productImage: String,
    val coolbluesChoiceInformationTitle: String,
    val promoIcon: PromoIcon,
    val nextDayDelivery: Boolean
)