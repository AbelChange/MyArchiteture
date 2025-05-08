package com.ablec.module_compose

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ablec.module_compose.ui.theme.AppTheme

class MainActivity2 : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            AppTheme {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = { Text("标题") },
                            navigationIcon = {
                                IconButton(onClick = { finish() }) {
                                    Icon(
                                        Icons.AutoMirrored.Filled.ArrowBack,
                                        contentDescription = "Back"
                                    )
                                }
                            }
                        )
                    }
                ) { padding ->
                    Surface(
                        modifier = Modifier
                            .padding(padding)
                            .fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        Content()
                    }
                }
            }
        }
    }


    @SuppressLint("UnrememberedMutableState")
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun Content() {

        var count by remember { mutableIntStateOf(0) }
        Log.d("Recompose", "A recomposed before")

        Log.d("Recompose", "C recomposed before")
        Text(text = "点击加一：${count}", modifier = Modifier.clickable {
            count++
        })
        Log.d("Recompose", "D recomposed before")
        Log.d("Recompose", "B recomposed before")
    }

    /**
     * 变化的nums驱动recomposition
     */
    @Composable
    fun A(nums: Int) {
        Log.d("Recompose", "A recomposed $nums")
    }

    @Composable
    fun C() {
        Log.d("Recompose", "C recomposed")
    }

    @Composable
    fun D() {
        Log.d("Recompose", "D recomposed")
    }

    @Composable
    fun B() {
        Log.d("Recompose", "B recomposed")
    }

    companion object{
        @JvmStatic
        fun start(context: Context) {
            val starter = Intent(context, MainActivity2::class.java)
            context.startActivity(starter)
        }
    }
}