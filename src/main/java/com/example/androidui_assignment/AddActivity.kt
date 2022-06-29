package com.example.androidui_assignment

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.example.androidui_assignment.databinding.ActivityAddBinding

// 목표 추가할 엑티비티
class AddActivity : AppCompatActivity() {
    lateinit var binding: ActivityAddBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_add,menu)
        return super.onCreateOptionsMenu(menu)

    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_add_save->{
                val inputData = binding.addEditView.text.toString()

                val intent = intent
                var userId = intent.getStringExtra("id")
                if(inputData.isBlank()){
                    setResult(Activity.RESULT_CANCELED,intent)
                } else {
                    val db = DBHelper(this).writableDatabase
                    var count = 0
                    val dupId = db.rawQuery(" select count(*) from USERS_TB where user_id = ?", arrayOf(userId))
                    while (dupId.moveToNext()) {
                        Log.d("myLog", "dupId ${dupId.getInt(0)}")
                        count = dupId.getInt(0)
                    }
//                    목표를 처음 등록하는 사람이면 새로 항목을 만들어준다.
                    if (count == 0 ){
                        db.execSQL("insert into USERS_TB(user_id,successYn) values(?,?)", arrayOf<String?>(userId,"N"))
                    }

                    db.execSQL("insert into DOLIST_TB(user_id,todo,successYn) values(?,?,?)", arrayOf<String?>(userId,inputData,"N"))
                    db.close()
                    intent.putExtra("id",userId)
                    setResult(Activity.RESULT_OK,intent)
                }
                finish()
                true
            } else -> true
        }
        return super.onOptionsItemSelected(item)
    }
}