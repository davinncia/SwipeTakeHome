package com.example.swipetakehome

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.swipetakehome.databinding.ActivityMainBinding
import com.yuyakaido.android.cardstackview.CardStackLayoutManager
import com.yuyakaido.android.cardstackview.CardStackListener
import com.yuyakaido.android.cardstackview.Direction
import com.yuyakaido.android.cardstackview.StackFrom

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

        viewModel.fetchUsers()

        viewModel.users.observe(this) {
            cardStackAdapter.items = it
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

    //---------------------------------- C A L L B A C K S ---------------------------------------//

    override fun onCardDragging(direction: Direction?, ratio: Float) {
    }

    override fun onCardSwiped(direction: Direction?) {
    }

    override fun onCardRewound() {
    }

    override fun onCardCanceled() {
    }

    override fun onCardAppeared(view: View?, position: Int) {
    }

    override fun onCardDisappeared(view: View?, position: Int) {
    }
}