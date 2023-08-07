package com.example.lab8_androinwk.Bai1_Bai2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lab8_androinwk.R;

public class Lab8_Main extends AppCompatActivity {
    private Button btnTodoList, btnProfile, btnSignOut;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab8_main);
        getSupportActionBar().setTitle("Lab 8 - Main");
        btnTodoList = (Button) findViewById(R.id.L8Main_btnTodoList);
        btnProfile = (Button) findViewById(R.id.L8Main_btnProfile);
        btnSignOut = (Button) findViewById(R.id.L8Main_btnSignOut);
        btnTodoList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Lab8_Main.this, Lab8_TodoList.class));
            }
        });
        btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Lab8_Main.this,Lab8_Profile.class));
            }
        });
        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Lab8_Main.this, Lab8_SignIn.class));
            }
        });
    }
}