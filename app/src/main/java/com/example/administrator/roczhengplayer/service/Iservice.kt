package com.example.administrator.roczhengplayer.service

/**
 * author : roczheng
 * e-mail : 306608923@qq.com
 * time   : 2019/1/28
 * desc   :
 */

interface Iservice {
    fun updatePlayState()
    fun isPlaying(): Boolean?
    fun getDuration(): Int
    fun getProgress(): Int
    fun seekTo(progress: Int)
    fun updatePlayMode()
    fun getPlayMode(): Int
    fun playPre()
    fun playNext()
}