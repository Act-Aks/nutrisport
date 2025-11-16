package com.actaks.nutrisport.shared.domain

import kotlinx.serialization.Serializable

@Serializable
data class Customer(
    val id: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val postalCode: Int? = null,
    val city: String? = null,
    val address: String? = null,
    val phoneNumber: PhoneNumber? = null,
    val cart: List<CartItem> = emptyList()
)

@Serializable
data class PhoneNumber(
    val dialCode: Int,
    val number: String,
)
