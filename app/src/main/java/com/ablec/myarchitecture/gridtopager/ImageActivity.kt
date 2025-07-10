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
        setContentView(R.layout.activity_main)

        // Set the toolbar as the activity's app bar.
        setSupportActionBar(findViewById(R.id.toolbar))

        //showNavigationIcon
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        //防止添加新的 Fragment
        if (savedInstanceState == null) {
            val fragmentManager = supportFragmentManager
            fragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, ImagePagerFragment(), ImagePagerFragment::class.java.simpleName)
                //添加到回退栈会影响返回行为
//                .addToBackStack(null)
                .commit()
        }
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