package com.example.todoapp.View

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.bumptech.glide.Glide
import com.example.todoapp.BuildConfig
import com.example.todoapp.R
import com.example.todoapp.utils.AppConstant
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class AddNotesActivity : AppCompatActivity() {
    lateinit var editTextTitle: EditText
    lateinit var editTextDescription: EditText
    lateinit var imageView: ImageView
    lateinit var submitButton: Button
    val REQUEST_CODE_GALLERY=2
    val REQUEST_CODE_CAMERA=1
    val MY_PERMISSION_CODE=124
    var picturePath=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_notes)
        bindView()
        clickListener()
    }

    private fun clickListener() {
        imageView.setOnClickListener(object: View.OnClickListener{
            override fun onClick(v: View?) {
                if(checkRequestPermission()){
                    setUpDialog()
                }

            }

        })
        submitButton.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                intent = Intent()
                if(!TextUtils.isEmpty(editTextTitle.text.toString())){
                    intent.putExtra(AppConstant.TITLE,editTextTitle.text.toString())
                    intent.putExtra(AppConstant.DESCRIPTION,editTextDescription.text.toString())
                    intent.putExtra(AppConstant.IMAGE_PATH,picturePath)
                    setResult(Activity.RESULT_OK,intent)
                }
                else{
                    Toast.makeText(this@AddNotesActivity,"Title is mandatory",Toast.LENGTH_SHORT).show()
                }
                finish()
            }

        })
//        backButton.setOnClickListener(object : View.OnClickListener{
//            override fun onClick(v: View?) {
//                onBackPressed()
//            }
//
//        })
    }

    private fun checkRequestPermission(): Boolean {
        val cameraPermission=ContextCompat.checkSelfPermission(this,android.Manifest.permission.CAMERA)
        val readStoragepermission= ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)

        var listPermissionrequired=ArrayList<String>()
        if(cameraPermission!=PackageManager.PERMISSION_GRANTED){
            listPermissionrequired.add(android.Manifest.permission.CAMERA)
        }
        if(readStoragepermission!=PackageManager.PERMISSION_GRANTED){
            listPermissionrequired.add(android.Manifest.permission.READ_EXTERNAL_STORAGE)
        }
        if(!listPermissionrequired.isEmpty()){
            ActivityCompat.requestPermissions(this,listPermissionrequired.toTypedArray<String>(),MY_PERMISSION_CODE)
            return false
        }
        return true

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            MY_PERMISSION_CODE->{
               if(grantResults.isNotEmpty() && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                   setUpDialog()
               }
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
    private fun setUpDialog() {
        val view = LayoutInflater.from(this).inflate(R.layout.dailog_selector,null)
        val textViewCamera: TextView = view.findViewById(R.id.TextViewCamera)
        val textViewGallery: TextView = view.findViewById(R.id.gallery)
        val dialog= AlertDialog.Builder(this)
                .setView(view)
                .setCancelable(true)
                .create()
        dialog.show()
        textViewCamera.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                Log.d("cameraActivity","here")
                val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                var photoFile : File? = null
                photoFile = createImageFile()
                if(photoFile!=null){

                    val photoURI= FileProvider.getUriForFile(this@AddNotesActivity,BuildConfig.APPLICATION_ID + ".provider",photoFile)
                    picturePath= photoFile.absolutePath
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,photoURI)
                    startActivityForResult(takePictureIntent,REQUEST_CODE_CAMERA)
                    dialog.hide()
                }

            }

        })

        textViewGallery.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                val intent = Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                startActivityForResult(intent,REQUEST_CODE_GALLERY)
                dialog.hide()
            }

        })




    }

    private fun createImageFile(): File? {
        val timeStamp=SimpleDateFormat("yyyyMMddHHmmss").format(Date())
        val fileName="Jpeg_" + timeStamp+ "_"
        val storageDir=getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(fileName,".jpg",storageDir)
    }

    private fun bindView() {
        editTextTitle=findViewById(R.id.Edit_Text_Title)
        editTextDescription=findViewById(R.id.Edit_Text_Description)
        imageView=findViewById(R.id.imageView)
        submitButton=findViewById(R.id.ToDoAddButton)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(data!=null){
            if(resultCode==Activity.RESULT_OK){
                if(requestCode==REQUEST_CODE_GALLERY){
                    val selectImage= data?.data
                    val filePath= arrayOf(MediaStore.Images.Media.DATA)
                    val c = contentResolver.query(selectImage!!,filePath,null,null,null)
                    if (c != null) {
                        c.moveToFirst()
                        val columnIndex=c.getColumnIndex(filePath[0])
                        picturePath=c.getString(columnIndex)
                        c.close()
                        Glide.with(this).load(picturePath).into(imageView)
                    }

                }
                else if(requestCode==REQUEST_CODE_CAMERA){
                    Log.d("hello","here I am")
                    Glide.with(this).load(picturePath).into(imageView)
                }
            }
        }
        else{

        }

    }
}
