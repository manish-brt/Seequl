package com.app.seequl.ui.adapters.binding

import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.app.seequl.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

object ImageBA {

    @JvmStatic
    @BindingAdapter("loadImageUrl")
    fun loadImageUrl(imageView: ImageView, url: String?) {
        val placeholder = ContextCompat.getDrawable(imageView.context, R.drawable.bg_blue_gradient)

        if (url != null && url.isNotEmpty()) {
            imageView.background = null

            Glide.with(imageView.context)
                .load(url)
                .error(R.drawable.bg_blue_gradient)
//                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                .skipMemoryCache(false)
                .into(imageView)
        } else {
            imageView.background = placeholder
        }
    }

}