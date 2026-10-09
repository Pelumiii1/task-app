package com.pelumi.todoapp.models

data class Task(
    val text: String,
    val completed: Boolean = false
)