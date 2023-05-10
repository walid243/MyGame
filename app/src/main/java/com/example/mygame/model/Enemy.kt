package com.example.mygame.model

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.RectF
import android.media.MediaPlayer
import android.media.SoundPool
import com.example.mygame.R
import com.example.mygame.objects.GameResources
import kotlin.math.abs

class Enemy(
    context: Context,
    screenX: Int,
    screenY: Int,
    imageResource: Int
) : Character(
    context, screenX, screenY,
    imageResource,
    ) {
    override var bitmap: Bitmap = BitmapFactory.decodeResource(context.resources, imageResource)
    override val width = screenX / 10f
    override val height = screenY / 10f
    override val start: Float = 0f
    override val end: Float = screenX - width
    override var positionX = screenX / 2f
    override val positionY = 80f
    override var speed = 4
    override val totalLP = 1000f
    override var lp = totalLP
    override var isActive = true
    override val lifeBar = LifeBar(totalLP, this)
    override fun shot(): Boolean {
        if (!isActive) return false
        val bullet = Bullet(
            context,
            screenX,
            screenY,
            positionX,
            positionY,
            GameResources.bulletSkins["enemyLaunchBullet"]!!,
            "down",
            5f
        )
        return try {
            bullet.setIsActive(true)
            bullet.playSound(bullet.bombLaunch)
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
        if (isActive) {
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
            if (bullets.size < 1) {
                shot()
            }
        }
    }



}