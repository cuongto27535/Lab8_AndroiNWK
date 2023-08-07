package com.example.lab8_androinwk.Bai1_Bai2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.lab8_androinwk.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Lab8_Profile extends AppCompatActivity {
    private FirebaseAuth auth;
    private TextInputLayout tilEmail, tilPassword;
    private EditText etEmail, etPassword;
    private Button btnChangeEmail, btnChangePassword, btnResetPassword;
    private Button btnDeleteUser, btnSignOut;
    private TextView tvCurrentEmail;
    private FirebaseUser currentUser;
    private SharedPreferences sharedPreferences;
    private String emailSP, passwordSP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab8_profile);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Lab 8 - Profile");
        auth = FirebaseAuth.getInstance();
        currentUser = auth.getCurrentUser();
        tilEmail = (TextInputLayout) findViewById(R.id.L8Profile_tilEmail);
        tilPassword = (TextInputLayout) findViewById(R.id.L8Profile_tilPassword);
        etEmail = (EditText) findViewById(R.id.L8Profile_etEmail);
        etPassword = (EditText) findViewById(R.id.L8Profile_etPassword);
        btnChangeEmail = (Button) findViewById(R.id.L8Profile_btnChangeEmail);
        btnChangePassword = (Button) findViewById(R.id.L8Profile_btnChangePassword);
        btnResetPassword = (Button) findViewById(R.id.L8Profile_btnResetPassword);
        btnDeleteUser = (Button) findViewById(R.id.L8Profile_btnDeleteUser);
        btnSignOut = (Button) findViewById(R.id.L8Profile_btnSignOut);
        tvCurrentEmail = (TextView) findViewById(R.id.L8Profile_tvCurrentEmail);
        sharedPreferences = this.getSharedPreferences("user", Context.MODE_PRIVATE);
        emailSP = sharedPreferences.getString("email", "Email");
        passwordSP = sharedPreferences.getString("password", "password");
        tvCurrentEmail.setText("Current Email: " + emailSP);
        reAuth();

    }

    public void reAuth() {
        AuthCredential authCredential = EmailAuthProvider.getCredential(emailSP, passwordSP);
        currentUser.reauthenticate(authCredential)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d("result", "Re authenticate successfully");
                            handleClick();
                        }
                    }
                });
    }

    private void handleClick() {
        btnChangeEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideError(tilPassword);
                String email = etEmail.getText().toString();
                if (email.isEmpty()) {
                    showError(tilEmail, "An Email address is provide");
                } else {
                    changeEmail(email);
                }
            }
        });

        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideError(tilEmail);
                if (etPassword.getText().toString().isEmpty()) {
                    showError(tilPassword, "Password is provide");
                } else {
                    changePassword(etPassword.getText().toString());
                }
            }
        });

        btnResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPassword();
            }
        });

        btnDeleteUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteUser();
            }
        });

        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Lab8_Profile.this, Lab8_SignIn.class));
            }
        });
    }

    private void changeEmail(String email) {
        currentUser.updateEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(Lab8_Profile.this, "Your email was updated", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(Lab8_Profile.this, Lab8_SignIn.class));
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        showError(tilEmail, e.getMessage());
                    }
                });
    }

    private void changePassword(String password) {
        currentUser.updatePassword(password)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(Lab8_Profile.this, "Your password was updated", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(Lab8_Profile.this, Lab8_SignIn.class));
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        showError(tilPassword, e.getMessage());
                    }
                });
    }

    private void resetPassword() {
        auth.sendPasswordResetEmail(emailSP)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(Lab8_Profile.this, "Email send.", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Lab8_Profile.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void deleteUser() {
        currentUser.delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(Lab8_Profile.this, "User account deleted.", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(Lab8_Profile.this, Lab8_SignIn.class));
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