package com.example.mcm_mangacollectionmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import MCMClass.AlertClass;

public class reportsetting extends BaseActivity {
    EditText edtReport;
    Button btnReport, btnRefresh;
    AlertClass alert;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        getLayoutInflater().inflate(R.layout.activity_reportsetting, findViewById(R.id.frameContent));
        edtReport = findViewById(R.id.edtReport);
        btnReport = findViewById(R.id.btnReport);
        btnRefresh = findViewById(R.id.btnRRefresh);
        alert = new AlertClass(this);
        btnReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String content = edtReport.getText().toString();
                if (!content.isEmpty()){
                    sendEmail("BÁO LỖI", content);
                }
                else {
                    alert.showSimpleAlerDialog("Cảnh báo", "Không được bỏ trống thông tin",R.drawable.warning);
                }
            }
        });
        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edtReport.setText("");
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

    @Override
    protected void onResume() {
        super.onResume();
        edtReport.setText("");
    }
}