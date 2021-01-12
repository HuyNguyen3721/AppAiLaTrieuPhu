package com.example.appailatrieuphu.db

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Environment
import java.io.File
import java.io.FileOutputStream
import java.util.*

class DatabaseManager(c: Context) {
    companion object {
        val DB_Name = "ailatrieuphu"
    }

    private val context: Context = c
    private var database: SQLiteDatabase? = null

    init {
        coppyDatabase()
    }
     fun coppyDatabase() {
        val inputDatabase = context.assets.open("database"+File.separator+ DB_Name)
        // tao duong dan
        val path = Environment.getDataDirectory().path +
                File.separator + "data" +
                File.separator + context.packageName +
                File.separator + "db"
        // tao file theo đường dẫn trên , nếu tồn tại thì tạo , nếu không thì thôi . Dùng  mkdir
        File(path).mkdir()
        // full path tới data base
        val fullpath = path + File.separator + DB_Name
        // check xem đã tồn tại file này chưa , chưa thì tạo , rồi thì return dừng
        if (File(fullpath).exists()) {
            return
        }
        val outputData = FileOutputStream(fullpath)
        // tạo mảng byet , size 1024 ( define)
        val b = ByteArray(1024)
        var length  = inputDatabase.read(b)
        // đọpc mảng byte , còn đọc đc thì length (>=0) viêt vao file (fullpath) , hết thì < 0 ->  thôi dừng
        while (length >= 0 ){
            outputData.write(b,0 , length)
            length = inputDatabase.read(b)

        }
        // đóng file
            inputDatabase.close()
            outputData.close()
    }

     fun openDatabse() {
        // open database khi chưa kết nối database hoặc khi database đã close
        if(database ==  null  || !database!!.isOpen){
            // tao duong dan
            val path = Environment.getDataDirectory().path +
                    File.separator + "data" +
                    File.separator + context.packageName +
                    File.separator + "db"
            // full path tới data base
            val fullpath = path + File.separator + DB_Name
            database =  SQLiteDatabase.openDatabase(fullpath,null,SQLiteDatabase.OPEN_READWRITE)
        }
    }

     fun closeDatabase() {
        if(database !=  null || database!!.isOpen){
            database?.close()
            database =  null
        }
    }
// xử lí casdc câu lệnh insert , create , update ,...
     fun queryData(sql: String) {
         database?.execSQL(sql)
    }
// xử lí các câu lệnh select , ...
     fun getData(sql: String) : Cursor {
        return database!!.rawQuery(sql,null)
    }
}