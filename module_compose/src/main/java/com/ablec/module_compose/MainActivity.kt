package com.ablec.module_compose

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import com.ablec.lib.ext.immerse
import com.ablec.lib.ext.showToast
import com.ablec.module_compose.ui.theme.AppTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        immerse()
        setContent {
            // A surface container using the 'background' color from the theme
            Content()
        }
    }

    companion object {
        @JvmStatic
        fun start(context: Context) {
            val starter = Intent(context, MainActivity::class.java)
            context.startActivity(starter)
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun Content() {
        AppTheme {
            Surface(
                modifier = Modifier
                    .fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                SimpleLinearLayout()
            }
        }
    }

    @Composable
    fun SimpleLinearLayout() {
        Column(
            modifier = Modifier
                .safeDrawingPadding()
                .fillMaxWidth()
                .fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally,//对齐方式
        ) {
            val count = remember { mutableStateOf(0) }  // 初始化为 0
            Surface(
                modifier = Modifier
                    .padding(vertical = 20.dp)//margin
                    .size(100.dp),
                border = BorderStroke(0.5.dp, Color.Blue),
                shadowElevation = 10.dp,
                shape = RoundedCornerShape(20.dp),//圆角
                onClick = {
                    showToast("点击了头像Surface")
                },
                color = Color.Red
            ) {
                Image(
                    painterResource(R.drawable.baseline_6_ft_apart_24),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(15.dp)//bg之前是margin
                        .background(Color.Green, CircleShape)
                        .padding(15.dp),//bg之后是padding
                )
            }
            Text(text = "John Doe", style = MaterialTheme.typography.bodyLarge, fontSize = 25.sp)
            Text(text = "john.doe@example.com", style = MaterialTheme.typography.bodySmall)
            OutlinedButton(onClick = {
                count.value++
            }) {
                Text(text = "已点击了 ${count.value} 次！")
            }
//            SimpleLoadMoreList()
            PhotographerCard()
        }
    }

    /**
     *  have data flow through a composition
     */
    val UserName =  compositionLocalOf { "default" }

    @Composable
    fun PhotographerCard() {
        CompositionLocalProvider(
            LocalContentColor provides Color.Blue,
            UserName.provides("小蓝")
        ) {
            Column {
                Text(UserName.current)
                Text(UserName.current)
                CompositionLocalProvider(
                    LocalContentColor provides Color.Red,
                    UserName.provides("小红")
                ) {
                    Text(UserName.current)
                }
            }
        }
    }



    @Composable
    fun SimpleLoadMoreList() {
        val isLoading = remember { mutableStateOf(false) } // 加载状态
        val adapterData = remember {
            mutableStateListOf<String>(
                "rawData", "rawData", "rawData",
            )
        } //列表数据
        val loadMoreAction: () -> Unit = {
            isLoading.value = true // 设置加载状态为true
            // 这里可以替换为实际的加载数据逻辑
            lifecycleScope.launch {
                delay(2000) // 模拟2秒的加载时间
                adapterData.add("newData")
                adapterData.add("newData")
                adapterData.add("newData")
                adapterData.add("newData")
                adapterData.add("newData")
                adapterData.add("newData")
                adapterData.add("newData")
                isLoading.value = false // 设置加载状态为false
            }
        }
        // 加载更多按钮
        Button(
            onClick = { loadMoreAction() },
            enabled = !isLoading.value, // 当不处于加载状态时可点击按钮
            modifier = Modifier.width(140.dp)
        ) {
            if (isLoading.value) {
                CircularProgressIndicator(modifier = Modifier.size(20.dp)) // 显示加载指示器
            } else {
                Text("Load More")
            }
        }
        //列表
        LazyColumn(modifier = Modifier.fillMaxHeight()) {
            itemsIndexed(items = adapterData,
                itemContent = { index, item ->
                    Text(text = item + index,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier
                            .padding(vertical = 20.dp)
                            .drawWithContent {
                                drawContent()
                                if (index < adapterData.lastIndex) {
                                    // 绘制分割线
                                    val strokeWidth = 1.dp.toPx()
                                    val y = size.height - strokeWidth / 2
                                    drawLine(
                                        color = Color.Black,
                                        start = Offset(0f, y),
                                        end = Offset(size.width, y),
                                        strokeWidth = strokeWidth
                                    )
                                }
                            }
                    )
                }
            )
        }
    }

}
