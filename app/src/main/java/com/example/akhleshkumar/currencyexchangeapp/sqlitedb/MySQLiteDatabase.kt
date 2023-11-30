package com.example.akhleshkumar.currencyexchangeapp.sqlitedb

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast

class MySQLiteDatabase(private var context: Context) {

    val dataBaseName = "Currency_Database"
    val version = 1
    private val tableName = "Currency_List"
    private val id = "Id"
    private val list = "List_Currency"
     val createTable = "CREATE TABLE $tableName($id INTEGER PRIMARY KEY AUTOINCREMENT,$list TEXT)"

    private val mySqliteOpenHelper = MySqliteOpenHelper(context)
    val myDatabase = mySqliteOpenHelper.writableDatabase


    fun saveList(stringList :List<String>){

        val myValues: ContentValues = ContentValues()
        var cursor : Long = 0
        for (i in stringList){
            myValues.run {this.put(list,i)}
           cursor = myDatabase.insert(tableName,null,myValues)
        }


        if (cursor > 0){
            Toast.makeText(context, "Saved Successfully", Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show()
        }
    }

    fun fetchList(): ArrayList<String>{
        val myList = ArrayList<String>()
        val cursor = myDatabase.rawQuery("SELECT * FROM $tableName",null)
        if (cursor.count > 0){
            cursor.moveToFirst()
            do {
                val dataFromList = cursor.getString(1)
                myList.add(dataFromList)
            }while (cursor.moveToNext())
        }
        return myList
    }

    inner class MySqliteOpenHelper(private val context: Context) : SQLiteOpenHelper(context,dataBaseName,null,version) {
        override fun onCreate(sqliteDb: SQLiteDatabase?) {
            sqliteDb!!.execSQL(createTable)
        }

        override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {

        }
    }
}