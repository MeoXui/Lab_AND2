package com.example.todolist;

import android.app.Activity;
import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ListToDoAdapter extends RecyclerView.Adapter<ListToDoAdapter.ViewHolder> {

    private final Context context;
    private ArrayList<ToDo> list;
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

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.txtTitle.setText(list.get(position).getTitle());
        holder.txtDate.setText(list.get(position).getDate());
        if (list.get(position).getStatus() == 1){
            holder.chkStatus.setChecked(true);
            holder.txtTitle.setPaintFlags(holder.txtTitle.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        } else {
            holder.chkStatus.setChecked(false);
            holder.txtTitle.setPaintFlags(holder.txtTitle.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
        }

        holder.btnDetele.setOnClickListener(v -> {
            int id = list.get(holder.getAdapterPosition()).getID();
            boolean check = dao.remove(id);
            if (check) {
                Toast.makeText(context, "Xoá thàng công", Toast.LENGTH_SHORT).show();
                list.clear();
                list = dao.getListToDo();
                notifyItemRemoved(holder.getAdapterPosition());
            } else Toast.makeText(context, "Xoá thất bại", Toast.LENGTH_SHORT).show();
        });

        holder.chkStatus.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int id = list.get(holder.getAdapterPosition()).getID();
                boolean check = dao.updateStatus(id,holder.chkStatus.isChecked());
                if (check) {
                    Toast.makeText(context, "Đã đổi trạng thái", Toast.LENGTH_SHORT).show();
                    list.clear();
                    list = dao.getListToDo();
                    notifyDataSetChanged();
                } else Toast.makeText(context, "Không đổi được trạng thái", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtTitle, txtDate;
        CheckBox chkStatus;
        ImageButton btnUpdate, btnDetele;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.txt_title);
            txtDate = itemView.findViewById(R.id.txt_date);
            chkStatus = itemView.findViewById(R.id.chk_status);
            btnUpdate = itemView.findViewById(R.id.btn_update);
            btnDetele = itemView.findViewById(R.id.btn_delete);
        }
    }
}

//public class ListToDoAdapter extends BaseAdapter {
//
//    ArrayList<ToDo> list = new ArrayList<>();
//
//    public ListToDoAdapter(ArrayList<ToDo> list) {
//        this.list = list;
//    }
//
//    @Override
//    public int getCount() {
//        return list.size();
//    }
//
//    @Override
//    public ToDo getItem(int position) {
//        return list.get(position);
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return position;
//    }
//    Holde holde = null;
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        if(convertView == null){
//            convertView = LayoutInflater.from(parent.getContext())
//                    .inflate(R.layout.listitem,parent,false);
//            holde.txtItem = convertView.findViewById(R.id.txt_item);
//            convertView.setTag(holde);
//        } else holde = (Holde) convertView.getTag();
//        holde.toDo = getItem(position);
//        holde.txtItem.setText(holde.toDo.toString());
//        return convertView;
//    }
//
//    private static class Holde {
//        TextView txtItem;
//        ToDo toDo;
//    }
//}
