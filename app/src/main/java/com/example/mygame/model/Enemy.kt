package com.example.mygame.model

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.RectF
import com.example.mygame.R
import kotlin.math.abs

class Enemy(context: Context, screenX: Int, screenY: Int) {
    var bitmap: Bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.enemy)
    val width = screenX / 10f
    val height = screenY / 10f
    val start = 0f
    val end = screenX - width
    var positionX = screenX / 2f
    val positionY = 0f
    var speed = 4

    init{
        bitmap = Bitmap.createScaledBitmap(bitmap, width.toInt(), height.toInt(),false)
    }
    fun updateEnemy(){
        if (positionX >= end){
            speed = -speed
        } else if (positionX <= start) {
            speed = abs(speed)
        }
        positionX += speed
    }
    fun getCollisionShape(): RectF {
        return RectF(positionX, positionY, positionX + width, positionY + height)
    }
    fun isCollision(bullet: Bullet): Boolean {
        return RectF.intersects(getCollisionShape(), bullet.getCollisionShape())
    }
}