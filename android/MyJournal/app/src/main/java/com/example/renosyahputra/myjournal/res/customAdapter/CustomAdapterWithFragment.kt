package com.example.renosyahputra.myjournal.res.customAdapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter



class CustomAdapterWithFragment(fm: FragmentManager?) : FragmentStatePagerAdapter(fm) {

    private val mFragmentList = ArrayList<Fragment>()
    private val mFragmentTitleList = ArrayList<String>()


    fun addFragment(fragment: Fragment, title: String) {
        mFragmentList.add(fragment)
        mFragmentTitleList.add(title)
    }


   override fun getPageTitle(position: Int): CharSequence {
        return mFragmentTitleList.get(position)
    }

    override fun getItem(position: Int): Fragment {
        return mFragmentList.get(position)
    }


    override fun getCount(): Int {
        return mFragmentList.size
    }
}
