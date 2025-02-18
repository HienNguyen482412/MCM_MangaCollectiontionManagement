package com.example.mcm_mangacollectionmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.time.Year;

import MCMClass.AlertClass;
import MCMClass.Author;
import database.AuthorDatabase;

public class authorinformation extends BaseActivity {
    EditText edtAuthorID, edtAuthorName, edtAuthorNationality, edtAuthorYear, edtAuthorHometown, edtAuthorNote;
    ImageButton btnAuthorAdd, btnAuthorEdit, btnAuthorDelete, btnAuthorRefresh;
    AuthorDatabase database;
    AlertClass alert;
    String id, name, nationality, hometown, note;
    Integer year;
    boolean isNumeric = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        getLayoutInflater().inflate(R.layout.activity_authorinformation, findViewById(R.id.frameContent));
        edtAuthorID = findViewById(R.id.edtAuthorID);
        edtAuthorName = findViewById(R.id.edtAuthorName);
        edtAuthorNationality = findViewById(R.id.edtAuthorNationality);
        edtAuthorYear = findViewById(R.id.edtAuthorYear);
        edtAuthorHometown = findViewById(R.id.edtAuthorHometown);
        edtAuthorNote = findViewById(R.id.edtAuthorNote);
        btnAuthorAdd = findViewById(R.id.btnAuthorAdd);
        btnAuthorEdit = findViewById(R.id.btnAuthorEdit);
        btnAuthorDelete = findViewById(R.id.btnAuthorDelete);
        btnAuthorRefresh = findViewById(R.id.btnAuthorRefresh);
        database = new AuthorDatabase(this);
        alert = new AlertClass(this);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("authorID")){
            Author author = database.getAuthor(intent.getStringExtra("authorID"));
            edtAuthorID.setText(author.getId());
            edtAuthorName.setText(author.getName());
            edtAuthorNationality.setText(author.getNationality());
            edtAuthorYear.setText(String.valueOf(author.getYearOfBirth()));
            edtAuthorHometown.setText(author.getHomeTown());
            edtAuthorNote.setText(author.getNote());
        }
        else {
            refresh();
        }
        disableEditText(edtAuthorID);
        btnAuthorAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getValue();
                if (checkData()){

                    if (database.addAuthor(new Author(id,name,nationality,hometown,note,year))){
                        alert.showAlertDialog("Thông báo", "Thêm thông tin tác giả thành công", R.drawable.success, false, new AlertClass.AlertDialogCallback() {
                            @Override
                            public void onResult(boolean isDone) {
                                if (isDone) {
                                    refresh();
                                }
                            }
                        });
                    }
                    else {
                        alert.showSimpleAlerDialog("Thông báo", "Thêm thông tin tác giả không thành công", R.drawable.fail);
                    }
                }

            }
        });
        btnAuthorEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getValue();
                if (checkData()){

                    if (database.updateAuthor(new Author(id,name,nationality,hometown,note,year))){
                        alert.showAlertDialog("Thông báo", "Sửa thông tin tác giả thành công", R.drawable.success, false,new AlertClass.AlertDialogCallback() {
                            @Override
                            public void onResult(boolean isDone) {
                                if (isDone){
                                    finish();
                                }
                            }
                        });
                    }
                    else {
                        alert.showSimpleAlerDialog("Thông báo", "Sửa thông tin tác giả không thành công", R.drawable.fail);
                    }
                }
            }
        });
        btnAuthorDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getValue();
                alert.showAlertDialog("Thông báo", "Bạn có chắc chắn muốn xóa thông tin này không ?", R.drawable.warning, true, new AlertClass.AlertDialogCallback() {
                    @Override
                    public void onResult(boolean isDone) {
                        if (isDone){
                            if (database.deleteAuthor(id)){
                                finish();
                            }
                            else{
                                alert.showSimpleAlerDialog("Thông báo", "Xóa thông tin tác giả không thành công", R.drawable.fail);
                            }

                        }
                    }
                });
            }
        });
        btnAuthorRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                refresh();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return false;
    }

    public void refresh(){
        edtAuthorID.setText(database.createID());
        edtAuthorName.requestFocus();
        edtAuthorName.setText("");
        edtAuthorNationality.setText("");
        edtAuthorYear.setText("");
        edtAuthorHometown.setText("");
        edtAuthorNote.setText("");
    }
    public void getValue(){
        id = edtAuthorID.getText().toString().trim();
        name = edtAuthorName.getText().toString().trim();
        nationality = edtAuthorNationality.getText().toString().trim();
        try{
            year = Integer.parseInt(edtAuthorYear.getText().toString().trim());
            isNumeric = true;
        }
        catch (Exception e){
            isNumeric = false;
            alert.showSimpleAlerDialog("Cảnh báo", "Năm sinh không hợp lệ", R.drawable.warning);
        }
        hometown = edtAuthorHometown.getText().toString().trim();
        note = edtAuthorNote.getText().toString().trim();
    }
    public boolean checkData(){
        boolean check = false;
        if (!name.isEmpty() && !nationality.isEmpty()){
            if (isNumeric){
                if (TextUtils.isDigitsOnly(String.valueOf(year))){
                    check = true;
                    isNumeric = true;
                }
            }

        }
        else {
            alert.showSimpleAlerDialog("Cảnh báo", "Không thể bỏ trống tên, quốc tịch và năm sinh của tác giả", R.drawable.warning);
        }
        return check;
    }
}