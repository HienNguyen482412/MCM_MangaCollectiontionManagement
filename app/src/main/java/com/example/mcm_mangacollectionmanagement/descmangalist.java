package com.example.mcm_mangacollectionmanagement;

import android.content.Intent;
import android.opengl.Visibility;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

import adapter.ArrayAdapter_DESCMangaList;
import database.StatisticClass;

public class descmangalist extends BaseActivity {

    ListView lvManga;
    ArrayList<String> mangaList;
    ArrayAdapter_DESCMangaList mangaAdapter;
    StatisticClass statisticClass;
    ImageView imgListBackground;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        getLayoutInflater().inflate(R.layout.activity_descmangalist, findViewById(R.id.frameContent));
        lvManga = findViewById(R.id.lvDescManga);
        imgListBackground = findViewById(R.id.imgDescListBackground);
        mangaList = new ArrayList<>();
        statisticClass = new StatisticClass(this);
        statisticClass.showDESCMangaList(mangaList);
        mangaAdapter = new ArrayAdapter_DESCMangaList(this, R.layout.desc_manga_list_item_layout, mangaList);
        lvManga.setAdapter(mangaAdapter);
        if (mangaList == null || mangaList.size() == 0){
            imgListBackground.setVisibility(ListView.VISIBLE);
        }
        lvManga.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(descmangalist.this, mangainformation.class);
                intent.putExtra("MangaID", mangaList.get(i).split("-")[1].trim());
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }
}