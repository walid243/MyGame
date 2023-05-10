package com.example.mygame.model

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.RectF
import android.media.SoundPool

abstract class GameObject(
    open val context: Context,
    val screenX: Int,
    val screenY: Int,
    imageResource: Int
) {
    abstract var bitmap: Bitmap
    abstract val width: Float
    abstract val height: Float
    abstract val start: Float
    abstract val end: Float
    abstract var positionX: Float
    abstract val positionY: Float
    abstract var speed: Int
    abstract var isActive: Boolean
    val soundPool: SoundPool = SoundPool.Builder().setMaxStreams(5).build()

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

    fun getSound(sound: Int): Int {
        return soundPool.load(context, sound, 1)
    }

    fun playSound(sound: Int) {
        soundPool.setOnLoadCompleteListener { _, _, _ ->
            soundPool.play(sound, 1f, 1f, 1, 0, 1f)
            soundPool.pause(sound)
        }
    }

}