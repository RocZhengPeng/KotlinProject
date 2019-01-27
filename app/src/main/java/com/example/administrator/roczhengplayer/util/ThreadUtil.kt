package com.example.administrator.roczhengplayer.util

import android.os.Handler
import android.os.Looper

/**
 * Created by Administrator on 2019/1/15.
 * 静态的类
 */
object ThreadUtil {
    val handler = Handler(Looper.getMainLooper())
    /**
     * 运行在主线程
     */
    fun runOnMainThread(runnable: Runnable) {
        handler.post(runnable)
    }
}