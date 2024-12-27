package com.illusionfollower.calendarappwithviewpager241226.ui.calendar

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.illusionfollower.calendarappwithviewpager241226.databinding.ItemCalendarDayBinding
import java.time.LocalDate

class CalendarAdapter(
    private var itemHeight:Int,
    private val dayList: List<LocalDate>
): RecyclerView.Adapter<CalendarAdapter.CalendarViewHolder>(){

    inner class CalendarViewHolder(private val binding: ItemCalendarDayBinding
    ):RecyclerView.ViewHolder(binding.root) {
        val layoutDay = binding.clDay
        val tvDayText = binding.tvDayText
        val tvLunarDay = binding.tvLunarDay
        val tvGanjiMonth = binding.tvGanjiMonth
        val tvGanjiDay = binding.tvGanjiDay
        val tvDot = binding.tvDot

        private var currentDate: LocalDate? = null
        private var currentAlpha: Float = 1.0f

        fun bind(position: Int){
            val date = dayList[position]
            if (currentDate?.dayOfMonth != date.dayOfMonth){
                binding.tvDayText.text = date.dayOfMonth.toString()
                currentDate = date
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarViewHolder {
        val binding = ItemCalendarDayBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
        )
        binding.root.layoutParams.height = itemHeight
        return CalendarViewHolder(binding)
    }

    override fun getItemCount():  Int = 42 // 42일

    override fun onBindViewHolder(holder: CalendarViewHolder, position: Int) {
        holder.bind(position)
    }

    fun updateItemHeight(newHeight: Int) {
        itemHeight = newHeight
    }
}