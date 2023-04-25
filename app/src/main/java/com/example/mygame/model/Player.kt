package com.example.mygame.model

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.example.mygame.R
import kotlin.math.abs

class Player(context: Context, screenX: Int, screenY: Int) {
    var bitmap: Bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.megaman)
    val width = screenX / 10f
    val height = screenY / 10f
    val start = 0f
    val end = screenX - width
    var positionX = screenX / 2f
    val positionY = (screenY - (3 * height))
    var speed = 5

    init {
        bitmap = Bitmap.createScaledBitmap(bitmap, width.toInt(), height.toInt(), false)
    }

    fun updatePlayer() {
        if (speed != 0) {
            if (positionX < end) {
                positionX += speed
            } else if (positionX > start) {
                positionX -= speed
            }
            else {
                speed = 0
            }
        }
    }
}
