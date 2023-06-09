package com.example.troisapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

public class Login extends AppCompatActivity {
    private EditText et_email, et_password;
    private Button btnlogin;
    private FirebaseAuth auth;
    private String getEmail, getPassword;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        et_email = findViewById(R.id.editEmail);
        et_password = findViewById(R.id.editPassword);
        btnlogin = findViewById(R.id.button_user);
        auth = FirebaseAuth.getInstance();

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Perintah tombol register
                cekDataUser();

            }
        });

    }

    boolean isEmail(EditText text) {
        CharSequence et_email = text.getText().toString();
        return (!TextUtils.isEmpty(et_email) && Patterns.EMAIL_ADDRESS.matcher(et_email).matches());
    }

    private void cekDataUser() {
        //mendapatkan data inputan user
        getEmail = et_email.getText().toString();
        getPassword = et_password.getText().toString();

        //Untuk mengecek apakah edittext email dan password kosong
        if (TextUtils.isEmpty(getEmail)) {
            Toast.makeText(this, "Email tidak boleh kosong", Toast.LENGTH_SHORT).show();

        } else if (isEmail(et_email) == false) {
            Toast.makeText(this, "Email tidak valid", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(getPassword)) {
            Toast.makeText(this, "Password tidak boleh kosong", Toast.LENGTH_SHORT).show();
        } else if (getPassword.length() < 6) {
            Toast.makeText(this, "Password Terlalu Pendek, minimal 6 karakter", Toast.LENGTH_SHORT).show();

        } else {
            createUserAccount();
        }
    }


    private void createUserAccount() {
        auth.createUserWithEmailAndPassword(getEmail, getPassword)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull final Task<AuthResult> task) {
                        Pengguna user = new Pengguna(getEmail, getPassword);
                        FirebaseDatabase.getInstance().getReference("Pengguna")
                                .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                        //Cek status Keberhasilan saat mendaftarkan email dan sandi baru
                                        if (task.isSuccessful()) {
                                            auth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        Toast.makeText(Login.this, "Login Berhasil", Toast.LENGTH_SHORT).show();
                                                        startActivity(new Intent(Login.this, Category.class));
                                                        finish();
                                                    } else {
                                                        Toast.makeText(Login.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                                    }

                                                }
                                            });
                                        }
                                    }
                                });
                    }
                });
    }
}