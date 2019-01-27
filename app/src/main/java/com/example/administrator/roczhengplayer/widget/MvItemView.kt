package com.example.administrator.roczhengplayer.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import com.example.administrator.roczhengplayer.R
import com.itheima.player.model.bean.VideosBean
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_mv.view.*

/**
 * Created by Administrator on 2019/1/18.
 */
class MvItemView : RelativeLayout {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)
    init {
        View.inflate(context, R.layout.item_mv,this)
    }

    /**
     * 适配每一个条目的View
     *
     */
    fun setData(data: VideosBean) {
        //歌手名称
        artist.text=data.artistName
        //歌曲名称
        title.text=data.title

        Picasso.with(context).load(data.playListPic).into(bg)

    }
}