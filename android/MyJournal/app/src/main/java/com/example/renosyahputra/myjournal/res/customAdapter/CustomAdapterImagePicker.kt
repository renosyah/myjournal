package com.example.renosyahputra.myjournal.res.customAdapter

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.renosyahputra.myjournal.res.objectData.ImageDataObj
import com.squareup.picasso.Picasso
import java.io.File


class CustomAdapterImagePicker (context: Context, resource: Int, objects: MutableList<ImageDataObj>) : ArrayAdapter<ImageDataObj>(context, resource, objects){
    internal val context : Context = context
    internal val resource : Int = resource
    internal val objects : MutableList<ImageDataObj> = objects
    var imagePostId : Int = 0
    var imageNameId : Int = 0

    fun SetPostId(imagePostId :Int,imageNameId : Int){
        this.imageNameId = imageNameId
        this.imagePostId = imagePostId
    }


    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var row : View? = convertView
        var holder : DataList? = null

        if (row == null){
            val inflater = (context as Activity).layoutInflater
            row = inflater.inflate(resource,parent,false)
            holder = DataList()

            holder.image = row.findViewById(imagePostId)
            holder.name = row.findViewById(imageNameId)

            row.setTag(holder)
        }else{
            holder = (row.getTag() as DataList)
        }
        val item : ImageDataObj = getItem(position)
        Picasso.with(context).load(File(item.url)).resize(80,80).into(holder.image)
        holder.name.text = item.name

        return row!!
    }
    internal class DataList{
        lateinit var image : ImageView
        lateinit var name : TextView
    }
}