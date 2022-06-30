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
    var datasId :MutableList<String>? = null
    lateinit var adapter: MyApdater_DoList
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_detail)
        binding  = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        로그인한 id
        val userId = intent.getStringExtra("id")
//        세부목록을 볼려는 아이디
        val baseId = intent.getStringExtra("baseId")



//        목표 목록을 담을 datas
        datas = mutableListOf<String>()
//        목표 목록의 id값을 담을 datasId
        datasId = mutableListOf<String>()
//        데이터베이스에서 해당 아이디의 목표만 긁어와서 뿌림
        val db = DBHelper(this).readableDatabase
//        누른 목록에 있는 id 값을 가져와 뿌림
        val cursor = db.rawQuery("select * from DOLIST_TB where user_id = ?", arrayOf(userId))
        cursor.run {
            while (moveToNext()){
                datas?.add(cursor.getString(2).toString())
                datasId?.add(cursor.getString(0).toString())
                Log.d("myLog","detail쪽 ${cursor.getString(2)}")
            }

        }



        val layoutManager = LinearLayoutManager(this)
        binding.mainRecylerView.layoutManager=layoutManager
        adapter= MyApdater_DoList(datas,datasId,userId)
        binding.mainRecylerView.adapter=adapter
        binding.mainRecylerView.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))


    }
}