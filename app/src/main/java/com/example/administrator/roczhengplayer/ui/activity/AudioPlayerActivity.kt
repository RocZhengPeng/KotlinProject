package com.example.administrator.roczhengplayer.ui.activity

import android.media.MediaPlayer
import com.example.administrator.roczhengplayer.R
import com.example.administrator.roczhengplayer.base.BaseActivity
import com.example.administrator.roczhengplayer.model.AudioBean

/**
 * Created by Administrator on 2019/1/23.
 */
class AudioPlayerActivity : BaseActivity() {
    override fun getLayoutId(): Int {
        return R.layout.activity_audio_player
    }

    override fun initData() {
        var list = intent.getParcelableArrayListExtra<AudioBean>("list")
        var position = intent.getIntExtra("position", -1)

        //播放音乐
        val mediaPlayer= MediaPlayer()
        mediaPlayer.setOnPreparedListener {
            //开始播放
            mediaPlayer.start()
        }
        mediaPlayer.setDataSource(list.get(position).date)
        mediaPlayer.prepareAsync()
    }
}