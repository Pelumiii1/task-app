package com.pelumi.todoapp

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Flag
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pelumi.todoapp.ui.theme.TodoAppTheme
import java.time.LocalDate
import java.time.format.DateTimeFormatter


class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TodoAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    TodoScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}


data class Task(
    val text: String,
    val completed: Boolean = false
)


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TodoScreen(modifier: Modifier = Modifier) {
    val today = LocalDate.now()

    val formattedDate = today.format(DateTimeFormatter.ofPattern("EEEE, MMM d,yyyy"))

    var taskText by remember {
        mutableStateOf("")
    }

    var tasks by remember {
        mutableStateOf(listOf<Task>())
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        Text(text = "My Tasks", fontSize = 28.sp, fontWeight = FontWeight.Bold)
        Text(
            text = formattedDate,
            fontSize = 16.sp
        )

        Column(modifier = Modifier.padding(top = 24.dp)) {
            tasks.forEachIndexed { index, task ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                        .border(
                            width = 1.dp,
                            color = Color.Gray,
                            shape = RoundedCornerShape(12.dp)
                        )
                        .padding(10.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = task.text,
                            fontSize = 18.sp,
                            textDecoration = if (task.completed) {
                                TextDecoration.LineThrough
                            } else {
                                TextDecoration.None
                            },
                            modifier = Modifier.weight(1f)
                        )
                        IconButton(
                            onClick = {
                                tasks = tasks.mapIndexed { i, currentTask ->
                                    if (index == i) {
                                        currentTask.copy(
                                            completed = !currentTask.completed
                                        )
                                    } else {
                                        currentTask
                                    }

                                }
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Flag,
                                tint = if (task.completed) Color.Green else Color.Black,
                                contentDescription = "Complete Task"
                            )
                        }
                    }

                }
            }
        }


        Spacer(modifier = Modifier.weight(1f))

        TextField(
            value = taskText,
            onValueChange = { taskText = it },
            modifier = Modifier.fillMaxWidth(),
            placeholder = {
                Text(text = "Add New Task...")
            },
            leadingIcon = {
                IconButton(
                    onClick = {
                        if (taskText.isNotBlank()) {
                            tasks = tasks + Task(text = taskText)
                            taskText = ""
                        }
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add Task"
                    )
                }

            },
            shape = RoundedCornerShape(16.dp),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            )
        )

    }
}