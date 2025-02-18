package com.example.mcm_mangacollectionmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;

import java.lang.annotation.Target;

import MCMClass.AlertClass;
import database.CollectionDatabase;

public class home extends BaseActivity {
    ImageButton btnMangaCollection, btnAuthor,btnPublisher, btnStatistic;
    ImageView imgView;
    AlertClass alertClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        getLayoutInflater().inflate(R.layout.activity_home,findViewById(R.id.frameContent));
        btnMangaCollection = findViewById(R.id.btnMangaCollection);
        btnAuthor = findViewById(R.id.btnAuthor);
        btnPublisher = findViewById(R.id.btnPublisher);
        btnStatistic = findViewById(R.id.btnStatistic);
        imgView = findViewById(R.id.imageView);
        alertClass = new AlertClass(this);

        try {
            Glide.with(this).load(R.drawable.homegifex).centerCrop().into(imgView);
        }catch (Exception e){
            imgView.setImageResource(R.drawable.welcomecardc);
        }
        btnMangaCollection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(home.this, detailcollection.class);
                startActivity(intent);
            }
        });
        btnAuthor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(home.this, detailauthor.class);
                startActivity(intent);
            }
        });
        btnPublisher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(home.this, detailpublisher.class);
                startActivity(intent);
            }
        });
        btnStatistic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(home.this, statistic.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            alertClass.showAlertDialog("Thông báo", "Trở về trang đăng nhập ?", R.drawable.warning, true, new AlertClass.AlertDialogCallback() {
                @Override
                public void onResult(boolean isDone) {
                    if (isDone){
                        finish();
                    }
                }
            });
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return  false;
    }
}