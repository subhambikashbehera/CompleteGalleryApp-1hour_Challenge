package com.smartgen.simplegallery

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.transition.Hold

class ImageAdapter(val context: Context,val dataList:ArrayList<String>): RecyclerView.Adapter<ImageAdapter.MyViewHolder>() {

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var image: ImageView =itemView.findViewById<ImageView>(R.id.image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(context).inflate(R.layout.image_item,parent,false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
       val listImages=dataList[position]
        Glide.with(context).load("file://$listImages").diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).into(holder.image)
        holder.image.setOnClickListener {
            val intent= Intent(context,FullViewImage::class.java)
            intent.putExtra("image","file://$listImages")
            context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
        return dataList.size
    }
}