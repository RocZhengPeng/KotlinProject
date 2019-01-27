package com.example.administrator.roczhengplayer.ui.activity

import android.support.v4.view.ViewCompat
import android.support.v4.view.ViewPropertyAnimatorListener
import android.view.View
import com.example.administrator.roczhengplayer.R
import com.example.administrator.roczhengplayer.base.BaseActivity
import kotlinx.android.synthetic.main.activity_splash.*

/**
 * Created by Administrator on 2019/1/13.
 */
class SplashActivity: BaseActivity(), ViewPropertyAnimatorListener {
    override fun onAnimationEnd(view: View?) {
        //进入主界面
        startActivityAndFinish<MainActivity>()
    }

    override fun onAnimationCancel(view: View?) {
    }

    override fun onAnimationStart(view: View?) {
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_splash
    }

    override fun initData() {
          ViewCompat.animate(imageView).scaleX(1.0f).scaleY(1.0f).setListener(this).setDuration(2000).start()
    }
}