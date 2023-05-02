package com.example.mygame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.mygame.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.startButton.setOnClickListener {
            startGame()
            it.visibility = View.GONE
            it.isClickable = false

        }
    }
    private fun startGame(){
        supportFragmentManager.beginTransaction().replace(binding.fragmentContainer.id, GameFragment()).commit()
    }

}