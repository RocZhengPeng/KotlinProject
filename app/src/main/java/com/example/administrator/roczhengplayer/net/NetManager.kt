package com.example.administrator.roczhengplayer.net

import com.example.administrator.roczhengplayer.util.ThreadUtil
import com.example.administrator.roczhengplayer.util.URLProviderUtils
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.itheima.player.model.bean.HomeItemBean
import okhttp3.*
import java.io.IOException

/**
 * Created by Administrator on 2019/1/16.
 * 发送网络请求类
 */
class NetManager private constructor() {
    val client by lazy {
        OkHttpClient()
    }

    //使用单列创建
    companion object {
        val manager by lazy {
            NetManager()
        }
    }

    /**
     * 发送网络请求
     */
    fun <RESPONSE> sendRequest(req: MRequest<RESPONSE>) {


        val request = Request.Builder()
                .url(req.url)
                .get()
                .build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call?, e: IOException?) {
                //隐藏刷新控件
                ThreadUtil.runOnMainThread(object : Runnable {
                    override fun run() {
                        req.handler.onError(req.type, e?.message)
                    }
                })
            }

            override fun onResponse(call: Call?, response: Response?) {
                val result = response?.body()?.string()
                val parseResult = req.parseResult(result)
                //刷新列表
                ThreadUtil.runOnMainThread(object : Runnable {
                    override fun run() {
                        req.handler.onSuccess(req.type, parseResult)
                    }
                })

            }

        })
    }
}