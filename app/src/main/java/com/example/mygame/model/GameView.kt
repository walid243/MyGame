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
    private val paint: Paint = Paint()
    val player = Player(context, size.x, size.y)
    val enemy = Enemy(context, size.x, size.y)
    private val bullets = mutableListOf<Bullet>()
    var playing = true
    var score = 0


    init {
        startGame()
    }

    fun startGame() {
        CoroutineScope(Dispatchers.Main).launch {
            while (playing) {
                draw()
                update()
                delay(5)
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
            canvas.drawRect(enemy.lifeBar.size, enemy.lifeBar.greenPaint)
            canvas.drawText("Score: $score", (size.x - paint.descent()), 75f, paint)
            for (bullet in bullets) {
                if (bullet.isActive) {
                    canvas.drawBitmap(bullet.bitmap, bullet.positionX, bullet.positionY, paint)
                }
            }
            paint.color = Color.YELLOW
            paint.textSize = 60f
            paint.textAlign = Paint.Align.RIGHT
            holder.unlockCanvasAndPost(canvas)
        }
    }

    fun update() {
        enemy.updateEnemy()
        player.updatePlayer()
        if (bullets.isNotEmpty()) {
            var bullet: Bullet
            for (i in bullets.size - 1 downTo  0) {
                bullet = bullets[i]
                if (bullet.isActive) {
                    bullet.updateBullet()
                    if (bullet.positionY <= 0) {
                        bullet.setIsActive(false)
                    }
                    if (enemy.isCollision(bullet)) {
                        if (bullet.canCollision) {
                            score++
                            enemy.setLifePoint(bullet.damage)
                            bullet.setCanCollision(false)
                        }
                        bullet.setDelayCollision()
                    }

                } else {
                    bullets.removeAt(i)
                }
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
                        player.speed = 5
                    } else if (event.x < player.positionX){
                        player.speed = -5
                    }
                    else {
                        player.speed = 0
                    }
                }
                MotionEvent.ACTION_UP -> {
                    player.speed = 0
                }

            }
        }
        return true
    }

    fun shot() {
        val bullet = Bullet(context, size.x, size.y, player.positionX, player.positionY)
        bullet.setIsActive()
        bullets.add(0, bullet)
    }


}

