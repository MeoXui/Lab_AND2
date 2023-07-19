package com.example.todolist;

import static android.content.ContentValues.TAG;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class ToDoDAO {
    private final Context context;
    final DBHelper dbHelper;

    public ToDoDAO(Context context) {
        this.context = context;
        dbHelper = new DBHelper(context);
    }

    public ArrayList<ToDo> getListToDo(){
        ArrayList<ToDo> list = new ArrayList<>();
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        database.beginTransaction();
        try{
            Cursor cursor = database.rawQuery("Select * From TODO",null);
            if(cursor != null && cursor.getCount() > 0){
                cursor.moveToFirst();
                while (!cursor.isAfterLast()){
                    ToDo toDo = new ToDo(
                            cursor.getInt(0),
                            cursor.getString(1),
                            cursor.getString(2),
                            cursor.getString(3),
                            cursor.getString(4),
                            cursor.getInt(5));
                    list.add(toDo);
                    cursor.moveToNext();
                }
                database.setTransactionSuccessful();
            } else Toast.makeText(context, "Null Data!", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.e(TAG, "Get List ToDo: "+e);
        } finally {
            database.endTransaction();
        }
        return list;
    }

    public void add(@NonNull ToDo toDo){
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        database.beginTransaction();
        ContentValues values = new ContentValues();
        values.put("ID", toDo.ID);
        values.put("TITLE", toDo.Title);
//        Toast.makeText(context, toDo.Title, Toast.LENGTH_SHORT).show();
        values.put("CONTENT", toDo.Comt);
        values.put("DATE", toDo.Date);
        values.put("TYPE", toDo.Type);
        values.put("STATUS", toDo.Status);
        database.insert("TODO", null, values);
    }

    public boolean remove(int position){
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        int row = database.delete( "TODO", "id = ?", new String[]{String.valueOf(position)});
        return row != -1;
    }

    public boolean updateStatus(int position, boolean check){
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        int status = check?1:0;
        ContentValues values = new ContentValues();
        values.put("STATUS",status);
        int row = database.update( "TODO", values, "id = ?", new String[]{String.valueOf(position)});
        return row != -1;
    }
}
