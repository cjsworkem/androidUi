package com.example.androidui_assignment

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.androidui_assignment.databinding.DoListRecyclerviewBinding

class MyViewHolder_DoList(val binding: DoListRecyclerviewBinding):RecyclerView.ViewHolder(binding.root)

class MyApdater_DoList(val datas:MutableList<String>?): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }
}