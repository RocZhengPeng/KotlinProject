package com.example.administrator.roczhengplayer.base

/**
 * Created by Administrator on 2019/1/17.
 */
interface BaseListPresenter {
    companion object {
        val TYPE_INIT_OR_REFRESH = 1
        val TYPE_LOAD_MORE = 2
    }
    fun loadDatas()
    fun loadMore(offset: Int)

    //解绑Presenter和View
    fun destoryView()
}