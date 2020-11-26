package com.coolblue.miniapp.util

import android.content.res.Resources.getSystem
import android.widget.ImageView
import com.coolblue.miniapp.model.entities.Product
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso

fun ImageView.setProductImage(item: Product?, width: Float, height: Float) {
    item?.let {
        loadAndSetImage(item.productImage, width.toInt(), height.toInt(), this)
    }
}

private fun loadAndSetImage(
    url: String,
    width: Int,
    height: Int,
    imageView: ImageView
) {
    Picasso.get()
        .load(url)
        .networkPolicy(NetworkPolicy.NO_CACHE)
        .resize(width.px, height.px)
        .into(imageView)
}

val Int.px: Int get() = (this * getSystem().displayMetrics.density).toInt()
