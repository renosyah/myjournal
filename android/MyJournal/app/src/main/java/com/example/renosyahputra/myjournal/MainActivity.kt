package com.example.renosyahputra.myjournal

import android.app.FragmentManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.example.renosyahputra.myjournal.addCatatan.DialogAddCatatan
import com.example.renosyahputra.myjournal.dataCatatan.laporan.LaporanAllCatatan
import com.example.renosyahputra.myjournal.dataCatatan.pemasukan.PemasukanFrame
import com.example.renosyahputra.myjournal.dataCatatan.pengeluaran.PengeluaranFrame
import com.example.renosyahputra.myjournal.res.objectData.MyJournalData
import com.example.renosyahputra.myjournal.res.objectData.UserData
import com.example.renosyahputra.myjournal.storage.grpc.catatan.SendUpdateDataJournal
import com.example.renosyahputra.myjournal.storage.localStorage.MyCatatanDataStorage
import com.example.renosyahputra.myjournal.ui.CircleTransform
import com.example.renosyahputra.myjournal.ui.LangSetting
import com.example.renosyahputra.myjournal.ui.mainActivityLangText.MainActivityLangText
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import java.io.File

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener,View.OnClickListener {


    var FragmentChanger : FragmentManager = fragmentManager
    lateinit var context : Context

    lateinit var lang : LangSetting
    lateinit var navigationView  : NavigationView
    lateinit var JournalData : MyJournalData

    lateinit var laporanAllCatatan : LaporanAllCatatan
    lateinit var pemasukanFrame : PemasukanFrame
    lateinit var pengeluaranFrame : PengeluaranFrame

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        InitiationWidget()
        SendUpdateDataJournal(context,JournalData).execute()

    }

    private fun InitiationWidget(){

        context = this@MainActivity
        lang = LangSetting(context)

        JournalData = intent.getSerializableExtra("data") as MyJournalData
        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        navigationView = findViewById(R.id.nav_view)

        lang = LangSetting(context)
        lang.CheckLangSetting()


        nav_view.setNavigationItemSelectedListener(this)
        fab.setOnClickListener(this)

        SetMenuTittle(navigationView.menu,lang.GetmainActivityLangText())
        SetSideProfile(navigationView,JournalData.getUserDataObj())

        AddContenToFrame()
        SetDefault(navigationView)
    }

    fun SetDefault(navigationView: NavigationView){
        navigationView.menu.getItem(0).setChecked(true)
        navigationView.menu.getItem(0).setTitle(lang.GetmainActivityLangText().getMenuLaporanText())
        toolbar.setTitle(lang.GetmainActivityLangText().getMenuLaporanText())
        FragmentChanger.beginTransaction().replace(R.id.main_frame_fragment,laporanAllCatatan).commit()
    }

    fun AddContenToFrame(){

        val datas = JournalData.getCatatanObjs()

        pemasukanFrame = PemasukanFrame()
        pemasukanFrame.SetcatatanData(datas)

        pengeluaranFrame = PengeluaranFrame()
        pengeluaranFrame.SetcatatanData(datas)

        laporanAllCatatan = LaporanAllCatatan()
        laporanAllCatatan.SetdataList(datas)

    }

    fun SetMenuTittle(mn : Menu,conten : MainActivityLangText){
        val laporan = mn.findItem(R.id.cat_laporan)
        laporan.setTitle(conten.getMenuLaporanText())

        val pendapatan = mn.findItem(R.id.cat_pemasukan)
        pendapatan.setTitle(conten.getMenuPendapatanText())

        val pengeluaran = mn.findItem(R.id.cat_pengeluaran)
        pengeluaran.setTitle(conten.getMenuPengeluaranText())
}

    fun SetSideProfile(navigationView: NavigationView,userData : UserData){

        val header = navigationView.getHeaderView(0)
        val image : ImageView = header.findViewById(R.id.image_user)
        val name : TextView = header.findViewById(R.id.name_user)
        val email : TextView = header.findViewById(R.id.email_user)

        Picasso.with(context).load(userData.getUrlImage()).transform(CircleTransform()).resize(80,80).into(image)
        name.setText(userData.getNama())
        email.setText(userData.getEmail())
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        AddContenToFrame()
        when (item.itemId) {
            R.id.cat_laporan-> {

                FragmentChanger.beginTransaction().replace(R.id.main_frame_fragment,laporanAllCatatan).commit()
                toolbar.setTitle(lang.GetmainActivityLangText().getMenuLaporanText())
            }
            R.id.cat_pemasukan -> {
                FragmentChanger.beginTransaction().replace(R.id.main_frame_fragment,pemasukanFrame).commit()
                toolbar.setTitle(lang.GetmainActivityLangText().getMenuPendapatanText())
            }
            R.id.cat_pengeluaran -> {
                FragmentChanger.beginTransaction().replace(R.id.main_frame_fragment,pengeluaranFrame).commit()
                toolbar.setTitle(lang.GetmainActivityLangText().getMenuPengeluaranText())
            }
            R.id.save -> {
                SendUpdateDataJournal(context,JournalData).execute()
            }

            R.id.logout -> {
                val f1 = File(context.filesDir,MyCatatanDataStorage.filename_session)
                val f2 = File(context.filesDir,MyCatatanDataStorage.filename)


                if (f1.deleteRecursively() && f2.deleteRecursively()) {
                    System.exit(0)
                }

            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun onClick(p0: View?) {
        if (p0 == fab){
            DialogAddCatatan.OpenAddCatatanForm(context,JournalData,FragmentChanger,navigationView,toolbar,pemasukanFrame,pengeluaranFrame)
        }
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)

        val exit = menu.findItem(R.id.exit)
        exit.setTitle("Exit")
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.exit -> {
                System.exit(0)
                return true
            }else -> return super.onOptionsItemSelected(item)

        }
    }

}
