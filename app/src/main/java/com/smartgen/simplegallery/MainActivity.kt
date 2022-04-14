package com.smartgen.simplegallery

import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    lateinit var recyclerView: RecyclerView
    lateinit var adapter: FolderAdapter
    var folderList= arrayListOf<FolderModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView=findViewById(R.id.recyclerView)

        if (ActivityCompat.checkSelfPermission(this,android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            resultLauncher.launch(arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE,android.Manifest.permission.CAMERA))
        }else{
            CoroutineScope(Dispatchers.IO).launch {
                readExternalStorage()
            }
        }


    }


    private suspend fun readExternalStorage(){

        var booleanFolder=false
        var position=0




        val cols= listOf<String>(MediaStore.MediaColumns.DATA,MediaStore.Images.Media.BUCKET_DISPLAY_NAME).toTypedArray()
        val cursor=contentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,cols,null,null,MediaStore.Images.Media.DATE_TAKEN +" DESC")
        val columnIndexData=cursor?.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA)
        val columnFolderNameIndex=cursor?.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME)




        while (cursor!!.moveToNext()){
           val folderName=cursor.getString(columnFolderNameIndex!!)
           val absolutePathOfImage=cursor.getString(columnIndexData!!)


            for (i in 0 until folderList.size) {
                if (folderList[i].folderName == cursor.getString(columnFolderNameIndex)) {
                    booleanFolder = true
                    position = i
                    break
                }else{
                     if (folderList[i].folderName == "root") {
                        booleanFolder = true
                        position = i
                        break
                    }else{
                         booleanFolder = false
                     }
                }
            }

            if (booleanFolder) {
                val imagePath: ArrayList<String> = ArrayList()
                imagePath.addAll(folderList[position].imagePathList)
                imagePath.add(absolutePathOfImage)
                folderList[position].imagePathList=imagePath
            } else {
                val imagePath: ArrayList<String> = ArrayList()
                imagePath.add(absolutePathOfImage)
                folderList.add(FolderModel(folderName?:"root",imagePath))
            }

        }
        MainScope().launch {
            recyclerView.layoutManager=GridLayoutManager(this@MainActivity,3)
            recyclerView.adapter=adapter
        }
        adapter= FolderAdapter(this,folderList)
        adapter.notifyDataSetChanged()
    }


   private val resultLauncher=registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()){ permission->
       permission.entries.forEach{ it ->
        val permissionName=it.key
        val isGranted=it.value
        if (isGranted){
            Toast.makeText(this,"$permissionName is Granted",Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(this,"$permissionName is Rejected",Toast.LENGTH_SHORT).show()
        }
       }


   }


    companion object{
        const val TAG="checkData"
    }





}