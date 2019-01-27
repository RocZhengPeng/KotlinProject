package com.example.administrator.roczhengplayer.net

import com.example.administrator.roczhengplayer.util.URLProviderUtils
import com.itheima.player.model.bean.MvAreaBean

/**
 * Created by Administrator on 2019/1/18.
 * MV区域数据请求类
 */
class MvAreaRequest( handler: ResponseHandler<List<MvAreaBean>>)
    : MRequest<List<MvAreaBean>>(0, URLProviderUtils.getMVareaUrl(), handler) {

}