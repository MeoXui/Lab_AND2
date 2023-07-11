package com.example.todolist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText edtTitle = findViewById(R.id.txt_title),
            edtCont = findViewById(R.id.txt_content),
            edtDate = findViewById(R.id.txt_date),
            edtType = findViewById(R.id.txt_type);

    Button btnAdd = findViewById(R.id.btn_add);

    RecyclerView recyclerView = findViewById(R.id.rcl_lst_todo);

    String sTitle = edtTitle.getText().toString(),
            sCont = edtCont.getText().toString(),
            sDate = edtDate.getText().toString(),
            sType = edtType.getText().toString();

    Context context = MainActivity.this;

    DBHelper dbHelper = new DBHelper(context);

    ToDoDAO dao = new ToDoDAO(context);

    ArrayList<ToDo> list;

    ListToDoAdapter adapter;

    void refresh(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        list = dao.getListToDo();
        adapter = new ListToDoAdapter(context, list, dao);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        refresh();

        btnAdd.setOnClickListener(v -> {
            int iD=0;
            while (ifExist(iD))iD++;
            dao.add(new ToDo(iD, sTitle, sCont, sDate, sType, 0));
            refresh();
        });
    }

    private boolean ifExist(int iD) {
        ArrayList<ToDo> listTodo = dao.getListToDo();
        for(ToDo toDo : listTodo)
            if(iD == toDo.getID()) return true;
        return false;
    }
}