package com.relearn.app.feature.offline.data

import kotlinx.coroutines.flow.Flow

class HabitRepository(private val habitDao: HabitDao) {

    val allHabits: Flow<List<HabitEntity>> = habitDao.getAllHabits()

    suspend fun insert(habit: HabitEntity) {
        habitDao.insertHabit(habit)
    }

    suspend fun delete(habit: HabitEntity) {
        habitDao.deleteHabit(habit)
    }

    suspend fun update(habit: HabitEntity) {
        habitDao.updateHabit(habit)
    }

    suspend fun getHabitById(id: Int): HabitEntity? {
        return habitDao.getHabitById(id)
    }
}
