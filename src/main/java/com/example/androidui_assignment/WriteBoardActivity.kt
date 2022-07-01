package com.example.androidui_assignment

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.example.androidui_assignment.databinding.ActivityWriteBoardBinding
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class WriteBoardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_write_board)
        val binding = ActivityWriteBoardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val userId = intent.getStringExtra("id")
        val boardId:String? = intent.getStringExtra("boardId")
        if (boardId != null) { //boardId가 null 아닐떄 > 수정요청으로 페이지 접근할떄
            val db = DBHelper(this).readableDatabase
            val deldb = db.rawQuery("select title,content from BOARD_TB where id = ? ",
                arrayOf(boardId))
            while(deldb.moveToNext()){
                binding.title.setText(deldb.getString(0))
                binding.content.setText(deldb.getString(1))
            }
            db.close()
            binding.submit.text = "adjust"
            binding.submit.setOnClickListener {
                val title = binding.title.text.toString()
                val content = binding.content.text.toString()
                if (title.isBlank()){ // 제목칸 빈 여부 체크
                    Toast.makeText(this,"제목을 입력해주세요",Toast.LENGTH_SHORT).show()
                } else if (content.isBlank()) { // 내용칸 빈 여부 체크
                    Toast.makeText(this, "내용을 입력해주세요", Toast.LENGTH_SHORT).show()
                } else { // 다 차있으면 데이터 입력
                    val db = DBHelper(this).writableDatabase
                    db.execSQL(" update BOARD_TB set title = ?, content = ? where id = ?",
                        arrayOf(title,content,boardId))
                    db.close()
                    val intent = Intent(this,BoardActivity::class.java)
                    intent.putExtra("id",userId)
                    startActivity(intent)
                }

            } //  binding.submit.setOnClickListener
        } else {


            binding.submit.setOnClickListener {
                val title = binding.title.text.toString()
                val content = binding.content.text.toString()
//            호환성.
                val regDate = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"))
                } else {
                    SimpleDateFormat("yyyy/MM/dd", Locale.getDefault()).format(Date())
                }
                if (title.isBlank()) { // 제목칸 빈 여부 체크
                    Toast.makeText(this, "제목을 입력해주세요", Toast.LENGTH_SHORT).show()
                } else if (content.isBlank()) { // 내용칸 빈 여부 체크
                    Toast.makeText(this, "내용을 입력해주세요", Toast.LENGTH_SHORT).show()
                } else { // 다 차있으면 데이터 입력

                    val db = DBHelper(this).writableDatabase
                    db.execSQL(
                        "insert into BOARD_TB(user_id,title,content,reg_date) values(?,?,?,?)",
                        arrayOf(userId, title, content, regDate)
                    )
                    db.close()
                    //            게시글목록으로 돌려보냄
                    val intent = Intent(this, BoardActivity::class.java)
                    intent.putExtra("id", userId)
                    startActivity(intent)

                }

            } // binding.submit.setOnClickListener
        }
    }
//    빈 영역터치시 키보드 내리기
    override fun onTouchEvent(event: MotionEvent): Boolean {
        val imm: InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
        return true
    }

}