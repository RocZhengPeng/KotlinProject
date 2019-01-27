package com.example.administrator.roczhengplayer.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import com.example.administrator.roczhengplayer.R
import com.itheima.player.model.bean.HomeItemBean
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_home.view.*
import kotlinx.android.synthetic.main.item_mv.view.*

/**
 * Created by Administrator on 2019/1/13.
 */
class HomeItemView : RelativeLayout {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    init {
        View.inflate(context, R.layout.item_home, this)
    }

    /**
     * 刷新条目view数据
     */
    fun setData(data: HomeItemBean) {
        desc.setText(data.description)
        tv_title.setText(data.title)
        Picasso.with(context).load(data.posterPic).into(imageView)
    }
}