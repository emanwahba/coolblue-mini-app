package com.coolblue.miniapp.ui.productlist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.coolblue.miniapp.model.entities.ProductResponse
import com.coolblue.miniapp.model.repository.ProductRepository
import com.coolblue.miniapp.util.Result
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.*

@ExperimentalCoroutinesApi
class ProductListViewModelTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    private val testCoroutineDispatcher = TestCoroutineDispatcher()

    private lateinit var viewModel: ProductListViewModel

    @Mock
    private lateinit var productRepository: ProductRepository

    @Mock
    private lateinit var productResponseObserver: Observer<Result<ProductResponse>>

    @Captor
    private lateinit var argumentCaptor: ArgumentCaptor<Result<ProductResponse>>

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)

        viewModel = ProductListViewModel(productRepository, testCoroutineDispatcher)
    }

    @Test
    fun test_searchProduct_return_success() {
        testCoroutineDispatcher.runBlockingTest {
            val query = ""
            val page = 1
            val emptyResponse = ProductResponse(
                emptyList(), 0,
                0, 0, 0
            )
            val expectedResult = Result.Success(emptyResponse)

            Mockito.doReturn(expectedResult)
                .`when`(productRepository)
                .searchProduct(query, page)

            viewModel.productResponse.observeForever(productResponseObserver)
            viewModel.searchProduct(query, page)
            Mockito.verify(productResponseObserver, Mockito.times(2))
                .onChanged(argumentCaptor.capture())

            val values = argumentCaptor.allValues
            Assert.assertEquals(Result.Status.LOADING, values[0].status)
            Assert.assertEquals(Result.Status.SUCCESS, values[1].status)
            Assert.assertEquals(emptyResponse, values[1].data)

            viewModel.productResponse.removeObserver(productResponseObserver)
        }
    }

    @Test
    fun test_searchProduct_return_failure() {
        testCoroutineDispatcher.runBlockingTest {
            val query = ""
            val page = 1
            val errorMessage = "Failed to load data"
            val expectedResult = Result.Error<ProductResponse>(errorMessage)

            Mockito.doReturn(expectedResult)
                .`when`(productRepository)
                .searchProduct(query, page)

            viewModel.productResponse.observeForever(productResponseObserver)
            viewModel.searchProduct(query, page)
            Mockito.verify(productResponseObserver, Mockito.times(2))
                .onChanged(argumentCaptor.capture())

            val values = argumentCaptor.allValues
            Assert.assertEquals(Result.Status.LOADING, values[0].status)
            Assert.assertEquals(Result.Status.ERROR, values[1].status)
            Assert.assertEquals(errorMessage, values[1].message)

            viewModel.productResponse.removeObserver(productResponseObserver)
        }
    }

    @Test
    fun test_searchProduct_return_loading() {
        testCoroutineDispatcher.runBlockingTest {
            val query = ""
            val page = 1
            val expectedResult = Result.Loading(null)

            Mockito.doReturn(expectedResult)
                .`when`(productRepository)
                .searchProduct(query, page)

            viewModel.productResponse.observeForever(productResponseObserver)
            viewModel.searchProduct(query, page)
            Mockito.verify(productResponseObserver, Mockito.times(1))
                .onChanged(argumentCaptor.capture())

            val values = argumentCaptor.allValues
            Assert.assertEquals(Result.Status.LOADING, values[0].status)
            Assert.assertNull(values[0].data)

            viewModel.productResponse.removeObserver(productResponseObserver)
        }
    }
}