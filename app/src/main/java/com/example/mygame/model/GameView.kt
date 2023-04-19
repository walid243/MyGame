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

class GameView(context: Context, private val size: Point) : SurfaceView(context) {
    var canvas: Canvas = Canvas()
    val paint: Paint = Paint()
    val player = Player(context, size.x, size.y)
    val enemy = Enemy(context, size.x, size.y)
    lateinit var bullet: Bullet
    var playing = true
    var score = 0
    var shotAction = false

    init {
        startGame()
    }

    fun startGame() {
        CoroutineScope(Dispatchers.Main).launch {
            while (playing) {
                draw()
                update()
                delay(10)
            }
        }
    }

    fun draw() {
        if (holder.surface.isValid) {
            canvas = holder.lockCanvas()
            canvas.drawColor(Color.GRAY)
            // Aquí dibuixem els elements de la pantalla de joc
            //SCORE
            canvas.drawBitmap(
                player.bitmap, player.positionX, player.positionY, paint
            )
//ENEMY
            canvas.drawBitmap(enemy.bitmap, enemy.positionX, enemy.positionY, paint)
            canvas.drawText("Score: $score", (size.x - paint.descent()), 75f, paint)
            if (shotAction) {
                canvas.drawBitmap(bullet.bitmap, bullet.positionX, bullet.positionY, paint)
            }
            paint.color = Color.YELLOW
            paint.textSize = 60f
            paint.textAlign = Paint.Align.RIGHT
            println()
            holder.unlockCanvasAndPost(canvas)
        }
    }

    fun update() {
        enemy.updateEnemy()
        player.updatePlayer()
        if (shotAction) {
            bullet.updateBullet()
            if (bullet.positionY <= 0) {
                shotAction = false
            }
            if (enemy.isCollision(bullet)) {
                score++
                shotAction = false
            }
        }

    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event != null) {
            when (event.action) {
                // Aquí capturem els events i el codi que volem executar per cadascún
                MotionEvent.ACTION_DOWN, MotionEvent.ACTION_MOVE -> {
                    // Modifiquem la velocitat del jugador perquè es mogui?
                    if (event.x > player.positionX) {
                        // ...
                    }
                }

            }
        }
        return true
    }
    fun shot(){
        bullet = Bullet(context, size.x, size.y, player.positionX, player.positionY)
        shotAction = true
    }


}

