package com.example.administrator.roczhengplayer.ui.activity

import com.example.administrator.roczhengplayer.R
import com.example.administrator.roczhengplayer.base.BaseActivity
import com.example.administrator.roczhengplayer.model.VideoPlayBean
import kotlinx.android.synthetic.main.activity_video_player.*

/**
 * Created by Administrator on 2019/1/19.
 */
class VideoPlayerActivity : BaseActivity() {
    override fun getLayoutId(): Int {
        return R.layout.activity_video_player
    }

    override fun initData() {
        //获取传递的数据
        var videoPlayBean = intent.getParcelableExtra<VideoPlayBean>("item")
        videoView.setVideoPath(videoPlayBean.url)//异步准备
        //videoView.start()
        videoView.setOnPreparedListener {
            //异步准备完成之后的监听
            videoView.start()
        }

    }
}