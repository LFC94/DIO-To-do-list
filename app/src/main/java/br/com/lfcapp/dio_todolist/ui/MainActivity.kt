package br.com.lfcapp.dio_todolist.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.com.lfcapp.dio_todolist.databinding.ActivityMainBinding
import br.com.lfcapp.dio_todolist.datasource.TaskDataSource

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val adapter by lazy { TaskListAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.rvTasks.adapter = adapter
        insertListeners()
    }

    private fun insertListeners() {
        binding.fabAddTask.setOnClickListener {
            startActivityForResult(Intent(this, AddTaskActivity::class.java), CREATE_NEW_TASK)
        }

        adapter.listenerEdit = {
            var intent = Intent(this, AddTaskActivity::class.java)
            intent.putExtra(AddTaskActivity.TASK_ID, it.id)
            startActivityForResult(intent, CREATE_NEW_TASK)
        }
        adapter.listenerDelet = {
            TaskDataSource.deleteTask(it)
            updateList()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CREATE_NEW_TASK && resultCode == Activity.RESULT_OK) {
            binding.rvTasks.adapter = adapter
            updateList()
        }
    }

    private fun updateList(){
        adapter.submitList(TaskDataSource.getList())
    }

    companion object {
        private const val CREATE_NEW_TASK = 1000;
    }

}