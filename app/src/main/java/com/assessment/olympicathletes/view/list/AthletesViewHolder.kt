package com.assessment.olympicathletes.view.list

import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.assessment.olympicathletes.R
import com.assessment.olympicathletes.databinding.AthletesAdapterItemBinding
import com.assessment.olympicathletes.detail.view.OlympicAthletesDetailActivity
import com.assessment.olympicathletes.model.Athlete
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

class AthletesViewHolder(private val binding: AthletesAdapterItemBinding) :
    RecyclerView.ViewHolder(binding.root), View.OnClickListener {

    private lateinit var athlete: Athlete
    private var context: Context = binding.root.context

    init {
        binding.root.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        val context = itemView.context
        val intent = Intent(context, OlympicAthletesDetailActivity::class.java)
        intent.putExtra(ATHLETE_KEY, athlete)
        context.startActivity(intent)
    }

    companion object {
        const val ATHLETE_KEY = "Athlete"
    }

    fun bindAthlete(athlete: Athlete) {
        this.athlete = athlete
        binding.name.isSelected = true
        binding.name.text = athlete.name + " " + athlete.surname
        Picasso.with(context).load(context.getString(R.string.photo_url, athlete.athlete_id))
            .into(binding.photo, object : Callback {
                override fun onSuccess() {
                    binding.progressBar.visibility = View.INVISIBLE
                }

                override fun onError() {
                    binding.progressBar.visibility = View.INVISIBLE
                }
            })
    }

}
