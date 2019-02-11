package com.example.administrator.roczhengplayer.util

import android.os.Environment
import java.io.File

/**
 * Created by Administrator on 2019/2/1.
 * 歌词文件加载类util
 */
object LyricLoader {
    //歌词文件夹
    val dir = File(Environment.getExternalStorageDirectory(), "Download/Lyric")

    /**
     * 根据歌曲名称加载歌词文件
     */
    fun loadLyricFile(display_name: String): File {
        return File(dir, display_name + ".lrc")
    }
}