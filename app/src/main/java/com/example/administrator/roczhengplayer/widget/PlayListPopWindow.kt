package com.example.administrator.roczhengplayer.widget

import android.content.Context
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Adapter
import android.widget.BaseAdapter
import android.widget.ListView
import android.widget.PopupWindow
import com.example.administrator.roczhengplayer.R
import org.jetbrains.anko.find

/**
 * Created by Administrator on 2019/1/28.
 */
class PlayListPopWindow(context: Context, adapter: BaseAdapter) : PopupWindow() {
    init {
        //设置布局
        val view = LayoutInflater.from(context).inflate(R.layout.pop_playlist, null, false)
        //获取listView
        val listView = view.find<ListView>(R.id.listView)
        //适配
        listView.adapter = adapter
        contentView = view
        //设置宽度和高度
        width = ViewGroup.LayoutParams.MATCH_PARENT
        //设置高度为屏幕高度的5分之3
        val manager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val point = Point()
        manager.defaultDisplay.getSize(point)
        val windowH = point.y
        height = (windowH * 3) / 5
        //设置获取焦点
        isFocusable = true
        //设置外部点击
        isOutsideTouchable = true
        //能够响应返回按钮(低版本popwindow点击返回按钮)能够dismiss关键
        setBackgroundDrawable(ColorDrawable())
    }
}