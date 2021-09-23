package br.com.lfcapp.dio_todolist.ui


import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.lfcapp.dio_todolist.R
import br.com.lfcapp.dio_todolist.databinding.ItemTaskBinding
import br.com.lfcapp.dio_todolist.model.Task

class TaskListAdapter : ListAdapter<Task, TaskListAdapter.TaskViewHolder>(DiffCallBack()) {

    var listenerEdit: (Task) -> Unit = {}
    var listenerDelet: (Task) -> Unit = {}

    inner class TaskViewHolder(private val binding: ItemTaskBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Task) {
            binding.tvTitle.text = item.title
            binding.tvData.text = "${item.date} ${item.time}"
            binding.btnAction.setOnClickListener {
                showPopup(item)
            }
        }

        private fun showPopup(item: Task) {
            var popupMenu = PopupMenu(binding.btnAction.context, binding.btnAction)
            popupMenu.menuInflater.inflate(R.menu.popup_menu, popupMenu.menu)
            popupMenu.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.action_edit -> listenerEdit(item)
                    R.id.action_delet -> listenerDelet(item)
                }
                return@setOnMenuItemClickListener true
            }
            popupMenu.show()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        var binding = ItemTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TaskViewHolder(binding);
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}

class DiffCallBack : DiffUtil.ItemCallback<Task>() {
    override fun areItemsTheSame(oldItem: Task, newItem: Task) = oldItem === newItem
    override fun areContentsTheSame(oldItem: Task, newItem: Task) = oldItem.id === newItem.id
}