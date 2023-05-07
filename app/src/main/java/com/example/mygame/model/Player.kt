package com.example.mygame.model

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaPlayer
import com.example.mygame.R

class Player(context: Context,   screenX: Int, screenY: Int): GameObject(context,screenX, screenY) {
    override var bitmap: Bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.megaman)
    var mediaPlayer = MediaPlayer.create(context, R.raw.hellomotherfucker)
    val bullets = mutableListOf<Bullet>()
    override val width = screenX / 10f
    override val height = screenY / 10f
    override val start = 0f
    override val end = screenX - width
    override var positionX = screenX / 2f
    override val positionY = (screenY - (3 * height))
    override var speed = 5
    override var isActive = true

    init {
        bitmap = Bitmap.createScaledBitmap(bitmap, width.toInt(), height.toInt(), false)
        mediaPlayer.start()
    }

    fun shot(): Boolean {
        val bullet =  Bullet(context, screenX, screenY, positionX, positionY)
        return try {
            bullet.setIsActive(true)
            bullets.add(bullet)
            true
        } catch (e: Exception) {
            println(e.message)
            false
        }

    }

    override fun update() {
        if (speed != 0) {
            if (isValidMove()){
                positionX += speed
            }
        }
    }

    fun isValidMove(): Boolean {
        return (positionX in start..end) || (positionX >= end && speed < 0 ) || (positionX <= start && speed > 0)
    }

    override fun getIsActive(): Boolean {
        return true
    }

    override fun setIsActive(value: Boolean) {
        isActive = value
    }

    override fun getCollisionShape(): android.graphics.RectF {
        return android.graphics.RectF(positionX, positionY, positionX + width, positionY + height)
    }

    override fun isCollision(gameObject: GameObject): Boolean {
        return android.graphics.RectF.intersects(getCollisionShape(), gameObject.getCollisionShape())
    }
}
