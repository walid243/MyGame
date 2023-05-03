package com.example.mygame.model

import android.content.Context
import android.graphics.Bitmap
import android.graphics.RectF

open class Character(context: Context, screenX: Int, screenY: Int): GameObject(context, screenX,
    screenY, ) {
    override var bitmap: Bitmap
        get() = TODO("Not yet implemented")
        set(value) {}
    override val width: Float
        get() = TODO("Not yet implemented")
    override val height: Float
        get() = TODO("Not yet implemented")
    override val start: Float
        get() = TODO("Not yet implemented")
    override val end: Float
        get() = TODO("Not yet implemented")
    override var positionX: Float
        get() = TODO("Not yet implemented")
        set(value) {}
    override val positionY: Float
        get() = TODO("Not yet implemented")
    override var speed: Int
        get() = TODO("Not yet implemented")
        set(value) {}
    override var isActive: Boolean
        get() = TODO("Not yet implemented")
        set(value) {}

    override fun update() {
        TODO("Not yet implemented")
    }

    override fun getCollisionShape(): RectF {
        TODO("Not yet implemented")
    }

    override fun isCollision(gameObject: GameObject): Boolean {
        TODO("Not yet implemented")
    }

    override fun getIsActive(): Boolean {
        TODO("Not yet implemented")
    }

    override fun setIsActive(value: Boolean) {
        TODO("Not yet implemented")
    }
}