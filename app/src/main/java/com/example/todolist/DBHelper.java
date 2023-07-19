package com.example.todolist;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context) {
        super(context, "TodoDatabase", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlCodeCreateTableTodo =
                "Create Table TODO(" +
                        "ID integer primary key autoincrement," +
                        "TITLE text," +
                        "CONTENT text," +
                        "DATE text," +
                        "TYPE text," +
                        "STATUS integer)";
        db.execSQL(sqlCodeCreateTableTodo);

        String sqlCodeInsertDataIntoTableTodo =
                "Insert Into TODO Values" +
                        "('Học Java', 'Học Java cơ bản', '27022023', 'Bình thường', 1)," +
                        "('Học React Native', 'Học React Native cơ bản', '24032023', 'Khó', 0)," +
                        "('Học Kotlin', 'Học Kotlin cơ bản', '01042023', 'Dễ', 0)";
        try {
            db.execSQL(sqlCodeInsertDataIntoTableTodo);
        } catch (Exception e){
            return;
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(oldVersion != newVersion){
            db.execSQL("Drop Table If Exists TODO");
            onCreate(db);
        }
    }
}
