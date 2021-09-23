package br.com.lfcapp.dio_todolist.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.com.lfcapp.dio_todolist.databinding.ActivityAddTaskBinding
import br.com.lfcapp.dio_todolist.extensions.format
import br.com.lfcapp.dio_todolist.extensions.text
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
                binding.tilTime.text = "${timePicker.hour} ${timePicker.minute}"
            }

            timePicker.show(supportFragmentManager, "TIME_PICKER_TAG")

        }

        binding.btnNewTask.setOnClickListener{

        }

        binding.btnCancel.setOnClickListener{
            finish()
        }

    }
}