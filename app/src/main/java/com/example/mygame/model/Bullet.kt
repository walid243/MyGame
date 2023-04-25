package com.example.mygame.model

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.RectF
import com.example.mygame.R

class Bullet(private val context: Context, screenX: Int, screenY: Int, val positionX: Float, var positionY: Float) {
    val bullets = mapOf(Pair("normal",R.drawable.bullet_launch),
        Pair("impact",R.drawable.bullet_impact)
    )
    var bitmap: Bitmap
    val width = screenX / 10f
    val height = screenY / 10f
    var speed = 15
    var isActive = false

    init{
        bitmap = setBitmap(setBullet(bullets["normal"]!!))
    }
    fun updateBullet(): Boolean {
        positionY -= speed
        return positionY >= height
    }

    fun getCollisionShape(): RectF {
        return RectF(positionX, positionY, positionX + width, positionY + height)
    }

    fun setBullet(image:Int): Bitmap {
        return BitmapFactory.decodeResource(context.resources, image)
    }
    fun setBitmap(bitImage:Bitmap): Bitmap {
        return Bitmap.createScaledBitmap(bitImage, width.toInt(), height.toInt(),false)

    }

    fun setIsActive(value: Boolean = !isActive){
        isActive = value
    }

}