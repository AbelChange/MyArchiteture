package com.ablec.myarchitecture.logic.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.ablec.lib.base.BaseActivity
import com.ablec.lib.ext.paddingStatusBar
import com.ablec.lib.ext.setUpBars
import com.ablec.lib.ext.showToast
import com.ablec.module_base.config.Main.BASE
import com.ablec.module_base.util.toJson
import com.ablec.myarchitecture.R
import com.ablec.myarchitecture.databinding.MainActivityBinding
import com.ablec.myarchitecture.logic.pageslist.DataListModel
import com.sankuai.waimai.router.annotation.RouterUri
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@RouterUri(path = [BASE])
class MainActivity : BaseActivity() {

    private lateinit var navController: NavController

    private lateinit var binding: MainActivityBinding

    private val vm by viewModels<DataListModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpBars(true)
        binding.root.paddingStatusBar()
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        val graph = navController.graph.apply {
            //动态设置start
            // graph.startDestination = R.id.xxx
        }
        //初始参数
        navController.setGraph(graph, Bundle())
        //联动toolbar rootFragment不显示返回按钮
        val appBarConfiguration =
            AppBarConfiguration.Builder(navController.graph)
                .build()
        binding.toolbar.setupWithNavController(navController, appBarConfiguration)

        //test
        vm.listLive.observe(this) {
            showToast(it.toJson())
        }
    }

    companion object {
        @JvmStatic
        fun start(context: Context) {
            val starter = Intent(context, MainActivity::class.java)
            context.startActivity(starter)
        }
    }
}