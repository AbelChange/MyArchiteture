package com.yunfeng.module_web

import android.content.Context
import android.content.Intent
import android.content.res.AssetManager
import android.os.Build
import android.os.Bundle
import android.webkit.WebView
import com.blankj.utilcode.util.FragmentUtils
import com.yunfeng.lib.base.BaseActivity
import com.yunfeng.module_web.config.Const.KEY_URL
import com.yunfeng.module_web.databinding.ModuleWebActivityBinding

/**
 * 功能:
 * Created By   LiChunWei on 2019/3/12
 */
class WebActivity : BaseActivity() {

    private lateinit var binding: ModuleWebActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //截长图需要
            WebView.enableSlowWholeDocumentDraw()
        }
        super.onCreate(savedInstanceState)
        binding = ModuleWebActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        intent.extras?.let {
            val url = it.getString(KEY_URL)
            if (savedInstanceState == null) {
                FragmentUtils.replace(supportFragmentManager, X5WebFragment.newInstance(url!!), binding.container.id)
            }
        }
        initImmersionBar()
    }

    override fun getAssets(): AssetManager {
        return if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP_MR1) {
            resources.assets
        } else {
            super.getAssets()
        }
    }

    companion object {

        fun start(context: Context, url: String) {
            val starter = Intent(context, WebActivity::class.java)
            starter.putExtra(KEY_URL, url)
            context.startActivity(starter)
        }
    }
}