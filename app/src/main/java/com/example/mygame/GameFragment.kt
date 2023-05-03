package com.example.mygame

import android.graphics.Color
import android.graphics.Point
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import android.widget.RelativeLayout
import androidx.fragment.app.Fragment
import com.example.mygame.model.GameView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class GameFragment : Fragment() {
    lateinit var gameView: GameView
    lateinit var fireButton: Button
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val display = requireActivity().windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        gameView = GameView(requireContext(), size)
        val game = FrameLayout(requireContext())
        val gameButtons = RelativeLayout(requireContext())
        fireButton = Button(requireContext());
        fireButton.text = "Fire"
        fireButton.setBackgroundColor(Color.RED)
        val b1 = RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.WRAP_CONTENT,
            RelativeLayout.LayoutParams.WRAP_CONTENT
        )
        val params = RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.FILL_PARENT,
            RelativeLayout.LayoutParams.FILL_PARENT
        )
        gameButtons.layoutParams = params
        gameButtons.addView(fireButton)
        b1.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE)
        b1.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE)
        fireButton.layoutParams = b1
        game.addView(gameView)
        game.addView(gameButtons)
        return game
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        CoroutineScope(Dispatchers.Default).launch {
            gameView.startGame().await()
            withContext(Dispatchers.Main) {
                val intent = GameOverFragment(gameView.score)
             parentFragmentManager.beginTransaction().replace(R.id.fragmentContainer, intent).commit()
            }
        }
        super.onViewCreated(view, savedInstanceState)
        fireButton.setOnClickListener {
            gameView.shot()
        }
    }


}