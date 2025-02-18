package com.ablec.lib.mvi

import android.os.Bundle
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.ablec.lib.ext.collectIn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlin.concurrent.thread

abstract class AbstractMviActivity<
        I : MviIntent,
        S : MviViewState,
        E : MviSingleEvent,
        VM : MviViewModel<I, S, E>,
        >(
    @LayoutRes contentLayoutId: Int,
) : AppCompatActivity(contentLayoutId),
    MviView<I, S, E> {
    protected abstract val vm: VM

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
        bindVM()
    }

    private fun bindVM() {
        // observe view model
        vm
            .viewState
            .collectIn(this) { render(it) }

        // observe single event
        vm
            .singleEvent
            .collectIn(this) {
                handleSingleEvent(it)
            }

        // pass view intent to view model
        viewIntents()
            .onEach { vm.processIntent(it) }
            .launchIn(lifecycleScope)
    }

    protected abstract fun setupViews()
}
