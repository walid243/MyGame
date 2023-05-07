package com.example.mygame

import android.media.AudioAttributes
import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.View
import androidx.annotation.RequiresApi
import com.example.mygame.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onPause() {
        super.onPause()
        binding.videoView.pause()
    }

    override fun onResume() {
        super.onResume()
        binding.videoView.start()
    }


    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.videoView.setVideoURI(Uri.parse("android.resource://" + packageName + "/" + R.raw.space_invaders_song_upgraded))
        binding.videoView.setOnPreparedListener { mp ->
            mp.isLooping = true
            binding.videoView.setAudioFocusRequest(AudioAttributes.CONTENT_TYPE_MUSIC)
            mp.start()
        }

        binding.startButton.setOnClickListener {
            startGame()
            it.visibility = View.GONE
            it.isClickable = false
            binding.videoView.visibility = View.GONE
        }
    }

    private fun startGame() {
        supportFragmentManager.beginTransaction()
            .replace(binding.fragmentContainer.id, GameFragment()).commit()
    }

}