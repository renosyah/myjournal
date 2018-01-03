package com.example.renosyahputra.myjournal.res.objectData

import java.io.Serializable


class UserData  : Serializable {
    internal lateinit var Id: String
    internal lateinit var Nama: String
    internal lateinit var Email: String
    internal lateinit var NomorTelp: String
    internal lateinit var UrlImage: String


    fun getId(): String {
        return Id
    }

    fun setId(id: String) {
        Id = id
    }

    fun getNama(): String {
        return Nama
    }

    fun setNama(nama: String) {
        Nama = nama
    }

    fun getEmail(): String {
        return Email
    }

    fun setEmail(email: String) {
        Email = email
    }

    fun getNomorTelp(): String {
        return NomorTelp
    }

    fun setNomorTelp(nomorTelp: String) {
        NomorTelp = nomorTelp
    }

    fun getUrlImage(): String {
        return UrlImage
    }

    fun setUrlImage(urlImage: String) {
        UrlImage = urlImage
    }

    fun UserDataObj(id: String, nama: String, email: String, nomorTelp: String, urlImage: String) {
        Id = id
        Nama = nama
        Email = email
        NomorTelp = nomorTelp
        UrlImage = urlImage
    }
}