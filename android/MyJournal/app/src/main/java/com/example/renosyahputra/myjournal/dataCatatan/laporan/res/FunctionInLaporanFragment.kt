package com.example.renosyahputra.myjournal.dataCatatan.laporan.res

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.view.View
import android.widget.*
import com.example.renosyahputra.myjournal.R
import com.example.renosyahputra.myjournal.addCatatan.TypeCatatan
import com.example.renosyahputra.myjournal.res.EditCatatan
import com.example.renosyahputra.myjournal.res.customAdapter.CustomAdapterLaporan
import com.example.renosyahputra.myjournal.res.objectData.CatatanData
import java.text.DecimalFormat


class FunctionInLaporanFragment{

companion object {
    val formatter = DecimalFormat("##,###")
    fun GetAllPeriodeInGroup(data: ArrayList<CatatanData>): ArrayList<Int> {
        val firstValue = ArrayList<Int>()
        for (tahun in data.sortedWith(compareBy(CatatanData::getTahun))) {
            firstValue.add(tahun.getTahun())
        }
        val secondValue = ArrayList<Int>()
        for (tahun in ArrayList<Int>(HashSet<Int>(firstValue)).sortedWith(compareBy({ it }))){
            secondValue.add(tahun)
        }
        return secondValue

    }

    fun ShowAllPeriod(ctx: Context, d: ArrayList<Int>, dataList: ArrayList<CatatanData>, list_laporan: ListView, t: TextView,LayoitListLaporan : LinearLayout,saldoak : TextView,saldoseb : TextView) {
        val dialog = AlertDialog.Builder(ctx).create()
        val inflater = (ctx as Activity).layoutInflater
        val v = inflater.inflate(R.layout.custom_alert_dialog_pilih_periode, null)

        val list: ListView = v.findViewById(R.id.list_periode)
        val batal : TextView = v.findViewById(R.id.batal_periode)
        list.adapter = ArrayAdapter<Int>(ctx, android.R.layout.simple_dropdown_item_1line, d)

        list.setOnItemClickListener(AdapterView.OnItemClickListener { adapterView, view, i, l ->
            setListLaporanByPeriode(ctx, d.get(i), dataList, list_laporan,LayoitListLaporan,saldoak,saldoseb)
            t.setText("Periode " + d.get(i).toString())
            dialog.dismiss()
        })
        batal.setOnClickListener(View.OnClickListener {
            dialog.dismiss()
        })

        dialog.setView(v)
        dialog.show()
    }

    fun setListLaporanByPeriode(ctx: Context, periode: Int, catatan: ArrayList<CatatanData>, list_laporan: ListView,LayoitListLaporan : LinearLayout,saldoak : TextView,saldoseb : TextView) {
        val newCatatanData = ArrayList<CatatanData>()
        val catatanPeriodeSebelum = ArrayList<CatatanData>()
        for (d in catatan.sortedWith(compareBy(CatatanData::getKodeBulan))) {
            if (d.getTahun() == periode) {
                newCatatanData.add(d)
            }
            if (d.getTahun() == (periode - 1)){
                catatanPeriodeSebelum.add(d)
            }
        }
        saldoak.setText("Saldo Akhir Periode : "+formatter.format(setTotalForPeriode(catatanPeriodeSebelum) + setTotalForPeriode(newCatatanData)))
        saldoseb.setText("Saldo Awal Periode : "+formatter.format(setTotalForPeriode(catatanPeriodeSebelum)))

        val adapter = CustomAdapterLaporan(ctx, R.layout.custom_adapter_laporan, newCatatanData)
        adapter.setOriginalCatatan(catatan)
        adapter.setUsingInAdapter(newCatatanData,catatanPeriodeSebelum)
        adapter.setTotal(saldoak)
        list_laporan.adapter = adapter
        LayoitListLaporan.layoutParams.height = FunctionInLaporanFragment.getListViewTotalHeight(list_laporan) + 100
        list_laporan.divider = null
        list_laporan.setOnItemClickListener(AdapterView.OnItemClickListener { adapterView, view, i, l ->

            if (newCatatanData.size >= 1){
                AlertDialog.Builder(ctx)
                        .setTitle("Catatan")
                        .setMessage("Hapus Catatan diBulan "+ newCatatanData.get(i).getBulan() +" "+newCatatanData.get(i).getTahun())
                        .setNegativeButton("Hapus", DialogInterface.OnClickListener { dialogInterface, posd ->
                            catatan.removeAt(EditCatatan.FindPositionCatatan(catatan,newCatatanData.get(i).getKodeBulan(),newCatatanData.get(i).getTahun()))
                            newCatatanData.clear()
                            for (d in catatan.sortedWith(compareBy(CatatanData::getKodeBulan))) {
                                if (d.getTahun() == periode) {
                                    newCatatanData.add(d)
                                }
                            }
                            list_laporan.adapter = adapter
                            saldoak.setText("Saldo Akhir Periode : "+formatter.format(setTotalForPeriode(newCatatanData)))
                        })
                        .create()
                        .show()
            }
        })
    }



    fun getListViewTotalHeight(listView: ListView): Int {
        val mAdapter = listView.adapter

        var totalHeight = 0

        for (i in 0 until mAdapter.count) {
            val mView = mAdapter.getView(i, null, listView)

            mView.measure(
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),

                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED))

            totalHeight += mView.measuredHeight


        }

        val params = listView.layoutParams
        params.height = totalHeight + listView.dividerHeight * (mAdapter.count - 1)
        listView.layoutParams = params
        listView.requestLayout()

        return totalHeight
    }

    fun setTotalForPeriode(dataList : ArrayList<CatatanData>) : Int {
        var total  = 0
        for (data in dataList){
            for (detail in data.GetDetail()){
                if (detail.getType() == TypeCatatan.pemasukkan){
                    total = total + detail.getJumlahTotal()

                }else if (detail.getType() == TypeCatatan.pengeluaran){
                    total = total - detail.getJumlahTotal()
                }
            }
        }
        return total
    }

}

}