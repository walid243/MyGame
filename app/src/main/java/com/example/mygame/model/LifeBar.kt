package com.example.mygame.model

import android.graphics.Paint
import android.graphics.Rect

class LifeBar(val totalLP: Float, owner: Enemy) {
    var start = owner.positionX
    var end = owner.positionX + owner.width - (totalLP - owner.lp)/100
    val top = owner.positionY + owner.height + 10
    val bottom = top + 10
    var deathBarEnd = (owner.positionX + owner.width).toInt()
    var lifeBarSize = Rect( start.toInt(), top.toInt(), end.toInt(), bottom.toInt() )
    var deathBarSize = Rect( end.toInt(), top.toInt(), deathBarEnd, bottom.toInt() )
    val greenPaint = Paint().apply {
        color = android.graphics.Color.GREEN
        style = Paint.Style.FILL
    }
    val redPaint = Paint().apply {
        color = android.graphics.Color.RED
        style = Paint.Style.FILL
    }
    fun updateLifeBar(owner: Enemy){
        start = owner.positionX
        end = owner.positionX + (owner.width * (owner.lp / totalLP))
        deathBarEnd = (owner.positionX + owner.width).toInt()
        lifeBarSize = Rect( start.toInt(), top.toInt(), end.toInt(), bottom.toInt() )
        deathBarSize = Rect( end.toInt(), top.toInt(), deathBarEnd, bottom.toInt() )
    }


}