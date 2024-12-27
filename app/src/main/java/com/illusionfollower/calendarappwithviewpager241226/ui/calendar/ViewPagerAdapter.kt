package com.illusionfollower.calendarappwithviewpager241226.ui.calendar

import android.os.Build
import android.util.LruCache
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.illusionfollower.calendarappwithviewpager241226.databinding.ItemCalendarContainerBinding
import com.illusionfollower.calendarappwithviewpager241226.utils.PerformanceTracker
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
class ViewPagerAdapter( private var itemHeight: Int
):RecyclerView.Adapter<ViewPagerAdapter.PagerViewHolder>() {
    private val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    private val dateCache = mutableMapOf<Int, LocalDate>()
    // 날짜 리스트 캐시 (position을 key로 사용)
    private val dayListCache = LruCache<Int, List<LocalDate>>(12) // 최근 12개월만

    private val center = Int.MAX_VALUE / 2

    // 현재 보고 있는 달의 정보를 저장
    private var currentViewingMonth: LocalDate = LocalDate.now()

    inner class PagerViewHolder(binding: ItemCalendarContainerBinding) : RecyclerView.ViewHolder(binding.root) {
        private val rvThisMonth = binding.rvThisMonth.apply {
            layoutManager = GridLayoutManager(context, 7)
        }

        fun bind(date: Int) {
            val position = layoutPosition
            val currentDate = getDateForPosition(position)
            val dayList = getDayListForPosition(position)

            val calendarAdapter = CalendarAdapter( itemHeight
            )

            rvThisMonth.apply {
                adapter = calendarAdapter
            }
        }
    }

    private fun getDateForPosition(position: Int): LocalDate {
        return dateCache.getOrPut(position) {
            LocalDate.now()
                .plusMonths((position - center).toLong())
                .withDayOfMonth(1)
        }
    }

    private fun getDayListForPosition(position: Int): List<LocalDate> {
        return dayListCache.get(position) ?: createDayList(getDateForPosition(position)).also {
            dayListCache.put(position, it)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerViewHolder {
        return PagerViewHolder(
            ItemCalendarContainerBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ).apply {
                rvThisMonth.setHasFixedSize(true)
            }
        )
    }

    override fun getItemCount(): Int = Int.MAX_VALUE

    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        PerformanceTracker.startTracking()

        currentViewingMonth = getDateForPosition(position)
        PerformanceTracker.markPoint("Date calculation")

        holder.bind(position)
        PerformanceTracker.markPoint("Binding")

        PerformanceTracker.printSummary()
    }

    private fun createDayList(baseDate: LocalDate): MutableList<LocalDate> {
        PerformanceTracker.startTracking()

        // 캐시나 재사용 가능한 리스트 사용을 고려
        val dayList = ArrayList<LocalDate>(42) // 초기 용량 지정으로 재할당 방지
        PerformanceTracker.markPoint("ArrayList creation")

        val firstDayOfMonth = baseDate.withDayOfMonth(1)
        val startDay = firstDayOfMonth.minusDays((firstDayOfMonth.dayOfWeek.value % 7).toLong())

        // 단일 루프로 처리
        var currentDate = startDay
        repeat(42) {
            dayList.add(currentDate)
            currentDate = currentDate.plusDays(1)
        }
        PerformanceTracker.markPoint("Day list population")
        PerformanceTracker.printSummary()
        return dayList
    }

    fun getCurrentViewingMonth(): LocalDate = currentViewingMonth

    // 캐시 정리 메서드 (필요시 호출)
    fun clearCache() {
        dateCache.clear()
        dayListCache.evictAll()
    }
}