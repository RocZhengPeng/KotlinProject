package com.example.administrator.roczhengplayer.adapter

import android.content.Context
import com.example.administrator.roczhengplayer.base.BaseListAdapter
import com.example.administrator.roczhengplayer.widget.HomeItemView
import com.itheima.player.model.bean.HomeItemBean

/**
 * Created by Administrator on 2019/1/13.
 */
class HomeAdapter : BaseListAdapter<HomeItemBean, HomeItemView>() {
    override fun refreshItemView(itemView: HomeItemView, data: HomeItemBean) {
        itemView.setData(data)
    }

    override fun getItemView(context: Context?): HomeItemView {
        return HomeItemView(context)
    }
}