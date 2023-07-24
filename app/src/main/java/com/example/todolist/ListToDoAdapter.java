package com.example.todolist;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ListToDoAdapter extends RecyclerView.Adapter<ListToDoAdapter.ViewHolder> {

    private final Context context;
    private final ArrayList<ToDo> list;
    private final ToDoDAO dao;

    public ListToDoAdapter(Context context, ArrayList<ToDo> list, ToDoDAO dao) {
        this.context = context;
        this.list = list;
        this.dao = dao;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = ((Activity) context).getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.listitem,parent,false);
        return new ViewHolder(view);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvTitle.setText(list.get(position).Title);
        holder.tvDate.setText(list.get(position).Date);
        if (list.get(position).Status == 1){
            holder.chkStatus.setChecked(true);
            holder.tvTitle.setPaintFlags(holder.tvTitle.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        } else {
            holder.chkStatus.setChecked(false);
            holder.tvTitle.setPaintFlags(holder.tvTitle.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
        }

        holder.btnUpdate.setOnClickListener(v -> {
            ToDo toDo = list.get(holder.getAdapterPosition());
            dialogUpdate(toDo);
        });

        holder.btnDetele.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Cảnh báo!");
            builder.setIcon(R.drawable.baseline_warning_24);
            builder.setMessage("Bạn có chắc muốn xoá công việc này không?");

            builder.setPositiveButton("Có", (dialog, which) -> {
                int id = list.get(holder.getAdapterPosition()).ID;
                boolean check = dao.remove(id);
                if (check) {
                    Toast.makeText(context, "Đã xoá", Toast.LENGTH_SHORT).show();
                    list.clear();
                    list.addAll(dao.getListToDo());
                    notifyItemRemoved(holder.getAdapterPosition());
                } else Toast.makeText(context, "Đã xảy ra lỗi", Toast.LENGTH_SHORT).show();
            });

            builder.setNegativeButton("Không", (dialog, which) -> {});

            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        });

        holder.chkStatus.setOnCheckedChangeListener((buttonView, isChecked) -> {
            int id = list.get(holder.getAdapterPosition()).ID;
            boolean check = dao.updateStatus(id,holder.chkStatus.isChecked());
            if (check) {
                Toast.makeText(context, "Đã đổi trạng thái", Toast.LENGTH_SHORT).show();
                list.clear();
                list.addAll(dao.getListToDo());
                notifyDataSetChanged();
            } else Toast.makeText(context, "Đã xảy ra lỗi", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvTitle, tvDate;
        public CheckBox chkStatus;
        public ImageButton btnUpdate, btnDetele;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvDate = itemView.findViewById(R.id.tv_date);
            chkStatus = itemView.findViewById(R.id.chk_status);
            btnUpdate = itemView.findViewById(R.id.btn_update);
            btnDetele = itemView.findViewById(R.id.btn_delete);
        }
    }

    public void dialogUpdate(ToDo toDoUpdate){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.updatelayout, null);
        builder.setView(view);
        AlertDialog dialog = builder.create();

        EditText edtTitle = view.findViewById(R.id.txt_up_title),
                edtComt = view.findViewById(R.id.txt_up_content),
                edtDate = view.findViewById(R.id.txt_up_date),
                edtType = view.findViewById(R.id.txt_up_type);

        Button btnUpdate = view.findViewById(R.id.btn_up_update),
                btnCancel = view.findViewById(R.id.btn_up_cancel);

        edtTitle.setText(toDoUpdate.Title);
        edtComt.setText(toDoUpdate.Comt);
        edtDate.setText(toDoUpdate.Date);
        edtType.setText(toDoUpdate.Type);

        edtType.setOnClickListener(v -> {
            String[] doKho = {"Khó", "Trung bình", "Dẽ"};

            AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
            builder1.setTitle("Chọn mức độ khó của công việc");
            builder1.setItems(doKho, (dialog1, which) -> edtType.setText(doKho[which]));

            AlertDialog alertDialog = builder1.create();
            alertDialog.show();
        });

        btnUpdate.setOnClickListener(v -> {
            toDoUpdate.setTitle(edtTitle.getText().toString());
            toDoUpdate.setComt(edtComt.getText().toString());
            toDoUpdate.setDate(edtDate.getText().toString());
            toDoUpdate.setType(edtType.getText().toString());
            boolean check = dao.update(toDoUpdate);
            if (check) {
                Toast.makeText(context, "Đã thay đổi công việc", Toast.LENGTH_SHORT).show();
                list.clear();
                list.addAll(dao.getListToDo());
                notifyDataSetChanged();
                dialog.dismiss();
            } else Toast.makeText(context, "Đã xảy ra lỗi", Toast.LENGTH_SHORT).show();
        });

        btnCancel.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }
}
