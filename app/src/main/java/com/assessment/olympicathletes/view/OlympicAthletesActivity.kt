package com.assessment.olympicathletes.view

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.assessment.olympicathletes.contract.OlympicAthletesContract
import com.assessment.olympicathletes.databinding.OlympicAthletesActivityBinding
import com.assessment.olympicathletes.model.Game
import com.assessment.olympicathletes.mvp.BaseMvpActivity
import com.assessment.olympicathletes.presenter.OlympicAthletesPresenter
import com.assessment.olympicathletes.view.list.GamesAdapter

class OlympicAthletesActivity :
    BaseMvpActivity<OlympicAthletesContract.View, OlympicAthletesContract.Presenter>(),
    OlympicAthletesContract.View {

    private lateinit var binding: OlympicAthletesActivityBinding
    private lateinit var gamesAdapter: GamesAdapter

    override var mPresenter: OlympicAthletesContract.Presenter = OlympicAthletesPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = OlympicAthletesActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpInitialView()
        mPresenter.loadData()
        binding.reloadButton.setOnClickListener {
            setUpInitialView()
            mPresenter.loadData()
        }
    }

    private fun setUpInitialView() {
        binding.gamesList.visibility = View.GONE
        binding.reloadButton.visibility = View.GONE
        binding.noGames.visibility = View.GONE
        binding.progressBar.visibility = View.VISIBLE
    }

    override fun showReloadButton() {
        binding.progressBar.visibility = View.GONE
        binding.gamesList.visibility = View.GONE
        binding.noGames.visibility = View.GONE
        binding.reloadButton.visibility = View.VISIBLE
    }

    override fun showData(it: List<Game>) {
        binding.progressBar.visibility = View.GONE
        binding.reloadButton.visibility = View.GONE
        binding.noGames.visibility = View.GONE

        initAthletesRecyclerView(it)
    }

    private fun initAthletesRecyclerView(games: List<Game>) {
        val linearLayoutManager = LinearLayoutManager(this)
        binding.gamesList.layoutManager = linearLayoutManager
        val sortedGames = games.sortedByDescending { it.year }
        gamesAdapter = GamesAdapter(sortedGames)
        binding.gamesList.adapter = gamesAdapter
        binding.gamesList.visibility = View.VISIBLE
    }

    override fun displayNoGamesView() {
        binding.progressBar.visibility = View.GONE
        binding.gamesList.visibility = View.GONE
        binding.reloadButton.visibility = View.VISIBLE
        binding.noGames.visibility = View.VISIBLE
    }

}