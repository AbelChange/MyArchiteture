package com.ablec.module_pay.serviceimpl

import android.app.Activity
import android.text.TextUtils
import com.alipay.sdk.app.PayTask
import com.sankuai.waimai.router.annotation.RouterService
import com.tencent.mm.opensdk.modelpay.PayReq
import com.ablec.module_base.PayOrderDto
import com.ablec.module_base.config.Pay.PAY_SERVICE
import com.ablec.module_base.config.ThirdApi
import com.ablec.module_base.service.IPayService
import com.ablec.module_pay.Util
import kotlinx.coroutines.*
import java.util.concurrent.CopyOnWriteArraySet

/**
 * @Description: 支付service
 * @Author: haoshuaihui
 * @CreateDate: 2021/4/30 13:24
 */
@RouterService(interfaces = [IPayService::class], key = [PAY_SERVICE], singleton = true)
class PayServiceImpl : IPayService {

    private val mObservers: MutableSet<IPayService.Observer> = CopyOnWriteArraySet()

    private val scope = CoroutineScope(Job() + Dispatchers.Main)

    override fun startPay(context: Activity?, order: PayOrderDto?) {
        order?.let {
            if (!TextUtils.isEmpty(order.alipayStr)) {
                dealAlipay(context, order.alipayStr)
            } else {
                dealWeiXinPay(context, order)
            }
        }
    }

    private fun dealWeiXinPay(context: Activity?, order: PayOrderDto) {
        val wxApi = ThirdApi.wxApi
        if (!wxApi.isWXAppInstalled) {
            notifyPayFailure("未安装微信客户端")
            return
        }
        val request = PayReq()
        request.appId = order.appId
        request.partnerId = order.partnerid
        request.prepayId = order.prepayid
        request.packageValue = order.packageInfo
        request.nonceStr = order.noncestr
        request.timeStamp = order.timestamp
        request.sign = order.sign
        wxApi.sendReq(request)
    }

    private fun dealAlipay(context: Activity?, alipayStr: String?) {
        if (!Util.isAliPayInstalled(context!!)) {
            notifyPayFailure("未安装支付宝客户端")
            return
        }
        scope.launch {
            withContext(Dispatchers.IO) {
                val aliPay = PayTask(context)
                val payResult: Map<String, String> = aliPay.payV2(alipayStr, true)
                val resultStatus = payResult["resultStatus"]
//                val result = payResult["result"]
//                val memo = payResult["memo"]
                withContext(Dispatchers.Main) {
                    notifyAlipayResult(resultStatus)
                }
            }

        }
    }

    private fun notifyAlipayResult(resultStatus: String?) {
        when (resultStatus) {
            "9000" -> {
                notifyPaySuccess()
            }
            "8000", "6004" -> {
                notifyPayFailure("支付完成，正在处理支付结果，请稍后查看")
            }
            "6001" -> {
                notifyPayCancel()
            }
            "6002" -> {
                // 网络连接出错
                notifyPayFailure("网络连接出错")
            }
            else -> {
                notifyPayFailure("支付失败,请稍后重试")
            }
        }
    }

    override fun registerObserver(observer: IPayService.Observer?) {
        observer?.let {
            mObservers.add(observer)
        }
    }

    override fun unregisterObserver(observer: IPayService.Observer?) {
        observer?.let {
            mObservers.remove(observer)
        }
    }

    override fun notifyPaySuccess() {
        mObservers.forEach { observer ->
            observer.onPaySuccess()
        }
    }

    override fun notifyPayCancel() {
        mObservers.forEach { observer ->
            observer.onPayCancel()
        }
    }

    override fun notifyPayFailure(reason: String?) {
        mObservers.forEach { observer ->
            observer.onPayFailure(reason)
        }
    }
}