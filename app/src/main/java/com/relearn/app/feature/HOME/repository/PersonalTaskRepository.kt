package com.relearn.app.feature.HOME.domain.repository

import android.util.Log
import com.relearn.app.feature.HOME.model.PersonalTask
// import com.relearn.app.feature.HOME.data.local.PersonalTaskDao
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class PersonalTaskRepository @Inject constructor(
    private val firestore: FirebaseFirestore,
    // private val localDao: PersonalTaskDao
) {

    private val tasksRef = firestore.collection("personal_tasks")

    suspend fun addTask(task: PersonalTask) {
        try {
            tasksRef.document(task.id).set(task).await()
        } catch (e: Exception) {
            // localDao.insertTask(task)
        }
    }

    suspend fun deleteTask(taskId: String) {
        try {
            tasksRef.document(taskId).delete().await()
        } catch (e: Exception) {
            // localDao.deleteTaskById(taskId)
        }
    }

    suspend fun updateTask(task: PersonalTask) {
        try {
            tasksRef.document(task.id).set(task).await()
            Log.d("PersonalTaskRepo", "Task updated id=${task.id} isDone=${task.isDone}")
        } catch (e: Exception) {
            Log.e("PersonalTaskRepo", "Error updating task", e)
        }
    }


    suspend fun getTasks(userId: String): List<PersonalTask> {
        return try {
            val snapshot = tasksRef.whereEqualTo("userId", userId).get().await()
            val tasks = snapshot.documents.mapNotNull { it.toObject(PersonalTask::class.java) }
            Log.d("PersonalTaskRepo", "getTasks fetched ${tasks.size} tasks for userId=$userId")
            tasks
        } catch (e: Exception) {
            Log.e("PersonalTaskRepo", "getTasks failed: ${e.message}")
            emptyList()
        }
    }
}
