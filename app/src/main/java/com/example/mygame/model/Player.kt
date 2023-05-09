package com.example.mygame.model

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaPlayer
import com.example.mygame.R
import com.example.mygame.objects.GameResources

class Player(context: Context, screenX: Int, screenY: Int, imageResource: Int) : Character(
    context, screenX, screenY,
    imageResource
) {
    override var mediaPlayer: MediaPlayer = MediaPlayer.create(context, R.raw.hellomotherfucker)
    override val totalLP: Float = 10f
    override val lifeBar: LifeBar = LifeBar(totalLP, this)
    override val width = screenX / 10f
    override val height = screenY / 10f
    override var positionX = screenX / 2f
    override val positionY = (screenY - (3 * height))
    override var speed = 5
    override var isActive = true

    init {
        updateBitmap(updateBitImage(imageResource))
        mediaPlayer.start()
    }

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

    override fun update() {
        if (speed != 0) {
            if (isValidMove()) {
                positionX += speed
            }
        }
        lifeBar.updateLifeBar(
            lp, positionX, width
        )
    }
}
