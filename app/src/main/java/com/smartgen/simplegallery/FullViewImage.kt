package com.smartgen.simplegallery

import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

class FullViewImage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_full_view_image)
        supportActionBar?.hide()
        val imageView=findViewById<ImageView>(R.id.imageView)
        try {
            val image=intent.getStringExtra("image").toString()
             Glide.with(this).load(image).diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).into(imageView)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}