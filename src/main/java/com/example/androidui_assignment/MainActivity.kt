package com.example.androidui_assignment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.androidui_assignment.databinding.ActivityMainBinding

// 로그인화면
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            val intent = Intent(this,ButtonActivity::class.java)
            val id = binding.userId.text.toString()
            intent.putExtra("id",id)
            startActivity(intent)
        }
        binding.btnJoin.setOnClickListener {
            val intent = Intent(this,JoinActivity::class.java)
            startActivity(intent)
        }


    }
}