package com.example.administrator.roczhengplayer.ui.fragment

import android.content.AsyncQueryHandler
import android.content.ContentResolver
import android.content.pm.PackageManager
import android.database.Cursor
import android.os.AsyncTask
import android.os.Handler
import android.os.Message
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.view.Gravity
import android.view.View
import android.widget.TextView
import com.example.administrator.roczhengplayer.R
import com.example.administrator.roczhengplayer.adapter.VbangAdapter
import com.example.administrator.roczhengplayer.base.BaseFragment
import com.example.administrator.roczhengplayer.model.AudioBean
import com.example.administrator.roczhengplayer.ui.activity.AudioPlayerActivity
import com.example.administrator.roczhengplayer.util.CursorUtil
import kotlinx.android.synthetic.main.fragment_vbang.*
import org.jetbrains.anko.noButton
import org.jetbrains.anko.support.v4.alert
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.yesButton
import java.util.jar.Manifest

/**
 * Created by Administrator on 2019/1/13.
 */
class VBangFragment : BaseFragment() {

/*    val handler = object : Handler() {
        override fun handleMessage(msg: Message?) {
            msg?.let {
                val cursor = msg.obj as Cursor
                //打印数据
                CursorUtil.logCurson(cursor)
            }
        }
    }*/

    override fun initView(): View? {

        return View.inflate(context, R.layout.fragment_vbang, null)
    }

    var adapter: VbangAdapter? = null

    override fun initListener() {
        adapter = VbangAdapter(context, null)
        listView.adapter = adapter
        //设置条目点击事件
        listView.setOnItemClickListener { adapterView, view, i, l ->
            //获取数据集合
            var cursor = adapter?.getItem(i) as Cursor
            //通过当前为止cursor来获取整个列表
            var list: ArrayList<AudioBean> = AudioBean.getAudioBeans(cursor)
            //位置position
            //跳转到音乐播放界面
            startActivity<AudioPlayerActivity>("list" to list,"position" to i)
        }
    }

    override fun initData() {
        handlePermission()
        loadSongs()
    }

    /**
     * 处理权限问题
     */
    private fun handlePermission() {
        val permission = android.Manifest.permission.READ_EXTERNAL_STORAGE
        var checkSelfPermission = ActivityCompat.checkSelfPermission(context, permission)
        if (checkSelfPermission == PackageManager.PERMISSION_GRANTED) {
            //已经获取
            loadSongs()
        } else {
            //没有权限
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
                //需要弹出
                alert("我们只会访问音乐文件，不会访问隐私文件", "温馨提示") {
                    yesButton { MyRequestPermission() }
                    noButton { }
                }
            } else {
                //不需要弹出
                MyRequestPermission()
            }
        }
    }

    private fun MyRequestPermission() {
        var permissions = arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE)
        requestPermissions(permissions, 1)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            loadSongs()
        }
    }

    private fun loadSongs() {
        //加载音乐列表数据
        var resolver = context.contentResolver
        /*var cursor = resolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                arrayOf(MediaStore.Audio.Media.DATA,
                        MediaStore.Audio.Media.SIZE,
                        MediaStore.Audio.Media.DISPLAY_NAME,
                        MediaStore.Audio.Media.ARTIST),
                null, null, null)*/
        //开启线程查询音乐数据
        /* Thread(object : Runnable {
             override fun run() {
                 var cursor = resolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                         arrayOf(MediaStore.Audio.Media.DATA,
                                 MediaStore.Audio.Media.SIZE,
                                 MediaStore.Audio.Media.DISPLAY_NAME,
                                 MediaStore.Audio.Media.ARTIST),
                         null, null, null)
                 val msg = Message.obtain()
                 msg.obj = cursor
                 handler.sendMessage(msg)
             }
         }).start()*/

        //asynctask
        // AudioTask().execute(resolver)

        //AsyncQueryHandler用来查询数据库的异步操作
        val handler = object : AsyncQueryHandler(resolver) {
            //token用来区分查询的
            override fun onQueryComplete(token: Int, cookie: Any?, cursor: Cursor?) {
                //查询完成回调，主线程中
                // (cookie as VbangAdapter).notifyDataSetChanged()
                //设置数据源
                //刷新adapter
                (cookie as VbangAdapter).swapCursor(cursor)
            }
        }
        handler.startQuery(0, adapter, MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                arrayOf(MediaStore.Audio.Media._ID,
                        MediaStore.Audio.Media.DATA,
                        MediaStore.Audio.Media.SIZE,
                        MediaStore.Audio.Media.DISPLAY_NAME,
                        MediaStore.Audio.Media.ARTIST),
                null, null, null)
    }

    /**
     * 音乐查询异步任务
     */
    class AudioTask : AsyncTask<ContentResolver, Void, Cursor>() {
        /**
         * 后台执行任务
         */
        override fun doInBackground(vararg p0: ContentResolver?): Cursor? {
            var cursor = p0[0]?.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                    arrayOf(MediaStore.Audio.Media.DATA,
                            MediaStore.Audio.Media.SIZE,
                            MediaStore.Audio.Media.DISPLAY_NAME,
                            MediaStore.Audio.Media.ARTIST),
                    null, null, null)
            return cursor
        }

        /**
         * 将后台任务回调到主线程
         */
        override fun onPostExecute(result: Cursor?) {
            super.onPostExecute(result)
            //打印
            CursorUtil.logCurson(result)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        //界面销毁 关闭cursor
        //获取adapter中的cursor关闭
        //将adapter的cursor设置为null
        adapter?.changeCursor(null)
    }
}