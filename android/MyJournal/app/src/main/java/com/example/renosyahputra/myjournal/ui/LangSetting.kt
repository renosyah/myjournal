package com.example.renosyahputra.myjournal.ui

import android.app.Activity
import android.content.Context
import android.content.Context.MODE_PRIVATE
import com.example.renosyahputra.myjournal.ui.mainActivityLangText.MainActivityLangText
import java.io.BufferedReader
import java.io.FileNotFoundException
import java.io.IOException
import java.io.InputStreamReader


public class LangSetting(ctx : Context){

    public val SetInglisLang : String = "ENGLISH"
    public val SetIdoLang : String = "INDONESIA"
     val FilenameLangSetting : String = "Langsetting.txt"
     var context : Context = ctx
     var mainActivityLangText : MainActivityLangText = MainActivityLangText()

    public fun GetmainActivityLangText() : MainActivityLangText{
        return mainActivityLangText
    }


    private fun SetInglish(){
        mainActivityLangText.setMenuLaporanText("Report Period")
        mainActivityLangText.setMenuPendapatanText("Income")
        mainActivityLangText.setMenuPengeluaranText("Cost")
    }

    private fun SetIndo(){
        mainActivityLangText.setMenuLaporanText("Laporan Periode")
        mainActivityLangText.setMenuPendapatanText("Pemasukan")
        mainActivityLangText.setMenuPengeluaranText("Pengeluaran")
    }

     fun CheckLangSetting(){

        val SettingLangData : String = load_data(FilenameLangSetting,context)

        if (SettingLangData ==  SetInglisLang){
            SetInglish()
        }else if(SettingLangData == SetIdoLang){
            SetIndo()
        }else {
            SetIndo()
        }
    }

    fun SetLangSetting(setting : String){
        save_data(context, setting,FilenameLangSetting)
    }


    fun save_data(context: Context, data_disimpan: String, nama_file: String) {
        val data_save = false
        try {
            val fileOutputStream = context.openFileOutput(nama_file, MODE_PRIVATE)
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