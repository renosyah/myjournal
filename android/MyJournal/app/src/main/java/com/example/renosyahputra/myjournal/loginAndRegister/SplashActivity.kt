package com.example.renosyahputra.myjournal.loginAndRegister

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.renosyahputra.myjournal.MainActivity
import com.example.renosyahputra.myjournal.R
import com.example.renosyahputra.myjournal.storage.grpc.catatan.SendRequestDataJournal
import com.example.renosyahputra.myjournal.storage.localStorage.MyCatatanDataStorage

class SplashActivity : AppCompatActivity() {

    lateinit var context : Context
    lateinit var IntentData : Intent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_spash)
        InitiationWidget()
    }

    private fun InitiationWidget(){
        context = this@SplashActivity
        IntentData = intent

        val openlocalstorage = MyCatatanDataStorage(context)

        if (openlocalstorage.CheckMyJournalData()!= ""){
            val data = openlocalstorage.GetMyJournalData()

            val load = Intent(context, MainActivity::class.java)
            load.putExtra("data",data)
            context.startActivity(load)
            (context as Activity).finish()

        }else {
            SendRequestDataJournal(context, IntentData.getStringExtra("id_user")).execute()
        }
    }
}
