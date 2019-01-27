package com.example.administrator.roczhengplayer.model

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by Administrator on 2019/1/19.
 * 传递给视屏播放界面的bean类
 */
class VideoPlayBean (var id:Int,var title:String,var url:String):Parcelable{
    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readString(),
            parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(title)
        parcel.writeString(url)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<VideoPlayBean> {
        override fun createFromParcel(parcel: Parcel): VideoPlayBean {
            return VideoPlayBean(parcel)
        }

        override fun newArray(size: Int): Array<VideoPlayBean?> {
            return arrayOfNulls(size)
        }
    }
}