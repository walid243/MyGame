package com.example.mygame.model

import android.content.Context
import android.graphics.Bitmap
import android.graphics.RectF
import android.media.MediaPlayer
import com.example.mygame.objects.GameResources

abstract class Character(context: Context, screenX: Int, screenY: Int, imageResource: Int) :
    GameObject(
        context, screenX,
        screenY,
        imageResource
    ) {
    abstract val mediaPlayer: MediaPlayer
    abstract val totalLP: Float
    var lp: Float = totalLP
    abstract val lifeBar: LifeBar
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