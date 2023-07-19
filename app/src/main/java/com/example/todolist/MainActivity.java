package com.example.todolist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Context context = MainActivity.this;

    DBHelper dbHelper = new DBHelper(context);

    ToDoDAO dao = new ToDoDAO(context);

    ArrayList<ToDo> list = new ArrayList<>();

    ListToDoAdapter adapter;

    void refresh(RecyclerView recyclerView){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        list.clear();
        list.addAll(dao.getListToDo());
        adapter = new ListToDoAdapter(context, list, dao);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText edtTitle = findViewById(R.id.title),
                edtCont = findViewById(R.id.txt_content),
                edtDate = findViewById(R.id.txt_date),
                edtType = findViewById(R.id.txt_type);

        Button btnAdd = findViewById(R.id.btn_add);

        RecyclerView recyclerView = findViewById(R.id.rcl_lst_todo);

        refresh(recyclerView);

        btnAdd.setOnClickListener(v -> {
            int iD=list.size()+1;
            dao.add(new ToDo(iD, edtTitle.getText().toString(),
                    edtCont.getText().toString(),
                    edtDate.getText().toString(),
                    edtType.getText().toString(),
                    0));
            refresh(recyclerView);
        });
    }
}