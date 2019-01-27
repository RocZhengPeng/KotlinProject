package com.example.administrator.roczhengplayer.ui.activity

import com.example.administrator.roczhengplayer.R
import com.example.administrator.roczhengplayer.base.BaseActivity
import com.example.administrator.roczhengplayer.model.VideoPlayBean
import io.vov.vitamio.LibsChecker
import kotlinx.android.synthetic.main.activity_video_player_ijk.*

/**
 * Created by Administrator on 2019/1/19.
 */
class IjkVideoPlayerActivity : BaseActivity() {
    override fun getLayoutId(): Int {
        return R.layout.activity_video_player_ijk
    }

    override fun initData() {
        //初始化vitamio
        LibsChecker.checkVitamioLibs(this)

        //获取传递的数据
        var videoPlayBean = intent.getParcelableExtra<VideoPlayBean>("item")
        videoView.setVideoPath(videoPlayBean.url)//异步准备
        //videoView.start()
        videoView.setOnPreparedListener {
            //异步准备完成之后的监听
            videoView.start()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        videoView.stopPlayback()
    }
}