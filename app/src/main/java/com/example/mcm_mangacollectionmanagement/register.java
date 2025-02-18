package com.example.mcm_mangacollectionmanagement;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import MCMClass.AlertClass;
import MCMClass.User;
import database.UserDatabase;

public class register extends BaseActivity {
    ImageButton btnBack;
    EditText txtUsername, txtPassword_1, txtPassword_2, txtGmail;
    Button btnRegister, btnRefresh;
    UserDatabase userDatabase;
    AlertClass alertClass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        btnBack = findViewById(R.id.btnBack);
        txtUsername = findViewById(R.id.txtRegisterUsername);
        txtPassword_1 = findViewById(R.id.txtRegisterPassword_1);
        txtPassword_2 = findViewById(R.id.txtRegisterPassword_2);
        txtGmail = findViewById(R.id.txtRegisterGmail);
        btnRegister = findViewById(R.id.btnRegister);
        btnRefresh = findViewById(R.id.btnRegisterRefresh);
        userDatabase = new UserDatabase(this);
        alertClass = new AlertClass(this);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = txtUsername.getText().toString().trim().replaceAll("\\s+", "");
                String password_1 = txtPassword_1.getText().toString().trim().replaceAll("\\s+", "");
                String password_2 = txtPassword_2.getText().toString().trim().replaceAll("\\s+", "");
                String gmail = txtGmail.getText().toString().trim();
                if (!username.isEmpty() && !password_1.isEmpty()&& !password_2.isEmpty() && !gmail.isEmpty()){
                    if (password_1.equals(password_2)){
                        if (Patterns.EMAIL_ADDRESS.matcher(gmail).matches()){
                            if (userDatabase.checkExitsUser() == 0){
                               if (userDatabase.createAccount(new User(null, null,gmail,username, password_1))) {
                                  alertClass.showAlertDialog("Thông báo", "Đăng kí thành công", R.drawable.success, false, new AlertClass.AlertDialogCallback() {
                                      @Override
                                      public void onResult(boolean isDone) {
                                          if (isDone){
                                              finish();
                                          }
                                      }
                                  });
                               }
                               else {
                                   alertClass.showSimpleAlerDialog("Thông báo","Đăng kí không thành công",R.drawable.fail);
                               }
                            }
                            else {
                                alertClass.showSimpleAlerDialog("Cảnh báo","Không thể tạo tài khoản do có tài khoản khác đã tồn tại",R.drawable.warning);
                            }
                        }
                        else {
                            alertClass.showSimpleAlerDialog("Cảnh báo","Gmail không đúng định dạng",R.drawable.warning);
                        }
                    }
                    else {
                        alertClass.showSimpleAlerDialog("Cảnh báo","Mật khẩu xác nhận không chính xác",R.drawable.warning);
                    }
                }
                else {
                    alertClass.showSimpleAlerDialog("Cảnh báo","Yêu cầu nhập đẩy đủ thông tin",R.drawable.warning);
                }
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtUsername.requestFocus();
                txtUsername.setText("");
                txtPassword_1.setText("");
                txtPassword_2.setText("");
                txtGmail.setText("");
            }
        });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}