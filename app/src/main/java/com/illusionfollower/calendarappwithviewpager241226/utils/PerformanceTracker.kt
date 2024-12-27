package com.illusionfollower.calendarappwithviewpager241226.utils

import android.util.Log

object PerformanceTracker {
    private var startTime: Long = 0
    private val measurements = mutableMapOf<String, Long>()
    private var lastPointTime: Long = 0

    fun startTracking() {
        startTime = System.nanoTime()
        lastPointTime = startTime
        measurements.clear()
        Log.d("Performance", "Started tracking")
    }

    fun markPoint(pointName: String) {
        val currentTime = System.nanoTime()
        val timeSinceLastPoint = (currentTime - lastPointTime) / 1_000_000 // to milliseconds
        val timeSinceStart = (currentTime - startTime) / 1_000_000

        measurements[pointName] = timeSinceLastPoint

        Log.d("Performance", "$pointName took: ${timeSinceLastPoint}ms (Total: ${timeSinceStart}ms)")
        lastPointTime = currentTime
    }

    fun printSummary() {
        val totalTime = (System.nanoTime() - startTime) / 1_000_000
        Log.d("Performance", "=== Performance Summary ===")
        measurements.forEach { (point, time) ->
            Log.d("Performance", "$point: ${time}ms")
        }
        Log.d("Performance", "Total time: ${totalTime}ms")
        Log.d("Performance", "========================")
    }
}