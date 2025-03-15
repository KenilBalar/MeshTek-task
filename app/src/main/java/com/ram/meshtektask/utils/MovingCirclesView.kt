package com.ram.meshtektask.utils

/**
 * @author Kenil
 * @date 15-04-2024
 */
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.ram.meshtektask.models.Coordinates

class MovingCirclesView(context: Context, attrs: AttributeSet? = null) : View(context, attrs) {

    private var circleSize = 50f
    private val circleColors = arrayOf(Color.BLUE, Color.GREEN, Color.RED)
    private val circlePaints = circleColors.map { color ->
        Paint().apply {
            this.color = color
            this.isAntiAlias = true
        }
    }
    private val strokePaint = Paint().apply {
        color = Color.GRAY
        style = Paint.Style.STROKE
        strokeWidth = 2f
        isAntiAlias = true
    }

    private var numCircles = 28
    private var arrayOfCoordinates = ArrayList<Coordinates>()
    private var k = 0

    init {
        post(object : Runnable {
            override fun run() {
                invalidate()
                postDelayed(this, 200)
            }
        })
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        var segmentX = width / 4

        circleSize = (width * 0.0666f) / 2
        var lastCrossY1 = 0f
        var lastCrossX1 = 0f
        var lastX1 = 0f
        var lastY1 = 0f

        for (i in 1..numCircles) {
            var x = 0f
            var y = 0f

            if (i < 12) {
                x = (segmentX / 2f)
                y = height - (circleSize * 4 * i).toFloat()
                arrayOfCoordinates.add(Coordinates(x, y))
                if (i == 11) {
                    lastCrossY1 = y
                    lastY1 = y
                }
            } else if (i == 12) {
                x = segmentX - circleSize * 2
                y = lastCrossY1 - circleSize * 3.5f
                arrayOfCoordinates.add(Coordinates(x, y))
                lastCrossX1 = segmentX - circleSize * 3
                lastCrossY1 = y
            } else if (i in 13..16) {
                y = lastCrossY1 - circleSize * 3f
                x = lastX1 + lastCrossX1 + (circleSize * 4f)
                arrayOfCoordinates.add(Coordinates(x, y))
                lastCrossX1 = 0f
                lastX1 = x
            } else if (i == 17) {
                x = (segmentX * 3) + circleSize
                y = lastCrossY1
                arrayOfCoordinates.add(Coordinates(x, y))
            } else {
                x = (segmentX * 3) + circleSize * 3
                y = lastY1 + (circleSize * 4 * (i - 18))
                arrayOfCoordinates.add(Coordinates(x, y))
            }
            canvas.drawCircle(x, y, circleSize, strokePaint)
        }

        when(k){
            0->{
                canvas.drawCircle(arrayOfCoordinates[k].x, arrayOfCoordinates[k].y, circleSize, circlePaints[0])
            }
            1->{
                canvas.drawCircle(arrayOfCoordinates[k].x, arrayOfCoordinates[k].y, circleSize, circlePaints[0])
                canvas.drawCircle(arrayOfCoordinates[k - 1].x, arrayOfCoordinates[k - 1].y, circleSize, circlePaints[1])
            }
            2->{
                canvas.drawCircle(arrayOfCoordinates[k].x, arrayOfCoordinates[k].y, circleSize, circlePaints[0])
                canvas.drawCircle(arrayOfCoordinates[k - 1].x, arrayOfCoordinates[k - 1].y, circleSize, circlePaints[1])
                canvas.drawCircle(arrayOfCoordinates[k - 2].x, arrayOfCoordinates[k - 2].y, circleSize, circlePaints[2])
            }
            else->{
                canvas.drawCircle(arrayOfCoordinates[k].x, arrayOfCoordinates[k].y, circleSize, circlePaints[0])
                canvas.drawCircle(arrayOfCoordinates[k - 1].x, arrayOfCoordinates[k - 1].y, circleSize, circlePaints[1])
                canvas.drawCircle(arrayOfCoordinates[k - 2].x, arrayOfCoordinates[k - 2].y, circleSize, circlePaints[2])
                for (i in arrayOfCoordinates.indices) {
                    if (i >= k) {
                        canvas.drawCircle(arrayOfCoordinates[i].x, arrayOfCoordinates[i].y, circleSize, strokePaint)
                    }
                }
            }
        }
        k++
    }
}