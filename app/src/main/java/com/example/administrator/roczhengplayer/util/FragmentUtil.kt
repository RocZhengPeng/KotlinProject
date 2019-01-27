package com.example.administrator.roczhengplayer.util

import com.example.administrator.roczhengplayer.R
import com.example.administrator.roczhengplayer.base.BaseFragment
import com.example.administrator.roczhengplayer.ui.fragment.HomeFragment
import com.example.administrator.roczhengplayer.ui.fragment.MvFragment
import com.example.administrator.roczhengplayer.ui.fragment.VBangFragment
import com.example.administrator.roczhengplayer.ui.fragment.YueDanFragment

/**
 * Created by Administrator on 2019/1/13.
 */
class FragmentUtil private constructor() {
    val homeFragment by lazy {
        HomeFragment()
    }
    val mvFragment by lazy {
        MvFragment()
    }
    val vbangFragment by lazy {
        VBangFragment()
    }
    val yueDanFragment by lazy {
        YueDanFragment()
    }

    //私有化构造方法
    companion object {
        //伴生对象
        val fragmentUtil by lazy { FragmentUtil() }
    }

    /**
     * 根据tabid获取对应的Fragment
     */
    fun getFragment(tabId: Int): BaseFragment? {
        when (tabId) {
            R.id.tab_home -> return homeFragment
            R.id.tab_mv -> return mvFragment
            R.id.tab_vbang -> return vbangFragment
            R.id.tab_yuedan -> return yueDanFragment
        }
        return null
    }
}