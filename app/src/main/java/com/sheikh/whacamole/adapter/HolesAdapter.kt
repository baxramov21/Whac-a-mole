package com.sheikh.whacamole.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.sheikh.whacamole.R
import kotlinx.android.synthetic.main.hole_item.view.*

class HolesAdapter() :
    RecyclerView.Adapter<HolesAdapter.CoinListViewHolder>() {

    var holeClickListener: CoinClickListener? = null

    var holeImageIDList: List<Int> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    interface CoinClickListener {
        fun onHoleClick(position: Int)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): CoinListViewHolder {
        val result = LayoutInflater.from(parent.context).inflate(R.layout.hole_item, parent, false)
        return CoinListViewHolder(result)
    }

    override fun onBindViewHolder(holder: CoinListViewHolder, position: Int) {
        val imageID = holeImageIDList[position]
        holder.imageViewCoinImage.setImageResource(imageID)

        holder.itemView.setOnClickListener {
            holeClickListener?.onHoleClick(position)
        }
    }

    override fun getItemCount() = holeImageIDList.size

    inner class CoinListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageViewCoinImage: ImageView = itemView.grass
    }
}

