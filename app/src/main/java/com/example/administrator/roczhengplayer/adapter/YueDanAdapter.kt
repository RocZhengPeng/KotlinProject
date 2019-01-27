package com.example.administrator.roczhengplayer.adapter

import android.content.Context
import com.example.administrator.roczhengplayer.base.BaseListAdapter
import com.example.administrator.roczhengplayer.widget.YueDanItemView
import com.itheima.player.model.bean.YueDanBean

/**
 * Created by Administrator on 2019/1/16.
 */
class YueDanAdapter : BaseListAdapter<YueDanBean.PlayListsBean,YueDanItemView>() {
    override fun refreshItemView(itemView: YueDanItemView, data: YueDanBean.PlayListsBean) {
           itemView.setData(data)
    }

    override fun getItemView(context: Context?): YueDanItemView {
        return  YueDanItemView(context)
    }
}