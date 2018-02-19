package com.example.renosyahputra.myjournal.res.customAdapter

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.renosyahputra.myjournal.R
import com.example.renosyahputra.myjournal.addCatatan.TypeCatatan
import com.example.renosyahputra.myjournal.dataCatatan.laporan.res.FunctionInLaporanFragment
import com.example.renosyahputra.myjournal.res.EditCatatan
import com.example.renosyahputra.myjournal.res.objectData.CatatanData
import com.example.renosyahputra.myjournal.res.objectData.DetailCatatanData
import java.text.DecimalFormat

class CustomAdapterLaporan(context: Context, resource: Int, objects: MutableList<CatatanData>) : ArrayAdapter<CatatanData>(context, resource, objects) {
    internal var context: Context = context
    internal var resources: Int = resource
    internal var objects: MutableList<CatatanData> = objects
    internal lateinit var OriginalCatatan: ArrayList<CatatanData>
    internal lateinit var UsingInAdapter: ArrayList<CatatanData>
    internal lateinit var Total : TextView
    val formatter = DecimalFormat("##,###")

    fun setOriginalCatatan(OriginalCatatan: ArrayList<CatatanData>) {
        this.OriginalCatatan = OriginalCatatan
    }

    fun setTotal(Total : TextView){
        this.Total = Total
    }
    fun setUsingInAdapter(UsingInAdapter: ArrayList<CatatanData>){
        this.UsingInAdapter = UsingInAdapter
    }


    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var row = convertView
        var holder: DataList? = null
        if (row == null) {
            val inflater = (context as Activity).layoutInflater
            row = inflater.inflate(resources, parent, false)
            holder = DataList()

            holder.listLaporanLayout = row.findViewById<LinearLayout>(R.id.linear_layout_detail_laporan)
            holder.listLaporan = row.findViewById(R.id.list_detail_laporan)
            holder.bulan = row.findViewById<TextView>(R.id.nama_bulan_detail_laporan)

            row.setTag(holder)
        } else {
            holder = (row.getTag() as DataList)
        }
        val item = getItem(position)
        val newDetail = ArrayList<DetailCatatanData>()

        holder.bulan.setText(item.getBulan())

        for (n in item.GetDetail().sortedWith(compareBy(DetailCatatanData::getTanggal))) {
            newDetail.add(n)
        }
        SetAdapterListDetailLaporan(context,item, holder,newDetail)

        holder.listLaporan.setOnItemClickListener(AdapterView.OnItemClickListener { adapterView, view, i, l ->

            val dataAdapter = newDetail.get(i)
            var message = dataAdapter.getType() + "\n" + dataAdapter.getCatatan()
            message = message + "\n" + dataAdapter.getJumlahTotal()

            AlertDialog.Builder(context)
                    .setTitle("Detail Catatan")
                    .setMessage(message)
                    .setPositiveButton("Hapus", DialogInterface.OnClickListener { dialogInterface, posd ->
                        EditCatatan.DeleteDetailPosDetailCatatan(item.GetDetail(),newDetail.get(i))
                        SetAdapterListDetailLaporan(context,item, holder,newDetail)
                        Total.setText("Saldo Akhir Periode : "+ formatter.format(FunctionInLaporanFragment.setTotalForPeriode(UsingInAdapter)))

                    })
                    .setNegativeButton("Edit", DialogInterface.OnClickListener { dialogInterface, posd ->
                        EditCatatan.EditDialogByDetailCatatanForAdapter(context, newDetail, dataAdapter,item,holder)
                        Total.setText("Saldo Akhir Periode : "+ formatter.format(FunctionInLaporanFragment.setTotalForPeriode(UsingInAdapter)))

                    })
                    .create()
                    .show()
        })

        return row!!
    }

    companion object {

    class DataList {
        lateinit var listLaporanLayout: LinearLayout
        lateinit var listLaporan: ListView
        lateinit var bulan: TextView
    }

    fun SetAdapterListDetailLaporan(context : Context,item: CatatanData, holder: DataList,newDetail : ArrayList<DetailCatatanData>) {
        newDetail.clear()
        for (n in item.GetDetail().sortedWith(compareBy(DetailCatatanData::getTanggal))) {
            newDetail.add(n)
        }
        val adapter = CustomAdapterDetailCatatan(context, R.layout.custom_adapter_detail_catatan, newDetail)
        adapter.setYearAndMoon(" - " + item.getBulan() + " - " + item.getTahun())
        adapter.setYearAndMoons(ArrayList<CatatanData>())
        holder.listLaporan.adapter = adapter
        holder.listLaporan.divider = null
        holder.listLaporanLayout.layoutParams.height = FunctionInLaporanFragment.getListViewTotalHeight(holder.listLaporan)
    }
}

    private fun GetTotal(d : MutableList<CatatanData>,position: Int) : Int{
        var total = 0
        try {
            for (data in d.get(position).GetDetail()){
                if (data.getType() == TypeCatatan.pemasukkan){
                    total = total + data.getJumlahTotal()

                }else if (data.getType() == TypeCatatan.pengeluaran){
                    total = total - data.getJumlahTotal()
                }

            }
        }catch (e : IndexOutOfBoundsException){
            total = 0
        }
        return total
    }
}