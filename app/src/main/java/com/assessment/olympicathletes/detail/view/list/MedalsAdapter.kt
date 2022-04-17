package com.assessment.olympicathletes.detail.view.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.assessment.olympicathletes.databinding.MedalsAdapterItemBinding
import com.assessment.olympicathletes.model.Result

class MedalsAdapter(private val results: List<Result>) : RecyclerView.Adapter<MedalsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MedalsViewHolder {
        val binding =
            MedalsAdapterItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MedalsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MedalsViewHolder, position: Int) {
        val result = results[position]
        holder.bindMedals(result)
    }

    override fun getItemCount(): Int {
        return results.size
    }
}