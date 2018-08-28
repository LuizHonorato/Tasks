package br.com.kotlin.cursoandroid.tasks.entities

data class TaskEntity (val id: Int,
                       val userId: Int,
                       val priorityId: Int,
                       val description: String,
                       var dueDate: String,
                       var complete: Boolean)