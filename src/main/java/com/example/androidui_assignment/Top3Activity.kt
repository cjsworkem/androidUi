package com.example.androidui_assignment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.androidui_assignment.databinding.ActivityTop3Binding

class Top3Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_top3)
        val binding = ActivityTop3Binding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}