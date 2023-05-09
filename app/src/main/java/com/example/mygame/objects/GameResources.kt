package com.example.mygame.objects

import com.example.mygame.R

object GameResources {
    val playerSkins = mutableMapOf(Pair("init", R.drawable.megaman))
    val enemySkins = mutableMapOf(Pair("init", R.drawable.enemy))
    val bulletSkins = mutableMapOf(Pair("playerLaunchBullet", R.drawable.bullet_launch), Pair("playerImpactBullet", R.drawable.bullet_impact))
}