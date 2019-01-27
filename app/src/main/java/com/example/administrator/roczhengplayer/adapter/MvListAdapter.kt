package com.example.administrator.roczhengplayer.adapter

import android.content.Context
import com.example.administrator.roczhengplayer.base.BaseListAdapter
import com.example.administrator.roczhengplayer.widget.MvItemView
import com.itheima.player.model.bean.VideosBean

/**
 * Created by Administrator on 2019/1/18.
 */
class MvListAdapter : BaseListAdapter<VideosBean, MvItemView>() {
    override fun refreshItemView(itemView: MvItemView, data: VideosBean) {
        itemView.setData(data)
    }

    override fun getItemView(context: Context?): MvItemView {
        return MvItemView(context)
    }
}