package com.example.androidui_assignment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.androidui_assignment.databinding.ActivityButtonBinding

class ButtonActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_button)
        val binding = ActivityButtonBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val userId = intent.getStringExtra("id")

        binding.userList.setOnClickListener {
            val intent = Intent(this,ListActivity::class.java)
            intent.putExtra("id",userId)
            startActivity(intent)
        }
        binding.btnBoard.setOnClickListener {
            val intent = Intent(this,BoardActivity::class.java)
            intent.putExtra("id",userId)
            startActivity(intent)
        }
        binding.top3.setOnClickListener {
            val intent = Intent(this,Top3Activity::class.java)
            intent.putExtra("id",userId)
            startActivity(intent)
        }

    }
}