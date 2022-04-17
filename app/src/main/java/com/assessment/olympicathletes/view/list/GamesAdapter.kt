package com.assessment.olympicathletes.view.list

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.assessment.olympicathletes.databinding.GamesAdapterItemBinding
import com.assessment.olympicathletes.model.Game

class GamesAdapter(private val games: List<Game>) : RecyclerView.Adapter<GamesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GamesViewHolder {
        val binding = GamesAdapterItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GamesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GamesViewHolder, position: Int) {
        val game = games[position]
        holder.bindGameAndAthlete(game)
    }

    override fun getItemCount(): Int {
        return games.size
    }
}