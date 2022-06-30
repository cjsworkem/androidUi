package com.example.androidui_assignment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.androidui_assignment.databinding.ActivityListBinding

// 목표를 등록한 사람들 목록을 보여줄 엑티비티
class ListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_list)
        val binding = ActivityListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val getIntent = intent
        var userId = getIntent.getStringExtra("id")


        binding.detail.setOnClickListener {
            val intent = Intent(this,DetailActivity::class.java)
            intent.putExtra("id",userId)
            startActivity(intent)
        }
        binding.add.setOnClickListener {
            val intent = Intent(this,AddActivity::class.java)
            intent.putExtra("id",userId)
            startActivity(intent)
        }

        val db = DBHelper(this).readableDatabase
        val cursor = db.rawQuery("select * from DOLIST_TB where user_id = ?", arrayOf(userId))
        cursor.run {
            while (moveToNext()){

                Log.d("myLog","detail쪽 ${cursor.getString(0)}")
            }

        }
    }
}