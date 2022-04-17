package com.assessment.olympicathletes.detail.view.list

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.assessment.olympicathletes.databinding.MedalsAdapterItemBinding
import com.assessment.olympicathletes.model.Result

class MedalsViewHolder(private val binding: MedalsAdapterItemBinding) :
    RecyclerView.ViewHolder(binding.root) {
    private lateinit var result: Result

    fun bindMedals(medals: Result) {
        this.result = medals
        binding.gameName.text = medals.city

        if (medals.gold > 0) {
            binding.gold.text = medals.gold.toString()
        } else {
            binding.gold.visibility = View.GONE
        }
        if (medals.silver > 0) {
            binding.silver.text = medals.silver.toString()
        } else {
            binding.silver.visibility = View.GONE
        }
        if (medals.bronze > 0) {
            binding.bronze.text = medals.bronze.toString()
        } else {
            binding.bronze.visibility = View.GONE
        }
    }

}
