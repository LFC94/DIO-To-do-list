package br.com.lfcapp.dio_todolist.ui

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import br.com.lfcapp.dio_todolist.R
import br.com.lfcapp.dio_todolist.databinding.ActivityAddTaskBinding
import br.com.lfcapp.dio_todolist.datasource.TaskDataSource
import br.com.lfcapp.dio_todolist.extensions.format
import br.com.lfcapp.dio_todolist.extensions.text
import br.com.lfcapp.dio_todolist.model.Task
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.util.*

class AddTaskActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddTaskBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (intent.hasExtra(TASK_ID)){
            val taskId = intent.getIntExtra(TASK_ID, 0)
            TaskDataSource.findById(taskId)?.let {
                binding.tilTitle.text = it.title
                binding.tilTime.text = it.time
                binding.tilDate.text = it.date

                binding.btnNewTask.setText(R.string.atualizar_tarefa)
            }
        }

        insertListeners()
    }

    private fun insertListeners() {
        binding.tilDate.editText?.setOnClickListener {
            val datePicker = MaterialDatePicker
                .Builder
                .datePicker()
                .build()

            datePicker.addOnPositiveButtonClickListener {
                val timeZone = TimeZone.getDefault()
                val offSet = timeZone.getOffset(Date().time) * -1
                binding.tilDate.text = Date(it + offSet).format()
            }

            datePicker.show(supportFragmentManager, "DATE_PICKER_TAG")

        }

        binding.tilTime.editText?.setOnClickListener {
            val timePicker = MaterialTimePicker
                .Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .build()

            timePicker.addOnPositiveButtonClickListener {
                var hour = (if (timePicker.hour in 0..9) "0" else "") + timePicker.hour;
                var minute = (if (timePicker.minute in 0..9) "0" else "") + timePicker.minute;
                binding.tilTime.text = "${hour}:${minute}"
            }

            timePicker.show(supportFragmentManager, "TIME_PICKER_TAG")

        }

        binding.btnNewTask.setOnClickListener {
            val task = Task(
                title = binding.tilTitle.text,
                date = binding.tilDate.text,
                time = binding.tilTime.text,
                id = intent.getIntExtra(TASK_ID, 0)
            )
            TaskDataSource.insetTask(task)
            setResult(Activity.RESULT_OK)
            finish()
        }

        binding.btnCancel.setOnClickListener{
            finish()
        }

        binding.toolBar.setNavigationOnClickListener {
            finish()
        }

    }

    companion object{
        const val TASK_ID = "task_id"
    }
}