package com.geekydroid.playwithcompose.composables.barchart

import kotlin.math.ceil

object BarChartUtils {

    fun findBarRange(barData: List<BarItem>,maxHorizontalLines:Int = 6): BarRange {
        val min = barData.minBy { it.value }.value
        val max = barData.maxBy { it.value }.value
        val rangeInterval = findRangeInterval(max/5)
        var range = 0f
        val intervals = mutableListOf<Float>()
        repeat(maxHorizontalLines) {
            intervals.add(range)
            range+=rangeInterval.toFloat()
        }
        return BarRange(
            minValue = min,
            maxValue = intervals[intervals.size-1]+rangeInterval.toFloat(),
            rangeInterval = rangeInterval.toFloat(),
            intervals = intervals
        )
    }

    private fun findRangeInterval(value: Float): Double {
        val cleanFactor = 100
        return ceil(value / cleanFactor.toDouble()) * cleanFactor
    }

    fun getSizePercentage(barRange: BarRange, barItem: BarItem): Float {
        return (barItem.value/barRange.maxValue)
    }
}