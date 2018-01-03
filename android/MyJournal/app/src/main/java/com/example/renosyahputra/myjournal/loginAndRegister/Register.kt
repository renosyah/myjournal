package com.example.renosyahputra.myjournal.loginAndRegister

import android.app.Activity
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import com.example.renosyahputra.myjournal.R
import com.example.renosyahputra.myjournal.loginAndRegister.res.ByteImageContainer
import com.example.renosyahputra.myjournal.loginAndRegister.res.FunctionUseInRegister
import com.example.renosyahputra.myjournal.res.objectData.ImageDataObj
import com.example.renosyahputra.myjournal.res.objectData.UserData
import com.example.renosyahputra.myjournal.storage.grpc.loginAndRegister.SendRegisterRequest
import java.io.File


class Register : AppCompatActivity(),View.OnClickListener {

    lateinit var context : Context

    lateinit var nama : EditText
    lateinit var username : EditText
    lateinit var password : EditText
    lateinit var email : EditText
    lateinit var nomor : EditText

    lateinit var open_image : Button
    lateinit var register_button : Button

    lateinit var back : ImageView
    lateinit var image_preview : ImageView

    val data = UserData()
    var image_byte : ByteImageContainer = ByteImageContainer()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        InitiationWidget()
    }

    internal fun InitiationWidget(){

        context = this@Register

        nama = findViewById(R.id.name_register)
        username = findViewById(R.id.username_register)
        password = findViewById(R.id.password_register)
        email = findViewById(R.id.email_register)
        nomor = findViewById(R.id.no_telp_register)

        open_image = findViewById(R.id.image_register)
        register_button = findViewById(R.id.register_new)

        back = findViewById(R.id.back_from_register)
        image_preview = findViewById(R.id.image_register_preview)

        open_image.setOnClickListener(this)
        register_button.setOnClickListener(this)
        back.setOnClickListener(this)

    }

    override fun onClick(p0: View?) {
        if (p0 == open_image){

            val images_from_galery = ArrayList<ImageDataObj>()
            PrepareImagePicker(images_from_galery)
             FunctionUseInRegister.OpenImagePicker(context,image_byte,image_preview,images_from_galery)

        }else if (p0 == register_button){
            data.setNama(nama.text.toString())
            data.setEmail(email.text.toString())
            data.setNomorTelp(nomor.text.toString())

            SendRegisterRequest(context,data,username.text.toString(),password.text.toString(),image_byte.image).execute()

        }else if(p0 == back){

            (context as Activity).finish()

        }
    }

    internal fun PrepareImagePicker(images : ArrayList<ImageDataObj>){
        val columns = arrayOf("*")

        val orderBy = MediaStore.Images.ImageColumns.DATE_TAKEN + " DESC"

        val phones = context.contentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns, null, null, orderBy)
        while (phones!!.moveToNext()) {
            val path = Uri.parse(phones.getString(phones.getColumnIndex(MediaStore.Images.Media.DATA)))

            images.add(ImageDataObj(File("" + path.getPath()).getName(), path.getPath()))

        }
        phones?.close()
    }
}
