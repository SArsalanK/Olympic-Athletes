package com.assessment.olympicathletes.view.list

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.assessment.olympicathletes.databinding.GamesAdapterItemBinding
import com.assessment.olympicathletes.model.Athlete
import com.assessment.olympicathletes.model.Game

class GamesViewHolder(private val binding: GamesAdapterItemBinding) :
    RecyclerView.ViewHolder(binding.root) {
    private lateinit var game: Game
    private var context: Context = binding.root.context

    init {
        val linearLayoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.athletesList.layoutManager = linearLayoutManager
    }

    companion object {
        private const val GOLD_POINTS = 5
        private const val SILVER_POINTS = 3
        private const val BRONZE_POINTS = 1
    }

    fun bindGameAndAthlete(game: Game) {
        this.game = game
        binding.gameNameYear.text = game.city + ' ' + game.year
        initAthletesRecyclerView(game.athletes)
    }

    private fun initAthletesRecyclerView(athletes: MutableList<Athlete>?) {
        if (!athletes.isNullOrEmpty()) {
            setGlobalScore(athletes)
            initAthletesAdapter(athletes)
            binding.athletesList.visibility = View.VISIBLE
            binding.noAthletes.visibility = View.GONE
        } else {
            binding.noAthletes.visibility = View.VISIBLE
            binding.athletesList.visibility = View.GONE
        }
    }

    private fun initAthletesAdapter(athletes: MutableList<Athlete>) {
        val sortedListOfAthletes = athletes.sortedByDescending { it.global_score }
        val athletesAdapter = AthletesAdapter(sortedListOfAthletes)
        binding.athletesList.adapter = athletesAdapter
    }

    private fun setGlobalScore(athletes: MutableList<Athlete>) {
        //Loop through the athletes and calculate the Global Score.
        athletes.forEach {
            var globalScore = 0
            it.results?.forEach { result ->
                if (result.gold > 0) {
                    globalScore = result.gold * GOLD_POINTS
                }
                if (result.silver > 0) {
                    globalScore += result.silver * SILVER_POINTS
                }
                if (result.bronze > 0) {
                    globalScore += result.bronze * BRONZE_POINTS
                }
            }
            it.global_score = globalScore
        }
    }

}
