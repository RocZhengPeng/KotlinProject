package com.example.administrator.roczhengplayer.service

import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder
import com.example.administrator.roczhengplayer.model.AudioBean
import org.greenrobot.eventbus.EventBus
import java.util.*

/**
 * author : roczheng
 * e-mail : 306608923@qq.com
 * time   : 2019/1/28
 * desc   :
 */

class AudioService : Service() {
    var mediaPlayer: MediaPlayer? = null
    var list: ArrayList<AudioBean>? = null
    var position: Int = 0
    val binder by lazy {
        AudioBinder()
    }

    companion object {
        val MODE_ALL = 1//全部循环
        val MODE_SINGLE = 2//单曲循环
        val MODE_RANDOM = 3//随机播放
    }

    var mode = MODE_ALL//默认全部循环

    val sp by lazy {
        getSharedPreferences("config", Context.MODE_PRIVATE)
    }

    override fun onCreate() {
        super.onCreate()
        //获取播放模式
        mode = sp.getInt("mode", 1)
    }

    //多次启动执行多次
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        //获取集合以及当前的position
        list = intent?.getParcelableArrayListExtra<AudioBean>("list")
        position = intent?.getIntExtra("position", -1) ?: -1
        //开始播放音乐
        binder.playItem()
        //START_STICKY 粘性的  service强制杀死后，会尝试重新启动service，不会传递原来的Intent
        //START_NOT_STICKY 非粘性  service强制杀死后，不会尝试重新启动service
        //START_REDELIVER_INTENT ervice强制杀死后，会尝试重新启动service，会传递原来的Intent
        return START_NOT_STICKY
    }

    //多次绑定执行一次
    override fun onBind(intent: Intent?): IBinder {
        return binder
    }

    inner class AudioBinder : Binder(), Iservice, MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener {
        /**
         * 播放上一曲
         */
        override fun playPre() {
            list?.let {
                //获取要播放音乐的position
                when (mode) {
                    MODE_RANDOM -> position = Random().nextInt(it.size - 1)
                    else -> {
                        if (position == 0) {
                            position = it.size - 1
                        } else {
                            position--
                        }
                    }
                }
            }
        }

        /**
         * 下一曲
         */
        override fun playNext() {
            list?.let {
                //获取要播放音乐的position
                when (mode) {
                    MODE_RANDOM -> position = Random().nextInt(it.size - 1)
                    else -> {
                        if (position == 0) {
                            position = it.size - 1
                        } else {
                            position--
                        }
                    }
                }
            }
        }

        /**
         * 获取播放模式
         */
        override fun getPlayMode(): Int {
            return mode
        }

        //修改播放模式
        override fun updatePlayMode() {
            when (mode) {
                MODE_ALL -> mode = MODE_SINGLE
                MODE_SINGLE -> mode = MODE_RANDOM
                MODE_RANDOM -> mode = MODE_ALL
            }
            //保存播放模式
            sp.edit().putInt("mode", mode).commit()
        }

        //歌曲播放完成的回调
        override fun onCompletion(mp: MediaPlayer?) {
            //自动播放下一曲
            autoPlayNext()
        }

        /**
         * 根据播放模式自动播放下一曲
         */
        private fun autoPlayNext() {
            when (mode) {
                MODE_ALL -> {
                    /* if (position == list.size - 1) {
                         position = 0;
                     } else {
                         position++
                     }*/
                    list?.let {
                        position = (position + 1) % it.size
                    }
                }
            //MODE_SINGLE ->
                MODE_RANDOM -> list?.let {
                    position = Random().nextInt(it.size - 1)
                }
            }

            playItem()
        }

        //跳转到当前进度进行播放
        override fun seekTo(progress: Int) {
            mediaPlayer?.seekTo(progress)
        }

        //获取当前进度
        override fun getProgress(): Int {
            return mediaPlayer?.currentPosition ?: 0
        }

        //获取歌曲总进度
        override fun getDuration(): Int {
            return mediaPlayer?.duration ?: 0
        }

        /**
         * 更新播放状态
         */
        override fun updatePlayState() {
            //获取当前播放状态
            val isPlaying = isPlaying()
            //切换播放状态
            isPlaying?.let {
                if (isPlaying) {
                    //正在播放，暂停
                    mediaPlayer?.pause()
                } else {
                    mediaPlayer?.start()
                }
            }
        }

        override fun isPlaying(): Boolean? {
            return mediaPlayer?.isPlaying
        }

        override fun onPrepared(mp: MediaPlayer?) {
            //播放音乐
            mediaPlayer?.start()
            //通知UI界面进行更新
            notifyUpdateUi()
        }

        /**
         * 通知界面更新
         */
        private fun notifyUpdateUi() {
            EventBus.getDefault().post(list?.get(position))
        }

        fun playItem() {
            mediaPlayer = MediaPlayer()
            mediaPlayer?.let {
                //播放音乐
                it.setOnPreparedListener(this)
                it.setOnCompletionListener(this)
                it.setDataSource(list?.get(position)?.date)
                it.prepareAsync()
            }
        }
    }
}