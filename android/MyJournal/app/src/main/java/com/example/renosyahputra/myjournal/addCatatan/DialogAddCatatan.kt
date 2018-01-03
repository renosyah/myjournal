package com.example.renosyahputra.myjournal.addCatatan

import android.app.Activity
import android.app.FragmentManager
import android.content.Context
import android.content.DialogInterface
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.design.widget.NavigationView
import android.support.v7.app.AlertDialog
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import com.example.renosyahputra.myjournal.R
import com.example.renosyahputra.myjournal.dataCatatan.pemasukan.PemasukanFrame
import com.example.renosyahputra.myjournal.dataCatatan.pengeluaran.PengeluaranFrame
import com.example.renosyahputra.myjournal.res.IdGenerator
import com.example.renosyahputra.myjournal.res.objectData.CatatanData
import com.example.renosyahputra.myjournal.res.objectData.DetailCatatanData
import com.example.renosyahputra.myjournal.res.objectData.MyJournalData
import com.example.renosyahputra.myjournal.storage.grpc.catatan.SendUpdateDataJournal
import com.example.renosyahputra.myjournal.ui.LangSetting
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import java.text.DateFormatSymbols
import java.util.*
import kotlin.collections.ArrayList




class DialogAddCatatan {
    companion object {
        @RequiresApi(Build.VERSION_CODES.KITKAT)
        fun OpenAddCatatanForm(ctx : Context,data : MyJournalData,FragmentChanger : FragmentManager,navigationView : NavigationView,toolbar : Toolbar,pemasukanFrame  : PemasukanFrame,pengeluaranFrame : PengeluaranFrame){

            val detailData = DetailCatatanData()
            var codeBulan : Int = 0
            var tahun : Int = 0

            val lang  = LangSetting(ctx)
            lang.CheckLangSetting()


            val dialog  = AlertDialog.Builder(ctx).create()
            val inflater : LayoutInflater = (ctx as Activity).layoutInflater
            val v = inflater.inflate(R.layout.custom_alert_dialog_add_catatan,null)

            val title_toolbar : Toolbar= v.findViewById(R.id.title_add_catatan)
            title_toolbar.setTitle("Form Tambah Catatan")

            val type  = v.findViewById<TextView>(R.id.typecatatan)
            type.setOnClickListener {
                val option = arrayOf<CharSequence>(TypeCatatan.pemasukkan,TypeCatatan.pengeluaran)
                val choosetype = AlertDialog.Builder(ctx).setTitle("Tipe Catatan")
                        .setItems(option, DialogInterface.OnClickListener { dialogInterface, i ->
                            detailData.setType(option.get(i).toString())
                            type.setText(option.get(i).toString())
                        })
                choosetype.create().show()
            }

            val bulan  = v.findViewById<TextView>(R.id.addbulan)
            bulan.isEnabled = true
            bulan.setOnClickListener {
                val now = Calendar.getInstance()
                val dpl = DatePickerDialog.newInstance(
                        DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->

                            detailData.setTanggal(dayOfMonth)
                            codeBulan = (monthOfYear + 1)
                            bulan.setText(""+ dayOfMonth +"-"+  (monthOfYear + 1) +"-"+ year)
                            tahun = year
                        },
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                )
                dpl.setVersion(DatePickerDialog.Version.VERSION_2)
                dpl.setAccentColor(ctx.resources.getColor(R.color.colorPrimary))
                dpl.show(ctx.fragmentManager,"Datepickerdialog")
            }
            val default_today : CheckBox = v.findViewById(R.id.checkBox_default_today)
            default_today.visibility = View.GONE
            default_today.isChecked = false
            default_today.setOnClickListener {
                bulan.isEnabled = !default_today.isChecked
            }

            val keterangan  = v.findViewById<TextView>(R.id.addketerangan)
            val total  = v.findViewById<TextView>(R.id.addtotal)
            val add  = v.findViewById<Button>(R.id.addbuttoncatatan)
            val batal = v.findViewById<Button>(R.id.bataladd)

            add.setOnClickListener {

                val idGenerator = IdGenerator()
                idGenerator.CreateRandomString((codeBulan + 10))

                detailData.setCatatan(keterangan.text.toString())
                detailData.setJumlahTotal(Integer.parseInt(total.text.toString()))
                detailData.setKodeCatatan(idGenerator.GetId())

                if (data.getCatatanObjs().size < 1) {

                    val newCatatan = CatatanData()
                    val detailCatatan = ArrayList<DetailCatatanData>()

                    detailCatatan.add(detailData)
                    newCatatan.setDetail(detailCatatan)

                    newCatatan.setKodeBulan(codeBulan)
                    newCatatan.setBulan(DateFormatSymbols().months[codeBulan - 1])
                    newCatatan.setTahun(tahun)

                    data.getCatatanObjs().add(newCatatan)
                }else {
                    var checkAda = false
                    var post = 0
                    for (i in 0..(data.getCatatanObjs().size -1)){
                        val d = data.getCatatanObjs().get(i)
                        if (d.getKodeBulan() == codeBulan && d.getTahun() == tahun){
                            post = i
                            checkAda = d.getKodeBulan() == codeBulan && d.getTahun() == tahun

                            break
                        }
                    }

                    if (checkAda){
                        AddDetailInCatatanBulan(data, detailData, post)

                    } else {
                        AddNewcatatan(data, detailData, codeBulan, tahun)
                    }
                }


                if (detailData.getType() == TypeCatatan.pemasukkan){

                    FragmentChanger.beginTransaction().replace(R.id.main_frame_fragment,pemasukanFrame).commit()

                    navigationView.menu.getItem(1).setChecked(true)
                    navigationView.menu.getItem(1).setTitle(lang.GetmainActivityLangText().getMenuPendapatanText())
                    toolbar.setTitle(lang.GetmainActivityLangText().getMenuPendapatanText())

                }else if (detailData.getType() == TypeCatatan.pengeluaran){

                    FragmentChanger.beginTransaction().replace(R.id.main_frame_fragment,pengeluaranFrame).commit()

                    navigationView.menu.getItem(2).setChecked(true)
                    navigationView.menu.getItem(2).setTitle(lang.GetmainActivityLangText().getMenuPengeluaranText())
                    toolbar.setTitle(lang.GetmainActivityLangText().getMenuPengeluaranText())
                }


                SendUpdateDataJournal(ctx,data).execute()

                dialog.dismiss()

            }
            batal.setOnClickListener {
                dialog.dismiss()
            }

            dialog.setView(v)
            dialog.show()

        }

        fun AddDetailInCatatanBulan(d : MyJournalData,detailData : DetailCatatanData,pos : Int){
            d.getCatatanObjs().get(pos).GetDetail().add(detailData)
        }
        fun AddNewcatatan(data : MyJournalData,detailData : DetailCatatanData,codeBulan : Int,tahun : Int){
            val newCatatan = CatatanData()
            val detailCatatan = ArrayList<DetailCatatanData>()

            detailCatatan.add(detailData)
            newCatatan.setDetail(detailCatatan)

            newCatatan.setKodeBulan(codeBulan)
            newCatatan.setBulan(DateFormatSymbols().months[codeBulan - 1])
            newCatatan.setTahun(tahun)

            data.getCatatanObjs().add(newCatatan)
        }
    }
}