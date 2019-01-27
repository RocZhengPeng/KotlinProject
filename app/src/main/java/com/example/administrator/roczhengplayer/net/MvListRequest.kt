package com.example.administrator.roczhengplayer.net

import com.example.administrator.roczhengplayer.util.URLProviderUtils
import com.itheima.player.model.bean.MvPagerBean

/**
 * Created by Administrator on 2019/1/19.
 */
class MvListRequest(type: Int, code: String, offset: Int, handler: ResponseHandler<MvPagerBean>)
    : MRequest<MvPagerBean>(type, URLProviderUtils.getMVListUrl(code, offset, 20), handler) {
}