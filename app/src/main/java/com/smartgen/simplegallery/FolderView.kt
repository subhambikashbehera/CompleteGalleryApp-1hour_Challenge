package com.smartgen.simplegallery

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class FolderView : AppCompatActivity() {

    lateinit var recyclerView: RecyclerView
    lateinit var adapter: ImageAdapter
    var imageList= arrayListOf<String>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_folder_view)
        recyclerView=findViewById(R.id.recyclerView)
        val listImages=intent.getStringArrayListExtra("imageList")

        try {
            recyclerView.layoutManager=GridLayoutManager(this,3)
            adapter=ImageAdapter(this,listImages!!)
            recyclerView.adapter=adapter
        } catch (e: Exception) {
            e.printStackTrace()
        }


    }
}