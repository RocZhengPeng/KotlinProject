package com.example.administrator.roczhengplayer.ui.fragment

import android.graphics.Color
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.example.administrator.roczhengplayer.R
import com.example.administrator.roczhengplayer.adapter.HomeAdapter
import com.example.administrator.roczhengplayer.base.BaseFragment
import com.example.administrator.roczhengplayer.base.BaseListAdapter
import com.example.administrator.roczhengplayer.base.BaseListFragment
import com.example.administrator.roczhengplayer.base.BaseListPresenter
import com.example.administrator.roczhengplayer.presenter.imp.HomePresenterImpl
import com.example.administrator.roczhengplayer.widget.HomeItemView
import com.itheima.player.model.bean.HomeItemBean


/**
 * Created by Administrator on 2019/1/13.
 */
class HomeFragment : BaseListFragment<List<HomeItemBean>, HomeItemBean, HomeItemView>() {
    override fun getSpecialPresenter(): BaseListPresenter {
        return HomePresenterImpl(this)
    }

    override fun getList(response: List<HomeItemBean>?): List<HomeItemBean>? {
        return response
    }

    override fun getSpecialAdapter(): BaseListAdapter<HomeItemBean, HomeItemView> {
        return HomeAdapter()
    }
}