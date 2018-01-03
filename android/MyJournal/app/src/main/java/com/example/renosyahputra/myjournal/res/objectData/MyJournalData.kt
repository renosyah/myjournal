package com.example.renosyahputra.myjournal.res.objectData

import java.io.Serializable
import java.util.*


public class MyJournalData : Serializable{

    private lateinit var  userDataObj: UserData
    private lateinit var  catatanObjs: ArrayList<CatatanData>
    private lateinit var  appSettingObj: AppSettings
    private var statusUpdate : Boolean = false

    fun getstatusUpdate(): Boolean {
        return statusUpdate
    }

    fun setstatusUpdate( statusUpdate : Boolean) {
        this.statusUpdate = statusUpdate
    }

    fun getUserDataObj(): UserData {
        return userDataObj
    }

    fun setUserDataObj(userDataObj: UserData) {
        this.userDataObj = userDataObj
    }

    fun getCatatanObjs(): ArrayList<CatatanData> {
        return catatanObjs
    }

    fun setCatatanObjs(catatanObjs: ArrayList<CatatanData>) {
        this.catatanObjs = catatanObjs
    }

    fun getAppSettingObj(): AppSettings {
        return appSettingObj
    }

    fun setAppSettingObj(appSettingObj: AppSettings) {
        this.appSettingObj = appSettingObj
    }

    fun DataObj(userDataObj: UserData, catatanObjs: ArrayList<CatatanData>, appSettingObj: AppSettings) {
        this.userDataObj = userDataObj
        this.catatanObjs = catatanObjs
        this.appSettingObj = appSettingObj
    }
}