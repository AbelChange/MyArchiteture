package com.ablec.lib.base

import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import com.gyf.immersionbar.ImmersionBar
import com.ablec.common.R

abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initImmersionBar()
    }


    fun setToolbar(
        toolbar: Toolbar?,
        title: String? = null,
        isShowBackIcon: Boolean = true
    ) {
        if (null != toolbar) {
            setSupportActionBar(toolbar)
            supportActionBar?.setHomeButtonEnabled(true)
            supportActionBar?.setDisplayHomeAsUpEnabled(isShowBackIcon)
            val titleView = toolbar.findViewById<TextView>(R.id.tvTitle)
            titleView.text = title
            supportActionBar?.title = null
        }
    }

    fun initImmersionBar() {
        ImmersionBar.with(this)
            .statusBarDarkFont(true, 0.2f)
            .transparentStatusBar()
            .init()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}