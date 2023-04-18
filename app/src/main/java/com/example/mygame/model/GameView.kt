package com.example.mygame.model

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Point
import android.view.MotionEvent
import android.view.SurfaceView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class GameView(context: Context, private val size: Point) : SurfaceView(context){
    var canvas: Canvas = Canvas()
    val paint: Paint = Paint()
    val player = Player(context,size.x, size.y)
    val enemy = Enemy(context, size.x, size.y)
    var playing = true
    var score = 0
    var shotAction = false
    init {
        startGame()
    }
    fun startGame(){
        CoroutineScope(Dispatchers.Main).launch{
            while(playing){
                draw()
                update()
                delay(10)
            }
        }
    }
    fun draw(){
        if (holder.surface.isValid) {
            canvas = holder.lockCanvas()
            // Aquí dibuixem els elements de la pantalla de joc
            holder.unlockCanvasAndPost(canvas)
            //SCORE
            paint.color = Color.YELLOW
            paint.textSize = 60f
            paint.textAlign = Paint.Align.RIGHT
            canvas.drawText("Score: $score", (size.x - paint.descent()), 75f, paint)
//ENEMY
            canvas.drawBitmap(enemy.bitmap, enemy.positionX.toFloat(),0f, paint)
        }
    }
    fun update(){
        enemy.updateEnemy()
        player.updatePlayer()
        if(shotAction){

        }
    }
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event != null) {
            when(event.action){
                // Aquí capturem els events i el codi que volem executar per cadascún
                MotionEvent.ACTION_DOWN, MotionEvent.ACTION_MOVE -> {
                    // Modifiquem la velocitat del jugador perquè es mogui?
                    if(event.x>player.positionX){
                        // ...
                    }
                }

            }
        }
        return true
    }



}

