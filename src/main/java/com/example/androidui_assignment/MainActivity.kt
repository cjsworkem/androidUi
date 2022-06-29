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
        val id = "123"


        binding.submit.setOnClickListener {
            val intent = Intent(this,JoinActivity::class.java)
            intent.putExtra("id",id)
            startActivity(intent)
        }
        binding.submit2.setOnClickListener {
            val intent = Intent(this,DetailActivity::class.java)
            intent.putExtra("id",id)
            startActivity(intent)
        }
        binding.submit3.setOnClickListener {
            val intent = Intent(this,AddActivity::class.java)
            intent.putExtra("id",id)
            startActivity(intent)
        }


//        val db = DBHelper(this).readableDatabase
////        db.execSQL(" insert into DOLIST_TB(user_id,todo,successYn) values (?,?,?) ", arrayOf<String>("123","1222","N"))
////        db.execSQL(" insert into DOLIST_TB(user_id,todo,successYn) values (?,?,?) ", arrayOf<String>("1423","122322","N"))
//        val cursor = db.rawQuery("select * from MEMBER_TB",null)
//        cursor.run {
//            while (moveToNext()){
//                Log.d("myLog","아이디 값 확인 ${cursor.getString(1)}")
//            }
//        }
    }
}