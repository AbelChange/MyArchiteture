package com.ablec.myarchitecture.logic.main.anim

import android.animation.ValueAnimator
import android.opengl.GLSurfaceView
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.ablec.lib.ext.viewBinding
import com.ablec.module_base.service.RouterServiceManager
import com.ablec.myarchitecture.R
import com.ablec.myarchitecture.databinding.FragmentPagBinding
import com.ablec.myarchitecture.logic.pag.GLRender
import com.ablec.myarchitecture.logic.pag.PagLayerWrapper
import com.ablec.myarchitecture.logic.pag.PlayEndStrategy
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import org.libpag.PAGFile


class PAGFragment : Fragment(R.layout.fragment_pag) {

    private val binding: FragmentPagBinding by viewBinding()

    private var renderJob:Job? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupSharedPagSystem()
    }

    val glRender by lazy {
        return@lazy GLRender(context, listOf<PagLayerWrapper>(
            PagLayerWrapper(
                PAGFile.Load(context?.assets, "autumn_start3.pag"),
                PlayEndStrategy.ONCE
            ),
            PagLayerWrapper(
                PAGFile.Load(context?.assets, "autumn_bg3.pag"),
                PlayEndStrategy.REPEAT
            ),
            PagLayerWrapper(
                PAGFile.Load(context?.assets, "autumn_rabbit3.pag"),
                PlayEndStrategy.REPEAT
            ),
            PagLayerWrapper(
                PAGFile.Load(context?.assets, "autumn_tree3.pag"),
                PlayEndStrategy.REPEAT
            ),

            PagLayerWrapper(
                PAGFile.Load(context?.assets, "autumn_rabbit_click3.pag"),
                PlayEndStrategy.ONCE
            ),
            PagLayerWrapper(
                PAGFile.Load(context?.assets, "autumn_tree_click3.pag"),
                PlayEndStrategy.ONCE
            ),

            PagLayerWrapper(
                PAGFile.Load(context?.assets, "autumn_end3.pag"),
                PlayEndStrategy.REPEAT
            )
        ))
    }


    private fun setupSharedPagSystem() {

        binding.surfaceView1.apply {
            setEGLContextClientVersion(2);
            setRenderer(glRender)
            renderMode = GLSurfaceView.RENDERMODE_WHEN_DIRTY;
            //该模式下可以持续播放 onDrawFrame会持续调用
            //renderMode = GLSurfaceView.RENDERMODE_CONTINUOUSLY;
        }

        val FPS = 12L

        renderJob = lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED){
                while (isActive) {
                    delay(1000 / FPS)//12帧
                    binding.surfaceView1.requestRender()
                }
            }
        }

        binding.surfaceView1.setOnClickListener {
            binding.surfaceView1.queueEvent {
                glRender.resetRoot(1)
                binding.surfaceView1.setOnClickListener {
                    binding.surfaceView1.queueEvent {
                        glRender.addLayer(2)
                        glRender.addLayer(3)
                    }
                }
            }
        }

        binding.btnAlpha.setOnClickListener {
            ValueAnimator.ofFloat(0.5f, 1.0f).apply {
                duration = 1000 // 动画持续时间（毫秒）
                addUpdateListener { animation ->
                    val value = animation.animatedValue as Float
                    // 使用 value 更新透明度、缩放等属性
                    glRender.animAlphaAll(value)
                }
                start()
            }
        }

        binding.btnClose.setOnClickListener {
            renderJob?.cancel()
            val viewGroup = binding.surfaceView1.parent as ViewGroup
            viewGroup.removeView(binding.surfaceView1)
            binding.surfaceView1.queueEvent {
                glRender.release()
            }
        }
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
        glRender.release()
        binding.surfaceView1.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

}