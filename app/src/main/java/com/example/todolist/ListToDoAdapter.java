package com.example.todolist;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
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

        holder.btnDetele.setOnClickListener(v -> {
            int id = list.get(holder.getAdapterPosition()).ID;
            boolean check = dao.remove(id);
            if (check) {
                Toast.makeText(context, "Đã xoá", Toast.LENGTH_SHORT).show();
                list.clear();
                list.addAll(dao.getListToDo());
                notifyItemRemoved(holder.getAdapterPosition());
            } else Toast.makeText(context, "Đã xảy ra lỗi", Toast.LENGTH_SHORT).show();
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
}
