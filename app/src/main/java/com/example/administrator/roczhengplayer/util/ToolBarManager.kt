package com.example.administrator.roczhengplayer.util


import android.content.Intent
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import com.example.administrator.roczhengplayer.R
import com.example.administrator.roczhengplayer.ui.activity.SettingActivity

/**
 * Created by Administrator on 2019/1/13.
 * 所有界面toolbar的管理类
 */
interface ToolBarManager {
    val toolbar: Toolbar
    /**
     * 初始化主界面中的toolBar
     */
    fun initMainToolBar() {
        toolbar.setTitle("Roc影音")
        toolbar.inflateMenu(R.menu.main)
        //kotlin和java调用特性
        //如果java接口中只有一个未实现的方法，可以省略接口对象 直接调用{}表示未实现的方法
        toolbar.setOnMenuItemClickListener {
            toolbar.context.startActivity(Intent(toolbar.context,SettingActivity::class.java))
            true
        }
       /* toolbar.setOnMenuItemClickListener(object : Toolbar.OnMenuItemClickListener {
            override fun onMenuItemClick(item: MenuItem?): Boolean {
                when (item?.itemId) {
                    R.id.setting -> {
                        //跳转到设置界面
                        toolbar.context.startActivity(Intent(toolbar.context,SettingActivity::class.java))
                    }
                }
                return true
            }
        })*/
    }

    /**
     * 处理设置页面的Toolbar
     */
    fun initSettingToolbar(){
        toolbar.setTitle("设置")
    }
}