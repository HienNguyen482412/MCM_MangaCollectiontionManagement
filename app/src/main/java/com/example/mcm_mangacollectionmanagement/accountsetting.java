package com.example.mcm_mangacollectionmanagement;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Patterns;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import MCMClass.AlertClass;
import MCMClass.User;
import database.UserDatabase;

public class accountsetting extends BaseActivity {
    LinearLayout imgBackground;
    ImageView imgAvatar;
    Button btnChooseAvatar, btnChooseBackground, btnAccountSave, btnAccountRefreh;
    EditText edtUsername, edtPassword, edtGmail;
    ActivityResultLauncher<Intent> activityResultLauncher;
    UserDatabase database;
    boolean avatar = true;
    AlertClass alert;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        getLayoutInflater().inflate(R.layout.activity_accountsetting,findViewById(R.id.frameContent));
        imgAvatar = findViewById(R.id.imgAccountAvatar);
        imgBackground = findViewById(R.id.imgAccountBackground);
        btnChooseAvatar = findViewById(R.id.btnChooseAvatar);
        btnChooseBackground = findViewById(R.id.btnChooseBackground);
        btnAccountSave = findViewById(R.id.btnAccountSave);
        btnAccountRefreh = findViewById(R.id.btnAccountRefresh);
        edtUsername = findViewById(R.id.edtAccountUsername);
        edtPassword = findViewById(R.id.edtAccountPassword);
        edtGmail = findViewById(R.id.edtAccountGmail);
        database = new UserDatabase(this);
        showAccountInformation();
        alert = new AlertClass(this);
        registerResult();

        btnChooseAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                avatar = true;
                pickImage();
            }
        });
        btnChooseBackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                avatar = false;
                pickImage();
            }
        });
        btnAccountSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //trường hợp người dùng chỉ lưu một ảnh hoặc không lưu ảnh (sau khi xóa tài khoản) ==> bug
                BitmapDrawable drawable1 = (BitmapDrawable) imgAvatar.getDrawable();
                Bitmap bitmap1 = drawable1.getBitmap();
                ByteArrayOutputStream byteArray1 = new ByteArrayOutputStream();
                bitmap1.compress(Bitmap.CompressFormat.PNG, 100, byteArray1);
                byte[] avatar = byteArray1.toByteArray();

                BitmapDrawable drawable2 = (BitmapDrawable) imgBackground.getBackground();
                Bitmap bitmap2 = drawable2.getBitmap();
                ByteArrayOutputStream byteArray2 = new ByteArrayOutputStream();
                bitmap2.compress(Bitmap.CompressFormat.PNG, 100, byteArray2);
                byte[] background = byteArray2.toByteArray();
                
                Toast.makeText(accountsetting.this, edtGmail.getText().toString(), Toast.LENGTH_LONG);
                if (!edtPassword.getText().toString().isEmpty() && !edtUsername.getText().toString().isEmpty() && !edtGmail.getText().toString().isEmpty()){
                    if (Patterns.EMAIL_ADDRESS.matcher(edtGmail.getText().toString().trim()).matches()){
                        if (database.editAccount(new User (background,avatar, edtGmail.getText().toString().trim(), edtUsername.getText().toString().trim(), edtPassword.getText().toString().trim() ))){
                            showAccountInformation();
                            showUserInformation();
                            alert.showSimpleAlerDialog("Thông báo","Đã lưu thông tin", R.drawable.success);
                        }


                    }
                    else {
                        alert.showSimpleAlerDialog("Cảnh báo","Gmail không hợp lệ",R.drawable.warning);
                    }
                }
                else {
                    alert.showSimpleAlerDialog("Cảnh báo","Không được bỏ trống thông tin",R.drawable.warning);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return false;
    }
    private void pickImage(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        activityResultLauncher.launch(intent);
    }
    private void registerResult() {
        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult o) {
                try {
                    Uri imageUri = o.getData().getData();
                    if (avatar){
                        imgAvatar.setImageURI(imageUri);
                    }
                    else {
                        try {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                            imgBackground.setBackground(new BitmapDrawable(getResources(), bitmap));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (Exception e) {
                    Toast.makeText(accountsetting.this, "No Image Selected", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    private void showAccountInformation(){
        User user = database.getAccount();
        edtUsername.setText(user.getName());
        edtPassword.setText(user.getPassword());
        edtGmail.setText(user.getGmail());
        if (user.getAvatar() != null){
            byte[] image = user.getAvatar();
            Bitmap img = BitmapFactory.decodeByteArray(image, 0, image.length);
            imgAvatar.setImageBitmap(img);
        }
        if (user.getBackground() != null){
            byte[] image = user.getBackground();
            Bitmap img = BitmapFactory.decodeByteArray(image, 0, image.length);
            Drawable drawable = new BitmapDrawable(getResources(),img);
            imgBackground.setBackground(drawable);
        }
    }

   
}