package com.example.mcm_mangacollectionmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

import MCMClass.Publisher;
import adapter.ArrayAdapter_Publisher;
import database.PublisherDatabase;

public class detailpublisher extends BaseActivity {
    ListView lvPublisher;
    ImageButton btnAddPublisher;
    PublisherDatabase database;
    ArrayAdapter_Publisher adapter;
    ArrayList<Publisher> publishers;
    ImageView imgBackground;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        getLayoutInflater().inflate(R.layout.activity_detailpublisher, findViewById(R.id.frameContent));
        lvPublisher = findViewById(R.id.lvPublisher);
        btnAddPublisher = findViewById(R.id.btnAddPublisher);
        imgBackground = findViewById(R.id.imgPublisherBackground);
        publishers = new ArrayList<>();
        database = new PublisherDatabase(this);
        database.getPublisherList(publishers);
        if (publishers.isEmpty()){
            imgBackground.setVisibility(View.VISIBLE);
           lvPublisher.setVisibility(View.GONE);
        }
        adapter = new ArrayAdapter_Publisher(this, R.layout.detail_publisher_item_layout, publishers);
        lvPublisher.setAdapter(adapter);
        btnAddPublisher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(detailpublisher.this, publisherinformation.class);
                startActivity(intent);
            }
        });
        lvPublisher.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(detailpublisher.this, publisherinformation.class);
                intent.putExtra("publisherID", publishers.get(i).getId());
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void handleSearch(String newText) {
        database.searchPublisher(newText.toLowerCase().trim(), publishers);
        adapter.notifyDataSetChanged();

    }

    @Override
    protected void onResume() {
        super.onResume();
        database.getPublisherList(publishers);
        adapter.notifyDataSetChanged();
        if (publishers.isEmpty()){
            imgBackground.setVisibility(View.VISIBLE);
            lvPublisher.setVisibility(View.GONE);
        }
        else{
            imgBackground.setVisibility(View.GONE);
            lvPublisher.setVisibility(View.VISIBLE);
        }
    }
}