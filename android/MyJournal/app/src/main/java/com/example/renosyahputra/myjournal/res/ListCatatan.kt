package com.example.renosyahputra.myjournal.res

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ListView
import com.example.renosyahputra.myjournal.R
import com.example.renosyahputra.myjournal.res.customAdapter.CustomAdapterDetailCatatan
import com.example.renosyahputra.myjournal.res.objectData.CatatanData
import com.example.renosyahputra.myjournal.res.objectData.DetailCatatanData


class ListCatatan : Fragment(),AdapterView.OnItemClickListener {

    lateinit var v : View
    lateinit var ctx : Context
    lateinit var list_catatan_listview : ListView
    lateinit var dataList : ArrayList<DetailCatatanData>
    lateinit var type : String
    val dataAdapter = ArrayList<DetailCatatanData>()
    internal lateinit var YearAndMoon  : String

    fun setYearAndMoon(YearAndMoon  : String){
        this.YearAndMoon = YearAndMoon
    }

    public fun SetdataList(dataList : ArrayList<DetailCatatanData>){
        this.dataList = dataList
    }


    public fun SetCatatanType(type : String){
        this.type = type
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        v = inflater.inflate(R.layout.listcatatan,container,false)
        InitiationWidget(v)
        return v
    }

    private fun InitiationWidget(v : View){
        ctx = activity!!
        list_catatan_listview = v.findViewById(R.id.list_catatan_listview)
        SetListCatatanAdapter(ctx,dataList,dataAdapter,list_catatan_listview,type,YearAndMoon)

        list_catatan_listview.setOnItemClickListener(this)
    }


    override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
            if(dataAdapter.size >= 1){
                var message = dataAdapter.get(p2).getType() + "\n" + dataAdapter.get(p2).getCatatan()
                message = message + "\n" + dataAdapter.get(p2).getJumlahTotal()

                AlertDialog.Builder(context)
                        .setTitle("Detail Catatan")
                        .setMessage(message)
                        .setPositiveButton("Hapus", DialogInterface.OnClickListener { dialogInterface, i ->
                            EditCatatan.DeleteDetailPosDetailCatatan(dataList,dataAdapter.get(p2))
                            SetListCatatanAdapter(ctx,dataList,dataAdapter,list_catatan_listview,type,YearAndMoon)
                        })
                        .setNegativeButton("Edit", DialogInterface.OnClickListener { dialogInterface, i ->
                            EditCatatan.EditDialogByDetailCatatanForListCatatan(ctx,dataList,dataAdapter.get(p2),dataAdapter,list_catatan_listview,type,YearAndMoon)
                        })
                        .create()
                        .show()

            }
    }
companion object {

    fun SetListCatatanAdapter(ctx : Context, dataList: ArrayList<DetailCatatanData>, dataAdapter : ArrayList<DetailCatatanData>,list_catatan_listview : ListView,type: String,YearAndMoon: String){
        dataAdapter.clear()
        for (data in dataList){
            if (data.getType() == type) {
                dataAdapter.add(data)
            }
        }
        dataAdapter.sortedWith(compareBy(DetailCatatanData::getTanggal))

        val adapter = CustomAdapterDetailCatatan(ctx,R.layout.custom_adapter_detail_catatan,dataAdapter)
        adapter.setYearAndMoon(YearAndMoon)
        adapter.setYearAndMoons(ArrayList<CatatanData>())
        list_catatan_listview.adapter = adapter
        list_catatan_listview.divider = null
    }
}
}