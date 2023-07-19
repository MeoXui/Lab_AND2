package com.example.todolist;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context) {
        super(context, "TodoDatabase", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("Create Table TODO(" +
                "ID integer primary key," +
                "TITLE text," +
                "CONTENT text," +
                "DATE text," +
                "TYPE text," +
                "STATUS integer)");

        db.execSQL("Insert Into TODO Values" +
                "(1, 'Học Java', 'Học Java cơ bản', '27022023', 'Bình thường', 1)," +
                "(2, 'Học React Native', 'Học React Native cơ bản', '24032023', 'Khó', 0)," +
                "(3, 'Học Kotlin', 'Học Kotlin cơ bản', '01042023', 'Dễ', 0)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(oldVersion != newVersion){
            db.execSQL("Drop Table If Exists TODO");
            onCreate(db);
        }
    }
}
