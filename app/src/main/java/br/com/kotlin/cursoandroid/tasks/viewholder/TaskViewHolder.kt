package br.com.kotlin.cursoandroid.tasks.viewholder

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.kotlin.cursoandroid.tasks.R
import br.com.kotlin.cursoandroid.tasks.entities.OnTaskListFragmentInteractionListener
import br.com.kotlin.cursoandroid.tasks.entities.TaskEntity
import br.com.kotlin.cursoandroid.tasks.repository.PriorityCacheConstants

class TaskViewHolder (itemView: View, val context : Context, val listener: OnTaskListFragmentInteractionListener) : RecyclerView.ViewHolder(itemView) {

    private val mTextDescription : TextView = itemView.findViewById(R.id.textDescription)
    private val mTextPriority : TextView = itemView.findViewById(R.id.textPriority)
    private val mTextDuedate : TextView = itemView.findViewById(R.id.textDueDate)
    private val mImageTask : ImageView = itemView.findViewById(R.id.imageTask)

    fun bindData(task : TaskEntity) {
        mTextDescription.text = task.description
        mTextPriority.text = PriorityCacheConstants.getPriorityDescription(task.priorityId)
        mTextDuedate.text = task.dueDate

        if (task.complete) {
            mImageTask.setImageResource(R.drawable.ic_done)
        }

        //Evento de clique para edição
        mTextDescription.setOnClickListener({
            listener.onListClick(task.id)

        })

        mTextDescription.setOnLongClickListener({
            showConfirmationDialog(task)

            true
        })

        mImageTask.setOnClickListener({
            if (task.complete) {
                listener.onUncompleteClick(task.id)
            } else {
                listener.onCompleteClick(task.id)
            }
        })

    }

    private fun showConfirmationDialog(task : TaskEntity) {

        AlertDialog.Builder(context)
                .setTitle("Excluir")
                .setMessage("Deseja remover a tarefa '${task.description}'?")
                .setIcon(R.drawable.ic_delete)
                .setPositiveButton("Ok", { dialogInterface, i -> listener.onDeleteClick(task.id) })
                .setNegativeButton("Cancelar", null).show()
    }


}