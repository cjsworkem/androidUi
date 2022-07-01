package com.example.androidui_assignment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androidui_assignment.databinding.ActivityBoardBinding

class BoardActivity : AppCompatActivity() {
    lateinit var binding : ActivityBoardBinding
    lateinit var adapter : MyAdapter_Board
    var datas : MutableList<String>? = null
    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_board)
        binding = ActivityBoardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val userId = intent.getStringExtra("id")
        datas = mutableListOf<String>()
        val db = DBHelper(this).readableDatabase
        val deldb = db.rawQuery("select id from BOARD_TB",null)
        while(deldb.moveToNext()){
            datas?.add(deldb.getString(0))
        }
        binding.write.setOnClickListener {
            val intent = Intent(this,WriteBoardActivity::class.java)
            intent.putExtra("id",userId)
            startActivity(intent)
        }

        val layoutManager = LinearLayoutManager(this)
        binding.doListRecyclerView.layoutManager = layoutManager
        adapter = MyAdapter_Board(datas,userId,this)
        binding.doListRecyclerView.adapter = adapter


    }
}