package com.example.androidui_assignment

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.androidui_assignment.databinding.BoardRecyclerviewBinding

class MyViewHolder_Board(val binding: BoardRecyclerviewBinding):RecyclerView.ViewHolder(binding.root)

class MyAdapter_Board(val datas:MutableList<String>?,val userId:String?,val context: Context):RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    lateinit var db:DBHelper
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        db = DBHelper(parent.context)
        return MyViewHolder_Board(BoardRecyclerviewBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val binding = (holder as MyViewHolder_Board).binding
        val deldb = db.readableDatabase
        var id:String? = null
        val cursor = deldb.rawQuery( "select id,user_id, title, reg_date from BOARD_TB where id = ?",
            arrayOf(datas!![position]))
        while (cursor.moveToNext()) {
            id = cursor.getString(0)
            val userId = cursor.getString(1)
            val title = cursor.getString(2)
            val regDate = cursor.getString(3)
            binding.id.text = userId
            binding.title.text = title
            binding.regDate.text = regDate
        }
        deldb.close()
        binding.title.setOnClickListener {
            val intent = Intent(context,ReadBoardActivity::class.java)
            intent.putExtra("boardId",id)
            intent.putExtra("id",userId)
            ContextCompat.startActivity(context,intent,null)
        }
    }

    override fun getItemCount(): Int {
        return datas?.size?:0
    }
}