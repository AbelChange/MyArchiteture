package com.yunfeng.lib.dialog.dialogmanager

import android.app.Activity
import androidx.fragment.app.FragmentManager
import com.yunfeng.lib.base.BaseDialogFragment
import java.util.concurrent.PriorityBlockingQueue

/**
 * @Description:
 * @Author:         haoshuaihui
 * @CreateDate:     2021/7/13 10:39
 */
object DialogQueue {

    fun add(dialog: IPriorityDialog) {
        mPriorityQueue.add(dialog)
    }

    fun launch(activity: Activity, manager: FragmentManager) {
        nextQueue(activity, manager)
    }

    private fun nextQueue(activity: Activity, manager: FragmentManager) {
        val poll = mPriorityQueue.poll()
        poll?.let {
            if (it is BaseDialogFragment) {
                it.show(activity, manager, poll.javaClass.canonicalName)
                it.setOnDismissListener(object : IPriorityDialog.OnDismissListener {
                    override fun onDismiss() {
                        nextQueue(activity, manager)
                    }
                })
            }
        }
    }

    /**
     * 优先级的dialog
     */
    private var mPriorityQueue = PriorityBlockingQueue<IPriorityDialog>(
        11
    ) { o1, o2 ->
        o2.getPriority() - o1.getPriority()
    }

    /**
     * FIFO
     */
//    private var mFairQueue = LinkedList<IPriorityDialog>()

}