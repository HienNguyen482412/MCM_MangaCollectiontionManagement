package com.example.mcm_mangacollectionmanagement;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Calendar;

import MCMClass.Manga;
import adapter.ArrayAdapter_Manga;
import database.MangaDatabase;

public class detailmanga extends BaseActivity {
    EditText edtChapterName;
    ImageView imgBackground;
    ImageButton btnMangaSearch, btnAddManga;
    ListView lvManga;
    MangaDatabase database;
    ArrayAdapter_Manga adapter;
    ArrayList<Manga> list;
    Button btnDate;
    String collectionID;
    DatePickerDialog datePickerDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        getLayoutInflater().inflate(R.layout.activity_detailmanga, findViewById(R.id.frameContent));
        edtChapterName = findViewById(R.id.edtChapterNumber);
        btnDate = findViewById(R.id.btnSearchDate);
        btnMangaSearch = findViewById(R.id.btnMangaSearch);
        btnAddManga = findViewById(R.id.btnAddManga);
        lvManga = findViewById(R.id.lvManga);
        imgBackground = findViewById(R.id.imgMangaBackground);
        database = new MangaDatabase(this);
        list = new ArrayList<>();
        initDatePicker();
        btnDate.setText(getTodayDate());
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("collID")) {
            collectionID = intent.getStringExtra("collID");
            database.getMangaList(collectionID, list);
        }
        if (list.isEmpty()){
            imgBackground.setVisibility(View.VISIBLE);
            lvManga.setVisibility(View.GONE);
        }
        adapter = new ArrayAdapter_Manga(this, R.layout.detail_manga_item_layout, list);
        lvManga.setAdapter(adapter);

        btnAddManga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(detailmanga.this, mangainformation.class);
                intent.putExtra("ID", collectionID);
                startActivity(intent);
            }
        });
        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialog.show();
            }
        });
        lvManga.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(detailmanga.this, mangainformation.class);
                intent.putExtra("MangaID", list.get(i).getIdChapter());
                startActivity(intent);
            }
        });

        btnMangaSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                database.seachManga(edtChapterName.getText().toString().trim(),btnDate.getText().toString().trim(), list);
                adapter.notifyDataSetChanged();
            }
        });
        btnMangaSearch.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                database.getMangaList(collectionID, list);
                adapter.notifyDataSetChanged();
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        database.getMangaList(collectionID, list);
        adapter.notifyDataSetChanged();
        if (list.isEmpty()){
            imgBackground.setVisibility(View.VISIBLE);
            lvManga.setVisibility(View.GONE);
        }
        else {
            imgBackground.setVisibility(View.GONE);
            lvManga.setVisibility(View.VISIBLE);
        }
    }
    private String getTodayDate() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return day + "/" + month + "/" + year;
    }

    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month += 1;
                String date = day + "/" + month + "/" + year;
                btnDate.setText(date);
            }
        };
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        datePickerDialog = new DatePickerDialog(this, android.R.style.Theme_Holo_Light,dateSetListener,year, month, day);

    }
}