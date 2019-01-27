package com.example.administrator.roczhengplayer.presenter.imp

import com.example.administrator.roczhengplayer.net.MvAreaRequest
import com.example.administrator.roczhengplayer.net.ResponseHandler
import com.example.administrator.roczhengplayer.presenter.interf.MvPresenter
import com.example.administrator.roczhengplayer.view.MvView
import com.itheima.player.model.bean.MvAreaBean

/**
 * Created by Administrator on 2019/1/18.
 */
class MvPresenterImpl(var mvView: MvView):MvPresenter, ResponseHandler<List<MvAreaBean>> {
    override fun onError(type: Int, msg: String?) {
           mvView.onError(msg)
    }

    override fun onSuccess(type: Int, result: List<MvAreaBean>) {
         mvView.onSuccess(result)
    }

    //加载区域数据
    override fun loadDatas() {
        MvAreaRequest(this).execute()
    }
}