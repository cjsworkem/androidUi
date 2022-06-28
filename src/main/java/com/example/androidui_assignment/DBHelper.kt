package com.example.androidui_assignment

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context): SQLiteOpenHelper(context,"testdb",null,1) {
    override fun onCreate(db: SQLiteDatabase?) {
//        로그인에 필요한 테이블
        db?.execSQL("create table MEMBER_TB(" +
                " id integer primary key autoincrement, " +
                " user_id unique not null," +
                " password not null)")
//        할일칸에 사람 목록을 뛰워줄 테이블
        db?.execSQL("create table USERS_TB(" +
                " id integer primary key autoincrement, " +
                " user_id not null," +
                " successYn not null)")
//        세부정보를 뛰워줄 테이블
        db?.execSQL("create table DOLIST_TB(" +
                " id integer primary key autoincrement, " +
                " user_id not null," +
                " todo not null," +
                " successYn not null)")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }
}