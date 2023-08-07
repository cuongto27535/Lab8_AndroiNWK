package com.example.lab8_androinwk.Bai1_Bai2;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.lab8_androinwk.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

public class Lab8_TodoList extends AppCompatActivity {
    private FloatingActionButton fabAdd;
    private RecyclerView recyclerView;
    private Firebase_Todo firebase_todo;
    private FirebaseAuth auth;
    private String author;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab8_todo_list);
        getSupportActionBar().setTitle("Lab 8 - Todo List");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        firebase_todo = new Firebase_Todo(this);
        auth = FirebaseAuth.getInstance();
        author = auth.getCurrentUser().getEmail();
        fabAdd = (FloatingActionButton) findViewById(R.id.L8Todo_fabAdd);
        recyclerView = (RecyclerView) findViewById(R.id.L8Todo_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        firebase_todo.getTodoList(recyclerView);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogAddTodo();
            }
        });
    }

    private void dialogAddTodo (){
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_add_todo);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        TextInputLayout tilTitle = (TextInputLayout) dialog.findViewById(R.id.dAddTodo_tilTitle);
        EditText etTitle = (EditText) dialog.findViewById(R.id.dAddTodo_etTitle);
        Button btnSubmit = (Button) dialog.findViewById(R.id.dAddTodo_btnSubmit);
        Button btnCancel = (Button) dialog.findViewById(R.id.dAddTodo_btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = etTitle.getText().toString();
                if (title.isEmpty()){
                    tilTitle.setErrorEnabled(true);
                    tilTitle.setError("Title can not be empty");
                }else {
                    Todo todo = new Todo();
                    todo.setAuthor(author);
                    todo.setDone(false);
                    todo.setTitle(title);
                    firebase_todo.addTodo(todo);
                    dialog.dismiss();
                }
            }
        });
        dialog.show();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            startActivity(new Intent(Lab8_TodoList.this, Lab8_Main.class));
        }
        return super.onOptionsItemSelected(item);
    }
}