package com.example.circleslist

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.circleslist.databinding.ActivityMainBinding
import com.github.javafaker.Faker
import kotlin.random.Random


class MainActivity : AppCompatActivity() {

    private val list: MutableList<Circle> = mutableListOf()
    private val faker: Faker = Faker()
    private lateinit var adapter: CirclesRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = CirclesRecyclerAdapter(layoutInflater) {
            val dialog = CircleDialog.newInstance(it.number)
            dialog.show(supportFragmentManager, "dlg")
        }

        binding.list.adapter = adapter
        binding.list.layoutManager = GridLayoutManager(this, 4)
        adapter.submitList(null)
        var i = 0
        while (i != 32) {
            val value = Random.nextInt(1, 99)
            val color = faker.color().hex()
            list.add(Circle(value, color))
            i++
        }
        adapter.submitList(list.toList())

    }

    override fun onStart() {
        super.onStart()

        adapter.submitList(list.toList())
    }

    data class Circle(val number: Int, val color: String)
}