package com.example.administrator.roczhengplayer.adapter

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.example.administrator.roczhengplayer.ui.fragment.MvPagerFragment
import com.itheima.player.model.bean.MvAreaBean

/**
 * Created by Administrator on 2019/1/18.
 */
class MvPagerAdapter(val context: Context, val list: List<MvAreaBean>, fm: FragmentManager) : FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {

        //第二种Fragment数据传递
        val bundle = Bundle()
        bundle.putString("args", list?.get(position)?.code)
        val fragment = Fragment.instantiate(context, MvPagerFragment::class.java.name, bundle)
        return fragment
    }

    override fun getCount(): Int {
        return list?.size ?: 0//如果不为null返回list.size，为空返回0
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return list?.get(position)?.name
    }
}