package com.example.mcm_mangacollectionmanagement;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Calendar;

import MCMClass.AlertClass;
import MCMClass.Manga;
import database.MangaDatabase;

public class mangainformation extends BaseActivity {
    EditText edtCollectionID, edtMangaID, edtMangaName, edtPrice, edtNote;
    Spinner spinnerType, spinnerStatus;
    ImageButton btnAdd, btnEdit, btnDelete, btnRefresh;
    Button btnMangaRelease;
    String[] types = {"Thường", "Đặc biệt"};
    String[] status = {"Mới", "Cũ", "Hỏng 1 (Rách, ố hoặc bị côn trùng ở một vài trang)", "Hỏng 2 (rách, bị bẩn hoặc bị côn trùng hơn nửa quyển)", "Hỏng 3 (không thể sử dụng được)", "Đã đặt hàng", "Chưa có"};
    ArrayAdapter<String> typesAdapter, statusAdapter;
    MangaDatabase mangaDatabase;
    AlertClass alert;
    String collectionID;
    String id, statu, type, price, name, note, date;
    DatePickerDialog datePickerDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        getLayoutInflater().inflate(R.layout.activity_mangainformation, findViewById(R.id.frameContent));
        edtCollectionID = findViewById(R.id.edtMangaCollectionID);
        edtMangaID = findViewById(R.id.edtMangaID);
        edtMangaName = findViewById(R.id.edtMangaName);
        edtPrice = findViewById(R.id.edtMangaPrice);
        edtNote = findViewById(R.id.edtMangaNote);
        spinnerStatus = findViewById(R.id.spinnerMangaStatus);
        spinnerType = findViewById(R.id.spinnerMangaType);
        btnAdd = findViewById(R.id.btnMangaAdd);
        btnEdit = findViewById(R.id.btnMangaEdit);
        btnDelete = findViewById(R.id.btnMangaDelete);
        btnRefresh = findViewById(R.id.btnMangaRefresh);
        btnMangaRelease = findViewById(R.id.btnMangaRealeseDate);
        initDatePicker();
        typesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, types);
        typesAdapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spinnerType.setAdapter(typesAdapter);

        statusAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, status);
        statusAdapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spinnerStatus.setAdapter(statusAdapter);

        mangaDatabase = new MangaDatabase(this);
        alert = new AlertClass(this);
        btnMangaRelease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialog.show();
            }
        });
        spinnerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                type = types[i].trim();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                type = types[0].trim();
            }
        });

        spinnerStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                statu = status[i].trim();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                statu = status[0].trim();
            }
        });
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("ID")) {
            collectionID = intent.getStringExtra("ID");
            refresh();
        }
        if (intent != null && intent.hasExtra("MangaID")) {
            Manga manga = mangaDatabase.getManga(intent.getStringExtra("MangaID"));
            edtCollectionID.setText(manga.getIdCollection());
            collectionID = edtCollectionID.getText().toString().trim();
            edtMangaID.setText(manga.getIdChapter());
            edtMangaName.setText(manga.getChapter());

            for (int i = 0; i < types.length; i++) {
                if (manga.getType().equals(types[i])) {
                    spinnerType.setSelection(i);
                }
            }
            for (int i = 0; i < status.length; i++) {
                if (manga.getStatus().equals(status[i])) {
                    spinnerStatus.setSelection(i);
                }
            }
            spinnerType.setSelection(0);
            spinnerType.setSelection(0);
            edtPrice.setText(String.valueOf(manga.getPrice()));
            edtNote.setText(manga.getNote());
            btnMangaRelease.setText(manga.getDate());

        }
        disableEditText(edtMangaID);
        disableEditText(edtCollectionID);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getData();
                if (checkData()) {
                    if (mangaDatabase.addManga(new Manga(collectionID, Integer.parseInt(price), note, statu, type, name, id,date))) {
                        alert.showAlertDialog("Thông báo", "Thêm thông tin tập truyện thành công", R.drawable.success, false, new AlertClass.AlertDialogCallback() {
                            @Override
                            public void onResult(boolean isDone) {
                                if (isDone) {
                                    refresh();
                                }
                            }
                        });
                    } else {
                        alert.showSimpleAlerDialog("Thông báo", "Thêm thông tin tập truyện không thành công", R.drawable.fail);
                    }
                }
            }
        });
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getData();
                if (checkData()) {
                    if (mangaDatabase.updateManga(new Manga(collectionID, Integer.parseInt(price), note, statu, type, name, id,date))) {
                        alert.showAlertDialog("Thông báo", "Sửa thông tin tập truyện thành công", R.drawable.success, false, new AlertClass.AlertDialogCallback() {
                            @Override
                            public void onResult(boolean isDone) {
                                if (isDone) {
                                    finish();
                                }
                            }
                        });
                    } else {
                        alert.showSimpleAlerDialog("Thông báo", "Sửa thông tin tập truyện không thành công", R.drawable.fail);
                    }
                }
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    getData();
                    alert.showAlertDialog("Thông báo", "Bạn có muốn xóa thông tin này không ?", R.drawable.warning, true, new AlertClass.AlertDialogCallback() {
                        @Override
                        public void onResult(boolean isDone) {
                            if (isDone) {
                               if (mangaDatabase.deleteManga(id)){
                                   alert.showAlertDialog("Thông báo", "Xóa thành công", R.drawable.success, false, new AlertClass.AlertDialogCallback() {
                                       @Override
                                       public void onResult(boolean isDone) {
                                           finish();
                                       }
                                   });

                               }
                                else {
                                   alert.showSimpleAlerDialog("Thông báo", "Xóa không thành công", R.drawable.fail);
                               }
                            }
                        }
                    });

            }
        });
        btnRefresh.setOnClickListener(new View.OnClickListener() {
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

    public void refresh() {
        edtCollectionID.setText(collectionID);

        edtMangaID.setText(mangaDatabase.createID());
        edtMangaName.setText("");
        edtMangaName.requestFocus();
        spinnerType.setSelection(0);
        spinnerStatus.setSelection(6);
        edtPrice.setText("");
        edtNote.setText("");
        btnMangaRelease.setText(getTodayDate());
    }

    public void getData() {
        id = edtMangaID.getText().toString().trim();
        name = edtMangaName.getText().toString().trim();
        price = edtPrice.getText().toString().trim();
        note = edtNote.getText().toString().trim();
        date = btnMangaRelease.getText().toString().trim();
    }

    public boolean checkData() {
        if (!name.isEmpty() && !price.isEmpty()) {
            if (TextUtils.isDigitsOnly(price)) {
                return true;
            } else {
                alert.showSimpleAlerDialog("Cảnh báo", "Giá tiền không đúng định dạng", R.drawable.warning);
                return false;
            }
        } else {
            alert.showSimpleAlerDialog("Cảnh báo", "Không thể bỏ trống tên tập truyện hay giá tiền", R.drawable.warning);
            return false;
        }
    }
    private String getTodayDate() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) +1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return day + "/" + month + "/" + year;
    }

    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month += 1;
                String date = day + "/" + month + "/" + year;
                btnMangaRelease.setText(date);
            }
        };
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        datePickerDialog = new DatePickerDialog(this, android.R.style.Theme_Holo_Light,dateSetListener,year, month, day);

    }
}