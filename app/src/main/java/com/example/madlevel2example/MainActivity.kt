package com.example.madlevel2example

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.madlevel2example.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val reminders = arrayListOf<Reminder>()
    private val reminderAdapter = ReminderAdapter(reminders)
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()

        binding.btnAddReminder.setOnClickListener {
            val reminder = binding.etReminder.text.toString()
            addReminder(reminder)
        }
    }

    private fun initViews() {
        binding.recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        binding.recyclerView.adapter = reminderAdapter

        binding.recyclerView.addItemDecoration(
            DividerItemDecoration(
                this,
                DividerItemDecoration.VERTICAL
            )
        )

        createItemTouchHelper().attachToRecyclerView(recyclerView)
    }

    private fun addReminder(reminder: String) {
        if (reminder.isNotBlank()) {
            reminders.add(Reminder(reminder))
            reminderAdapter.notifyDataSetChanged()
            binding.etReminder.text?.clear()
        } else {
            Snackbar.make(etReminder, "You must fill in the input field!", Snackbar.LENGTH_LONG)
                .show()
        }
    }

    private fun createItemTouchHelper(): ItemTouchHelper {
        val callback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                reminders.removeAt(position)
                reminderAdapter.notifyDataSetChanged()
            }
        }

        return ItemTouchHelper(callback)
    }
}