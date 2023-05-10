package com.example.mygame.model

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.SoundPool
import com.example.mygame.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class Bullet(
    override val context: Context,
    screenX: Int,
    screenY: Int,
    override var positionX: Float,
    override var positionY: Float,
    imageResource: Int,
    private val direction : String,
    var damage : Float = 1f
) :
    GameObject(context, screenX, screenY, imageResource) {
    override var bitmap: Bitmap = BitmapFactory.decodeResource(context.resources, imageResource)
    override val width = screenX / 10f
    override val height = screenY / 10f
    override val start: Float = 0f
    override val end: Float = screenX - width
    private val ground = screenY - (3 * height)
    override var speed = 15
    override var isActive = false
    val bulletLaunch = getSound(R.raw.sonido_disparo_laser)
    val bulletCollision = getSound(R.raw.sonido_explosion_laser)
    val bombLaunch = getSound(R.raw.sonido_lanzamiento_bomba)
    val bombCollision = getSound(R.raw.sonido_explosion)

    var canCollision = true

    init {
        updateBitmap(updateBitImage(imageResource))
    }

    override fun update() {
        if (direction == "up") {
            moveUp()
        } else {
            moveDown()
        }
    }

    private fun moveDown() {
        if (!isInGround()) positionY += speed
    }

    private fun moveUp() {
        positionY -= speed
    }

    fun setDelayCollision(newImageResource:Int) {
        CoroutineScope(Dispatchers.Default).launch {
            updateBitmap(updateBitImage(newImageResource))
            delay(400)
            setIsActive(false)
        }
    }

    @JvmName("setCanCollision1")
    fun setCanCollision(value: Boolean = !canCollision) {
        canCollision = value
    }

    fun isInGround(): Boolean {
        return positionY >= ground
    }

}