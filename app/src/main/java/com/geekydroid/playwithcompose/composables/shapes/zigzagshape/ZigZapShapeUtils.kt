package com.geekydroid.playwithcompose.composables.shapes.zigzagshape

import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Path

object ZigZapShapeUtils {

    fun drawZigZagCurve(
        path: Path,
        crossSize: Float,
        noOfCrosses: Int,
        size: Size,
        crossPosition: CrossPosition = CrossPosition.TOP
    ) {
        path.apply {

            when(crossPosition) {
                CrossPosition.TOP -> {
                    for (i in 0 ..< noOfCrosses) {
                        if (i % 2 == 0) {
                            arcTo(
                                rect = Rect(
                                    top = 0f,
                                    left = (i*crossSize),
                                    right = (i*crossSize)+crossSize,
                                    bottom = crossSize
                                ),
                                startAngleDegrees = 180f,
                                sweepAngleDegrees = 180f,
                                forceMoveTo = false
                            )
                        } else {
                            arcTo(
                                rect = Rect(
                                    top = 0f,
                                    left = (i*crossSize),
                                    right = (i*crossSize)+crossSize,
                                    bottom = crossSize
                                ),
                                startAngleDegrees = 180f,
                                sweepAngleDegrees = -180f,
                                forceMoveTo = false
                            )
                        }
                    }
                }
                CrossPosition.BOTTOM -> {
                    for (i in 0 ..< noOfCrosses) {
                        if (i % 2 == 0) {
                            arcTo(
                                rect = Rect(
                                    top = size.height - crossSize,
                                    left = size.width - (i*crossSize) - crossSize,
                                    right = size.width - (i*crossSize),
                                    bottom = size.height
                                ),
                                startAngleDegrees = 0f,
                                sweepAngleDegrees = 180f,
                                forceMoveTo = false
                            )
                        } else {
                            arcTo(
                                rect = Rect(
                                    top = size.height - crossSize,
                                    left = size.width - (i*crossSize) - crossSize,
                                    right = size.width - (i*crossSize),
                                    bottom = size.height
                                ),
                                startAngleDegrees = 0f,
                                sweepAngleDegrees = -180f,
                                forceMoveTo = false
                            )
                        }
                    }
                }
            }
        }
    }

    fun drawZigZagDown(
        path: Path,
        crossSize: Float,
        noOfCrosses: Int,
        size: Size,
        crossPosition: CrossPosition = CrossPosition.TOP
    ) {

        path.apply {
            when (crossPosition) {
                CrossPosition.TOP -> {
                    for (i in 0..<noOfCrosses) {
                        if (i % 2 == 0) {
                            lineTo((i * crossSize) + crossSize, crossSize)
                        } else {
                            lineTo((i * crossSize) + crossSize, 0f)
                        }
                    }
                }

                CrossPosition.BOTTOM -> {
                    for (i in 0..noOfCrosses+1) {
                        if (i % 2 == 0) {
                            lineTo(size.width - (i * crossSize) + crossSize, size.height)
                        } else {
                            lineTo(size.width - (i * crossSize) + crossSize, size.height - crossSize)
                        }
                    }
                }
            }
        }
    }

    fun drawZigZagUp(
        path: Path,
        size: Size,
        crossSize: Float,
        noOfCrosses: Int,
        crossPosition: CrossPosition = CrossPosition.BOTTOM
    ) {
        path.apply {
            when(crossPosition) {
                CrossPosition.TOP -> {
                    for (i in 0.. noOfCrosses+1) {
                        if (i % 2 == 0) {
                            lineTo((i * crossSize) - crossSize, 0f)
                        } else {
                            lineTo((i * crossSize) - crossSize, crossSize)
                        }
                    }
                }
                CrossPosition.BOTTOM -> {
                    for (i in 0.. noOfCrosses) {
                        if (i % 2 == 0) {
                            lineTo(size.width - (i * crossSize) - crossSize, size.height - crossSize)
                        } else {
                            lineTo(size.width - (i * crossSize) - crossSize, size.height)
                        }
                    }
                }
            }
        }
    }

}