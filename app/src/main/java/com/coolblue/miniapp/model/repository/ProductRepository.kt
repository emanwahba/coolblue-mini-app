package com.coolblue.miniapp.model.repository

import com.coolblue.miniapp.model.PAGE
import com.coolblue.miniapp.model.remote.ProductService
import javax.inject.Inject

class ProductRepository @Inject constructor(
    private val productService: ProductService
) : BaseRepository() {
    suspend fun searchProduct(query: String, page: Int = PAGE) =
        getResult { productService.searchProduct(query, page) }
}
