package br.com.lfcapp.dio_todolist.datasource

import br.com.lfcapp.dio_todolist.model.Task

object TaskDataSource {
    private var list = arrayListOf<Task>()

    fun getList() = list

    fun insetTask(task: Task){
        list.add(task.copy(id=list.size + 1))
    }
}