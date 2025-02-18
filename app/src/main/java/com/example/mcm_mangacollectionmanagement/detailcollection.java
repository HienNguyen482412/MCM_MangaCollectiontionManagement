package com.example.mcm_mangacollectionmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

import MCMClass.MangaCollection;
import adapter.ArrayAdapter_Collection;
import database.CollectionDatabase;

public class detailcollection extends BaseActivity {
    ImageButton btnAddCollection;
    ListView lvCollection;
    ArrayList<MangaCollection> list;
    ArrayAdapter_Collection adapter;
    CollectionDatabase database;
    ImageView imgBackground;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        getLayoutInflater().inflate(R.layout.activity_detailcollection, findViewById(R.id.frameContent));
        btnAddCollection = findViewById(R.id.btnAddCollection);
        lvCollection = findViewById(R.id.lvCollection);
        imgBackground = findViewById(R.id.imgCollectionBackground);
        database = new CollectionDatabase(this);
        list = new ArrayList<>();
        database.getCollectionList(list);
        if (list.isEmpty()){
            imgBackground.setVisibility(View.VISIBLE);
            lvCollection.setVisibility(View.GONE);
        }
        adapter = new ArrayAdapter_Collection(this, R.layout.detail_collection_item_layout, list);
        lvCollection.setAdapter(adapter);
        btnAddCollection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(detailcollection.this, collectioninformation.class);
                startActivity(intent);
            }
        });

        lvCollection.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(detailcollection.this, collectioninformation.class);
                intent.putExtra("collectionID", list.get(i).getId());
                startActivity(intent);
                return true;
            }
        });
        lvCollection.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(detailcollection.this, detailmanga.class);
                intent.putExtra("collID", list.get(i).getId());
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        database.getCollectionList(list);
        adapter.notifyDataSetChanged();
        if (list.isEmpty()){
            imgBackground.setVisibility(View.VISIBLE);
            lvCollection.setVisibility(View.GONE);
        }else{
            imgBackground.setVisibility(View.GONE);
            lvCollection.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void handleSearch(String newText) {
        database.searchCollection(newText.trim(), list);
        adapter.notifyDataSetChanged();
    }
}