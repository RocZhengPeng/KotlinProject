package com.example.administrator.roczhengplayer.presenter.imp

import com.example.administrator.roczhengplayer.base.BaseListPresenter
import com.example.administrator.roczhengplayer.net.MvListRequest
import com.example.administrator.roczhengplayer.net.ResponseHandler
import com.example.administrator.roczhengplayer.presenter.interf.MvListPresenter
import com.example.administrator.roczhengplayer.view.MvListView
import com.itheima.player.model.bean.MvPagerBean

/**
 * Created by Administrator on 2019/1/18.
 */
class MvListPresenterImpl(var code: String, var MvListView: MvListView) : MvListPresenter, ResponseHandler<MvPagerBean> {
    override fun onError(type: Int, msg: String?) {
        MvListView.onError(msg)
    }

    override fun onSuccess(type: Int, result: MvPagerBean) {
        if (type == BaseListPresenter.TYPE_INIT_OR_REFRESH) {
            MvListView.loadSuccess(result)
        } else if (type == BaseListPresenter.TYPE_LOAD_MORE) {
            MvListView.loadMore(result)
        }
    }

    override fun loadDatas() {
        MvListRequest(BaseListPresenter.TYPE_INIT_OR_REFRESH, code, 0, this).execute()
    }

    override fun loadMore(offset: Int) {
        MvListRequest(BaseListPresenter.TYPE_INIT_OR_REFRESH, code, offset, this).execute()
    }

    override fun destoryView() {
        if (MvListView != null) {
            MvListView == null
        }
    }
}