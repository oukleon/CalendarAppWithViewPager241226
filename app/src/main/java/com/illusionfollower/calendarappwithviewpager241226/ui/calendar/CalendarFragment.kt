package com.illusionfollower.calendarappwithviewpager241226.ui.calendar

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.illusionfollower.calendarappwithviewpager241226.databinding.FragmentCalendarBinding

@RequiresApi(Build.VERSION_CODES.O)
class CalendarFragment : Fragment() {

    private var _binding: FragmentCalendarBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CalendarViewModel by activityViewModels()

    private lateinit var calendarManager: CalendarManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d("startingTimeCheck", "onCreateView start")
        _binding = FragmentCalendarBinding.inflate(inflater, container, false)
        val root: View = binding.root
        Log.d("startingTimeCheck", "after binding")

        calendarManager = CalendarManager(binding)
        Log.d("startingTimeCheck", "after CalendarManager init")

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d("startingTimeCheck", "onViewCreated start")
        super.onViewCreated(view, savedInstanceState)
        viewModel.dateHeight.observe(viewLifecycleOwner) { dateHeight ->
            Log.d("startingTimeCheck", "dateHeight changed: $dateHeight")
            calendarManager.setupCalendar(dateHeight)
            Log.d("startingTimeCheck", "setupCalendar end: $dateHeight")
        }
        Log.d("startingTimeCheck", "onViewCreated end")
    }
}