package com.example.renosyahputra.myjournal.storage.localStorage

import com.example.renosyahputra.myjournal.res.objectData.*
import org.json.JSONObject


class JsonToData(s : String){
    val DataJson = s
    val d = MyJournalData()

    fun SetMyJournalData(){

        val MainjsonObj = JSONObject(DataJson)
        val MyJournalDataObj = MainjsonObj.getJSONObject("MyJournalData")

        val userDataObj = MyJournalDataObj.getJSONObject("UserData")
        val UserData = UserData()
        UserData.setId(userDataObj.getString("Id"))
        UserData.setNama(userDataObj.getString("Nama"))
        UserData.setEmail(userDataObj.getString("Email"))
        UserData.setNomorTelp(userDataObj.getString("NomorTelp"))
        UserData.setUrlImage(userDataObj.getString("UrlImage"))

        val catatanDataArray = MyJournalDataObj.getJSONArray("CatatanData")
        val CatatanDataList = ArrayList<CatatanData>()

        for (i in 0..(catatanDataArray.length() - 1)){
            val catatanDataObj = CatatanData()
            val catatanDataObjJson = catatanDataArray.getJSONObject(i)
            val dataInArrayCatatan =  catatanDataObjJson.getJSONArray("Detail")
            val DetailCatatanDataList = ArrayList<DetailCatatanData>()

            for (o in 0..(dataInArrayCatatan.length() - 1)){
                val detailcatatanDataObj = DetailCatatanData()
                val dataInArrayDetail = dataInArrayCatatan.getJSONObject(o)

                detailcatatanDataObj.setKodeCatatan(dataInArrayDetail.getString("KodeCatatan"))
                detailcatatanDataObj.setType(dataInArrayDetail.getString("Type"))
                detailcatatanDataObj.setTanggal(dataInArrayDetail.getInt("Tanggal"))
                detailcatatanDataObj.setCatatan(dataInArrayDetail.getString("Catatan"))
                detailcatatanDataObj.setJumlahTotal(dataInArrayDetail.getInt("JumlahTotal"))

                DetailCatatanDataList.add(detailcatatanDataObj)
            }

            catatanDataObj.setKodeBulan(catatanDataObjJson.getInt("KodeBulan"))
            catatanDataObj.setTahun(catatanDataObjJson.getInt("Tahun"))
            catatanDataObj.setBulan(catatanDataObjJson.getString("Bulan"))
            catatanDataObj.setDetail(DetailCatatanDataList)

            CatatanDataList.add(catatanDataObj)
        }
        val appSettingObj = AppSettings()
        val appSettingJson = MyJournalDataObj.getJSONObject("AppSetting")
        appSettingObj.setLang(appSettingJson.getString("Lang"))
        appSettingObj.setStatusLoggin(appSettingJson.getString("StatusLoggin"))

        d.setUserDataObj(UserData)
        d.setCatatanObjs(CatatanDataList)
        d.setAppSettingObj(appSettingObj)
        d.setstatusUpdate(MyJournalDataObj.getBoolean("statusUpdate"))
    }

    fun GetMyJournalData() :  MyJournalData{
        return d
    }
}