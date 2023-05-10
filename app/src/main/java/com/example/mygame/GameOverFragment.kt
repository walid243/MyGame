package com.example.mygame

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import com.example.mygame.databinding.FragmentGameOverBinding

class GameOverFragment(private val score: Int, private val isWin: Boolean) : Fragment() {
    private lateinit var binding: FragmentGameOverBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGameOverBinding.inflate(layoutInflater)
        // Inflate the layout for this fragment
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (isWin) {
            binding.root.background =
                ResourcesCompat.getDrawable(resources, R.drawable.game_win, null)
            binding.gameOverTextView.text = "You Win!"
        } else {
            binding.root.background =
                ResourcesCompat.getDrawable(resources, R.drawable.game_over, null)
            binding.gameOverTextView.text = "You Lose!"
        }
        binding.scoreTextView.text = "Score: $score"

        binding.playAgainButton.setOnClickListener {
            parentFragmentManager.beginTransaction().replace(R.id.fragmentContainer, GameFragment())
                .commit()
        }

        binding.quitButton.setOnClickListener {
            requireActivity().finish()
        }
    }

}