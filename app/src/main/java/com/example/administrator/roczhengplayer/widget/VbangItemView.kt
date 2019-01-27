package com.example.administrator.roczhengplayer.widget

import android.content.Context
import android.text.format.Formatter
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import com.example.administrator.roczhengplayer.R
import com.example.administrator.roczhengplayer.model.AudioBean
import kotlinx.android.synthetic.main.item_mv.view.*
import kotlinx.android.synthetic.main.item_vbang.view.*

/**
 * Created by Administrator on 2019/1/23.
 */
class VbangItemView : RelativeLayout {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    init {
        View.inflate(context, R.layout.item_vbang, this)
    }

    fun setData(itemBean: AudioBean) {
        title1.text = itemBean.display_name
        artist1.text = itemBean.artist
        size.text = Formatter.formatFileSize(context, itemBean.size)

    }
}