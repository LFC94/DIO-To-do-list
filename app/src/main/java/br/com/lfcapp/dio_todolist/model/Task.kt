package br.com.lfcapp.dio_todolist.model

data class Task(
    val title: String,
    val date: String,
    val time: String,
    val id: Int = 0
)
