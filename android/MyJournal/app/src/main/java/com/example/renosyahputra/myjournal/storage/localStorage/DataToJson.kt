package com.example.renosyahputra.myjournal.storage.localStorage

import com.example.renosyahputra.myjournal.res.objectData.MyJournalData
import org.json.JSONArray
import org.json.JSONObject

class DataToJson{
    internal lateinit var d : MyJournalData
    internal lateinit var s : String


    fun SetMyJournalData(d : MyJournalData,statusUpdate : Boolean){
        this.d = d

        val MainjsonObj = JSONObject()
        val MyJournalDataObj = JSONObject()

        val userDataObj = JSONObject()
        userDataObj.accumulate("Id",d.getUserDataObj().getId())
        userDataObj.accumulate("Nama",d.getUserDataObj().getNama())
        userDataObj.accumulate("Email",d.getUserDataObj().getEmail())
        userDataObj.accumulate("NomorTelp",d.getUserDataObj().getNomorTelp())
        userDataObj.accumulate("UrlImage",d.getUserDataObj().getUrlImage())

        val catatanDataArray = JSONArray()
        for (data in d.getCatatanObjs()!!){
            val catatanDataObj = JSONObject()
            val detailcatatanDataArray = JSONArray()

            for (detail in data.GetDetail()){
                val detailcatatanDataObj = JSONObject()
                detailcatatanDataObj.accumulate("KodeCatatan",detail.getKodeCatatan())
                detailcatatanDataObj.accumulate("Type",detail.getType())
                detailcatatanDataObj.accumulate("Tanggal",detail.getTanggal())
                detailcatatanDataObj.accumulate("Catatan",detail.getCatatan())
                detailcatatanDataObj.accumulate("JumlahTotal",detail.getJumlahTotal())

                detailcatatanDataArray.put(detailcatatanDataObj)
            }

            catatanDataObj.accumulate("KodeBulan",data.getKodeBulan())
            catatanDataObj.accumulate("Tahun",data.getTahun())
            catatanDataObj.accumulate("Bulan",data.getBulan())
            catatanDataObj.accumulate("Detail",detailcatatanDataArray)

            catatanDataArray.put(catatanDataObj)
        }
        val appSettingObj = JSONObject()
        appSettingObj.accumulate("Lang",d.getAppSettingObj()!!.getLang())
        appSettingObj.accumulate("StatusLoggin",d.getAppSettingObj()!!.getStatusLoggin())

        MyJournalDataObj.accumulate("UserData", userDataObj)
        MyJournalDataObj.accumulate("CatatanData",catatanDataArray)
        MyJournalDataObj.accumulate("AppSetting", appSettingObj)
        MyJournalDataObj.accumulate("statusUpdate",statusUpdate)

        MainjsonObj.accumulate("MyJournalData",MyJournalDataObj)

        this.s = MainjsonObj.toString()
    }
    fun GetMyJournalDataAsJson(): String{
        return s
    }
}
