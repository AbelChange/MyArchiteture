package com.ablec.myarchitecture.gridtopager

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityOptionsCompat
import com.ablec.lib.base.BaseActivity
import com.ablec.myarchitecture.R
import com.ablec.myarchitecture.gridtopager.fragment.ImagePagerFragment

/**
 * @author shuaihui_hao
 * @date 2024/5/30
 * @description
 */
class ImageActivity :BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.simple_fragment)
        if (savedInstanceState != null) {
            return
        }
        val fragmentManager = supportFragmentManager
        fragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, ImagePagerFragment(), ImagePagerFragment::class.java.simpleName)
            .commit()
    }

    companion object{
        @JvmStatic
        fun start(context: Activity, transitionView: ImageView) {
            val starter = Intent(context, ImageActivity::class.java)
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                context,
                transitionView, transitionView.transitionName
            )
            ActivityCompat.startActivity(context, starter, options.toBundle())
        }
    }

}