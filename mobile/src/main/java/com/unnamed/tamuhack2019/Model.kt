package com.unnamed.tamuhack2019

import java.util.*
import kotlin.collections.ArrayList

object Model {
    data class User(val id: Int = 0, val name: String = "", val awaiting: Boolean = false, val tasks: ArrayList<Task> = ArrayList())
    data class Task(val id: Int, val name: String, val category: String, val completed: Boolean, val created_at: Date, val updated_at: Date)
    data class Progress(val progress_percentage: Int)
}

data class User(
    val user: UserX = UserX()
)

data class UserX(
    val awaiting: Boolean = false,
    val id: Int = 0,
    val name: String = "",
    val tasks: List<Task> = ArrayList()
)

data class Tasks(
    val category: String,
    val completed: Boolean,
    val created_at: String,
    val id: Int,
    val name: String,
    val updated_at: String
)

data class Progress(
    val progress_percent: Int
)

data class Task(
    val category: String,
    val completed: Boolean,
    val id: Int,
    val name: String,
    val users: List<User>
)
