package com.example.androidui_assignment

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.androidui_assignment.databinding.DoListRecyclerviewBinding

class MyViewHolder_DoList(val binding: DoListRecyclerviewBinding):RecyclerView.ViewHolder(binding.root)

class MyApdater_DoList(val datas:MutableList<String>?,val datas2:MutableList<String>?): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    lateinit var db:DBHelper
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        db = DBHelper(parent.context)
        return MyViewHolder_DoList(DoListRecyclerviewBinding.inflate(LayoutInflater.from(parent.context),parent,false))

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val binding = (holder as MyViewHolder_DoList).binding
        binding.itemData.text = datas!![position]
        val deldb = db.writableDatabase
        var count = 0
        binding.image.setImageResource(R.drawable.todo)
//        페이지를 열었을때 달성한 목표의 이미지에 이미지 적용
        val dupId = deldb.rawQuery(" select count(*) from DOLIST_TB where id = ? and successYn = 'Y'", arrayOf(datas2!![position]))
        while (dupId.moveToNext()) {
            count = dupId.getInt(0)
        }
        if (count == 1 ){
            binding.image.setImageResource(R.drawable.x)
        }

        binding.deleteBtn.setOnClickListener {
            deldb.execSQL("delete from DOLIST_TB where id = ?", arrayOf(datas2!![position]))
            datas.removeAt(position)
            datas2.removeAt(position)
            notifyDataSetChanged()
        }

//        버튼을 눌렀을때 바로 이미지를 바뀌게 하는 내용
        binding.btn.setOnClickListener {
            deldb.execSQL("update DOLIST_TB set successYn = 'Y' where id = ?", arrayOf(datas2!![position]))

            val dupId = deldb.rawQuery(" select count(*) from DOLIST_TB where id = ? and successYn = 'Y'", arrayOf(datas2!![position]))
            while (dupId.moveToNext()) {
                count = dupId.getInt(0)
            }
            if (count == 1 ){
                binding.image.setImageResource(R.drawable.x)
            }
//            val cursor = deldb.rawQuery(" select * from DOLIST_TB where id = ?", arrayOf(datas2!![position]))
//            while (cursor.moveToNext()) {
//                Log.d("myLog","클릭시 ${cursor.getString(0)}, ${cursor.getString(2)}, ${cursor.getString(3)}")
//            }
        }

//        val cursor1 = deldb.rawQuery(" select * from DOLIST_TB where id = ?", arrayOf(datas2!![position]))
//        while (cursor1.moveToNext()) {
//            Log.d("myLog","클릭후 ${cursor1.getString(0)}, ${cursor1.getString(2)}, ${cursor1.getString(3)}")
//        }
    }

    override fun getItemCount(): Int {
        return datas?.size?:0
    }
}