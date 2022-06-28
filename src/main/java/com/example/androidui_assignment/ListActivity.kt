package com.example.androidui_assignment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

// 목표를 등록한 사람들 목록을 보여줄 엑티비티
class ListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)
    }
}