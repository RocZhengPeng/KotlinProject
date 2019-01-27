package com.example.administrator.roczhengplayer.net

import com.example.administrator.roczhengplayer.util.URLProviderUtils
import com.itheima.player.model.bean.YueDanBean

/**
 * Created by Administrator on 2019/1/17.
 */
class YueDanRequest(type: Int, offset: Int, handler: ResponseHandler<YueDanBean>)
    : MRequest<YueDanBean>(type, URLProviderUtils.getYueDanUrl(offset, 20), handler) {
}