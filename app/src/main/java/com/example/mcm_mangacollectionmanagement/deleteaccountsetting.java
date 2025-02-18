package com.example.mcm_mangacollectionmanagement;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import MCMClass.AlertClass;
import database.UserDatabase;

public class deleteaccountsetting extends BaseActivity {
    Button btnDeleteAccount;
    AlertClass alertClass;
    EditText edtPassword;
    UserDatabase database ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        getLayoutInflater().inflate(R.layout.activity_deleteaccountsetting, findViewById(R.id.frameContent));
        btnDeleteAccount = findViewById(R.id.btnDeleteAccount);
        edtPassword = findViewById(R.id.edtDeletePassword);
        alertClass = new AlertClass(this);
        database = new UserDatabase(this);
        btnDeleteAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               alertClass.showAlertDialog("Cảnh báo", "Bạn có chắc chắn muốn xóa tài khoản này không ?", R.drawable.warning, true, new AlertClass.AlertDialogCallback() {
                   @Override
                   public void onResult(boolean isDone) {
                       if (isDone){
                           if (edtPassword.getText().toString().trim().equals(database.getAccount().getPassword())){
                                database.deleteAccount(deleteaccountsetting.this);

                           }
                           else {
                               alertClass.showSimpleAlerDialog("Thông báo", "Mật khẩu không chính xác",R.drawable.fail);
                           }
                       }
                   }
               });

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return false;
    }
}