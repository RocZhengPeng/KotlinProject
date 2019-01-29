package com.example.administrator.roczhengplayer.service

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder
import android.support.v4.app.NotificationCompat
import android.widget.RemoteViews
import com.example.administrator.roczhengplayer.R
import com.example.administrator.roczhengplayer.model.AudioBean
import com.example.administrator.roczhengplayer.ui.activity.AudioPlayerActivity
import com.example.administrator.roczhengplayer.ui.activity.MainActivity
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
    var manager: NotificationManager? = null
    var notification: Notification? = null
    var position: Int = -2

    val FROM_PRE = 1
    val FROM_NEXT = 2
    val FROM_STATE = 3
    val FROM_CONTENT = 4

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
        //判断进入service方法
        val from = intent?.getIntExtra("from", -1)
        when (from) {
            FROM_PRE -> {
                binder.playPre()
            }
            FROM_NEXT -> {
                binder.playNext()
            }
            FROM_CONTENT -> {
                binder.notifyUpdateUi()
            }
            FROM_STATE -> {
                binder.updatePlayState()
            }
            else -> {
                var pos = intent?.getIntExtra("position", -1) ?: -1
                if (pos != position) {//想要播放的条目和正在播放的条目不是同一首
                    position = pos
                    //获取集合以及当前的position
                    list = intent?.getParcelableArrayListExtra<AudioBean>("list")
                    //开始播放音乐
                    binder.playItem()
                } else {
                    //主动通知界面更新
                    binder.notifyUpdateUi()

                }
            }
        }

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
         * 播放当前位置的歌曲
         * @AudioService指定是AudioService
         */
        override fun playPosition(position: Int) {
            this@AudioService.position = position
            playItem()
        }

        override fun getPlayList(): List<AudioBean>? {
            return list
        }

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
                    else -> position = (position + 1) % it.size
                }
            }
            playItem()
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
                    pause()
                } else {
                    start()
                }
            }
        }

        /**
         * 暂停播放
         */
        private fun pause() {
            mediaPlayer?.pause()
            EventBus.getDefault().post(list?.get(position))
            //更新通知图标
            notification?.contentView?.setImageViewResource(R.id.state, R.mipmap.btn_audio_pause_normal)
            //重新显示
            manager?.notify(1, notification)
        }

        /**
         * 开始播放
         */
        private fun start() {
            mediaPlayer?.start()
            EventBus.getDefault().post(list?.get(position))
            //更新通知图标
            notification?.contentView?.setImageViewResource(R.id.state, R.mipmap.btn_audio_play_normal)
            //重新显示
            manager?.notify(1, notification)
        }

        override fun isPlaying(): Boolean? {
            return mediaPlayer?.isPlaying
        }

        override fun onPrepared(mp: MediaPlayer?) {
            //播放音乐
            mediaPlayer?.start()
            //通知UI界面进行更新
            notifyUpdateUi()
            //显示通知
            showNotification()
        }

        /**
         * 显示通知
         */
        private fun showNotification() {
            manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notification = getNotification()
            manager?.notify(1, notification)
        }

        private fun getNotification(): Notification? {
            // val bitmap = BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher)
            val notification = NotificationCompat.Builder(this@AudioService)
                    .setTicker("正在播放歌曲${list?.get(position)?.display_name}")
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setCustomContentView(getRemoteViews())
                    .setWhen(System.currentTimeMillis())
                    .setOngoing(true)//设置不能滑动删除通知
                    .setContentIntent(getPendingIntent())//通知栏主体点击事件
                    .build()
            return notification
        }

        /**
         * 创建通知自定义远程view
         */
        private fun getRemoteViews(): RemoteViews? {
            val remoteViews = RemoteViews(packageName, R.layout.notification)
            //修改标题和内容
            remoteViews.setTextViewText(R.id.title, list?.get(position)?.display_name)
            remoteViews.setTextViewText(R.id.artist, list?.get(position)?.artist)
            //处理上一曲 下一曲  状态点击事件
            remoteViews.setOnClickPendingIntent(R.id.pre, getPrePendingIntent())
            remoteViews.setOnClickPendingIntent(R.id.state, getStatePendingIntent())
            remoteViews.setOnClickPendingIntent(R.id.next, getNextPendingIntent())
            return remoteViews
        }

        /**
         * 下一曲点击事件
         */
        private fun getNextPendingIntent(): PendingIntent? {
            val intent = Intent(this@AudioService, AudioService::class.java)//点击主体进入当前界面中
            intent.putExtra("from", FROM_NEXT)
            val pendingIntent = PendingIntent.getService(this@AudioService, 2, intent, PendingIntent.FLAG_UPDATE_CURRENT)
            return pendingIntent
        }

        /**
         * 播放暂停按钮点击事件
         */
        private fun getStatePendingIntent(): PendingIntent? {
            val intent = Intent(this@AudioService, AudioService::class.java)//点击主体进入当前界面中
            intent.putExtra("from", FROM_STATE)
            val pendingIntent = PendingIntent.getService(this@AudioService, 3, intent, PendingIntent.FLAG_UPDATE_CURRENT)
            return pendingIntent
        }

        /**
         * 上一曲点击事件
         */
        private fun getPrePendingIntent(): PendingIntent? {
            val intent = Intent(this@AudioService, AudioService::class.java)//点击主体进入当前界面中
            intent.putExtra("from", FROM_PRE)
            val pendingIntent = PendingIntent.getService(this@AudioService, 4, intent, PendingIntent.FLAG_UPDATE_CURRENT)
            return pendingIntent
        }

        /**
         * 通知栏主体点击事件
         */
        private fun getPendingIntent(): PendingIntent? {
            val intentM = Intent(this@AudioService, MainActivity::class.java)//点击主体进入当前界面中
            val intentA = Intent(this@AudioService, AudioPlayerActivity::class.java)//点击主体进入当前界面中
            intentA.putExtra("from", FROM_CONTENT)
            val intents = arrayOf(intentM, intentA)
            val pendingIntent = PendingIntent.getActivities(this@AudioService, 1, intents, PendingIntent.FLAG_UPDATE_CURRENT)
            return pendingIntent
        }

        /**
         * 通知界面更新
         */
        fun notifyUpdateUi() {
            EventBus.getDefault().post(list?.get(position))
        }

        fun playItem() {
            //如果mediaPlayer已经存在就先释放掉
            if (mediaPlayer != null) {
                mediaPlayer?.reset()
                mediaPlayer?.release()
                mediaPlayer = null
            }
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