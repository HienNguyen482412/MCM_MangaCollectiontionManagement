package com.example.mcm_mangacollectionmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import MCMClass.AlertClass;
import database.UserDatabase;

public class login extends AppCompatActivity {
    TextView txtRegister;
    TextView txtForgotPassword;
    Button btnLogin;
    EditText edtLoginUsername, edtLoginPassword;
    UserDatabase userDatabase;
    AlertClass alertClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        txtRegister = findViewById(R.id.txtRegister);
        txtForgotPassword = findViewById(R.id.txtForgotPassword);
        btnLogin = findViewById(R.id.btnLogin);
        edtLoginPassword = findViewById(R.id.edtLoginPassword);
        edtLoginUsername = findViewById(R.id.edtLoginUsername);
        userDatabase = new UserDatabase(this);
        alertClass = new AlertClass(this);
        txtRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(login.this, register.class);
                startActivity(intent);
            }
        });
        txtForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(login.this, forgotpassword.class);
                startActivity(intent);
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = edtLoginUsername.getText().toString().trim();
                String password = edtLoginPassword.getText().toString().trim();
                if (!username.isEmpty() && !password.isEmpty()) {
                    if (userDatabase.login(username, password) > 0) {
                        alertClass.showAlertDialog("Thông báo", "Đăng nhập thành công \n Chào mừng " + userDatabase.getAccount().getName() + " >.< ", R.drawable.success, false, new AlertClass.AlertDialogCallback() {
                            @Override
                            public void onResult(boolean isDone) {
                                Intent intent = new Intent(login.this, home.class);
                                startActivity(intent);
                            }
                        });

                    } else {
                        alertClass.showSimpleAlerDialog("Thông báo", "Đăng nhập không thành công", R.drawable.fail);
                    }
                } else {
                    alertClass.showSimpleAlerDialog("Cảnh báo", "Hãy nhập đầy đủ thông tin", R.drawable.warning);
                }

            }
        });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        edtLoginPassword.setText("");
        edtLoginUsername.setText("");
        edtLoginUsername.requestFocus();
    }
}