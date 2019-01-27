package com.example.administrator.roczhengplayer.presenter.imp

import com.example.administrator.roczhengplayer.base.BaseListPresenter
import com.example.administrator.roczhengplayer.base.BaseView
import com.example.administrator.roczhengplayer.net.HomeRequest
import com.example.administrator.roczhengplayer.net.ResponseHandler
import com.example.administrator.roczhengplayer.presenter.interf.HomePresenter
import com.itheima.player.model.bean.HomeItemBean

/**
 * Created by Administrator on 2019/1/16.
 */
class HomePresenterImpl(var homeView: BaseView<List<HomeItemBean>>?) : HomePresenter, ResponseHandler<List<HomeItemBean>> {

    /**
     * 解绑view和Presenter
     */
    override fun destoryView() {
        if (homeView != null) {
            homeView = null
        }
    }


    override fun onError(type: Int, msg: String?) {
        homeView?.onError(msg)
    }

    override fun onSuccess(type: Int, result: List<HomeItemBean>) {
        //区分初始化数据还是加载更多
        when (type) {
            BaseListPresenter.TYPE_INIT_OR_REFRESH -> homeView?.loadSuccess(result)
            BaseListPresenter.TYPE_LOAD_MORE -> homeView?.loadMore(result)
        }
    }

    //初始化数据和刷新数据
    override fun loadDatas() {
        //定义一个Request
        HomeRequest(BaseListPresenter.TYPE_INIT_OR_REFRESH, 0, this).execute()
        //NetManager.manager.sendRequest(request)
        //发送Request
        /*    val path = URLProviderUtils.getHomeUrl(0, 20)
            val client = OkHttpClient()
            val request = Request.Builder()
                    .url(path)
                    .get()
                    .build()
            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call?, e: IOException?) {
                    //隐藏刷新控件
                    ThreadUtil.runOnMainThread(object : Runnable {
                        override fun run() {
                           //回调到View层处理
                            homeView.onError(e?.message)
                        }
                    })
                }

                override fun onResponse(call: Call?, response: Response?) {

                    val result = response?.body()?.string()
                    val gson = Gson()
                    val list = gson.fromJson<List<HomeItemBean>>(result, object : TypeToken<List<HomeItemBean>>() {}.type)
                    //刷新列表
                    ThreadUtil.runOnMainThread(object : Runnable {
                        override fun run() {
                            //将正确结果回调到View层
                            homeView.loadSuccess(list)
                        }
                    })

                }

            })*/
    }

    override fun loadMore(offset: Int) {
        //定义一个Request
        HomeRequest(BaseListPresenter.TYPE_LOAD_MORE, offset, this).execute()
        // NetManager.manager.sendRequest(request)
        /*   val path = URLProviderUtils.getHomeUrl(offset, 20)
           val client = OkHttpClient()
           val request = Request.Builder()
                   .url(path)
                   .get()
                   .build()
           client.newCall(request).enqueue(object : Callback {
               override fun onFailure(call: Call?, e: IOException?) {
                   //隐藏刷新控件
                   ThreadUtil.runOnMainThread(object : Runnable {
                       override fun run() {
                           homeView.onError(e?.message)
                       }
                   })
               }

               override fun onResponse(call: Call?, response: Response?) {

                   val result = response?.body()?.string()
                   val gson = Gson()
                   val list = gson.fromJson<List<HomeItemBean>>(result, object : TypeToken<List<HomeItemBean>>() {}.type)
                   //刷新列表
                   ThreadUtil.runOnMainThread(object : Runnable {
                       override fun run() {
                           homeView.loadMore(list)
                       }
                   })

               }

           })*/
    }
}