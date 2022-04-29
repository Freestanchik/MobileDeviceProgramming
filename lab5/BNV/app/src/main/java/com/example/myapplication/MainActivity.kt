package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.example.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNavigationView.setOnItemSelectedListener {
            val fragment: Fragment
            when (it.itemId) {
                R.id.nav_game -> {
                    fragment = GameFragment.newInstance()
                    setCurrentFragment(fragment)
                }
                R.id.nav_leaderboard -> {
                    fragment = LeaderBoardFragment.newInstance()
                    setCurrentFragment(fragment)
                }
                R.id.nav_profile -> {
                    fragment = ProfileFragment.newInstance()
                    setCurrentFragment(fragment)
                }
            }
            true
        }
        binding.bottomNavigationView.selectedItemId = R.id.nav_profile
    }

    private fun setCurrentFragment(fragment: Fragment) {
        supportFragmentManager.commit {
            replace(R.id.fragmentContainerView, fragment)
        }
    }
}