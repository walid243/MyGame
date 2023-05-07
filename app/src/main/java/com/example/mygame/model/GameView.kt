package com.example.mygame.model

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Point
import android.media.MediaPlayer
import android.view.MotionEvent
import android.view.SurfaceView
import com.example.mygame.R
import kotlinx.coroutines.*

class GameView(context: Context, private val size: Point) : SurfaceView(context) {
    var mediaPlayer = MediaPlayer.create(context, R.raw.tunak_tunak_remix)
    var canvas: Canvas = Canvas()
    private val paint: Paint = Paint()
    private val player = Player(context, size.x, size.y)
    private val enemy = Enemy(context, size.x, size.y)
    private var bullets = player.bullets
    var playing = true
    var score = 0

    fun pause() {
        mediaPlayer.pause()
    }

    fun resume() {
        mediaPlayer.start()
    }

    fun startGame(): Deferred<Boolean> {
        return CoroutineScope(Dispatchers.Main).async {
            mediaPlayer.start()
            mediaPlayer.setOnCompletionListener {
                mediaPlayer.start()
            }
            while (playing) {
                draw()
                update()
                if(isGameOver()){
                    playing = false
                }
                delay(5)
            }
            true
        }
    }

    private fun isGameOver(): Boolean {
        println(enemy.getIsActive())
        println(player.getIsActive())
        println("isGameOver: ${!enemy.getIsActive() || !player.getIsActive()}")
        return !enemy.getIsActive() || !player.getIsActive()
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
            canvas.drawRect(enemy.lifeBar.lifeBarSize, enemy.lifeBar.greenPaint)
            canvas.drawRect(enemy.lifeBar.deathBarSize, enemy.lifeBar.redPaint)
            canvas.drawRect(enemy.getCollisionShape(), paint)
            canvas.drawText("Score: $score", (size.x - paint.descent()), 75f, paint)
            for (bullet in bullets) {
                if (bullet.isActive) {
                    canvas.drawBitmap(bullet.bitmap, bullet.positionX, bullet.positionY, paint)
                    canvas.drawRect(bullet.getCollisionShape(), paint)
                }
            }
            paint.color = Color.YELLOW
            paint.textSize = 60f
            paint.textAlign = Paint.Align.RIGHT
            holder.unlockCanvasAndPost(canvas)
        }
    }

    fun update() {
        enemy.update()
        player.update()
        bullets = player.bullets
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

