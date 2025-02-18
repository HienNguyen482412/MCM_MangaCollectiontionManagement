package com.example.mcm_mangacollectionmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import MCMClass.AlertClass;
import database.UserDatabase;

public class assesementsetting extends BaseActivity {
    RatingBar ratingBar;
    EditText edtComment;
    Button btnSend, btnRefresh;
    AlertClass alert;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        getLayoutInflater().inflate(R.layout.activity_assesementsetting, findViewById(R.id.frameContent));
        ratingBar = findViewById(R.id.ratingBar);
        edtComment = findViewById(R.id.edtComment);
        btnSend = findViewById(R.id.btnASend);
        btnRefresh = findViewById(R.id.btnARefresh);;
        ratingBar.setRating(5);
        alert = new AlertClass(this);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String content = edtComment.getText().toString();
                float score = ratingBar.getNumStars();
                if (!content.isEmpty() && score != 0){
                    sendEmail("ĐÁNH GIÁ", content + "\nSố điểm: " + score);
                }
                else {
                    alert.showSimpleAlerDialog("Cảnh báo", "Cần nhập đầy đủ thông tin",R.drawable.warning);
                }
            }
        });
        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edtComment.setText("");
                edtComment.requestFocus();
                ratingBar.setRating(5);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return false;
    }
    public  void sendEmail(String subject, String content){
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"hibarikyoya2k4@gmail.com"});
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, content);
        intent.setType("message/rfc822");
        startActivity(Intent.createChooser(intent,"Choose email:"));
    }
}