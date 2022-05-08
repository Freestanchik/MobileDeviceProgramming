package com.example.circleslist

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView


class CirclesRecyclerAdapter(
    private val inflater: LayoutInflater,
    private val onClick: (MainActivity.Circle) -> Unit,
) :
    ListAdapter<MainActivity.Circle, CirclesRecyclerAdapter.ViewHolder>(UserDiffCallback) {

    class ViewHolder(
        view: View,
        val onClick: (MainActivity.Circle) -> Unit,
    ) : RecyclerView.ViewHolder(view) {
        private val number = view.findViewById<TextView>(R.id.number)
        private val circleCard = view.findViewById<CardView>(R.id.circleCard)
        private var circle: MainActivity.Circle? = null

        init {
            view.setOnClickListener {
                circle?.let {
                    onClick(it)
                }
            }
        }

        fun bind(circle: MainActivity.Circle) {
            this.circle = circle
            number.text = circle.number.toString()
            circleCard.setCardBackgroundColor(Color.parseColor(circle.color))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = inflater.inflate(R.layout.list_item, parent, false)
        return ViewHolder(view, onClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = getItem(position)
        holder.bind(user)
    }

    object UserDiffCallback : DiffUtil.ItemCallback<MainActivity.Circle>() {
        override fun areItemsTheSame(
            oldItem: MainActivity.Circle,
            newItem: MainActivity.Circle,
        ): Boolean {

            val res = oldItem == newItem
            return res
        }

        override fun areContentsTheSame(
            oldItem: MainActivity.Circle,
            newItem: MainActivity.Circle,
        ): Boolean {
            val res = oldItem.number == newItem.number && oldItem.color == newItem.color
            return res
        }
    }
}