package com.example.administrator.roczhengplayer.base

import android.graphics.Color
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.example.administrator.roczhengplayer.R
import kotlinx.android.synthetic.main.fragment_home.*

/**
 * Created by Administrator on 2019/1/17.
 * 所有具有下拉刷新和上拉加载的基类
 * 基类抽取
 * HomeView可变-BaseView
 * Adapter->BaseListAdapter
 * Presenter->BasePresenter
 */
abstract class BaseListFragment<RESPONSE, ITEMBEAN, ITEMVIEW : View> : BaseFragment(), BaseView<RESPONSE> {
    override fun loadMore(response: RESPONSE?) {
        adapter.loadMore(getList(response))
    }

    override fun loadSuccess(response: RESPONSE?) {
        refreshLayout.isRefreshing = false
        adapter.updateList(getList(response))
    }

    override fun onError(message: String?) {
        myToast("数据加载失败！")
    }

    val adapter by lazy {
        getSpecialAdapter()
    }

    val presenter by lazy {
        getSpecialPresenter()
    }

    override fun initView(): View? {
        return View.inflate(context, R.layout.fragment_home, null)
    }

    override fun initListener() {
        recycleView.layoutManager = LinearLayoutManager(context)
        //适配
        recycleView.adapter = adapter

        //初始化刷新控件
        refreshLayout.setColorSchemeColors(Color.RED, Color.YELLOW, Color.GREEN)

        //设置刷新监听
        refreshLayout.setOnRefreshListener {
            //刷新监听
            presenter.loadDatas()
        }

        //监听列表滑动
        recycleView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {//是否为空闲状态
                    //最后一条是否已经显示
                    val layoutManager = recyclerView.layoutManager
                    if (layoutManager is LinearLayoutManager) {
                        val manager: LinearLayoutManager = layoutManager//不需要强转
                        val lastPosition = manager.findLastVisibleItemPosition()//获取最后一个可见条目
                        if (lastPosition == adapter.itemCount - 1) {
                            //最后一条已经显示了
                            presenter.loadMore(adapter.itemCount - 1)
                        }
                    }
                }
            }

            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
            }
        })
    }

    override fun initData() {
        //初始化数据
        presenter.loadDatas()
    }

    /**
     * 获取适配器adapter
     */
    abstract fun getSpecialAdapter(): BaseListAdapter<ITEMBEAN, ITEMVIEW>

    /**
     * 获取presenter
     */
    abstract fun getSpecialPresenter(): BaseListPresenter


    /**
     * 从返回结果
     */
    abstract fun getList(response: RESPONSE?): List<ITEMBEAN>?
}