package com.example.administrator.roczhengplayer.ui.activity

import android.support.v4.view.ViewPager
import com.example.administrator.roczhengplayer.R
import com.example.administrator.roczhengplayer.adapter.VideoPagerAdapter
import com.example.administrator.roczhengplayer.base.BaseActivity
import com.example.administrator.roczhengplayer.model.VideoPlayBean
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard
import kotlinx.android.synthetic.main.activity_video_player_jiecao.*

/**
 * Created by Administrator on 2019/1/19.
 */
class JiecaoVideoPlayerActivity : BaseActivity() {
    override fun getLayoutId(): Int {
        return R.layout.activity_video_player_jiecao
    }

    override fun initData() {
        val data = intent.data
        println("data=$data")
        if (data == null) {//通过data来判断是应用外传递数据还是应用内传递数据
            //获取传递的数据
            val videoPlayBean = intent.getParcelableExtra<VideoPlayBean>("item")
            //从应用内响应视频播放
            videoplayer.setUp(videoPlayBean.url,
                    JCVideoPlayerStandard.SCREEN_LAYOUT_NORMAL, videoPlayBean.title)
        } else {
            if (data.toString().startsWith("http")) {  //应用外网络视频请求
                //应用外响应
                videoplayer.setUp(data.toString(),
                        JCVideoPlayerStandard.SCREEN_LAYOUT_NORMAL, data.toString())
            } else {
                //应用外的本地视频请求
                //应用外响应
                videoplayer.setUp(data.path,
                        JCVideoPlayerStandard.SCREEN_LAYOUT_NORMAL, data.toString())
            }
        }
        //初始化vitamio
        /*    LibsChecker.checkVitamioLibs(this)

            //获取传递的数据
            var videoPlayBean = intent.getParcelableExtra<VideoPlayBean>("item")

            //val jcVideoPlayerStandard = findViewById<View>(R.id.videoplayer) as JCVideoPlayerStandard
            videoplayer.setUp(videoPlayBean.url,
                    JCVideoPlayerStandard.SCREEN_LAYOUT_NORMAL, videoPlayBean.title)*/
        //  jcVideoPlayerStandard.thumbImageView.setImage("http://p.qpic.cn/videoyun/0/2449_43b6f696980311e59ed467f22794e792_1/640")
    }

    override fun initListener() {
        //适配viewpager
        viewPager.adapter = VideoPagerAdapter(supportFragmentManager)
        //radiogroup选中监听
        rg.setOnCheckedChangeListener { radioGroup, i ->
            when (i) {
                R.id.rb1 -> viewPager.setCurrentItem(0)
                R.id.rb2 -> viewPager.setCurrentItem(1)
                R.id.rb3 -> viewPager.setCurrentItem(2)
            }
        }
        //viewPager选中状态监听
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {

            /**
             * 滑动回调
             */
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            /**
             * 滑动选中
             */
            override fun onPageSelected(position: Int) {
                when (position) {
                    0 -> rg.check(R.id.rb1)
                    1 -> rg.check(R.id.rb2)
                    2 -> rg.check(R.id.rb3)
                }
            }

            /**
             * 滑动状态改变的回调
             */
            override fun onPageScrollStateChanged(state: Int) {
            }

        })
    }


    override fun onBackPressed() {
        if (JCVideoPlayer.backPress()) {
            return
        }
        super.onBackPressed()
    }

    override fun onPause() {
        super.onPause()
        JCVideoPlayer.releaseAllVideos()
    }
}