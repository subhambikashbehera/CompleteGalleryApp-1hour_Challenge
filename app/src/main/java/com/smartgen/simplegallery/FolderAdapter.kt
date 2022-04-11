package com.smartgen.simplegallery

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

class FolderAdapter(private val context:Context, var list:ArrayList<FolderModel>): RecyclerView.Adapter<FolderAdapter.MyViewHolder>() {

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val image:ImageView=itemView.findViewById(R.id.folderImage)
    val folderName:TextView=itemView.findViewById(R.id.folderName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view=LayoutInflater.from(context).inflate(R.layout.folder_item,parent,false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val listData=list[position]
        Glide.with(context).load("file://"+listData.imagePathList[0]).diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).into(holder.image)
        holder.folderName.text=listData.folderName+"\n"+listData.imagePathList.size

        holder.image.setOnClickListener {
            val intent=Intent(context,FolderView::class.java)
            intent.putStringArrayListExtra("imageList",listData.imagePathList)
            context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }
}