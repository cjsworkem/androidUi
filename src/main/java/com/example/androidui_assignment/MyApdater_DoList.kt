package com.example.androidui_assignment


import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.androidui_assignment.databinding.DoListRecyclerviewBinding


class MyViewHolder_DoList(val binding: DoListRecyclerviewBinding):RecyclerView.ViewHolder(binding.root)

class MyApdater_DoList(val datas:MutableList<String>?,val datasId:MutableList<String>?,val userId:String?,val context: Context): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
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
            binding.image.setImageResource(R.drawable.ok)
            binding.finishButton.text = "완료"
            binding.finishButton.isEnabled = false
        }
        dupId.close()

//        삭제버튼 클릭시 삭제
        binding.delButton.setOnClickListener {
            deldb.execSQL("delete from DOLIST_TB where id = ?", arrayOf(datasId!![position]))
            datas.removeAt(position)
            datasId.removeAt(position)
            notifyDataSetChanged()
//            클릭시 바로 새로고침
            val intent = (context as Activity).intent
            (context as Activity).finish()
            (context as Activity).overridePendingTransition(0, 0)
            (context as Activity).startActivity(intent)
            (context as Activity).overridePendingTransition(0, 0)
        }
//        로그인한 아이디와 정보보기한 아이디가 같을떄만 수정가능
        var baseId:String? = null
        val baseIdDB = deldb.rawQuery( "select user_id from DOLIST_TB where id = ?", arrayOf(datasId!![position]))
        while (baseIdDB.moveToNext()){
            baseId = baseIdDB.getString(0)
        }
        if(userId != baseId) {
            binding.finishButton.visibility = View.INVISIBLE
            binding.delButton.visibility = View.INVISIBLE
        }

//        버튼을 눌렀을때 바로 이미지를 바뀌게 하는 내용
        binding.finishButton.setOnClickListener {
            deldb.execSQL("update DOLIST_TB set successYn = 'Y' where id = ?", arrayOf(datasId!![position]))
//            만약 목표달성이 되어있다면 해당 이미지를 바꿔줌
            val dupId = deldb.rawQuery(" select count(*) from DOLIST_TB where id = ? and successYn = 'Y'", arrayOf(datasId!![position]))
            while (dupId.moveToNext()) {
                count = dupId.getInt(0)
            }
            if (count == 1 ){
                binding.image.setImageResource(R.drawable.ok)
                binding.finishButton.text = "완료"
                binding.finishButton.isEnabled = false
            }
            dupId.close()

//            클릭시 바로 새로고침
            val intent = (context as Activity).intent
            (context as Activity).finish()
            (context as Activity).overridePendingTransition(0, 0)
            (context as Activity).startActivity(intent)
            (context as Activity).overridePendingTransition(0, 0)


        }






    }

    override fun getItemCount(): Int {
        return datas?.size?:0
    }
}