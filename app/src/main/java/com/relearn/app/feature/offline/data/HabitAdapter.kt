package com.relearn.app.feature.offline.data

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.relearn.app.R
import com.relearn.app.feature.offline.data.HabitEntity

class HabitAdapter(
    private val onEditClick: (HabitEntity) -> Unit,
    private val onDeleteClick: (HabitEntity) -> Unit
) : RecyclerView.Adapter<HabitAdapter.HabitViewHolder>() {

    private var habitList: List<HabitEntity> = emptyList()

    fun submitList(list: List<HabitEntity>) {
        habitList = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HabitViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.habit_item, parent, false)
        return HabitViewHolder(view)
    }

    override fun onBindViewHolder(holder: HabitViewHolder, position: Int) {
        val habit = habitList[position]
        holder.bind(habit)
    }

    override fun getItemCount(): Int = habitList.size

    inner class HabitViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val habitName: TextView = itemView.findViewById(R.id.habit_name)
        private val editButton: ImageButton = itemView.findViewById(R.id.edit_button)
        private val deleteButton: ImageButton = itemView.findViewById(R.id.delete_button)

        fun bind(habit: HabitEntity) {
            habitName.text = habit.name

            editButton.setOnClickListener {
                onEditClick(habit)
            }

            deleteButton.setOnClickListener {
                onDeleteClick(habit)
            }
        }
    }
}
