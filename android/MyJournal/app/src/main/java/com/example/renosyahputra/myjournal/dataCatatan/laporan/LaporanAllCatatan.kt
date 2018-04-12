package com.example.renosyahputra.myjournal.dataCatatan.laporan

import android.app.Fragment
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.TextView
import com.example.renosyahputra.myjournal.R
import com.example.renosyahputra.myjournal.dataCatatan.laporan.res.FunctionInLaporanFragment
import com.example.renosyahputra.myjournal.res.objectData.CatatanData

class LaporanAllCatatan : Fragment(),View.OnClickListener{


    lateinit var v : View
    lateinit var ctx : Context
    lateinit var periodeSet : TextView
    lateinit var saldoAwal : TextView
    lateinit var saldoAkhir : TextView
    lateinit var list_catatan_listview : ListView
    lateinit var dataList : ArrayList<CatatanData>
    lateinit var LayoitListLaporan : LinearLayout

    fun SetdataList(dataList : ArrayList<CatatanData>){
        this.dataList = dataList
    }


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        v = inflater!!.inflate(R.layout.laporan_periode,container,false)
        InitiationWidget(v)
        return v
    }


    private fun InitiationWidget(v : View){
        ctx = activity!!

        periodeSet = v.findViewById(R.id.LaporanPeriodeSet) as TextView
        saldoAwal = v.findViewById(R.id.saldoAwal) as TextView
        saldoAkhir = v.findViewById(R.id.SaldoAkhir) as TextView
        LayoitListLaporan = v.findViewById(R.id.layoutlistDetailLaporan)
        list_catatan_listview = v.findViewById(R.id.listDetailLaporan) as ListView

        periodeSet.setOnClickListener(this)

        DefaultLaporanByperiode()
    }

    private fun ShowPeriode(){
        val allPeriod = FunctionInLaporanFragment.GetAllPeriodeInGroup(dataList)
        FunctionInLaporanFragment.ShowAllPeriod(ctx,allPeriod,dataList,list_catatan_listview,periodeSet,LayoitListLaporan,saldoAkhir,saldoAwal)
    }


    private fun DefaultLaporanByperiode(){
        val now = java.util.Calendar.getInstance()
        val year = now.get(java.util.Calendar.YEAR)
        FunctionInLaporanFragment.setListLaporanByPeriode(ctx,year,dataList,list_catatan_listview,LayoitListLaporan,saldoAkhir,saldoAwal)
        periodeSet.setText("Periode "+year.toString())


    }


    override fun onClick(p0: View?) {
        if (p0 == periodeSet){
            ShowPeriode()
        }
    }

}