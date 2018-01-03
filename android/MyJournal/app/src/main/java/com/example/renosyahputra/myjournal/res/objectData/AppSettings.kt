package com.example.renosyahputra.myjournal.res.objectData

import java.io.Serializable


public class AppSettings  : Serializable {
    private lateinit var Lang: String
    private lateinit var  StatusLoggin: String

    fun getLang(): String? {
        return Lang
    }

    fun setLang(lang: String) {
        Lang = lang
    }

    fun getStatusLoggin(): String? {
        return StatusLoggin
    }

    fun setStatusLoggin(statusLoggin: String) {
        StatusLoggin = statusLoggin
    }

    fun AppSettingObj(lang: String, statusLoggin: String){
        Lang = lang
        StatusLoggin = statusLoggin
    }
}