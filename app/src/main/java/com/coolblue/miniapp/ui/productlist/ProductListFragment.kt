package com.coolblue.miniapp.ui.productlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView.OnEditorActionListener
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.coolblue.miniapp.R
import com.coolblue.miniapp.util.Result
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.product_list_fragment.*

@AndroidEntryPoint
class ProductListFragment : Fragment() {

    private val viewModel: ProductListViewModel by viewModels()
    private lateinit var adapter: ProductRecyclerViewAdapter
    private val page = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.product_list_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        addActionListenerToEditText()
        setupObservers()
        performSearch("", page)
    }

    private fun performSearch(query: String, page: Int) {
        viewModel.searchProduct(query, page)
    }

    private fun addActionListenerToEditText() {
        search_product_edit_text.setOnEditorActionListener(OnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                performSearch(search_product_edit_text.text.toString(), page)
                clearEditText()
            }
            false
        })
    }

    private fun clearEditText() {
        search_product_edit_text.setText("")
    }

    private fun setupRecyclerView() {
        val linearLayoutManager = LinearLayoutManager(activity)
        product_list.layoutManager = linearLayoutManager
        adapter = ProductRecyclerViewAdapter()
        product_list.adapter = adapter
    }

    private fun setupObservers() {
        viewModel.productResponse.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Result.Status.SUCCESS -> {
                    progress_bar.visibility = View.GONE
                    product_list.visibility = View.VISIBLE
                    product_list.requestFocus()
                    if (it.data != null) adapter.submitList(it.data!!.products)
                }
                Result.Status.ERROR -> {
                    Toast.makeText(activity, it.message, Toast.LENGTH_SHORT).show()
                }
                Result.Status.LOADING -> {
                    progress_bar.visibility = View.VISIBLE
                    product_list.visibility = View.GONE
                }
            }
        })
    }
}