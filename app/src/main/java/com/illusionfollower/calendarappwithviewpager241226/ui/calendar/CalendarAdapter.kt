package com.illusionfollower.calendarappwithviewpager241226.ui.calendar

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.illusionfollower.calendarappwithviewpager241226.R
import com.illusionfollower.calendarappwithviewpager241226.databinding.ItemCalendarDayBinding
import java.time.LocalDate
import java.time.ZoneId

class CalendarAdapter(
    private var itemHeight:Int,
    private val dayList: List<LocalDate>
): RecyclerView.Adapter<CalendarAdapter.CalendarViewHolder>(){
    // adapter 레벨에서 한 번만 계산
    private val today = LocalDate.now(ZoneId.systemDefault())
    // adapter 레벨의 변수로 선언
    private var todayDrawable: Drawable? = null
    private var todayDrawableAlpha: Drawable? = null
    private val transparentColor = Color.TRANSPARENT

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

            val isToday = date == today
            if (binding.clDay.tag != isToday){
                binding.clDay.tag = isToday
                if (isToday){
                    binding.clDay.foreground = if (date.monthValue == today.dayOfMonth){
                        todayDrawable
                    }else{
                        todayDrawableAlpha
                    }
                }
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
        // ViewHolder 생성 시 Drawable 초기화
        if (todayDrawable == null) {
            todayDrawable = ContextCompat.getDrawable(parent.context, R.drawable.today_border)
            todayDrawableAlpha = ContextCompat.getDrawable(parent.context, R.drawable.today_border)?.mutate()?.apply {
                alpha = (0.5 * 255).toInt()
            }
        }
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