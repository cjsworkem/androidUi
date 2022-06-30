package com.example.androidui_assignment

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.androidui_assignment.databinding.DoListRecyclerviewBinding

class MyViewHolder_DoList(val binding: DoListRecyclerviewBinding):RecyclerView.ViewHolder(binding.root)

class MyApdater_DoList(val datas:MutableList<String>?,val datasId:MutableList<String>?,val userId:String?): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
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
        val dupId = deldb.rawQuery(" select count(*) from DOLIST_TB where id = ? and successYn = 'Y'", arrayOf(datasId!![position]))
        while (dupId.moveToNext()) {
            count = dupId.getInt(0)
        }
        if (count == 1 ){
            binding.image.setImageResource(R.drawable.x)
        }

        binding.deleteBtn.setOnClickListener {
            deldb.execSQL("delete from DOLIST_TB where id = ?", arrayOf(datasId!![position]))
            datas.removeAt(position)
            datasId.removeAt(position)
            notifyDataSetChanged()
        }
//        로그인한 아이디와 정보보기한 아이디가 같을떄만 수정가능
//        var baseId:String? = null
//        val baseIdDB = deldb.rawQuery( "select id from DOLIST_TB where id = ?", arrayOf(datas2!![position]))
//        while (baseIdDB.moveToNext()){
//            baseId = baseIdDB.getString(0)
//        }
//        if(userId == baseId) {
//
//        }

//        버튼을 눌렀을때 바로 이미지를 바뀌게 하는 내용
        binding.btn.setOnClickListener {
            deldb.execSQL("update DOLIST_TB set successYn = 'Y' where id = ?", arrayOf(datasId!![position]))

            val dupId = deldb.rawQuery(" select count(*) from DOLIST_TB where id = ? and successYn = 'Y'", arrayOf(datasId!![position]))
            while (dupId.moveToNext()) {
                count = dupId.getInt(0)
            }
            if (count == 1 ){
                binding.image.setImageResource(R.drawable.x)
            }

        }






    }

    override fun getItemCount(): Int {
        return datas?.size?:0
    }
}