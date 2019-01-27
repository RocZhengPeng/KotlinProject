package com.example.administrator.roczhengplayer.base

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

/**
 * Created by Administrator on 2019/1/13.
 * 所有Activity的基类
 */
abstract class BaseActivity : AppCompatActivity(),AnkoLogger {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        initListener()
        initData()
    }

    /**
     * 初始化数据
     */
    open protected fun initData() {

    }

    /**
     * adapter listener
     */
    open protected fun initListener() {

    }

    abstract fun getLayoutId(): Int

    protected fun myToast(msg: String) {
        runOnUiThread { toast(msg) }
    }

    inline fun<reified T:BaseActivity> startActivityAndFinish(){
        startActivity<T>()
        finish()
    }
}