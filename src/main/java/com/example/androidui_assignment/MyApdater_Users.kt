package com.example.androidui_assignment

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.androidui_assignment.databinding.UserRecyclerviewBinding

//
class MyViewHolder_Users(val binding:UserRecyclerviewBinding):RecyclerView.ViewHolder(binding.root)


class MyApdater_Users(val datas:MutableList<String>?, val datasId:MutableList<String>?,val context: Context,val userId:String?): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    lateinit var db : DBHelper
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        db = DBHelper(parent.context)
        return MyViewHolder_Users(UserRecyclerviewBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val binding = (holder as MyViewHolder_Users).binding
        binding.userData.text = "${datas!![position]}님의 목표입니다."

        var count = 1
        val deldb  = db.readableDatabase
        var baseId:String? = ""
        val basedb = deldb.rawQuery( " select user_id from USERS_TB where id = ?", arrayOf(datasId!![position]))
        while (basedb.moveToNext()){
            baseId = basedb.getString(0)
        }
        val imagedb = deldb.rawQuery("select count(*) from DOLIST_TB where user_id = ? AND successYn = 'N' ", arrayOf(baseId))
        while (imagedb.moveToNext()){
            count = imagedb.getInt(0)
        }
        if (count == 0 ){
            binding.image.setImageResource(R.drawable.success)
        }


        binding.userData.setOnClickListener {

            val intent = Intent(context,DetailActivity::class.java)
            intent.putExtra("baseId",baseId)
            intent.putExtra("id",userId)
            ContextCompat.startActivity(context,intent,null)

        }
        deldb.close()

    }

    override fun getItemCount(): Int {
        return datas?.size?:0
    }
}