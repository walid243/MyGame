package com.example.mygame.model

import android.content.Context
import android.graphics.Bitmap

abstract class GameObject( val context: Context, val screenX: Int, val screenY: Int) {
    abstract var bitmap: Bitmap
    abstract val width: Float
    abstract val height: Float
    abstract val start: Float
    abstract val end: Float
    abstract var positionX: Float
    abstract val positionY: Float
    abstract var speed: Int
    abstract var isActive: Boolean

    abstract fun update()
    abstract fun getCollisionShape(): android.graphics.RectF
    abstract fun isCollision(gameObject: GameObject): Boolean
    abstract fun getIsActive(): Boolean
    abstract fun setIsActive(value: Boolean= !isActive)

}