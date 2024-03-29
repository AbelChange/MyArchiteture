package com.ablec.myarchitecture.logic.other

import android.animation.Animator
import android.view.ViewGroup
import android.view.animation.Interpolator
import android.view.animation.LinearInterpolator
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import jp.wasabeef.recyclerview.internal.ViewHelper

/**
 * Copyright (C) 2021 Daichi Furiya / Wasabeef
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
abstract class AnimationAdapter2(wrapped: RecyclerView.Adapter<out ViewHolder>) :
    RecyclerView.Adapter<ViewHolder>() {

    private var duration = 300
    private var interpolator: Interpolator = LinearInterpolator()
    private var lastPosition = -1
    private var isFirstOnly = true

    protected var adapter: RecyclerView.Adapter<ViewHolder>

    init {
        @Suppress("UNCHECKED_CAST")
        this.adapter = wrapped as RecyclerView.Adapter<ViewHolder>
        super.setHasStableIds(this.adapter.hasStableIds())
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return adapter.onCreateViewHolder(parent, viewType)
    }

    override fun registerAdapterDataObserver(observer: AdapterDataObserver) {
        super.registerAdapterDataObserver(observer)
        adapter.registerAdapterDataObserver(observer)
    }

    override fun unregisterAdapterDataObserver(observer: AdapterDataObserver) {
        super.unregisterAdapterDataObserver(observer)
        adapter.unregisterAdapterDataObserver(observer)
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        adapter.onAttachedToRecyclerView(recyclerView)
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        adapter.onDetachedFromRecyclerView(recyclerView)
    }

    override fun onViewAttachedToWindow(holder: ViewHolder) {
        super.onViewAttachedToWindow(holder)
        adapter.onViewAttachedToWindow(holder)
    }

    override fun onViewDetachedFromWindow(holder: ViewHolder) {
        super.onViewDetachedFromWindow(holder)
        adapter.onViewDetachedFromWindow(holder)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        adapter.onBindViewHolder(holder, position)
        val adapterPosition = holder.bindingAdapterPosition
        if (!isFirstOnly || adapterPosition > lastPosition) {
            for (anim in getAnimators(holder)) {
                anim.setDuration(duration.toLong()).start()
                anim.interpolator = interpolator
            }
            lastPosition = adapterPosition
        } else {
            ViewHelper.clear(holder.itemView)
        }
    }

    override fun onViewRecycled(holder: ViewHolder) {
        adapter.onViewRecycled(holder)
        super.onViewRecycled(holder)
    }

    override fun getItemCount(): Int {
        return adapter.itemCount
    }

    fun setDuration(duration: Int) {
        this.duration = duration
    }

    fun setInterpolator(interpolator: Interpolator) {
        this.interpolator = interpolator
    }

    fun setStartPosition(start: Int) {
        lastPosition = start
    }

    protected abstract fun getAnimators(viewHolder: ViewHolder): Array<Animator>
    fun setFirstOnly(firstOnly: Boolean) {
        isFirstOnly = firstOnly
    }

    override fun getItemViewType(position: Int): Int {
        return adapter.getItemViewType(position)
    }

    val wrappedAdapter: RecyclerView.Adapter<ViewHolder>
        get() = adapter

    override fun setHasStableIds(hasStableIds: Boolean) {
        super.setHasStableIds(hasStableIds)
        adapter.setHasStableIds(hasStableIds)
    }

    override fun getItemId(position: Int): Long {
        return adapter.getItemId(position)
    }
}
