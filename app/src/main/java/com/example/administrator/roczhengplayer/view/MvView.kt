package com.example.administrator.roczhengplayer.view

import com.itheima.player.model.bean.MvAreaBean

/**
 * Created by Administrator on 2019/1/18.
 */
interface MvView {
    fun onError(msg: String?)
    fun onSuccess(result: List<MvAreaBean>)
}