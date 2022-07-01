package com.example.androidui_assignment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androidui_assignment.databinding.ActivityDetailBinding
import com.example.androidui_assignment.databinding.ActivityListBinding

// 목표를 등록한 사람들 목록을 보여줄 엑티비티
class ListActivity : AppCompatActivity() {
    lateinit var binding : ActivityListBinding
    var datas :MutableList<String>? = null
    var datasId :MutableList<String>? = null
    lateinit var adapter: MyApdater_Users

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_list)
        binding = ActivityListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var userId = intent.getStringExtra("id")

        //        목표 목록을 담을 datas
        datas = mutableListOf<String>()
//        목표 목록의 id값을 담을 datasId
        datasId = mutableListOf<String>()
//        데이터베이스에서 해당 아이디의 목표만 긁어와서 뿌림
        val db = DBHelper(this).readableDatabase
//        누른 목록에 있는 id 값을 가져와 뿌림
        val cursor = db.rawQuery("select * from USERS_TB",null)
        cursor.run {
            while (moveToNext()){
                datas?.add(cursor.getString(1).toString())
                datasId?.add(cursor.getString(0).toString())
            }

        }

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager=layoutManager
        adapter= MyApdater_Users(datas,datasId,this,userId)
        binding.recyclerView.adapter=adapter
        binding.recyclerView.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))

        binding.addTodo.setOnClickListener {
            val intent = Intent(this,AddActivity::class.java)
            intent.putExtra("id",userId)
            startActivity(intent)
        }
        binding.selfBtn.setOnClickListener {
            val intent = Intent(this,DetailActivity::class.java)
            intent.putExtra("id",userId)
            intent.putExtra("baseId",userId)
            startActivity(intent)
        }

    }
}