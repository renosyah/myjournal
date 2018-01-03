package com.example.renosyahputra.myjournal.dataCatatan.pemasukan

import android.app.Fragment
import android.content.Context
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.renosyahputra.myjournal.R
import com.example.renosyahputra.myjournal.addCatatan.TypeCatatan
import com.example.renosyahputra.myjournal.res.ListCatatan
import com.example.renosyahputra.myjournal.res.customAdapter.CustomAdapterWithFragment
import com.example.renosyahputra.myjournal.res.objectData.CatatanData

public class PemasukanFrame: Fragment(),View.OnClickListener,ViewPager.OnPageChangeListener{


    lateinit var v : View
    lateinit var ctx : Context
    lateinit var fragmentActivity : FragmentActivity
    lateinit var catatanData : ArrayList<CatatanData>

    lateinit var bulan : TextView
    lateinit var viewpagger : ViewPager

    public fun SetcatatanData(data : ArrayList<CatatanData>){
        this.catatanData = data
    }

    lateinit var customAdapterWithFragment : CustomAdapterWithFragment


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        v = inflater.inflate(R.layout.catatan_frame,container,false)
        InitiationWidget(v)
        return v
    }

    private fun InitiationWidget(v :View){
        ctx = activity
        fragmentActivity = (ctx as  FragmentActivity)

        bulan = v.findViewById(R.id.bulanCatatan)
        viewpagger = v.findViewById(R.id.view_pagger_pemasukkan_list)
        addFragmentListByBulan(catatanData,viewpagger)


        viewpagger.addOnPageChangeListener(this)


    }

    private fun addFragmentListByBulan(dataCatatan : ArrayList<CatatanData>,pager: ViewPager){

        customAdapterWithFragment = CustomAdapterWithFragment(fragmentActivity.supportFragmentManager)
        for (data in dataCatatan.sortedWith(compareBy(CatatanData::getTahun,CatatanData::getKodeBulan))){
            val listCatatanFragment = ListCatatan()
            listCatatanFragment.SetdataList(data.GetDetail())
            listCatatanFragment.SetCatatanType(TypeCatatan.pemasukkan)
            listCatatanFragment.setYearAndMoon(" - "+data.getBulan()+" - "+data.getTahun())
            customAdapterWithFragment.addFragment(listCatatanFragment,data.getBulan())
        }
        viewpagger.adapter = customAdapterWithFragment
    }
    override fun onClick(p0: View?) {

    }


    override fun onPageScrollStateChanged(state: Int) {

    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        val data = catatanData.sortedWith(compareBy(CatatanData::getTahun,CatatanData::getKodeBulan))
        bulan.setText(data.get(position).getBulan() +" "+ data.get(position).getTahun())
    }

    override fun onPageSelected(position: Int) {

    }
}