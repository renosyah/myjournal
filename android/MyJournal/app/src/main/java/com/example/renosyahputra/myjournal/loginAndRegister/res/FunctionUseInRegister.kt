package com.example.renosyahputra.myjournal.loginAndRegister.res

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.GridView
import android.widget.ImageView
import android.widget.TextView
import com.example.renosyahputra.myjournal.R
import com.example.renosyahputra.myjournal.res.customAdapter.CustomAdapterImagePicker
import com.example.renosyahputra.myjournal.res.objectData.ImageDataObj
import com.google.protobuf.ByteString
import com.squareup.picasso.Picasso
import java.io.File
import java.io.FileInputStream
import java.io.IOException


class FunctionUseInRegister{
    companion object {
        fun OpenImagePicker(ctx : Context,reasigByteString : ByteImageContainer,preview : ImageView,images : ArrayList<ImageDataObj>){


            val imagesFind = ArrayList<ImageDataObj>()

            val dialog = AlertDialog.Builder(ctx).create()
            val inflater  = (ctx as Activity).layoutInflater
            val v = inflater.inflate(R.layout.custom_alert_dialog_image_chooser,null)

            val title : TextView = v.findViewById(R.id.title_image_chooser)
            val imagegrid : GridView = v.findViewById(R.id.gridview_image_chooser)
            val find : EditText = v.findViewById(R.id.find_image_chooser)
            val cancel : TextView = v.findViewById(R.id.cancel_image_chooser)
            val adapter = CustomAdapterImagePicker(ctx, R.layout.custom_adapter_image_pick, images)
            adapter.SetPostId(R.id.image_image_picker,R.id.name_image_picker)
            imagegrid.setAdapter(adapter)

            imagegrid.setOnItemClickListener {
                adapterView, view, i, l ->
                if (imagesFind.size > 1){
                    reasigByteString.image = ChangeFileToImageBytes(File(imagesFind.get(i).url))
                    Picasso.with(ctx).load(File(imagesFind.get(i).url)).resize(100,100).into(preview)
                    preview.visibility = View.VISIBLE
                }else {
                    reasigByteString.image = ChangeFileToImageBytes(File(images.get(i).url))
                    Picasso.with(ctx).load(File(images.get(i).url)).resize(100,100).into(preview)
                    preview.visibility = View.VISIBLE
                }
                dialog.dismiss()
            }
            cancel.setOnClickListener {
                dialog.dismiss()
            }
            find.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

                }

                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                    if (images.size > 1) {
                        imagesFind.clear()

                        for (data in images) {
                            if (data.name.matches(("(?i).*" + find.text.toString() + "(.*)").toRegex())) {
                                imagesFind.add(data)
                            }
                        }
                    }
                    val adapter2 = CustomAdapterImagePicker(ctx, R.layout.custom_adapter_image_pick, imagesFind)
                    adapter2.SetPostId(R.id.image_image_picker,R.id.name_image_picker)
                    imagegrid.setAdapter(adapter2)
                }

                override fun afterTextChanged(s: Editable) {

                }
            })
            dialog.setView(v)
            dialog.show()
    }
        fun ChangeFileToImageBytes(f : File): ByteString{
            var bytes: ByteString? = null
            try {
                bytes = ByteString.readFrom(FileInputStream(f))
            } catch (e: IOException) {
                e.printStackTrace()
            }

            return bytes!!
        }
    }

}