package com.example.renosyahputra.myjournal.storage.localStorage

import android.app.Activity
import android.content.Context
import com.example.renosyahputra.myjournal.res.objectData.MyJournalData
import java.io.BufferedReader
import java.io.FileNotFoundException
import java.io.IOException
import java.io.InputStreamReader


class MyCatatanDataStorage(ctx : Context){
companion object {

    val filename : String = "dataJournal"
    val filename_session : String = "dataSession"
}
    private lateinit var d : MyJournalData
    val context = ctx

    fun SetAndSaveMyJournalData(d : MyJournalData,statusUpdate : Boolean){
        this.d = d
        val dataToJson = DataToJson()
        dataToJson.SetMyJournalData(d,statusUpdate)
        save_data(context,dataToJson.GetMyJournalDataAsJson(),filename)
    }

    fun CheckMyJournalData() : String{
        return load_data(filename,context)
    }


    fun GetMyJournalData(): MyJournalData{
       val jsonToData = JsonToData(load_data(filename,context))
        jsonToData.SetMyJournalData()
       return jsonToData.GetMyJournalData()
    }

    fun SaveSessionLogin(id :String){
        save_data(context,id,filename_session)
    }

    fun GetSessionLogin() : String {
        return ""+load_data(filename_session, context)
    }

    fun save_data(context: Context, data_disimpan: String, nama_file: String) {
        try {
            val fileOutputStream = context.openFileOutput(nama_file, Context.MODE_PRIVATE)
            fileOutputStream.write(data_disimpan.toByteArray())


        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

    fun load_data(nama_file: String, context: Context): String {
        val stringbufer = StringBuffer()
        try {
            val fileinputstream = (context as Activity).openFileInput(nama_file)
            val reader = InputStreamReader(fileinputstream)
            val buferreader = BufferedReader(reader)

            for (pesan in buferreader.readLine()) {
                stringbufer.append(pesan)
            }

        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return stringbufer.toString()
    }
}