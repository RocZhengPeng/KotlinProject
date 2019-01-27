package com.example.administrator.roczhengplayer.ui.activity

import android.support.v7.widget.Toolbar
import com.example.administrator.roczhengplayer.R
import com.example.administrator.roczhengplayer.base.BaseActivity
import com.example.administrator.roczhengplayer.util.FragmentUtil
import com.example.administrator.roczhengplayer.util.ToolBarManager
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.find

class MainActivity : BaseActivity(),ToolBarManager {
    //by lazy惰性加载
    override val toolbar: Toolbar by lazy {
        find<Toolbar>(R.id.toolbar)
    }
    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun initData() {
     initMainToolBar()
    }

    override fun initListener() {
        //设置tab切换监听
        bottomBar.setOnTabSelectListener {
            //it代表参数tabId
          val transaction=supportFragmentManager.beginTransaction()
            transaction.replace(R.id.container,FragmentUtil.fragmentUtil.getFragment(it),it.toString())
            transaction.commit()
        }
    }
}
