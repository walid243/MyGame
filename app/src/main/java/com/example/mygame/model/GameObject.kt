package com.example.mygame.model

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.RectF

abstract class GameObject(
    open val context: Context,
    val screenX: Int,
    val screenY: Int,
    imageResource: Int
) {
    var bitmap: Bitmap = BitmapFactory.decodeResource(context.resources, imageResource)
    abstract val width: Float
    abstract val height: Float
    val start: Float = 0f
    val end: Float = screenX - width
    abstract var positionX: Float
    abstract val positionY: Float
    abstract var speed: Int
    abstract var isActive: Boolean

    abstract fun update()
    fun getCollisionShape(): RectF {
        return RectF(positionX, positionY, positionX + width, positionY + height)

    }

    fun isCollision(enemyCollisionShape: RectF): Boolean {
        return RectF.intersects(getCollisionShape(), enemyCollisionShape)
    }

    fun getIsActive(): Boolean {
        return isActive
    }

    fun setIsActive(value: Boolean = !isActive) {
        isActive = value
    }

    fun isValidMove(): Boolean {
        return (positionX in start..end) || (positionX >= end && speed < 0) || (positionX <= start && speed > 0)

    }
    fun updateBitmap(bitImage: Bitmap) {
        bitmap = Bitmap.createScaledBitmap(bitImage, width.toInt(), height.toInt(), false)
    }

    fun updateBitImage(newImageResource: Int): Bitmap {
        return BitmapFactory.decodeResource(context.resources, newImageResource)
    }

}