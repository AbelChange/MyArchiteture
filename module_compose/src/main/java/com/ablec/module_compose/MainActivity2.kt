package com.ablec.module_compose

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
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
                        CenterAlignedTopAppBar(
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
                        NoteApp()
                    }
                }
            }
        }
    }


    @Preview
    @Composable
    fun NoteApp() {
        var darkTheme by remember { mutableStateOf(false) }
        CompositionLocalProvider(LocalIsDark provides darkTheme) {
            Surface(
                color = if (darkTheme) Color.DarkGray else Color.White,
                modifier = Modifier.fillMaxSize()
            ) {
                Column {
                    ThemeSwitch(darkTheme) { darkTheme = it }
                    NoteEditor()
                }
            }
        }
    }

    val LocalIsDark = compositionLocalOf { false }

    @Composable
    fun ThemeSwitch(isDark: Boolean, onToggle: (Boolean) -> Unit) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("Dark Theme")
            Switch(checked = isDark, onCheckedChange = onToggle)
        }
    }

    @OptIn(ExperimentalFoundationApi::class)
    @Preview
    @Composable
    fun NoteEditor() {
        var notes by remember { mutableStateOf(listOf("Compose is cool!")) }
        var currentText by remember { mutableStateOf("") }

        Column {
            TextField(
                value = currentText,
                onValueChange = { currentText = it },
                label = { Text("Write note") }
            )

            Row {
                Button(onClick = {
                    if (currentText.isNotBlank() && !notes.contains(currentText)) {
                        notes = notes + currentText
                        currentText = ""
                    }
                }) {
                    Text("Add")
                }

                Spacer(Modifier.width(8.dp))

                Button(onClick = {
                    if (notes.isNotEmpty()) notes = notes - notes[notes.size -4]
                }) {
                    Text("Delete Last")
                }
            }
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(items = notes, key = { it }) { item ->
                    NoteItem(item, Modifier.animateItemPlacement())
                }
            }
        }
    }

    @Composable
    fun NoteItem(note: String, modifier: Modifier = Modifier) {
        val isDark = LocalIsDark.current
        Text(
            text = note,
            modifier = modifier
                .fillMaxWidth()
                .padding(8.dp)
                .background(if (isDark) Color.Gray else Color.LightGray)
        )
    }




    companion object {
        @JvmStatic
        fun start(context: Context) {
            val starter = Intent(context, MainActivity2::class.java)
            context.startActivity(starter)
        }
    }
}