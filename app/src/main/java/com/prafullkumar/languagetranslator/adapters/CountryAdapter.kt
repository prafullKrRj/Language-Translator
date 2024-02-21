package com.prafullkumar.languagetranslator.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.prafullkumar.languagetranslator.R
class CountryAdapter(
    private val countries: List<Pair<String, String>>, private val onCountryClick: (String) -> Unit
) : RecyclerView.Adapter<CountryAdapter.CountryViewHolder>()
{
    private var selectedPosition = -1

    inner class CountryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvCountryName: TextView = view.findViewById(R.id.tv_country_name)
        val tvCountryFlag: TextView = view.findViewById(R.id.tv_country_code)
        val cardView: CardView = view.findViewById(R.id.cardSelected)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.country_list_item, parent, false)
        return CountryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        val country = countries[position]
        holder.tvCountryName.text = country.first
        holder.tvCountryFlag.text = country.second
        holder.cardView.setOnClickListener {
            val previousSelectedPosition = selectedPosition
            selectedPosition = holder.adapterPosition
            onCountryClick(country.first)
            notifyItemChanged(previousSelectedPosition)
            notifyItemChanged(selectedPosition)
        }
        if (selectedPosition == position) {
            holder.cardView.setBackgroundResource(R.drawable.selected_card_background)
        } else {
            holder.cardView.setBackgroundResource(R.drawable.card_backround)
        }
    }

    override fun getItemCount() = countries.size
}