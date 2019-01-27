package com.example.administrator.roczhengplayer.net

import com.example.administrator.roczhengplayer.util.URLProviderUtils
import com.itheima.player.model.bean.HomeItemBean

/**
 * Created by Administrator on 2019/1/16.
 */
class HomeRequest(type: Int, offset: Int, handler: ResponseHandler<List<HomeItemBean>>) :
        MRequest<List<HomeItemBean>>(type, URLProviderUtils.getHomeUrl(offset, 20), handler) {
}