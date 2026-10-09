package com.pelumi.todoapp.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.pelumi.todoapp.models.Task

class TodoViewModel : ViewModel() {
    var tasks by mutableStateOf(listOf<Task>())
        private set

    fun addTask(text: String) {
        if (text.isBlank()) return
        tasks = tasks + Task(text = text.trim())
    }

    fun toggleTask(index: Int) {
        tasks = tasks.mapIndexed { i, task ->
            if (i == index) {
                task.copy(completed = !task.completed)
            } else {
                task
            }
        }
    }
}