package com.example.leaderboard

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class UsersRecyclerAdapter(
    private val inflater: LayoutInflater,
    private val onClick: (MainActivity.User) -> Unit
) :
    ListAdapter<MainActivity.User, UsersRecyclerAdapter.ViewHolder>(UserDiffCallback) {

    class ViewHolder(
        view: View,
        val onClick: (MainActivity.User) -> Unit
    ) : RecyclerView.ViewHolder(view) {
        private val name = view.findViewById<TextView>(R.id.tv_name)
        private val job = view.findViewById<TextView>(R.id.tv_score)
        private var user: MainActivity.User? = null

        init {
            view.setOnClickListener {
                user?. let {
                    onClick(it)
                }
            }
        }

        fun bind(user: MainActivity.User) {
            this.user = user
            name.text = user.name
            job.text = user.score.toString()
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

    object UserDiffCallback : DiffUtil.ItemCallback<MainActivity.User>() {
        override fun areItemsTheSame(
            oldItem: MainActivity.User,
            newItem: MainActivity.User
        ): Boolean {

            val res = oldItem == newItem

            return res
        }

        override fun areContentsTheSame(
            oldItem: MainActivity.User,
            newItem: MainActivity.User
        ): Boolean {

            val res = oldItem.name == newItem.name && oldItem.score == newItem.score

            return res
        }
    }
}