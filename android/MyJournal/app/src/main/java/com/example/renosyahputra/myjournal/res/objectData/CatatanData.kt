package com.example.renosyahputra.myjournal.res.objectData

import java.io.Serializable


class CatatanData  : Serializable {

    internal var KodeBulan: Int = 0
    internal  var Tahun: Int  = 0
    internal lateinit var Bulan: String
    internal lateinit var Detail : ArrayList<DetailCatatanData>


    fun setDetail(Detail : ArrayList<DetailCatatanData>){
        this.Detail = Detail
    }
    fun GetDetail() : ArrayList<DetailCatatanData>{
        return Detail
    }

    fun getKodeBulan(): Int {
        return KodeBulan
    }

    fun setKodeBulan(kodeBulan: Int) {
        KodeBulan = kodeBulan
    }

    fun getTahun(): Int {
        return Tahun
    }

    fun setTahun(tahun: Int) {
        Tahun = tahun
    }

    fun getBulan(): String {
        return Bulan
    }

    fun setBulan(bulan: String) {
        Bulan = bulan
    }


    fun CatatanObj( bulan: String, kodeBulan: Int, tahun: Int,Detail: ArrayList<DetailCatatanData>) {
        this.Bulan = bulan
        this.KodeBulan = kodeBulan
        this.Tahun = tahun
        this.Detail = Detail
    }
}