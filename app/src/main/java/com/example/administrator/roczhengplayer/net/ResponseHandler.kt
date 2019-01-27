package com.example.administrator.roczhengplayer.net

/**
 * Created by Administrator on 2019/1/16.
 * 请求回调
 */
interface ResponseHandler<RESPONSE> {
    fun onError(type: Int, msg: String?)
    fun onSuccess(type: Int, result: RESPONSE)
}