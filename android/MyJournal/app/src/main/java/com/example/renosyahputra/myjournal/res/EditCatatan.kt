package com.example.renosyahputra.myjournal.res

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.support.v7.app.AlertDialog
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import com.example.renosyahputra.myjournal.R
import com.example.renosyahputra.myjournal.addCatatan.TypeCatatan
import com.example.renosyahputra.myjournal.res.customAdapter.CustomAdapterLaporan
import com.example.renosyahputra.myjournal.res.objectData.CatatanData
import com.example.renosyahputra.myjournal.res.objectData.DetailCatatanData


class EditCatatan {
    companion object {
        fun FindPositionCatatan(d : ArrayList<CatatanData>,KodeBulan : Int,KodeTahun : Int) : Int{
            var pos = 0
            for (i in 0..(d.size -1)){
                val data = d.get(i)
                if (data.getKodeBulan() == KodeBulan && data.getTahun() == KodeTahun){
                    pos = i
                    break
                }
            }
            return pos
        }


        fun EditDetailByPosCatatan(d : ArrayList<CatatanData>,FindByCatatan : CatatanData,newData : DetailCatatanData){
            val posCatatan = FindPositionCatatan(d,FindByCatatan.getKodeBulan(), FindByCatatan.getTahun())
            val posEdit = d.get(posCatatan).GetDetail()
            var Ketemu = false
            var posisi = 0
            for (i in 0..(posEdit.size-1)){
                val data = posEdit.get(i)
                if (data.getKodeCatatan() == newData.getKodeCatatan()){
                    Ketemu = true
                    posisi = i
                    break
                }
            }
            if (Ketemu){
                val ganti = posEdit.get(posisi)
                ganti.setType(newData.getType())
                ganti.setTanggal(newData.getTanggal())
                ganti.setCatatan(newData.getCatatan())
                ganti.setJumlahTotal(newData.getJumlahTotal())
            }
        }
        fun DeleteDetailByPosCatatan(d : ArrayList<CatatanData>,FindByCatatan : CatatanData,newData : DetailCatatanData){
            val posCatatan = FindPositionCatatan(d,FindByCatatan.getKodeBulan(), FindByCatatan.getTahun())
            val posEdit = d.get(posCatatan).GetDetail()
            var Ketemu = false
            var posisi = 0
            for (i in 0..(posEdit.size-1)){
                val data = posEdit.get(i)
                if (data.getKodeCatatan() == newData.getKodeCatatan()){
                    Ketemu = true
                    posisi = i
                    break
                }
            }
            if (Ketemu){
                posEdit.removeAt(posisi)
            }
        }

        fun FindPositionDetail(d : ArrayList<DetailCatatanData>,c : DetailCatatanData) : Int{
            var pos = 0
            for (i in 0..(d.size -1)){
                val data = d.get(i)
                if (data.getKodeCatatan() == c.getKodeCatatan()){
                    pos = i
                    break
                }
            }
            return pos
        }
        fun EditDetailByPosDetailCatatan(d : ArrayList<DetailCatatanData>,c : DetailCatatanData){
            val posCatatan = FindPositionDetail(d,c)
            val posEdit = d.get(posCatatan)
            posEdit.setType(c.getType())
            posEdit.setTanggal(c.getTanggal())
            posEdit.setCatatan(c.getCatatan())
            posEdit.setJumlahTotal(c.getJumlahTotal())

        }
        fun DeleteDetailPosDetailCatatan(d : ArrayList<DetailCatatanData>,c : DetailCatatanData){
            val posCatatan = FindPositionDetail(d,c)
            d.removeAt(posCatatan)
        }

        fun EditDialogByDetailCatatanForListCatatan(ctx : Context,d : ArrayList<DetailCatatanData>,c : DetailCatatanData,dataAdapter : ArrayList<DetailCatatanData>,list_catatan_listview : ListView ,type : String, YearAndMoon : String){

            val detailData = DetailCatatanData()

            val dialog = AlertDialogEditDetailCatatan(ctx,c)
            dialog.setView()
            detailData.setType(c.getType())

            dialog.Type().setOnClickListener {
                val option = arrayOf<CharSequence>(TypeCatatan.pemasukkan, TypeCatatan.pengeluaran)
                val choosetype = AlertDialog.Builder(ctx).setTitle("Tipe Catatan")
                        .setItems(option, DialogInterface.OnClickListener { dialogInterface, i ->
                            detailData.setType(option.get(i).toString())
                            dialog.type.setText(option.get(i).toString())
                        })
                choosetype.create().show()
            }
            dialog.Keterangan().setText(c.getCatatan())
            dialog.Total().setText(c.getJumlahTotal().toString())

            dialog.Add().setOnClickListener {

                detailData.setTanggal(c.getTanggal())
                detailData.setKodeCatatan(c.getKodeCatatan())
                detailData.setCatatan(dialog.Keterangan().text.toString())
                detailData.setJumlahTotal(Integer.parseInt(dialog.Total().text.toString()))

                EditDetailByPosDetailCatatan(d,detailData)
                ListCatatan.SetListCatatanAdapter(ctx, d, dataAdapter, list_catatan_listview, type, YearAndMoon)

                dialog.GetDialog().dismiss()

            }
            dialog.Batal().setOnClickListener {
                dialog.GetDialog().dismiss()
            }

            dialog.GetDialog().show()

        }

        fun EditDialogByDetailCatatanForAdapter(ctx : Context,d : ArrayList<DetailCatatanData>,c : DetailCatatanData,item : CatatanData,holder : CustomAdapterLaporan.Companion.DataList){
            val detailData = DetailCatatanData()

            val dialog = AlertDialogEditDetailCatatan(ctx,c)
            dialog.setView()
            detailData.setType(c.getType())

            dialog.Type().setOnClickListener {
                val option = arrayOf<CharSequence>(TypeCatatan.pemasukkan, TypeCatatan.pengeluaran)
                val choosetype = AlertDialog.Builder(ctx).setTitle("Tipe Catatan")
                        .setItems(option, DialogInterface.OnClickListener { dialogInterface, i ->
                            detailData.setType(option.get(i).toString())
                            dialog.type.setText(option.get(i).toString())
                        })
                choosetype.create().show()
            }
            dialog.Keterangan().setText(c.getCatatan())
            dialog.Total().setText(c.getJumlahTotal().toString())

            dialog.Add().setOnClickListener {

                detailData.setTanggal(c.getTanggal())
                detailData.setKodeCatatan(c.getKodeCatatan())
                detailData.setCatatan(dialog.Keterangan().text.toString())
                detailData.setJumlahTotal(Integer.parseInt(dialog.Total().text.toString()))

                EditDetailByPosDetailCatatan(d,detailData)
                CustomAdapterLaporan.SetAdapterListDetailLaporan(ctx,item,holder,d)

                dialog.GetDialog().dismiss()

            }
            dialog.Batal().setOnClickListener {
                dialog.GetDialog().dismiss()
            }

            dialog.GetDialog().show()
        }



        private class AlertDialogEditDetailCatatan(){
            internal lateinit var dialog : AlertDialog
            internal lateinit var ctx : Context
            internal lateinit var c : DetailCatatanData
            internal lateinit var add : Button
            internal lateinit var  batal : Button
            internal lateinit var type : TextView
            internal lateinit var bulan : TextView
            internal lateinit var default_today : CheckBox
            internal lateinit var keterangan : EditText
            lateinit var total : EditText

            constructor(ctx : Context,c: DetailCatatanData) : this() {
                this.dialog = AlertDialog.Builder(ctx).create()
                this.ctx = ctx
                this.c = c

            }
            fun GetDialog() : AlertDialog {
                return dialog
            }

            fun setView(){

                val inflater : LayoutInflater = (ctx as Activity).layoutInflater
                val v = inflater.inflate(R.layout.custom_alert_dialog_add_catatan,null)

                val title_toolbar : Toolbar = v.findViewById(R.id.title_add_catatan)
                title_toolbar.setTitle("Form Edit Catatan")

                type  = v.findViewById<TextView>(R.id.typecatatan)
                type.setText(c.getType())

                bulan  = v.findViewById<TextView>(R.id.addbulan)
                bulan.visibility = View.GONE

                default_today = v.findViewById(R.id.checkBox_default_today)
                default_today.visibility = View.GONE

                keterangan  = v.findViewById(R.id.addketerangan)
                keterangan.setText(c.getCatatan())
                total  = v.findViewById(R.id.addtotal)
                total.setText(c.getJumlahTotal().toString())

                add  = v.findViewById(R.id.addbuttoncatatan) as Button
                add.setText("Simpan")

                batal = v.findViewById(R.id.bataladd) as Button

                dialog.setView(v)
            }

            fun Add() : Button{
                return add
            }
            fun Batal() : Button{
                return batal
            }
            fun Type() : TextView {
                return type
            }
            fun Bulan() : TextView {
                return bulan
            }
            fun Keterangan() : EditText {
                return keterangan
            }
            fun Total() : EditText{
                return total
            }
        }
    }
}