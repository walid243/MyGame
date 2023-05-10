package com.example.mygame.model

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.SoundPool
import com.example.mygame.R
import com.example.mygame.objects.GameResources

class Player(
    context: Context,
    screenX: Int,
    screenY: Int,
    imageResource: Int,
) : Character(
    context, screenX, screenY,
    imageResource
) {
    override var bitmap: Bitmap = BitmapFactory.decodeResource(context.resources, imageResource)
    override val totalLP: Float = 10f
    override var lp: Float = totalLP
    override val width = screenX / 10f
    override val height = screenY / 10f
    override val start: Float = 0f
    override val end: Float = screenX - width
    override var positionX = screenX / 2f
    override val positionY = (screenY - (3 * height))
    override var speed = 5
    override var isActive = true
    private val playerLoad = getSound(R.raw.hellomotherfucker)
    override val lifeBar: LifeBar = LifeBar(totalLP, this)

    init {
        updateBitmap(updateBitImage(imageResource))
        playSound(playerLoad)
    }


    override fun shot(): Boolean {
        if (!isActive) return false
        return try {
            val bullet = Bullet(
                context,
                screenX,
                screenY,
                positionX,
                positionY,
                GameResources.bulletSkins["playerLaunchBullet"]!!,
                "up",
                150f
            )

            bullet.setIsActive(true)
            bullet.playSound(bullet.bulletLaunch)
            bullets.add(bullet)
            true
        } catch (e: Exception) {
            println(e.message)
            false
        }
    }


    override fun update() {
        if (isActive) {
            if (speed != 0) {
                if (isValidMove()) {
                    positionX += speed
                }
            }
            lifeBar.updateLifeBar(
                lp, positionX, width
            )
            if (lp <= 0) {
                setIsActive(false)
            }
        }
    }

}
