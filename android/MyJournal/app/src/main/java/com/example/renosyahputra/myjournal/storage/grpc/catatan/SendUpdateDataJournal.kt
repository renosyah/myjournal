package com.example.renosyahputra.myjournal.storage.grpc.catatan

import android.content.Context
import android.os.AsyncTask
import android.widget.Toast
import com.example.renosyahputra.myjournal.res.objectData.MyJournalData
import com.example.renosyahputra.myjournal.storage.grpc.UrlAndPort
import com.example.renosyahputra.myjournal.storage.localStorage.MyCatatanDataStorage
import io.grpc.ManagedChannelBuilder
import synchronizationCatatanData.SynchronizationCatatan
import java.util.concurrent.TimeUnit


class SendUpdateDataJournal : AsyncTask<Void,Void, SynchronizationCatatan.ResponseUpdateDataCatatan>{

    internal var context : Context
    var data : MyJournalData
    val channel = ManagedChannelBuilder.forAddress(UrlAndPort.url, UrlAndPort.port).usePlaintext(true).build()
    lateinit var dataToSend : SynchronizationCatatan.ServerMyJournalData

    constructor(context: Context,data: MyJournalData) : super() {
        this.context = context
        this.data = data
    }

    override fun onPreExecute() {
        super.onPreExecute()
        val datauser = SynchronizationCatatan.ServerUserData.newBuilder()
            .setId(data.getUserDataObj().getId())
            .setNama(data.getUserDataObj().getNama())
            .setEmail(data.getUserDataObj().getEmail())
            .setNomorTelp(data.getUserDataObj().getNomorTelp())
            .setUrlImage(data.getUserDataObj().getUrlImage())
        .build()

        val serverCatatanDatas = ArrayList<SynchronizationCatatan.ServerCatatanData>()
        for (data in data.getCatatanObjs()){

            val detailserverCatatanDatas  = ArrayList<SynchronizationCatatan.ServerDetailCatatanData>()
            for (detail in data.GetDetail()){
                val detaildata = SynchronizationCatatan.ServerDetailCatatanData.newBuilder()
                            .setKodeCatatan(detail.getKodeCatatan())
                            .setType(detail.getType())
                            .setTanggal(detail.getTanggal())
                            .setCatatan(detail.getCatatan())
                            .setJumlahTotal(detail.getJumlahTotal())
                        .build()

                detailserverCatatanDatas.add(detaildata)
            }

             val serverCatatanData = SynchronizationCatatan.ServerCatatanData.newBuilder()
                .setBulan(data.getBulan())
                .setKodeBulan(data.getKodeBulan())
                .setTahun(data.getTahun())
                .addAllDetail(detailserverCatatanDatas)
            .build()

            serverCatatanDatas.add(serverCatatanData)

        }
        val serverAppSettings = SynchronizationCatatan.ServerAppSettings.newBuilder()
                .setLang(data.getAppSettingObj().getLang())
                .setStatusLoggin(data.getAppSettingObj().getStatusLoggin())
                .build()

        dataToSend = SynchronizationCatatan.ServerMyJournalData.newBuilder()
                .setUserDataObj(datauser)
                .addAllCatatanObjs(serverCatatanDatas)
                .setAppSettingObj(serverAppSettings)
                .build()
    }


    override fun doInBackground(vararg p0: Void?): SynchronizationCatatan.ResponseUpdateDataCatatan? {
        var response : SynchronizationCatatan.ResponseUpdateDataCatatan? = null
        try{
            val stub = synchronizationCatatanData.synchronizationCatatanServiceGrpc.newBlockingStub(channel)
            val request = SynchronizationCatatan.RequestUpdateDataCatatan.newBuilder().setData(dataToSend).build()
            response = stub.updateDataCatatan(request)

        }catch (e : Exception){
            e.printStackTrace()
        }

        return response
    }


    override fun onPostExecute(result: SynchronizationCatatan.ResponseUpdateDataCatatan?) {
        super.onPostExecute(result)
        try {
            channel.shutdown().awaitTermination(1, TimeUnit.SECONDS)
        } catch (e: InterruptedException) {
            Thread.currentThread().interrupt()
        }
        val save = MyCatatanDataStorage(context)
        if (result != null){
            if (result.ok){
                //Toast.makeText(context,"data diupdate",Toast.LENGTH_SHORT).show()
                save.SetAndSaveMyJournalData(data,true)

            }else{
                Toast.makeText(context,"data gagal diupdate",Toast.LENGTH_SHORT).show()
                save.SetAndSaveMyJournalData(data,false)
            }
        }else{
            save.SetAndSaveMyJournalData(data,false)
        }
    }


}