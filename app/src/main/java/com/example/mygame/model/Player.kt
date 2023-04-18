package com.example.mygame.model

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.example.mygame.R

class Player(context: Context, screenX: Int, screenY: Int) {
    var bitmap: Bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.megaman)
    val width = screenX / 10f
    val height = screenY / 10f
    var positionX = screenX / 2
    var speed = 5

    init{
        bitmap = Bitmap.createScaledBitmap(bitmap, width.toInt(), height.toInt(),false)
    }

    fun updatePlayer(){
        if (positionX >= width){
            speed = -speed
        } else if (positionX <= 0) {
            speed = +speed
        }
        positionX += speed
    }
}
