package com.ablec.myarchitecture.logic.main

import android.os.Bundle
import android.view.MenuItem
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import com.ablec.lib.base.BaseActivity
import com.ablec.lib.ext.showToast
import com.ablec.module_base.config.Main.BASE
import com.ablec.module_base.util.convert
import com.ablec.myarchitecture.R
import com.ablec.myarchitecture.data.server.api.TestApiService
import com.ablec.myarchitecture.data.server.dto.GetListReq
import com.ablec.myarchitecture.databinding.MainActivityBinding
import com.sankuai.waimai.router.annotation.RouterUri
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
@RouterUri(path = [BASE])
class MainActivity : BaseActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration

    private lateinit var navController: NavController

    private lateinit var binding: MainActivityBinding

    @Inject
    lateinit var apiService: TestApiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        navController.setGraph(R.navigation.nav_graph)
        appBarConfiguration =
            AppBarConfiguration.Builder(navController.graph)
                .build()

        lifecycleScope.launch {
            val resp = apiService.getListData(GetListReq(1, 10).convert())
            if (resp.isSuccess) {
                showToast(resp.data?.toString())
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}