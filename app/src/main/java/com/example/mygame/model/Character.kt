package com.example.mygame.model

import android.content.Context

abstract class Character(context: Context, screenX: Int, screenY: Int, imageResource: Int) :
    GameObject(
        context, screenX,
        screenY,
        imageResource,
    ) {
    abstract val totalLP: Float
    abstract val lifeBar: LifeBar
    abstract var lp : Float
    val bullets: MutableList<Bullet> = mutableListOf()
    fun setLifePoint(value: Float) {
        if (lp > 0f) {
            lp -= value
        } else {
            lp = 0f
        }
    }

    abstract fun shot():Boolean

}