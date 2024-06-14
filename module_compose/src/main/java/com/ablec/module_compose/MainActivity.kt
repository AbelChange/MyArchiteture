package com.ablec.module_compose

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ablec.module_compose.ui.theme.appTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // A surface container using the 'background' color from the theme
            defaultPreview()
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
    fun defaultPreview() {
        appTheme {
            Surface(
                modifier = Modifier.fillMaxSize(),
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
                .padding(16.dp)
                .wrapContentWidth()
                .wrapContentHeight(),
            verticalArrangement = Arrangement.spacedBy(15.dp),//排列方式
            horizontalAlignment = Alignment.CenterHorizontally,//对齐方式
        ) {
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .background(Color.LightGray, CircleShape)
            ) {
                Image(
                    imageVector = ImageVector.vectorResource(R.drawable.baseline_6_ft_apart_24),
//                    contentScale = ContentScale.Fit,
//                    painter = painterResource(id = R.drawable.loading_error),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(CircleShape)
                )
            }
            Text(text = "John Doe", style = MaterialTheme.typography.bodyLarge, fontSize = 25.sp)
            Text(text = "john.doe@example.com", style = MaterialTheme.typography.bodySmall)
            OutlinedButton(onClick = { /* Handle logout */ }) {
                Text(text = "Logout")
            }
        }
    }

}
