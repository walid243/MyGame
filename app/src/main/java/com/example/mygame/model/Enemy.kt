package com.example.mygame.model

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.RectF
import com.example.mygame.R
import kotlin.math.abs

class Enemy(context: Context, screenX: Int, screenY: Int): GameObject(context, screenX, screenY) {
    override var bitmap: Bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.enemy)
    override val width = screenX / 10f
    override val height = screenY / 10f
    override val start = 0f
    override val end = screenX - width
    override var positionX = screenX / 2f
    override val positionY = 80f
    override var speed = 4
    private val totalLP = 1000f
    override var isActive = true
    var lp = totalLP
    val lifeBar = LifeBar(totalLP, this)

    init{
        bitmap = Bitmap.createScaledBitmap(bitmap, width.toInt(), height.toInt(),false)
    }
    override fun update(){
        if (positionX >= end){
            speed = -speed
        } else if (positionX <= start) {
            speed = abs(speed)
        }
        positionX += speed
        lifeBar.updateLifeBar(this)
        if (lp <= 0f){
            setIsActive(false)
        }
    }
    override fun getCollisionShape(): RectF {
        return RectF(positionX, positionY, positionX + width, positionY + height)
    }
    fun isCollision(bullet: Bullet): Boolean {
        return RectF.intersects(getCollisionShape(), bullet.getCollisionShape())
    }
    fun setLifePoint(value: Float){
        if (lp > 0f){
            lp -= value
        } else {
            lp = 0f
        }
    }

    override fun isCollision(gameObject: GameObject): Boolean {
        TODO()
    }

    override fun getIsActive(): Boolean {
        return isActive
    }

    override fun setIsActive(value: Boolean) {
        isActive = value
    }
}