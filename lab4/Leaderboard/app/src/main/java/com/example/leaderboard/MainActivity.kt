package com.example.leaderboard

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.leaderboard.databinding.ActivityMainBinding
import com.github.javafaker.Faker


class MainActivity : AppCompatActivity() {

    private val list: MutableList<User> = mutableListOf()
    private val faker: Faker = Faker()
    private lateinit var adapter: UsersRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = UsersRecyclerAdapter(layoutInflater) {
            val intent = Intent(this, UserActivity::class.java)
            intent.putExtra("name", it.name)
            intent.putExtra("score", it.score.toString())
            startActivity(intent)
        }

        binding.list.adapter = adapter
        binding.list.layoutManager = LinearLayoutManager(this)
        adapter.submitList(null)
        binding.btnAddLeader.setOnClickListener {
            val user = User(faker.name().fullName(), faker.number().randomDigit() * 100)
            list.add(user)

            adapter.submitList(list.toList())
        }

        binding.btnRemoveLeader.setOnClickListener {
            if (list.isNotEmpty())
                list.removeLast()

            adapter.submitList(list.toList())
        }

        binding.btnClear.setOnClickListener {
            list.clear()
            adapter.submitList(list.toList())
        }
    }

    override fun onStart() {
        super.onStart()

        adapter.submitList(list.toList())
    }

    data class User(val name: String, val score: Int)
}