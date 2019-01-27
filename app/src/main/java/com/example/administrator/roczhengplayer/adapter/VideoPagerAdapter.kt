package com.example.administrator.roczhengplayer.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.example.administrator.roczhengplayer.ui.fragment.DefaultFragment

/**
 * Created by Administrator on 2019/1/22.
 */
class VideoPagerAdapter(fm: FragmentManager?) : FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        return DefaultFragment()
    }

    override fun getCount(): Int {
        return 3
    }
}