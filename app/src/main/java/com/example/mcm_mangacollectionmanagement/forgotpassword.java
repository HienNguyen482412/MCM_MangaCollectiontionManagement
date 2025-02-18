package com.example.mcm_mangacollectionmanagement;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.service.autofill.UserData;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputLayout;

import java.util.Random;

import MCMClass.AlertClass;
import MCMClass.EmailSender;
import MCMClass.User;
import database.UserDatabase;

public class forgotpassword extends AppCompatActivity {
    EditText edtUsername, edtPassword1, edtPassword2, edtCode;
    Button btnCreate, btnRefresh;
    AlertClass alert;
    UserDatabase database;
    ImageButton btnBack;
    TextInputLayout edtLayout;
    String code;
    User newUser;
    String username, pass1, pass2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_forgotpassword);
        btnBack = findViewById(R.id.btnBack);
        edtUsername = findViewById(R.id.edtFPUsername);
        edtPassword1 = findViewById(R.id.edtFPPassword1);
        edtPassword2 = findViewById(R.id.edtFPPasswrod2);
        edtCode = findViewById(R.id.edtFPConfirmedCode);
        btnCreate = findViewById(R.id.btnFPRegister);
        btnRefresh = findViewById(R.id.btnFPRefresh);
        edtLayout = findViewById(R.id.edtLayout);
        alert = new AlertClass(this);
        database = new UserDatabase(this);
        newUser = database.getAccount();
        code = "";
        edtLayout.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Random r = new Random();
                int intCode = r.nextInt(999999) + 100000;
                code = String.valueOf(intCode);
                EmailSender.sendEmail(newUser.getGmail().trim(), "Mã xác nhận", code);
            }
        });
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getData();
                if (checkData()) {
                    newUser.setName(username);
                    newUser.setPassword(pass1);
                    if (database.editAccount(newUser)) {
                        alert.showAlertDialog("Thông báo", "Tạo lại tài khoản thành công", R.drawable.warning, false, new AlertClass.AlertDialogCallback() {
                            @Override
                            public void onResult(boolean isDone) {
                                if (isDone) {
                                    finish();
                                }
                            }
                        });
                    } else {
                        alert.showSimpleAlerDialog("Thông báo", "Tạo lại tài khoản không thành công", R.drawable.warning);
                    }
                }

            }
        });
        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edtUsername.setText("");
                edtUsername.requestFocus();
                edtPassword1.setText("");
                edtPassword2.setText("");
                edtCode.setText("");
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public Boolean checkData() {
        if (!username.isEmpty() && !pass1.isEmpty() && !pass2.isEmpty() && !edtCode.getText().toString().isEmpty()) {
            if (pass1.equals(pass2)) {
                if (code.equals(edtCode.getText().toString().trim())) {
                    return true;
                } else {
                    alert.showSimpleAlerDialog("Cảnh báo", "Mã xác nhận không chính xác", R.drawable.warning);
                    return false;
                }
            } else {
                alert.showSimpleAlerDialog("Cảnh báo", "Mật khẩu xác nhận không chính xác", R.drawable.warning);
                return false;
            }
        } else {
            alert.showSimpleAlerDialog("Cảnh báo", "Không được bỏ trống thông tin", R.drawable.warning);
            return false;
        }

    }

    public void getData() {
        username = edtUsername.getText().toString().trim();
        pass1 = edtPassword1.getText().toString().trim();
        pass2 = edtPassword2.getText().toString().trim();
    }

}