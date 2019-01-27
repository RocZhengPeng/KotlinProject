package com.example.administrator.roczhengplayer.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import com.example.administrator.roczhengplayer.R
import com.itheima.player.model.bean.YueDanBean
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.CropCircleTransformation
import kotlinx.android.synthetic.main.item_mv.view.*
import kotlinx.android.synthetic.main.item_yuedan.view.*

/**
 * Created by Administrator on 2019/1/16.
 * 悦单界面item
 */
class YueDanItemView : RelativeLayout {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    init {
        View.inflate(context, R.layout.item_yuedan, this)
    }

    /**
     * 条目view的控件初始化
     */
    fun setData(data: YueDanBean.PlayListsBean) {
        //歌单名称
        title_one.text = data.title
        author_name.text = data.creator?.nickName
        count.text = data.videoCount.toString()
        //背景
        Picasso.with(context).load(data.playListBigPic).into(bg_yuedan)
        //头像
        Picasso.with(context).load(data.creator?.largeAvatar).transform(CropCircleTransformation()).into(author_image)
    }
}