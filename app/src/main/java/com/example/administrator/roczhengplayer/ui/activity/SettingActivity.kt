package com.example.administrator.roczhengplayer.ui.activity

import android.preference.PreferenceManager
import android.support.v7.widget.Toolbar
import com.example.administrator.roczhengplayer.R
import com.example.administrator.roczhengplayer.base.BaseActivity
import com.example.administrator.roczhengplayer.util.ToolBarManager
import org.jetbrains.anko.find

/**
 * Created by Administrator on 2019/1/13.
 * PreferenceFragment和PreferenceActivity可以保存页面按钮是否选中
 */
class SettingActivity : BaseActivity(), ToolBarManager {
    override val toolbar: Toolbar by lazy {
        find<Toolbar>(R.id.toolbar)
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_setting
    }

    override fun initData() {
        initSettingToolbar()
        //获取推动通知有没有选中
        val sp = PreferenceManager.getDefaultSharedPreferences(this)
        val push = sp.getBoolean("push", false)
    }
}