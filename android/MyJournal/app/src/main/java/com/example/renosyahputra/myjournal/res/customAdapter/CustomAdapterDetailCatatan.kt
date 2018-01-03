package com.example.renosyahputra.myjournal.res.customAdapter

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.renosyahputra.myjournal.R
import com.example.renosyahputra.myjournal.addCatatan.TypeCatatan
import com.example.renosyahputra.myjournal.res.objectData.CatatanData
import com.example.renosyahputra.myjournal.res.objectData.DetailCatatanData
import java.text.DecimalFormat


class CustomAdapterDetailCatatan(context: Context, resource: Int, objects: MutableList<DetailCatatanData>) : ArrayAdapter<DetailCatatanData>(context, resource, objects) {
    internal var context : Context = context
    internal var resources : Int = resource
    internal var objects : MutableList<DetailCatatanData> = objects
    val formatter = DecimalFormat("##,###")
    internal lateinit var YearAndMoon  : String
    internal lateinit var YearAndMoons  : ArrayList<CatatanData>

    fun setYearAndMoon(YearAndMoon  : String){
        this.YearAndMoon = YearAndMoon
    }
    fun setYearAndMoons(YearAndMoons  : ArrayList<CatatanData>){
        this.YearAndMoons = YearAndMoons
    }


    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var row : View? = convertView
        var holder : DataList? = null
        if (row == null){
            val inflater = (context as Activity).layoutInflater
            row = inflater.inflate(resources,parent,false)
            holder = DataList()

            holder.image_icon  = row.findViewById(R.id.detail_image)
            holder.keterangan = row.findViewById(R.id.detail_keterangan)
            holder.jumlah = row.findViewById(R.id.detail_jumlah)
            holder.tanggal = row.findViewById(R.id.detail_tanggal)

            row.setTag(holder)
        }else{
            holder = (row.getTag() as DataList)
        }
        val item = getItem(position)
        var color_jumlah = 0
        if (item.getType() == TypeCatatan.pemasukkan){
            color_jumlah = (context as Activity).resources.getColor(R.color.green)
        }else if (item.getType() == TypeCatatan.pengeluaran){
            color_jumlah = (context as Activity).resources.getColor(R.color.red)
        }
        holder.keterangan.setText(item.getCatatan())

        holder.jumlah.setText(formatter.format(item.getJumlahTotal()))
        holder.jumlah.setTextColor(color_jumlah)

        holder.tanggal.setText("" + item.getTanggal() + YearAndMoon)

        if(YearAndMoons.size >= 1) {

            holder.tanggal.setText("" + item.getTanggal() +" - "+ YearAndMoons.get(position)!!.getBulan()+" - "+YearAndMoons.get(position)!!.getTahun())

        }

        return row!!
    }

    internal class DataList{
        lateinit var image_icon : ImageView
        lateinit var keterangan : TextView
        lateinit var jumlah  : TextView
        lateinit var tanggal : TextView
    }
}