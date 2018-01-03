package com.example.renosyahputra.myjournal.storage.grpc.catatan

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import com.example.renosyahputra.myjournal.MainActivity
import com.example.renosyahputra.myjournal.res.objectData.*
import com.example.renosyahputra.myjournal.storage.grpc.UrlAndPort
import com.example.renosyahputra.myjournal.storage.localStorage.MyCatatanDataStorage
import io.grpc.ManagedChannelBuilder
import synchronizationCatatanData.SynchronizationCatatan.RequestDataCatatan
import synchronizationCatatanData.SynchronizationCatatan.ResponseDataCatatan
import java.util.concurrent.TimeUnit


class SendRequestDataJournal : AsyncTask<Void,Void, ResponseDataCatatan>{

    internal var context : Context
    internal var IdUser : String
    val channel = ManagedChannelBuilder.forAddress(UrlAndPort.url, UrlAndPort.port).usePlaintext(true).build()
    internal lateinit var data : MyJournalData

    constructor(context: Context, IdUser: String) : super() {
        this.context = context
        this.IdUser = IdUser
    }


    override fun onPreExecute() {
        super.onPreExecute()
    }

    override fun doInBackground(vararg p0: Void?): ResponseDataCatatan? {
        var response : ResponseDataCatatan? = null
        try{
            val channel = ManagedChannelBuilder.forAddress(UrlAndPort.url, UrlAndPort.port).usePlaintext(true).build()
            val stub = synchronizationCatatanData.synchronizationCatatanServiceGrpc.newBlockingStub(channel)
            val request = RequestDataCatatan.newBuilder().setIdUser(IdUser).build()
            response = stub.dataCatatan(request)

        }catch (e :Exception){
            e.printStackTrace()
        }

        return response
    }

    override fun onPostExecute(result: ResponseDataCatatan?) {
        super.onPostExecute(result)
        try {
            channel.shutdown().awaitTermination(1, TimeUnit.SECONDS)
        } catch (e: InterruptedException) {
            Thread.currentThread().interrupt()
        }
        val load = Intent(context, MainActivity::class.java)
        if (result != null){
            data = MyJournalData()
            val userData = UserData()
            val catatanDatas = ArrayList<CatatanData>()
            val appSettings = AppSettings()

            userData.setId(result.data.userDataObj.id)
            userData.setNama(result.data.userDataObj.nama)
            userData.setNomorTelp(result.data.userDataObj.nomorTelp)
            userData.setEmail(result.data.userDataObj.email)
            userData.setUrlImage(result.data.userDataObj.urlImage)

            for (data in result.data.catatanObjsList){
                val catatanData = CatatanData()
                val detailcatatanDatas = ArrayList<DetailCatatanData>()
                for (detailData in data.detailList){
                    val detail = DetailCatatanData()

                    detail.setKodeCatatan(detailData.kodeCatatan)
                    detail.setType(detailData.type)
                    detail.setTanggal(detailData.tanggal)
                    detail.setCatatan(detailData.catatan)
                    detail.setJumlahTotal(detailData.jumlahTotal)

                    detailcatatanDatas.add(detail)
                }
                catatanData.setTahun(data.tahun)
                catatanData.setBulan(data.bulan)
                catatanData.setKodeBulan(data.kodeBulan)
                catatanData.setDetail(detailcatatanDatas)

                catatanDatas.add(catatanData)
            }

            appSettings.setLang(result.data.appSettingObj.lang)
            appSettings.setStatusLoggin(result.data.appSettingObj.statusLoggin)

            data.setUserDataObj(userData)
            data.setCatatanObjs(catatanDatas)
            data.setAppSettingObj(appSettings)

            MyCatatanDataStorage(context as Activity).SetAndSaveMyJournalData(data,true)

            load.putExtra("data",data)
            context.startActivity(load)
            (context as Activity).finish()

        }

    }

}