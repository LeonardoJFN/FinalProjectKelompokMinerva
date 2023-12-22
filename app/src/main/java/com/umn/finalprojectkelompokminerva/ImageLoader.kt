package com.umn.finalprojectkelompokminerva


import android.graphics.Bitmap
import android.widget.ImageView


interface ImageLoader {
    fun loadImage(imageUrl: String, imageView: ImageView)
    fun loadImageAsBitmap(imageUrl: String): Bitmap
}