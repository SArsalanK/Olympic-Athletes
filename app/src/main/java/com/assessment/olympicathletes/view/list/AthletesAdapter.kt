package com.assessment.olympicathletes.view.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.assessment.olympicathletes.databinding.AthletesAdapterItemBinding
import com.assessment.olympicathletes.model.Athlete

class AthletesAdapter(private val athletes: List<Athlete>) :
    RecyclerView.Adapter<AthletesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AthletesViewHolder {
        val binding =
            AthletesAdapterItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AthletesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AthletesViewHolder, position: Int) {
        val athlete = athletes[position]
        holder.bindAthlete(athlete)
    }

    override fun getItemCount(): Int {
        return athletes.size
    }
}