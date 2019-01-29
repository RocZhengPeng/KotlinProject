package com.example.administrator.roczhengplayer.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View
import com.example.administrator.roczhengplayer.R
import com.example.administrator.roczhengplayer.model.LyricBean

/**
 * author : roczheng
 * e-mail : 306608923@qq.com
 * time   : 2019/1/29
 * desc   : 自定义歌词view
 */

class LyricView : View {
    val paint by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG)//设置抗锯齿
    }

    val list by lazy { ArrayList<LyricBean>() }
    var viewW: Int = 0
    var viewH: Int = 0
    var bigSize = 0f
    var smallSize = 0f
    var white = 0
    var green = 0

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    init {
        bigSize = resources.getDimension(R.dimen.bigSize)
        smallSize = resources.getDimension(R.dimen.smallSize)
        white = resources.getColor(R.color.white)
        green = resources.getColor(R.color.green)

        //画笔
        paint.textAlign = Paint.Align.CENTER//在X放方向确定位置是通过中间位置确定坐标

        //循环添加歌词bean
        for (i in 0 until 30) {
            list.add(LyricBean(2000 * i, "正在播放第${i}行文本"))
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

       // drawSingleLine(canvas)
        drawManyLine(canvas)
    }

    /**
     * 绘制多行居中
     */
    private fun drawManyLine(canvas: Canvas?) {
        
    }

    /**
     * 绘制单行居中
     */
    private fun drawSingleLine(canvas: Canvas?) {
        //初始化pint颜色和大小
        paint.textSize = bigSize
        paint.color = green

        val text = "正在加载歌词"
        //求文本的宽度和高度
        val bounds = Rect()
        paint.getTextBounds(text, 0, text.length, bounds)
        val textW = bounds.width()
        val textH = bounds.height()

        //val x = viewW / 2 - textW / 2
        val y = viewH / 2 - textH / 2

        //绘制内容
        canvas?.drawText(text, viewW / 2.toFloat(), y.toFloat(), paint)
    }

    /**
     * 布局之后执行
     */
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        viewH = h
        viewW = w
    }
}