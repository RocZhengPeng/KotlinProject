package com.example.administrator.roczhengplayer.ui.fragment

import com.example.administrator.roczhengplayer.adapter.YueDanAdapter
import com.example.administrator.roczhengplayer.base.BaseListAdapter
import com.example.administrator.roczhengplayer.base.BaseListFragment
import com.example.administrator.roczhengplayer.base.BaseListPresenter
import com.example.administrator.roczhengplayer.presenter.imp.YueDanPresenterImpl
import com.example.administrator.roczhengplayer.widget.YueDanItemView
import com.itheima.player.model.bean.YueDanBean

/**
 * Created by Administrator on 2019/1/13.
 */
class YueDanFragment : BaseListFragment<YueDanBean, YueDanBean.PlayListsBean, YueDanItemView>() {
    override fun getSpecialPresenter(): BaseListPresenter {
        return YueDanPresenterImpl(this)
    }

    override fun getList(response: YueDanBean?): List<YueDanBean.PlayListsBean>? {
        return response?.playLists

    }

    override fun getSpecialAdapter(): BaseListAdapter<YueDanBean.PlayListsBean, YueDanItemView> {
        return YueDanAdapter()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        //解绑presenter
        presenter.destoryView()
    }
}