package com.example.renosyahputra.myjournal.res

import android.content.Context
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

        val dataAdapter = ArrayList<DetailCatatanData>()
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

        list_catatan_listview.setOnItemClickListener(this)
    }

    override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

    }
}