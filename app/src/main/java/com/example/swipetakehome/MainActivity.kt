package com.example.swipetakehome

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.swipetakehome.databinding.ActivityMainBinding
import com.yuyakaido.android.cardstackview.*

class MainActivity : AppCompatActivity(), CardStackListener {

    private lateinit var binding: ActivityMainBinding

    private val viewModel by lazy {
        ViewModelProvider(this)[MainViewModel::class.java]
    }

    private lateinit var cardStackAdapter: CardStackAdapter
    private lateinit var cardStackManager: CardStackLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setCardAdapter()
        setButtons()

        viewModel.fetchUsers()

        viewModel.users.observe(this) {
            cardStackAdapter.items = it
        }

        viewModel.matchCount.observe(this) {
            binding.matchCountText.text = it.toString()
        }
    }

    private fun setCardAdapter() {
        cardStackAdapter = CardStackAdapter()
        cardStackManager = CardStackLayoutManager(this, this).apply {
            setStackFrom(StackFrom.Bottom)
            setVisibleCount(3)
            setTranslationInterval(18.0f)
            setScaleInterval(0.90f)
        }

        binding.stackView.layoutManager = cardStackManager
        binding.stackView.adapter = cardStackAdapter
    }

    private fun setButtons() {
        binding.btnNope.setOnClickListener {
            cardStackManager.setSwipeAnimationSetting(
                SwipeAnimationSetting.Builder().setDirection(Direction.Left).build()
            )
            binding.stackView.swipe()
        }

        binding.btnYep.setOnClickListener {
            cardStackManager.setSwipeAnimationSetting(
                SwipeAnimationSetting.Builder().setDirection(Direction.Right).build()
            )
            binding.stackView.swipe()
        }
    }

    //---------------------------------- C A L L B A C K S ---------------------------------------//

    override fun onCardDragging(direction: Direction?, ratio: Float) {
    }

    override fun onCardSwiped(direction: Direction?) {
        if (direction == Direction.Right) viewModel.checkForMatch()
    }

    override fun onCardRewound() {
    }

    override fun onCardCanceled() {
    }

    override fun onCardAppeared(view: View?, position: Int) {
        if (position in 0..cardStackAdapter.items.lastIndex) {
            viewModel.currentProfileDisplayed = cardStackAdapter.items[position]
        }
    }

    override fun onCardDisappeared(view: View?, position: Int) {
    }
}