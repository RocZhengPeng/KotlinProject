package com.example.administrator.roczhengplayer.ui.activity

import android.graphics.SurfaceTexture
import android.media.MediaPlayer
import android.view.Surface
import android.view.TextureView
import com.example.administrator.roczhengplayer.R
import com.example.administrator.roczhengplayer.base.BaseActivity
import com.example.administrator.roczhengplayer.model.VideoPlayBean
import kotlinx.android.synthetic.main.activity_video_player_texture.*

/**
 * Created by Administrator on 2019/1/19.
 * mediaPlayer视屏解码
 */
class TextureVideoPlayerActivity : BaseActivity(), TextureView.SurfaceTextureListener {
    override fun onSurfaceTextureSizeChanged(p0: SurfaceTexture?, p1: Int, p2: Int) {
        //视图更新
    }

    override fun onSurfaceTextureUpdated(p0: SurfaceTexture?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onSurfaceTextureDestroyed(p0: SurfaceTexture?): Boolean {
        //关闭mediaplayer
        mediaPlayer?.let {
            mediaPlayer.stop()
            mediaPlayer.release()
        }
        //视图销毁
        return true
    }

    override fun onSurfaceTextureAvailable(p0: SurfaceTexture?, p1: Int, p2: Int) {
        //视图可用
        videoPlayBean?.let {
            mediaPlayer.setDataSource(it.url)
            mediaPlayer.setSurface(Surface(p0))//设置播放视屏画面的View
            mediaPlayer.prepareAsync()//异步准备
            mediaPlayer.setOnPreparedListener {
                mediaPlayer.start()
                //旋转画面
                textureView.rotation = 100f
            }
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_video_player
    }

    var videoPlayBean: VideoPlayBean? = null
    val mediaPlayer by lazy {
        MediaPlayer()
    }

    override fun initData() {
        //获取传递的数据
        videoPlayBean = intent.getParcelableExtra<VideoPlayBean>("item")
        textureView.surfaceTextureListener = this

    }
}