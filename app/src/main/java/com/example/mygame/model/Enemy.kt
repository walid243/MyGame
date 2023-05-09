package com.example.mygame.model

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.RectF
import android.media.MediaPlayer
import com.example.mygame.R
import com.example.mygame.objects.GameResources
import kotlin.math.abs

class Enemy(context: Context, screenX: Int, screenY: Int, imageResource: Int) : Character(
    context, screenX, screenY,
    imageResource
) {
    override val width = screenX / 10f
    override val height = screenY / 10f
    override var positionX = screenX / 2f
    override val positionY = 80f
    override var speed = 4
    override val mediaPlayer: MediaPlayer = MediaPlayer.create(context, R.raw.oh_boy)
    override val totalLP = 1000f
    override var isActive = true
    override val lifeBar = LifeBar(totalLP, this)
    override fun shot(): Boolean {
        val bullet = Bullet(context, screenX, screenY, positionX, positionY, GameResources.bulletSkins["playerLaunchBullet"]!!)
        return try {
            bullet.setIsActive(true)
            bullets.add(bullet)
            true
        } catch (e: Exception) {
            println(e.message)
            false
        }
    }

    init {
        updateBitmap(updateBitImage(imageResource))
    }

    override fun update() {
        if (positionX >= end) {
            speed = -speed
        } else if (positionX <= start) {
            speed = abs(speed)
        }
        positionX += speed
        lifeBar.updateLifeBar(lp, positionX, width)
        if (lp <= 0f) {
            setIsActive(false)
        }
    }


}