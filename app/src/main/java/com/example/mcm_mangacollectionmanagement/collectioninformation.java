package com.example.mcm_mangacollectionmanagement;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Calendar;

import MCMClass.AlertClass;
import MCMClass.MangaCollection;
import database.AuthorDatabase;
import database.CollectionDatabase;
import database.PublisherDatabase;

public class collectioninformation extends BaseActivity {
    EditText edtCollectionID, edtCollectionName, edtCollectionNote;
    Spinner spinnerAuthor, spinnerPublisher, spinnerStatus;
    ImageButton  btnCollectionAdd, btnCollectionEdit, btnCollectionDelete, btnCollectionRefresh;
    Button btnReleaseDate;
    ImageView imgCover;
    ActivityResultLauncher<Intent> activityResultLauncher;

    String[] statusList = {"Tiếp tục", "Tạm dừng", "Hoàn thành"};
    ArrayAdapter<String> statusAdapter, authorAdapter, publisherAdapter;
    ArrayList<String> authorList, publisherList;
    CollectionDatabase collectionDatabase;
    AuthorDatabase authorDatabase;
    PublisherDatabase publisherDatabase;

    AlertClass alert;
    String id, name, author, publisher, status, date, note;
    DatePickerDialog datePickerDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        getLayoutInflater().inflate(R.layout.activity_collectioninformation,findViewById(R.id.frameContent));
        edtCollectionID = findViewById(R.id.edtCollectionID);
        edtCollectionName = findViewById(R.id.edtCollectionName);
        edtCollectionNote = findViewById(R.id.edtCollectionNote);
        spinnerAuthor = findViewById(R.id.spinnerAuthor);
        spinnerPublisher = findViewById(R.id.spinnerPublisher);
        spinnerStatus = findViewById(R.id.spinnerCollectionStatus);
        btnReleaseDate = findViewById(R.id.btnRealeseDate);
        btnCollectionAdd = findViewById(R.id.btnCollectionAdd);
        btnCollectionEdit = findViewById(R.id.btnCollectionEdit);
        btnCollectionDelete = findViewById(R.id.btnCollectionDelete);
        btnCollectionRefresh = findViewById(R.id.btnCollectionRefresh);

        alert = new AlertClass(this);
        collectionDatabase = new CollectionDatabase(this);
        authorDatabase = new AuthorDatabase(this);
        publisherDatabase = new PublisherDatabase(this);

        authorList = new ArrayList<>();
        authorDatabase.getAuthorSpinner(authorList);
        publisherList = new ArrayList<>();
        publisherDatabase.getPublisherSpinner(publisherList);

        statusAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, statusList);
        statusAdapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spinnerStatus.setAdapter(statusAdapter);

        authorAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, authorList);
        authorAdapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spinnerAuthor.setAdapter(authorAdapter);

        publisherAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, publisherList);
        publisherAdapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spinnerPublisher.setAdapter(publisherAdapter);

        imgCover = findViewById(R.id.imgCover);
        registerResult();
        imgCover.setOnClickListener(view -> pickImage());

        initDatePicker();

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("collectionID")){
            MangaCollection mangaCollection = collectionDatabase.getCollection(intent.getStringExtra("collectionID"));
            edtCollectionID.setText(mangaCollection.getId());
            edtCollectionName.setText(mangaCollection.getName());
            edtCollectionNote.setText(mangaCollection.getNote());

            for (int i = 0; i< authorList.size();i++){
                if (authorList.get(i).trim().contains(mangaCollection.getAuthorId().trim())){
                    spinnerAuthor.setSelection(i);
                }
            }
            for (int i = 0; i< publisherList.size();i++){
                if (publisherList.get(i).trim().contains(mangaCollection.getPublisherId().trim())){
                    spinnerPublisher.setSelection(i);
                }
            }
            for (int i = 0; i< statusList.length;i++){
                if (statusList[i].trim().equals(mangaCollection.getStatus().trim())){
                    spinnerStatus.setSelection(i);
                }
            }

            btnReleaseDate.setText(mangaCollection.getDate());
            byte[] bytes = mangaCollection.getImage();
            if (bytes!= null && bytes.length> 0){
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                imgCover.setImageBitmap(bitmap);
            }
            else {
                imgCover.setImageResource(R.drawable.publishericon);
            }
        }
        else {
            refresh();
        }

        btnReleaseDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialog.show();
            }
        });

        spinnerStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                status = statusList[i].trim();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                status = statusList[0].trim();
            }
        });
        spinnerAuthor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                author = authorList.get(i).split("-")[0].trim();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                author = authorList.get(0).split("-")[0].trim();
            }
        });
        spinnerPublisher.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                publisher = publisherList.get(i).split("-")[0].trim();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                publisher = publisherList.get(0).split("-")[0].trim();
            }
        });
        disableEditText(edtCollectionID);
        btnCollectionAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getData();
                if (checkData()){
                    BitmapDrawable drawable = (BitmapDrawable) imgCover.getDrawable();
                    Bitmap bitmap = drawable.getBitmap();
                    ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArray);
                    byte[] img = byteArray.toByteArray();
                    if (collectionDatabase.addCollection(new MangaCollection(id, name, publisher,author, status, date, note, img))){
                        alert.showAlertDialog("Thông báo", "Thêm thông tin bộ truyện thành công", R.drawable.success, false, new AlertClass.AlertDialogCallback() {
                            @Override
                            public void onResult(boolean isDone) {
                                if (isDone) {
                                    refresh();
                                }
                            }
                        });
                    }
                    else {
                        alert.showSimpleAlerDialog("Thông báo", "Thêm bộ truyện không thành công", R.drawable.fail);
                    }
                }


            }
        });
        btnCollectionEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getData();
                if (checkData()){
                    BitmapDrawable drawable = (BitmapDrawable) imgCover.getDrawable();
                    Bitmap bitmap = drawable.getBitmap();
                    ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArray);
                    byte[] img = byteArray.toByteArray();
                    if (collectionDatabase.updateCollection(new MangaCollection(id, name, publisher,author, status, date, note, img))){
                        alert.showAlertDialog("Thông báo", "Sửa thông tin bộ truyện thành công", R.drawable.success, false,new AlertClass.AlertDialogCallback() {
                            @Override
                            public void onResult(boolean isDone) {
                                if (isDone){
                                    finish();
                                }
                            }
                        });
                    }
                    else {
                        alert.showSimpleAlerDialog("Thông báo", "Sửa thông tin bộ truyện không thành công", R.drawable.fail);
                    }
                }
            }
        });
        btnCollectionDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getData();
                alert.showAlertDialog("Thông báo", "Bạn có chắc chắn muốn xóa thông tin này không ?", R.drawable.warning, true, new AlertClass.AlertDialogCallback() {
                    @Override
                    public void onResult(boolean isDone) {
                        if (isDone){
                            if (collectionDatabase.deleteCollection(id)){
                                finish();
                            }
                            else {
                                alert.showSimpleAlerDialog("Thông báo", "Xóa thông tin bộ truyện không thành công", R.drawable.fail);
                            }

                        }
                    }
                });
            }
        });
        btnCollectionRefresh.setOnClickListener(new View.OnClickListener() {
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
    private void pickImage() {
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
                    imgCover.setImageURI(imageUri);
                } catch (Exception e) {
                    Log.d("TAG","No Image Selected");
                }
            }
        });
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
                btnReleaseDate.setText(date);
            }
        };
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        datePickerDialog = new DatePickerDialog(this, android.R.style.Theme_Holo_Light,dateSetListener,year, month, day);

    }

    public  void getData(){
        id = edtCollectionID.getText().toString().trim();
        name = edtCollectionName.getText().toString().trim();
        date = btnReleaseDate.getText().toString().trim();
        note = edtCollectionNote.getText().toString().trim();
    }
    public void refresh(){
        edtCollectionID.setText(collectionDatabase.createID());
        edtCollectionName.requestFocus();
        edtCollectionName.setText("");
        edtCollectionNote.setText("");
        spinnerPublisher.setSelection(0);
        spinnerAuthor.setSelection(0);
        spinnerStatus.setSelection(0);
        btnReleaseDate.setText(getTodayDate());
        imgCover.setImageResource(R.drawable.nocover);
    }


    public boolean checkData(){
        if (name.isEmpty()){
            alert.showSimpleAlerDialog("Cảnh báo", "Không được bỏ trống tên bộ truyện", R.drawable.warning);
            return false;
        }
        return true;
    }
}