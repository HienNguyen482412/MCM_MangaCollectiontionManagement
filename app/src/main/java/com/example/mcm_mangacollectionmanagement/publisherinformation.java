package com.example.mcm_mangacollectionmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import MCMClass.AlertClass;
import MCMClass.Author;
import MCMClass.Publisher;
import database.PublisherDatabase;

public class publisherinformation extends BaseActivity {
    EditText edtPublisherID, edtPublisherName, edtPublisherPhone, edtPublisherAddress, edtPublisherNote;
    Spinner spinnerPublisherStatus;
    ImageButton btnPublisherAdd, btnPublisherEdit, btnPublisherDelete, btnPublisherRefresh;
    String[] statusList = {"Hoạt động", "Tạm dừng", "Không hoạt động"};
    ArrayAdapter<String> spinnerAdapter;
    PublisherDatabase database;
    AlertClass alert;
    String id, name, phone, address, status, note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        getLayoutInflater().inflate(R.layout.activity_publisherinformation, findViewById(R.id.frameContent));
        edtPublisherID = findViewById(R.id.edtPublisherID);
        edtPublisherName = findViewById(R.id.edtPublisherName);
        edtPublisherPhone = findViewById(R.id.edtPublisherPhone);
        edtPublisherAddress = findViewById(R.id.edtPublisherAddress);
        edtPublisherNote = findViewById(R.id.edtPublisherNote);
        btnPublisherAdd = findViewById(R.id.btnPublisherAdd);
        btnPublisherEdit = findViewById(R.id.btnPublisherEdit);
        btnPublisherDelete = findViewById(R.id.btnPublisherDelete);
        btnPublisherRefresh = findViewById(R.id.btnPublisherRefresh);
        spinnerPublisherStatus = findViewById(R.id.spinnerPublisherStatus);
        spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, statusList);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spinnerPublisherStatus.setAdapter(spinnerAdapter);
        database = new PublisherDatabase(this);
        alert = new AlertClass(this);
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("publisherID")){
            Publisher publisher = database.getPublisher(intent.getStringExtra("publisherID"));
            edtPublisherID.setText(publisher.getId());
            edtPublisherName.setText(publisher.getName());
            for (int i = 0; i < statusList.length;i++){
                if (statusList[i].equals(publisher.getStatus().trim())){
                    spinnerPublisherStatus.setSelection(i);
                }
            }
            edtPublisherPhone.setText(publisher.getPhoneNumber());
            edtPublisherAddress.setText(publisher.getAddress());
            edtPublisherNote.setText(publisher.getNote());
        }
        else {
            refresh();
        }
        spinnerPublisherStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                status = statusList[i].trim();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                status = statusList[0].trim();
            }
        });
        disableEditText(edtPublisherID);



        btnPublisherAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getValues();
                if (checkData()){
                    if(database.addPublisher(new Publisher(id, name, status, phone,address,note))) {
                        alert.showAlertDialog("Thông báo", "Thêm thông tin nhà xuất bản thành công", R.drawable.success, false, new AlertClass.AlertDialogCallback() {
                            @Override
                            public void onResult(boolean isDone) {
                                if (isDone) {
                                    refresh();
                                }
                            }
                        });
                    }
                    else {
                        alert.showSimpleAlerDialog("Thông báo", "Thêm nhà xuất bản không thành công", R.drawable.fail);
                    }
                }
            }
        });

        btnPublisherEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getValues();
                if (checkData()){
                    if(database.updatePublisher(new Publisher(id, name, status, phone,address,note))) {
                        alert.showAlertDialog("Thông báo", "Sửa thông tin nhà xuất bản thành công", R.drawable.success, false,new AlertClass.AlertDialogCallback() {
                            @Override
                            public void onResult(boolean isDone) {
                                if (isDone){
                                    finish();
                                }
                            }
                        });
                    }
                    else {
                        alert.showSimpleAlerDialog("Thông báo", "Sửa nhà xuất bản không thành công", R.drawable.fail);
                    }
                }
            }
        });
        btnPublisherDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getValues();
                alert.showAlertDialog("Thông báo", "Bạn có chắc chắn muốn xóa thông tin này không ?", R.drawable.warning, true, new AlertClass.AlertDialogCallback() {
                    @Override
                    public void onResult(boolean isDone) {
                        if (isDone){
                            if (database.deletePublisher(id)){
                                finish();
                            }
                            else {
                                alert.showSimpleAlerDialog("Thông báo", "Xóa nhà xuất bản không thành công", R.drawable.fail);
                            }

                        }
                    }
                });
            }
        });
        btnPublisherRefresh.setOnClickListener(new View.OnClickListener() {
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



    public void getValues() {
        id = edtPublisherID.getText().toString().trim();
        name = edtPublisherName.getText().toString().trim();
        phone = edtPublisherPhone.getText().toString().trim();
        address = edtPublisherAddress.getText().toString().trim();
        note = edtPublisherNote.getText().toString().trim();
    }

    public boolean checkData() {
        boolean check = false;
        if (!name.isEmpty()) {
            if (!phone.isEmpty()) {
                if (!Patterns.PHONE.matcher(phone).matches()) {
                    alert.showSimpleAlerDialog("Cảnh báo", "Số điện thoại không hợp lệ", R.drawable.warning);
                    return false;
                }
            }
            check = true;
        } else {
            alert.showSimpleAlerDialog("Cảnh báo", "Không được bỏ trống tên nhà xuất bản", R.drawable.warning);
        }
        return check;
    }

    public void refresh() {
        edtPublisherID.setText(database.createID());
        edtPublisherName.setText("");
        edtPublisherName.requestFocus();
        spinnerPublisherStatus.setSelection(0);
        edtPublisherPhone.setText("");
        edtPublisherAddress.setText("");
        edtPublisherNote.setText("");
    }
}