package com.actaks.nutrisport.shared.domain

data class SelectOption<T>(
    val text: String,
    val value: T,
)