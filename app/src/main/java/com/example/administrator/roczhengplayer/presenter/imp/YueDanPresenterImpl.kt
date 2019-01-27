package com.example.administrator.roczhengplayer.presenter.imp

import com.example.administrator.roczhengplayer.base.BaseListPresenter
import com.example.administrator.roczhengplayer.base.BaseView
import com.example.administrator.roczhengplayer.net.HomeRequest
import com.example.administrator.roczhengplayer.net.ResponseHandler
import com.example.administrator.roczhengplayer.net.YueDanRequest
import com.example.administrator.roczhengplayer.presenter.interf.HomePresenter
import com.example.administrator.roczhengplayer.presenter.interf.YueDanPresenter
import com.example.administrator.roczhengplayer.view.YueDanView
import com.itheima.player.model.bean.YueDanBean

/**
 * Created by Administrator on 2019/1/17.
 */
class YueDanPresenterImpl(var yueDanView: BaseView<YueDanBean>?) : YueDanPresenter, ResponseHandler<YueDanBean> {
    override fun destoryView() {
        if (yueDanView != null) {
            yueDanView = null
        }
    }

    override fun onError(type: Int, msg: String?) {
        yueDanView?.onError(msg)
    }

    override fun onSuccess(type: Int, result: YueDanBean) {
        when (type) {
            BaseListPresenter.TYPE_INIT_OR_REFRESH -> yueDanView?.loadSuccess(result)
            BaseListPresenter.TYPE_LOAD_MORE -> yueDanView?.loadMore(result)
        }
    }

    override fun loadDatas() {
        YueDanRequest(BaseListPresenter.TYPE_INIT_OR_REFRESH, 0, this).execute()
    }

    override fun loadMore(offset: Int) {
        YueDanRequest(BaseListPresenter.TYPE_LOAD_MORE, offset, this).execute()

    }
}