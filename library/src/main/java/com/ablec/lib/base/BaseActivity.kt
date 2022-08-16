package com.ablec.lib.base

import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.ablec.common.R

abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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