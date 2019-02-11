package com.example.administrator.roczhengplayer.ui.activity

import android.content.ComponentName
import android.content.Context
import android.content.ServiceConnection
import android.graphics.drawable.AnimationDrawable
import android.os.Handler
import android.os.IBinder
import android.os.Message
import android.view.View
import android.widget.AdapterView
import android.widget.SeekBar
import com.example.administrator.roczhengplayer.R
import com.example.administrator.roczhengplayer.adapter.PopAdapter
import com.example.administrator.roczhengplayer.base.BaseActivity
import com.example.administrator.roczhengplayer.model.AudioBean
import com.example.administrator.roczhengplayer.service.AudioService
import com.example.administrator.roczhengplayer.service.Iservice
import com.example.administrator.roczhengplayer.util.StringUtil
import com.example.administrator.roczhengplayer.widget.PlayListPopWindow
import kotlinx.android.synthetic.main.activity_music_player_bottom.*
import kotlinx.android.synthetic.main.activity_music_player_middle.*
import kotlinx.android.synthetic.main.activity_music_player_top.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * Created by Administrator on 2019/1/23.
 */
class AudioPlayerActivity : BaseActivity(), View.OnClickListener, SeekBar.OnSeekBarChangeListener, AdapterView.OnItemClickListener {
    /**
     * 弹出的播放列表ListViewitem点击事件
     */
    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        //播放当前歌曲
        iService?.playPosition(position)
    }

    /**
     * 进度改变的回调
     * progress:改变之后的进度
     * fromUser:true通过用户手指拖动改变的，false通过代码的方式改变的进度
     */
    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        //判断是否是用户操作
        if (fromUser) {
            return
        }
        //更新播放进度
        iService?.seekTo(progress)
        //更新界面进度显示
        updateProgress(progress)
    }

    /**
     * 手指触摸seekbar回调
     */
    override fun onStartTrackingTouch(seekBar: SeekBar?) {

    }

    /**
     * 手指离开回调
     */
    override fun onStopTrackingTouch(seekBar: SeekBar?) {

    }

    var audioBean: AudioBean? = null
    var drawable: AnimationDrawable? = null
    var duration: Int = 0
    val MSG_PROGRESS = 0
    val handler = object : Handler() {
        override fun handleMessage(msg: Message?) {
            when (msg?.what) {
                MSG_PROGRESS -> startUpdateProgress()
            }
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.state_play -> updatePlayState()
            R.id.back -> finish()
            R.id.mode -> updatePlayMode()
            R.id.pre -> iService?.playPre()
            R.id.next -> iService?.playNext()
            R.id.playlist -> showPlayList()
        }
    }

    /**
     * 显示播放列表
     */
    private fun showPlayList() {
        val list = iService?.getPlayList()
        list?.let {
            //创建adapter
            val adpater = PopAdapter(it)
            //获取底部高度
            val bottomH = audio_player_bottom.height
            val popWindow = PlayListPopWindow(this, adpater, this, window)
            popWindow.showAsDropDown(audio_player_bottom, 0, bottomH)
        }
    }

    /**
     * 更新播放模式
     */
    private fun updatePlayMode() {
        //修改service中的model
        iService?.updatePlayMode()
        //修改界面模式图标
        updatePlayModeBtn()
    }

    /**
     * 根据当前播放模式修改播放模式图标
     */
    private fun updatePlayModeBtn() {
        iService?.let {
            //获取播放模式
            val modeI: Int = it.getPlayMode()
            //设置图标
            when (modeI) {
                AudioService.MODE_ALL -> mode.setImageResource(R.drawable.selector_btn_playmode_order)
                AudioService.MODE_SINGLE -> mode.setImageResource(R.drawable.selector_btn_playmode_single)
                AudioService.MODE_RANDOM -> mode.setImageResource(R.drawable.selector_btn_playmode_random)
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEventMainThread(itemBean: AudioBean) {
        //设置播放歌曲名称
        lyricView.setSongName(itemBean.display_name)
        //记录下播放的歌曲
        this.audioBean = itemBean
        //歌曲名称
        audio_title.text = itemBean.display_name
        artist_name.text = itemBean.artist

        //更新播放状态按钮
        updatePlayStateBtn()
        //动画播放
        drawable = audio_anim.drawable as AnimationDrawable
        drawable?.state

        //获取总进度
        duration = iService?.getDuration() ?: 0
        //设置歌词播放总进度
        lyricView.setSongDuration(duration)

        //进度条设置进度最大值
        progress_sk.max = duration
        //更新播放进度
        startUpdateProgress()

        //更新播放模式图标
        updatePlayModeBtn()

    }

    /**
     * 开始更新进度
     */
    private fun startUpdateProgress() {
        //获取当前进度
        var progress: Int = iService?.getProgress() ?: 0
        //更新进度数据
        updateProgress(progress)
        //定时获取进度
        handler.sendEmptyMessage(MSG_PROGRESS)

    }

    /**
     * 根据当前进度数据更新界面(界面操作)
     */
    private fun updateProgress(pro: Int) {
        //更新进度数值
        progress.text = (StringUtil.parseDuration(pro) + "/" + StringUtil.parseDuration(duration))
        //更新进度条
        progress_sk.setProgress(pro)
        //更新歌词进度
        lyricView.updateProgress(pro)
    }

    /**
     * 更新播放状态
     */
    private fun updatePlayState() {
        //更新播放状态
        iService?.updatePlayState()
        //更新播放状态图标
        updatePlayStateBtn()
    }

    /**
     * 根据播放状态更新图标
     */
    private fun updatePlayStateBtn() {
        //获取当前状态
        val isPlaying = iService?.isPlaying()

        isPlaying?.let {
            //根据状态更新图标
            if (isPlaying) {
                //播放
                state_play.setImageResource(R.drawable.selector_btn_audio_play)

                //开始播放动画
                drawable?.state

                //开始更新进度
                handler.sendEmptyMessage(MSG_PROGRESS)
            } else {
                //暂停
                state_play.setImageResource(R.drawable.selector_btn_audio_pause)

                //停止播放动画
                drawable?.stop()

                //停止更新进度
                handler.removeMessages(MSG_PROGRESS)
            }
        }
    }

    override fun initListener() {
        //播放状态切换
        state_play.setOnClickListener(this)
        back.setOnClickListener(this)
        //进度条变化的监听
        progress_sk.setOnSeekBarChangeListener(this)
        //播放模式点击事件
        mode.setOnClickListener(this)
        //上一曲和下一曲
        pre.setOnClickListener(this)
        next.setOnClickListener(this)
        //播放列表
        playlist.setOnClickListener(this)
        //歌词拖动进度更新监听
        lyricView.setProgressListener {
            //更新播放进度
            iService?.seekTo(it)
            //更新进度显示
            updateProgress(it)
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_audio_player
    }

    val conn by lazy {
        AudioConnection()
    }

    override fun initData() {
        //注册EventBus
        EventBus.getDefault().register(this)
        /*  var list = intent.getParcelableArrayListExtra<AudioBean>("list")
          var position = intent.getIntExtra("position", -1)*/

        //开启服务，通过AudioService播放音乐
        // val intent = Intent(this, AudioService::class.java)

        val intent = intent
        intent.setClass(this, AudioService::class.java)
        //通过Intent将list以及position传递过去
        /*  intent.putExtra("list", list)
          intent.putExtra("position", position)*/
        //先绑定服务,BIND_AUTO_CREATE绑定的时候服务吗，没有创建就给它创建出来
        bindService(intent, conn, Context.BIND_AUTO_CREATE)
        //再开启服务
        startService(intent)

        /*   //播放音乐
           val mediaPlayer= MediaPlayer()
           mediaPlayer.setOnPreparedListener {
               //开始播放
               mediaPlayer.start()
           }
           mediaPlayer.setDataSource(list.get(position).date)
           mediaPlayer.prepareAsync()*/
    }

    var iService: Iservice? = null

    inner class AudioConnection : ServiceConnection {
        /**
         * 意外断开连接时
         */
        override fun onServiceDisconnected(name: ComponentName?) {

        }

        /**
         * service连接的时候
         */
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            iService = service as Iservice
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        //手动解绑服务
        unbindService(conn)
        EventBus.getDefault().unregister(this)
        //清空handler发送的所有消息
        handler.removeCallbacksAndMessages(null)
    }
}