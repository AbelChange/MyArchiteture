package com.ablec.myarchitecture.logic.main

import android.opengl.GLSurfaceView
import android.os.Bundle
import android.util.Log
import android.view.GestureDetector
import android.view.GestureDetector.SimpleOnGestureListener
import android.view.MotionEvent
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.ablec.lib.ext.viewBinding
import com.ablec.module_base.service.RouterServiceManager
import com.ablec.myarchitecture.R
import com.ablec.myarchitecture.databinding.FragmentMineBinding
import com.ablec.myarchitecture.logic.pag.GLRender
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch


class MineFragment : Fragment(R.layout.fragment_mine) {

    private val binding: FragmentMineBinding by viewBinding()

    //只消费点击事件的识别器
    private val gestureDetector: GestureDetector by lazy {
        GestureDetector(requireContext(), object : SimpleOnGestureListener() {
//            override fun onDoubleTap(e: MotionEvent): Boolean {
//                Log.e("MineFragment", "双击")
//                return true
//            }

            override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
                Log.e("MineFragment", "单击")
                return true
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        binding.btnGoLogin.setOnTouchListener { v, event ->
//            return@setOnTouchListener gestureDetector.onTouchEvent(event)
//        }
        binding.btnGoLogin.setOnClickListener {
            RouterServiceManager.getAccountService()?.startLogin(requireContext())
        }
        setupSharedPagSystem()
    }

    private fun setupSharedPagSystem() {
        val pagPaths = ArrayDeque<String>().apply {
            add("autumn_bg3.pag")
            add("autumn_rabbit3.pag")
            add("autumn_rabbit_click3.pag")
            add("autumn_tree_click3.pag")
        }
        val glRender = GLRender(context,pagPaths.removeFirst())

        binding.surfaceView1.apply {
            setEGLContextClientVersion(2);
            setRenderer(glRender)
            renderMode = GLSurfaceView.RENDERMODE_WHEN_DIRTY;
            //该模式下可以持续播放 onDrawFrame会持续调用
            //renderMode = GLSurfaceView.RENDERMODE_CONTINUOUSLY;
        }

        val FPS = 12L
        lifecycleScope.launch {
            while (isActive){
                delay(1000/FPS)//12帧
                binding.surfaceView1.requestRender()
            }
        }

        binding.surfaceView1.setOnClickListener {
            binding.surfaceView1.queueEvent {
                glRender.addLayer(pagPaths.removeFirstOrNull())
                binding.surfaceView1.setOnClickListener {
                    binding.surfaceView1.queueEvent {
                        glRender.replaceLayer(1,pagPaths.removeFirstOrNull())
                        binding.surfaceView1.setOnClickListener {
                            binding.surfaceView1.queueEvent {
                                glRender.addLayer(pagPaths.removeFirstOrNull())
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        binding.surfaceView1.onResume()
//        binding.surfaceView2.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.surfaceView1.onPause()
//        binding.surfaceView2.onPause()
    }

}