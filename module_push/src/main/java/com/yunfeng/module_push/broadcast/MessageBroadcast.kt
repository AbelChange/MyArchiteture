package com.yunfeng.module_push.broadcast

import android.content.Context
import com.blankj.utilcode.util.LogUtils
import com.tencent.android.tpush.*
import com.yunfeng.module_base.service.RouterServiceManager

/**
 * 腾讯信鸽推送消息接收
 */

class MessageBroadcast : XGPushBaseReceiver() {

    override fun onRegisterResult(
        context: Context, i: Int,
        xgPushRegisterResult: XGPushRegisterResult
    ) {
//        LogUtils.d("onRegisterResult：" + xgPushRegisterResult.toJson().toString());
    }

    override fun onUnregisterResult(context: Context, i: Int) {
        LogUtils.d(TAG, "onUnregisterResult")
    }

    override fun onSetTagResult(context: Context, i: Int, s: String) {}
    override fun onDeleteTagResult(context: Context, i: Int, s: String) {}
    override fun onSetAccountResult(context: Context, i: Int, s: String) {}
    override fun onDeleteAccountResult(context: Context, i: Int, s: String) {}

    /**
     * 应用内消息的回调
     *
     * @param context
     * @param xgPushTextMessage
     */
    override fun onTextMessage(context: Context, xgPushTextMessage: XGPushTextMessage) {
        LogUtils.d(TAG, "onTextMessage：" + xgPushTextMessage.content)
    }

    override fun onNotificationClickedResult(
        context: Context,
        xgPushClickedResult: XGPushClickedResult
    ) {
        LogUtils.d(TAG, "onNotificationClickedResult:" + xgPushClickedResult.customContent)
    }

    override fun onNotificationShowedResult(
        context: Context,
        xgPushShowedResult: XGPushShowedResult
    ) {
        LogUtils.d(
            TAG,
            "onNotificationShowedResult:" + xgPushShowedResult.customContent + "\t" + xgPushShowedResult.title + "\t" + xgPushShowedResult.content
        )
        RouterServiceManager.getPushService()?.notifyMsgCome()
    }

    override fun onSetAttributeResult(p0: Context?, p1: Int, p2: String?) {
    }

    override fun onQueryTagsResult(p0: Context?, p1: Int, p2: String?, p3: String?) {
    }

    override fun onDeleteAttributeResult(p0: Context?, p1: Int, p2: String?) {
    }

    companion object {
        const val TAG = "XGPush"
        private const val CONTENT = "content"
        private const val TYPE = "type"
    }
}