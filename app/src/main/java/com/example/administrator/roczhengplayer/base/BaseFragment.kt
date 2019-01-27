package com.example.administrator.roczhengplayer.base

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.runOnUiThread
import org.jetbrains.anko.toast


/**
 * Created by Administrator on 2019/1/13.
 */
abstract class BaseFragment : Fragment(), AnkoLogger {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    /**
     * Fragment初始化
     */
    open protected fun init() {

    }

    /**
     * 数据的初始化
     */
    open protected fun initData() {
    }

    open protected fun initListener() {
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return initView()
    }

    abstract fun initView(): View?

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initListener()
        initData()
    }

    fun myToast(msg: String) {
        context.runOnUiThread { toast(msg) }
    }
}