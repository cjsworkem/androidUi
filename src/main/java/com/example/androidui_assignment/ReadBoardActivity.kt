package com.example.androidui_assignment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.androidui_assignment.databinding.ActivityReadBoardBinding

class ReadBoardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_read_board)
        val binding = ActivityReadBoardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val userId = intent.getStringExtra("id")
        val boardId = intent.getStringExtra("boardId")
        var boardUserId:String? = null
        val db = DBHelper(this).readableDatabase
        val deldb = db.rawQuery("select user_id,title,content,reg_date from BOARD_TB where id = ? ",
            arrayOf(boardId))
        while(deldb.moveToNext()){
            boardUserId = deldb.getString(0)
            binding.id.text = boardUserId
            binding.title.text = deldb.getString(1)
            binding.content.text = deldb.getString(2)
            binding.regDate.text = deldb.getString(3)
        }
        db.close()

        if (userId != boardUserId){
            binding.adjust.visibility = View.INVISIBLE
        }

        binding.adjust.setOnClickListener {
            val intent = Intent(this,WriteBoardActivity::class.java)
            intent.putExtra("id",userId)
            intent.putExtra("boardId",boardId)
            startActivity(intent)
        }



    }
}