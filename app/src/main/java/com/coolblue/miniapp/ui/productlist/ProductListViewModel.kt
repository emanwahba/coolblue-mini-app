package com.coolblue.miniapp.ui.productlist

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coolblue.miniapp.di.IoDispatcher
import com.coolblue.miniapp.model.entities.ProductResponse
import com.coolblue.miniapp.model.repository.ProductRepository
import com.coolblue.miniapp.util.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch

class ProductListViewModel @ViewModelInject constructor(
    private val productRepository: ProductRepository,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _productResponse = MutableLiveData<Result<ProductResponse>>()
    val productResponse: LiveData<Result<ProductResponse>> = _productResponse

    fun searchProduct(query: String, page: Int) = viewModelScope.launch(dispatcher) {
        _productResponse.postValue(Result.Loading())

        val result = productRepository.searchProduct(query, page)
        if (result.status == Result.Status.SUCCESS && result.data != null) {
            _productResponse.postValue(Result.Success(result.data!!))
        } else if (result.status == Result.Status.ERROR) {
            _productResponse.postValue(Result.Error(result.message))
        }
    }
}