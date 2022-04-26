package com.example.leaderboard

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.leaderboard.databinding.ActivityUserBinding

class UserActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvName.text = intent.getStringExtra("name") ?: ""
        binding.tvScore.text = intent.getStringExtra("score") ?: ""
    }
}