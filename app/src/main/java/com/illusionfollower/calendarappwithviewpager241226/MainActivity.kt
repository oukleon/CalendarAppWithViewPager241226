package com.illusionfollower.calendarappwithviewpager241226

import android.os.Build
import android.os.Bundle
import android.view.WindowInsets
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.illusionfollower.calendarappwithviewpager241226.databinding.ActivityMainBinding
import com.illusionfollower.calendarappwithviewpager241226.ui.calendar.CalendarFragment
import com.illusionfollower.calendarappwithviewpager241226.ui.calendar.CalendarViewModel
import com.illusionfollower.calendarappwithviewpager241226.ui.settings.SettingsFragment
import com.illusionfollower.calendarappwithviewpager241226.ui.today.TodayFragment

@RequiresApi(Build.VERSION_CODES.R) //had to fix
class MainActivity : AppCompatActivity() {
    companion object {
        private val BAR_HEIGHT_RES_ID = R.dimen.bar_height
    }
    private lateinit var binding: ActivityMainBinding
    private val calendarFragment = CalendarFragment()
    private val todayFragment = TodayFragment()
    private val settingsFragment = SettingsFragment()

    private val viewModel: CalendarViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.setScreenHeight(measureUsableScreenSize())
        setupNavigation()
    }

    private fun setupNavigation() {
        // 초기 세팅
        supportFragmentManager.beginTransaction()
            .add(R.id.container, calendarFragment)
            .add(R.id.container, todayFragment).hide(todayFragment)
            .add(R.id.container, settingsFragment).hide(settingsFragment)
            .commit()

        // 네비게이션 클릭 시
        binding.navView.setOnItemSelectedListener { item ->
            val transaction = supportFragmentManager.beginTransaction()
            when (item.itemId) {
                R.id.navigation_calendar -> {
                    transaction
                        .hide(todayFragment)
                        .hide(settingsFragment)
                        .show(calendarFragment)
                }
                R.id.navigation_today -> {
                    transaction
                        .hide(calendarFragment)
                        .hide(settingsFragment)
                        .show(todayFragment)
                }
                R.id.navigation_settings -> {
                    transaction
                        .hide(calendarFragment)
                        .hide(todayFragment)
                        .show(settingsFragment)
                }
            }
            transaction.commit()
            true
        }
    }

    //utils
    private fun measureUsableScreenSize(): Int {
        val windowMetrics = windowManager.currentWindowMetrics
        val insets = windowMetrics.windowInsets.getInsetsIgnoringVisibility(
            WindowInsets.Type.systemBars() or WindowInsets.Type.displayCutout()
        )
        val usableHeight = windowMetrics.bounds.height() - insets.top - insets.bottom
        val toolbarHeight = resources.getDimensionPixelSize(BAR_HEIGHT_RES_ID)
        val dayTextViewHeight = resources.getDimensionPixelSize(R.dimen.days_line_part_height)
        return usableHeight - toolbarHeight - toolbarHeight - dayTextViewHeight
    }
}