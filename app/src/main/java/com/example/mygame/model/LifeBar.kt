package com.example.mygame.model

import android.graphics.Paint
import android.graphics.Rect

class LifeBar(private val totalLP: Float, owner: Character) {
    private var start = owner.positionX
    private var end = owner.positionX + owner.width - (totalLP - owner.lp)/100
    // change to var if owner moves in X axis
    private val top = owner.positionY + owner.height + 10
    private val bottom = top + 10
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
    fun updateLifeBar(newLP: Float, newPositionX:Float, newWidth:Float){
        start = newPositionX
        end = newPositionX + (newWidth * (newLP / totalLP))
        deathBarEnd = (newPositionX + newWidth).toInt()
        lifeBarSize = Rect( start.toInt(), top.toInt(), end.toInt(), bottom.toInt() )
        deathBarSize = Rect( end.toInt(), top.toInt(), deathBarEnd, bottom.toInt() )
    }


}