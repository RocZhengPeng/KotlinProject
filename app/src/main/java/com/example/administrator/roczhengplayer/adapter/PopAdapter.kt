package com.example.administrator.roczhengplayer.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.example.administrator.roczhengplayer.model.AudioBean
import com.example.administrator.roczhengplayer.widget.PopListItemView

/**
 * Created by Administrator on 2019/1/28.
 */
class PopAdapter(var list: List<AudioBean>) : BaseAdapter() {
    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        var itemView: PopListItemView? = null
        if (p1 == null) {
            itemView = PopListItemView(p2?.context)
        } else {
            itemView = p1 as PopListItemView
        }
        itemView.setData(list.get(p0))
        return itemView
    }

    override fun getItem(p0: Int): Any {
        return list.get(p0)
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getCount(): Int {
        return list.size
    }
}