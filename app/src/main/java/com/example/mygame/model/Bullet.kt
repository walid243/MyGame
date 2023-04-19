package com.example.mygame.model

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.RectF
import com.example.mygame.R

class Bullet(context: Context, screenX: Int, screenY: Int, val positionX: Float, var positionY: Float) {
    var bitmap: Bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.bullet)
    val width = screenX / 10f
    val height = screenY / 10f
    var speed = 15

    init{
        bitmap = Bitmap.createScaledBitmap(bitmap, width.toInt(), height.toInt(),false)
    }
    fun updateBullet(): Boolean {
        positionY -= speed
        return positionY >= height
    }

    fun getCollisionShape(): RectF {
        return RectF(positionX, positionY, positionX + width, positionY + height)
    }

}