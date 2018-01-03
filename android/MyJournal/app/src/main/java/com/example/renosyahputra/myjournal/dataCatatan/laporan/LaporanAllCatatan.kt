package com.example.renosyahputra.myjournal.dataCatatan.laporan

import android.app.Fragment
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ListView
import com.example.renosyahputra.myjournal.R
import com.example.renosyahputra.myjournal.res.customAdapter.CustomAdapterDetailCatatan
import com.example.renosyahputra.myjournal.res.objectData.CatatanData
import com.example.renosyahputra.myjournal.res.objectData.DetailCatatanData


class LaporanAllCatatan : Fragment(),AdapterView.OnItemClickListener{
    lateinit var v : View
    lateinit var ctx : Context
    lateinit var list_catatan_listview : ListView
    lateinit var dataList : ArrayList<CatatanData>

    public fun SetdataList(dataList : ArrayList<CatatanData>){
        this.dataList = dataList
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        v = inflater!!.inflate(R.layout.listcatatan,container,false)
        InitiationWidget(v)
        return v
    }

    private fun InitiationWidget(v : View){
        ctx = activity!!
        list_catatan_listview = v.findViewById(R.id.list_catatan_listview) as ListView

        val dataAdapter = ArrayList<DetailCatatanData>()
        val yearAndMoons = ArrayList<CatatanData>()

        for (data in dataList.sortedWith(compareBy(CatatanData::getTahun,CatatanData::getKodeBulan))) {
            for (dataDetail in data.Detail) {
                dataAdapter.add(dataDetail)
                yearAndMoons.add(data)
            }
        }

        val adapter = CustomAdapterDetailCatatan(ctx,R.layout.custom_adapter_detail_catatan,dataAdapter)
        adapter.setYearAndMoons(yearAndMoons)
        adapter.setYearAndMoon("")
        list_catatan_listview.adapter = adapter
        list_catatan_listview.divider = null

        list_catatan_listview.setOnItemClickListener(this)
    }

    override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

    }

}