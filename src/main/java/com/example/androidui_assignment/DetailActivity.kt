package com.example.androidui_assignment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androidui_assignment.databinding.ActivityDetailBinding

// 목표의 세부사항을 볼 엑티비티
class DetailActivity : AppCompatActivity() {
    lateinit var binding : ActivityDetailBinding
    var datas :MutableList<String>? = null
    var datas2 :MutableList<String>? = null
    lateinit var adapter: MyApdater_DoList
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_detail)
        binding  = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val intent = intent
        var userId = intent.getStringExtra("id")



        datas = mutableListOf<String>()
        datas2 = mutableListOf<String>()
//        데이터베이스에서 해당 아이디의 목표만 긁어와서 뿌림
        val db = DBHelper(this).readableDatabase
        val cursor = db.rawQuery("select * from DOLIST_TB where user_id = ?", arrayOf(userId))
        cursor.run {
            while (moveToNext()){
                datas?.add(cursor.getString(2).toString())
                datas2?.add(cursor.getString(0).toString())
                Log.d("myLog","detail쪽 ${cursor.getString(2)}")
            }

        }



        val layoutManager = LinearLayoutManager(this)
        binding.mainRecylerView.layoutManager=layoutManager
        adapter= MyApdater_DoList(datas,datas2)
        binding.mainRecylerView.adapter=adapter
        binding.mainRecylerView.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))


    }
}