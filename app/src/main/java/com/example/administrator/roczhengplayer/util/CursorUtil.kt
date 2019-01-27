package com.example.administrator.roczhengplayer.util

import android.database.Cursor

/**
 * Created by Administrator on 2019/1/22.
 * 静态，cursor打印util
 */
object CursorUtil {
    fun logCurson(cursor: Cursor?) {
        cursor?.let {
            //将cursor游标复位
            it.moveToPosition(-1)
            while (it.moveToNext()) {
                //until不包含columnCount
                for (index in 0 until it.columnCount) {
                    println("key=${it.getColumnName(index)}--value=${it.getString(index)}")
                }
            }
        }
    }
}