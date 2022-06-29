package com.example.androidui_assignment

import android.content.ContentValues
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.androidui_assignment.databinding.ActivityJoinBinding

// 회원가입 엑티비티
class JoinActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_join)
        val binding = ActivityJoinBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val db:SQLiteDatabase = DBHelper(this).writableDatabase
        var count = 0
        binding.submit.setOnClickListener {
//            입력된 id, password값 가져옴
            val userId = binding.id.text.toString()
            val password = binding.password.text.toString()
//            중복 아이디체크
            val dupId = db.rawQuery(" select count(*) from MEMBER_TB where user_id = ?", arrayOf(userId))
            while (dupId.moveToNext()) {
                Log.d("myLog", "dupId ${dupId.getInt(0)}")
                count = dupId.getInt(0)
            }
            if (count >= 1){
//                중복일시 토스트메세지 띄움
                val toast = Toast.makeText(this,"중복된 아이디입니다", Toast.LENGTH_SHORT)
                toast.show()

            } else {
//                중복아니면 데이터베이스에 입력후 로그인 화면으로 돌려보냄
                db.execSQL(" insert into MEMBER_TB(user_id,password) values (?,?) ", arrayOf<String>(userId,password))
                val intent = Intent(this,MainActivity::class.java)
                startActivity(intent)

            }

        }




    }
}