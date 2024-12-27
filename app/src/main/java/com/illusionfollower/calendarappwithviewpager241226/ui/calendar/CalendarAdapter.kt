package com.illusionfollower.calendarappwithviewpager241226.ui.calendar

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.illusionfollower.calendarappwithviewpager241226.databinding.ItemCalendarDayBinding

class CalendarAdapter(
    private var itemHeight:Int
): RecyclerView.Adapter<CalendarAdapter.CalendarViewHolder>(){
    inner class CalendarViewHolder(private val binding: ItemCalendarDayBinding
    ):RecyclerView.ViewHolder(binding.root) {
        fun bind(){
            binding.root.layoutParams.height = itemHeight
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarViewHolder {
        return CalendarViewHolder(
            ItemCalendarDayBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount():  Int = 42 // 42일

    override fun onBindViewHolder(holder: CalendarViewHolder, position: Int) {
        holder.bind()
    }

    fun updateItemHeight(newHeight: Int) {
        itemHeight = newHeight
    }
}