package com.example.mygame.model

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.example.mygame.R

class Bullet(context: Context, screenX: Int, screenY: Int, initalPosition:Int) {
    var bitmap: Bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.bullet)
    val width = screenX / 10f
    val height = screenY / 10f
    var positionY = initalPosition
    var speed = 15

    init{
        bitmap = Bitmap.createScaledBitmap(bitmap, width.toInt(), height.toInt(),false)
    }
    fun updateBullet(): Boolean {
        positionY += speed
        return positionY >= height
    }

}