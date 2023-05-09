package com.example.mygame.model

import android.content.Context
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
    imageResource: Int
) :
    GameObject(context, screenX, screenY, imageResource) {
    override val width = screenX / 10f
    override val height = screenY / 10f
    override var speed = 15
    override var isActive = false

    val damage = 100f
    var canCollision = true

    init {
        updateBitmap(updateBitImage(imageResource))
    }

    override fun update() {
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

}