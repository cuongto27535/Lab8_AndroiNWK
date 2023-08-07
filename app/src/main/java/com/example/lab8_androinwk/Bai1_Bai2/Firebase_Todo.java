package com.example.lab8_androinwk.Bai1_Bai2;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Firebase_Todo {
    private Context context;
    private DatabaseReference db;

    public Firebase_Todo(Context context) {
        this.context = context;
        this.db = FirebaseDatabase.getInstance().getReference("todo");
    }

    public void addTodo(Todo todo){
        todo.setId(db.push().getKey());
        db.child(todo.getId()).setValue(todo, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                if (error == null){
                    toast("Add todo successfully");
                }else {
                    toast(error.getMessage());
                }
            }
        });
    }
    public void deleteTodo (Todo todo){
        db.child(todo.getId()).setValue(null);
    }
    public void getTodoList(RecyclerView recyclerView){
        List<Todo> todoList = new ArrayList<>();
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                todoList.clear();
                for (DataSnapshot ds: snapshot.getChildren()){
                    Todo todo = ds.getValue(Todo.class);
                    todoList.add(todo);
                    Log.d("todo",todo.toString());
                }
                Adapter_Todo adapter_todo = new Adapter_Todo(context,todoList);
                recyclerView.setAdapter(adapter_todo);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void toggleCheck(Todo todo){
        DatabaseReference todoRf = db.child(todo.getId()).child("done");
        todoRf.setValue(!todo.isDone());
    }
    private void toast(String msg){
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }
}
