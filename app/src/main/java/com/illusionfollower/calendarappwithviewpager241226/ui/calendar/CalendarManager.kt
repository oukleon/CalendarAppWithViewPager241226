package com.illusionfollower.calendarappwithviewpager241226.ui.calendar

import android.os.Build
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.viewpager2.widget.ViewPager2
import com.illusionfollower.calendarappwithviewpager241226.databinding.FragmentCalendarBinding
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
class CalendarManager(
    private val binding: FragmentCalendarBinding
) {
    fun setupCalendar(calendarHeight: Int){
        Log.d("startingTimeCheck", "setupCalendar start")
        binding.viewPager.apply {
            Log.d("startingTimeCheck", "before adapter set")
            // adapter 연결 추가
            adapter = ViewPagerAdapter( calendarHeight
            )
            Log.d("startingTimeCheck", "after adapter set")

            orientation = ViewPager2.ORIENTATION_HORIZONTAL

            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    val date = LocalDate.now()
                        .plusMonths((position - Int.MAX_VALUE / 2).toLong())
                        .withDayOfMonth(1)

                    // 년월 업데이트
                    binding.tvYear.text ="${date.year}"
                    binding.tvMonth.text = if (date.monthValue < 10) "  ${date.monthValue}" else "${date.monthValue}"

                }
            })
            Log.d("startingTimeCheck", "before setCurrentItem")

            // 시작 위치를 중앙으로 설정
            setCurrentItem(Int.MAX_VALUE / 2, false)
            Log.d("startingTimeCheck", "after setCurrentItem")
        }
    }
}