package com.example.lab8_androinwk.Bai1_Bai2;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.google.firebase.auth.FirebaseAuthException;

public class Lab8_SignIn extends AppCompatActivity {
    private TextInputLayout tilEmail, tilPassword;
    private EditText etEmail, etPassword;
    private Button btnSubmit, btnSignUp;
    private FirebaseAuth auth;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    @SuppressLint("CommitPrefEdits")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab8_sign_in);
        getSupportActionBar().setTitle("Lab 8 - Sign In");
        auth = FirebaseAuth.getInstance();
        sharedPreferences = this.getSharedPreferences("user",Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        tilEmail = (TextInputLayout) findViewById(R.id.L8SignIn_tilEmail);
        tilPassword = (TextInputLayout) findViewById(R.id.L8SignIn_tilPassword);
        etEmail = (EditText) findViewById(R.id.L8SignIn_etEmail);
        etPassword = (EditText) findViewById(R.id.L8SignIn_etPassword);
        btnSignUp = (Button) findViewById(R.id.L8SignIn_btnSignUp);
        btnSubmit = (Button) findViewById(R.id.L8SignIn_btnSubmit);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Lab8_SignIn.this, Lab8_SignUp.class));
            }
        });
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = etPassword.getText().toString();
                String email = etEmail.getText().toString();
                if (email.isEmpty() || password.isEmpty()) {
                    if (email.isEmpty()) {
                        showError(tilEmail, "An Email address must be provide ");
                    }
                    if (password.isEmpty()) {
                        showError(tilPassword, " Password must be provide");
                    }
                } else {
                    if (password.length() < 6) {
                        showError(tilPassword, "Password need at least 6 characters");
                    } else {
                        hideError(tilPassword);
                        signIn(email, password);
                    }
                }
            }
        });
    }



    private void signIn(String email, String password) {
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(
                                    Lab8_SignIn.this,
                                    "Login successfully",
                                    Toast.LENGTH_SHORT).show();
                            hideError(tilEmail);
                            hideError(tilPassword);
                            editor.putString("email",email);
                            editor.putString("password",password);
                            editor.apply();
                            startActivity(new Intent(Lab8_SignIn.this, Lab8_Main.class));
                        } else {
                            String errorCode = ((FirebaseAuthException) task.getException()).getErrorCode();
                            switch (errorCode) {
                                case "ERROR_USER_NOT_FOUND":
                                    showError(tilEmail, "User not found with this email");
                                    hideError(tilPassword);
                                    break;
                                case "ERROR_WRONG_PASSWORD":
                                    showError(tilPassword, "The password is invalid");
                                    hideError(tilEmail);
                                    break;
                                case "ERROR_INVALID_EMAIL":
                                    showError(tilEmail,"The email address is badly formatted.");
                                    hideError(tilPassword);
                                    break;
                            }
                        }
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
}