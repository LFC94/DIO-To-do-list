package br.com.lfcapp.dio_todolist.datasource

import br.com.lfcapp.dio_todolist.model.Task

object TaskDataSource {
    private var list = arrayListOf<Task>()

    fun getList() = list.toList()

    fun insetTask(task: Task){
        if (task.id == 0) {
            list.add(task.copy(id = list.size + 1))
        } else {
            list.remove(task)
            list.add(task)
        }
    }

    fun findById(taskId: Int)= list.find { it.id == taskId}

    fun deleteTask (task: Task){
        list.remove(task)
    }
}