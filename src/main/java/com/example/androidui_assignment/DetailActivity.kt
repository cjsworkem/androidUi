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
        val cursor = db.rawQuery("select * from DOLIST_TB where user_id = ?", arrayOf(baseId))
        cursor.run {
            while (moveToNext()){
                datas?.add(cursor.getString(2).toString())
                datasId?.add(cursor.getString(0).toString())
                Log.d("myLog","detail쪽 ${cursor.getString(2)}")
            }
        }

        binding.userId.text = "$baseId 님의 목표입니다"


        var success:Double = 0.0
        var total = 0

        val cursorTotal = db.rawQuery("select count(*) from DOLIST_TB where user_id = ? ", arrayOf(baseId))
        while(cursorTotal.moveToNext()){
            total = cursorTotal.getInt(0)
        }
        if (total == 0) {
            binding.success.text = "목표를 입력해주세요!"
        } else {
//        달성율을 알려주는 쿼리문.
            val cursorSuccess = db.rawQuery("select count(*) from DOLIST_TB  where user_id = ? and successYn = 'Y'", arrayOf(baseId))
            while(cursorSuccess.moveToNext()){
                success = cursorSuccess.getDouble(0)
            }
            val result:Int = (success/total * 100).toInt()
            if(result == 100) {
                binding.success.text = "목표 완전 달성!"
            } else {
                binding.success.text = "목표 달성율 (${result}%)!"
            }

        }
        db.close()





        val layoutManager = LinearLayoutManager(this)
        binding.doListRecyclerView.layoutManager=layoutManager
        adapter= MyApdater_DoList(datas,datasId,userId,this)
        binding.doListRecyclerView.adapter=adapter
        binding.doListRecyclerView.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))


    }
}