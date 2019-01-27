package com.example.administrator.roczhengplayer.model

import android.annotation.SuppressLint
import android.database.Cursor
import android.os.Parcel
import android.os.Parcelable
import io.vov.vitamio.provider.MediaStore
import java.util.*
import kotlin.collections.ArrayList

/**
 * Created by Administrator on 2019/1/23.
 * 音乐列表条目bean类
 */
data class AudioBean(var date: String, var size: Long, var display_name: String, var artist: String) : Parcelable{
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readLong(),
            parcel.readString(),
            parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel?, p1: Int) {
        parcel?.writeString(date)
        parcel?.writeLong(size)
        parcel?.writeString(display_name)
        parcel?.writeString(artist)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<AudioBean> {
        override fun createFromParcel(parcel: Parcel): AudioBean {
            return AudioBean(parcel)
        }

        override fun newArray(size: Int): Array<AudioBean?> {
            return arrayOfNulls(size)
        }

        /**
         * 根据特定位置上的cursor获取bean类
         */
        fun getAudioBean(cursor: Cursor?): AudioBean {
            //创建一个audioBean对象
            var audioBean = AudioBean("", 0, "", "")
            //判断cursor是否为空
            cursor?.let {
                //解析cursor并且设置到bean对象中
                audioBean.date = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA))
                audioBean.size = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.SIZE))
                audioBean.display_name = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME))
                audioBean.display_name = audioBean.display_name.substring(0, audioBean.display_name.lastIndexOf("."))
                audioBean.artist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST))
            }
            //解析cursor并且设置到bean对象中
            return audioBean
        }

        /**
         * 根据特定为位置cursor获取整个播放列表
         */
        fun getAudioBeans(cursor: Cursor?): ArrayList<AudioBean> {
            //创建集合
            var list = ArrayList<AudioBean>()
            //cursor是否为空
            cursor?.let {
                //将curso游标移动到-1
                it.moveToPosition(-1)
                while (it.moveToNext()) {
                    val autioBean = getAudioBean(it)
                    list.add(autioBean)
                }
                //解析cursor添加到集合中
            }
            return list
        }
    }
}