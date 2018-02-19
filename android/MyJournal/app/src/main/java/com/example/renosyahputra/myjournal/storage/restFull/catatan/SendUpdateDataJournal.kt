package com.example.renosyahputra.myjournal.storage.restFull.catatan

import android.content.Context
import com.example.renosyahputra.myjournal.res.objectData.MyJournalData


class SendUpdateDataJournal {
    lateinit var ctx : Context
    lateinit var data : MyJournalData

    constructor(ctx : Context,data : MyJournalData) : super() {
        this.ctx = ctx
        this.data = data
    }

    fun Send(){

    }

}