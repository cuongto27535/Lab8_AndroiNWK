package com.example.lab8_androinwk.Bai1_Bai2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import com.example.lab8_androinwk.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Lab8_SignUp extends AppCompatActivity {
    private TextInputLayout tilEmail, tilPassword;
    private EditText etEmail, etPassword;
    private Button btnSubmit, btnSignIn;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab8_sign_up);
        getSupportActionBar().setTitle("Lab 8 - Sign Up");
        auth = FirebaseAuth.getInstance();
        tilEmail = (TextInputLayout) findViewById(R.id.L8SignUp_tilEmail);
        tilPassword = (TextInputLayout) findViewById(R.id.L8SignUp_tilPassword);
        etEmail = (EditText) findViewById(R.id.L8SignUp_etEmail);
        etPassword = (EditText) findViewById(R.id.L8SignUp_etPassword);
        btnSignIn = (Button) findViewById(R.id.L8SignUp_btnSignIn);
        btnSubmit = (Button) findViewById(R.id.L8SignUp_btnSubmit);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = etPassword.getText().toString();
                String email = etEmail.getText().toString();
                if (email.isEmpty() || password.isEmpty()) {
                    if (email.isEmpty()) {
                        showError(tilEmail, "Email is required");
                    }
                    if (password.isEmpty()) {
                        showError(tilPassword, "Email is required");
                    }
                } else {
                    if (password.length() < 6) {
                        showError(tilPassword, "Password need at least 6 characters");
                    } else {
                        hideError(tilPassword);
                        signUp(email, password);
                    }
                }
            }
        });
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Lab8_SignUp.this, Lab8_SignIn.class));
            }
        });
    }

    private void showError(TextInputLayout textInputLayout, String msg) {
        textInputLayout.setErrorEnabled(true);
        textInputLayout.setError(msg);
    }

    private void hideError(TextInputLayout textInputLayout) {
        textInputLayout.setErrorEnabled(false);
    }

    private void signUp(String email, String password) {
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(
                                    Lab8_SignUp.this,
                                    "Create user with email successfully",
                                    Toast.LENGTH_SHORT).show();
                            hideError(tilEmail);
                            startActivity(new Intent(Lab8_SignUp.this, Lab8_SignIn.class));
                        } else {
                            showError(tilEmail, task.getException().getMessage());
                        }
                    }
                });
    }
}