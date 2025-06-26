package com.relearn.app.feature.offline.data

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.relearn.app.R
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class OfflineHabitsFragment : Fragment(R.layout.fragment_offline_habits) {

    private val viewModel: HabitViewModel by viewModels()
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: HabitAdapter
    private lateinit var habitEditText: EditText
    private lateinit var addButton: Button

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inițializări UI
        recyclerView = view.findViewById(R.id.habitsRecyclerView)
        habitEditText = view.findViewById(R.id.habitEditText)
        addButton = view.findViewById(R.id.addButton)

        adapter = HabitAdapter(
            onDeleteClick = { habit ->
                viewModel.deleteHabit(habit)
                Toast.makeText(requireContext(), "Șters: ${habit.name}", Toast.LENGTH_SHORT).show()
            },
            onEditClick = { habit ->
                val updatedHabit = habit.copy(name = "${habit.name} (editat)")
                viewModel.updateHabit(updatedHabit)
                Toast.makeText(requireContext(), "Actualizat: ${updatedHabit.name}", Toast.LENGTH_SHORT).show()
            }
        )

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        // Observare ViewModel
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.allHabits.collectLatest { habits ->
                adapter.submitList(habits)
            }
        }

        // Adăugare obicei nou
        addButton.setOnClickListener {
            val habitName = habitEditText.text.toString().trim()
            if (habitName.isNotEmpty()) {
                val habit = HabitEntity(name = habitName, category = "General")
                viewModel.addHabit(habit)
                habitEditText.text.clear()
                Toast.makeText(requireContext(), "Adăugat: $habitName", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Introduceți un obicei", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
