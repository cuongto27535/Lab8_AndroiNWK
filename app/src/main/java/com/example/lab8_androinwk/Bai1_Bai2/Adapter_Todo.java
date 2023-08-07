package com.example.lab8_androinwk.Bai1_Bai2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.lab8_androinwk.R;

import java.util.List;

public class Adapter_Todo extends RecyclerView.Adapter<Adapter_Todo.ViewHolder> {
    private Context context;
    private List<Todo> todoList;
    private Firebase_Todo firebase_todo;

    public Adapter_Todo(Context context, List<Todo> todoList) {
        this.context = context;
        this.todoList = todoList;
        firebase_todo = new Firebase_Todo(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row_todo,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Todo todo = todoList.get(position);
        holder.tvTitle.setText(todo.getTitle());
        if (todo.isDone()){
            holder.imgToggleCheck.setImageResource(R.drawable.check);
        }else {
            holder.imgToggleCheck.setImageResource(R.drawable.uncheck);
        }
        holder.imgToggleCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebase_todo.toggleCheck(todo);
            }
        });

        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebase_todo.deleteTodo(todo);
            }
        });
    }

    @Override
    public int getItemCount() {
        return todoList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvTitle;
        ImageView imgToggleCheck, imgDelete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.rowTodo_tvTitle);
            imgToggleCheck = (ImageView) itemView.findViewById(R.id.rowTodo_imgToggleCheck);
            imgDelete = (ImageView) itemView.findViewById(R.id.rowTodo_imgDelete);
        }
    }
}
