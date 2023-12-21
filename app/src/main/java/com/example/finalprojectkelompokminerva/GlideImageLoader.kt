package com.example.finalprojectkelompokminerva

import android.content.Context
import android.graphics.Bitmap
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.BitmapImageViewTarget
import com.bumptech.glide.request.transition.Transition

class GlideImageLoader(private val context: Context) : ImageLoader {
    override fun loadImage(imageUrl: String, imageView: ImageView) {
        Glide.with(context)
            .load(imageUrl)
            .centerCrop()
            .into(imageView)
    }

    override fun loadImageAsBitmap(imageUrl: String): Bitmap {
        // This Glide method loads the image into a Bitmap
        return Glide.with(context)
            .asBitmap()
            .load(imageUrl)
            .centerCrop()
            .submit()
            .get()
    }
}