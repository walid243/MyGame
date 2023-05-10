package com.example.mygame.model

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Point
import android.media.MediaPlayer
import android.media.SoundPool
import android.view.MotionEvent
import android.view.SurfaceView
import com.example.mygame.R
import com.example.mygame.objects.GameResources
import kotlinx.coroutines.*

class GameView(context: Context, private val size: Point) : SurfaceView(context) {
    val backgroundBitmap = Bitmap.createScaledBitmap(
        BitmapFactory.decodeResource(
            context.resources,
            R.drawable.backgroud
        ), size.x, size.y, false
    )
    var mediaPlayer = MediaPlayer.create(context, R.raw.oh_boy)
    var canvas: Canvas = Canvas()
    private val paint: Paint = Paint()
    private val player = Player(context, size.x, size.y, GameResources.playerSkins["init"]!!)
    private val enemy = Enemy(context, size.x, size.y, GameResources.enemySkins["init"]!!)
    var playing = true
    var score = 0
    var isWin = false

    fun pause() {
        mediaPlayer.pause()
    }

    fun resume() {
        mediaPlayer.start()
    }

    fun startGame(): Deferred<Boolean> {
        return CoroutineScope(Dispatchers.Main).async {
            mediaPlayer.setVolume(0.5f, 0.5f)
            mediaPlayer.start()
            mediaPlayer.setOnCompletionListener {
                mediaPlayer.start()
            }
            while (playing) {
                draw()
                update()
                if(isGameOver()){
                    CoroutineScope(Dispatchers.Default).launch {
                        delay(1000)
                        playing = false
                    }
                }
                delay(5)
            }
            if (player.getIsActive()) {
                isWin = true
            }
            true
        }
    }

    private fun isGameOver(): Boolean {

        return !player.getIsActive() || !enemy.getIsActive()
    }


    fun draw() {
        if (holder.surface.isValid) {
            canvas = holder.lockCanvas()
            canvas.drawBitmap(backgroundBitmap, 0f, 0f, paint)
            // Aquí dibuixem els elements de la pantalla de joc
            //SCORE
            drawGameObject(player)
            drawGameObject(enemy)
            canvas.drawText("Score: $score", (size.x - paint.descent()), 75f, paint)
            paint.color = Color.YELLOW
            paint.textSize = 60f
            paint.textAlign = Paint.Align.RIGHT
            holder.unlockCanvasAndPost(canvas)
        }
    }

    private fun drawGameObject(element : GameObject) {
        canvas.drawBitmap(element.bitmap, element.positionX, element.positionY, paint)
        if (element is Character) {
            canvas.drawRect(element.lifeBar.lifeBarSize, element.lifeBar.greenPaint)
            canvas.drawRect(element.lifeBar.deathBarSize, element.lifeBar.redPaint)
            if (element.bullets.isNotEmpty()){
                for (bullet in element.bullets) {
                    if (bullet.isActive) {
                        canvas.drawBitmap(bullet.bitmap, bullet.positionX, bullet.positionY, paint)
                    }
                }
            }
        }

    }

    private fun update() {
        enemy.update()
        player.update()
        updateBullets(player, enemy)
        updateBullets(enemy, player)

    }

    private fun updateBullets(owner: Character, enemy: Character) {
        val bullets = owner.bullets
        if (bullets.isNotEmpty()) {
            var bullet: Bullet
            for (i in bullets.size - 1 downTo  0) {
                bullet = bullets[i]
                if (bullet.isActive) {
                    bullet.update()
                    println("BULLET is in ground?: ${bullet.isInGround()}")
                    if (bullet.positionY <= 0 || bullet.isInGround()){
                            if (owner is Player) {
                                bullet.setDelayCollision(GameResources.bulletSkins["playerImpactBullet"]!!)
                                bullet.playSound(bullet.bulletCollision)
                            } else {
                                println("BOMB in ground")
                                bullet.setDelayCollision(GameResources.bulletSkins["enemyImpactBullet"]!!)
                                bullet.playSound(bullet.bombCollision)
                            }
                        }
                    if (bullet.isCollision(enemy.getCollisionShape())) {
                        if (bullet.canCollision) {
                            if (owner is Player) score++
                            enemy.setLifePoint(bullet.damage)
                            bullet.setCanCollision(false)
                        }
                        if (owner is Player) {
                        bullet.setDelayCollision(GameResources.bulletSkins["playerImpactBullet"]!!)
                            bullet.playSound(bullet.bulletCollision)
                        } else {
                            bullet.setDelayCollision(GameResources.bulletSkins["enemyImpactBullet"]!!)
                            bullet.playSound(bullet.bombCollision)
                        }

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
        if (player.bullets.size < 5 ) player.shot()
        player.playSound(player.getSound(R.raw.sonido_disparo_laser))

    }


}

