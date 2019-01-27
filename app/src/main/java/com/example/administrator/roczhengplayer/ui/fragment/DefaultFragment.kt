package com.example.administrator.roczhengplayer.ui.fragment

import android.view.View
import android.widget.TextView
import com.example.administrator.roczhengplayer.base.BaseFragment

/**
 * Created by Administrator on 2019/1/22.
 */
class DefaultFragment : BaseFragment() {
    override fun initView(): View? {
        val tv = TextView(context)
        tv.setText("哈哈哈")

        return tv
    }
}