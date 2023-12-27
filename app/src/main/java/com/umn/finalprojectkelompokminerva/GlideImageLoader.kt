package com.umn.finalprojectkelompokminerva

import android.content.Context
import android.graphics.Bitmap
import android.widget.ImageView
import com.bumptech.glide.Glide

class GlideImageLoader(private val context: Context) : ImageLoader {
    override fun loadImage(imageUrl: String, imageView: ImageView) {
        Glide.with(context)
            .load(imageUrl)
            .centerCrop()
            .into(imageView)
    }

    override fun loadImageAsBitmap(imageUrl: String): Bitmap {
        return Glide.with(context)
            .asBitmap()
            .load(imageUrl)
            .centerCrop()
            .submit()
            .get()
    }
}