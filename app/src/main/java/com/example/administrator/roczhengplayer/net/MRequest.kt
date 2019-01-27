package com.example.administrator.roczhengplayer.net

import com.google.gson.Gson
import java.lang.reflect.ParameterizedType

/**
 * Created by Administrator on 2019/1/16.
 * 所有请求的基类
 */
open class MRequest<RESPONSE>(val type: Int, val url: String, val handler: ResponseHandler<RESPONSE>) {
    //解析网络请求接口
    fun parseResult(result: String?): RESPONSE {
        val gson = Gson()
        //获取泛型类型
        val type = (this.javaClass.genericSuperclass as ParameterizedType).getActualTypeArguments()[0]
        val list = gson.fromJson<RESPONSE>(result, type)
        return list
    }

    fun execute() {
        NetManager.manager.sendRequest(this)
    }

}