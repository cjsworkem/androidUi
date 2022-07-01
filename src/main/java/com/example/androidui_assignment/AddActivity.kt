package com.example.androidui_assignment

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import com.example.androidui_assignment.databinding.ActivityAddBinding

// 목표 추가할 엑티비티
class AddActivity : AppCompatActivity() {
    lateinit var binding: ActivityAddBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val userId = intent.getStringExtra("id")

        binding.add.setOnClickListener {
            val intent = Intent(this,ListActivity::class.java)
            val inputData = binding.addEditView.text.toString()
            if (inputData.isBlank()) { // 입력창이 비어있으면 그냥 돌려보냄
                intent.putExtra("id", userId)
                startActivity(intent)
            } else {
                val db = DBHelper(this).writableDatabase
                var count = 0
                val dupId =
                    db.rawQuery(" select count(*) from USERS_TB where user_id = ?", arrayOf(userId))
                while (dupId.moveToNext()) {
                    count = dupId.getInt(0)
                }
                dupId.close()
    //                    목표를 처음 등록하는 사람이면 새로 항목을 만들어준다.
                if (count == 0) {
                    db.execSQL(
                        "insert into USERS_TB(user_id,successYn) values(?,?)",
                        arrayOf<String?>(userId, "N")
                    )
                }
//                목푝 목록 등록
                db.execSQL(
                    "insert into DOLIST_TB(user_id,todo,successYn) values(?,?,?)",
                    arrayOf<String?>(userId, inputData, "N")
                )
                db.close()
                intent.putExtra("id", userId)
                startActivity(intent)
            }
        }
    }
    //    다른 영역터치시 키보드 내리기
    override fun onTouchEvent(event: MotionEvent): Boolean {
        val imm: InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
        return true
    }

}

