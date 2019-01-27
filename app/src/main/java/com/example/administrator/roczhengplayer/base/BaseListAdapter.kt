package com.example.administrator.roczhengplayer.base

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.example.administrator.roczhengplayer.widget.LoadMoreView

/**
 * Created by Administrator on 2019/1/17.
 * 所有下拉刷新和上拉加载更多列表界面的Adapter
 *
 */
abstract class BaseListAdapter<ITEMBEAN, ITEMVIEW : View> : RecyclerView.Adapter<BaseListAdapter.BaseListHolder>() {

    private var list = ArrayList<ITEMBEAN>()

    fun updateList(list: List<ITEMBEAN>?) {
        //  if (list == null) return  java 中的写法

        /* val s = "123"
         s.let { a ->
             Unit
             a.toInt()
         }*/

        list?.let {
            this.list.clear()
            this.list.addAll(list)
            notifyDataSetChanged()
        }
    }

    fun loadMore(list: List<ITEMBEAN>?) {
        list?.let {
            this.list.addAll(list)
            notifyDataSetChanged()
        }
    }


    //定义函数类型变量
    var listener: ((itemBean: ITEMBEAN) -> Unit)? = null

    fun setMyListener(listener: (itemBean: ITEMBEAN) -> Unit) {
        this.listener = listener
    }

    override fun onBindViewHolder(holder: BaseListAdapter.BaseListHolder?, position: Int) {
        //如果是最后一条就不需要再刷新view
        if (position == list.size) return

        val data = list.get(position)
        //条目view
        val itemView = holder?.itemView as ITEMVIEW
        //条目刷新
        refreshItemView(itemView, data)

        //设置点击事件
        itemView.setOnClickListener {
            //方法1，listener不为空才执行
        /*   listener?.let {
               it(data)
           }*/
            //方法2，listener不为空才执行
            listener?.invoke(data)
        }
    }




    override fun getItemViewType(position: Int): Int {
        if (position == list.size) {
            return 1
        } else {
            return 0
        }
    }

    override fun getItemCount(): Int {
        return list.size + 1
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): BaseListAdapter.BaseListHolder {
        if (viewType == 1) {
            return BaseListHolder(LoadMoreView(parent?.context))
        } else {
            return BaseListHolder(getItemView(parent?.context))
        }

    }

    class BaseListHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    /**
     * 刷新条目view
     */
    abstract fun refreshItemView(itemView: ITEMVIEW, data: ITEMBEAN)

    /**
     * 获取条目view
     */
    abstract fun getItemView(context: Context?): ITEMVIEW
}