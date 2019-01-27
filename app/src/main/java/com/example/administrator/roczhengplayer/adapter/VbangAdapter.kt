package com.example.administrator.roczhengplayer.adapter

import android.content.Context
import android.database.Cursor
import android.support.v4.widget.CursorAdapter
import android.view.View
import android.view.ViewGroup
import com.example.administrator.roczhengplayer.model.AudioBean
import com.example.administrator.roczhengplayer.widget.VbangItemView

/**
 * Created by Administrator on 2019/1/23.
 * v榜界面
 */
class VbangAdapter(context: Context?, c: Cursor?) : CursorAdapter(context, c) {
    /**
     * 创建条目View
     */
    override fun newView(context: Context?, cursor: Cursor?, parent: ViewGroup?): View {
        return VbangItemView(context)
    }

    /**
     * 进行View和数据的绑定
     */
    override fun bindView(view: View?, context: Context?, cursor: Cursor?) {
        //view
        val itemView = view as VbangItemView
        //data
        var itemBean = AudioBean.getAudioBean(cursor)
        //view+data
        itemView.setData(itemBean)
    }
}