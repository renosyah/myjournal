package com.example.renosyahputra.myjournal.res.objectData

import java.io.Serializable

class DetailCatatanData : Serializable{
    internal lateinit var KodeCatatan: String
    internal lateinit var Type: String
    internal var Tanggal: Int = 0
    internal lateinit var Catatan: String
    internal var JumlahTotal: Int = 0


    fun getKodeCatatan(): String {
        return KodeCatatan
    }

    fun setKodeCatatan(kodeCatatan: String) {
        KodeCatatan = kodeCatatan
    }

    fun getType(): String {
        return Type
    }

    fun setType(type: String) {
        Type = type
    }

    fun getTanggal(): Int {
        return Tanggal
    }

    fun setTanggal(tanggal: Int) {
        Tanggal = tanggal
    }



    fun getCatatan(): String {
        return Catatan
    }

    fun setCatatan(catatan: String) {
        Catatan = catatan
    }

    fun getJumlahTotal(): Int {
        return JumlahTotal
    }

    fun setJumlahTotal(jumlahTotal: Int) {
        JumlahTotal = jumlahTotal
    }



    fun DetailCatatanData(kodeCatatan: String, type: String, tanggal: Int, catatan: String, jumlahTotal: Int) {
        KodeCatatan = kodeCatatan
        Type = type
        Tanggal = tanggal
        Catatan = catatan
        JumlahTotal = jumlahTotal
    }
}
