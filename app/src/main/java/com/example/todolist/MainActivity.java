package com.example.todolist;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class MainActivity extends AppCompatActivity {

    Context context = MainActivity.this;

    DBHelper dbHelper = new DBHelper(context);

    ToDoDAO dao = new ToDoDAO(context,dbHelper);

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

        edtType.setOnClickListener(v -> {
            String[] doKho = {"Khó", "Trung bình", "Dẽ"};

            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Chọn mức độ khó của công việc");
            builder.setItems(doKho, (dialog, which) -> edtType.setText(doKho[which]));
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        });

        Button btnAdd = findViewById(R.id.btn_add);

        RecyclerView recyclerView = findViewById(R.id.rcl_lst_todo);

        refresh(recyclerView);

        AtomicInteger iD = new AtomicInteger();
        btnAdd.setOnClickListener(v -> {
            boolean check = dao.add(new ToDo(iD.get(),
                    edtTitle.getText().toString(),
                    edtCont.getText().toString(),
                    edtDate.getText().toString(),
                    edtType.getText().toString(),
                    0));
            iD.getAndIncrement();
            if(check){
                refresh(recyclerView);
                Toast.makeText(context, "Đã thêm", Toast.LENGTH_SHORT).show();
                edtTitle.setText("");
                edtCont.setText("");
                edtDate.setText("");
                edtType.setText("");
            } else Toast.makeText(context, "Đã xảy ra lỗi", Toast.LENGTH_SHORT).show();
        });
    }
}