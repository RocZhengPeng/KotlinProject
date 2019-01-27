package com.example.administrator.roczhengplayer.base


/**
 * Created by Administrator on 2019/1/17.
 * 所有下拉刷新和上拉加载更多列表的基类
 */
interface BaseView<RESPONSE> {
    /**
     * 获取数据失败
     */
    fun onError(message: String?)

    /**
     * 初始haul数据或者刷新数据
     */
    fun loadMore(list: RESPONSE?)

    /**
     * 加载成功
     */
    fun loadSuccess(list: RESPONSE?)
}