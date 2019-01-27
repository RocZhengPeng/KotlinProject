package com.example.administrator.roczhengplayer.ui.fragment

import android.view.View
import com.example.administrator.roczhengplayer.R
import com.example.administrator.roczhengplayer.adapter.MvPagerAdapter
import com.example.administrator.roczhengplayer.base.BaseFragment
import com.example.administrator.roczhengplayer.presenter.imp.MvPresenterImpl
import com.example.administrator.roczhengplayer.view.MvView
import com.itheima.player.model.bean.MvAreaBean
import kotlinx.android.synthetic.main.fragment_mv.*

/**
 * Created by Administrator on 2019/1/13.
 */
class MvFragment : BaseFragment(), MvView {
    override fun onError(msg: String?) {
        myToast("加载数据失败！")
    }

    override fun onSuccess(result: List<MvAreaBean>) {
        //Fragment中嵌套Fragment，需要使用childFragmentManager
        val adapter = MvPagerAdapter(context,result, childFragmentManager)
        viewPager.adapter = adapter
        tabLayout.setupWithViewPager(viewPager)
    }

    val presenter by lazy {
        MvPresenterImpl(this)
    }

    override fun initView(): View? {
        return View.inflate(context, R.layout.fragment_mv, null)
    }

    override fun initListener() {
        super.initListener()
    }

    override fun initData() {
        //加载区域数据
        presenter.loadDatas()
    }
}