package com.example.mcm_mangacollectionmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
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

import MCMClass.Author;
import adapter.ArrayAdaper_Author;
import database.AuthorDatabase;

public class detailauthor extends BaseActivity {
    ImageButton btnAddAuthor;
    ListView lvAuthor;
    AuthorDatabase database;
    ArrayAdaper_Author adaperAuthor;
    ArrayList<Author> authors;
    ImageView imgBackground;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        getLayoutInflater().inflate(R.layout.activity_detailauthor, findViewById(R.id.frameContent));
        btnAddAuthor = findViewById(R.id.btnAddAuthor);
        lvAuthor = findViewById(R.id.lvAuthor);
        imgBackground = findViewById(R.id.imgAuthorBackground);
        database = new AuthorDatabase(this);
        authors = new ArrayList<>();
        database.getAuthorList(authors);
        if (authors.isEmpty()){
            imgBackground.setVisibility(View.VISIBLE);
            lvAuthor.setVisibility(View.GONE);
        }
        adaperAuthor = new ArrayAdaper_Author(this, R.layout.detail_author_item_layout,authors);
        lvAuthor.setAdapter(adaperAuthor);
        lvAuthor.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(detailauthor.this, authorinformation.class);
                intent.putExtra("authorID", authors.get(i).getId());
                startActivity(intent);
            }
        });
        btnAddAuthor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(detailauthor.this, authorinformation.class);
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
       database.searchAuthor(newText.toLowerCase().trim(),authors);
       adaperAuthor.notifyDataSetChanged();

    }

    @Override
    protected void onResume() {
        database.getAuthorList(authors);
        adaperAuthor.notifyDataSetChanged();
        if (authors.isEmpty()){
            imgBackground.setVisibility(View.VISIBLE);
            lvAuthor.setVisibility(View.GONE);
        }
        else {
            imgBackground.setVisibility(View.GONE);
            lvAuthor.setVisibility(View.VISIBLE);
        }
        super.onResume();
    }
}