package com.example.explorehaven

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class MyViewPagerAdapter(fm: FragmentManager): FragmentPagerAdapter(fm)  {
    var flist : ArrayList<Fragment> = ArrayList<Fragment>()
    var titleList : ArrayList<String> = ArrayList<String>()

    override fun getCount(): Int {
        return flist.size
    }

    override fun getItem(position: Int): Fragment {

        return flist.get(position)
    }
    override fun getPageTitle(position: Int): CharSequence {
        return titleList.get(position)
    }

    fun addFragment(fr: Fragment, str: String){
        flist.add(fr)
        titleList.add(str)
    }
}