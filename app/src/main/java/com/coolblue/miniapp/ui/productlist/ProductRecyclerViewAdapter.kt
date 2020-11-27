package com.coolblue.miniapp.ui.productlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.coolblue.miniapp.R
import com.coolblue.miniapp.model.entities.Product
import com.coolblue.miniapp.util.setProductImage
import kotlinx.android.synthetic.main.list_item_product.view.*

class ProductRecyclerViewAdapter() :
    ListAdapter<Product, ProductViewHolder>(ProductDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val item = getItem(position) as Product
        holder.bind(item)
    }
}

class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    companion object {
        fun from(parent: ViewGroup): ProductViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = layoutInflater.inflate(R.layout.list_item_product, parent, false)
            return ProductViewHolder(view)
        }
    }

    fun bind(item: Product) {
        val context = itemView.context
        itemView.name.text = item.productName
        if (item.reviewInformation.reviewSummary.reviewCount > 0) {
            itemView.review_count.text =
                context.getString(
                    R.string.review_count,
                    item.reviewInformation.reviewSummary.reviewCount
                )
        }
        itemView.sales_price.text = item.salesPriceIncVat.toString()
        itemView.image.setProductImage(
            item,
            context.resources.getDimension(R.dimen.image_width),
            context.resources.getDimension(R.dimen.image_height),
        )
    }
}

class ProductDiffCallback : DiffUtil.ItemCallback<Product>() {
    override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem.productId == newItem.productId
    }

    override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem == newItem
    }
}