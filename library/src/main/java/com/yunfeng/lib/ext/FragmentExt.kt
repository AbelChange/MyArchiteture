package com.yunfeng.lib.ext;

/**
 * Extension functions for Fragment.
 */

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.fragment.NavHostFragment


fun Fragment.findNavController(): NavController =
    NavHostFragment.findNavController(this)

fun Fragment.navigate(destination: NavDirections) = with(findNavController()) {
    currentDestination?.getAction(destination.actionId)
        ?.let { navigate(destination) }
}

fun Fragment.showToolBar(showBackIcon: Boolean, toolbar: Toolbar) {
    setHasOptionsMenu(true);
    val host = activity as AppCompatActivity
    host.setSupportActionBar(toolbar)
    host.supportActionBar?.let {
        it.title = ""
        it.setHomeButtonEnabled(true)
        it.setDisplayHomeAsUpEnabled(showBackIcon)
    }
}

