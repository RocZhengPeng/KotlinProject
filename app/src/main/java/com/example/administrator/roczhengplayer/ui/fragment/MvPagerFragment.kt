package com.example.administrator.roczhengplayer.ui.fragment

import android.view.TextureView
import android.view.View
import android.widget.TextView
import com.example.administrator.roczhengplayer.adapter.MvListAdapter
import com.example.administrator.roczhengplayer.base.BaseFragment
import com.example.administrator.roczhengplayer.base.BaseListAdapter
import com.example.administrator.roczhengplayer.base.BaseListFragment
import com.example.administrator.roczhengplayer.base.BaseListPresenter
import com.example.administrator.roczhengplayer.model.VideoPlayBean
import com.example.administrator.roczhengplayer.presenter.imp.MvListPresenterImpl
import com.example.administrator.roczhengplayer.presenter.interf.MvListPresenter
import com.example.administrator.roczhengplayer.ui.activity.*
import com.example.administrator.roczhengplayer.view.MvListView
import com.example.administrator.roczhengplayer.widget.MvItemView
import com.itheima.player.model.bean.MvPagerBean
import com.itheima.player.model.bean.VideosBean
import org.jetbrains.anko.support.v4.startActivity

/**
 * Created by Administrator on 2019/1/18.
 * MV界面每一个页面的Fragment
 */
class MvPagerFragment : BaseListFragment<MvPagerBean, VideosBean, MvItemView>(), MvListView {
    var code: String? = null

    override fun init() {
        code = arguments.getString("args")
    }

    override fun getSpecialAdapter(): BaseListAdapter<VideosBean, MvItemView> {
        return MvListAdapter()
    }

    override fun getSpecialPresenter(): BaseListPresenter {
        return MvListPresenterImpl(code!!, this)
    }

    override fun getList(response: MvPagerBean?): List<VideosBean>? {
        return response?.videos
    }

    override fun initListener() {
        super.initListener()
        //设置条目点击事件监听函数
        adapter.setMyListener {
            var videoPlayBean = VideoPlayBean(it.id, it.title, it.url)
            //跳转到视屏播放界面
            //startActivity<VideoPlayerActivity>("item" to videoPlayBean)
            startActivity<JiecaoVideoPlayerActivity>("item" to videoPlayBean)
        }
    }

}