package com.yunfeng.module_base.dto

import android.graphics.Bitmap
import com.google.gson.annotations.SerializedName
import com.yunfeng.module_base.config.ModuleConstant

//{"id":12,"a_id":6,"name":"分享","url":"http://testjiasu.upnono.com/act/shoot_iphone/index.html?activity_id=6&channel=huawei","game_system":0,"title":"[有人@我] 喊你来抽奖","key":"fx","img_url":"http://teststaticjs.upnono.com/jiasuup/pic/202007/31/375146fccaaf6ab518ee1a5ff43b7517.jpg","content":"测试中测试中测试中","is_outer_link":1,"is_enable":1,"update_time":"2020-08-28 11:22:39","create_time":"2020-07-31 01:56:08"}

data class ShareInfo(
    @SerializedName("id")
    var id: Int = 0,
    @SerializedName("a_id")
    var a_id: String? = null,
    @SerializedName("title")
    var title: String? = null,
    @SerializedName("url")
    var url: String? = null,
    @SerializedName("content")
    var content: String? = null,
    ////     teststaticjs.upnono.com/jiasuup/pic/202105/13/4d9cffce21e16e3493c1a75072c99ec2.png
    //http://teststaticjs.upnono.com/jiasuup/pic/202105/13/4d9cffce21e16e3493c1a75072c99ec2.png
    @SerializedName("s_icon")
    var imgIcon: String? = null,
    @SerializedName("img_url")
    var img_url: String? = null,
    @SerializedName("is_enable")
    var is_enable: Int = 1,
    @SerializedName("imageByte")
    var bitmap: Bitmap? = null,
    var shareEventId: Int = -1,
    val shareType: Int = ModuleConstant.Share.WEB
)