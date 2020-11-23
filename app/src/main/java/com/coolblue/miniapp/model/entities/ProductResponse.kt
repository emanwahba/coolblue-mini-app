package com.coolblue.miniapp.model.entities

data class ProductResponse(
    val products: List<Product>,
    val currentPage: Int,
    val pageSize: Int,
    val totalResults: Int,
    val pageCount: Int
)